package cn.example.demo.modules.sys.model.dto;

import cn.example.demo.common.validation.groups.Insert;
import cn.example.demo.common.validation.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 角色结构体 DTO
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-24 23:21
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDto {
    @NotNull(message = "角色主键不能为空！", groups = Update.class)
    private Integer roleId;    // 主键
    @NotNull(message = "角色名称不能为空！", groups = Insert.class)
    private String role;    // 角色名
    private String description; // 描述信息
    private Short status = 1;   // {1:正常, 0:禁用, -1:删除标记}

}
