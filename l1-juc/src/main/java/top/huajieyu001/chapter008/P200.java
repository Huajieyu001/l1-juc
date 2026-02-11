package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author huajieyu
 * @Date 2026/2/11 14:40
 * @Version 1.0
 * @Description TODO
 */
public class P200 {

    public static void main(String[] args) {
        test3();
    }

    public static void test1() {
        BlockingQueue<String> bq = new BlockingQueue<>(5);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                bq.submit(UUID.randomUUID().toString());
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
                for (int i = 0; i < 30; i++) {
                    Thread.sleep(200);
                    bq.submit(UUID.randomUUID().toString());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(bq.take());
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(bq.size());
    }

    public static void test2() {
        BlockingQueue bq = new BlockingQueue<>(5);

        try {
            System.out.println(bq.poll(1000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test3() {
        BlockingQueue bq = new BlockingQueue<>(5);

        try {
            System.out.println(bq.pollV2(1000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

@Slf4j(topic = "c.bq")
class BlockingQueue<T> {

    private Deque<T> tasks = new ArrayDeque<>();

    private ReentrantLock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();

    private Condition notFull = lock.newCondition();

    private int maxSize;

    BlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void submit(T t) {
        lock.lock();
        try {
            while(tasks.size() == maxSize){
                notFull.await();
            }
            tasks.addFirst(t);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while(tasks.isEmpty()) {
                notEmpty.await();
            }
            T task = tasks.removeFirst();
            notFull.signalAll();
            return task;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 带超时的获取任务
     * @param timeout
     * @return
     * @throws InterruptedException
     */
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        lock.lock();
        try {
            long start = System.currentTimeMillis();
            log.debug("poll start");
            while(tasks.isEmpty()) {
                long elapsed = System.currentTimeMillis() - start;
                if(elapsed < timeout){
                    notEmpty.await(timeout - elapsed, unit);
                } else {
                    log.debug("poll timeout");
                    throw new RuntimeException("timeout");
                }
            }
            T task = tasks.removeFirst();
            notFull.signalAll();
            return task;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 带超时的获取任务
     * @param timeout
     * @return
     * @throws InterruptedException
     */
    public T pollV2(long timeout, TimeUnit unit) throws InterruptedException {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            log.debug("pollV2 start");
            while(tasks.isEmpty()) {
                if((nanos = notEmpty.awaitNanos(nanos)) <= 0){
                    log.debug("pollV2 timeout");
                    return null;
                }
            }
            T task = tasks.removeFirst();
            notFull.signalAll();
            return task;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try{
            return tasks.size();
        } finally {
            lock.unlock();
        }
    }
}