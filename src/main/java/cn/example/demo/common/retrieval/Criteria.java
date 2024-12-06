package cn.example.demo.common.retrieval;

/**
 * <p>
 * 检索条件关键词
 * </p>
 *
 * @author Lizuxian
 * @create 2021-01-14 17:14
 */
public enum Criteria {
    equal("=", "等于"),
    notEqual("<>", "不等于"),
    greater(">", "大于"),
    less("<", "小于"),
    greaterOrEqual(">=", "大于或等于"),
    lessOrEqual("<=", "小于或等于"),
    between("BETWEEN", "介于...之间"),
    notBetween("NOT BETWEEN", "非介于...之间"),
    like("LIKE", "包含某字符"),
    notLike("NOT LIKE", "非包含某字符"),
    in("IN", "值域内"),
    notIn("NOT IN", "非值域内"),
    exists("EXISTS", "存在..."),
    notExists("NOT EXISTS", "非存在..."),
    isNull("IS NULL", "为空"),
    isNotNull("IS NOT NULL", "非空");

    private final String code;
    private final String name;

    Criteria(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
