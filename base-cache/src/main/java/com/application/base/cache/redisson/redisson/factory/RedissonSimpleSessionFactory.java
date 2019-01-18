package com.application.base.cache.redisson.redisson.factory;

import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redisson.api.RedissonSession;
import com.application.base.cache.redisson.exception.RedissonException;
import com.application.base.cache.redisson.factory.RedissonSessionFactory;
import com.application.base.cache.redisson.redisson.session.RedissonSentinelSession;
import com.application.base.cache.redisson.redisson.session.RedissonSimpleSession;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @desc 简单工厂 模式
 * @author 孤狼.
 */
public class RedissonSimpleSessionFactory implements RedissonSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 操作实例.
	 */
	private RedissonClient simpleClient;
	
	public RedissonClient getSimpleClient() {
		if (null==simpleClient){
			logger.error("[redisson错误:{}]","获得redisson简单工厂实例对象为空");
			throw new RedisException("获得redisson简单工厂实例对象为空");
		}
		return simpleClient;
	}
	
	public void setSimpleClient(RedissonClient simpleClient) {
		this.simpleClient = simpleClient;
	}
	
	public RedissonSimpleSessionFactory(){}
	
	public RedissonSimpleSessionFactory(RedissonClient simpleClient){
		this.simpleClient = simpleClient;
	}
	
	@Override
	public RedissonSession getRedissonSession() throws RedissonException {
		RedissonSession session = null;
		try {
			session = (RedissonSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[]{RedissonSession.class},new RedissonSimpleSessionProxy(new RedissonSimpleSession())
			);
		} catch (Exception e) {
			logger.error("出現的异常是: {}", e);
		}
		if (null==session){
			logger.error("[redisson错误:{}]","获得redisson简单工厂实例对象为空");
			throw new RedisException("获得redisson简单工厂实例对象为空");
		}
		return session;
	}
	
	/**
	 * 代理实现处理.
	 */
	private class RedissonSimpleSessionProxy implements InvocationHandler {
		
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private RedissonSimpleSession simpleSession;
		
		public RedissonSimpleSessionProxy(RedissonSimpleSession simpleSession) {
			this.simpleSession = simpleSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized RedissonClient getClient() {
			logger.debug("获取 redisson 链接");
			RedissonClient client = null;
			try {
				client = RedissonSimpleSessionFactory.this.getSimpleClient();
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
				simpleSession.setClient(client);
				return method.invoke(simpleSession, args);
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
