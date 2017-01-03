package kwaymergesort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import timing.Ticker;

public class KWayMergeSort {
	
	/**
	 * 
	 * @param K some positive power of 2.
	 * @param input an array of unsorted integers.  Its size is either 1, or some other power of 2 that is at least K
	 * @param ticker call .tick() on this to account for the work you do
	 * @return
	 */
	
	public static Integer[] kwaymergesort(int K, Integer[] input, Ticker ticker) {
		int n = input.length;

		//
		// FIXME
		// Following just copies the input as the answer
		//
		// You must replace the loop below with code that performs
		// a K-way merge sort, placing the result in ans
		//
		// The web page for this assignment provides more detail.
		//
		// Use the ticker as you normally would, to account for
		// the operations taken to perform the K-way merge sort.
		//
		
		Integer[] ans = new Integer[n];
		Queue<Integer[]> Karrayqueue = new LinkedList<Integer[]>();
		ArrayList<Integer[]> Karraylist = new ArrayList<Integer[]>();
				
		if(input.length == 1){
			//Arrays.sort(input);
			return input;
		}
		
		for(int i=0; i<K ; ++i){
			Integer[] Karray = new Integer[n/K];
			System.arraycopy(input, i*n/K, Karray, 0, n/K);
			Karrayqueue.add(kwaymergesort(K, Karray, ticker));
		}
		
		//Merge K arrays
		ans = mergequeue(Karrayqueue,ticker);
		
		return ans;
	}
	
	public static Integer[] mergequeue(Queue<Integer[]> Karrayqueue,Ticker ticker){
		int K = Karrayqueue.size();
		
		if(K<=1){
			return Karrayqueue.poll();
		}
		
		for(int i=0;i<K;i=i+2){
			Karrayqueue.offer(mergepair(Karrayqueue.poll(),Karrayqueue.poll(),ticker));
		}
		
		return mergequeue(Karrayqueue,ticker);
		
	}
	
	
	public static Integer[] mergepair(Integer[] first, Integer [] second,  Ticker ticker){
		int iFirst = 0;
		int iSecond = 0;
		int iMerged  =0;

		Integer[] ans = new Integer[first.length + second.length];
		
		for (int i=0; i < ans.length-1; ++i) {
			if(iFirst<first.length && iSecond<second.length){ 
			if(first[iFirst].compareTo(second[iSecond]) < 0){
				ans[iMerged] = first[iFirst];
				iFirst++;
				ticker.tick();
			}else{
				ans[iMerged] = second[iSecond];
				iSecond++;
				ticker.tick();
			  }
			iMerged++;
		    }
			
		}
		
		System.arraycopy(first, iFirst, ans, iMerged, first.length - iFirst);
		System.arraycopy(second, iSecond, ans, iMerged, second.length - iSecond);
		return ans;
	}
	

}
