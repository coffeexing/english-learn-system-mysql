package cn.example.demo.modules.sys.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 用户注册信息表单
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-29 11:59
 */
@Data
public class SysUserBaseDto {
    @NotEmpty(message = "姓名不能设置为空！")
    private String realName;    // 姓名
    private String sex; // 性别
    private String phone;   // 电话
    private String email;   // 邮箱
}
