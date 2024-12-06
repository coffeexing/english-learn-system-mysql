package cn.example.demo.common.secure.controller;

import cn.example.demo.common.dictionary.AuthCenterBaseInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 * 公共视图页（不需要登录认证）
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-24 12:05
 */
@Controller("commonViewController")
@Api(tags = {"通用模板视图"})
public class ViewController {
    @Autowired
    private AuthCenterBaseInfo authCenterBaseInfo;

    /**
     * <p>
     * 登录页
     * </P>
     *
     * @return
     */
    @GetMapping(value = "login")
    public Object loginPage() {
        return "system/login";
    }

    @GetMapping(value = "register")
    public Object registerPage() {
        return "system/register";
    }

    /**
     * Describe:无权限页面
     * Return:返回403页面
     */
    @GetMapping("error/403")
    public Object noPermission() {
        return "error/403";
    }

    /**
     * Describe:找不到页面
     * Return:返回404页面
     */
    @GetMapping("error/404")
    public Object notFound() {
        return "error/404";
    }

    /**
     * Describe:异常处理页
     * Return:返回500界面
     */
    @GetMapping("error/500")
    public Object onException() {
        return "error/500";
    }
}
