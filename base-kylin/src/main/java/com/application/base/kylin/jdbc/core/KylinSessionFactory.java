package com.application.base.kylin.jdbc.core;

import com.application.base.kylin.jdbc.api.KylinJdbcSession;
import com.application.base.kylin.exception.KylinException;

/**
 * @author : 孤狼
 * @NAME: KylinSessionFactory
 * @DESC: 执行的操作实例工厂
 **/
public interface KylinSessionFactory {
	
	/**
	 * 获得操作kylin的实例对象.
	 * @return
	 * @throws KylinException
	 */
	KylinJdbcSession getJdbcSession() throws KylinException;
	
}
