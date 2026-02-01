package top.huajieyu001.chatper004;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author huajieyu
 * @Date 2026/2/1 15:44
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p126")
public class P126 {

    public static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        lock.lock();

    }
}
