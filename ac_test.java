import java.util.List;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/*Make a fast-searchable dictionary by using the DLB Trie*/
public class ac_test{
	static List<String> normalResults =new ArrayList<String>();
	static List<String> userHistory = new ArrayList<String>();
	static DLB<String> dictionary = new DLB<String>();
	static List<Long> time = new ArrayList<Long>();//to calculate Average time
	
	public static void contructDLB_dictionary() throws FileNotFoundException
	{
		Scanner fileReader = new Scanner(new File("dictionary.txt"));
		while(fileReader.hasNextLine())
		{
			dictionary.insert(fileReader.nextLine());
		}
		fileReader.close();
	}
	
	public static List<String> consultedResult()
	{
		List<String> consultedResults = new ArrayList<String>();
		List<String> unEqualWords = new ArrayList<String>();
		
		for(int i = 0;i<normalResults.size(); i++)
		{
			if(normalResults.get(i).equals(userHistory.get(i)))
				{
					consultedResults.add(normalResults.get(i));
				}
			unEqualWords.add(normalResults.get(i));
		}
		if(consultedResults.size() < 5 && normalResults.size() > 5 )
		{
			for(int i =0; i<unEqualWords.size(); i++)
			{
				consultedResults.add(unEqualWords.get(i));
				if(consultedResults.size() == 5) {break;}
			}
		}
		return consultedResults;
	}
	
	public static void saveHistory (String word)
	{
		File historyFile = new File("history.txt");
		FileWriter fileWriter ;
		
		try {
			historyFile.createNewFile();
			fileWriter = new FileWriter(historyFile);
			BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
			bufferWriter.write(word +",");
			bufferWriter.close();
			fileWriter.close();
		} 

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) throws FileNotFoundException
	{
		long averageTime;
		List<String> result = new ArrayList<String>();
		contructDLB_dictionary();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter your first character");//prompt
		String input =scanner.nextLine();
		String word = "";
		while(!input.equals("!"))
		{
			
			
			while(!input.equals("$"))
			{
				word += input;
				if(Character.isDigit(input.toCharArray()[0]))
				{
					String w = result.get(Integer.parseInt(input)-1);
					System.out.println("\n WORD COMPLETED: "+ w);
					userHistory.add(w);
					saveHistory(word);
					//System.out.println(userHistory);
					
							
					break;
				}
				
				 System.out.println("Unproritized  : ");
				 result  = getSuggestions(word,result);
				 
				 if(result.isEmpty())
				 {
					 System.out.println("Key doesn't exist");
				 }
				 
				  /*result = consultedResult();
				  if(!result.isEmpty())
				  {
				 System.out.println("\n Proritized  : ");
				
				 
				 for(int i = 0;i<result.size(); i++)
				 {
					 System.out.print(i +" : "+result.get(i)+"   ");
				 }
				  }*/
				 ////
				System.out.println("\n Enter your next character");
				input = scanner.nextLine();
				if(input.equals("!")) {
					System.out.println("You have exited ");
					long sum =0;
					for(long l : time) {sum +=l;}
					averageTime = sum/time.size();
					System.out.println("Average time: " +averageTime+ "(nano seconds)" );
					System.exit(0);
					}
			}
			
			if(input.equals("$"))
			{

				userHistory.add(word);
				saveHistory(word);
				//System.out.println(userHistory);
				
			}
			
			word ="";
			System.out.println("Enter your first character");//prompt
			input =scanner.nextLine();
			
			
			
			
		}
		System.out.println("You have existed.");
		long sum =0;
		for(long l : time) {sum +=l;}
		averageTime = sum/time.size();
		System.out.println("Average time: " +averageTime+ "(nano seconds)" );
		System.exit(0);
		
		
	}
	private static List<String> getSuggestions(String key,List<String> results)
	{
		Long startTime =  System.nanoTime(); // the point the program starts its thing
		results = dictionary.autoComplete(key); //don't include space in your input, plz.
		long endTime =  System.nanoTime(); // the point the main functionality is finished without consulting
		time.add(endTime - startTime);
		System.out.println(((endTime - startTime) + "(nano seconds)" +"\n"));
		
		int count = 1;
		for(String word : results)
		{
			
			System.out.print(count +" : "+word+"   ");
			count++;
			if(count >=6)
			{
				break;
			}
			
		}
		
		return results;
	}
	
}