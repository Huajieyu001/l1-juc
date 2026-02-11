package top.huajieyu001.chapter008;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author huajieyu
 * @Date 2026/2/11 22:22
 * @Version 1.0
 * @Description TODO
 */
public class P236 {

    public static void main(String[] args) {
        MyLock lock = new MyLock();

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("第二次获取锁");
                lock.unlock();
                System.out.println(lock.isSelf());
                lock.lock();
                System.out.println(lock.isSelf());
            } finally {
                lock.unlock();
                System.out.println("解锁成功");
            }
        });

        Thread t2 = new Thread(() -> {
        });

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        t2.start();
    }
}

class MyLock implements Lock {

    class MySync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0, arg)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if(getExclusiveOwnerThread() == Thread.currentThread()) {
                setExclusiveOwnerThread(null);
                return compareAndSetState(arg, 0);
            } else {
                return false;
            }
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition() {
            return new ConditionObject();
        }
    }

    private MySync sync = new MySync();

    @Override
    public void lock() {
        if(!sync.tryAcquire(1)){
            throw new IllegalMonitorStateException();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        do {
            sync.tryAcquire(1);
            Thread.sleep(10);
        } while (!sync.isHeldExclusively());
    }

    @Override
    public boolean tryLock() {
        if(!sync.tryAcquire(1)){
            throw new IllegalMonitorStateException();
        }
        return sync.isHeldExclusively();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long startTime = System.nanoTime();
        long nanos = unit.toNanos(time);
        do {
            if (sync.tryAcquire(1)) {
                return true;
            }
            Thread.sleep(10);
        } while (System.nanoTime() - startTime <= nanos);
        return false;
    }

    @Override
    public void unlock() {
        if(!sync.tryRelease(1)){
            throw new IllegalMonitorStateException();
        }
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isSelf(){
        return sync.isHeldExclusively();
    }
}