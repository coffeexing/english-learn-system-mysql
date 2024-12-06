package cn.example.demo.common.filter;

import javax.servlet.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 统一字符集过滤器
 * </p>
 *
 * @author Lizuxian
 * @create 2020/6/1 17:22
 */
public class GlobalCharsetFilter implements Filter {
    String filterName;

    @Override
    public void init(FilterConfig filterConfig) {
        filterName = filterConfig.getFilterName();
        System.out.println(filterName + " 过滤器已初始化！");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println(filterName + " 过滤器已销毁！");
    }
}
