package cn.example.demo.modules.sys.model.dto;

import cn.example.demo.common.validation.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

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
public class SysRoleMenuDto {
    @NotNull(message = "角色主键不能为空！", groups = Update.class)
    private Integer roleId;    // 主键
    @NotNull(message = "授权菜单不能为空！", groups = Update.class)
    private List<Integer> menuIds;
}
