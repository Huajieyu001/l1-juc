package top.huajieyu001.chatper004;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author huajieyu
 * @Date 2026/2/1 13:46
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p118")
public class P118 {

    public static final Object lock = new Object();

    public static int count = 10;

    public static void main(String[] args) {
        new Thread(() -> {
            while (count < 20) {
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                    count++;
                    log.debug("+++++count:{}", count);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();

        new Thread(() -> {
            while (count > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                    count--;
                    log.debug("-----count:{}", count);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();


    }

}
