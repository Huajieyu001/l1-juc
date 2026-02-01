package top.huajieyu001.chatper004;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

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
        test3();
    }

    /**
     * 用Thread.join控制执行顺序
     */
    public static void test1() {
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

    /**
     * 用wait notify控制执行顺序
     */
    public static void test2() {
        P128 t = new P128();

        new Thread(() -> {
            synchronized (t) {
                while (!flag) {
                    try {
                        t.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (flag) {
                        log.debug("A");
                        break;
                    }
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (t) {
                log.debug("B");
                flag = true;
                t.notify();
            }
        }).start();
    }

    /**
     * 用park，unpark控制执行顺序
     */
    public static void test3() {
        P128 t = new P128();

        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("A");
        });

        Thread t2 = new Thread(() -> {
            LockSupport.park();
            log.debug("B");
            LockSupport.unpark(t1);
        });

        Thread t3 = new Thread(() -> {
            log.debug("C");
            LockSupport.unpark(t2);
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
