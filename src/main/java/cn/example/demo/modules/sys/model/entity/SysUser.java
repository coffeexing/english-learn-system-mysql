package cn.example.demo.modules.sys.model.entity;

import cn.example.demo.common.tools.obj.reflect.Immutable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统用户数据结构
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-24 16:03
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {
    private static final long serialVersionUID = 1L;
    @Immutable
    @TableId(type = IdType.AUTO)
    private Integer userId;    // 主键
    @Immutable
    private String username;    // 用户名
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;    // 密码
    private String realName;    // 姓名
    private String avatar;  // 头像
    private String sex; // 性别
    private String dept;   // 部门
    private String duty; // 单位职务
    private String phone;   // 电话
    private String email;   // 邮箱
    private String idNumber;   // 身份证号
    @Immutable
    private Date createTime;    // 创建时间
    private Date lastModifyTime;    // 最后修改时间
    private Date lastLoginTime; // 最后一次登录时间
    private Short status;   // {1:正常, 0:禁用, -1:删除标记}

    @TableField(exist = false)
    private List<Integer> roleIds;

    @TableField(exist = false)
    private Integer deptId;

    /**
     * <p>
     * 用户新增构造器
     * </P>
     *
     * @param current
     */
    public SysUser(Date current, Short status) {
        this.createTime = current;
        this.lastModifyTime = createTime;
        this.status = status;
    }
}
