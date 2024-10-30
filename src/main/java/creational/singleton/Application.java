package creational.singleton;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    private static final int POOL_SIZE = 20;
    public static void main(String[] args) {
        try(ExecutorService threadPool = Executors.newVirtualThreadPerTaskExecutor()) {
            CountDownLatch countDownLatch = new CountDownLatch(POOL_SIZE);
            for(int i = 0; i < POOL_SIZE; i++) {
                int finalI = i;
                threadPool.submit(() -> {
                    IMDatabase.getInstance().set(String.valueOf(finalI), "value of key: " + finalI);
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await();
            threadPool.submit(() -> {
               for(int i = 0; i < POOL_SIZE / 2; i++) {
                   System.out.println(IMDatabase.getInstance().get(String.valueOf(i)));
               }
            });
            threadPool.submit(() -> {
                for(int i = POOL_SIZE / 2; i < POOL_SIZE; i++) {
                    System.out.println(IMDatabase.getInstance().get(String.valueOf(i)));
                }
            });
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

}
