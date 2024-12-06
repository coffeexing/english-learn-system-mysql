package cn.example.demo.common.retrieval;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 排序 Bean
 * </p>
 *
 * @author Lizuxian
 * @create 2021-01-18 15:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortBean {
    private String orderField;
    private String orderType;
}
