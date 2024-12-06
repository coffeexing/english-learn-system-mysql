package cn.example.demo.modules.sys.model.entity;

import java.util.ArrayList;
import java.util.List;

public class SysDepartmentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysDepartmentExample() {
        oredCriteria = new ArrayList<>();
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andNodeIsNull() {
            addCriterion("node is null");
            return (Criteria) this;
        }

        public Criteria andNodeIsNotNull() {
            addCriterion("node is not null");
            return (Criteria) this;
        }

        public Criteria andNodeEqualTo(Integer value) {
            addCriterion("node =", value, "node");
            return (Criteria) this;
        }

        public Criteria andNodeNotEqualTo(Integer value) {
            addCriterion("node <>", value, "node");
            return (Criteria) this;
        }

        public Criteria andNodeGreaterThan(Integer value) {
            addCriterion("node >", value, "node");
            return (Criteria) this;
        }

        public Criteria andNodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("node >=", value, "node");
            return (Criteria) this;
        }

        public Criteria andNodeLessThan(Integer value) {
            addCriterion("node <", value, "node");
            return (Criteria) this;
        }

        public Criteria andNodeLessThanOrEqualTo(Integer value) {
            addCriterion("node <=", value, "node");
            return (Criteria) this;
        }

        public Criteria andNodeIn(List<Integer> values) {
            addCriterion("node in", values, "node");
            return (Criteria) this;
        }

        public Criteria andNodeNotIn(List<Integer> values) {
            addCriterion("node not in", values, "node");
            return (Criteria) this;
        }

        public Criteria andNodeBetween(Integer value1, Integer value2) {
            addCriterion("node between", value1, value2, "node");
            return (Criteria) this;
        }

        public Criteria andNodeNotBetween(Integer value1, Integer value2) {
            addCriterion("node not between", value1, value2, "node");
            return (Criteria) this;
        }

        public Criteria andParentNodeIsNull() {
            addCriterion("parent_node is null");
            return (Criteria) this;
        }

        public Criteria andParentNodeIsNotNull() {
            addCriterion("parent_node is not null");
            return (Criteria) this;
        }

        public Criteria andParentNodeEqualTo(Integer value) {
            addCriterion("parent_node =", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeNotEqualTo(Integer value) {
            addCriterion("parent_node <>", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeGreaterThan(Integer value) {
            addCriterion("parent_node >", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("parent_node >=", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeLessThan(Integer value) {
            addCriterion("parent_node <", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeLessThanOrEqualTo(Integer value) {
            addCriterion("parent_node <=", value, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeIn(List<Integer> values) {
            addCriterion("parent_node in", values, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeNotIn(List<Integer> values) {
            addCriterion("parent_node not in", values, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeBetween(Integer value1, Integer value2) {
            addCriterion("parent_node between", value1, value2, "parentNode");
            return (Criteria) this;
        }

        public Criteria andParentNodeNotBetween(Integer value1, Integer value2) {
            addCriterion("parent_node not between", value1, value2, "parentNode");
            return (Criteria) this;
        }

        public Criteria andIsNodeIsNull() {
            addCriterion("is_node is null");
            return (Criteria) this;
        }

        public Criteria andIsNodeIsNotNull() {
            addCriterion("is_node is not null");
            return (Criteria) this;
        }

        public Criteria andIsNodeEqualTo(Boolean value) {
            addCriterion("is_node =", value, "isNode");
            return (Criteria) this;
        }

        public Criteria andIsNodeNotEqualTo(Boolean value) {
            addCriterion("is_node <>", value, "isNode");
            return (Criteria) this;
        }

        public Criteria andIsNodeGreaterThan(Boolean value) {
            addCriterion("is_node >", value, "isNode");
            return (Criteria) this;
        }

        public Criteria andIsNodeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_node >=", value, "isNode");
            return (Criteria) this;
        }

        public Criteria andIsNodeLessThan(Boolean value) {
            addCriterion("is_node <", value, "isNode");
            return (Criteria) this;
        }

        public Criteria andIsNodeLessThanOrEqualTo(Boolean value) {
            addCriterion("is_node <=", value, "isNode");
            return (Criteria) this;
        }

        public Criteria andIsNodeIn(List<Boolean> values) {
            addCriterion("is_node in", values, "isNode");
            return (Criteria) this;
        }

        public Criteria andIsNodeNotIn(List<Boolean> values) {
            addCriterion("is_node not in", values, "isNode");
            return (Criteria) this;
        }

        public Criteria andIsNodeBetween(Boolean value1, Boolean value2) {
            addCriterion("is_node between", value1, value2, "isNode");
            return (Criteria) this;
        }

        public Criteria andIsNodeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_node not between", value1, value2, "isNode");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryIsNull() {
            addCriterion("is_directory is null");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryIsNotNull() {
            addCriterion("is_directory is not null");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryEqualTo(Boolean value) {
            addCriterion("is_directory =", value, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryNotEqualTo(Boolean value) {
            addCriterion("is_directory <>", value, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryGreaterThan(Boolean value) {
            addCriterion("is_directory >", value, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_directory >=", value, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryLessThan(Boolean value) {
            addCriterion("is_directory <", value, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryLessThanOrEqualTo(Boolean value) {
            addCriterion("is_directory <=", value, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryIn(List<Boolean> values) {
            addCriterion("is_directory in", values, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryNotIn(List<Boolean> values) {
            addCriterion("is_directory not in", values, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryBetween(Boolean value1, Boolean value2) {
            addCriterion("is_directory between", value1, value2, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andIsDirectoryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_directory not between", value1, value2, "isDirectory");
            return (Criteria) this;
        }

        public Criteria andDeptNameIsNull() {
            addCriterion("dept_name is null");
            return (Criteria) this;
        }

        public Criteria andDeptNameIsNotNull() {
            addCriterion("dept_name is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNameEqualTo(String value) {
            addCriterion("dept_name =", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotEqualTo(String value) {
            addCriterion("dept_name <>", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThan(String value) {
            addCriterion("dept_name >", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThanOrEqualTo(String value) {
            addCriterion("dept_name >=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThan(String value) {
            addCriterion("dept_name <", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThanOrEqualTo(String value) {
            addCriterion("dept_name <=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLike(String value) {
            addCriterion("dept_name like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotLike(String value) {
            addCriterion("dept_name not like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameIn(List<String> values) {
            addCriterion("dept_name in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotIn(List<String> values) {
            addCriterion("dept_name not in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameBetween(String value1, String value2) {
            addCriterion("dept_name between", value1, value2, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotBetween(String value1, String value2) {
            addCriterion("dept_name not between", value1, value2, "deptName");
            return (Criteria) this;
        }

        public Criteria andRankIsNull() {
            addCriterion("rank is null");
            return (Criteria) this;
        }

        public Criteria andRankIsNotNull() {
            addCriterion("rank is not null");
            return (Criteria) this;
        }

        public Criteria andRankEqualTo(Integer value) {
            addCriterion("rank =", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotEqualTo(Integer value) {
            addCriterion("rank <>", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThan(Integer value) {
            addCriterion("rank >", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThanOrEqualTo(Integer value) {
            addCriterion("rank >=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThan(Integer value) {
            addCriterion("rank <", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThanOrEqualTo(Integer value) {
            addCriterion("rank <=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankIn(List<Integer> values) {
            addCriterion("rank in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotIn(List<Integer> values) {
            addCriterion("rank not in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankBetween(Integer value1, Integer value2) {
            addCriterion("rank between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotBetween(Integer value1, Integer value2) {
            addCriterion("rank not between", value1, value2, "rank");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
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

        public String getTypeHandler() {
            return typeHandler;
        }
    }
}
