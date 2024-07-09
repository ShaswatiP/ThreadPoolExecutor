package CompletableFuture.thenComposeAndthenComposeAsync;

import java.util.concurrent.*;

public class maincompose {
    public static void main(String[] args) {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "hello";
        }, poolExecutor);

        /* below async task is dependent on completableFuture object,
           once it is available, or previous task is completed then only below task is run - for which thenCompose() is used,
           thenCompose() uses same thread as previous  CompletableFuture.supplyAsync()
        */
        CompletableFuture<String> completableFuture1 = completableFuture.thenCompose((String s) -> {
            return CompletableFuture.supplyAsync(() -> s + " nitu");
        });
        // if we want the dependent async tasks to run after another in an order use thenCompose() - uses same thread as the supplyAsync() parent task
        // if we use thenComposeAsync() then we have to pass a threadPoolExecutor and the task runs on a separate thread

        CompletableFuture<String> completableFuture2 = completableFuture1.thenComposeAsync((String s) -> {
            return CompletableFuture.supplyAsync(() -> s + " bye");
        }, poolExecutor);

        try {
            System.out.println(completableFuture2.get());
        } catch (Exception e) {
            //
        }

    }
}
