package com.application.base.core.datasource.api;

import org.apache.ibatis.session.SqlSessionFactory;
/**
 * @desc 获得 sessionFactory 对象
 * @author 孤狼
 */
public interface SqlSessionFactorySupport {
	
	/**
	 * 通过指定的种子,获得 SqlSessionFactory 数据源对象。
	 * @param seed
	 * @return
	 */
    SqlSessionFactory getSqlSessionFacotry(String seed);
    
}
