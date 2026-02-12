package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author huajieyu
 * @Date 2026/2/12 12:20
 * @Version 1.0
 * @Description TODO
 */
public class P247 {

    public static void main(String[] args) {
        DataContainer dc = new DataContainer();

        dc.write("Ssdauigfdjg");

//        for (int i = 0; i < 5; i++) {
//            new Thread(() -> {
//                System.out.println(dc.read());
//            }).start();
//        }

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                dc.write("sdjgfsiafnisdhfisdfsfd");
            }).start();
        }
    }
}

@Slf4j(topic = "c.p247dc")
class DataContainer {

    private Object data;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Object read() {
        log.debug("获取读锁");
        lock.readLock().lock();
        try {
            log.debug("读取中...");
            return data;
        }   finally {
            log.debug("释放读锁");
            lock.readLock().unlock();
        }
    }

    public void write(Object data) {
        log.debug("获取写锁");
        lock.writeLock().lock();
        try {
            log.debug("写入中...");
            this.data = data;
        } finally {
            log.debug("释放写锁");
            lock.writeLock().unlock();
        }
    }
}