package com.application.base.cache.redis.jedis.session;

import com.application.base.cache.redis.api.DistributedSession;
import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redis.jedis.JedisUtil;
import com.application.base.utils.json.JsonConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ShardedJedis;

import java.util.List;
import java.util.Set;

/**
 * @desc :redis 分布式集群配置.
 * @author 孤狼.
 */
public class JedisDistributedSession implements DistributedSession {

	private Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 默认时间设置,一天时间
     */
	protected static final int DEFAULT_TIMEOUT = 60 * 60 * 24;

	/** 默认的 {@code JSON} 完整日期/时间字段的格式化模式。 */
    /**
     *  被 装配到  Spring 工厂
     */
	private ShardedJedis distributedJedis;

	public ShardedJedis getDistributedJedis() {
        if (null== distributedJedis){
            logger.error("[redis错误:{}]","获得redis集群实例对象为空");
            throw new RedisException("获得redis集群实例对象为空");
        }
		return distributedJedis;
	}

	public void setDistributedJedis(ShardedJedis distributedJedis) {
		this.distributedJedis = distributedJedis;
	}
	  
    @Override
    public <T> T getTypeObject(Class<T> clazz, String key) throws RedisException {
        T t = null;
        String objStr = getData(key);
        logger.debug("[根据key:{},获得:{}]",key,objStr);
        try {
            t = JsonConvertUtils.fromJson(objStr,clazz);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
        return t;
    }

   
    @Override
    public String getData(String key) throws RedisException {
        String objStr;
        try {
            JedisUtil.redisValidated(logger,key);
            Object o = getDistributedJedis().get(key);
            if(isEmpty(o)) {
				return null;
			}
            objStr = stringValue(o);
            logger.debug("[根据key:{},获得:{}]",key,objStr);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
        return objStr;
    }
    
    @Override
    public void setData(String key, Object value) throws RedisException {
        try {
            JedisUtil.redisValidated(logger,key,value);
            setData(key,stringValue(value),DEFAULT_TIMEOUT);
            logger.debug("[存入key:{},value:{}]" ,key,stringValue(value));
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
   
    @Override
    public void setData(String key, Object value, int timeout)  throws RedisException {
        try {
            JedisUtil.redisValidated(logger,key,value);
            if (timeout == 0) {
				timeout = DEFAULT_TIMEOUT;
			}
            getDistributedJedis().setex(key, timeout,stringValue(value));
            logger.info("[存入key:{},value:{}]" ,key,stringValue(value));
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
   
    @Override
    public boolean contains(String key) throws RedisException {
        try {
            JedisUtil.redisValidated(logger,key);
            return getDistributedJedis().exists(key);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
   
    @Override
    public long getKeyLastTime(String key)  throws RedisException {
        try {
            JedisUtil.redisValidated(logger,key);
            long timeout = getDistributedJedis().ttl(key);
            logger.info("key:{},剩余超时时间为：{}",key,timeout);
            return timeout;
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
	
	@Override
    public long delete(String key)  throws RedisException {
        try {
            JedisUtil.redisValidated(logger,key);
            return this.getDistributedJedis().del(key);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }

    @Override
    public String set(String key, String value) throws RedisException {
        try {
            JedisUtil.redisValidated(logger,key,value);
            return this.getDistributedJedis().set(key,value);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    @Override
    public String set(String key, String value, String nxxx, String expx, long expireTime) throws RedisException {
        try {
            JedisUtil.redisValidated(logger,key,value);
            if (isEmpty(nxxx)) {
                //SET IF NOT EXIST
                nxxx = SET_IF_NOT_EXIST;
            }
            if (isEmpty(expx)) {
                nxxx = SET_WITH_EXPIRE_TIME;
            }
            return this.getDistributedJedis().set(key,value,nxxx,expx,expireTime);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    
    @Override
    public long setnx(String key, Object value) throws RedisException {
        try {
            JedisUtil.redisValidated(logger,key,value);
            long result = getDistributedJedis().setnx(key,value.toString());
            return result;
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    @Override
    public long rpush(String key, String... value) throws RedisException {
        try {
            JedisUtil.redisValidated(logger,key,value);
            long result =  getDistributedJedis().rpush(key,value);
            logger.debug("[存入队列key:{},value:{}]" ,key,stringValue(value));
            return result;
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    @Override
    public String rpop(String key) throws RedisException {
        try {
            JedisUtil.redisValidated(logger,key);
            String o = getDistributedJedis().rpop(key);
            if(isEmpty(o)) {
                return null;
            }
            logger.debug("[根据key:{},获得:{}]",key,o);
            return o;
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    @Override
    public long lpush(String key, String... value) throws RedisException {
        try {
            JedisUtil.redisValidated(logger,key,value);
            long result = getDistributedJedis().lpush(key,value);
            logger.debug("[存入队列key:{},value:{}]" ,key,stringValue(value));
            return result;
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    @Override
    public String lpop(String key) throws RedisException {
        try {
            JedisUtil.redisValidated(logger,key);
            String o = getDistributedJedis().lpop(key);
            if(isEmpty(o)) {
                return null;
            }
            logger.debug("[根据key:{},获得:{}]",key,o);
            return o;
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    @Override
    public long expire(String key, int seconds) throws RedisException {
        JedisUtil.redisValidated(logger,key);
        if (seconds <= 0) {
            logger.info("[超时时间应为大于零的整数,输入值为{}！]", seconds);
            throw new RedisException("存入值为空!");
        }
        return getDistributedJedis().expire(key, seconds);
    }
    
    @Override
	public long incrNum(String key) throws RedisException {
        JedisUtil.redisValidated(logger,key);
        return getDistributedJedis().incr(key);
	}

	@Override
	public long incrByNum(String key, long index) throws RedisException {
        JedisUtil.redisValidated(logger,key);
        return getDistributedJedis().incrBy(key, index);
	}

	@Override
	public long decrNum(String key) throws RedisException {
        JedisUtil.redisValidated(logger,key);
        return getDistributedJedis().decr(key);
	}

	@Override
	public long decrByNum(String key, long index) throws RedisException {
        JedisUtil.redisValidated(logger,key);
        return getDistributedJedis().decrBy(key, index);
	}
    
    @Override
    public List<String> betweenRange(String key, long start, long end) throws RedisException {
        JedisUtil.redisValidated(logger,key);
        return getDistributedJedis().lrange(key, start,end);
    }
    
    @Override
    public long addSet(String key, String... value) throws RedisException {
        JedisUtil.redisValidated(logger, key, value);
        return getDistributedJedis().sadd(key, value);
    }
    
    @Override
    public long removeSet(String key, String... value) throws RedisException {
        JedisUtil.redisValidated(logger, key, value);
        return getDistributedJedis().srem(key, value);
    }
    
    @Override
    public Set<String> getSets(String key) throws RedisException {
        JedisUtil.redisValidated(logger, key);
        return getDistributedJedis().smembers(key);
    }
    
    @Override
    public long addHash(String key, String field, String value) throws RedisException {
        JedisUtil.redisValidated(logger, key, field);
        return getDistributedJedis().hset(key, field, value);
    }
    
    @Override
    public long removeHash(String key, String field) throws RedisException {
        JedisUtil.redisValidated(logger, key, field);
        return getDistributedJedis().hdel(key, field);
    }
    
    @Override
    public String getHash(String key, String field) throws RedisException {
        JedisUtil.redisValidated(logger, key, field);
        return getDistributedJedis().hget(key, field);
    }
    
    @Override
    public List<String> getHashs(String key) throws RedisException {
        JedisUtil.redisValidated(logger, key);
        return getDistributedJedis().hvals(key);
    }
    
    @Override
    public ShardedJedis getClient() throws RedisException {
        return getDistributedJedis();
    }
    
}
