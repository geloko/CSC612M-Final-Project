public class BWTSorterThread implements Runnable {
	private int[] numberArray;
	private int threadCount;
	private char[] input;
	
	public BWTSorterThread(char[] input, int[] numberArray, int threadCount) {
		this.numberArray = numberArray;
		this.threadCount = threadCount;
		this.input = input;
	}
	
	public void run() {
		//System.out.println("Thread Started");
		ParallelMergeSort.parallelBWTMergeSort(input, numberArray, threadCount);
	}
}