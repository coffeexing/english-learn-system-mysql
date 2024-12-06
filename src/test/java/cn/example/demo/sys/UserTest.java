package cn.example.demo.sys;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-29 11:19
 */
@SpringBootTest
public class UserTest {
    @Test
    void recurseTest() {
        System.out.println("实际数：" + count(10));
    }

    int count(int m) {
        int i = m;
        if (m != 1) {
            if (m % 2 != 0) {
                i += 1;
            }
            i += count(m / 2);
        }
        System.out.println(m);
        return i;
    }

    @Test
    void simpleTest(){
        System.out.println(new Date());
    }
}
