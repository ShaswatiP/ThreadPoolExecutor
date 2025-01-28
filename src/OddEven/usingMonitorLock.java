package OddEven;

public class usingMonitorLock {
    public static void main(String[] args){
        SharedResource sharedResource = new SharedResource(false);
        Thread t1 = new Thread(() -> {
            sharedResource.printOdd();
        });

        Thread t2 = new Thread(() -> {
             sharedResource.printEven();
        });

        t2.start();
        t1.start();
    }

}
class SharedResource{
    boolean current;
    SharedResource(boolean current){
        this.current = current;
    }
    public synchronized void printEven(){
        for(int i = 2; i<10; i = i+2){
            while (!current){
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(i);
            current = false;
            notifyAll();
        }
    }
    public synchronized void printOdd(){
        for(int i = 1; i<10; i = i+2){
            while (current){
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(i);
            current = true;
            notifyAll();
        }
    }
}
