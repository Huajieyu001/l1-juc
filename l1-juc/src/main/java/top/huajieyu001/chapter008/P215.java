package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author huajieyu
 * @Date 2026/2/11 19:21
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p215")
public class P215 {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
        test5();
    }

    public static void test1() {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        Future<Integer> f1 = pool.submit(() -> {
            Thread.sleep(1000);
            return 100;
        });

        try {
            log.debug("{}", f1.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        Future<Integer> f2 = pool.submit(() -> {
            Thread.sleep(1000);
            return 200 / 0;
        });

        try {
            log.debug("{}", f2.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public static void test2() {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        pool.execute(() -> {
            log.debug("{}", Thread.currentThread().getName());
        });
        pool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("{}", Thread.currentThread().getName());
        });
        pool.shutdown();
    }

    public static void test3() {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        pool.execute(() -> {
            log.debug("{}", Thread.currentThread().getName());
        });
        pool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("{}", Thread.currentThread().getName());
        });
        pool.shutdownNow();
    }

    public static void test4() {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        Collection<Callable<Integer>> tasks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            tasks.add(()->finalI);
        }

        try {
            List<Future<Integer>> futures = pool.invokeAll(tasks);
            for (Future<Integer> future : futures) {
                log.debug("{}", future.get());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        pool.shutdown();
    }

    public static void test5() {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        Collection<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            tasks.add(()->finalI);
        }

        try {
            Integer i = pool.invokeAny(tasks);
            log.debug("{}", i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        pool.shutdown();
    }


}
