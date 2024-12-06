package cn.example.demo.common.tools.obj.reflect;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 实体类工具类
 * </p>
 *
 * @author Lizuxian
 * @create 2020/6/29 11:12
 */
public class EntityUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityUtils.class);

    /**
     * <p>
     * 实体类映射转换
     * </P>
     *
     * @param originEntity
     * @param targetEntity
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T, U> U entityConvert(T originEntity, U targetEntity, boolean isImmutable) {
        Class<?> originClazz = originEntity.getClass();
        List<Field> originFields = Arrays.asList(originClazz.getDeclaredFields());
        // 求交集，获取要赋值的目标字段
        List<Field> targetFields = Arrays.asList(targetEntity.getClass().getDeclaredFields())
                .stream()
                .filter(o -> originFields.stream().anyMatch(o1 -> o1.getName().equals(o.getName())))
                .collect(Collectors.toList());
        // 目标字段赋值
        for (Field f : targetFields) {
            try {
                // 根据相同字段名获取源实体类的字段
                Field originField = originClazz.getDeclaredField(f.getName());
                // 跳过不可变更的字段
                if (isImmutable) {
                    if (f.isAnnotationPresent(Immutable.class)) {
                        continue;
                    }
                }
                // 如果字段类型兼容，则将值注入目标对象
                if (f.getType().equals(originField.getType())) {
                    originField.setAccessible(true);
                    Object o = originField.get(originEntity);
                    // 跳过null或空字符的字段
                    if (o == null) {
                        continue;
                    }
                    if (f.getType().equals(String.class)) {
                        if (StringUtils.isEmpty((String) o)) {
                            continue;
                        }
                    }
                    f.setAccessible(true);
                    f.set(targetEntity, originField.get(originEntity));
                }
            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//                LOGGER.info("(异常）没有此字段: " + e.getMessage());
            } catch (IllegalAccessException e) {
                LOGGER.error("非法访问异常: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return targetEntity;
    }

    /**
     * <p>
     * 是否存在某字段
     * </P>
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static boolean isExistFiled(Class clazz, String fieldName) {
        try {
            clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return false;
        }
        return true;
    }
}
