package com.application.base.operapi.hive.factory;

import com.application.base.operapi.hive.api.HiveJdbcSession;
import com.application.base.operapi.hive.core.HiveJdbcClient;
import com.application.base.operapi.hive.core.HiveSessionFactory;
import com.application.base.operapi.hive.exception.HiveException;
import com.application.base.operapi.hive.pool.HiveJdbcOperPool;
import com.application.base.operapi.hive.session.HiveJdbcOperSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @NAME: HiveJdbcSessionFactory
 * @DESC: 连接池工厂
 * @USER: 孤狼
 **/
public class HiveJdbcSessionFactory implements HiveSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private HiveJdbcOperPool jdbcOperPool;
	
	public HiveJdbcSessionFactory() {
	}
	
	public HiveJdbcSessionFactory(HiveJdbcOperPool jdbcOperPool) {
		this.jdbcOperPool = jdbcOperPool;
	}
	
	public HiveJdbcOperPool getJdbcOperPool() {
		return jdbcOperPool;
	}
	
	public void setJdbcOperPool(HiveJdbcOperPool jdbcOperPool) {
		this.jdbcOperPool = jdbcOperPool;
	}
	
	@Override
	public HiveJdbcSession getJdbcSession()  throws HiveException {
		HiveJdbcSession session = null;
		try {
			session = (HiveJdbcSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[]{HiveJdbcSession.class}, new HiveJdbcSimpleSessionProxy(new HiveJdbcOperSession()));
		} catch (Exception e) {
			logger.error("错误信息是:{}", e);
		}
		return session;
	}
	
	/**
	 * 动态代理类实现
	 */
	private class HiveJdbcSimpleSessionProxy implements InvocationHandler {
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private HiveJdbcOperSession jdbcSession;
		
		public HiveJdbcSimpleSessionProxy(HiveJdbcOperSession jdbcSession) {
			this.jdbcSession = jdbcSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized HiveJdbcClient getJdbcClient() {
			logger.debug("获取Hive jdbc 链接");
			HiveJdbcClient client = null;
			try {
				client = HiveJdbcSessionFactory.this.jdbcOperPool.borrowObject();
			}
			catch (Exception e) {
				logger.error("获取 Hive 链接错误,{}", e);
				throw new HiveException(e);
			}
			if (null==client){
				logger.error("[keylin错误:{}]","获得Hive client实例对象为空");
				throw new HiveException("获得Hive client实例对象为空");
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
			HiveJdbcClient client = null;
			boolean success = true;
			try {
				if (jdbcOperPool == null) {
					logger.error("获取Hive连接池失败");
					throw new HiveException("获取Hive连接池失败");
				}
				client = getJdbcClient();
				jdbcSession.setJdbcClient(client);
				return method.invoke(jdbcSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (client != null) {
					jdbcOperPool.returnObject(client);
					client=null;
				}
				logger.error("[Hive执行失败！异常信息为：{}]", e);
				throw e;
			}finally {
				if (success && client != null) {
					jdbcOperPool.returnObject(client);
					client=null;
				}
			}
		}
	}
}
