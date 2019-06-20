package com.application.base.cache.redis.jedis.lock;

import com.application.base.cache.redis.api.RedisSession;
import com.application.base.cache.redis.exception.DistributedLockException;
import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redis.factory.RedisSessionFactory;
import com.application.base.utils.common.BaseStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;

/**
 * @desc 分布式锁实现.
 * https://redis.io/topics/distlock
 * @author 孤狼
 */
public class DelegateDistributedLock implements DistributedLock {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 释放key
     */
    final String SCRIPT_SHELL = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    
    /**
     * Redis的实例工厂.
     */
    private RedisSessionFactory sessionFactory;
    
    public RedisSessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public void setSessionFactory(RedisSessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public boolean getDistLock(String uniqueKey, String uniqueValue) throws DistributedLockException, RedisException {
        return getDistLock(uniqueKey,uniqueValue,DEFAULT_SINGLE_EXPIRE_TIME);
    }
    
    @Override
    public boolean getDistLock(String uniqueKey, String uniqueValue, int expireTime) throws DistributedLockException, RedisException {
        RedisSession session = null;
        try {
            session = sessionFactory.getRedisSession();
            if (session==null){
                logger.debug(" can't get session ");
                return false;
            }
            Jedis jedis = session.getJedisClient();
            JedisCluster cluster = session.getClusterClient();
            String result = "";
            if (jedis!=null){
                logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueValue);
                result = jedis.set(uniqueKey, uniqueValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_MILLISECONDS, expireTime);
            }
            if (cluster!=null){
                logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueValue);
                result = cluster.set(uniqueKey, uniqueValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_MILLISECONDS, expireTime);
            }
            if (LOCK_SUCCESS.equalsIgnoreCase(result)) {
                return true;
            }
            return false;
        } catch (RedisException e) {
            logger.debug("获得分布式锁异常了,异常信息是:{}",e);
            throw e;
        } catch (Exception e) {
            throw new DistributedLockException(e);
        }
    }
    
    @Override
    public boolean releaseDistLock(String uniqueKey, String uniqueValue) throws DistributedLockException, RedisException {
        RedisSession session = null;
        try {
            session = sessionFactory.getRedisSession();
            if (session==null){
                logger.debug(" can't get session ");
                return false;
            }
            Jedis jedis = session.getJedisClient();
            JedisCluster cluster = session.getClusterClient();
            Object result  = null;
            if (jedis!=null){
                result = jedis.eval(SCRIPT_SHELL, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueValue));
                if (SUCCESS==BaseStringUtil.intValue(result)) {
                    return true;
                }
            }
            if (cluster!=null){
                result = cluster.eval(SCRIPT_SHELL, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueValue));
                if (SUCCESS==BaseStringUtil.intValue(result)) {
                    return true;
                }
            }
            return false;
        } catch (RedisException e) {
            logger.debug("释放分布式锁异常了,异常信息:{}",e);
            throw e;
        } catch (Exception e) {
            throw new DistributedLockException(e);
        }
    }
    
    @Override
    public boolean isLock(String uniqueKey) throws DistributedLockException, RedisException {
        RedisSession session = null;
        try {
            session = sessionFactory.getRedisSession();
            if (session==null){
                logger.debug(" can't get session ");
                return false;
            }
            Jedis jedis = session.getJedisClient();
            JedisCluster cluster = session.getClusterClient();
            String result = "";
            if (jedis!=null){
                result = jedis.get(uniqueKey);
            }
            if (cluster!=null){
                result = cluster.get(uniqueKey);
            }
            if (BaseStringUtil.isNotEmpty(result)){
                return true;
            }
            return false;
        } catch (RedisException e) {
            logger.debug("获得分布式锁是否存在异常了,异常信息:{}",e);
            throw e;
        } catch (Exception e) {
            throw new DistributedLockException(e);
        }
    }
    
   
    /*************************************************************************************************************************************************************************/
    /******************************************************************************** 按照数据库选择分布式锁的实现 *******************************************************************/
    /*************************************************************************************************************************************************************************/

    @Override
    public boolean getDistLock(int dbIndex, String uniqueKey, String uniqueValue) throws DistributedLockException, RedisException {
        return getDistLock(dbIndex,uniqueKey,uniqueValue,DEFAULT_SINGLE_EXPIRE_TIME);
    }
    
    
    @Override
    public boolean getDistLock(int dbIndex,String uniqueKey, String uniqueValue, int expireTime) throws DistributedLockException, RedisException {
        RedisSession session = null;
        try {
            session = sessionFactory.getRedisSession();
            if (session==null){
                logger.debug(" can't get session ");
                return false;
            }
            Jedis jedis = session.getJedisClient();
            JedisCluster cluster = session.getClusterClient();
            String result = "";
            if (jedis!=null){
                logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueValue);
                jedis.select(dbIndex);
                result = jedis.set(uniqueKey, uniqueValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_MILLISECONDS, expireTime);
            }
            if (cluster!=null){
                logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueValue);
                cluster.select(dbIndex);
                result = cluster.set(uniqueKey, uniqueValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_MILLISECONDS, expireTime);
            }
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } catch (RedisException e) {
            logger.debug("获得分布式锁异常了" + e);
            throw e;
        } catch (Exception e) {
            throw new DistributedLockException(e);
        }
    }
    
    @Override
    public boolean releaseDistLock(int dbIndex,String uniqueKey, String uniqueValue) throws DistributedLockException, RedisException {
        RedisSession session = null;
        try {
            session = sessionFactory.getRedisSession();
            if (session==null){
                logger.debug(" can't get session ");
                return false;
            }
            Jedis jedis = session.getJedisClient();
            JedisCluster cluster = session.getClusterClient();
            Object result  = null;
            if (jedis!=null){
                jedis.select(dbIndex);
                result = jedis.eval(SCRIPT_SHELL, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueValue));
                if (SUCCESS== BaseStringUtil.intValue(result)) {
                    return true;
                }
            }
            if (cluster!=null){
                cluster.select(dbIndex);
                result = cluster.eval(SCRIPT_SHELL, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueValue));
                if (SUCCESS== BaseStringUtil.intValue(result)) {
                    return true;
                }
            }
            return false;
        } catch (RedisException e) {
            logger.debug("获得分布式锁异常了" + e);
            throw e;
        } catch (Exception e) {
            throw new DistributedLockException(e);
        }
    }
    
    @Override
    public boolean isLock(int dbIndex,String uniqueKey) throws DistributedLockException, RedisException {
        RedisSession session = null;
        try {
            session = sessionFactory.getRedisSession();
            if (session==null){
                logger.debug(" can't get session ");
                return false;
            }
            Jedis jedis = session.getJedisClient();
            JedisCluster cluster = session.getClusterClient();
            String result = "";
            if (jedis!=null){
                jedis.select(dbIndex);
                result = jedis.get(uniqueKey);
            }
            if (cluster!=null){
                cluster.select(dbIndex);
                result = cluster.get(uniqueKey);
            }
            if (BaseStringUtil.isNotEmpty(result)){
                return true;
            }
            return false;
        } catch (RedisException e) {
            logger.debug("获得分布式锁异常了" + e);
            throw e;
        } catch (Exception e) {
            throw new DistributedLockException(e);
        }
    }
    
}
