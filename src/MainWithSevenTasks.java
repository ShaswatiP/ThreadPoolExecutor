import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainWithSevenTasks {
    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 1,
                TimeUnit.MINUTES, new ArrayBlockingQueue<>(2), new customThreadFactory(), new customRejectionPolicy());

        for (int i = 0; i < 7; i++) {
            executor.submit(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("task is processed by :" + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
        /*
        *
        * Output of above:
           task :java.util.concurrent.FutureTask@682a0b20 is rejected.
            task is processed by :Thread-1
            task is processed by :Thread-2
            task is processed by :Thread-3
            task is processed by :Thread-0
            task is processed by :Thread-2
            task is processed by :Thread-1
        *
        *
        * here corePoolSize = 2,  Queue size = 2 and maxPoolSize = 4
            with 7 tasks and the queue limit and maxPoollimit is reached, here max 6 tasks can be submitted
            * hence 1 task is rejected at submit only.
           *
           if we increase 'i' limit to 9 is for loop, that is if we want to submit 9 tasks,
           then 3 are rejected and 6 are processed.
           Output:
           task :java.util.concurrent.FutureTask@682a0b20 is rejected.
            task :java.util.concurrent.FutureTask@3d075dc0 is rejected.
            task :java.util.concurrent.FutureTask@214c265e is rejected.
            task is processed by :Thread-0
            task is processed by :Thread-1
            task is processed by :Thread-3
            task is processed by :Thread-2
            task is processed by :Thread-1
            task is processed by :Thread-3


           *
           *
           *
           *
           * /
         */

    }
}