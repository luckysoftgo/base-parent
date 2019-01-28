package com.application.base.cache.redis.jedis.session;

import com.application.base.cache.redis.api.ShardedSession;
import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redis.jedis.JedisValidUtil;
import com.application.base.utils.json.JsonConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.ShardedJedis;

import java.util.List;
import java.util.Set;

/**
 * @desc :redis 分片 session 客户端.
 * @author 孤狼
 */
public class JedisShardedSession implements ShardedSession {

	private Logger logger = LoggerFactory.getLogger(getClass());
    
	/** 默认的 {@code JSON} 完整日期/时间字段的格式化模式。 */
    /**
     *  被 装配到  Spring 工厂
     */
	private ShardedJedis shardedJedis;

	public ShardedJedis getShardedJedis() {
        if (null== shardedJedis){
            logger.error("[redis错误:{}]","获得redis分片实例对象为空");
            throw new RedisException("获得redis分片实例对象为空");
        }
		return shardedJedis;
	}

	public void setShardedJedis(ShardedJedis shardedJedis) {
		this.shardedJedis = shardedJedis;
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
            JedisValidUtil.redisValidated(logger,key);
            Object o = getShardedClient().get(key);
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
            JedisValidUtil.redisValidated(logger,key,value);
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
            JedisValidUtil.redisValidated(logger,key,value);
            if (timeout == 0) {
				timeout = DEFAULT_TIMEOUT;
			}
            getShardedClient().setex(key, timeout,stringValue(value));
            logger.info("[存入key:{},value:{}]" ,key,stringValue(value));
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
   
    @Override
    public boolean contains(String key) throws RedisException {
        try {
            JedisValidUtil.redisValidated(logger,key);
            return getShardedClient().exists(key);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
   
    @Override
    public long getKeyLastTime(String key)  throws RedisException {
        try {
            JedisValidUtil.redisValidated(logger,key);
            long timeout = getShardedClient().ttl(key);
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
            JedisValidUtil.redisValidated(logger,key);
            return this.getShardedClient().del(key);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    @Override
    public String set(String key, String value) throws RedisException {
        try {
            JedisValidUtil.redisValidated(logger,key,value);
            return this.getShardedClient().set(key,value);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    @Override
    public String set(String key, String value, String nxxx, String expx, long expireTime) throws RedisException {
        try {
            JedisValidUtil.redisValidated(logger,key,value);
            if (isEmpty(nxxx)) {
                //SET IF NOT EXIST
                nxxx = SET_IF_NOT_EXIST;
            }
            if (isEmpty(expx)) {
                expx = SET_WITH_EXPIRE_TIME;
            }
            return this.getShardedClient().set(key,value,nxxx,expx,expireTime);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    
    @Override
    public long setnx(String key, Object value) throws RedisException {
        try {
            JedisValidUtil.redisValidated(logger,key,value);
            long result = getShardedClient().setnx(key,value.toString());
            return result;
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    @Override
    public long rpush(String key, String... value) throws RedisException {
        try {
            JedisValidUtil.redisValidated(logger,key,value);
            long result =  getShardedClient().rpush(key,value);
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
            JedisValidUtil.redisValidated(logger,key);
            String o = getShardedClient().rpop(key);
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
    public List<String> brpop(int timeout, String key) throws RedisException {
        try {
            JedisValidUtil.redisValidated(logger,key);
            List<String> values = getShardedClient().brpop(timeout,key);
            if(isEmpty(values)) {
                return null;
            }
            logger.debug("[根据key:{},获得:{}]",key,values.toString());
            return values;
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    @Override
    public long lpush(String key, String... value) throws RedisException {
        try {
            JedisValidUtil.redisValidated(logger,key,value);
            long result =  getShardedClient().lpush(key,value);
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
            JedisValidUtil.redisValidated(logger,key);
            String o = getShardedClient().lpop(key);
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
    public List<String> blpop(int timeout, String key) throws RedisException {
        try {
            JedisValidUtil.redisValidated(logger,key);
            List<String> values = getShardedClient().blpop(timeout,key);
            if(isEmpty(values)) {
                return null;
            }
            logger.debug("[根据key:{},获得:{}]",key,values.toString());
            return values;
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    @Override
    public long expire(String key, int seconds) throws RedisException {
        JedisValidUtil.redisValidated(logger,key);
        if (seconds <= 0) {
            logger.info("[超时时间应为大于零的整数,输入值为{}！]", seconds);
            throw new RedisException("存入值为空!");
        }
        return getShardedClient().expire(key, seconds);
    }
    
    /**
     * 发布消息
     */
    @Override
    public void publish(String chanel, Object msg) {
    	if (isEmpty(chanel)) {
            logger.info("[chanel:{}为空！]", chanel);
            throw new RedisException("chanel为空!");
        }
    	if (isEmpty(msg)) {
            logger.info("[msg:{}为空！]", msg);
            throw new RedisException("发送msg为空!");
        }
        String msgJson =null;
        try {
        	msgJson = JsonConvertUtils.toJson(msg);
		}
		catch (Exception e) {
            logger.error("[ Object 转换成 Json 失败！]", msg);
		}
        publish(chanel,msgJson);
    }
    
    @Override
	public long incrNum(String key) throws RedisException {
        JedisValidUtil.redisValidated(logger,key);
        return getShardedClient().incr(key);
	}

	@Override
	public long incrByNum(String key, long index) throws RedisException {
        JedisValidUtil.redisValidated(logger,key);
        return getShardedClient().incrBy(key, index);
	}

	@Override
	public long decrNum(String key) throws RedisException {
        JedisValidUtil.redisValidated(logger,key);
        return getShardedClient().decr(key);
	}

	@Override
	public long decrByNum(String key, long index) throws RedisException {
        JedisValidUtil.redisValidated(logger,key);
        return getShardedClient().decrBy(key, index);
	}
    
    @Override
    public List<String> betweenRange(String key, long start, long end) throws RedisException {
        JedisValidUtil.redisValidated(logger,key);
        return getShardedClient().lrange(key, start,end);
    }
    
    @Override
    public long addSet(String key, String... value) throws RedisException {
        JedisValidUtil.redisValidated(logger, key, value);
        return getShardedClient().sadd(key, value);
    }
    
    @Override
    public long removeSet(String key, String... value) throws RedisException {
        JedisValidUtil.redisValidated(logger, key, value);
        return getShardedClient().srem(key, value);
    }
    
    @Override
    public Set<String> getSets(String key) throws RedisException {
        JedisValidUtil.redisValidated(logger, key);
        return getShardedClient().smembers(key);
    }
    
    @Override
    public long addHash(String key, String field, String value) throws RedisException {
        JedisValidUtil.redisValidated(logger, key, field);
        return getShardedClient().hset(key, field, value);
    }
    
    @Override
    public long removeHash(String key, String field) throws RedisException {
        JedisValidUtil.redisValidated(logger, key, field);
        return getShardedClient().hdel(key, field);
    }
    
    @Override
    public String getHash(String key, String field) throws RedisException {
        JedisValidUtil.redisValidated(logger, key, field);
        return getShardedClient().hget(key, field);
    }
    
    @Override
    public List<String> getHashs(String key) throws RedisException {
        JedisValidUtil.redisValidated(logger, key);
        return getShardedClient().hvals(key);
    }
    
    @Override
    public ShardedJedis getShardedClient() throws RedisException {
        return getShardedJedis();
    }
}
