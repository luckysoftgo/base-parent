package com.application.base.cache.redis.jedis.factory.sharded;

import com.application.base.cache.redis.api.RedisSession;
import com.application.base.cache.redis.api.ShardedSession;
import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redis.factory.RedisSessionFactory;
import com.application.base.cache.redis.jedis.session.JedisShardedSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.ShardedJedis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @desc redis 分片工厂 session 设置
 * @author 孤狼
 */
public class JedisShardedSessionFactory implements RedisSessionFactory {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 集群工厂对象.
	 */
	private JedisSimpleShardedPool shardedPool;

	public JedisSimpleShardedPool getShardedPool() {
		return shardedPool;
	}
	public void setShardedPool(JedisSimpleShardedPool shardedPool) {
		this.shardedPool = shardedPool;
	}

	@Override
	@Deprecated
	public RedisSession getRedisSession() throws RedisException  {
		return null;
	}
	
	@Override
	public ShardedSession getShardedSession() throws RedisException {
		ShardedSession session = null;
		try {
			session = (ShardedSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[] { ShardedSession.class }, new JedisShardedSessionProxy(new JedisShardedSession()));
		}
		catch (Exception e) {
			logger.error("{ Get RedisSession error : }", e.getMessage());
		}
		return session;
	}
	
	/**
	 * redis 分片的代理
	 * @author bruce
	 */
	private class JedisShardedSessionProxy implements InvocationHandler {
		
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private JedisShardedSession shardedSession;

		public JedisShardedSessionProxy(JedisShardedSession shardedSession) {
			this.shardedSession = shardedSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized ShardedJedis getShardedClient() {
			logger.debug("获取redis链接");
			ShardedJedis jedis = null;
			try {
				jedis = JedisShardedSessionFactory.this.shardedPool.getShardedResource();
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
			ShardedJedis shardedJedis = null;
			boolean success = true;
			try {
				if (getShardedPool() == null) {
					logger.error("获取Jedi连接池失败");
					throw new RedisException("获取Jedi连接池失败");
				}
				shardedJedis = getShardedClient();
				shardedSession.setShardedJedis(shardedJedis);
				return method.invoke(shardedSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (shardedJedis != null) {
					//shardedJedis.close();
					shardedPool.returnBrokenResource(shardedJedis);
					shardedJedis=null;
				}
				logger.error("[Jedis执行失败！异常信息为：{}]", e);
				throw e;
			}
			finally {
				if (success && shardedJedis != null) {
					logger.debug("redis 链接关闭");
					//shardedJedis.close();
					shardedPool.returnResource(shardedJedis);
					shardedJedis=null;
				}
			}
		}
	}

}
