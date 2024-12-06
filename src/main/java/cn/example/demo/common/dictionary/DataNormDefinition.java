package cn.example.demo.common.dictionary;

/**
 * <p>
 * 系统数据规范定义
 * </p>
 *
 * @author Lizuxian
 * @create 2020-12-29 17:01
 */
public class DataNormDefinition {
    /**
     * <p>
     * 用户名。{1）【2-30】个字符；2）大小写字母、数字、下划线 - '_'、中文的任意组合；}
     * </P>
     */
    public static final String username = "^[A-Za-z0-9_\\u4e00-\\u9fa5]{2,30}";

    /**
     * <p>
     * 角色名
     * </P>
     */
    public static final String roleName = "^[A-Za-z0-9_\\u4e00-\\u9fa5]{2,30}";

    /**
     * <p>
     * 浮点数正则表达式
     * </P>
     */
    public static final String doubleFormat = "^[+-]?(0|([1-9]\\d*))(\\.\\d+)?$";

    /**
     * <p>
     * 整型正则表达式
     * </P>
     */
    public static final String integerFormat = "^0|([1-9]\\d*)$";
}
