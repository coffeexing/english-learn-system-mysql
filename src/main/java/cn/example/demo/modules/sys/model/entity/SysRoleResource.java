package cn.example.demo.modules.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统角色-资源映射关系
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-25 0:17
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleResource {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private Integer roleId;
    private Integer resourceId;
}
