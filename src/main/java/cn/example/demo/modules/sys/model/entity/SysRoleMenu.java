package cn.example.demo.modules.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 角色-菜单映射关系
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-24 23:35
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleMenu {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private Integer roleId;
    private Integer menuId;
}