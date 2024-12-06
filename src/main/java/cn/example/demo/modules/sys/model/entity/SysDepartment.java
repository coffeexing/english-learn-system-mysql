package cn.example.demo.modules.sys.model.entity;

import cn.example.demo.common.model.tree.SimpleTreeNode;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统部门-数据结构
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-24 16:03
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysDepartment extends SimpleTreeNode {
    private String deptName;   // 部门名称
    @TableField(value = "`rank`")
    private Integer rank;   // 同级节点次序
}
