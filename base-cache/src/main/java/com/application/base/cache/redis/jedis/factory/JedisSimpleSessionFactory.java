package com.application.base.cache.redis.jedis.factory;

import com.application.base.cache.redis.api.RedisSession;
import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redis.factory.RedisSessionFactory;
import com.application.base.cache.redis.jedis.session.JedisSimpleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @desc 简单工厂
 * @author 孤狼.
 */
public class JedisSimpleSessionFactory implements RedisSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	private Pool<Jedis> pool;

	public JedisSimpleSessionFactory() {
	}
	
	public JedisSimpleSessionFactory(Pool<Jedis> pool) {
		this.pool = pool;
	}

    @Override
    public RedisSession getRedisSession() {
        RedisSession session = null;
        try {
            session = (RedisSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{RedisSession.class}, new JedisSimpleSessionProxy(new JedisSimpleSession()));
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return session;
    }

	public Pool<Jedis> getPool() {
		return pool;
	}

	public void setPool(Pool<Jedis> pool) {
		this.pool = pool;
	}

	private class JedisSimpleSessionProxy implements InvocationHandler {
		
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private JedisSimpleSession jedisSession;

		public JedisSimpleSessionProxy(JedisSimpleSession jedisSession) {
			this.jedisSession = jedisSession;
		}
		
		/**
		 * 同步获取Jedis链接
		 * @return
		 */
		private synchronized Jedis getJedis() {
			logger.debug("获取redis链接");
			Jedis jedis = null;
			try {
				jedis = JedisSimpleSessionFactory.this.pool.getResource();
			}
			catch (Exception e) {
				logger.error("获取redis链接错误,{}", e);
				throw new RedisException(e);
			}
			if (null==jedis){
				logger.error("[redis错误:{}]","获得redis实例对象为空");
				throw new RedisException("获得redis实例对象为空");
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
				if (pool == null) {
					logger.error("获取Jedi连接池失败");
					throw new RedisException("获取Jedi连接池失败");
				}
				jedis = getJedis();
				jedisSession.setJedis(jedis);
				return method.invoke(jedisSession, args);
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
