package cn.example.demo.common.filter;

import cn.example.demo.common.dictionary.AuthCenterBaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *
 * </p>
 *
 * @author Lizuxian
 * @create 2020/6/4 15:12
 */
public class ReferrerUrlAnalyzeInterceptor implements HandlerInterceptor {
    @Autowired
    private AuthCenterBaseInfo authCenterBaseInfo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String referrerUrl = request.getParameter("referrerUrl");
        if (referrerUrl == null || referrerUrl.isEmpty()) {
//            referrerUrl = Base64Utils.e(authCenterBaseInfo.getDefaultReferrerUrl());
            request.setAttribute("referrerUrl", referrerUrl);
            return true;
        }
//        referrerUrl = Base64Utils.decodeUrl(referrerUrl);
//        if (referrerUrl == null) {
//            referrerUrl = authCenterBaseInfo.getDefaultReferrerUrl();
//            request.setAttribute("referrerUrl", referrerUrl);
//            return true;
//        }
        request.setAttribute("referrerUrl", referrerUrl);
//        request.setAttribute("referrerUrl", authCenterBaseInfo.getDefaultReferrerUrl());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
