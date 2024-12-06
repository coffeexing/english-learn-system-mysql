package cn.example.demo.common.tools;

import cn.example.demo.common.retrieval.PageBean;
import cn.example.demo.common.tools.obj.SimpleStringUtils;
import cn.example.demo.common.tools.obj.reflect.FieldColumn;
import cn.example.demo.common.tools.obj.reflect.FieldStartCount;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 关系型数据库查询工具类
 * </p>
 *
 * @author Lizuxian
 * @create 2020/10/10 17:22
 */
public class QueryServiceUtils {
    /**
     * <p>
     * 封装到 PageBean 对象
     * </P>
     *
     * @param pageBean
     * @param pageResult
     * @return PageBean
     */
    public static <T> PageBean<T> encapsulatePageBean(PageBean<T> pageBean, Page<T> pageResult) {
        // 封装通用字段
        encapsulateCommonPageBean(pageBean, pageResult);
        pageBean.setItems(pageResult.getResult());  // 分页结果
        return pageBean;
    }

    /**
     * <p>
     * 封装到 PageBean 对象
     * </P>
     *
     * @param pageBean
     * @param pageResult
     * @return PageBean
     */
    public static <T> PageBean<T> encapsulatePageBean(PageBean<T> pageBean, Page<T> pageResult, List<T> aggregateItems) {
        // 封装通用字段
        encapsulateCommonPageBean(pageBean, pageResult);
        pageBean.setItems(pageResult.getResult());  // 分页结果
        pageBean.setAggregateItems(aggregateItems);  // 聚合结果
        return pageBean;
    }

    /**
     * <p>
     * 封装到 PageBean 对象
     * </P>
     *
     * @param pageResult
     * @return PageBean
     */
    public static <T> PageBean<T> encapsulatePageBean(PageBean<T> pageBean, Page<?> pageResult, List<T> items, List<T> aggregateItems) {
        // 封装通用字段
        encapsulateCommonPageBean(pageBean, pageResult);
        pageBean.setItems(items);  // 分页结果
        pageBean.setAggregateItems(aggregateItems);  // 聚合结果
        return pageBean;
    }

    /**
     * <p>
     * 生成通用排序语句
     * </P>
     *
     * @param orderField
     * @param orderType
     * @return String
     */
    public static String generateOrderClause(String orderField, String orderType) {
        StringBuilder sb = new StringBuilder("\t" + SimpleStringUtils.toLowerCamel(orderField));
        sb.append("\t" + orderType + "\t");
        sb.append("nulls\tlast\t");
        return sb.toString();
    }

    /**
     * <p>
     * 生成通用排序语句（第二排序字段唯一，防止出现 Oracle【按不唯一字段进行排序 + 分页】时的重复问题）
     * </P>
     *
     * @param orderField
     * @param orderType
     * @return String
     */
    public static String generateOrderClause(String orderField, String orderType, String secondOrderIdField) {
        StringBuilder sb = new StringBuilder(generateOrderClause(orderField, orderType));
        sb.append("," + SimpleStringUtils.toLowerCamel(secondOrderIdField) + "\tasc");
        return sb.toString();
    }

    /**
     * <p>
     * 生成通用排序语句
     * </P>
     *
     * @param orderField
     * @param orderType
     * @return String
     */
    public static String generateOrderClause(String orderField, String orderType, String secondField, String thirdFiled) {
        StringBuilder sb = new StringBuilder(generateOrderClause(orderField, orderType));
        sb.append("," + SimpleStringUtils.toLowerCamel(secondField) + "\tasc");
        sb.append("," + SimpleStringUtils.toLowerCamel(thirdFiled) + "\tasc");
        return sb.toString();
    }

    /**
     * <p>
     * 根据实体类字段注解反射生成聚合SQL语句
     * </P>
     *
     * @param clazz
     * @return
     */
    public static String generateAggregateClause(Class clazz) {
        List<String> sql = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {
            if (f.isAnnotationPresent(FieldStartCount.class)) {
                String camelFieldName;
                if (f.isAnnotationPresent(FieldColumn.class)) {
                    camelFieldName = f.getAnnotation(FieldColumn.class).value();
                } else {
                    camelFieldName = SimpleStringUtils.toLowerCamel(f.getName());
                }
                sql.add("sum(" + camelFieldName + ")as " + camelFieldName);
            }
        }
        return StringUtils.join(sql, ",");
    }

    /**
     * <p>
     * 封装 PageBean 通用字段
     * </P>
     *
     * @param pageBean
     * @param pageResult
     */
    private static void encapsulateCommonPageBean(PageBean pageBean, Page pageResult) {
        pageBean.setTotalNum((int) pageResult.getTotal());  // 总记录数
        pageBean.setTotalPage(pageResult.getPages());   // 总页数
        pageBean.setCurrentPage(pageResult.getPageNum());   // 当前页
        pageBean.setPageSize(pageResult.getPageSize()); // 每页数量
    }
}
