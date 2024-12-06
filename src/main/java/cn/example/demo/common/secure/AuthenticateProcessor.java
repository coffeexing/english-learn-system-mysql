package cn.example.demo.common.secure;

import cn.example.demo.common.dictionary.AuthCenterBaseInfo;
import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import cn.example.demo.common.tools.obj.ServletUtils;
import com.github.benmanes.caffeine.cache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户认证处理器
 * </p>
 *
 * @author Lizuxian
 * @create 2020-11-19 10:45
 */
@Component
@Aspect
@Order(2)
public class AuthenticateProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateProcessor.class);

    @Resource
    private AuthCenterBaseInfo authCenterBaseInfo;
    @Resource(name = "sessionCache")
    private Cache sessionCache;

    /**
     * <p>
     * 拦截【标记为认证的处理器】
     * </P>
     */
    @Pointcut(value = "@annotation(cn.example.demo.common.secure.authority.AuthEnable)")
    private void pointCutApiMethod() {
    }

    /**
     * <p>
     * 认证处理逻辑
     * </P>
     *
     * @param pJoinPoint
     * @return
     */
    @Around(value = "pointCutApiMethod()")
    public Object auth(ProceedingJoinPoint pJoinPoint) throws Throwable {
        // Request & Response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 从 Cookie 获取 Token
        String tokenName = authCenterBaseInfo.getTokenName();
        Cookie cookie = ServletUtils.getCookieByName(request, tokenName);
        if (cookie != null) {
            String tokenValue = cookie.getValue();
            if (StringUtils.isNotEmpty(tokenValue)) {
                // 如果本地缓存会话存在，放行
                Object obj = sessionCache.getIfPresent(tokenValue);
                if (obj != null) {
                    request.setAttribute(tokenName, obj);
                    return pJoinPoint.proceed();
                }
            }
        }

        // 拒绝访问
        MethodSignature signature = (MethodSignature) pJoinPoint.getSignature();    // Get Method
        Class returnType = signature.getReturnType();   // Get ReturnType
        if (HttpResponseResult.class.isAssignableFrom(returnType)) {  // 若返回类型为 API 结构体：HttpResponseResult，含子类
            return HttpDataResponseResult.isUnauthorized("拒绝访问，未认证的用户，请先进行登录！", null);
        }

        return "error/403";
    }

    @Before(value = "execution(public * cn.example.demo..controller..*Controller.*(..))")
    public void sessionInfoChain(JoinPoint joinPoint) {
        // Request & Response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String tokenName = authCenterBaseInfo.getTokenName();
        Cookie cookie = ServletUtils.getCookieByName(request, tokenName);
        if (cookie != null) {
            String tokenValue = cookie.getValue();
            if (StringUtils.isNotEmpty(tokenValue)) {
                Object obj = sessionCache.getIfPresent(tokenValue);
                if (obj != null) {
                    request.setAttribute(tokenName, obj);
                }
            }
        }
    }
}
