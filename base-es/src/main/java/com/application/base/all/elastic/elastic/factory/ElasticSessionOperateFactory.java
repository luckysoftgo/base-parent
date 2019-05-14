package com.application.base.all.elastic.elastic.factory;

import com.application.base.all.elastic.core.ElasticSession;
import com.application.base.all.elastic.elastic.client.ElasticPool;
import com.application.base.all.elastic.elastic.session.ElasticOperateSession;
import com.application.base.all.elastic.exception.ElasticException;
import com.application.base.all.elastic.factory.ElasticSessionFactory;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @NAME: ElasticSessionOperateFactory
 * @DESC: 对于操作 elastic 的实现
 * @USER: admin
 **/
public class ElasticSessionOperateFactory implements ElasticSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	private ElasticPool elasticPool;
	
	public ElasticSessionOperateFactory() {
	}
	
	public ElasticSessionOperateFactory(ElasticPool elasticPool) {
		this.elasticPool = elasticPool;
	}
	
	/**
	 * 获得操作的实例
	 * @return
	 * @throws ElasticException
	 */
	@Override
	public ElasticSession getElasticSession() throws ElasticException {
		ElasticSession session = null;
		try {
			session = (ElasticSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[]{ElasticSession.class}, new ElasticSessionOperateProxy(new ElasticOperateSession()));
		} catch (Exception e) {
			logger.error("获取实例信息错误,信息是:{}", e);
		}
		return session;
	}
	
	public ElasticPool getElasticPool() {
		return elasticPool;
	}
	
	public void setElasticPool(ElasticPool elasticPool) {
		this.elasticPool = elasticPool;
	}
	
	/**
	 * 操作的代理
	 */
	private class ElasticSessionOperateProxy implements InvocationHandler {
		
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private ElasticOperateSession operateSession;
		
		public ElasticSessionOperateProxy(ElasticOperateSession operateSession) {
			this.operateSession = operateSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized TransportClient getClient() {
			logger.debug("获取Elastic链接");
			TransportClient client = null;
			try {
				client = ElasticSessionOperateFactory.this.elasticPool.getResource();
			}
			catch (Exception e) {
				logger.error("获取Elastic链接错误,{}", e);
				throw new ElasticException(e);
			}
			if (null==client){
				logger.error("[Elastic错误:{}]","获得Elastic实例对象为空");
				throw new ElasticException("获得Elastic实例对象为空");
			}
			return client;
		}
		
		/**
		 * Redis方法的代理实现
		 *
		 * @param proxy
		 * @param method
		 * @param args
		 * @return
		 * @throws Throwable
		 */
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			TransportClient client = null;
			boolean success = true;
			try {
				if (elasticPool == null) {
					logger.error("获取elastic连接池失败");
					throw new ElasticException("获取elastic连接池失败");
				}
				client = getClient();
				operateSession.setClient(client);
				return method.invoke(operateSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (client != null) {
					client.close();
				}
				logger.error("[Jedis执行失败！异常信息为：{}]", e);
				throw e;
			}
			finally {
				if (success && client != null) {
					logger.debug("elastic 链接关闭");
					client.close();
				}
			}
		}
	}
}
