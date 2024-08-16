package Future;

import java.util.concurrent.*;

public class FutureWithGet {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor1 = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS,
                new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        //new thread will be created and it will perform the task, it's status stored in 'futureobj'
        Future<?> futureobj = threadPoolExecutor1.submit(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("this is a new task executed by :"+Thread.currentThread().getName());
        });

        //caller that is main thread is checking the status of the thread it created
        System.out.println("is Done :"+futureobj.isDone());
        try {
            // wait for 2 seconds
            futureobj.get(2,TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("task didn't finish within 2 seconds "+e.toString());
        } catch (Exception e){//
        }

        try {
            futureobj.get();
        } catch (Exception e) {//
        }
        System.out.println("is Done :"+futureobj.isDone());
        System.out.println("is cancelled :"+futureobj.isCancelled());
    }
}
