package top.huajieyu001.chatper004;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author huajieyu
 * @Date 2026/2/1 14:39
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p122")
public class P122 {

    public static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.debug("Interrupted");
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        lock.lock();
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            t1.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
