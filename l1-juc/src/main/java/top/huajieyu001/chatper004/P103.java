package top.huajieyu001.chatper004;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * @Author huajieyu
 * @Date 2026/1/30 21:53
 * @Version 1.0
 * @Description TODO
 */
public class P103 {
}

class MailBox{
    private static Map<Integer, GuardedObjectTimeoutV3> box = new Hashtable<>();

    private static int id = 1;

    public synchronized static int generateId(){
        return id++;
    }

    public static GuardedObjectTimeoutV3 createObject(){
        GuardedObjectTimeoutV3 obj = new GuardedObjectTimeoutV3(generateId());
        box.put(obj.getId(), obj);
        return obj;
    }

    public static Set<Integer> getIds() {
        return box.keySet();
    }
}

class GuardedObjectTimeoutV3 {

    private int id;

    private Object response;

    public int getId() {
        return id;
    }

    GuardedObjectTimeoutV3(int id) {
        this.id = id;
    }

    public Object get(long timeout){
        synchronized (this){
            long start = System.currentTimeMillis();
            long passed = System.currentTimeMillis() - start;
            while(response == null){
                long waitTime = timeout - passed;
                if(waitTime <= 0){
                    break;
                }
                try {
                    wait(waitTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                passed = System.currentTimeMillis() - start;
            }
            return response;
        }
    }

    public void complete(Object response){
        synchronized (this){
            this.response = response;
            notifyAll();
        }
    }
}
