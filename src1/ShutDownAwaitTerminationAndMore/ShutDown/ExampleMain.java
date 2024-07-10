package ShutDown;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExampleMain {

    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,5,1,
                TimeUnit.HOURS,new ArrayBlockingQueue<>(2));

        threadPoolExecutor.submit(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task 1 is running");
        });

        threadPoolExecutor.shutdown();
        System.out.println("shutdown() is called and main thread is finished.");
        /*
        *
        * Here even after shutdown() is called, then already submitted task before shutdown() keeps running.
        Output:
        shutdown() is called and main thread is finished.
        task 1 is running
        *
        *
        * */
    }
}
