package com.application.base.kylin.rest.core;

import com.application.base.kylin.exception.KylinException;
import com.application.base.kylin.rest.api.KylinRestSession;

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
	KylinRestSession getRestSession() throws KylinException;
	
}
