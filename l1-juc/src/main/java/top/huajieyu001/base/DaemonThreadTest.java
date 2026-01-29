package top.huajieyu001.base;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author huajieyu
 * @Date 2026/1/29 18:46
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.dtt")
public class DaemonThreadTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            log.debug("t1 结束");
        });

        t1.setDaemon(true);
        t1.start();
        Thread.sleep(1000);
        log.debug("main 结束");
    }
}
