package cn.example.demo.common.validation.validator;

import cn.example.demo.common.validation.constraint.ParamRegex;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p>
 *
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-03 16:33
 */
@Component
@Configuration
public class DoubleValidator implements ConstraintValidator<ParamRegex.Double, String> {
    private String reg;

    @Override
    public void initialize(ParamRegex.Double d) {
        reg = d.regexp();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches(reg) ? true : false;
    }
}
