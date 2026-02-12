package top.huajieyu001.chatper004;

/**
 * @Author huajieyu
 * @Date 2026/2/8 21:27
 * @Version 1.0
 * @Description TODO
 */
public class P135 {

    static boolean flag = true; // 常规写法，会导致修改的flag对其他线程不可见，因为可能有缓存

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (flag) {

            }
        }).start();

        Thread.sleep(1000);
        flag = false;
    }
}
