package cn.example.demo.common.log.annotation;

import cn.example.demo.common.log.enums.BusinessType;

import java.lang.annotation.*;

/**
 * Describe: 系 统 日 志 注 解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Logging {

    /**
     * 默认无参输入
     */
    String value() default "暂无标题";

    /**
     * Title 默认输入
     */
    String title() default "暂无标题";

    /**
     * Describe 默认输入
     */
    String describe() default "暂无介绍";

    /**
     * 业 务 类 型  默认Query
     */
    BusinessType type() default BusinessType.QUERY;
}
