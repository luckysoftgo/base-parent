package com.application.base.cache.redis.jedis.factory;

import com.application.base.cache.redis.api.RedisSession;
import com.application.base.cache.redis.api.ShardedSession;
import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redis.factory.RedisSessionFactory;
import com.application.base.cache.redis.jedis.session.JedisClusterSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @desc redis 集群工厂 session 设置
 * @author 孤狼
 */
public class JedisClusterSessionFactory implements RedisSessionFactory {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 集群工厂对象.
	 */
	private JedisClusterFactory jedisCluster;

	public JedisClusterFactory getClusterPool() {
		return jedisCluster;
	}

	public void setClusterPool(JedisClusterFactory jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	@Override
	public RedisSession getRedisSession() {
		RedisSession session = null;
		try {
			session = (RedisSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					new Class[] { RedisSession.class }, new JedisClusterSessionProxy(new JedisClusterSession()));
		}
		catch (Exception e) {
			logger.error("{ Get RedisSession error : }", e.getMessage());
		}
		return session;
	}
	
	@Override
	@Deprecated
	public ShardedSession getShardedSession() throws RedisException {
		return null;
	}
	
	/**
	 * redis 集群代理
	 * @author bruce
	 */
	private class JedisClusterSessionProxy implements InvocationHandler {
		private Logger logger = LoggerFactory.getLogger(getClass());
		private JedisClusterSession clusterSession;

		public JedisClusterSessionProxy(JedisClusterSession clusterSession) {
			this.clusterSession = clusterSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized JedisCluster getClusterClient() {
			logger.debug("获取redis链接");
			JedisCluster jedis = null;
			try {
				jedis = JedisClusterSessionFactory.this.getClusterPool().getClusterResource();
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
			JedisCluster jedisCluster = null;
			boolean success = true;
			try {
				if (getClusterPool() == null) {
					logger.error("获取Jedi连接池失败");
					throw new RedisException("获取Jedi连接池失败");
				}
				jedisCluster = getClusterClient();
				clusterSession.setClusterJedis(jedisCluster);
				return method.invoke(clusterSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (jedisCluster != null) {
					jedisCluster.close();
				}
				logger.error("[Jedis执行失败！异常信息为：{}]", e);
				throw e;
			}
			finally {
				if (success && jedisCluster != null) {
					logger.debug("redis 链接关闭");
					jedisCluster.close();
				}
			}
		}
	}

}
