package com.application.base.cache.redis.jedis.factory;

import com.application.base.cache.redis.api.RedisSession;
import com.application.base.cache.redis.api.ShardedSession;
import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redis.factory.RedisSessionFactory;
import com.application.base.cache.redis.jedis.session.JedisSimpleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @desc redis 哨兵工厂 session 设置
 * @author 孤狼
 */
public class  JedisSentinelSessionFactory implements RedisSessionFactory {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 集群工厂对象.
	 */
	private JedisSentinelFactory sentinelPool;

	public JedisSentinelFactory getSentinelPool() {
		return sentinelPool;
	}
	
	public void setSentinelPool(JedisSentinelFactory sentinelPool) {
		this.sentinelPool = sentinelPool;
	}
	
	@Override
	public RedisSession getRedisSession() throws RedisException {
		RedisSession session = null;
		try {
			session = (RedisSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[]{RedisSession.class}, new JedisSentinelSessionProxy(new JedisSimpleSession()));
		} catch (Exception e) {
			logger.error("错误信息是:{}", e);
		}
		return session;
	}
	
	@Override
	@Deprecated
	public ShardedSession getShardedSession() throws RedisException {
		return null;
	}
	
	/**
	 * redis 哨兵代理
	 * @author bruce
	 */
	private class JedisSentinelSessionProxy implements InvocationHandler {
		
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private JedisSimpleSession jedisSession;

		public JedisSentinelSessionProxy(JedisSimpleSession jedisSession) {
			this.jedisSession = jedisSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized Jedis getJedisClient() {
			logger.debug("获取redis链接");
			Jedis jedis = null;
			try {
				jedis = JedisSentinelSessionFactory.this.sentinelPool.getResource();
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
			Jedis jedis = null;
			boolean success = true;
			try {
				if (getSentinelPool() == null) {
					logger.error("获取Jedi连接池失败");
					throw new RedisException("获取Jedi连接池失败");
				}
				jedis = getJedisClient();
				jedisSession.setJedis(jedis);
				return method.invoke(jedisSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (jedis != null) {
					//jedis.close();
					sentinelPool.returnBrokenResource(jedis);
					jedis=null;
				}
				logger.error("[Jedis执行失败！异常信息为：{}]", e);
				throw e;
			}
			finally {
				if (success && jedis != null) {
					logger.debug("redis 链接关闭");
					//jedis.close();
					sentinelPool.returnResource(jedis);
					jedis=null;
				}
			}
		}
	}

}
