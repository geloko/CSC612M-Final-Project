public class SorterThread implements Runnable {
	private int threadCount;
	private String[] input;
	
	public SorterThread(String[] input, int threadCount) {
		this.threadCount = threadCount;
		this.input = input;
	}
	
	public void run() {
		//System.out.println("Thread Started");
		ParallelMergeSort.parallelMergeSort(input, threadCount);
	}
}