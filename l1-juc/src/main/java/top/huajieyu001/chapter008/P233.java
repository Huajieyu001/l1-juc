package top.huajieyu001.chapter008;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @Author huajieyu
 * @Date 2026/2/11 21:53
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p233")
public class P233 {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        int number = 1000;
        long start = System.currentTimeMillis();
        Integer invoke = forkJoinPool.invoke(new MyTask(number));
        long end = System.currentTimeMillis();
        System.out.println("spend time: " + (end - start));
        System.out.println("result: " + invoke);
        start = System.currentTimeMillis();
        int result = recursive(number);
        end = System.currentTimeMillis();
        System.out.println("spend time: " + (end - start));
        System.out.println("result: " + result);
    }

    public static int recursive(int n) {
        if(n == 1) {
            return 1;
        }
        return n + recursive(n - 1);
    }
}

/**
 * 1 + 2 + .. + n
 */
class MyTask extends RecursiveTask<Integer> {

    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if(n == 1) {
            return 1;
        }
        MyTask newTask = new MyTask(n - 1);
        newTask.fork();
        return n + newTask.join();
    }
}
