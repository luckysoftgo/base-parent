package com.application.base.operapi.api.hive.core;

import com.application.base.operapi.api.hive.api.HiveJdbcSession;
import com.application.base.operapi.api.hive.exception.HiveException;

/**
 * @author : 孤狼
 * @NAME: HiveSessionFactory
 * @DESC: 执行的操作实例工厂
 **/
public interface HiveSessionFactory {
	
	/**
	 * 获得操作hive的实例对象.
	 * @return
	 * @throws HiveException
	 */
	HiveJdbcSession getJdbcSession() throws HiveException;
	
}
