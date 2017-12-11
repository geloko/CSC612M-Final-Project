import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main 
{
	private ArrayList<Integer> CTable = new ArrayList<Integer>();
	private String bwt;
	private HashMap<Character, Integer> uniqueCharIndex = new HashMap<Character, Integer>();
	private int[][] rankTable;
	
	static String readFile(String path, Charset encoding) throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}
	
	public void execute()
	{
		Scanner sc = new Scanner(System.in);
		String input = "";
		
		//System.out.print("String Input:");
		//input = sc.nextLine();
		
		try {
			input = readFile("input_1M.txt", StandardCharsets.UTF_8);
			input = input.replaceAll("\\n|\\r", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//input = new RandomString(1048576).nextString();

		//input = input.concat("$");
		System.out.println(input.length());
		//System.out.println(input);
		
		long BWTabsoluteStartTime = System.nanoTime();
        int[] indices = BWT(input);
        System.out.println("BWT Runtime: "+ + (System.nanoTime() - BWTabsoluteStartTime) / 1000000f + " Milliseconds" );
        
        //System.out.println("Burrow Wheel Transformed: " + bwt);
        System.out.println("BWT Length: " + bwt.length());
		//generateRankTable(input, indices);
		System.out.println("Substring: ");
		while((input = sc.nextLine()).length() > 0)
		{
			FMIndex(input);
		}
		
		
	}
	
	public int[] BWT(String input)
	{
		int[] indices = IntStream.range(0, input.length()).toArray();
		ParallelMergeSort.parallelBWTMergeSort(input.toCharArray(), indices, 8);

		 
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < input.length(); i++)
		{
			builder.append(input.charAt((indices[i] + input.length() - 1) % input.length()) + "");
		}
		this.bwt = builder.toString();
		
		return indices;
	}
	
	public void generateRankTable(String input, int[] indices)
	{
		
		int c = 0;
		for(int i = 0; i < input.length(); i++)
		{
			
			if(i == 0 || input.charAt(indices[i]) > input.charAt(indices[i - 1]))
			{
				CTable.add(i);
				uniqueCharIndex.put(input.charAt(indices[i]), CTable.size() - 1);
			}
		}
		
		rankTable = new int[uniqueCharIndex.size()][bwt.length()];
		for(int i = 0; i < bwt.length(); i++)
		{
			for(int j = 0; j < uniqueCharIndex.size(); j++)
			{
				if(i == 0)
				{
					rankTable[j][i] = 0;
				}
				else
				{
					rankTable[j][i] = rankTable[j][i - 1];
				}
			}
			rankTable[uniqueCharIndex.get(bwt.charAt(i))][i] += 1;
		}
	}
	
	public void FMIndex(String input)
	{
		/*
		int s, e;
		s = CTable.get(uniqueCharIndex.get(input.charAt(input.length() - 1))) + 1;
		if(uniqueCharIndex.get(input.charAt(input.length() - 1)) + 1 < CTable.size())
			e = CTable.get(uniqueCharIndex.get(input.charAt(input.length() - 1)) + 1);
		else
			e = bwt.length();
		
		for(int i = input.length() - 2; i >= 0; i--)
		{
			s = CTable.get(uniqueCharIndex.get(input.charAt(i))) + rankTable[uniqueCharIndex.get(input.charAt(i))][s - 2] + 1;
			e = CTable.get(uniqueCharIndex.get(input.charAt(i))) + rankTable[uniqueCharIndex.get(input.charAt(i))][e - 1];
		}

		
		System.out.println("Occurences: " + (e - s + 1));
		*/
		char[] temp = this.bwt.toCharArray();
		String[] fm_index = new String[bwt.length()];
		for(int i = 0; i < temp.length; i++)
		{
			fm_index[i] = temp[i] + "";
		}
		ParallelMergeSort.parallelMergeSort(fm_index, 16);
		
		
		
		for(int i = 1; i < input.length(); i++)
		{
			ParallelMergeSort.parallelMergeSort(fm_index, 16);
			for(int j = 0; j < temp.length; j++)
			{
				fm_index[j] = temp[j] + fm_index[j];
			}
			System.out.println();
		}
		
		int substring_count = 0;
		ArrayList<Integer> indices = new ArrayList<Integer>();
		for(int i = 0; i < fm_index.length; i++)
		{
			if(fm_index[i].equals(input))
			{
				substring_count++;
				indices.add(i);
			}
		}
		
		System.out.println("Number of Occurrences: " + substring_count);
		//if(indices.size() > 0)
		//	System.out.print("Location of Occurences: " + indices.toString());
		System.out.println();
				
	}
	
		
	public static void main(String[] args)
	{
		new Main().execute();
		
	}
}
