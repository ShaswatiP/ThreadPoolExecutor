package ThrePoolForkJoinExample;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class main {
    public static void main(String[] args){
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool(); // returns ForkJoinPool
        // submit() puts the task into submissionQueue.
        Future<Integer> futureObj = forkJoinPool.submit(new computeSumTask(0,99)); // find the sum from 0 to 99
        try {
            System.out.println(futureObj.get());
        }  catch (Exception e) {
            //
        }
    }
}

//RecursiveTask makes the below task, divide into smaller task when fork() is called.
class computeSumTask extends RecursiveTask<Integer>{

    int start;
    int end;

    public computeSumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if(start > end){
        return 0;
        }
        int sum = 0;
        // when numbers are less than 4 in number don't divide into smaller tasks
        if(end - start < 4){
            for(int i = start;i<=end;i++){
                sum = sum+i;
            }
            return sum;
        }
        else {
            int mid = (start+end)/2;
            computeSumTask left = new computeSumTask(start,mid); // similar to recursion in binary search, here we are doing it via thread
            computeSumTask right = new computeSumTask(mid+1,end);
            left.fork(); //  creates smaller sub-task that computes sum of start to mid
            right.fork(); // creates smaller sub-task that computes sum of (mid+1) to end

            // combine the results of the sub-tasks
            int leftsum = left.join();
            int rightsum = right.join();
            return leftsum+rightsum;
        }
    }
}