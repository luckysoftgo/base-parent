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
import java.util.concurrent.TimeUnit;

/**
 * @desc 分布式锁实现.
 * @author 孤狼
 */
public class DelegateDistributedLock implements DistributedLock {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private RedisSessionFactory sessionFactory;
    
    public RedisSessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public void setSessionFactory(RedisSessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
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
                result = jedis.set(uniqueKey, uniqueValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            }
            if (cluster!=null){
                logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueValue);
                result = cluster.set(uniqueKey, uniqueValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
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
    public boolean releaseDistLock(String uniqueKey, String uniqueValue) throws DistributedLockException, RedisException {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
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
                result = jedis.eval(script, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueValue));
                if (SUCCESS== BaseStringUtil.intValue(result)) {
                    return true;
                }
            }
            if (cluster!=null){
                result = cluster.eval(script, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueValue));
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
    
    /**
     * 这种的弊端：(如果在执行成功之后,设置失效时间失败,则无法做到锁定失效的效果)
     * @param uniqueKey
     * @return
     * @throws DistributedLockException
     * @throws RedisException
     */
    @Override
    public boolean loopLock(String uniqueKey) throws DistributedLockException, RedisException {
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
            do {
                if (jedis!=null){
                    logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueKey);
                    result = jedis.set(uniqueKey, uniqueKey, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, DEFAULT_SINGLE_EXPIRE_TIME*10);
                }
                if (cluster!=null){
                    logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueKey);
                    result = cluster.set(uniqueKey, uniqueKey, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, DEFAULT_SINGLE_EXPIRE_TIME*10);
                }
                if (LOCK_SUCCESS.equals(result)) {
                    return true;
                } else {
                    String desc = session.getData(uniqueKey);
                    logger.debug("key: " + uniqueKey + " locked by another business：" + desc);
                }
                Thread.sleep(300);
            } while (true);
        } catch (RedisException e) {
            logger.debug("获得分布式锁异常了" + e);
            throw e;
        } catch (Exception e) {
            throw new DistributedLockException(e);
        }
    }

    @Override
    public boolean tryLock(String uniqueKey) throws DistributedLockException, RedisException {
        return tryLock(uniqueKey, DEFAULT_SINGLE_EXPIRE_TIME, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean tryLock(String uniqueKey, long timeout, TimeUnit unit) throws DistributedLockException, RedisException {
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
            long nano = System.nanoTime();
            do {
                if (jedis!=null){
                    logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueKey);
                    result = jedis.set(uniqueKey, uniqueKey, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, timeout);
                }
                if (cluster!=null){
                    logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueKey);
                    result = cluster.set(uniqueKey, uniqueKey, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, timeout);
                }
                if (LOCK_SUCCESS.equals(result)) {
                    return true;
                } else { // 存在锁
                    String desc = session.getData(uniqueKey);
                    logger.debug("key: " + uniqueKey + " locked by another business：" + desc);
                }
                if (timeout == 0) {
                    break;
                }
                Thread.sleep(300);
            } while ((System.nanoTime() - nano) < unit.toNanos(timeout));
            return Boolean.FALSE;
        } catch (RedisException e) {
            logger.debug("获得分布式锁异常了" + e);
            throw e;
        } catch (Exception e) {
            throw new DistributedLockException(e);
        }
    }

    @Override
    public boolean unLock(String uniqueKey) throws DistributedLockException, RedisException {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Jedis jedis = sessionFactory.getRedisSession().getJedisClient();
        JedisCluster cluster = sessionFactory.getRedisSession().getClusterClient();
        if (jedis!=null){
            Object result = jedis.eval(script, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueKey));
            if (SUCCESS== BaseStringUtil.intValue(result)) {
                return true;
            }
        }
        if (cluster!=null){
            Object result = cluster.eval(script, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueKey));
            if (SUCCESS== BaseStringUtil.intValue(result)) {
                return true;
            }
        }
        logger.debug("解分布式锁失败");
        return false;
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
            logger.debug("获得分布式锁异常了" + e);
            throw e;
        } catch (Exception e) {
            throw new DistributedLockException(e);
        }
    }
    
    
    /*************************************************************************************************************************************************************************/
    /******************************************************************************** 按照数据库选择分布式锁的实现 *******************************************************************/
    /*************************************************************************************************************************************************************************/

    @Override
    public boolean getDistLock(int dbindex,String uniqueKey, String uniqueValue, int expireTime) throws DistributedLockException, RedisException {
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
                jedis.select(dbindex);
                result = jedis.set(uniqueKey, uniqueValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            }
            if (cluster!=null){
                logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueValue);
                cluster.select(dbindex);
                result = cluster.set(uniqueKey, uniqueValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
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
    public boolean releaseDistLock(int dbindex,String uniqueKey, String uniqueValue) throws DistributedLockException, RedisException {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
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
                jedis.select(dbindex);
                result = jedis.eval(script, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueValue));
                if (SUCCESS== BaseStringUtil.intValue(result)) {
                    return true;
                }
            }
            if (cluster!=null){
                cluster.select(dbindex);
                result = cluster.eval(script, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueValue));
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
    
    /**
     * 这种的弊端：(如果在执行成功之后,设置失效时间失败,则无法做到锁定失效的效果)
     * @param uniqueKey
     * @return
     * @throws DistributedLockException
     * @throws RedisException
     */
    @Override
    public boolean loopLock(int dbindex,String uniqueKey) throws DistributedLockException, RedisException {
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
            do {
                if (jedis!=null){
                    logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueKey);
                    jedis.select(dbindex);
                    result = jedis.set(uniqueKey, uniqueKey, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, DEFAULT_SINGLE_EXPIRE_TIME*10);
                }
                if (cluster!=null){
                    logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueKey);
                    cluster.select(dbindex);
                    result = cluster.set(uniqueKey, uniqueKey, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, DEFAULT_SINGLE_EXPIRE_TIME*10);
                }
                if (LOCK_SUCCESS.equals(result)) {
                    return true;
                } else {
                    String desc = session.getData(uniqueKey);
                    logger.debug("key: " + uniqueKey + " locked by another business：" + desc);
                }
                Thread.sleep(300);
            } while (true);
        } catch (RedisException e) {
            logger.debug("获得分布式锁异常了" + e);
            throw e;
        } catch (Exception e) {
            throw new DistributedLockException(e);
        }
    }
    
    @Override
    public boolean tryLock(int dbindex,String uniqueKey) throws DistributedLockException, RedisException {
        return tryLock(dbindex,uniqueKey, DEFAULT_SINGLE_EXPIRE_TIME, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public boolean tryLock(int dbindex,String uniqueKey, long timeout, TimeUnit unit) throws DistributedLockException, RedisException {
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
            long nano = System.nanoTime();
            do {
                if (jedis!=null){
                    logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueKey);
                    jedis.select(dbindex);
                    result = jedis.set(uniqueKey, uniqueKey, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, timeout);
                }
                if (cluster!=null){
                    logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueKey);
                    cluster.select(dbindex);
                    result = cluster.set(uniqueKey, uniqueKey, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, timeout);
                }
                if (LOCK_SUCCESS.equals(result)) {
                    return true;
                } else { // 存在锁
                    String desc = session.getData(uniqueKey);
                    logger.debug("key: " + uniqueKey + " locked by another business：" + desc);
                }
                if (timeout == 0) {
                    break;
                }
                Thread.sleep(300);
            } while ((System.nanoTime() - nano) < unit.toNanos(timeout));
            return Boolean.FALSE;
        } catch (RedisException e) {
            logger.debug("获得分布式锁异常了" + e);
            throw e;
        } catch (Exception e) {
            throw new DistributedLockException(e);
        }
    }
    
    @Override
    public boolean unLock(int dbindex,String uniqueKey) throws DistributedLockException, RedisException {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Jedis jedis = sessionFactory.getRedisSession().getJedisClient();
        JedisCluster cluster = sessionFactory.getRedisSession().getClusterClient();
        if (jedis!=null){
            jedis.select(dbindex);
            Object result = jedis.eval(script, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueKey));
            if (SUCCESS== BaseStringUtil.intValue(result)) {
                return true;
            }
        }
        if (cluster!=null){
            cluster.select(dbindex);
            Object result = cluster.eval(script, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueKey));
            if (SUCCESS== BaseStringUtil.intValue(result)) {
                return true;
            }
        }
        logger.debug("解分布式锁失败");
        return false;
    }
    
    @Override
    public boolean isLock(int dbindex,String uniqueKey) throws DistributedLockException, RedisException {
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
                jedis.select(dbindex);
                result = jedis.get(uniqueKey);
            }
            if (cluster!=null){
                cluster.select(dbindex);
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
