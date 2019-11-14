package com.application.base.kylin.rest.factory;

import com.application.base.exception.KylinException;
import com.application.base.kylin.rest.api.KylinRestSession;
import com.application.base.kylin.rest.core.KylinRestApiClient;
import com.application.base.kylin.rest.core.KylinSessionFactory;
import com.application.base.kylin.rest.pool.KylinJestApiPool;
import com.application.base.kylin.rest.session.KylinRestApiSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @NAME: KylinJdbcSessionFactory
 * @DESC: 连接池工厂
 * @USER: 孤狼
 **/
public class KylinJestSessionFactory implements KylinSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private KylinJestApiPool restApiPool;
	
	public KylinJestSessionFactory() {
	}
	
	public KylinJestSessionFactory(KylinJestApiPool apiPool) {
		this.restApiPool = apiPool;
	}
	
	public KylinJestApiPool getRestApiPool() {
		return restApiPool;
	}
	
	public void setRestApiPool(KylinJestApiPool restApiPool) {
		this.restApiPool = restApiPool;
	}
	
	@Override
	public KylinRestSession getRestSession()  throws KylinException {
		KylinRestSession session = null;
		try {
			session = (KylinRestSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[]{KylinRestSession.class}, new KylinJestSimpleSessionProxy(new KylinRestApiSession()));
		} catch (Exception e) {
			logger.error("错误信息是:{}", e);
		}
		return session;
	}
	
	/**
	 * 动态代理类实现
	 */
	private class KylinJestSimpleSessionProxy implements InvocationHandler {
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private KylinRestApiSession restSession;
		
		public KylinJestSimpleSessionProxy(KylinRestApiSession restSession) {
			this.restSession = restSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized KylinRestApiClient getRestClient() {
			logger.debug("获取kylin链接");
			KylinRestApiClient client = null;
			try {
				client = KylinJestSessionFactory.this.restApiPool.borrowObject();
			}
			catch (Exception e) {
				logger.error("获取 kylin 链接错误,{}", e);
				throw new KylinException(e);
			}
			if (null==client){
				logger.error("[keylin错误:{}]","获得kylin client实例对象为空");
				throw new KylinException("获得kylin client实例对象为空");
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
			KylinRestApiClient client = null;
			boolean success = true;
			try {
				if (restApiPool == null) {
					logger.error("获取kylin连接池失败");
					throw new KylinException("获取kylin连接池失败");
				}
				client = getRestClient();
				restSession.setKylinRestApiClient(client);
				return method.invoke(restSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (client != null) {
					client=null;
				}
				logger.error("[kylin执行失败！异常信息为：{}]", e);
				throw e;
			}
			finally {
				if (success && client != null) {
					logger.debug("kylin链接关闭");
					client=null;
				}
			}
		}
	}
}
