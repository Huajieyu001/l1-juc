package top.huajieyu001.chatper004;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author huajieyu
 * @Date 2026/2/10 16:24
 * @Version 1.0
 * @Description TODO
 */
public class P167 {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        atomicInteger.getAndUpdate(x -> x * 8);
        System.out.println(atomicInteger);
        atomicInteger.getAndUpdate(x -> x * 8);
        System.out.println(atomicInteger);
        atomicInteger.getAndUpdate(x -> x / 2 + 1);
        System.out.println(atomicInteger);
    }
}
