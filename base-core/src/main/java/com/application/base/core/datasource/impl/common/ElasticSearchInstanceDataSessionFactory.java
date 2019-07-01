package com.application.base.core.datasource.impl.common;

import com.application.base.all.elastic.factory.ElasticSessionFactory;
import com.application.base.core.datasource.api.EsDataSessionFactory;
import com.application.base.core.datasource.session.EsSession;

/**
 * @NAME: ElasticSearchInstanceDataSessionFactory
 * @DESC: 操作elastic的服务接口实现.
 * @USER: 孤狼
 **/
public class ElasticSearchInstanceDataSessionFactory implements EsDataSessionFactory {
	
	/**
	 * 操作的实例工厂.
	 */
	private ElasticSessionFactory sessionFactory;
	
	@Override
	public EsSession getElasticSession() {
		DefaultEsDataSession session = new DefaultEsDataSession(sessionFactory);
		return session;
	}
	
	public ElasticSessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(ElasticSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
