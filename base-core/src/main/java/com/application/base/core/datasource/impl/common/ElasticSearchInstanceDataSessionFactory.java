package com.application.base.core.datasource.impl.common;

import com.application.base.core.datasource.session.ElasticSession;
import com.application.base.elastic.factory.ElasticSessionFactory;
import com.application.base.core.datasource.api.ElasticDataSessionFactory;

/**
 * @NAME: ElasticSearchInstanceDataSessionFactory
 * @DESC: 操作elastic的服务接口实现.
 * @USER: 孤狼
 **/
public class ElasticSearchInstanceDataSessionFactory implements ElasticDataSessionFactory {
	
	/**
	 * 操作的实例工厂.
	 */
	private ElasticSessionFactory sessionFactory;
	
	@Override
	public ElasticSession getElasticSession() {
		DefaultElasticDataSession session = new DefaultElasticDataSession(sessionFactory);
		return session;
	}
	
	public ElasticSessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(ElasticSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
