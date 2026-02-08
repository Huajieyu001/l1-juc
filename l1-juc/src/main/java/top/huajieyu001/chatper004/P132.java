package top.huajieyu001.chatper004;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author huajieyu
 * @Date 2026/2/7 20:34
 * @Version 1.0
 * @Description TODO
 */
public class P132 {

    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) {

        ParkUnParkPrint instance = new ParkUnParkPrint(5);

        t1 = new Thread(()-> {
            instance.print("a", t2);
        });
        t2 = new Thread(()-> {
            instance.print("b", t3);
        });
        t3 = new Thread(()-> {
            instance.print("c", t1);
        });

        t1.start();
        t2.start();
        t3.start();
        LockSupport.unpark(t1);
    }

}

class ParkUnParkPrint {

    private int loopNumber;

    public ParkUnParkPrint(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String str, Thread next) {    
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(next);
        }
    }
}
