package Callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallableAndRunnableExample {
        public static void main(String[] args) {

            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3 ,3, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

            // below calls submit(Runnable) : as nothing is returned
            // ? is wildCard, it can be anything, so the return type is caught into 'object' of Object type, as Object is parent of all classes
            Future<?> futureObj1 = threadPoolExecutor.submit(()-> {
                System.out.println("task 1 with runnable");
            });

            try {
                // ? is wildCard, it can be anything, so the return type is caught into 'object' of Object type.
                Object object = futureObj1.get();
                System.out.println(object); // returns null
                System.out.println(object == null); // true
            } catch (Exception e) {
                //
            }

            List<Integer> al = new ArrayList<>();
            // below calls : V submit(Runnable) with returnType
            Future<List<Integer>> futureObj2 = threadPoolExecutor.submit(()->{
                al.add(455);
                System.out.println("task 2 runnable with return object");
            },al);

            try {
                List<Integer> list = futureObj2.get();
                System.out.println("list :"+list);
            } catch (Exception e) {
                //
            }


            // below calls submit(Callable<T>) : as we are returning something
            Future<List<Integer>> futureObj3 = threadPoolExecutor.submit(()->{
                al.add(456);
                System.out.println("task 3 callable");
                return al;
            });

            try {
                List<Integer> list = futureObj3.get();
                System.out.println("list :"+list);
            } catch (Exception e) {
                //
            }


        }
}
