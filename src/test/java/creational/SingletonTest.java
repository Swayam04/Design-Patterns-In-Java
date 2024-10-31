package creational;

import creational.singleton.IMDatabase;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class SingletonTest {

    @Test
    public void testSingleInstance() {
        IMDatabase store1 = IMDatabase.getInstance();
        IMDatabase store2 = IMDatabase.getInstance();
        assertSame(store1, store2, "Multiple getInstance() calls should return the same object");
    }

    @Test
    public void testConcurrentAccess() {
        final int THREAD_COUNT = 100;
        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            Set<IMDatabase> instances = Collections.synchronizedSet(new HashSet<>());
            CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

            for (int i = 0; i < THREAD_COUNT; i++) {
                executorService.submit(() -> {
                   try {
                       Thread.sleep(new Random().nextInt(100));
                       instances.add(IMDatabase.getInstance());
                   } catch (InterruptedException e) {
                       Thread.currentThread().interrupt();
                   } finally {
                       latch.countDown();
                   }
                });
            }

            latch.await();
            executorService.shutdown();
            assertSame(1, instances.size(), "All threads should get the same instance");
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testSingletonStateConsistency() throws InterruptedException {
        IMDatabase store = IMDatabase.getInstance();
        final int THREAD_COUNT = 10;
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadNum = i;
            executorService.submit(() -> {
                try {
                    IMDatabase store1 = IMDatabase.getInstance();
                    store1.set(String.valueOf(threadNum), "Thread: " + threadNum);
                    if (threadNum % 2 == 0) {
                        store1.set(String.valueOf(threadNum), store1.get(String.valueOf(threadNum)) + " is even numbered.");
                    } else {
                        store1.set(String.valueOf(threadNum), store1.get(String.valueOf(threadNum)) + " is odd numbered.");
                    }
                    Thread.sleep(10);
                    assertSame(store1, store);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        for (int i = 0; i < THREAD_COUNT; i++) {
            String value = store.get(String.valueOf(i));
            assertNotNull(value, "Value should not be null");
            if(i % 2 == 0) {
                assertTrue(value.contains("even"));
            } else {
                assertTrue(value.contains("odd"));
            }
        }
    }

}
