package top.huajieyu001.base;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author huajieyu
 * @Date 2026/1/29 16:16
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.Tpt")
public class TwoPhaseTest {

    public static void main(String[] args) {
        TwoPhase monitor = new TwoPhase();
        monitor.start();

        try {
            Thread.sleep(3500);
            monitor.stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

@Slf4j(topic = "c.Tp")
class TwoPhase {
    private Thread monitor;

    public void start() {
        monitor = new Thread(()->{
            while (true) {
                Thread currentThread = Thread.currentThread();
                if(currentThread.isInterrupted()){
                    log.debug("结束监控");
                    break;
                }
                try{
                    Thread.sleep(1000);
                    log.debug("监控中");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.debug("处理异常，重置中断标记");
                    currentThread.interrupt();
                }
            }
        });
        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
    }
}
