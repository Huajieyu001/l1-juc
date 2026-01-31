package top.huajieyu001.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author huajieyu
 * @Date 2026/1/29 16:29
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.lst")
public class LockSupportTest {

    public static void main(String[] args) {
        testUnParkException();
    }

    public static void testPark(){
        Thread t1 = new Thread(()->{
            log.debug("park");
            LockSupport.park(); // 这里会停下来，直到线程被interrupt，才会往下执行
            log.debug("unpark");
            log.debug("Status:{}", Thread.currentThread().isInterrupted());
            LockSupport.park(); // 这里不会停下来，因为被中断了一次，会直接往下执行
            log.debug("unpark");
            log.debug("Status:{}", Thread.currentThread().isInterrupted());
        });

        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            t1.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 异常现象：此方法就不会在第二次park阻塞，因为logback框架可能会异步调用了Thread.interrupted()
     */
    public static void testUnParkException(){
        Thread t1 = new Thread(()->{
            log.debug("park");
            LockSupport.park(); // 这里会停下来，直到线程被interrupt，才会往下执行
            log.debug("unpark111");
            log.debug("Status111:{}", Thread.currentThread().isInterrupted());
            Thread.interrupted();
            LockSupport.park(); // 这里会停下来，因为使用了Thread.interrupted()恢复状态，需要再次中断才能继续往下走
            log.debug("unpark222");
            log.debug("Status222:{}", Thread.currentThread().isInterrupted());
        });

        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            t1.interrupt();
            TimeUnit.SECONDS.sleep(2);
            t1.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void testUnPark(){
        Thread t1 = new Thread(()->{
            log.debug("park");
            LockSupport.park(); // 这里会停下来，直到线程被interrupt，才会往下执行
            log.debug("unpark111");
            log.debug("Status111:{}", Thread.interrupted());
            LockSupport.park(); // 这里会停下来，因为使用了Thread.interrupted()恢复状态，需要再次中断才能继续往下走
            log.debug("unpark222");
            log.debug("Status222:{}", Thread.currentThread().isInterrupted());
        });

        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            t1.interrupt();
            TimeUnit.SECONDS.sleep(2);
            t1.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
