package com.application.base.core.datasource.api;

import com.application.base.core.datasource.session.ElasticSession;

/**
 * @NAME: ElasticDataSessionFactory
 * @DESC: elasticsearch 的操作实例工厂.
 * @USER: 孤狼
 **/
public interface ElasticDataSessionFactory {
	
	/**
	 * 获取操作的实例
	 * @return
	 */
	ElasticSession getElasticSession();
	
}
