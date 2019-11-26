package com.application.base.kylin.jdbc.factory;

import com.application.base.kylin.exception.KylinException;
import com.application.base.kylin.jdbc.api.KylinJdbcSession;
import com.application.base.kylin.jdbc.core.KylinJdbcClient;
import com.application.base.kylin.jdbc.core.KylinSessionFactory;
import com.application.base.kylin.jdbc.pool.KylinJdbcOperPool;
import com.application.base.kylin.jdbc.session.KylinJdbcOperSession;
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
public class KylinJdbcSessionFactory implements KylinSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private KylinJdbcOperPool jdbcOperPool;
	
	public KylinJdbcSessionFactory() {
	}
	
	public KylinJdbcSessionFactory(KylinJdbcOperPool jdbcOperPool) {
		this.jdbcOperPool = jdbcOperPool;
	}
	
	public KylinJdbcOperPool getJdbcOperPool() {
		return jdbcOperPool;
	}
	
	public void setJdbcOperPool(KylinJdbcOperPool jdbcOperPool) {
		this.jdbcOperPool = jdbcOperPool;
	}
	
	@Override
	public KylinJdbcSession getJdbcSession()  throws KylinException {
		KylinJdbcSession session = null;
		try {
			session = (KylinJdbcSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[]{KylinJdbcSession.class}, new KylinJdbcSimpleSessionProxy(new KylinJdbcOperSession()));
		} catch (Exception e) {
			logger.error("错误信息是:{}", e);
		}
		return session;
	}
	
	/**
	 * 动态代理类实现
	 */
	private class KylinJdbcSimpleSessionProxy implements InvocationHandler {
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private KylinJdbcOperSession jdbcSession;
		
		public KylinJdbcSimpleSessionProxy(KylinJdbcOperSession jdbcSession) {
			this.jdbcSession = jdbcSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized KylinJdbcClient getJdbcClient() {
			logger.debug("获取kylin jdbc 链接");
			KylinJdbcClient client = null;
			try {
				client = KylinJdbcSessionFactory.this.jdbcOperPool.borrowObject();
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
			KylinJdbcClient client = null;
			boolean success = true;
			try {
				if (jdbcOperPool == null) {
					logger.error("获取kylin连接池失败");
					throw new KylinException("获取kylin连接池失败");
				}
				client = getJdbcClient();
				jdbcSession.setKylinJdbcClient(client);
				return method.invoke(jdbcSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (client != null) {
					client=null;
				}
				logger.error("[kylin执行失败！异常信息为：{}]", e);
				throw e;
			}
		}
	}
}
