package ShutDown;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(5);
        /*can use below implementation of threadPoolExecutor as well.
                ThreadPoolExecutor threadPoolExecutor1 = new ThreadPoolExecutor(2,5,1,
                        TimeUnit.HOURS,new ArrayBlockingQueue<>(2));
         */
        threadPoolExecutor.submit(()->{
            System.out.println("task 1 is executing");
        });

        //shutdown called.
        threadPoolExecutor.shutdown();

        // below task is rejected.
        threadPoolExecutor.submit(()->{
            System.out.println("task 2 now");
        });
    }
}