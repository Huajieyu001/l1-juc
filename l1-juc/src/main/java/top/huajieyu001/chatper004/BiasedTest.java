package top.huajieyu001.chatper004;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @Author huajieyu
 * @Date 2026/1/30 13:15
 * @Version 1.0
 * @Description TODO
 */
@Slf4j(topic = "c.BiasedTest")
public class BiasedTest {

    public static void main(String[] args) {
        Dog dog = new Dog();
        log.debug(ClassLayout.parseInstance(dog).toPrintable());
    }
}

class Dog{

}
