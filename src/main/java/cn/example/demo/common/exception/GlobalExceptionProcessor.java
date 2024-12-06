package cn.example.demo.common.exception;

import cn.example.demo.common.model.response.HttpDataResponseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 全局异常处理器
 * </p>
 *
 * @author Lizuxian
 * @create 2020/10/26 15:59
 */
@Component
@Aspect
@Order(0)
public class GlobalExceptionProcessor {
    /**
     * <p>
     * 日志处理器无法处理的异常在此处理
     * </P>
     *
     * @param jJoinPoint
     * @return
     */
//    @Around(value = "execution(public * cn.example.demo.modules.sys.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint jJoinPoint) {
        try {
            return jJoinPoint.proceed();
        } catch (Throwable t) {
//            t.printStackTrace();
            // 输出到日志文件
            ExceptionUtils.outputExceptionMsgToLogFile(t);
            return HttpDataResponseResult.isInternalError("系统正在维护中，暂时无法访问！给您带来不便，敬请谅解！", null);
        }
    }
}
