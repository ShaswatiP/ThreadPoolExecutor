import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        /* executor with minimumThread/corePoolSize 2, maxThreads 4, keepAliveTime 1 minute, Queue with size 2 and a customThreadFactory that creates threads those aren't daemon and have normal Priority
          here the rejection policy for tasks those can't be accepted is our own defined customRejectionPolicy2, where simply rejected tasks are logged, no Exception is thrown*/
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,4,1,
                TimeUnit.MINUTES,new ArrayBlockingQueue<>(2), new customThreadFactory(), new customRejectionPolicy());

       /*        in below case, the ThreadPoolExecutor uses DefaultThreadFactory present in Executor.
                     ThreadPoolExecutor executor = new ThreadPoolExecutor(2,4,1, TimeUnit.MINUTES,new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory());


         below implementation is same as above except here the rejection policy for tasks those can't be accepted is DiscardPolicy, tasks are silently discarded, no Exception is thrown
                ThreadPoolExecutor executor = new ThreadPoolExecutor(2,4,1,TimeUnit.MINUTES,new ArrayBlockingQueue<>(2), new customThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
        */



        // here we have defined 4 tasks, those are submitted through submit(Runnable r) method
        for(int i=0;i<4;i++){
            // here task inside submit() is simply a Runnable task, here we are simply printing a line that is the task and task sleeps for 5 second
            executor.submit(()->{
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("task is processed by :"+Thread.currentThread().getName());
            });
        }

        executor.shutdown();
        /*
        *
        * Output of above:
        * Hello world!
            task is processed by :Thread-1
            task is processed by :Thread-0
            task is processed by :Thread-1
            task is processed by :Thread-0
        *
        *
        * here corePoolSize = 2, so at one moment 2 threads are running,
        *  when task3 and 4 comes in they are put in the Queue of size 2,
        * once thread 1 is free it picks up task3 and thread 0 picks task 4
        */
    }
}

class customThreadFactory implements ThreadFactory{

    @Override
    public Thread newThread(Runnable r) {
        Thread th = new Thread(r);
        th.setPriority(Thread.NORM_PRIORITY);
        th.setDaemon(false);
        return th;
    }
}

class customRejectionPolicy implements RejectedExecutionHandler{

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("task :"+r.toString()+" is rejected.");
    }
}