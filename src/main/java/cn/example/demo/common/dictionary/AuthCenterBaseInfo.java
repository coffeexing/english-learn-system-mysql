package cn.example.demo.common.dictionary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 单点登录-认证中心通用参数，值由配置文件注入
 * </p>
 *
 * @author Lizuxian
 * @create 2020/6/3 15:06
 */
@Component
public class AuthCenterBaseInfo {
    @Value("${global.secure-center.port}")
    private String port;

    @Value("${global.secure-center.token-name}")
    private String tokenName;

    @Value("${global.secure-center.token-secure-uri}")
    private String tokenAuthURI;

    @Value("${global.secure-center.login-uri}")
    private String loginURI;

    public String getPort() {
        return port;
    }

    public String getTokenName() {
        return tokenName;
    }

    public String getTokenAuthURI() {
        return tokenAuthURI;
    }

    public String getLoginURI() {
        return loginURI;
    }
}
