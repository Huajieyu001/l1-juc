package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @Author huajieyu
 * @Date 2026/2/11 20:56
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p224")
public class P224 {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
        test6();
    }

    public static void test1() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task1");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task1");
            }
        };

        log.debug("start");
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }

    public static void test2() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task1");
                try {
                    Thread.sleep(2000);
                    int i = 1 / 0;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task1");
            }
        };

        log.debug("start");
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }

    public static void test3() {
        ScheduledThreadPoolExecutor pool = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> result1 = pool.schedule(() -> {
            log.debug("task1");
            try {
                Thread.sleep(2000);
                int i = 1 / 0;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> result2 = pool.schedule(()->{
            log.debug("task2");
        }, 1, TimeUnit.SECONDS);
        log.debug("start");

        try {
            log.debug("result2 = {}", result2.get());
            log.debug("result1 = {}", result1.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test4() {
        ScheduledThreadPoolExecutor pool = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(()->{
            log.debug("running");
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public static void test5() {
        ScheduledThreadPoolExecutor pool = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(()->{
            log.debug("running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public static void test6() {
        ScheduledThreadPoolExecutor pool = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        pool.scheduleWithFixedDelay(()->{
            log.debug("running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }
}
