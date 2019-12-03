package com.application.base.elastic.elastic.restclient.factory;

import com.application.base.elastic.core.ElasticSession;
import com.application.base.elastic.elastic.restclient.pool.ElasticRestClientPool;
import com.application.base.elastic.elastic.restclient.session.ElasticRestClientSession;
import com.application.base.elastic.exception.ElasticException;
import com.application.base.elastic.factory.ElasticSessionFactory;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @NAME: EsTransportSessionPoolFactory
 * @DESC: 连接池工厂
 * @USER: 孤狼
 **/
public class EsRestClientSessionPoolFactory implements ElasticSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private ElasticRestClientPool jestPool;
	
	public EsRestClientSessionPoolFactory() {
	}
	
	public EsRestClientSessionPoolFactory(ElasticRestClientPool jestPool) {
		this.jestPool = jestPool;
	}
	
	
	public ElasticRestClientPool getJestPool() {
		return jestPool;
	}
	public void setJestPool(ElasticRestClientPool jestPool) {
		this.jestPool = jestPool;
	}
	
	@Override
	public ElasticSession getElasticSession() throws ElasticException {
		ElasticSession session = null;
		try {
			session = (ElasticSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[]{ElasticSession.class}, new EsRestClientSimpleSessionProxy(new ElasticRestClientSession()));
		} catch (Exception e) {
			logger.error("错误信息是:{}", e);
		}
		return session;
	}
	
	/**
	 * 动态代理类实现
	 */
	private class EsRestClientSimpleSessionProxy implements InvocationHandler {
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private ElasticRestClientSession restSession;
		
		public EsRestClientSimpleSessionProxy(ElasticRestClientSession restSession) {
			this.restSession = restSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized RestHighLevelClient getLevelClient() {
			logger.debug("获取elastic链接");
			RestHighLevelClient client = null;
			try {
				client = EsRestClientSessionPoolFactory.this.jestPool.getResource();
			}
			catch (Exception e) {
				logger.error("获取elastic链接错误,{}", e);
				throw new ElasticException(e);
			}
			if (null==client){
				logger.error("[elastic错误:{}]","获得elastic实例对象为空");
				throw new ElasticException("获得elastic实例对象为空");
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
			RestHighLevelClient client = null;
			boolean success = true;
			try {
				if (jestPool == null) {
					logger.error("获取elastic连接池失败");
					throw new ElasticException("获取elastic连接池失败");
				}
				client = getLevelClient();
				restSession.setLevelClient(client);
				return method.invoke(restSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (client != null) {
					jestPool.returnBrokenResource(client);
					client.close();
					client=null;
				}
				logger.error("[elastic执行失败！异常信息为：{}]", e);
				throw e;
			}
			finally {
				if (success && client != null) {
					logger.debug("elastic链接关闭");
					jestPool.returnResource(client);
					client.close();
					client=null;
				}
			}
		}
	}
}
