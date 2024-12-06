package cn.example.demo.common.exception;

import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author Lizuxian
 * @create 2021-04-16 17:34
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * <p>
     * 参数绑定校验失败处理器（Json、Form表单）
     * </P>
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public HttpResponseResult methodArgumentBindExceptionHandler(BindException ex) {
        List<String> validResult = getCustomBindErrorMsg(ex.getFieldErrors());
        return HttpDataResponseResult.isBadRequest("非法请求参数！ --->>> \r\n" + StringUtils.join(validResult, "\r\n"), null);
    }

    /**
     * <p>
     * 处理单个参数校验失败抛出的异常（如：String, Integer, Long, Double 等方法入参）
     * </P>
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public HttpResponseResult constraintViolationExceptionHandler(ConstraintViolationException ex) {
        List<String> validResult = new ArrayList<>();   // 校验结果

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        constraintViolations.forEach(f -> {
            String msg = "【字段名：" + f.getPropertyPath() +
                    "；拒绝的值：" + f.getInvalidValue() +
                    "；错误信息：" + f.getMessage() + "】";
            validResult.add(msg);
        });
        return HttpDataResponseResult.isBadRequest("非法请求参数！ --->>> \r\n" + StringUtils.join(validResult, "\r\n"), null);
    }

    /**
     * <p>
     * 站内资源不存在
     * </P>
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler({NoHandlerFoundException.class, FileNotFoundException.class})
    public HttpResponseResult notFoundExceptionHandler(HttpServletRequest request, Exception ex) {
        return HttpDataResponseResult.isNotFound("不存在的接口【" + request.getRequestURI() + "】，请确保请求路径正确！", null);
    }

    /**
     * <p>
     * 其他未知异常
     * </p>
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler({Exception.class})
    public HttpResponseResult unknownExceptionHandler(HttpServletRequest request, Exception ex) {
        // 输出到日志文件
        ExceptionUtils.outputExceptionMsgToLogFile(ex);
        // 保存到数据库 <待完成>

        return HttpDataResponseResult.isInternalError("该接口【" + request.getRequestURI() + "】正在维护中，暂时无法访问！给您带来不便，敬请谅解！", null);
    }

    /**
     * <p>
     * 自定义绑定错误信息
     * </P>
     *
     * @param fieldErrors
     * @return
     */
    protected List<String> getCustomBindErrorMsg(List<FieldError> fieldErrors) {
        List<String> validResult = new ArrayList<>();   // 校验结果

        fieldErrors.forEach(f -> {
            String msg = "【字段名：" + f.getField() +
                    "；拒绝的值：" + f.getRejectedValue() +
                    "；错误信息：" + f.getDefaultMessage() +
                    "；数据类型：" + f.getObjectName() + "】";
            validResult.add(msg);
        });

        return validResult;
    }
}
