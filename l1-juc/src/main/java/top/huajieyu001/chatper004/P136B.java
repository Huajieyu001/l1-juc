package top.huajieyu001.chatper004;

/**
 * @Author huajieyu
 * @Date 2026/2/8 21:27
 * @Version 1.0
 * @Description TODO
 */
public class P136B {

    static boolean flag = true; // 不使用volatile修饰，通过搭配synchronized关键字修改对其他线程可见，每次读取变量都从主存中获取

    final static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (!flag) {
                        System.out.println("Ending thread");
                        break;
                    }
                }
            }
        }).start();

        Thread.sleep(1000);
        flag = false;
    }
}
