package top.huajieyu001.chatper004;

import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author huajieyu
 * @Date 2026/2/10 19:48
 * @Version 1.0
 * @Description TODO
 */
public class P186 {

    public static void main(String[] args) throws InterruptedException {
        Integer int1 = new Integer(0);
        MyAtomicInteger int2 = new MyAtomicInteger(0);

        List<Thread> ts1 = new ArrayList<>();
        List<Thread> ts2 = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            ts1.add(new Thread(() -> {
//                int1.
//            }));
//        }

        for (int i = 0; i < 1000; i++) {
            ts1.add(new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    int2.increment();
                }
            }));
        }

        ts1.forEach(Thread::start);
        for (Thread thread : ts1) {
            thread.join();
        }

        System.out.println(int2.get());
    }
}

class MyAtomicInteger {
    private volatile int value;
    private static final long offset;
    private static final Unsafe unsafe;
    static {
        try {
            unsafe = UnsafeUtils.getUnsafe();
            offset = unsafe.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public int get(){
        return value;
    }

    public void increment(){
        int prev, newValue;
        do{
            prev = value;
            newValue = prev + 1;
        } while(!unsafe.compareAndSwapInt(this, offset, prev, newValue));
    }

    public void decrement(){
        int prev, newValue;
        do{
            prev = value;
            newValue = prev - 1;
        } while(!unsafe.compareAndSwapInt(this, offset, prev, newValue));
    }
}