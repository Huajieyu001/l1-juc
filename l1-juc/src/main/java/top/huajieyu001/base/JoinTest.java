package top.huajieyu001.base;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author huajieyu
 * @Date 2026/1/29 15:26
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.joinTest")
public class JoinTest {

    static int num = 0;

    public static void main(String[] args) {
        testJoinTimed();
    }

    public static void testJoin(){
        log.debug("start");

        Thread t1 = new Thread(() -> {
            log.debug("t1 start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("t1 end");
            num = 10;
        });

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("num = {}", num);
    }

    public static void testJoinTimed(){
        log.debug("start");

        Thread t1 = new Thread(() -> {
            log.debug("t1 start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("t1 end");
            num = 10;
        });

        t1.start();
        try {
            t1.join(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("num = {}", num);
    }
}
