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
public class SysDeptDto {
    @NotEmpty(message = "部门名称不能为空！")
    private String deptName;
    @NotNull(message = "同级节点次序不能为空！")
    private Integer rank;   // 同级节点次序

    @NotNull(message = "节点ID不能为空！", groups = Update.class)
    private Integer node;
    private Integer parentNode;
}
