import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main 
{
	private ArrayList<Integer> CTable = new ArrayList<Integer>();
	private String bwt;
	private HashMap<Character, Integer> uniqueCharIndex = new HashMap<Character, Integer>();
	private int[][] rankTable;
	
	public void execute()
	{
		Scanner sc = new Scanner(System.in);
		
		//System.out.print("String Input:");
		//String input = sc.nextLine();
		String input = "abracadabra$";
		System.out.println(input.length());
        long BWTabsoluteStartTime = System.nanoTime();
        BWT(input);

        System.out.println("BWT Runtime: "+ + (System.nanoTime() - BWTabsoluteStartTime) / 1000000000f + " Seconds" );
        System.out.println("Burrow Wheel Transformed: " + this.bwt);
		
		System.out.println(CTable.get(uniqueCharIndex.get('a')));
		int nextChar = uniqueCharIndex.get('a') + 1;
		
		System.out.println(CTable.get(nextChar));
		generateRankTable();
		System.out.println("Substring: ");
		while((input = sc.nextLine()).length() > 0)
		{
			FMIndex(input);
		}
		
		
	}
	
	public void BWT(String input)
	{
		InputComparator comparator = new InputComparator(input);
        //Integer[] indices = IntStream.of( IntStream.range(0, input.length()).toArray() ).boxed().toArray( Integer[]::new );
		
		//Arrays.sort(indices, comparator);
		//Arrays.parallelSort(indices, comparator);

		int[] indices = IntStream.range(0, input.length()).toArray();
		ParallelMergeSort.parallelMergeSort(input, indices, 2);


		 
		String bwt = "";
		int c = 0;
		for(int i = 0; i < input.length(); i++)
		{
			bwt = bwt.concat(input.charAt((indices[i] + input.length() - 1) % input.length()) + "");
			
			if(i == 0 || input.charAt(indices[i]) > input.charAt(indices[i - 1]))
			{
				CTable.add(i);
				uniqueCharIndex.put(input.charAt(indices[i]), CTable.size() - 1);
			}
			
			System.out.println(input.charAt(indices[i]));
		}
		
		this.bwt = bwt;
	}
	
	public void generateRankTable()
	{
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
		
		for(int i = 0; i < uniqueCharIndex.size(); i++)
		{
			for(int j = 0; j < bwt.length(); j++)
			{
				System.out.print(rankTable[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void FMIndex(String input)
	{
		int s, e;
		s = CTable.get(uniqueCharIndex.get(input.charAt(input.length() - 1))) + 1;
		e = CTable.get(uniqueCharIndex.get(input.charAt(input.length() - 1)) + 1);
		System.out.println("S = " + s + " E = " + e);
		for(int i = input.length() - 2; i > 0; i--)
		{
			s = CTable.get(uniqueCharIndex.get(input.charAt(i))) + rankTable[uniqueCharIndex.get(input.charAt(i))][s - 2] + 1;
			e = CTable.get(uniqueCharIndex.get(input.charAt(i))) + rankTable[uniqueCharIndex.get(input.charAt(i))][e - 1];
			System.out.println("S = " + s + " E = " + e);
		}
		
		System.out.println("Occurences: " + (e - s + 1));
	}
	
		
	public static void main(String[] args)
	{
		new Main().execute();
		
	}
}
