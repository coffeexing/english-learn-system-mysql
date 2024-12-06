package cn.example.demo.modules.sys.model.entity;

import cn.example.demo.common.model.tree.SimpleTreeNode;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统菜单数据结构
 * </p>
 *
 * @author Lizuxian
 * @create 2021-03-24 16:03
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysMenu extends SimpleTreeNode {
    private String title;   // 标题
    private Short type; // 菜单类型: {0:菜单目录, 1:菜单}
    private String openType;    // 打 开 类 型: {"_iframe"}
    private String icon;    // 图标
    private String href;    // 跳转路径
    @TableField(value = "`rank`")
    private Integer rank;   // 同级节点次序

    @TableField(exist = false)
    private Integer id;
    @TableField(exist = false)
    private Integer parentId;

    @TableField(exist = false)
    private String checkArr = "0";  // 计算列 提供给前端组件
}
