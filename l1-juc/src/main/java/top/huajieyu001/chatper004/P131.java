package top.huajieyu001.chatper004;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author huajieyu
 * @Date 2026/2/3 22:07
 * @Version 1.0
 * @Description TODO
 */
public class P131 {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        AwaitSignal instance = new AwaitSignal(5);

        Condition a = instance.newCondition();
        Condition b = instance.newCondition();
        Condition c = instance.newCondition();


        new Thread(() -> {
            instance.print("a", a, b);
        }).start();
        new Thread(() -> {
            instance.print("b", b, c);
        }).start();
        new Thread(() -> {
            instance.print("c", c, a);
        }).start();

        try{
            instance.lock();
            a.signal();
        } finally {
            instance.unlock();
        }
    }
}

class AwaitSignal extends ReentrantLock {

    private int loopNumber;

    public AwaitSignal(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String str, Condition cur, Condition next) {
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try{
                cur.await();
                System.out.print(str);
                next.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                unlock();
            }
        }
    }
}