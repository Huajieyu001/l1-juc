package top.huajieyu001.chatper004;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author huajieyu
 * @Date 2026/1/30 21:14
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p98")
public class Part98 {

    public static void main(String[] args) {
        test2();
    }

    public static void test1(){
        GuardedObject guardedObject = new GuardedObject();

        new Thread(() -> {
            synchronized (guardedObject) {
                log.debug("111");
                log.debug(guardedObject.get().toString());
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            synchronized (guardedObject) {
                log.debug("222");
                guardedObject.complete("sdfg");
            }
        }).start();
    }

    public static void test2(){
        GuardedObjectTimeout guardedObject = new GuardedObjectTimeout();

        new Thread(() -> {
            synchronized (guardedObject) {
                log.debug("111");
                log.debug("返回结果={}", guardedObject.get(1000));
            }
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            synchronized (guardedObject) {
                log.debug("222");
                guardedObject.complete("sdfg");
            }
        }).start();
    }
}
class GuardedObject {
    private Object response;

    public Object get(){
        synchronized (this){
            while(response == null){
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return response;
        }
    }

    public void complete(Object response){
        synchronized (this){
            this.response = response;
            notifyAll();
        }
    }
}

class GuardedObjectTimeout {
    private Object response;

    public Object get(long timeout){
        synchronized (this){
            long start = System.currentTimeMillis();
            long passed = System.currentTimeMillis() - start;
            while(response == null){
                long waitTime = timeout - passed;
                if(waitTime <= 0){
                    break;
                }
                try {
                    wait(waitTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                passed = System.currentTimeMillis() - start;
            }
            return response;
        }
    }

    public void complete(Object response){
        synchronized (this){
            this.response = response;
            notifyAll();
        }
    }
}
