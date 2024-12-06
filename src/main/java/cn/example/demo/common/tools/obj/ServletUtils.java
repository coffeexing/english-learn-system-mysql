package cn.example.demo.common.tools.obj;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Servlet 工具类
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-30 17:50
 */
public class ServletUtils {
    /**
     * <p>
     * 根据名字获取Cookie
     * </P>
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
