package cn.example.demo.common.retrieval;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 检索条件构造器
 * </p>
 *
 * @author Lizuxian
 * @create 2021-02-03 9:34
 */
public class RetrievalExample {
    private String table;
    private String tableAlias;
    private List<OrCriteria> orCriteria;

    public RetrievalExample(String table, String tableAlias) {
        this.table = table;
        this.tableAlias = tableAlias;
        this.orCriteria = new ArrayList<>();
    }

    /**
     * <p>
     * 根据表别名生成单个查询条件
     * </P>
     *
     * @param tableAlias
     * @param criteria
     * @return
     */
    protected static Criterion generateCriterion(String tableAlias, RetrievalCriteria criteria) {
        String condition = tableAlias + "." + criteria.getField() + " " + criteria.getCriteria().getCode();
        if (criteria.getCriteria().equals(Criteria.isNull) || criteria.getCriteria().equals(Criteria.isNotNull)) {
            return new Criterion(condition);
        } else if (criteria.getCriteria().equals(Criteria.between) || criteria.getCriteria().equals(Criteria.notBetween)) {
            return new Criterion(condition, criteria.getParam(), criteria.getParam2());
        } else if (criteria.getCriteria().equals(Criteria.like) || criteria.getCriteria().equals(Criteria.notLike)) {
            return new Criterion(condition, "%" + criteria.getParam() + "%");
        } else {
            return new Criterion(condition, criteria.getParam());
        }
    }

    /**
     * <p>
     * 生成所有条件对象
     * </P>
     *
     * @param criteria
     * @param associateClause
     * @return
     */
    public RetrievalExample addAllCriterion(List<RetrievalCriteria> criteria, String associateClause) {
        criteria.get(0).setIsMust(CriteriaIsMust.and);
        // 初始化主查询与子查询对象
        OrCriteria orCriteria = new OrCriteria();
        SubQuery subQuery1 = new SubQuery(EntityObject.detail.getTable(), EntityObject.detail.getTableAlias(), associateClause, true);

        for (int i = 0; i < criteria.size(); i++) {
            RetrievalCriteria c = criteria.get(i);
            // 往 orCriteria 集合添加上一个 or 条件，并生成下一个 or 条件对象
            if (c.getIsMust().equals(CriteriaIsMust.or)) {
                if (orCriteria.isSubQuery()) {
                    orCriteria.getSubQueries().add(subQuery1);
                    subQuery1 = null;
                }
                this.orCriteria.add(orCriteria);
                orCriteria = new OrCriteria();
            }

            // 添加主表条件
            if (c.getEntity().equals(EntityObject.basy)) {
                orCriteria.getCriteria().add(generateCriterion(tableAlias, c));
            } else if (c.getEntity().equals(EntityObject.detail)) {
                orCriteria.setSubQuery(true);
                // 添加子查询条件
                if (subQuery1 == null) {
                    subQuery1 = new SubQuery(EntityObject.detail.getTable(), EntityObject.detail.getTableAlias(), associateClause, true);
                }
                subQuery1.criteria.add(generateCriterion(EntityObject.detail.getTableAlias(), c));
            }
        }
        // 往 orCriteria 集合添加最后一个 or 条件
        if (orCriteria.isSubQuery()) {
            orCriteria.getSubQueries().add(subQuery1);
        }
        this.orCriteria.add(orCriteria);
        return this;
    }

    /**
     * 主查询对象
     */
    public static class OrCriteria {
        private boolean isSubQuery;
        private List<Criterion> criteria;
        private List<SubQuery> subQueries;

        public OrCriteria() {
            this.isSubQuery = false;
            this.criteria = new ArrayList<>();
            this.subQueries = new ArrayList<>();
        }

        public boolean isSubQuery() {
            return this.isSubQuery;
        }

        public void setSubQuery(boolean subQuery) {
            this.isSubQuery = subQuery;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        public void setCriteria(List<Criterion> criteria) {
            this.criteria = criteria;
        }

        public List<SubQuery> getSubQueries() {
            return subQueries;
        }

        public void setSubQueries(List<SubQuery> subQueries) {
            this.subQueries = subQueries;
        }
    }

    /**
     * 子查询对象
     */
    public static class SubQuery {
        private String table;
        private String tableAlias;
        private boolean isExist;
        private List<Criterion> criteria;

        public SubQuery(String table, String tableAlias, String associateClause, boolean isExist) {
            this.table = table;
            this.tableAlias = tableAlias;
            this.isExist = isExist;
            this.criteria = new ArrayList<>();

            // 两表关联语句
            criteria.add(new Criterion(associateClause));
        }
    }

    /**
     * 单个条件对象
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }
    }
}
