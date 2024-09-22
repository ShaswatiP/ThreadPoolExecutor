package OddEven;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class using2Threads {
    static boolean sharedVar = true;
    static ReentrantLock reentrantLock = new ReentrantLock();
    static Condition odd = reentrantLock.newCondition();
    static Condition even = reentrantLock.newCondition();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                printOdd();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                printEven();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();
        t2.start();
    }

    public static void printOdd() throws InterruptedException {
        for (int i = 1; i < 20; i = i + 2) {
            reentrantLock.lock();
            while (!sharedVar) {
                odd.await();
            }
            sharedVar = false;
            System.out.println("odd :" + i);
            even.signal();

            reentrantLock.unlock();

        }

    }

    public static void printEven() throws InterruptedException {
        for (int i = 2; i < 20; i = i + 2) {
            reentrantLock.lock();
            while (sharedVar) {
                even.await();
            }
            sharedVar = true;
            System.out.println("even :" + i);
            odd.signal();
            reentrantLock.unlock();


        }
    }

}
