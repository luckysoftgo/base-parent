package com.application.base.core.datasource.api;

import com.application.base.core.datasource.session.EsSession;

/**
 * @NAME: EsDataSessionFactory
 * @DESC: elasticsearch 的操作实例工厂.
 * @USER: 孤狼
 **/
public interface EsDataSessionFactory {
	
	/**
	 * 获取操作的实例
	 * @return
	 */
	EsSession getSession();
	
}
