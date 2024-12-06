package cn.example.demo.common.retrieval;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 数据库检索条件 DTO
 * </p>
 *
 * @author Lizuxian
 * @create 2021-01-13 11:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalCriteria<T> {
    @NotNull
    private EntityObject entity;
    private FieldType type;
    private CriteriaIsMust isMust;
    private String field;
    private Criteria criteria;
    private T param;
    private T param2;
}
