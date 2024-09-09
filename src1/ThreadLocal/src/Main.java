import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        threadLocal.set(Thread.currentThread().getName());

        Thread thread = new Thread(()->{
            System.out.println("thread working");
            // can re-use the same threadLocal variable.
            threadLocal.set(Thread.currentThread().getName());
            System.out.println("thread is :"+threadLocal.get());
        });

        Thread thread1 = new Thread(()->{
            System.out.println("another thread working");
            // can re-use the same threadLocal variable.
            threadLocal.set(Thread.currentThread().getName());
            System.out.println("thread is :"+threadLocal.get());
        });

        System.out.println("two :"+threadLocal.get());
        thread.start();
        thread1.start();
        System.out.println("three :"+threadLocal.get());

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,10,5, TimeUnit.MINUTES,new ArrayBlockingQueue<>(5));
        for (int i=0;i<10;i++) { // add loop always else poolExecutor.submit() will create 1 thread only and do the work.
            poolExecutor.submit(() -> {
                        System.out.println("hello moto");
                        // current thread set each time
                        threadLocal.set(Thread.currentThread().getName());
                        System.out.println("thread is :" + threadLocal.get());
                       // System.out.println(ll);
                    }
            );
        }
        poolExecutor.shutdown();

    }
}