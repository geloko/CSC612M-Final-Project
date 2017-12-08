public class SorterThread implements Runnable {
	private int[] numberArray;
	private int threadCount;
	private String input;
	
	public SorterThread(String input, int[] numberArray, int threadCount) {
		this.numberArray = numberArray;
		this.threadCount = threadCount;
		this.input = input;
	}
	
	public void run() {
		System.out.println("Thread Started");
		ParallelMergeSort.parallelMergeSort(input, numberArray, threadCount);
	}
}