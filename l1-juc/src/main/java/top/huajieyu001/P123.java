package top.huajieyu001;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author huajieyu
 * @Date 2026/2/1 14:53
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p123")
public class P123 {

    public static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();

        log.debug("main thread locked");

        new Thread(() -> {
            log.debug("new thread start");
            if(!lock.tryLock()){
                log.debug("try lock failed");
                return;
            }
            try {
                if(!lock.tryLock(1000, TimeUnit.MILLISECONDS)){
                    log.debug("try lock failed");
                    return;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try{
                log.debug("lock acquired");
            } finally {
                lock.unlock();
            }
        }).start();


    }
}
