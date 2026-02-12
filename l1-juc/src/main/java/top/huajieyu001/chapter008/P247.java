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
        test2();
    }

    public static void test0(){
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

    /**
     * 测试锁升级（读锁->写锁）
     */
    public static void test1(){
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

        System.out.println(1);
        rwl.readLock().lock();
        System.out.println(2);
        rwl.writeLock().lock(); // 这里会被一直卡着
        System.out.println(3);
        rwl.readLock().unlock();
        System.out.println(4);
        rwl.writeLock().unlock();
        System.out.println(5);
    }

    /**
     * 测试锁降级（写锁->读锁）可以全部执行
     */
    public static void test2(){
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

        System.out.println(1);
        rwl.writeLock().lock();
        System.out.println(2);
        rwl.readLock().lock();
        System.out.println(3);
        rwl.readLock().unlock();
        System.out.println(4);
        rwl.writeLock().unlock();
        System.out.println(5);
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