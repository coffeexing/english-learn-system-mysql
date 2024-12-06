package cn.example.demo;

import org.junit.jupiter.api.Test;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * <p>
 *
 * </p>
 *
 * @author Lizuxian
 * @create 2021-04-27 13:35
 */
public class DynamicClassLoadTest {
    @Test
    void test() {
        JavaCompiler systemJavaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = systemJavaCompiler.getStandardFileManager(null, Locale.CHINA, StandardCharsets.UTF_8);
    }

    @Test
    void test2() {
        List<List<Boolean>> rules = new ArrayList();
        for (int i = 0; i < 3; i++) {
            List<Boolean> orCriteria = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                int i1 = new Random().nextInt(1000);
                if (i1 % 2 == 0) {
                    orCriteria.add(true);
                } else {
                    orCriteria.add(false);
                }
            }
            rules.add(orCriteria);
        }

        System.out.println(rules);
        System.out.println(ruleEngine(rules));
    }

    boolean ruleEngine(List<List<Boolean>> rules) {
        byte result = 0;
        for (List<Boolean> or : rules) {
            byte orCriteria = 1;
            for (Boolean c : or) {
                if (c) {
                    orCriteria &= 1;
                } else {
                    orCriteria &= 0;
                    break;
                }
            }
            result |= orCriteria;
        }
        System.out.println(result);
        return result == 1;
    }

}
