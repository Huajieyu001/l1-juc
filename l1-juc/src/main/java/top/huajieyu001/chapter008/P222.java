package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author huajieyu
 * @Date 2026/2/11 20:18
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p222")
public class P222 {

    private static List<String> MENU = Arrays.asList("回锅肉", "辣子鸡", "宫保鸡丁", "东坡肉");

    private static Random RANDOM = new Random();

    public static String cooking() {
        return MENU.get(RANDOM.nextInt(MENU.size()));
    }

    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 线程饥饿现象
     */
    public static void test1() {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        pool.execute(() -> {
            log.debug("ordering");
            Future<String> result = pool.submit(()->{
                log.debug("cooking");
                return cooking();
            });
            try {
                log.debug("上菜-{}", result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        pool.execute(() -> {
            log.debug("ordering");
            Future<String> result = pool.submit(()->{
                log.debug("cooking");
                return cooking();
            });
            try {
                log.debug("上菜-{}", result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    public static void test2() {
        ThreadPoolExecutor worker = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        ThreadPoolExecutor cooker = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

        worker.execute(() -> {
            log.debug("ordering");
            Future<String> result = cooker.submit(()->{
                log.debug("cooking");
                return cooking();
            });
            try {
                log.debug("上菜-{}", result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        worker.execute(() -> {
            log.debug("ordering");
            Future<String> result = cooker.submit(()->{
                log.debug("cooking");
                return cooking();
            });
            try {
                log.debug("上菜-{}", result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
