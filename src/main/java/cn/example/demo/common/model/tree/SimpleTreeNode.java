package cn.example.demo.common.model.tree;

import cn.example.demo.common.tools.obj.reflect.Immutable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 简易树结构模型，可继承该类派生多种业务类型
 * </p>
 *
 * @author Lizuxian
 * @create 2021-04-25 15:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTreeNode {
    @Immutable
    @TableId(type = IdType.AUTO)
    private Integer node;   // 主键自增
    private Integer parentNode;
    private Boolean isNode;
    private Boolean isDirectory;

    @TableField(exist = false)
    private List<SimpleTreeNode> children;
}
