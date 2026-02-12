package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author huajieyu
 * @Date 2026/2/11 21:19
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p228")
public class P228 {

    public static void main(String[] args) {
        test2();
    }

    /**
     * 测试定时任务
     */
    public static void test1() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime time = now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);
        log.debug("{}", now);
        log.debug("{}", time);
        if (time.isBefore(now)) {
            time = time.plusWeeks(1);
        }
        log.debug("{}", time);

        Duration duration = Duration.between(now, time);
        long delay = duration.toMillis();
        long rate = 1000 * 60 * 60 * 24 * 7;

        log.debug("delay = {}", delay);

        executor.scheduleAtFixedRate(() -> {}, delay, rate, TimeUnit.MILLISECONDS);
    }

    /**
     * 测试定时任务
     */
    public static void test2() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime time = now.withHour(21).withMinute(40).withSecond(50).withNano(0).with(DayOfWeek.WEDNESDAY);
        log.debug("{}", now);
        log.debug("{}", time);
        if (time.isBefore(now)) {
            time = time.plusWeeks(1);
        }
        log.debug("{}", time);

        Duration duration = Duration.between(now, time);
        long delay = duration.toMillis();
        long rate = 1000 * 3;

        log.debug("delay = {}", delay);

        executor.scheduleAtFixedRate(() -> {
            log.debug("running");
        }, delay, rate, TimeUnit.MILLISECONDS);
    }
}
