package Future;

import  java.util.concurrent.*;

public class FutureExample {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        //new thread will be created and it will perform the, it's status stored in 'future'
        java.util.concurrent.Future<?> future = threadPoolExecutor.submit(()->{
            System.out.println("this is a new task executed by :"+Thread.currentThread().getName());
        });

        //caller that is main thread is checking the status of the thread it created
        System.out.println(future.isDone());
    }



}
