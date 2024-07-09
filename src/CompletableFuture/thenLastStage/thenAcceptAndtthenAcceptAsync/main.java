package CompletableFuture.thenLastStage.thenAcceptAndtthenAcceptAsync;

import java.util.concurrent.*;

public class main {

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        // thenAccept() and thenAcceptAsync() is generally used at the end of the chain after supplyAsync(), thenApply() or thenApplyAsync() is done.
        //  it has no return type

        CompletableFuture<String> finalCompletable = CompletableFuture.supplyAsync(()->{
            return "hey hey";
        },poolExecutor).thenApply((String s) ->{
            return s+" new ";
        }).thenCompose((String s)->{
            return CompletableFuture.supplyAsync(() -> {return s+ " year ";},poolExecutor);
        });

        finalCompletable.thenAccept((String res) ->{
            System.out.println(res+"\ndone.");
        });

        try {
            finalCompletable.get();
        } catch (Exception e) {
           //
        }
    }

    }
