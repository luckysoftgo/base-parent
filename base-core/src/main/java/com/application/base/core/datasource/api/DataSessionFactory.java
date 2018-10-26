package com.application.base.core.datasource.api;

import com.application.base.core.datasource.session.DataSession;

/**
 * @desc 获得数据库的数据源
 * @author 孤狼
 */
public interface DataSessionFactory {
	
	/**
	 * 通过sourceName获得连接对象
	 * @param dataSourceName
	 * @return
	 */
    DataSession getDaoByDataSourceName(String dataSourceName);
    
}

