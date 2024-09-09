package OddEven;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
*
* - Add the required number of threads in newFixedThreadPool()
- Add the odd function and even function as runnable task, ran using submit(RunnableTask) of threadPool.
- Take Reentrant Lock - will be easier to manage
- As with Reentrant Lock, inter thread communication happens via condition.await() , Condition.signal()
	○ Take 2 signal objects : one for odd thread, one for even thread
Put the whole logic of the function inside lock, before unlocking, call signal()
* */

// this class helps with maintaining order of printing odd even Numbers.
public class usingOddEvenExecutor {
    private boolean sharedVariable = true;

    public static void main(String[] args) {
        usingOddEvenExecutor oddEvenExecutor = new usingOddEvenExecutor();
        oddEvenExecutor.printOddEven();
    }

    public void printOddEven() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        ReentrantLock reentrantLock = new ReentrantLock();
        Condition oddCondition = reentrantLock.newCondition(); // have to use Condition.await(), Condition.Signal() --> wait() and notify() won't work.
        Condition evenCondition = reentrantLock.newCondition();
        Runnable oddTask = () -> {
            for (int i = 1; i <= 19; i += 2) {
                reentrantLock.lock();
                while (!sharedVariable) {
                    try {
                        oddCondition.await();
                    } catch (InterruptedException e) {
                        //  Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Odd: " + i);
                sharedVariable = false; // Now it's even's turn
                evenCondition.signal();
                reentrantLock.unlock();

            }
        };

        Runnable evenTask = () -> {
            for (int i = 2; i <= 20; i += 2) {
                reentrantLock.lock();
                while (sharedVariable) {
                    try {
                        evenCondition.await();
                    } catch (InterruptedException e) {
                        // Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Even: " + i);
                sharedVariable = true; // Now it's odd's turn
                oddCondition.signal();
                reentrantLock.unlock();

            }
        };

        executorService.submit(oddTask);
        executorService.submit(evenTask);

        executorService.shutdown();
    }
}
