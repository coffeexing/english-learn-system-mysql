package cn.example.demo.modules.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统资源数据结构
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-25 0:04
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysResource {
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;    // 主键
    private String resourceName;    // 资源名称
    private String uri; // 资源URI
    private String type;    // 资源类型
    private String operation; // 资源操作
    private String description; // 描述信息
    private String button; // 客户端操作按钮
}
