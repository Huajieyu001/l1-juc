package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author huajieyu
 * @Date 2026/2/12 15:27
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p271")
public class P271 {

    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(2, ()->{
            log.debug("任务结束");
        });
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Random random = new Random();

        for (int j = 0; j < 3; j++) {
            executorService.submit(() -> {
                log.debug("starting {}", Thread.currentThread().getName());
                try {
                    Thread.sleep(random.nextInt(100));
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
                log.debug("ending {}", Thread.currentThread().getName());
            });

            executorService.submit(() -> {
                log.debug("starting {}", Thread.currentThread().getName());
                try {
                    Thread.sleep(random.nextInt(100));
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
                log.debug("ending {}", Thread.currentThread().getName());
            });
        }
    }
}
