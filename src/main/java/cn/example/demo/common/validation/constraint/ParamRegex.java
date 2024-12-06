package cn.example.demo.common.validation.constraint;

import cn.example.demo.common.dictionary.DataNormDefinition;
import cn.example.demo.common.validation.validator.DoubleValidator;
import cn.example.demo.common.validation.validator.IntegerValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


/**
 * <p>
 * 通用入参校验注解
 * </p>
 *
 * @author Lizuxian
 * @create 2020-12-10 9:45
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamRegex {
    /**
     * <p>
     * 浮点型
     * </P>
     */
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = DoubleValidator.class)
    @interface Double {
        String message() default "非数值型格式（浮点数）";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        String regexp() default DataNormDefinition.doubleFormat;
    }

    /**
     * <p>
     * 整型
     * </P>
     */
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = IntegerValidator.class)
    @interface Integer {
        String message() default "非数值型格式（整数型）";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        String regexp() default DataNormDefinition.integerFormat;
    }
}


