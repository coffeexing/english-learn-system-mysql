package cn.example.demo.common.filter.parameter;

import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.retrieval.Criteria;
import cn.example.demo.common.retrieval.RetrievalCriteria;
import cn.example.demo.common.retrieval.RetrievalParam;
import cn.example.demo.common.tools.obj.DateAgeUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 请求参数预处理器
 * </p>
 *
 * @author Lizuxian
 * @create 2020/11/11 11:53
 */
@Component
@Aspect
@Order(3)
public class RequestParamPreProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestParamPreProcessor.class);

    /**
     * <p>
     * 拦截所有（分页）查询接口，校验参数合法性
     * </P>
     *
     * @param pJoinPoint
     */
//    @Around(value = "execution(public * cn.example.demo..controller..*Controller.*(..))")
    public Object preProcess1(ProceedingJoinPoint pJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) pJoinPoint.getSignature();    // Get Method
        Parameter[] parameters = signature.getMethod().getParameters();  // Get Parameters
        Object[] args = pJoinPoint.getArgs();   // Get Arguments

        List<String> validResult = new ArrayList<>();   // 校验结果
        BindingResult bindingResult = null; // The return Object of Spring Data Validation
        // 数值型格式
        String numberFormat = "^[0-9]*";
        for (int i = 0; i < parameters.length; i++) {
            // 1.校验参数是否合法数字
            String msg;
            if (parameters[i].getName().equals("page")) {   // 页数
                if (!args[i].toString().matches(numberFormat)) {
                    msg = "【字段名：page" +
                            "；拒绝的值：" + args[i] +
                            "；错误信息：非数值型（整型）" +
                            "；数据类型：" + parameters[i].getType() + "】";
                    validResult.add(msg);
                }
            } else if (parameters[i].getName().equals("limit")) {   // 每页大小
                if (!args[i].toString().matches(numberFormat)) {
                    msg = "【字段名：limit" +
                            "；拒绝的值：" + args[i] +
                            "；错误信息：非数值型（整型）" +
                            "；数据类型：" + parameters[i].getType() + "】";
                    validResult.add(msg);
                }
            } else if (args[i] instanceof BindingResult) { // 入参是否定义了类型<BindingResult>
                bindingResult = (BindingResult) args[i];
            }
        }
        // 2.从Validated的参数校验结果<BindingResult>生成错误信息
        if (bindingResult != null) {
            bindingResult.getFieldErrors().forEach(f -> {
                String msg = "【字段名：" + f.getField() +
                        "；拒绝的值：" + f.getRejectedValue() +
                        "；错误信息：" + f.getDefaultMessage() +
                        "；数据类型：" + f.getObjectName() + "】";
                validResult.add(msg);
            });
        }
        // 返回校验结果
        if (!validResult.isEmpty()) {
            return HttpDataResponseResult.isBadRequest("非法请求参数！---->\r\n" + StringUtils.join(validResult, "\r\n"), null);
        }
        // 放行
        return pJoinPoint.proceed();
    }

    /**
     * <p>
     * 对方法入参进行预处理：正则校验；
     * 适用的接口：1）含动态检索
     * </P>
     *
     * @param pJoinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "@annotation(cn.example.demo.common.validation.constraint.ParamRegex)")
    public Object preProcess2(ProceedingJoinPoint pJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) pJoinPoint.getSignature();    // Get Method
        Parameter[] parameters = signature.getMethod().getParameters();  // Get Parameters
        Object[] args = pJoinPoint.getArgs();   // Get Arguments

        List<String> validResult = new ArrayList<>();   // 校验结果
        BindingResult bindingResult = null; // The return Object of Spring Data Validation

        String doubleFormat = "^[+-]?(0|([1-9]\\d*))(\\.\\d+)?$";   // 数值型格式（浮点数）
        String integerFormat = "^0|([1-9]\\d*)$";    // 数值型格式（整型）

        for (int i = 0; i < parameters.length; i++) {
            // 1.检索参数校验
            if (args[i] instanceof RetrievalParam) {
                RetrievalParam retrievalParams = (RetrievalParam) args[i];
                if (retrievalParams != null && !(retrievalParams.getCriteria() == null || retrievalParams.getCriteria().isEmpty())) {
                    //1.过滤不符合条件的条件
                    List<RetrievalCriteria> processedCriteria = retrievalParams.getCriteria().stream()
                            .filter(o -> {
                                if (!o.getCriteria().equals(Criteria.isNull) && !o.getCriteria().equals(Criteria.isNotNull)) {
                                    if (!o.getCriteria().equals(Criteria.between) && !o.getCriteria().equals(Criteria.notBetween)) {
                                        if (StringUtils.isNotEmpty((String) o.getParam())) {
                                            Object param = o.getParam();
                                            if (o.getCriteria().equals(Criteria.in) || o.getCriteria().equals(Criteria.notIn)) {
                                                List<String> l = Arrays.asList(StringUtils.split(param.toString(), ","));
                                                ArrayList l1;
                                                switch (o.getType()) {
                                                    case Integer:
                                                        l1 = new ArrayList<>();
                                                        l.forEach(o1 -> {
                                                            if (o1.matches(integerFormat))
                                                                l1.add(Integer.valueOf(o1));
                                                        });
                                                        o.setParam(l1);
                                                        break;
                                                    case Long:
                                                        l1 = new ArrayList<>();
                                                        l.forEach(o1 -> {
                                                            if (o1.matches(integerFormat))
                                                                l1.add(Long.valueOf(o1));
                                                        });
                                                        o.setParam(l1);
                                                        break;
                                                    case Short:
                                                        l1 = new ArrayList<>();
                                                        l.forEach(o1 -> {
                                                            if (o1.matches(integerFormat))
                                                                l1.add(Short.valueOf(o1));
                                                        });
                                                        o.setParam(l1);
                                                        break;
                                                    case BigDecimal:
                                                        l1 = new ArrayList<>();
                                                        l.forEach(o1 -> {
                                                            if (o1.matches(doubleFormat))
                                                                l1.add(new BigDecimal(o1));
                                                        });
                                                        o.setParam(l1);
                                                        break;
                                                    case Double:
                                                        l1 = new ArrayList<>();
                                                        l.forEach(o1 -> {
                                                            if (o1.matches(doubleFormat))
                                                                l1.add(Double.valueOf(o1));
                                                        });
                                                        o.setParam(l1);
                                                        break;
                                                    case Float:
                                                        l1 = new ArrayList<>();
                                                        l.forEach(o1 -> {
                                                            if (o1.matches(doubleFormat))
                                                                l1.add(Float.valueOf(o1));
                                                        });
                                                        o.setParam(l1);
                                                        break;
                                                    case Date:
                                                        l1 = new ArrayList<>();
                                                        l.forEach(o1 -> l1.add(DateAgeUtils.stringToDate(o1)));
                                                        o.setParam(l1);
                                                        break;
                                                    case String:
                                                        o.setParam(l);
                                                }
                                            } else {
                                                switch (o.getType()) {
                                                    case Integer:
                                                        if (param.toString().matches(integerFormat))
                                                            o.setParam(Integer.valueOf(param.toString()));
                                                        break;
                                                    case Long:
                                                        if (param.toString().matches(integerFormat))
                                                            o.setParam(Long.valueOf(param.toString()));
                                                        break;
                                                    case Short:
                                                        if (param.toString().matches(integerFormat))
                                                            o.setParam(Short.valueOf(param.toString()));
                                                    case BigDecimal:
                                                        if (param.toString().matches(doubleFormat))
                                                            o.setParam(new BigDecimal(param.toString()));
                                                        break;
                                                    case Double:
                                                        if (param.toString().matches(doubleFormat))
                                                            o.setParam(Double.valueOf(param.toString()));
                                                        break;
                                                    case Float:
                                                        if (param.toString().matches(doubleFormat))
                                                            o.setParam(Float.valueOf(param.toString()));
                                                        break;
                                                    case Date:
                                                        o.setParam(DateAgeUtils.stringToDate(param.toString()));
                                                }
                                            }
                                            return true;
                                        }
                                    } else if (StringUtils.isNotEmpty((String) o.getParam()) && StringUtils.isNotEmpty((String) o.getParam2())) {
                                        Object param = o.getParam();
                                        Object param2 = o.getParam2();
                                        boolean b1 = param.toString().matches(integerFormat) && param2.toString().matches(integerFormat);
                                        boolean b2 = param.toString().matches(doubleFormat) && param2.toString().matches(doubleFormat);
                                        switch (o.getType()) {
                                            case Integer:
                                                if (b1) {
                                                    o.setParam(Integer.valueOf(param.toString()));
                                                    o.setParam2(Integer.valueOf(param2.toString()));
                                                }
                                                break;
                                            case Long:
                                                if (b1) {
                                                    o.setParam(Long.valueOf(param.toString()));
                                                    o.setParam2(Long.valueOf(param2.toString()));
                                                }
                                                break;
                                            case Short:
                                                if (b1) {
                                                    o.setParam(Short.valueOf(param.toString()));
                                                    o.setParam2(Short.valueOf(param2.toString()));
                                                }
                                                break;
                                            case BigDecimal:
                                                if (b2) {
                                                    o.setParam(new BigDecimal(param.toString()));
                                                    o.setParam2(new BigDecimal(param2.toString()));
                                                }
                                                break;
                                            case Double:
                                                if (b2) {
                                                    o.setParam(Double.valueOf(param.toString()));
                                                    o.setParam2(Double.valueOf(param2.toString()));
                                                }
                                                break;
                                            case Float:
                                                if (b2) {
                                                    o.setParam(Float.valueOf(param.toString()));
                                                    o.setParam2(Float.valueOf(param2.toString()));
                                                }
                                                break;
                                            case Date:
                                                o.setParam(DateAgeUtils.stringToDate(param.toString()));
                                                o.setParam2(DateAgeUtils.stringToDate(param2.toString()));
                                        }
                                        return true;
                                    }

                                    return false;
                                } else {
                                    return true;
                                }
                            })
                            .collect(Collectors.toList());
                    // 重新赋值条件
                    retrievalParams.setCriteria(processedCriteria);
                }
            } else if (args[i] instanceof BindingResult) { // 入参是否定义了类型<BindingResult>
                bindingResult = (BindingResult) args[i];
            }
        }
        // 2.从Validated的参数校验结果<BindingResult>生成错误信息
        if (bindingResult != null) {
            bindingResult.getFieldErrors().forEach(f -> {
                String msg = "【字段名：" + f.getField() +
                        "；拒绝的值：" + f.getRejectedValue() +
                        "；错误信息：" + f.getDefaultMessage() +
                        "；数据类型：" + f.getObjectName() + "】";
                validResult.add(msg);
            });
        }
        // 返回校验结果
        if (!validResult.isEmpty()) {
            return HttpDataResponseResult.isBadRequest("非法请求参数！---->\r\n" + StringUtils.join(validResult, "\r\n"), null);
        }
        // 放行
        return pJoinPoint.proceed();
    }

    /**
     * <p>
     * 拦截所有查询接口，对请求参数进行统一逻辑处理
     * </P>
     *
     * @param joinPoint
     */
    @Before(value = "execution(public * cn.example.demo.modules.sys.controller..*Query*.*(..))")
    public void preProcess3(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof List) {
                // 去除空值或空字符串
                ((List) arg).removeIf(s -> s == null ? true : s.equals(""));
            }
        }
    }
}
