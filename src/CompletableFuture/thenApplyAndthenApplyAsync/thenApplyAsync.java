package CompletableFuture.thenApplyAndthenApplyAsync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

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

        CompletableFuture<List<Integer>> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            List<Integer> al = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                if ((i & 1) != 0) {
                    al.add(i);
                }
            }
            return al;
            // from the list, filter the ones divisible by 3
        }, poolExecutor).thenApplyAsync((List<Integer> al) -> {
            return al.stream().filter(ele -> ele % 3 == 0).collect(Collectors.toList());
            // square each number in the list
        }).thenApplyAsync((List<Integer> al) -> {
            return al.stream().map(x -> x * x).collect(Collectors.toList());
        });

        try {
            System.out.println(completableFuture1.get());
        } catch (Exception e) {
            //
        }
        poolExecutor.shutdown();
    }
}
