package top.huajieyu001.chatper004;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author huajieyu
 * @Date 2026/1/31 12:53
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p108")
public class P108 {

    public static void main(String[] args) throws InterruptedException {
        test4();
    }

    public static void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("111");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("222");
            LockSupport.park();
            log.debug("333");
        });

        t1.start();

        TimeUnit.SECONDS.sleep(2);
        LockSupport.unpark(t1);
        log.debug("444");
    }

    public static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("111");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("222");
            LockSupport.park();
            log.debug("333");
        });

        t1.start();

        TimeUnit.SECONDS.sleep(1);
        LockSupport.unpark(t1);
        log.debug("444");
    }

    public static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("111");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("222");
            LockSupport.park();
            log.debug("333");
        });

        t1.start();

        TimeUnit.SECONDS.sleep(1);
        t1.interrupt();
        log.debug("444");
    }

    public static void test4() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("111");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("222");
            LockSupport.park();
            log.debug("333");
            LockSupport.park();
            log.debug("555");
        });

        t1.start();

        TimeUnit.SECONDS.sleep(1);
        LockSupport.unpark(t1);
        log.debug("444");
    }
}
