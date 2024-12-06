package cn.example.demo.common.tools.obj.reflect;

import java.lang.annotation.*;

/**
 * <p>
 * 实体类字段别名
 * </p>
 *
 * @author Lizuxian
 * @create 2020/6/29 12:05
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldColumn {
    String value();
}
