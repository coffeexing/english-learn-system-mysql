package cn.example.demo.common.secure.controller;

import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 登录验证码控制器
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-25 16:40
 */
@RestController
@RequestMapping("system/captcha")
public class SysCaptchaController {
    /**
     * 验证码生成
     *
     * @param request  请求报文
     * @param response 响应报文
     */
    @RequestMapping("generate")
    public void generate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }

    /**
     * 异步验证
     *
     * @param request 请求报文
     * @param captcha 验证码
     * @return 验证结果
     */
    @RequestMapping("verify")
    public HttpResponseResult verify(HttpServletRequest request, String captcha) {
        if (CaptchaUtil.ver(captcha, request)) {
            return HttpDataResponseResult.isSuccess("验证成功");
        }
        return HttpDataResponseResult.isBadRequest("验证失败");
    }
}
