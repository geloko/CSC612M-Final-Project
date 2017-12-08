import java.util.Comparator;

public class InputComparator implements Comparator<Integer> 
{
	private String input;
	
	public InputComparator(String input)
	{
		this.input = input;
	}

	@Override
	public int compare(Integer index1, Integer index2) 
	{
		int i = 0;
		
		while(input.charAt((index1 + i) % input.length()) == input.charAt((index2 + i) % input.length())){
			//System.out.println("Same for index " + index1 + " and " + index2);
			i++;
		}
		
		if(input.charAt((index1 + i) % input.length()) > input.charAt((index2 + i) % input.length()))
			return 1;
		else if(input.charAt((index1 + i) % input.length()) < input.charAt((index2 + i) % input.length()))
			return -1;
		else
			System.out.println("OH NOOO");
			return 0;
			

	}	
}
