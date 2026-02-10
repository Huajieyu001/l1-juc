package top.huajieyu001.chatper004;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author huajieyu
 * @Date 2026/2/10 18:47
 * @Version 1.0
 * @Description TODO
 */
public class P177 {

    public static void main(String[] args) {
        LockCas instance = new LockCas();
        new Thread(() -> {
            System.out.println("Begin " + Thread.currentThread().getName());
            instance.lock();
            try {
                System.out.println("Running " + Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                instance.unlock();
            }
        }).start();

        new Thread(() -> {
            System.out.println("Begin " + Thread.currentThread().getName());
            instance.lock();
            try {
                System.out.println("Running " + Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                instance.unlock();
            }
        }).start();
    }
}

class LockCas{

    private AtomicInteger state = new AtomicInteger(0);

    public void lock(){
        while(true) {
            if(state.compareAndSet(0, 1)){
                return;
            }
        }
    }

    public void unlock(){
        System.out.println("unlock");
        state.set(0);
    }
}
