package com.application.base.elastic.elastic.transport.factory;

import com.application.base.elastic.core.ElasticSession;
import com.application.base.elastic.elastic.transport.pool.ElasticTransportPool;
import com.application.base.elastic.elastic.transport.session.ElasticTransportSession;
import com.application.base.elastic.exception.ElasticException;
import com.application.base.elastic.factory.ElasticSessionFactory;
import org.elasticsearch.client.transport.TransportClient;
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
public class EsTransportSessionPoolFactory implements ElasticSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private ElasticTransportPool transportPool;
	
	public EsTransportSessionPoolFactory() {
	}
	public EsTransportSessionPoolFactory(ElasticTransportPool transportPool) {
		this.transportPool = transportPool;
	}
	
	public ElasticTransportPool getTransportPool() {
		return transportPool;
	}
	public void setTransportPool(ElasticTransportPool transportPool) {
		this.transportPool = transportPool;
	}
	
	@Override
	public ElasticSession getElasticSession() throws ElasticException {
		ElasticSession session = null;
		try {
			session = (ElasticSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[]{ElasticSession.class}, new EsTransportSimpleSessionProxy(new ElasticTransportSession()));
		} catch (Exception e) {
			logger.error("错误信息是:{}", e);
		}
		return session;
	}
	
	/**
	 * 动态代理类实现
	 */
	private class EsTransportSimpleSessionProxy implements InvocationHandler {
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private ElasticTransportSession transportSession;
		
		public EsTransportSimpleSessionProxy(ElasticTransportSession transportSession) {
			this.transportSession = transportSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized TransportClient getTransportClient() {
			

			logger.debug("获取elastic链接");
			TransportClient client = null;
			try {
				client = EsTransportSessionPoolFactory.this.transportPool.getResource();
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
			TransportClient client = null;
			boolean success = true;
			try {
				if (transportPool == null) {
					logger.error("获取elastic连接池失败");
					throw new ElasticException("获取elastic连接池失败");
				}
				client = getTransportClient();
				transportSession.setTransportClient(client);
				return method.invoke(transportSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (client != null) {
					client.close();
					transportPool.returnBrokenResource(client);
					client=null;
				}
				logger.error("[elastic执行失败！异常信息为：{}]", e);
				throw e;
			}
			finally {
				if (success && client != null) {
					logger.debug("elastic链接关闭");
					client.close();
					transportPool.returnResource(client);
					client=null;
				}
			}
		}
	}
}
