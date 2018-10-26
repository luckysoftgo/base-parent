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
    
    private RedisSessionFactory factory;
    
    public RedisSessionFactory getFactory(){
        return factory;
    }

    public void setFactory(RedisSessionFactory factory){
        this.factory = factory;
    }
    
    @Override
    public boolean getDistLock(String uniqueKey, String uniqueValue, int expireTime) throws DistributedLockException, RedisException {
        RedisSession session = null;
        try {
            session = factory.getRedisSession();
            logger.debug(" distributed lock key: " + uniqueKey + ",uniqueValue:" + uniqueValue);
            String result = session.set(uniqueKey, uniqueValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
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
        Jedis jedis = factory.getRedisSession().getJedisClient();
        JedisCluster cluster = factory.getRedisSession().getClusterClient();
        if (jedis!=null){
            Object result = jedis.eval(script, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueValue));
            if (SUCCESS== BaseStringUtil.intValue(result)) {
                return true;
            }
        }
        if (cluster!=null){
            Object result = cluster.eval(script, Collections.singletonList(uniqueKey), Collections.singletonList(uniqueValue));
            if (SUCCESS== BaseStringUtil.intValue(result)) {
                return true;
            }
        }
        logger.debug("解分布式锁失败");
        return false;
    }
    
    /**
     * 这种的弊端：(如果在执行成功之后,设置失效时间失败,则无法做到锁定失效的效果)
     * @param uniqueKey
     * @return
     * @throws DistributedLockException
     * @throws RedisException
     */
    @Override
    public boolean lock(String uniqueKey) throws DistributedLockException, RedisException {
        RedisSession session = null;
        try {
            session = factory.getRedisSession();
            do {
                logger.debug("lock key: " + uniqueKey);
                Long i = session.setnx(uniqueKey, uniqueKey);
                if (i == SUCCESS) {
                    //失效时间为:秒
                    session.expire(uniqueKey, DEFAULT_SINGLE_EXPIRE_TIME);
                    logger.debug("get lock, key: " + uniqueKey + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
                    return true;
                } else {
                    String desc = session.getData(uniqueKey);
                    logger.debug("key: " + uniqueKey + " locked by another business：" + desc);
                }
                Thread.sleep(300);
            } while (true);
        } catch (RedisException e) {
            throw e;
        } catch (Exception e) {
           throw new DistributedLockException(e);
        }
    }

    @Override
    public boolean tryLock(String uniqueKey) throws DistributedLockException, RedisException {
        return tryLock(uniqueKey, 0L, null);
    }

    @Override
    public boolean tryLock(String uniqueKey, long timeout, TimeUnit unit) throws DistributedLockException, RedisException {
        RedisSession session = null;
        try {
            session = factory.getRedisSession();
            long nano = System.nanoTime();
            do {
                logger.debug("try lock key: " + uniqueKey);
                Long i = session.setnx(uniqueKey, uniqueKey);
                if (i == SUCCESS) {
                    //失效时间为:秒
                    session.expire(uniqueKey, DEFAULT_SINGLE_EXPIRE_TIME);
                    logger.debug("get lock, key: " + uniqueKey + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
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
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DistributedLockException(e);
        }
    }

    @Override
    public boolean unLock(String uniqueKey) throws DistributedLockException, RedisException {
        try {
           long value  = factory.getRedisSession().delete(uniqueKey);
           if (value>0){
               return true;
           }
           return false;
        } catch (RedisException e) {
           throw e;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new DistributedLockException(e);
        }
    }
    
    @Override
    public boolean isLock(String uniqueKey) throws DistributedLockException, RedisException {
        try {
           String value = factory.getRedisSession().getData(uniqueKey);
           if (BaseStringUtil.isNotEmpty(value)){
               return true;
           }
           return false;
        } catch (RedisException e) {
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new DistributedLockException(e);
        }
    }

    /*************************************************************************************************************************************************************************/
    /******************************************************************************** 按照数据库选择分布式锁的实现 *******************************************************************/
    /*************************************************************************************************************************************************************************/
    
	@Override
	public synchronized boolean lock(int dbindex, String uniqueKey) throws DistributedLockException, RedisException {
		RedisSession session = null;
        try {
        	session = factory.getRedisSession();
            do {
                logger.debug("lock key: " + uniqueKey);
                Long i = session.setnx(dbindex,uniqueKey, uniqueKey);
                if (i == SUCCESS) {
                    //失效时间为:秒
                	session.expire(dbindex,uniqueKey, DEFAULT_SINGLE_EXPIRE_TIME);
                    logger.debug("get lock, key: " + uniqueKey + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
                    return true;
                } else {
                    String desc = session.getData(dbindex,uniqueKey);
                    logger.debug("key: " + uniqueKey + " locked by another business：" + desc);
                }
                Thread.sleep(300);
            } while (true);
        } catch (RedisException e) {
            throw e;
        } catch (Exception e) {
           throw new DistributedLockException(e);
        }
    }

	@Override
	public synchronized boolean tryLock(int dbindex, String uniqueKey) throws DistributedLockException, RedisException {
        return tryLock(dbindex,uniqueKey, 0L, null);
    }

	@Override
	public synchronized boolean tryLock(int dbindex, String uniqueKey, long timeout, TimeUnit unit)
			throws DistributedLockException, RedisException {
		RedisSession session = null;
        try {
        	session = factory.getRedisSession();
            long nano = System.nanoTime();
            do {
                logger.debug("try lock key: " + uniqueKey);
                Long i = session.setnx(dbindex,uniqueKey, uniqueKey);
                if (i == SUCCESS) {
                    //失效时间为:秒
                	session.expire(dbindex,uniqueKey, DEFAULT_SINGLE_EXPIRE_TIME);
                    logger.debug("get lock, key: " + uniqueKey + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
                    return true;
                } else { // 存在锁
                    String desc = session.getData(dbindex,uniqueKey);
                    logger.debug("key: " + uniqueKey + " locked by another business：" + desc);
                }
                if (timeout == 0) {
                    break;
                }
                Thread.sleep(300);
            } while ((System.nanoTime() - nano) < unit.toNanos(timeout));
            return false;
        } catch (RedisException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DistributedLockException(e);
        }
    }

	@Override
	public synchronized boolean unLock(int dbindex, String uniqueKey) throws DistributedLockException, RedisException {
        try {
           long value = factory.getRedisSession().delete(dbindex,uniqueKey);
           if (value > 0){
               return true;
           }
           return false;
        } catch (RedisException e) {
           throw e;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new DistributedLockException(e);
        }
    }
    
    @Override
    public boolean isLock(int dbindex,String uniqueKey) throws DistributedLockException, RedisException {
        try {
            String value = factory.getRedisSession().getData(dbindex,uniqueKey);
            if (BaseStringUtil.isNotEmpty(value)){
                return true;
            }
            return false;
        } catch (RedisException e) {
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new DistributedLockException(e);
        }
    }
}
