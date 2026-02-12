package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Author huajieyu
 * @Date 2026/2/11 19:54
 * @Version 1.0
 * @Description TODO
 */

@Slf4j(topic = "c.p219")
public class P219 {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    public static void test1() {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        Future<Integer> f1 = pool.submit(() -> {
            log.debug("begin1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("end1");
            return 1;
        });

        Future<Integer> f2 = pool.submit(() -> {
            log.debug("begin2");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("end2");
            return 2;
        });

        Future<Integer> f3 = pool.submit(() -> {
            log.debug("begin3");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("end3");
            return 3;
        });

        // shutdown之前正在执行的任务和队列中还未执行的任务都会正常执行完
        log.debug("begin shutdown");
        pool.shutdown();
        log.debug("end shutdown");

        // shutdown之后提交新任务会触发异常RejectedExecutionException
//        Future<Integer> f4 = pool.submit(() -> {
//            log.debug("begin4");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            log.debug("end4");
//            return 4;
//        });

        try {
            // shutdown之前正在执行的任务和队列中还未执行的任务都会正常执行完
            System.out.println(f1.get());
            System.out.println(f2.get());
            System.out.println(f3.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test2() {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        Future<Integer> f1 = pool.submit(() -> {
            log.debug("begin1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("end1");
            return 1;
        });

        Future<Integer> f2 = pool.submit(() -> {
            log.debug("begin2");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("end2");
            return 2;
        });

        Future<Integer> f3 = pool.submit(() -> {
            log.debug("begin3");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("end3");
            return 3;
        });

        // shutdown之前正在执行的任务和队列中还未执行的任务都会正常执行完
        log.debug("begin shutdown");
        pool.shutdown();
        try {
            pool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("end shutdown");

        try {
            System.out.println(f1.get());
            System.out.println(f2.get());
            System.out.println(f3.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test3() {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        Future<Integer> f1 = pool.submit(() -> {
            log.debug("begin1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("end1");
            return 1;
        });

        Future<Integer> f2 = pool.submit(() -> {
            log.debug("begin2");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("end2");
            return 2;
        });

        Future<Integer> f3 = pool.submit(() -> {
            log.debug("begin3");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("end3");
            return 3;
        });

        // shutdown之前正在执行的任务和队列中还未执行的任务都会正常执行完
        log.debug("begin shutdown");
        List<Runnable> list = pool.shutdownNow();
        log.debug("end shutdown");

        list.forEach(System.out::println);
    }
}
