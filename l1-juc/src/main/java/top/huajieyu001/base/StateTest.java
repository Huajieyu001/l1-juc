package top.huajieyu001.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author huajieyu
 * @Date 2026/1/29 14:47
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.stateTest")
public class StateTest {

    public static void main(String[] args) {
        testYield();
    }

    public static void testTimedWaiting(){
        Thread t1 = new Thread(() -> {
            try {
                log.debug("t1 running");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("t1 running");
        log.debug(t1.getState().toString());
        t1.start();
        log.debug(t1.getState().toString());
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(100);
                log.debug(t1.getState().toString());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void testTimedWaitingInterrupt(){
        Thread t1 = new Thread(() -> {
            try {
                log.debug("t1 running");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("t1 running");
        log.debug(t1.getState().toString());
        t1.start();
        log.debug(t1.getState().toString());
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(100);
                log.debug(t1.getState().toString());
            }
            t1.interrupt();
            log.debug(t1.getState().toString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void testWaiting(){
        Object o = new Object();
        Thread t1 = new Thread(() -> {
            try {
                log.debug("t1 running");
                o.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("t1 running");
        log.debug(t1.getState().toString());
        t1.start();
        log.debug(t1.getState().toString());
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(100);
                log.debug(t1.getState().toString());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void testTimeUnit() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
    }

    public static void testYield() {
        Thread t1 = new Thread(() -> {
            try {
                log.debug("t1 running");
                Thread.sleep(2000);
                log.debug(Thread.currentThread().getState().toString());
                Thread.sleep(1000);
                Thread.yield();
                Thread.sleep(1000);
                log.debug(Thread.currentThread().getState().toString());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

//        try{
            t1.start();
//            log.debug(t1.getState().toString());
//            TimeUnit.SECONDS.sleep(1);
//            log.debug(t1.getState().toString());
//            TimeUnit.SECONDS.sleep(1);
//            TimeUnit.SECONDS.sleep(1);
//            log.debug(t1.getState().toString());
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

    }
}
