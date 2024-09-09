package OddEven;

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
public class usingThread {
    static boolean sharedVar = true;
    static ReentrantLock reentrantLock = new ReentrantLock();
    static Condition odd = reentrantLock.newCondition();
    static Condition even = reentrantLock.newCondition();
    static int x = 1;

    public static void main(String[] args) {
        for (int i = 0 ; i<20 ;i++) {
            Thread t1 = new Thread(() -> {
                printOdd();
            });

            Thread t2 = new Thread(() -> {
                printEven();
            });
            t1.start();
            t2.start();
        }

    }

    public static void printOdd() {
        reentrantLock.lock(); // put all logic inside lock --> will be easier to manage the shared variable
        while (!sharedVar) {
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
        odd.signal();
        reentrantLock.unlock(); // before unlock call signal

    }

    public static void printEven() {
        reentrantLock.lock();
        while (sharedVar) {
            try {
                even.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        sharedVar = true;
        if (x % 2 == 0) {
            System.out.println(x++);
        }
        even.signal(); // before unlock call signal
        reentrantLock.unlock();
    }
}
