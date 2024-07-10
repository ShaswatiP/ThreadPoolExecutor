package ShutDownThenAwaitTermination;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main(String[] args){

        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(5);

        threadPoolExecutor.submit(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task 1 is executing");
        });

        //shutdown called.
        threadPoolExecutor.shutdown();
        System.out.println("wait for 2 seconds after shutDown");
        // main thread waits for 2 seconds - if task won't finish within 2 seconds, simply return false
        try {
            boolean completed = threadPoolExecutor.awaitTermination(2000, TimeUnit.MILLISECONDS);
            System.out.println("task completed :"+completed);
        } catch (Exception e) {
           //
        }
        System.out.println("main thread is completed");
    }
}
