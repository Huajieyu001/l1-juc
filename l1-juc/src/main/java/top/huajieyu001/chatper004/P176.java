package top.huajieyu001.chatper004;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @Author huajieyu
 * @Date 2026/2/10 18:22
 * @Version 1.0
 * @Description TODO
 */
public class P176 {

    public static void main(String[] args) {
        testIncrement(() -> new AtomicLong(0), AtomicLong::getAndIncrement);
        testIncrement(() -> new LongAdder(), LongAdder::increment);
    }

    private static <T> void testIncrement(Supplier<T> supplier, Consumer<T> consumer) {
        T adder = supplier.get();
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500000; j++) {
                    consumer.accept(adder);
                }
            }));
        }

        long start = System.nanoTime();
        for (Thread t : ts) {
            t.start();
        }

        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        long end = System.nanoTime();

        System.out.println(adder + " == Speed time: " +(end - start) / 1000000 + "ms");
    }
}
