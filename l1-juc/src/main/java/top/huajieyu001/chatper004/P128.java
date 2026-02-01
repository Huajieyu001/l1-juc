package top.huajieyu001.chatper004;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author huajieyu
 * @Date 2026/2/1 15:56
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p128")
public class P128 {

    private final Object lock = new Object();

    static boolean flag = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("A");
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("B");
        });

        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("C");
        });

        t3.start();
        t2.start();
        t1.start();
    }
}
