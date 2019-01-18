package com.application.base.cache.redisson.redisson.factory;

import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redisson.api.RedissonSession;
import com.application.base.cache.redisson.exception.RedissonException;
import com.application.base.cache.redisson.factory.RedissonSessionFactory;
import com.application.base.cache.redisson.redisson.session.RedissonClusterSession;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @desc 集群模式 工厂
 * @author 孤狼.
 */
public class RedissonClusterSessionFactory implements RedissonSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 操作实例.
	 */
	private RedissonClient clusterClient;
	
	public RedissonClient getClusterClient() {
		if (null==clusterClient){
			logger.error("[redisson错误:{}]","获得redisson集群实例对象为空");
			throw new RedisException("获得redisson集群实例对象为空");
		}
		return clusterClient;
	}
	public void setClusterClient(RedissonClient clusterClient) {
		this.clusterClient = clusterClient;
	}
	
	public RedissonClusterSessionFactory(){}
	
	public RedissonClusterSessionFactory(RedissonClient clusterClient){
		this.clusterClient = clusterClient;
	}
	
	@Override
	public RedissonSession getRedissonSession() throws RedissonException {
		RedissonSession session = null;
		try {
			session = (RedissonSession) Proxy.newProxyInstance(
					Thread.currentThread().getContextClassLoader(),
					new Class[]{RedissonSession.class},new RedissonClusterSessionProxy(new RedissonClusterSession())
			);
		} catch (Exception e) {
			logger.error("出現的异常是: {}", e);
		}
		if (null==session){
			logger.error("[redisson错误:{}]","获得redisson集群实例对象为空");
			throw new RedisException("获得redisson集群实例对象为空");
		}
		return session;
	}
	
	
	/**
	 * 代理实现处理.
	 */
	private class RedissonClusterSessionProxy implements InvocationHandler {
		
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private RedissonClusterSession clusterSession;
		
		public RedissonClusterSessionProxy(RedissonClusterSession clusterSession) {
			this.clusterSession = clusterSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized RedissonClient getClient() {
			logger.debug("获取 redisson 链接");
			RedissonClient client = null;
			try {
				client = RedissonClusterSessionFactory.this.getClusterClient();
			}
			catch (Exception e) {
				logger.error("获取 redisson 链接错误,{}", e);
				throw new RedissonException(e);
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
			RedissonClient client = null;
			try {
				client = getClient();
				clusterSession.setClient(client);
				return method.invoke(clusterSession, args);
			}
			catch (RuntimeException e) {
				if (client != null) {
					client.shutdown();
				}
				logger.error("[redisson 执行失败！异常信息为：{}]", e);
				throw e;
			}
		}
	}
}
