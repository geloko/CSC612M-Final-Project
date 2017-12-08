

import java.util.*;

public class ParallelMergeSort
{

    public static void parallelMergeSort(String input, int[] dataSet, int threadCount)
    {
        if (threadCount <= 1)
        {
            mergeSort(input.toCharArray(), dataSet);
        } else
        {
            if (dataSet.length >= 2)
            {

                int[] left = new int[dataSet.length / 2];
                int[] right = new int[dataSet.length - (dataSet.length / 2)];
                System.arraycopy(dataSet, 0, left, 0, dataSet.length / 2);
                System.arraycopy(dataSet, dataSet.length / 2, right, 0, dataSet.length - (dataSet.length / 2));

                Thread lThread = new Thread(new SorterThread(input, left, threadCount / 2));
                Thread rThread = new Thread(new SorterThread(input, right, threadCount / 2));
                lThread.start();
                rThread.start();

                try
                {
                    lThread.join();
                    rThread.join();
                } catch (InterruptedException ie)
                {
                }

                merge(input.toCharArray(), left, right, dataSet);
            }
        }
    }

    public static void mergeSort(char[] inputArr, int[] dataSet)
    {
        if (dataSet.length >= 2)
        {
            int[] left = Arrays.copyOfRange(dataSet, 0, dataSet.length / 2);
            int[] right = Arrays.copyOfRange(dataSet, dataSet.length / 2, dataSet.length);
            mergeSort(inputArr, left);
            mergeSort(inputArr, right);
            merge(inputArr, left, right, dataSet);
        }
    }

    public static void merge(char[] inputArr, int[] left, int[] right, int[] dataSet)
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
        
}
