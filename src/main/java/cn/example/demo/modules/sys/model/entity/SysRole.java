package cn.example.demo.modules.sys.model.entity;

import cn.example.demo.common.tools.obj.reflect.Immutable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统角色数据结构
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-24 23:21
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRole {
    @Immutable
    private Integer roleId;    // 主键
    private String role;    // 角色名
    private String description; // 描述信息
    private Short status = 1;   // {1:正常, 0:禁用, -1:删除标记}
}
