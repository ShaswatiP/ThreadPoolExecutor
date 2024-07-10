import java.util.concurrent.*;

public class mainExample {
    public static void main(String[] args){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        // below calls schedule(Callable<V>,delay,TimeUnit), it is a Callable task as a return statement is there
        // Callable task return String, here type of Future object is String
        Future<String> future = scheduledThreadPool.schedule(()->{
            return "hello";
        },4, TimeUnit.SECONDS);

        try {
            System.out.println(future.get());
        } catch (Exception e) {
            //
        }
        System.out.println("main thread is completed");
    }
}
