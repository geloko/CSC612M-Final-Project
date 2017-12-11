import java.util.Arrays;

public class ParallelMergeSort
{

    public static void parallelBWTMergeSort(char[] input, int[] dataSet, int threadCount)
    {
        if (threadCount <= 1)
        {
            bwtMergeSort(input, dataSet);
        } else
        {
            if (dataSet.length >= 2)
            {

                int[] left = new int[dataSet.length / 2];
                int[] right = new int[dataSet.length - (dataSet.length / 2)];
                System.arraycopy(dataSet, 0, left, 0, dataSet.length / 2);
                System.arraycopy(dataSet, dataSet.length / 2, right, 0, dataSet.length - (dataSet.length / 2));

                Thread lThread = new Thread(new BWTSorterThread(input, left, threadCount / 2));
                Thread rThread = new Thread(new BWTSorterThread(input, right, threadCount / 2));
                
                lThread.start();
                rThread.start();
                long BWTabsoluteStartTime = System.nanoTime();
                try
                {
                    lThread.join();
                    rThread.join();
                } catch (InterruptedException ie)
                {
                }
                bwtMerge(input, left, right, dataSet);
            }
        }
    }

    public static void bwtMergeSort(char[] inputArr, int[] dataSet)
    {
        if (dataSet.length >= 2)
        {
            int[] left = Arrays.copyOfRange(dataSet, 0, dataSet.length / 2);
            int[] right = Arrays.copyOfRange(dataSet, dataSet.length / 2, dataSet.length);
            bwtMergeSort(inputArr, left);
            bwtMergeSort(inputArr, right);
            bwtMerge(inputArr, left, right, dataSet);
        }
    }

    public static void bwtMerge(char[] inputArr, int[] left, int[] right, int[] dataSet)
    {

        int x = 0;
        int y = 0;
        for (int i = 0; i < dataSet.length; i++)
        {
            if (y >= right.length || (x < left.length && inputArr[left[x]] < inputArr[right[y]]))
            {
                dataSet[i] = left[x];
                x++;
            }
            else if(x < left.length && inputArr[left[x]] == inputArr[right[y]])
            {
            	int j = 1;
            	while(inputArr[(left[x] + j) % inputArr.length] == inputArr[(right[y] + j)  % inputArr.length])
            	{
            		j++;
            	}
            	
            	if(inputArr[(left[x] + j) % inputArr.length] < inputArr[(right[y] + j)  % inputArr.length])
            	{
            		dataSet[i] = left[x];
                    x++;
            	}
            	else
            	{
            		dataSet[i] = right[y];
                    y++;
            	}
            	
            }
            else
            {
                dataSet[i] = right[y];
                y++;
            }
        }
    }
    
    public static void parallelMergeSort(String[] dataSet, int threadCount)
    {
        if (threadCount <= 1)
        {
            mergeSort(dataSet);
        } else
        {
            if (dataSet.length >= 2)
            {

                String[] left = new String[dataSet.length / 2];
                String[] right = new String[dataSet.length - (dataSet.length / 2)];
                System.arraycopy(dataSet, 0, left, 0, dataSet.length / 2);
                System.arraycopy(dataSet, dataSet.length / 2, right, 0, dataSet.length - (dataSet.length / 2));

                Thread lThread = new Thread(new SorterThread(left, threadCount / 2));
                Thread rThread = new Thread(new SorterThread(right, threadCount / 2));
                
                lThread.start();
                rThread.start();
                long BWTabsoluteStartTime = System.nanoTime();
                try
                {
                    lThread.join();
                    rThread.join();
                } catch (InterruptedException ie)
                {
                }
                merge(left, right, dataSet);
            }
        }
    }

    public static void mergeSort(String[] dataSet)
    {
        if (dataSet.length >= 2)
        {
            String[] left = Arrays.copyOfRange(dataSet, 0, dataSet.length / 2);
            String[] right = Arrays.copyOfRange(dataSet, dataSet.length / 2, dataSet.length);
            mergeSort(left);
            mergeSort(right);
            merge(left, right, dataSet);
        }
    }

    public static void merge(String[] left, String[] right, String[] dataSet)
    {

        int x = 0;
        int y = 0;
        for (int i = 0; i < dataSet.length; i++)
        {
            if (y >= right.length || (x < left.length && left[x].compareTo(right[y]) < 0))
            {
                dataSet[i] = left[x];
                x++;
            }
            else
            {
                dataSet[i] = right[y];
                y++;
            }
        }
    }

        
}
