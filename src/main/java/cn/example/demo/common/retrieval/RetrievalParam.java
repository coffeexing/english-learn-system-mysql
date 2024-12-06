package cn.example.demo.common.retrieval;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 自定义查询请求参数
 * </p>
 *
 * @author Lizuxian
 * @create 2021-01-18 15:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalParam {
    private Boolean exist = true;
    private List<RetrievalCriteria> criteria; // 检索条件
    private List<SortBean> sort;  // 排序参数
    private Integer page;   // 页码
    private Integer limit;  // 条数
}
