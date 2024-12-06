package cn.example.demo.common.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * <p>
 * 数据源动态路由
 * </p>
 *
 * @author Lizuxian
 * @create 2021-02-25 16:47
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceType.getDataBase();
    }
}
