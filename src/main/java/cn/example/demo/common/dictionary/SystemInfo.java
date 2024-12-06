package cn.example.demo.common.dictionary;

/**
 * <p>
 * 【平台所有系统】字典信息
 * </p>
 *
 * @author Lizuxian
 * @create 2020-11-16 17:06
 */
public enum SystemInfo {
    ADMIN_DEMO("SYS001_ADMIN", "后台管理系统");

    private final String code;
    private final String name;

    SystemInfo(String code, String name) {
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
