package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
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
        test5();
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

        System.out.println(bq.pollV2(1000, TimeUnit.MILLISECONDS));
    }

    /**
     * 模拟测试线程池正常情况，任务超过核心线程但不超过任务队列和核心线程的总数
     */
    public static void test4() {
        ThreadPool tp = new ThreadPool(2, 6, 1, TimeUnit.SECONDS, 10);

        try {
            for (int i = 0; i < 10; i++) {
                int finalI = i;
                tp.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "*****"  + finalI);
                });
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 模拟测试线程池异常情况，任务超过任务队列和核心线程的总数
     */
    public static void test5() {
        ThreadPool tp = new ThreadPool(2, 6, 1, TimeUnit.SECONDS, 10, ((queue, task) ->
                task.run()));
        try {
            for (int i = 0; i < 20; i++) {
                int finalI = i;
                tp.execute(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + "*****"  + finalI);
                });
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

@Slf4j()
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

    public void trySubmit(RejectPolicy rejectPolicy, T task) {
        lock.lock();
        try {
            if(tasks.size() == maxSize){
                rejectPolicy.reject(this, task);
            } else {
                this.submit(task);
                notEmpty.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean offer(T t, long timeout, TimeUnit unit){
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while(tasks.size() == maxSize && nanos > 0){
                nanos = notFull.awaitNanos(nanos);
            }
            if(nanos < 0){
                return false;
            }
            tasks.addFirst(t);
            notEmpty.signalAll();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return false;
    }

    public T take() {
        lock.lock();
        try {
            while(tasks.isEmpty()) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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
    public T pollV2(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            log.debug("pollV2 start");
            while(tasks.isEmpty()) {
                try {
                    if((nanos = notEmpty.awaitNanos(nanos)) <= 0){
                        log.debug("pollV2 timeout");
                        return null;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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


@Slf4j(topic = "c.tp")
class ThreadPool {

    private HashSet<Worker> workers = new HashSet<>();

    private int coolSize;

    private int maxSize;

    private long timeout;

    private TimeUnit unit;

    private BlockingQueue<Runnable> taskQueue;

    private boolean allowReleaseCoreThread;

    private RejectPolicy<Runnable> rejectPolicy;

    ThreadPool(int coolSize, int maxSize, long timeout, TimeUnit unit, int queueSize) {
        this(coolSize, maxSize, timeout, unit, queueSize, new AbortPolicy<>());
    }

    ThreadPool(int coolSize, int maxSize, long timeout, TimeUnit unit, int queueSize, RejectPolicy<Runnable> rejectPolicy) {
        this.coolSize = coolSize;
        this.maxSize = maxSize;
        this.timeout = timeout;
        this.unit = unit;
        taskQueue = new BlockingQueue<>(queueSize);
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task) throws InterruptedException {
        synchronized (workers) {
            if(workers.size() < coolSize){
                log.debug("create worker, worker is [{}], task is [{}]", workers, task);
                Worker worker = new Worker(task);
                workers.add(worker);
                worker.start();
            } else {
                log.debug("worker already full, submit task to task queue, task is [{}]", task);
                // taskQueue.submit(task);
                // 把上面的直接提交任务改为通过拒绝策略灵=灵活提交任务
                taskQueue.trySubmit(rejectPolicy, task);
            }
        }
    }

    public void allowReleaseCoreThread() {
        allowReleaseCoreThread = true;
    }

    private class Worker extends Thread {

        private Runnable task;

        Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            // 方式1：死等，会让整个主线程一直等待着
            // while(task != null || (task = taskQueue.take()) != null) {
            // 方式2：超时的话就结束，主线程也能够被释放
            while(task != null || (task = taskQueue.pollV2(timeout, unit)) != null) {
                log.debug("Running, task is [{}]", task);
                try {
                    task.run();
                } catch (RejectedExecutionException e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                log.debug("worker [{}] is free", this);
                workers.remove(this);
            }
        }
    }
}

@FunctionalInterface
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}

/**
 * 直接抛弃策略
 * @param <T>
 */
class AbortPolicy<T> implements RejectPolicy<T> {
    @Override
    public void reject(BlockingQueue<T> queue, T task) {
        System.out.println("AbortPolicy reject task : [" + task + "]");
    }
}

class ThrowingExceptionPolicy<T> implements RejectPolicy<T> {
    @Override
    public void reject(BlockingQueue<T> queue, T task) {
        throw new RuntimeException("task is aborted");
    }
}

/**
 * 死等策略
 * @param <T>
 */
class WaitingPolicy<T> implements RejectPolicy<T> {
    @Override
    public void reject(BlockingQueue<T> queue, T task) {
        queue.submit(task);
    }
}

/**
 * 超时等待策略
 * @param <T>
 */
class WaitTimeoutPolicy<T> implements RejectPolicy<T> {
    @Override
    public void reject(BlockingQueue<T> queue, T task) {
        queue.pollV2(1000, TimeUnit.MILLISECONDS);
    }
}

class CallerRunPolicy<T> implements RejectPolicy<T> {
    @Override
    public void reject(BlockingQueue<T> queue, T task) {
        if(task instanceof Runnable) {
            Runnable r = (Runnable)task;
            r.run();
        }
    }
}

