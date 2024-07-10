import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main(String[] args){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        // below calls schedule(Runnable,delay,TimeUnit), it is a Runnable task as no return type is there
        scheduledThreadPool.schedule(()->{
            System.out.println("hello");
        },4, TimeUnit.SECONDS);

        System.out.println("main thread is completed");
    }
}
