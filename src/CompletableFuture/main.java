package CompletableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class main {
    public static void main(String[] args) {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


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
    }

}
