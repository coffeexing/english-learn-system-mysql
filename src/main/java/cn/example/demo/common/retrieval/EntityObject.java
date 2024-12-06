package cn.example.demo.common.retrieval;

/**
 * <p>
 * 自定义查询：实体对象
 * </p>
 *
 * @author Lizuxian
 * @create 2021-01-14 17:14
 */
public enum EntityObject {
    basy(Object.class, "VIEW_BASY_GENERAL_QUERY", "t1", "主表"),
    detail(Object.class, "TD_HIS_CURE_DETAIL", "t2", "明细表");

    private final Class clazz;
    private final String table;
    private final String tableAlias;
    private final String name;

    EntityObject(Class clazz, String table, String tableAlias, String name) {
        this.clazz = clazz;
        this.table = table;
        this.tableAlias = tableAlias;
        this.name = name;
    }

    public Class getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    public String getTable() {
        return table;
    }

    public String getTableAlias() {
        return tableAlias;
    }
}
