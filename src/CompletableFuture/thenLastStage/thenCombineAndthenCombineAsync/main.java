package CompletableFuture.thenLastStage.thenCombineAndthenCombineAsync;

import java.util.concurrent.*;

public class main {

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->{
            return "I am studying";
        },poolExecutor);

        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(()->{
            return " java thread pools";
        },poolExecutor);

        CompletableFuture<String> combinedFuture = completableFuture.thenCombine(completableFuture1,(String s1,String s2) -> s1+s2);

        try {
            System.out.println(combinedFuture.get());
        }  catch (Exception e) {
           //
        }

    }
}