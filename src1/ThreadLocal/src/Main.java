public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        threadLocal.set(Thread.currentThread().getName());

        Thread thread = new Thread(()->{
            System.out.println("thread working");
            // can re-use the same threadLocal variable.
            threadLocal.set(Thread.currentThread().getName());
            System.out.println("thread is :"+threadLocal.get());
        });

        System.out.println("two :"+threadLocal.get());
        thread.start();
        System.out.println("three :"+threadLocal.get());
    }
}