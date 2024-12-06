package cn.example.demo.common.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Caffeine本地缓存配置
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-30 16:59
 */
@Configuration
public class CaffeineConfig {
    /**
     * <p>
     * 登录会话缓存配置
     * </P>
     *
     * @return
     */
    @Bean(name = "sessionCache")
    public Cache<String, Object> sessionCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .build();
    }
}
