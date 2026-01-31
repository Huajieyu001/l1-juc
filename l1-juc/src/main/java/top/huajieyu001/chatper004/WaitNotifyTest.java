package top.huajieyu001.chatper004;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author huajieyu
 * @Date 2026/1/30 20:23
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.wnt")
public class WaitNotifyTest {

    static final Object lock = new Object();

    private static boolean hasCigarette = false;

    private static boolean hasTakeout = false;

    public static void main(String[] args) {
        waitNotifyMultiNotifyWhere();
    }

    public static void noWaitNotify() {
        new Thread(() -> {
            synchronized (lock) {
                log.debug("Has Cigarette: {}", hasCigarette);
                if(!hasCigarette){
                    log.debug("Resting");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("Has Cigarette: {}", hasCigarette);
                if(hasCigarette){
                    log.debug("Working");
                }
            }
        }, "Worker").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (lock) {
                    log.debug("Working");
                }
            }, "Else worker").start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            hasCigarette = true;
        }, "Gift").start();
    }

    public static void waitNotify() {
        new Thread(() -> {
            synchronized (lock) {
                log.debug("Has Cigarette: {}", hasCigarette);
                if(!hasCigarette){
                    log.debug("Resting");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("Has Cigarette: {}", hasCigarette);
                if(hasCigarette){
                    log.debug("Working");
                }
            }
        }, "Worker").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (lock) {
                    log.debug("Working");
                }
            }, "Else worker").start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            synchronized (lock) {
                hasCigarette = true;
                lock.notify();
            }
        }, "Gift").start();
    }

    public static void waitNotifyMultiNotify() {
        new Thread(() -> {
            synchronized (lock) {
                log.debug("Has Cigarette: {}", hasCigarette);
                if(!hasCigarette){
                    log.debug("Resting");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("Has Cigarette: {}", hasCigarette);
                if(hasCigarette){
                    log.debug("Cigarette Working");
                }
            }
        }, "Worker").start();

        new Thread(() -> {
            synchronized (lock) {
                log.debug("Has Takeout: {}", hasTakeout);
                if(!hasTakeout){
                    log.debug("Resting");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("Has Takeout: {}", hasTakeout);
                if(hasTakeout){
                    log.debug("hasTakeout Working");
                }
            }
        }, "Worker").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            synchronized (lock) {
                hasCigarette = true;
                hasTakeout = true;
                lock.notify();
            }
        }, "Gift").start();
    }

    public static void waitNotifyMultiNotifyAll() {
        new Thread(() -> {
            synchronized (lock) {
                log.debug("Has Cigarette: {}", hasCigarette);
                if(!hasCigarette){
                    log.debug("Resting");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("Has Cigarette: {}", hasCigarette);
                if(hasCigarette){
                    log.debug("Cigarette Working");
                }
            }
        }, "Worker").start();

        new Thread(() -> {
            synchronized (lock) {
                log.debug("Has Takeout: {}", hasTakeout);
                if(!hasTakeout){
                    log.debug("Resting");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("Has Takeout: {}", hasTakeout);
                if(hasTakeout){
                    log.debug("hasTakeout Working");
                }
            }
        }, "Worker").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            synchronized (lock) {
                hasCigarette = true;
                hasTakeout = true;
                lock.notifyAll();
            }
        }, "Gift").start();
    }

    public static void waitNotifyMultiNotifyWhere() {
        new Thread(() -> {
            synchronized (lock) {
                log.debug("Has Cigarette: {}", hasCigarette);
                while(!hasCigarette){
                    log.debug("Resting");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("Has Cigarette: {}", hasCigarette);
                if(hasCigarette){
                    log.debug("Cigarette Working");
                }
            }
        }, "Worker111").start();

        new Thread(() -> {
            synchronized (lock) {
                log.debug("Has Takeout: {}", hasTakeout);
                if(!hasTakeout){
                    log.debug("Resting");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("Has Takeout: {}", hasTakeout);
                if(hasTakeout){
                    log.debug("hasTakeout Working");
                }
            }
        }, "Worker222").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            synchronized (lock) {
                hasTakeout = true;
                lock.notifyAll();
                hasCigarette = true;
            }
        }, "Gift").start();
    }
}
