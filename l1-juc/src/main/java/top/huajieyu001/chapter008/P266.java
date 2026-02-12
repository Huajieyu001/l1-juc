package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author huajieyu
 * @Date 2026/2/12 14:54
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p266")
public class P266 {

    public static void main(String[] args) {
        test2();
    }

    public static void test1() {
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                log.debug("Running thread {}", Thread.currentThread().getName());
                try {
                    long timeout = 500;
                    Thread.sleep(timeout + new Random().nextInt(500));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                latch.countDown();
                log.debug("Finished thread {}", Thread.currentThread().getName());
            }).start();
        }

        try {
            latch.await();
            log.debug("Finished all threads");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test2() {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                log.debug("Running thread {}", Thread.currentThread().getName());
                try {
                    long timeout = 500;
                    Thread.sleep(timeout + new Random().nextInt(500));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                latch.countDown();
                log.debug("Finished thread {}", Thread.currentThread().getName());
            });
        }

        executorService.submit(() -> {
            try {
                latch.await();
                log.debug("Finished all threads");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
