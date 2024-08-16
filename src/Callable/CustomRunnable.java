package Callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CustomRunnable {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


        List<Integer> ll = new ArrayList<>();
        //customRunnable passed and below calls : T submit(Runnable,T obj)
        // Future object here is List<Integer>, here T : is generic type
        // below submit() returns the same output that is passed
        Future<List<Integer>> futureObj1 = threadPoolExecutor.submit(new myRunnable(ll),ll);


        try {
            List<Integer> returnList = futureObj1.get();
            System.out.println(returnList);
            // ll is also has same values as return List
            System.out.println(ll);
        } catch (Exception e) {
            //
        }
        threadPoolExecutor.shutdown();
    }
}
class myRunnable implements Runnable{

    List<Integer> list;
    myRunnable(List<Integer> list){
        this.list = list;
    }
    @Override
    public void run() {
        for (int i=0;i<10;i++) {
            if((i&1) == 1){
            list.add(i);
            }
        }
    }
}
