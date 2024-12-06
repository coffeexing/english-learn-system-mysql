package cn.example.demo.modules.sys.model.dto;

import cn.example.demo.common.validation.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class SysMenuDto {
    @NotEmpty(message = "菜单标题不能为空！")
    private String title;   // 标题
    @NotNull(message = "菜单类型不能为空！")
    private Short type; // 菜单类型: {0:菜单目录, 1:菜单}
    private String openType;    // 打 开 类 型: {"_iframe"}
    private String icon;    // 图标
    private String href;    // 跳转路径
    @NotNull(message = "同级节点次序不能为空！")
    private Integer rank;   // 同级节点次序

    @NotNull(message = "节点ID不能为空！", groups = Update.class)
    private Integer node;
    private Integer parentNode;
}
