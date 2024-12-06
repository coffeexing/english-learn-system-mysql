package cn.example.demo.common.config.datasource;

import org.springframework.util.Assert;

/**
 * <p>
 * 数据源类型定义
 * </p>
 *
 * @author Lizuxian
 * @create 2021-02-25 16:48
 */
public class DataSourceType {
    // 使用 ThreadLocal 保证线程安全
    private static final ThreadLocal<DataBase> DB = new ThreadLocal<>();

    // 获取数据源类型
    public static DataBase getDataBase() {
        DataBase dataBase = DB.get() == null ? DataBase.ORACLE : DB.get();
        System.err.println("【当前数据源】：" + dataBase);
        return dataBase;
    }

    // 往当前线程里设置数据源类型
    public static void setDataBase(DataBase dataBase) {
        Assert.notNull(dataBase, "数据库不能为空!");
        DB.set(dataBase);
        System.err.println("【数据源改为】：" + dataBase);
    }

    // 清空数据类型
    public static void clearDataBase() {
        DB.remove();
    }

    public enum DataBase {
        ORACLE, MYSQL
    }
}
