import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) {

        System.out.println("\n=== Thread Creation Example ===");

        Thread runnableThread = new Thread(new ClassImplRunnable());
        runnableThread.start();


        ClassExtendsThread threadClass = new ClassExtendsThread();
        threadClass.start();


        try {
            runnableThread.join();
            threadClass.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Both threads have completed execution.");

// --------------------------------------------------------------------------------------------------

        System.out.println("\n=== Thread Synchronization Example ===");

        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Counter Value: " + counter.getCount());

// --------------------------------------------------------------------------------------------------

        System.out.println("\n=== Thread Pool Counter Example ===");


        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }
            });
        }

        executorService.shutdown();

        try {

            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Not all tasks completed within the timeout. Forcing shutdown...");
                executorService.shutdownNow();
            } else {
                System.out.println("All tasks completed.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executorService.shutdownNow();
        }

        System.out.println("Final Counter Value: " + counter.getCount());
    }
}