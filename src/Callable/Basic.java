package Callable;

import java.util.concurrent.*;

public class Basic {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        // below calls submit(Runnable) : as nothing is returned
        Future<?> futureObj1 = threadPoolExecutor.submit(()-> {
            System.out.println("something");
        });

        // below calls submit(Runnable) : as nothing is returned - same as above, as 1 line is within Runnable.run(), braces are note needed.
        Future<?> futureObj2 = threadPoolExecutor.submit(()->System.out.println("something again"));

        // below calls submit(Callable<T>) : as we are returning something - a return statement is there
        Future<?> futureObj3 = threadPoolExecutor.submit(()->{
            System.out.println("something again");
            return 45;
        });

        // Callable task return Integer, here type of Future object is Integers
        Future<Integer> futureObj4 = threadPoolExecutor.submit(()->{
            System.out.println("something again");
            return 45;
        });

        try {
            System.out.println(futureObj1.get());
            System.out.println(futureObj2.get());
            System.out.println(futureObj3.get());
            System.out.println(futureObj4.get());
        } catch (Exception e) {
            //
        }

    }
}