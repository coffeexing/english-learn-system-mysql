package cn.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
class DemoApplicationTests {
    @Test
    void recurseFilesTest() throws IOException {
        File file = new File("new_src_test");

        long t1 = System.currentTimeMillis();

        // 递归-深度遍历文件树
//        System.out.println(TreeUtils.recurseFiles(file));
        // 栈-深度遍历文件树
//        TreeUtils.stackRecurseFiles(file);
        // 队列-广度度遍历文件树
//        TreeUtils.queueRecurseFiles(file);

        long t2 = System.currentTimeMillis();
        System.out.println("耗时：" + (t2 - t1));
    }
}
