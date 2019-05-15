package com.application.base.all.elastic.elastic.transport.factory;

import com.application.base.all.elastic.core.ElasticSession;
import com.application.base.all.elastic.elastic.transport.client.ElasticPool;
import com.application.base.all.elastic.exception.ElasticException;
import com.application.base.all.elastic.factory.ElasticSessionFactory;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @NAME: ElasticSessionRestFactory
 * @DESC: 对于操作 elastic 的实现
 * @USER: admin
 **/
public class ElasticSessionTransportFactory implements ElasticSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Override
	public ElasticSession getElasticSession() throws ElasticException {
		return null;
	}
}
