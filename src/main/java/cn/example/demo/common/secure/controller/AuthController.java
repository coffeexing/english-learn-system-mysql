package cn.example.demo.common.secure.controller;

import cn.example.demo.common.dictionary.AuthCenterBaseInfo;
import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import cn.example.demo.common.secure.dto.UserLoginDto;
import cn.example.demo.common.tools.obj.ServletUtils;
import cn.example.demo.modules.sys.model.entity.SysUser;
import cn.example.demo.modules.sys.service.UserService;
import com.github.benmanes.caffeine.cache.Cache;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * <p>
 * 认证控制器
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-25 17:28
 */
@Api(tags = {"认证、授权控制处理器"})
@RestController
public class AuthController {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Resource
    private AuthCenterBaseInfo authCenterBaseInfo;
    @Resource
    private UserService userService;
    @Resource(name = "sessionCache")
    private Cache sessionCache;

    /**
     * <p>
     * 用户登录认证
     * </P>
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "用户登录认证")
    @PostMapping(value = "login/auth")
    public HttpResponseResult accountAuth(HttpServletRequest request, HttpServletResponse response, @Validated UserLoginDto loginDto, BindingResult result) {
        if (CaptchaUtil.ver(loginDto.getCaptcha(), request)) {
            SysUser user = userService.findUserByUsername(loginDto.getUsername());
            if (user != null) {
                // 密码验证
                if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                    String tokenName = authCenterBaseInfo.getTokenName();
                    String token = UUID.randomUUID().toString();

                    Cookie cookie = ServletUtils.getCookieByName(request, tokenName);
                    if (cookie != null) {
                        sessionCache.invalidate(cookie.getValue());
                        cookie.setValue(token);
                    } else {
                        cookie = new Cookie(tokenName, token);
                    }

                    cookie.setDomain(request.getServerName());
                    cookie.setPath("/");
                    cookie.setMaxAge(7200);
                    response.addCookie(cookie);

                    // 保存数据到服务端本地缓存中
                    sessionCache.put(token, user);

                    return HttpDataResponseResult.isSuccess("登录成功！", null);
                }
            }
            return HttpDataResponseResult.isBadRequest("用户名或密码不正确！", null);
        }

        return HttpDataResponseResult.isBadRequest("验证码不正确！", null);
    }

    /**
     * <p>
     * 用户登出
     * </P>
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "用户登出")
    @PostMapping(value = "logout")
    public HttpResponseResult accountLogout(HttpServletRequest request) {
        Cookie cookie = ServletUtils.getCookieByName(request, authCenterBaseInfo.getTokenName());
        if (cookie != null) {
            sessionCache.invalidate(cookie.getValue());
        }
        return HttpDataResponseResult.isSuccess("已退出系统！", null);
    }
}
