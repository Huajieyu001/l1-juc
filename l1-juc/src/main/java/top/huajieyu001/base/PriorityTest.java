package top.huajieyu001.base;

/**
 * @Author huajieyu
 * @Date 2026/1/29 15:09
 * @Version 1.0
 * @Description TODO
 */
public class PriorityTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            int count = 0;
            while (true) {
                System.out.println("-------1>>>" + count++);
            }
        });
        Thread t2 = new Thread(() -> {
            int count = 0;
            while (true) {
//                Thread.yield();
                System.out.println("              -------2>>>" + count++);
            }
        });
//        t1.setPriority(Thread.MAX_PRIORITY);
//        t2.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        t2.start();
    }
}
