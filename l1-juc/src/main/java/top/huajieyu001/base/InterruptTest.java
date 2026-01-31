package top.huajieyu001.base;

import java.util.concurrent.TimeUnit;

/**
 * @Author huajieyu
 * @Date 2026/1/29 15:58
 * @Version 1.0
 * @Description TODO
 */
public class InterruptTest {

    public static void main(String[] args) {
        testInterrupt();
    }

    public static void testInterrupt() {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {

            }
        });

        thread.start();

        try {
            System.out.println(thread.isInterrupted());
            TimeUnit.SECONDS.sleep(1);
            thread.interrupt();
            System.out.println(thread.isInterrupted());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
