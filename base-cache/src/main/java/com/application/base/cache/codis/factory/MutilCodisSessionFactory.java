package com.application.base.cache.codis.factory;

import com.application.base.cache.codis.architecture.cache.CacheClient;
import com.application.base.cache.codis.session.MutilCodisSession;
import com.application.base.cache.redis.api.RedisSession;
import com.application.base.cache.redis.api.ShardedSession;
import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redis.factory.RedisSessionFactory;
import com.application.base.cache.redis.jedis.factory.cluster.OwnRedisClusterPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @desc codis 工厂
 * @author 孤狼
 */
public class MutilCodisSessionFactory implements RedisSessionFactory {
	
    Logger logger = LoggerFactory.getLogger(getClass());

    private OwnRedisClusterPool clusterPool;

    private CacheClient client;
    
    public CacheClient getClient() {
        return client;
    }
    public void setClient(CacheClient client) {
        this.client = client;
    }
    
    public OwnRedisClusterPool getClusterPool() {
        return clusterPool;
    }
    public void setClusterPool(OwnRedisClusterPool pool) {
        this.clusterPool = pool;
    }
    
    @Override
    public RedisSession getRedisSession() throws RedisException {
        RedisSession session;
        try {
            session = (RedisSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new
                    Class[]{RedisSession.class}, new MutilCodisSessionFactory.CodisClusterSessionProxy(new MutilCodisSession()));
        } catch (Exception e) {
            logger.error("{}", e);
            throw new RedisException("获取RedisSession失败");
        }
        return session;
    }
    
    @Override
    public ShardedSession getShardedSession() throws RedisException {
        return null;
    }
    
    private class CodisClusterSessionProxy implements InvocationHandler {
        private MutilCodisSession codisSession;

        public CodisClusterSessionProxy(MutilCodisSession codisSession) {
            this.codisSession = codisSession;
        }
    
        /**
         * 同步获取Jedis链接
         * @return
         */
        private synchronized JedisCluster getJedisCluster() {
            logger.debug("获取redis链接");
            JedisCluster jedis = null;
            try {
                jedis = MutilCodisSessionFactory.this.clusterPool.getResource();
            } catch (Exception e) {
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
            JedisCluster jedis = null;
            boolean success = true;
            try {
                if (getClusterPool() == null) {
                    logger.error("获取Jedi连接池失败");
                    throw new RedisException("获取Jedi连接池失败");
                }
                if (codisSession == null) {
                    logger.error("获取codisSession失败");
                    throw new RedisException("获取codisSession失败");
                }
                jedis = getJedisCluster();
                codisSession.setClusterJedis(jedis);
                codisSession.setClient(client);
                return method.invoke(codisSession, args);
            } catch (RuntimeException e) {
                success = false;
                if (jedis != null) {
	                //jedis.close();
	                clusterPool.returnBrokenResource(jedis);
	                jedis=null;
                }
                logger.error("[Jedis执行失败！异常信息为：{}]", e);
                throw e;
            } finally {
                if (success && jedis != null) {
                    logger.debug("redis 链接关闭");
                    //jedis.close();
	                clusterPool.returnResource(jedis);
	                jedis=null;
                }
            }
        }
    }

}