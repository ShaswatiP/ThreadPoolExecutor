package CompletableFuture.thenLastStage.thenCombineAndthenCombineAsync;

import java.util.concurrent.*;

public class main {

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()->{
            return "I am studying";
        },poolExecutor);

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(()->{
            return " java thread pools";
        },poolExecutor);

        // combine cf1 and cf2
        CompletableFuture<String> combinedFuture = cf1.thenCombine(cf2,(String s1, String s2) -> s1+s2);

        try {
            System.out.println(combinedFuture.get());
        }  catch (Exception e) {
           //
        }
        poolExecutor.shutdown();

    }
}