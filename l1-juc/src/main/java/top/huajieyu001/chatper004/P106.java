package top.huajieyu001.chatper004;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @Author huajieyu
 * @Date 2026/1/31 11:48
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.p106")
public class P106 {

    public static void main(String[] args) throws InterruptedException {
        MessageQueue messageQueue = new MessageQueue(2);
        for (int i = 0; i < 3; i++) {
            final int finalI = i;
            new Thread(() -> {
                Message message = new Message(finalI);
                message.setMessage("HAHAHAH" + finalI);
                try {
                    messageQueue.put(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "producer-" + i).start();
        }

        for (int i = 0; i < 4; i++) {
            Thread.sleep(1000);
            new Thread(() -> {
                try {
                    messageQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "consumer-" + i).start();
        }
    }
}

@Slf4j(topic = "c.mq")
class MessageQueue {

    private final LinkedList<Message> list = new LinkedList<Message>();

    private int size;

    public MessageQueue(int size) {
        this.size = size;
    }

    public Message take() throws InterruptedException {
        synchronized (list) {
            while(list.isEmpty()){
                log.debug("队列为空");
                list.wait();
            }
            Message message = list.removeFirst();
            list.notifyAll();
            log.debug("消费消息：{}", message);
            return message;
        }
    }

    public void put(Message message) throws InterruptedException {
        synchronized(list){
            while(list.size() >= size){
                log.debug("队列已满");
                list.wait();
            }
            list.addLast(message);
            log.debug("新增生产消息：{}", message);
            list.notifyAll();
        }
    }

}

final class Message {

    private int id;

    private Object message;

    public Message(int id) {
        this.id = id;
    }

    public Message(int id, Object message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message=" + message +
                '}';
    }
}