package CompletableFuture.thenApplyAndthenApplyAsync;

import java.util.concurrent.*;

public class thenApplyAsync {

    public static void main(String[] args) {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            String first = "first Nilu da";
            System.out.println("async task thread:" + Thread.currentThread().getName());
            return first;
            // same as: thenApply(), only difference is that it uses new thread for the follow up synchronous task after supplyAsync()
        }, poolExecutor).thenApplyAsync((String result) ->
        {
            System.out.println("thenApply task thread:" + Thread.currentThread().getName()); // same thread as above
            return result + " then nitu";
        });

        try {
            System.out.println(completableFuture.get());
        } catch (Exception e) {
            //
        }
        /*
        * same as: thenApply(), only difference is that it uses new thread for the follow up synchronous task after supplyAsync()
        * output:
        *   async task thread:pool-1-thread-1
            thenApply task thread:ForkJoinPool.commonPool-worker-9
            first Nilu da then nitu
        *
        *
        * */
    }
}
