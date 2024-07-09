package CompletableFuture.thenApplyAndthenApplyAsync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class thenApplyNoChaininginSynatx {
    public static void main(String[] args) {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        learnThenApplyNoChainingInSyntax(poolExecutor);

    }

    // below does the same task as learnThenApply(). here the syntax is only different.
    private static void learnThenApplyNoChainingInSyntax(ThreadPoolExecutor poolExecutor) {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            String first = "first Nilu da";
            System.out.println("async task thread:" + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                //
            }
            return first;
        }, poolExecutor);

        CompletableFuture<String> completableFuture1 = completableFuture.thenApply((String result) ->
        {
            System.out.println("thenApply task thread:" + Thread.currentThread().getName()); // same thread as above
            return result + " then nitu";
        });


        try {
            System.out.println(completableFuture1.get());
        } catch (Exception e) {
            //
        }


        CompletableFuture<List<Integer>> completableFuture11 = CompletableFuture.supplyAsync(() -> {
            List<Integer> al = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                if ((i & 1) != 0) {
                    al.add(i);
                }
            }
            return al;
        }, poolExecutor);

        CompletableFuture<List<Integer>> completableFuture12 = completableFuture11.thenApply((List<Integer> al) -> {
            return al.stream().filter(ele -> ele % 3 == 0).collect(Collectors.toList());

        });

        CompletableFuture<List<Integer>> completableFuture13 = completableFuture12.thenApply((List<Integer> al) -> {
            return al.stream().map(x -> x * x).collect(Collectors.toList());
        });

        try {
            System.out.println(completableFuture13.get());
        } catch (Exception e) {
            //
        }

    }
}
