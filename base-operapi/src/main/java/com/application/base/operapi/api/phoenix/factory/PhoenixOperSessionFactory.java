package com.application.base.operapi.api.phoenix.factory;

import com.application.base.operapi.api.phoenix.api.PhoenixSession;
import com.application.base.operapi.api.phoenix.core.PhoenixClient;
import com.application.base.operapi.api.phoenix.core.PhoenixSessionFactory;
import com.application.base.operapi.api.phoenix.exception.PhoenixException;
import com.application.base.operapi.api.phoenix.pool.PhoenixOperPool;
import com.application.base.operapi.api.phoenix.session.PhoenixOperSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author : 孤狼
 * @NAME: PhoenixOperSessionFactory
 * @DESC: 连接池工厂
 **/
public class PhoenixOperSessionFactory implements PhoenixSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private PhoenixOperPool phoenixOperPool;
	
	public PhoenixOperSessionFactory() {
	}
	
	public PhoenixOperSessionFactory(PhoenixOperPool phoenixOperPool) {
		this.phoenixOperPool = phoenixOperPool;
	}
	
	public PhoenixOperPool getPhoenixOperPool() {
		return phoenixOperPool;
	}
	
	public void setPhoenixOperPool(PhoenixOperPool phoenixOperPool) {
		this.phoenixOperPool = phoenixOperPool;
	}
	
	@Override
	public PhoenixSession getPhoenixxSession() throws PhoenixException {
		PhoenixSession session = null;
		try {
			session = (PhoenixSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[]{PhoenixSession.class}, new PhoenixSimpleSessionProxy(new PhoenixOperSession()));
		} catch (Exception e) {
			logger.error("错误信息是:{}", e);
		}
		return session;
	}
	
	/**
	 * 动态代理类实现
	 */
	private class PhoenixSimpleSessionProxy implements InvocationHandler {
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private PhoenixOperSession operSession;
		
		public PhoenixSimpleSessionProxy(PhoenixOperSession operSession) {
			this.operSession = operSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 *
		 * @return
		 */
		private synchronized PhoenixClient getClient() {
			logger.debug("获取hbase 链接");
			PhoenixClient client = null;
			try {
				client = PhoenixOperSessionFactory.this.phoenixOperPool.borrowObject();
			} catch (Exception e) {
				logger.error("获取 Hbase 链接错误,{}", e);
				throw new PhoenixException(e);
			}
			if (null == client) {
				logger.error("[错误:{}]", "获得hbase client实例对象为空");
				throw new PhoenixException("获得hbase client实例对象为空");
			}
			return client;
		}
		
		/**
		 * 方法的代理实现
		 *
		 * @param proxy
		 * @param method
		 * @param args
		 * @return
		 * @throws Throwable
		 */
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			PhoenixClient client = null;
			boolean success = true;
			try {
				if (phoenixOperPool == null) {
					logger.error("获取hbase连接池失败");
					throw new PhoenixException("获取hbase连接池失败");
				}
				client = getClient();
				operSession.setPhoenixClient(client);
				return method.invoke(operSession, args);
			} catch (RuntimeException e) {
				success = false;
				if (client != null) {
					phoenixOperPool.returnObject(client);
					client = null;
				}
				logger.error("[hbase执行失败！异常信息为：{}]", e);
				throw e;
			} finally {
				if (success && client != null) {
					phoenixOperPool.returnObject(client);
					client = null;
				}
			}
		}
	}
}
