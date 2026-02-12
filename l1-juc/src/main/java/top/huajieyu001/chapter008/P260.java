package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.StampedLock;

/**
 * @Author huajieyu
 * @Date 2026/2/12 13:19
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p260")
public class P260 {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        StampedData data = new StampedData("Joe");

        new Thread(() -> {
            try {
                Thread.sleep(200);
                data.write("Hello");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            log.debug("read data is {}", data.read(1000));
        }).start();

    }
}

@Slf4j(topic = "c.p260sd")
class StampedData {

    private Object data;

    private StampedLock stampedLock = new StampedLock();

    StampedData(Object data) {
        this.data = data;
    }

    public Object read(long readTime){
        long readStamp = stampedLock.tryOptimisticRead();
        if(stampedLock.validate(readStamp)){
            try {
                Thread.sleep(readTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return data;
        }
        long stamp = stampedLock.readLock();
        try {
            Thread.sleep(readTime);
            return data;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    public void write(Object data){
        long writeStamp = stampedLock.writeLock();
        try {
            log.debug("writing data");
            this.data = data;
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            stampedLock.unlockWrite(writeStamp);
        }
    }
}
