package cn.example.demo.common.secure.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 *
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-29 1:59
 */
@Data
public class UserLoginDto {
    @NotEmpty(message = "用户名不能为空！")
    private String username;
    @NotEmpty(message = "密码不能为空！")
    private String password;
    @NotEmpty(message = "验证码不能为空！")
    private String captcha;
}
