package cn.example.demo.common.tools.obj.reflect;

import java.lang.annotation.*;

/**
 * <p>
 * 实体类：字段忽略注解
 * </p>
 *
 * @author Lizuxian
 * @create 2020/11/5 16:12
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldIgnore {
    boolean value() default true;
}
