package com.pwx.mall.disk.common.util.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class backup_DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        //从自定义的位置获取数据源标识
        return backup_DynamicDataSourceHolder.getDataSource();
    }

}