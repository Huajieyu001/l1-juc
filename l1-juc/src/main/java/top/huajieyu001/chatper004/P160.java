package top.huajieyu001.chatper004;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author huajieyu
 * @Date 2026/2/10 15:03
 * @Version 1.0
 * @Description TODO
 */
public class P160 {

    public static void main(String[] args) {
        Account5 account = new Account5UnsafeNonLock(10000);
        Account5.test(account);

        System.out.println("--------------------Used Lock");
        Account5 account2 = new Account5UnsafeUsedLock(10000);
        Account5.test(account2);

        System.out.println("--------------------CAS");
        Account5 account3 = new Account5UnsafeUsedCas(10000);
        Account5.test(account3);

    }
}

interface Account5{

    Integer getBalance();

    void withdraw(Integer amount);

    static void test(Account5 account){
        List<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }
        long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Speed up time: " + (System.currentTimeMillis() - startTime));
        System.out.println(account.getBalance());
    }
}

class Account5UnsafeNonLock implements Account5{

    private Integer balance;

    public Account5UnsafeNonLock(Integer balance){
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return this.balance;
    }

    @Override
    public void withdraw(Integer amount) {
        this.balance -= amount;
    }
}

class Account5UnsafeUsedLock implements Account5{

    private Integer balance;

    public Account5UnsafeUsedLock(Integer balance){
        this.balance = balance;
    }

    @Override
    public synchronized Integer getBalance() {
        return this.balance;
    }

    @Override
    public synchronized void withdraw(Integer amount) {
        this.balance -= amount;
    }
}

class Account5UnsafeUsedCas implements Account5{

    private AtomicInteger balance;

    public Account5UnsafeUsedCas(Integer balance){
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return this.balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
//        while (true){
//            int oldBalance = this.balance.get();
//            int newBalance = oldBalance - amount;
//            if(this.balance.compareAndSet(oldBalance, newBalance)){
//                break;
//            }
//        }

        // 简化写法
        this.balance.getAndAdd(-amount);
    }
}
