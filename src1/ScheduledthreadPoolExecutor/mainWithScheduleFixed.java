import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class mainWithScheduleFixed {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

        /*
        after initialDelay of 2 seconds, print "hello" in each 4 seconds of interval.
        * */
        Future<?> futureObj = scheduledThreadPool.scheduleAtFixedRate(()->{
            System.out.println("hello");
        },2, 4,TimeUnit.SECONDS);


        try {
            Thread.sleep(10000);
            //cancel the scheduled futureObj even if it is running else it keeps running every 4 seconds.
            futureObj.cancel(true);
            System.out.println(futureObj.isCancelled());
        } catch (Exception e) {
            //
        }
        scheduledThreadPool.shutdown();

    }

}
