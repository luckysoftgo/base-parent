package com.application.base.core.datasource.impl.common;

import com.application.base.all.elastic.factory.ElasticSessionFactory;
import com.application.base.core.datasource.api.EsDataSessionFactory;
import com.application.base.core.datasource.session.EsSession;

/**
 * @NAME: ElasticSearchInstanceDataSessionFactory
 * @DESC:
 * @USER: 孤狼
 * @DATE: 2019年7月1日
 **/
public class ElasticSearchInstanceDataSessionFactory implements EsDataSessionFactory {
	
	/**
	 * 操作的实例工厂.
	 */
	private ElasticSessionFactory sessionFactory;
	
	@Override
	public EsSession getSession() {
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
