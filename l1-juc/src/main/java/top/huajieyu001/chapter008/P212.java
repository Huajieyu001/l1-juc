package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author huajieyu
 * @Date 2026/2/11 18:41
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p212")
public class P212 {

    public static void main(String[] args) {
//        test1();
//        test1CustomThreadFactory();
//        test2();
//        test3();
        log.debug("------------------");
        test3FixedThreadPool();
    }

    public static void test1() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        for (int i = 1; i <= 3; i++) {
            int finalI = i;
            pool.execute(()->{
                log.debug("{}", finalI);
            });
        }
    }

    public static void test1CustomThreadFactory() {
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {

            private final AtomicInteger count = new AtomicInteger(0);

            private final String poolName = UUID.randomUUID().toString().substring(0, 8);

            @Override
            public Thread newThread(Runnable r) {
                String name = "pool - " + poolName + " - " + count.incrementAndGet();
                Thread t = new Thread(r, name);
                return t;
            }
        });
        for (int i = 1; i <= 3; i++) {
            int finalI = i;
            pool.execute(()->{
                log.debug("{}", finalI);
            });
        }
    }

    public static void test2() {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        new Thread(()->{
            try {
                log.debug("put1");
                queue.put(1);
                log.debug("end1");
                // -----------------
                log.debug("put2");
                queue.put(2);
                log.debug("end2");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(()->{
            try {
                log.debug("taking {}", 1);
                queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(()->{
            try {
                log.debug("taking {}", 2);
                queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static void test3() {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newSingleThreadExecutor();
        pool.execute(()->{
            log.debug("111{}", Thread.currentThread().getName());
            int i = 1 / 0;
        });
        pool.execute(()->{
            log.debug("222{}", Thread.currentThread().getName());
        });
        pool.execute(()->{
            log.debug("333{}", Thread.currentThread().getName());
        });

    }

    public static void test3FixedThreadPool() {
        // newFixedThreadPool(1)和newSingleThreadExecutor()的区别
        // Fixed可以把对象从ExecutorService转换成实现类ThreadPoolExecutor，然后调用实现类中的方法
        // Single不能把对象从ExecutorService转为实现类ThreadPoolExecutor，强转会抛异常ClassCastException
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        pool.execute(()->{
            log.debug("111{}", Thread.currentThread().getName());
            int i = 1 / 0;
        });
        pool.execute(()->{
            log.debug("222{}", Thread.currentThread().getName());
        });
        pool.execute(()->{
            log.debug("333{}", Thread.currentThread().getName());
        });
    }
}
