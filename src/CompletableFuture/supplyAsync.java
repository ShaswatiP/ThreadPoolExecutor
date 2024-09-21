package CompletableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class supplyAsync {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<List<Integer>> l = Arrays.asList(
                Arrays.asList(10,20,30,40,50,4,5,6,7),
                Arrays.asList(67,45,97,100),
                Arrays.asList(200,300,400));


        Future<Object> integerFuture = CompletableFuture.supplyAsync(()->{
            List<Integer> res =
                    l.stream().flatMap(all -> all.stream().filter(x -> (x%10 ==0))).collect(Collectors.toList());
            System.out.println("first :"+res);
            return res;
        }).thenApply(res -> {
            System.out.println("got res :"+res);
            List<Integer> sortedRes = res.stream().sorted().collect(Collectors.toList());
            return sortedRes;
        }).thenApply(ll ->{
            System.out.println("got sorted res :"+ll);
            Optional<Integer> result = ll.stream().filter(x -> (x%3 ==0)).findFirst();
            if (result.isPresent()){
                return result;
            }
            return -1;
        });

        System.out.println(integerFuture.get());

    }
}
