package top.huajieyu001.chatper004;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author huajieyu
 * @Date 2026/2/10 19:30
 * @Version 1.0
 * @Description TODO
 */
public class P184 {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe obj = (Unsafe) theUnsafe.get(null);

        System.out.println(obj);
    }
}
