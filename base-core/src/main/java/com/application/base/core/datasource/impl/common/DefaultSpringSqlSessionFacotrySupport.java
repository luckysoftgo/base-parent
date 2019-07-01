package com.application.base.core.datasource.impl.common;

import com.application.base.core.datasource.api.AbstractSpringSqlSessionFactorySupport;
import com.application.base.core.datasource.cons.DefaultConst;
import com.application.base.core.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @desc 获得 SessionFactory 的工具类,得到 sessionFactory 的实例。
 * @author 孤狼
 */
public class DefaultSpringSqlSessionFacotrySupport extends AbstractSpringSqlSessionFactorySupport{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
    @Override
    protected String getFactoryName(String factoryName) {
		// ...默认数据源的Id...
	    String instance = PropertiesUtils.getString(factoryName, DefaultConst.DEFAULT_SQL_SESSION_FACTORY_NAME);
	    logger.info("初始化数据工厂的实例名称是:"+factoryName+",得到的数据实例全名是:"+instance);
	    return instance;
    }
    
}
