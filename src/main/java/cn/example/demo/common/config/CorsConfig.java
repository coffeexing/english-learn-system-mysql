package cn.example.demo.common.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <p>
 * 浏览器同源策略配置类
 * </p>
 *
 * @auther lee
 * @data 2020/6/15
 */
@Configuration
public class CorsConfig {
    @Value("${global.cors-config.allowed-origins}")
    private String allowedOrigins;  // 同源策略：允许请求来源地址（配置文件注入值）

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    /**
     * <p>
     * 同源策略配置
     * </P>
     *
     * @return CorsConfiguration
     */
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1.允许指定来源
        String[] origins = StringUtils.split(allowedOrigins, ",");
        for (String o : origins) {
            corsConfiguration.addAllowedOrigin(o);
        }
        // 2.开启Credentials
        corsConfiguration.setAllowCredentials(true);
        // 3.允许任何头
        corsConfiguration.addAllowedHeader("*");
        // 4.允许任何方法（post、get等）
        corsConfiguration.addAllowedMethod("*");

        return corsConfiguration;
    }
}
