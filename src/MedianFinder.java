//This class uses two heaps to store the data and return the median

import java.util.Collections;
import java.util.PriorityQueue;

public class MedianFinder {
    PriorityQueue<Float> maxHeap;//lower half
    PriorityQueue<Float> minHeap;//higher half

    public MedianFinder(){
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());//head is the biggest num
        minHeap = new PriorityQueue<>();//head is the smallest num
    }

    //add new num to lower half,
    //and then transfer the biggest num from the lower half to the higher half
    //if the size of higher half is larger than the lower half, transfer its smallest num to the lower half
    public void addNum(float num) {
        maxHeap.offer(num);
        minHeap.offer(maxHeap.poll());

        if(maxHeap.size() < minHeap.size()){
            maxHeap.offer(minHeap.poll());
        }
    }
    //return the count of transactions
    public int count(){
        return maxHeap.size()+minHeap.size();
    }

    //if the higher half and the lower half are the same, the median is the avg of their head
    //if lower half has more numbers (by 1), its head is the median
    public int findMedian() {
        Float mf;
        if(maxHeap.size()== minHeap.size()){
            mf = (maxHeap.peek()+(minHeap.peek()))/2;
        }else{
            mf = maxHeap.peek();
        }
        //round half up
        int m = Math.round(mf);
        if ((mf - m) >= 0.5){
            return m+1;
        }
        else return m;
    }
    //return the sum of total donations
    public int sum(){
        Object[] t1 = maxHeap.toArray();
        Object[] t2 = minHeap.toArray();
        Float sumf = 0f;
        for (int i=0; i< t1.length; i++)
        {
            sumf = sumf + Float.parseFloat(t1[i].toString());
        }
        for (int j=0; j < t2.length; j++)
        {
            sumf = sumf + Float.parseFloat(t2[j].toString());
        }
        //round half up
        int sum = Math.round(sumf);
        if ((sumf - sum) >= 0.5){
            return sum+1;
        }
        else return sum;
    }
}
