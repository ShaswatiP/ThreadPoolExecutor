package ShutDown;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class shutdownNowExample {
    public static void main(String[] args){

       ExecutorService threadPoolExecutor =  Executors.newFixedThreadPool(5);
        threadPoolExecutor.submit(()->{
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task 1 is executing");
        });

        //shutdownNow called.
        threadPoolExecutor.shutdownNow();
        System.out.println("main thread is completed");

        /*
        * Output:
          main thread is completed
        *
        * */
    }
}
