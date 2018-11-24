package com.application.base.cache.redis.jedis.factory;

import com.application.base.cache.redis.api.DistributedSession;
import com.application.base.cache.redis.api.RedisSession;
import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redis.factory.RedisSessionFactory;
import com.application.base.cache.redis.jedis.session.JedisClusterSession;
import com.application.base.cache.redis.jedis.session.JedisDistributedSession;
import com.application.base.cache.redis.jedis.session.JedisSimpleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @desc redis 集群工厂 session 设置
 * @author 孤狼
 */
public class JedisDistributedSessionFactory implements RedisSessionFactory {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 集群工厂对象.
	 */
	private JedisDistributedFactory pool;

	public JedisDistributedFactory getPool() {
		return pool;
	}
	public void setPool(JedisDistributedFactory pool) {
		this.pool = pool;
	}
	
	@Override
	public RedisSession getRedisSession() {
		return null;
	}
	
	@Override
	public DistributedSession getDistributedSession() throws RedisException {
		DistributedSession session = null;
		try {
			session = (DistributedSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{DistributedSession.class}, new JedisDistributedSessionProxy(new JedisDistributedSession()));
		} catch (Exception e) {
			logger.error("错误信息是:{}", e);
		}
		return session;
	}
	
	/**
	 * redis 集群代理
	 * @author bruce
	 */
	private class JedisDistributedSessionProxy implements InvocationHandler {
		private Logger logger = LoggerFactory.getLogger(getClass());
		private JedisDistributedSession distributedSession;

		public JedisDistributedSessionProxy(JedisDistributedSession distributedSession) {
			this.distributedSession = distributedSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized ShardedJedis getJedisClient() {
			logger.debug("获取redis链接");
			ShardedJedis jedis = null;
			try {
				jedis = JedisDistributedSessionFactory.this.pool.getResource();
			}
			catch (Exception e) {
				logger.error("获取redis链接错误,{}", e);
				throw new RedisException(e);
			}
			if (null==jedis){
				logger.error("[redis错误:{}]","获得redis集群实例对象为空");
				throw new RedisException("获得redis集群实例对象为空");
			}
			return jedis;
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
			ShardedJedis jedis = null;
			boolean success = true;
			try {
				if (pool == null) {
					logger.error("获取Jedi连接池失败");
					throw new RedisException("获取Jedi连接池失败");
				}
				jedis = getJedisClient();
				distributedSession.setDistributedJedis(jedis);
				return method.invoke(distributedSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (jedis != null) {
					jedis.close();
				}
				logger.error("[Jedis执行失败！异常信息为：{}]", e);
				throw e;
			}
			finally {
				if (success && jedis != null) {
					logger.debug("redis 链接关闭");
					jedis.close();
				}
			}
		}
	}

}
