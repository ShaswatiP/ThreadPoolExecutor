package CompletableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class main {
    public static void main(String[] args) {

        // if we don't want to define a ThreadPoolExecutor use CompletableFuture.supplyAsync() -> here it uses default forkJoinPool as threadPoolExecutor.
        // here we have no control over the threadPool
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(()->{
            System.out.println("async task");
            return "completable future with default threadPoolExecutor";
        });

        try {
            System.out.println(completableFuture1.get());
        } catch (Exception e) {
            //
        }


        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        // if we want control over threadPoolExecutor that is minimum and max number of threads in threadPool then create a threadPoolExecutor and pass in CompletableFuture.supplyAsync()
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            // this is the task that is executed by the thread in poolExecutor.
            System.out.println("async task running");
            return "async task";
        }, poolExecutor);

        try {
            System.out.println(completableFuture.get());
        } catch (Exception e) {
            //
        }
        poolExecutor.shutdown();
    }

}
