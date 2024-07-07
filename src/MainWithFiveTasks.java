import java.util.concurrent.*;

public class MainWithFiveTasks {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        ThreadPoolExecutor executor1 = new ThreadPoolExecutor(2,4,1,
                TimeUnit.MINUTES,new ArrayBlockingQueue<>(2), new customThreadFactory(), new customRejectionPolicy());

        for(int i=0;i<5;i++){
            executor1.submit(()->{
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("task is processed by :"+Thread.currentThread().getName());
            });
        }

        executor1.shutdown();
        /*
        *
        * Output of above:
        * Hello world!
            task is processed by :Thread-1
            task is processed by :Thread-0
            task is processed by :Thread-2
            task is processed by :Thread-2
            task is processed by :Thread-0
        *
        *
        * here corePoolSize = 2, so at one moment 2 threads are running, when task3 and 4 comes in they are put in the Queue of size 2,
        * when 5th task comes in as threads and busy and Queue is full, maxPoolSize is checked, it is 4 and threadPoolSize is 2 currently so new thread 'thread-2' is created
        */

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,4,1,
                TimeUnit.MINUTES,new ArrayBlockingQueue<>(2), new customThreadFactory(), new customRejectionPolicy());

        for(int i=0;i<5;i++){
            executor.submit(()->{
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("task is processed by :"+Thread.currentThread().getName());
            });
        }

        executor.shutdown();

    }
}