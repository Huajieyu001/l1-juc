package top.huajieyu001;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author huajieyu
 * @Date 2026/1/28 13:39
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "Multi")
public class Multi {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            while (true) {
                log.debug(Thread.currentThread().getName());
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println(thread.getState());
        Thread thread1 = new Thread(runnable);
        thread1.start();
    }
}
