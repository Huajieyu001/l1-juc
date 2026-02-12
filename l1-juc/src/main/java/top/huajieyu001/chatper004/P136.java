package top.huajieyu001.chatper004;

/**
 * @Author huajieyu
 * @Date 2026/2/8 21:27
 * @Version 1.0
 * @Description TODO
 */
public class P136 {

    volatile static boolean flag = true; // 使用volatile修饰，修改对其他线程可见，每次读取变量都从主存中获取

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (flag) {

            }
        }).start();

        Thread.sleep(1000);
        flag = false;
    }
}
