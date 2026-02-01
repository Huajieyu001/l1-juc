package top.huajieyu001.chatper004;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author huajieyu
 * @Date 2026/1/31 13:29
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p114")
public class P114 {

    public static void main(String[] args) {
        test2();
    }

    public static void test1() {
        Room114 room = new Room114();

        log.debug("start---");
        new Thread(() -> {
            try {
                room.sleep();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                room.study();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static void test2() {
        Room114Adv room = new Room114Adv();

        log.debug("start---");
        new Thread(() -> {
            try {
                room.sleep();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                room.study();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}

@Slf4j(topic = "c.room114")
class Room114{

    public void sleep() throws InterruptedException {
        synchronized (this){
            TimeUnit.SECONDS.sleep(1);
            log.debug("sleeping...");
        }
    }

    public void study() throws InterruptedException {
        synchronized (this){
            TimeUnit.SECONDS.sleep(2);
            log.debug("studying...");
        }
    }
}

@Slf4j(topic = "c.room114")
class Room114Adv{

    private final Object sleepLock = new Object();

    private final Object studyLock = new Object();

    public void sleep() throws InterruptedException {
        synchronized (sleepLock){
            TimeUnit.SECONDS.sleep(1);
            log.debug("sleeping...");
        }
    }

    public void study() throws InterruptedException {
        synchronized (studyLock){
            TimeUnit.SECONDS.sleep(2);
            log.debug("studying...");
        }
    }
}
