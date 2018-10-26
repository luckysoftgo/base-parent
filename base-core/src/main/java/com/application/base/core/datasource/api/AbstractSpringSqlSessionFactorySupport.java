package com.application.base.core.datasource.api;

import com.application.base.core.datasource.impl.cache.ObjectMapUtil;
import com.application.base.core.utils.SpringContextHolder;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Sqlsessionfactory提供者SpringContext的抽象(模板)实现，
 * 所有的Spring的sqlsessionFacotry实现都由此类派生,模板类提供生成工厂前后方法定制。
 * @ClassName: AbstractSqlSessionFactorySupport
 * @author 孤狼
 */
public abstract class AbstractSpringSqlSessionFactorySupport implements SqlSessionFactorySupport {

	/**
	 * 通过种子获得 applicationContext.xml 中配置的 SqlSessionFactory 对象.
	 */
	@Override
	public SqlSessionFactory getSqlSessionFacotry(String seed) {
		/**
		 * 内存判断得到结果.
		 */
        String sqlSessionFactoryBeanName = getFactoryName(seed);
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ObjectMapUtil.sessionFactoryKey.get(sqlSessionFactoryBeanName);
        if (sqlSessionFactory==null) {
        	sqlSessionFactory = SpringContextHolder.getBean(sqlSessionFactoryBeanName, SqlSessionFactory.class);
        	//存储全局内存.
        	ObjectMapUtil.sessionFactoryKey.put(sqlSessionFactoryBeanName, sqlSessionFactory);
        	ObjectMapUtil.sessionFactoryVal.put(sqlSessionFactory, sqlSessionFactoryBeanName);
		}
        return  sqlSessionFactory;
    }
	
	/**
	 * 该方法返回 springContext 中 sqlSessionFactoryBean 的名字，该方法的覆盖实现需提供Bean名称产生策略。
	 * @param factoryName
	 * @return
	 */
	protected abstract String getFactoryName(String factoryName);
    
}
