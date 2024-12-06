package cn.example.demo.common.retrieval;

/**
 * <p>
 * 条件是否必须
 * </p>
 *
 * @author Lizuxian
 * @create 2021-01-14 17:14
 */
public enum CriteriaIsMust {
    and("AND", "必须"),
    or("OR", "非必须");

    private final String code;
    private final String name;

    CriteriaIsMust(String code, String name) {
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
