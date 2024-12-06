package cn.example.demo.common.log;

import cn.example.demo.common.dictionary.AuthCenterBaseInfo;
import cn.example.demo.common.dictionary.SystemInfo;
import cn.example.demo.common.exception.ExceptionUtils;
import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import cn.example.demo.modules.sys.dao.log.SystemAccessLogMapper;
import cn.example.demo.modules.sys.model.entity.SysUser;
import cn.example.demo.modules.sys.model.entity.log.SsoSystemAccessLog;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 接口访问日志处理器
 * </p>
 *
 * @author Lizuxian
 * @create 2020/10/26 17:01
 */
//@Component
@Aspect
@Order(1)
public class AccessLogProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogProcessor.class);

    @Resource
    private SystemAccessLogMapper accessLogMapper;
    @Resource
    private AuthCenterBaseInfo authCenterBaseInfo;

    /**
     * <p>
     * 拦截除【公共字典表】外的其他所有接口
     * </P>
     */
    @Pointcut(value = "execution(public * cn.example.demo.modules.sys.controller..*.*(..))")
    private void pointCutApiMethod() {
    }

    /**
     * <p>
     * 日志处理逻辑
     * </P>
     *
     * @param pJoinPoint
     * @return
     */
    @Around(value = "pointCutApiMethod()")
    public Object doAround(ProceedingJoinPoint pJoinPoint) {
        SsoSystemAccessLog log = new SsoSystemAccessLog();
        // Request Object
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String requestUrl = request.getRequestURL().toString(); // requestUrl
        MethodSignature signature = (MethodSignature) pJoinPoint.getSignature();    // Get Method
        Method method = signature.getMethod();
        String apiOperation = ""; // 接口操作内容
        if (method.isAnnotationPresent(ApiOperation.class)) {
            apiOperation = method.getAnnotation(ApiOperation.class).value();
        }
        // 日志基本信息赋值
        log.setOperationTime(new Date());
        log.setOperation(apiOperation);
        log.setResourcePath(requestUrl);
        log.setRequestMethod(request.getMethod());
        log.setClientIp(request.getRemoteAddr());
        log.setSystemCode(SystemInfo.ADMIN_DEMO.getCode());
        try {
            // 耗时统计
            long t1 = System.currentTimeMillis();
            Object obj = pJoinPoint.proceed();  // 目标方法执行
            long t2 = System.currentTimeMillis();

            HttpResponseResult result = null;
            if (obj instanceof HttpResponseResult) {
                result = (HttpResponseResult) obj;
            }
            // 生成详细信息
            StringBuilder detailMsg;
            if (result == null) {   // 如果【导出功能】响应成功，返回的 JSON 结果默认为空
                detailMsg = new StringBuilder("【响应结果：OK】；\r\n");
                log.setStatus(HttpStatus.OK.value());
            } else {
                detailMsg = new StringBuilder("【响应结果：### " + result.getMessage() + " ###】；\r\n");
                log.setStatus(result.getStatus());
            }
            detailMsg.append("【调用接口：" + apiOperation + "】；\r\n");
            detailMsg.append("【资源路径：" + requestUrl + "】；\r\n");
            detailMsg.append("【耗时：" + (t2 - t1) + "(ms)】；\r\n");
            detailMsg.append("【请求参数：{\t" + getRequestParam(pJoinPoint.getArgs(), method) + "\t}】");

            log.setConsumeTime(t2 - t1);
            log.setDetailMsg(detailMsg.toString());
            return result;
        } catch (Throwable t) {
            String msg = ExceptionUtils.generateThrowableMsg(t); // 获取异常信息

            log.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            log.setExceptionMsg(msg);

//            t.printStackTrace();
//            LOGGER.error(msg);
            return HttpDataResponseResult.isInternalError(msg, "报错啦！");
        } finally {
            log.setUsername(getUserName(request));  // 获取用户名
            // 保存日志
            accessLogMapper.insert(log);
        }
    }

    /**
     * <p>
     * 从本地会话中获取用户信息
     * </P>
     *
     * @param request
     * @return
     */
    protected String getUserName(HttpServletRequest request) {
        SysUser o = (SysUser) request.getAttribute(authCenterBaseInfo.getTokenName());
        if (o != null) {
            return o.getUsername();
        }
        return "Unauthorized_User";
    }

    /**
     * <p>
     * 获取请求参数
     * </P>
     *
     * @param args
     * @param method
     * @return
     */
    protected String getRequestParam(Object args[], Method method) {
        Parameter[] p = method.getParameters();  // Get Parameters
        List<String> l = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String str = p[i].isAnnotationPresent(ApiParam.class) ? p[i].getAnnotation(ApiParam.class).value() : "";
            l.add(p[i] + "（" + str + "）：" + args[i]);
        }
        return StringUtils.join(l, "; \r\n");
    }
}
