package CompletableFuture.thenApplyAndthenApplyAsync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class thenApply {

    public static void main(String[] args) {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        learnThenApply(poolExecutor);

    }

    private static void learnThenApply(ThreadPoolExecutor poolExecutor) {

        /* thenApply() : Applies a function to the result of previous Async computation.
           Returns a new CompletableFuture object.
           thenApply() has only 1 parameter as input that it gets as output of parent async task,
           it does some operation on the input and returns it

           here the same thread that does the task in supplyAsync(), executes the thenApply(), no separate thread is created.

            below are 2 examples of chaining with the help of supplyAsync() along with thenApply():
          */


        // here the async task returns a string "first Nilu da" to another task, which concats with original string, 2nd task is done in thenApply()
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                            String first = "first Nilu da";
                            return first;
                        },
                        poolExecutor).
                thenApply((String result) -> {
                    String s = result + " then nitu";
                    return s;
                });

        try {
            System.out.println(completableFuture.get()); // output: first Nilu da then nitu
        } catch (Exception e) {
            //
        }

        // here in async task -> only odd numbers from 0 to 20 are added in the list
        CompletableFuture<List<Integer>> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            List<Integer> al = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                if ((i & 1) != 0) {
                    al.add(i);
                }
            }
            return al;
            // from the list, filter the ones divisible by 3
        }, poolExecutor).thenApply((List<Integer> al) -> {
            return al.stream().filter(ele -> ele % 3 == 0).collect(Collectors.toList());
            // square each number in the list
        }).thenApply((List<Integer> al) -> {
            return al.stream().map(x -> x * x).collect(Collectors.toList());
        });

        try {
            System.out.println(completableFuture1.get());
        } catch (Exception e) {
            //
        }
    }


}
