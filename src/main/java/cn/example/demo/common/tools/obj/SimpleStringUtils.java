package cn.example.demo.common.tools.obj;

import java.util.UUID;

/**
 * <p>
 * 简单字符串工具类
 * </p>
 *
 * @author Lizuxian
 * @create 2020/6/29 16:01
 */
public class SimpleStringUtils {
    /**
     * <p>
     * 首字母大写
     * </P>
     *
     * @param str
     * @return
     */
    public static String firstCharUpperCase(String str) {
        char[] c = str.toCharArray();
        c[0] -= 32;
        return String.valueOf(c);
    }

    /**
     * <p>
     * 首字母小写
     * </P>
     *
     * @param str
     * @return
     */
    public static String firstCharLowerCase(String str) {
        char[] c = str.toCharArray();
        c[0] += 32;
        return String.valueOf(c);
    }

    /**
     * <p>
     * 转驼峰格式
     * </P>
     *
     * @param str
     * @return String
     */
    public static String toLowerCamel(String str) {
        if (str == null || str.trim().equals("")) {
            return "";
        }
        // 如果纯大写，不作转换
        if (str.matches("[A-Z]*")) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getRandomId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
