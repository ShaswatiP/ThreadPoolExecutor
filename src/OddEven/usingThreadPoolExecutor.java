package OddEven;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;



/*
*
*
* 	- Take Reentrant Lock - will be easier to manage
	- As with Reentrant Lock, inter thread communication happens via condition.await() , Condition.signal()
		○ Take 2 signal objects : one for odd thread, one for even thread
Put the whole logic of the function inside lock, before unlocking, call signal()
*
*
* */
public class usingThreadPoolExecutor {
    static boolean sharedVar = true;
    static ReentrantLock reentrantLock = new ReentrantLock();
    static Condition odd = reentrantLock.newCondition();
    static Condition even = reentrantLock.newCondition();
    static int x = 1;

    public static void main(String[] args) {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2,4,1000,
                TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(10));
        // can support upto 14 tasks only. more than 14 will give exception.
        for(int i = 0 ; i < 14 ; i++){
            poolExecutor.submit(()-> {
                printOdd();
                printEven();
            });
        }

        poolExecutor.shutdown();
    }

    public static void printOdd() {
        reentrantLock.lock(); // put all logic inside lock --> will be easier to manage the shared variable
        while (!sharedVar) { // sharedVar - false, it's not oddTask's turn, go into waiting state.
            try {
                odd.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        sharedVar = false;
        if (x % 2 != 0) {
            System.out.println(x++);
        }
        even.signal(); // oddTask is done, signal even task.
        reentrantLock.unlock(); // before unlock call signal

    }

    public static void printEven() {
        reentrantLock.lock();
        while (sharedVar) {
            try {
                even.await(); //  sharedVar - true, it's not evenTask's turn, go into waiting state.
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        sharedVar = true;
        if (x % 2 == 0) {
            System.out.println(x++);
        }
        odd.signal(); // evenTask is done, signal odd task.before unlock call signal
        reentrantLock.unlock();
    }
}
