package com.application.base.core.datasource.impl.common;

import com.application.base.core.datasource.api.AbstractSpringSqlSessionFactorySupport;
import com.application.base.core.utils.PropertiesUtils;

/**
 * @desc 获得 SessionFactory 的工具类.
 * @author 孤狼
 */
public class DefaultSpringSqlSessionFacotrySupport extends AbstractSpringSqlSessionFactorySupport{
	
    @Override
    protected String getFactoryName(String factoryName) {
    	System.out.println("*********************************************"+factoryName+"*********************************************");
		// ...默认数据源的Id...
    	return PropertiesUtils.getString(factoryName, "sqlSessionFactoryDefault");
    }
    
}
