package top.huajieyu001.chatper004;

/**
 * @Author huajieyu
 * @Date 2026/1/31 13:37
 * @Version 1.0
 * @Description TODO
 */
public class P115 {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        DeadLockRoom room = new DeadLockRoom();
        new Thread(()->{
            try {
                room.fun1();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(()->{
            try {
                room.fun2();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}

class DeadLockRoom{
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void fun1() throws InterruptedException {
        synchronized (lock1){
            Thread.sleep(1000);
            synchronized (lock2){
                System.out.println("fun1");
            }
        }
    }

    public void fun2() throws InterruptedException {
        synchronized (lock2){
            Thread.sleep(1000);
            synchronized (lock1){
                System.out.println("fun2");
            }
        }
    }
}
