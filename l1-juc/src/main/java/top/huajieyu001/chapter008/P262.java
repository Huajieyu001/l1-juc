package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @Author huajieyu
 * @Date 2026/2/12 13:36
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p262")
public class P262 {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        // 用来控制同一时刻访问某个资源的最大线程数，类似于停车场的停车位
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    log.debug("Thread " + Thread.currentThread().getName() + " is running");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
