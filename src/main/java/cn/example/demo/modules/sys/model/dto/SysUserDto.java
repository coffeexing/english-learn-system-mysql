package cn.example.demo.modules.sys.model.dto;

import cn.example.demo.common.dictionary.DataNormDefinition;
import cn.example.demo.common.validation.groups.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * <p>
 * 用户注册信息表单
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-29 11:59
 */
@Data
public class SysUserDto {
    @NotNull(message = "用户ID不能为空！", groups = Update.class)
    private Integer userId;
    @NotEmpty(message = "用户名不能为空！")
    @Pattern(regexp = DataNormDefinition.username, message = "用户名格式不正确，请按以下规则命名：{1）【2-30】个字符；2）大小写字母、数字、下划线 - '_'、中文的任意组合；}")
    private String username;    // 用户名
    //    @NotEmpty(message = "密码不能为空！", groups = Update.class)
    private String password;    // 密码
    private String realName;    // 姓名
    private String avatar;  // 头像
    private String sex; // 性别
    private String dept;   // 部门
    private String duty; // 单位职务
    private String phone;   // 电话
    private String email;   // 邮箱
    private Short status;   // {1:正常, 0:禁用, -1:删除标记}

    private List<Integer> roleIds; // 绑定角色
    private Integer deptId; // 所属部门
}
