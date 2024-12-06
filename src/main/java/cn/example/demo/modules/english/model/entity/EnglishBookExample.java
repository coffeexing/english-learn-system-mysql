package cn.example.demo.modules.english.model.entity;

import java.util.ArrayList;
import java.util.List;

public class EnglishBookExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EnglishBookExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBookCodeIsNull() {
            addCriterion("book_code is null");
            return (Criteria) this;
        }

        public Criteria andBookCodeIsNotNull() {
            addCriterion("book_code is not null");
            return (Criteria) this;
        }

        public Criteria andBookCodeEqualTo(String value) {
            addCriterion("book_code =", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeNotEqualTo(String value) {
            addCriterion("book_code <>", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeGreaterThan(String value) {
            addCriterion("book_code >", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeGreaterThanOrEqualTo(String value) {
            addCriterion("book_code >=", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeLessThan(String value) {
            addCriterion("book_code <", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeLessThanOrEqualTo(String value) {
            addCriterion("book_code <=", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeLike(String value) {
            addCriterion("book_code like", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeNotLike(String value) {
            addCriterion("book_code not like", value, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeIn(List<String> values) {
            addCriterion("book_code in", values, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeNotIn(List<String> values) {
            addCriterion("book_code not in", values, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeBetween(String value1, String value2) {
            addCriterion("book_code between", value1, value2, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookCodeNotBetween(String value1, String value2) {
            addCriterion("book_code not between", value1, value2, "bookCode");
            return (Criteria) this;
        }

        public Criteria andBookNameIsNull() {
            addCriterion("book_name is null");
            return (Criteria) this;
        }

        public Criteria andBookNameIsNotNull() {
            addCriterion("book_name is not null");
            return (Criteria) this;
        }

        public Criteria andBookNameEqualTo(String value) {
            addCriterion("book_name =", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameNotEqualTo(String value) {
            addCriterion("book_name <>", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameGreaterThan(String value) {
            addCriterion("book_name >", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameGreaterThanOrEqualTo(String value) {
            addCriterion("book_name >=", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameLessThan(String value) {
            addCriterion("book_name <", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameLessThanOrEqualTo(String value) {
            addCriterion("book_name <=", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameLike(String value) {
            addCriterion("book_name like", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameNotLike(String value) {
            addCriterion("book_name not like", value, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameIn(List<String> values) {
            addCriterion("book_name in", values, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameNotIn(List<String> values) {
            addCriterion("book_name not in", values, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameBetween(String value1, String value2) {
            addCriterion("book_name between", value1, value2, "bookName");
            return (Criteria) this;
        }

        public Criteria andBookNameNotBetween(String value1, String value2) {
            addCriterion("book_name not between", value1, value2, "bookName");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNull() {
            addCriterion("file_path is null");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNotNull() {
            addCriterion("file_path is not null");
            return (Criteria) this;
        }

        public Criteria andFilePathEqualTo(String value) {
            addCriterion("file_path =", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotEqualTo(String value) {
            addCriterion("file_path <>", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThan(String value) {
            addCriterion("file_path >", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("file_path >=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThan(String value) {
            addCriterion("file_path <", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThanOrEqualTo(String value) {
            addCriterion("file_path <=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLike(String value) {
            addCriterion("file_path like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotLike(String value) {
            addCriterion("file_path not like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathIn(List<String> values) {
            addCriterion("file_path in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotIn(List<String> values) {
            addCriterion("file_path not in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathBetween(String value1, String value2) {
            addCriterion("file_path between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotBetween(String value1, String value2) {
            addCriterion("file_path not between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andBasePracticeNumIsNull() {
            addCriterion("base_practice_num is null");
            return (Criteria) this;
        }

        public Criteria andBasePracticeNumIsNotNull() {
            addCriterion("base_practice_num is not null");
            return (Criteria) this;
        }

        public Criteria andBasePracticeNumEqualTo(Integer value) {
            addCriterion("base_practice_num =", value, "basePracticeNum");
            return (Criteria) this;
        }

        public Criteria andBasePracticeNumNotEqualTo(Integer value) {
            addCriterion("base_practice_num <>", value, "basePracticeNum");
            return (Criteria) this;
        }

        public Criteria andBasePracticeNumGreaterThan(Integer value) {
            addCriterion("base_practice_num >", value, "basePracticeNum");
            return (Criteria) this;
        }

        public Criteria andBasePracticeNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("base_practice_num >=", value, "basePracticeNum");
            return (Criteria) this;
        }

        public Criteria andBasePracticeNumLessThan(Integer value) {
            addCriterion("base_practice_num <", value, "basePracticeNum");
            return (Criteria) this;
        }

        public Criteria andBasePracticeNumLessThanOrEqualTo(Integer value) {
            addCriterion("base_practice_num <=", value, "basePracticeNum");
            return (Criteria) this;
        }

        public Criteria andBasePracticeNumIn(List<Integer> values) {
            addCriterion("base_practice_num in", values, "basePracticeNum");
            return (Criteria) this;
        }

        public Criteria andBasePracticeNumNotIn(List<Integer> values) {
            addCriterion("base_practice_num not in", values, "basePracticeNum");
            return (Criteria) this;
        }

        public Criteria andBasePracticeNumBetween(Integer value1, Integer value2) {
            addCriterion("base_practice_num between", value1, value2, "basePracticeNum");
            return (Criteria) this;
        }

        public Criteria andBasePracticeNumNotBetween(Integer value1, Integer value2) {
            addCriterion("base_practice_num not between", value1, value2, "basePracticeNum");
            return (Criteria) this;
        }

        public Criteria andTranslatePracticeNumIsNull() {
            addCriterion("translate_practice_num is null");
            return (Criteria) this;
        }

        public Criteria andTranslatePracticeNumIsNotNull() {
            addCriterion("translate_practice_num is not null");
            return (Criteria) this;
        }

        public Criteria andTranslatePracticeNumEqualTo(Integer value) {
            addCriterion("translate_practice_num =", value, "translatePracticeNum");
            return (Criteria) this;
        }

        public Criteria andTranslatePracticeNumNotEqualTo(Integer value) {
            addCriterion("translate_practice_num <>", value, "translatePracticeNum");
            return (Criteria) this;
        }

        public Criteria andTranslatePracticeNumGreaterThan(Integer value) {
            addCriterion("translate_practice_num >", value, "translatePracticeNum");
            return (Criteria) this;
        }

        public Criteria andTranslatePracticeNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("translate_practice_num >=", value, "translatePracticeNum");
            return (Criteria) this;
        }

        public Criteria andTranslatePracticeNumLessThan(Integer value) {
            addCriterion("translate_practice_num <", value, "translatePracticeNum");
            return (Criteria) this;
        }

        public Criteria andTranslatePracticeNumLessThanOrEqualTo(Integer value) {
            addCriterion("translate_practice_num <=", value, "translatePracticeNum");
            return (Criteria) this;
        }

        public Criteria andTranslatePracticeNumIn(List<Integer> values) {
            addCriterion("translate_practice_num in", values, "translatePracticeNum");
            return (Criteria) this;
        }

        public Criteria andTranslatePracticeNumNotIn(List<Integer> values) {
            addCriterion("translate_practice_num not in", values, "translatePracticeNum");
            return (Criteria) this;
        }

        public Criteria andTranslatePracticeNumBetween(Integer value1, Integer value2) {
            addCriterion("translate_practice_num between", value1, value2, "translatePracticeNum");
            return (Criteria) this;
        }

        public Criteria andTranslatePracticeNumNotBetween(Integer value1, Integer value2) {
            addCriterion("translate_practice_num not between", value1, value2, "translatePracticeNum");
            return (Criteria) this;
        }

        public Criteria andExamNumIsNull() {
            addCriterion("exam_num is null");
            return (Criteria) this;
        }

        public Criteria andExamNumIsNotNull() {
            addCriterion("exam_num is not null");
            return (Criteria) this;
        }

        public Criteria andExamNumEqualTo(Integer value) {
            addCriterion("exam_num =", value, "examNum");
            return (Criteria) this;
        }

        public Criteria andExamNumNotEqualTo(Integer value) {
            addCriterion("exam_num <>", value, "examNum");
            return (Criteria) this;
        }

        public Criteria andExamNumGreaterThan(Integer value) {
            addCriterion("exam_num >", value, "examNum");
            return (Criteria) this;
        }

        public Criteria andExamNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("exam_num >=", value, "examNum");
            return (Criteria) this;
        }

        public Criteria andExamNumLessThan(Integer value) {
            addCriterion("exam_num <", value, "examNum");
            return (Criteria) this;
        }

        public Criteria andExamNumLessThanOrEqualTo(Integer value) {
            addCriterion("exam_num <=", value, "examNum");
            return (Criteria) this;
        }

        public Criteria andExamNumIn(List<Integer> values) {
            addCriterion("exam_num in", values, "examNum");
            return (Criteria) this;
        }

        public Criteria andExamNumNotIn(List<Integer> values) {
            addCriterion("exam_num not in", values, "examNum");
            return (Criteria) this;
        }

        public Criteria andExamNumBetween(Integer value1, Integer value2) {
            addCriterion("exam_num between", value1, value2, "examNum");
            return (Criteria) this;
        }

        public Criteria andExamNumNotBetween(Integer value1, Integer value2) {
            addCriterion("exam_num not between", value1, value2, "examNum");
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