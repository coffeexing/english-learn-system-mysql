package cn.example.demo.common.tools.obj.reflect;

import java.lang.annotation.*;

/**
 * <p>
 * 实体类字段开启不可变更
 * </p>
 *
 * @author Lizuxian
 * @create 2020/11/6 16:45
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Immutable {
    boolean value() default true;
}
