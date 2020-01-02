package com.application.base.operapi.api.hbase.factory;

import com.application.base.operapi.api.hbase.api.HbaseSession;
import com.application.base.operapi.api.hbase.core.HbaseClient;
import com.application.base.operapi.api.hbase.core.HbaseSessionFactory;
import com.application.base.operapi.api.hbase.exception.HbaseException;
import com.application.base.operapi.api.hbase.pool.HbaseOperPool;
import com.application.base.operapi.api.hbase.session.HbaseOperSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @NAME: HiveJdbcSessionFactory
 * @DESC: 连接池工厂
 * @author : 孤狼
 **/
public class HbaseOperSessionFactory implements HbaseSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private HbaseOperPool hbaseOperPool;
	
	public HbaseOperSessionFactory() {
	}
	
	public HbaseOperSessionFactory(HbaseOperPool hbaseOperPool) {
		this.hbaseOperPool = hbaseOperPool;
	}
	
	public HbaseOperPool getHbaseOperPool() {
		return hbaseOperPool;
	}
	
	public void setHbaseOperPool(HbaseOperPool hbaseOperPool) {
		this.hbaseOperPool = hbaseOperPool;
	}
	
	@Override
	public HbaseSession getHbaseSession()  throws HbaseException {
		HbaseSession session = null;
		try {
			session = (HbaseSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[]{HbaseSession.class}, new HbaseSimpleSessionProxy(new HbaseOperSession()));
		} catch (Exception e) {
			logger.error("错误信息是:{}", e);
		}
		return session;
	}
	
	/**
	 * 动态代理类实现
	 */
	private class HbaseSimpleSessionProxy implements InvocationHandler {
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private HbaseOperSession operSession;
		
		public HbaseSimpleSessionProxy(HbaseOperSession operSession) {
			this.operSession = operSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized HbaseClient getClient() {
			logger.debug("获取hbase 链接");
			HbaseClient client = null;
			try {
				client = HbaseOperSessionFactory.this.hbaseOperPool.borrowObject();
			}
			catch (Exception e) {
				logger.error("获取 Hbase 链接错误,{}", e);
				throw new HbaseException(e);
			}
			if (null==client){
				logger.error("[错误:{}]","获得hbase client实例对象为空");
				throw new HbaseException("获得hbase client实例对象为空");
			}
			return client;
		}
		
		/**
		 * 方法的代理实现
		 * @param proxy
		 * @param method
		 * @param args
		 * @return
		 * @throws Throwable
		 */
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			HbaseClient client = null;
			boolean success = true;
			try {
				if (hbaseOperPool == null) {
					logger.error("获取hbase连接池失败");
					throw new HbaseException("获取hbase连接池失败");
				}
				client = getClient();
				operSession.setHbaseClient(client);
				return method.invoke(operSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (client != null) {
					hbaseOperPool.returnObject(client);
					client=null;
				}
				logger.error("[hbase执行失败！异常信息为：{}]", e);
				throw e;
			}finally {
				if (success && client != null) {
					hbaseOperPool.returnObject(client);
					client=null;
				}
			}
		}
	}
}
