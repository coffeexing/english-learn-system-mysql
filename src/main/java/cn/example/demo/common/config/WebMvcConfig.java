package cn.example.demo.common.config;

import cn.example.demo.common.filter.GlobalCharsetFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * MVC组件配置
 * </p>
 *
 * @author Lizuxian
 * @create 2020/6/1 17:18
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 全局统一字符集过滤器
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean<GlobalCharsetFilter> filterRegistrationBean = new FilterRegistrationBean(new GlobalCharsetFilter());
        filterRegistrationBean.addUrlPatterns("*");
        return filterRegistrationBean;
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/admin/**", "/assets/**", "/component/**", "/swagger-ui.html", "/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resosurces/", "classpath:/static/", "classpath:/resources/", "classpath:/public/", "classpath:/META-INF/resources/webjars/");
//    }
}
