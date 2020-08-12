/*De La Briandais (DLB) trie*/
import java.util.*;
public class DLB<E> {
    private DLBnode root;
    private int n; //NUMBER OF KEYS EXIST
    public DLB(){
    	root = new DLBnode(' ');
    	n = 0;
    }
  
    public void insert(String key)
    {
    	if(search(key) == true)
		{
			return;
		}
    	DLBnode current = root;
    	DLBnode previous;
    	for(char ch : key.toCharArray())
    	{
    		previous = current;
    		DLBnode child = current.getChild(ch);
    		if(child!=null)
    		{
    			current = child;
    			child.parent = previous;
    		}
    		else {
    			current.children.add(new DLBnode(ch));
    			current = current.getChild(ch);
    			current.parent = previous;
    		}
    	}
    	current.isEnd = true;
		
    } 


    public List<String> autoComplete(String key)
    {
    	DLBnode lastNode = root;
    	for(int i =0; i<key.length();i++)
    	{
    		lastNode = lastNode.getChild(key.charAt(i));
    		if(lastNode == null)
    		{
    			return new ArrayList<String>();
    		}    		
    	}
    	//1.find the last node of the prefix
    	//2.find all the words associated with the node
    	return lastNode.getWords();
    	
    }
   
    public boolean search (String key) {
    	DLBnode current = root;
    	for(char ch : key.toCharArray())
    	{
    		if(current.getChild(ch) == null)
    		{
    			return false;
    		}
    		else {
    			current = current.getChild(ch);
    		}
    	}
    	if(current.isEnd == true) {return true;}
    	return false;
    }
    public int getSize() {
    	return n;
    }
  
    private static class DLBnode
    {
    	private boolean isEnd ;//indicate if this is a valid word
    	private char value;//the character
    	private LinkedList<DLBnode> children ;
    	//public DLBnode rightSib;
    	private DLBnode parent;

    	public DLBnode(char c){ 
    		value = c;
    		children = new LinkedList<DLBnode>();
    		isEnd = false;
    	}
    	
    	public String toString() {
    		if(parent == null) {return "";}
    		return parent.toString() + new String(new char[] {value});//Concatenate the parent node characters
    	}
    	
		public DLBnode getChild(char c) { //gets the child nodes of a given character
			if(children != null) {
				for(DLBnode child : children) {
					if(child.value == c)
						{
							return child;
						}
				}
			
			}
			return null;
		}
		
		protected List<String> getWords(){ //returns a list of words that are marked by a character 
			List<String> list = new ArrayList<String>();
			if(isEnd) {
				list.add(toString());
			}
			
			if(children != null)
			{
				for(int i = 0 ; i<children.size(); i++)
				{
					if(children.get(i) != null) {
						list.addAll(children.get(i).getWords());
					}
				}
			}
			return list;
		}
    	
    }
}
