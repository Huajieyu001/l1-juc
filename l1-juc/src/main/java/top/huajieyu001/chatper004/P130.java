package top.huajieyu001.chatper004;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author huajieyu
 * @Date 2026/2/1 16:46
 * @Version 1.0
 * @Description 三个线程，分别打印a，b，c，依次交替打印abc5次，使用wait+notify和共享变量来实现
 */
@Slf4j(topic = "c.p130")
public class P130 {

    static final Object lock = new Object();

    static int countA = 5;

    static int countB = 5;

    static int countC = 5;

    static int status = 1;

    public static void main(String[] args) {
        test3();
    }

    public static void test1() {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    try {
                        if (status != 1){
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (status == 1) {
                        status = 2;
                        countA--;
                        log.debug("a");
                        lock.notifyAll();
                        if (countA == 0) {
                            break;
                        }
                    }
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    try {
                        if (status != 2){
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (status == 2) {
                        status = 3;
                        countB--;
                        log.debug("b");
                        lock.notifyAll();
                        if (countB == 0) {
                            break;
                        }
                    }
                }
            }
        });

        Thread t3 = new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    try {
                        if (status != 3){
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (status == 3) {
                        status = 1;
                        countC--;
                        log.debug("c");
                        lock.notifyAll();
                        if (countC == 0) {
                            break;
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

    public static void test2() {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    try {
                        if (status != 1){
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (status == 1) {
                        status = 2;
                        countA--;
                        log.debug("a");
                        lock.notifyAll();
                        if (countA == 0) {
                            break;
                        }
                    }
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    try {
                        if (status != 2){
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (status == 2) {
                        status = 3;
                        countB--;
                        log.debug("b");
                        lock.notifyAll();
                        if (countB == 0) {
                            break;
                        }
                    }
                }
            }
        });

        Thread t3 = new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    try {
                        if (status != 3){
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (status == 3) {
                        status = 1;
                        countC--;
                        log.debug("c");
                        lock.notifyAll();
                        if (countC == 0) {
                            break;
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

    public static void test3() {
        WaitNotifyLoop obj = new WaitNotifyLoop(5);

        new Thread(() -> {
            try {
                obj.print("a", 1, 2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                obj.print("b", 2, 3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                obj.print("c", 3, 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}

class WaitNotifyLoop {

    private int status = 1;

    private int loopCount;

    public WaitNotifyLoop(int loopCount) {
        this.loopCount = loopCount;
    }

    public int getLoopCount() {
        return loopCount;
    }

    public void print(String str, int waitFlag, int nextFlag) throws InterruptedException {
        for (int i = 0; i < loopCount; i++) {
            synchronized (this) {
                while (status != waitFlag) {
                    this.wait();
                }
                System.out.print(str);
                status = nextFlag;
                this.notifyAll();
            }
        }
    }
}
