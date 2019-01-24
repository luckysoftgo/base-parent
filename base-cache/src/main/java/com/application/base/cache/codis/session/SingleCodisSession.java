package com.application.base.cache.codis.session;

import com.application.base.cache.codis.architecture.cache.CacheClient;
import com.application.base.cache.redis.api.RedisSession;
import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redis.jedis.JedisValidUtil;
import com.application.base.cache.redis.jedis.session.JedisSimpleSession;
import com.application.base.utils.common.BaseStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @desccodis 单实例缓存
 *
 * @author 孤狼
 */
public class SingleCodisSession extends JedisSimpleSession implements RedisSession {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private CacheClient client;
	
	public CacheClient getClient() {
		if (null==client){
			logger.error("[codis错误:{}]","获得codis实例对象为空");
			throw new RedisException("获得codis实例对象为空");
		}
		return client;
	}
	public void setClient(CacheClient client) {
		this.client = client;
	}
	
	public SingleCodisSession() {
	}
	public SingleCodisSession(CacheClient client) {
		this.client = client;
	}

	@Override
	public void setData(String key, Object value) throws RedisException {
		super.setData(key, value);
	}

	@Override
	public <T> T getTypeObject(Class<T> clazz, String key) throws RedisException {
		return super.getTypeObject(clazz, key);
	}

	@Override
	public String getData(String key) throws RedisException {
		String objStr;
		try {
			JedisValidUtil.codisValidated(logger,key);
			Object o = getClient().get(key);
			if (o == null) {
				return null;
			}
			objStr = Objects.toString(o);
			logger.debug("[codis操作，根据key:{},获得:{}]", key, objStr);
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
		return objStr;
	}
	
	@Override
	public List<String> getData(String... keys) throws RedisException {
		try {
			JedisValidUtil.codisValidated(logger,keys);
			List<String> instances = getClient().mget(keys);
			if (instances == null) {
				return null;
			}
			logger.debug("[codis操作，根据key:{},获得:{}]", keys, instances);
			return instances;
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
	}

	@Override
	public void setData(String key, Object value, int timeout) throws RedisException {
		try {
			JedisValidUtil.codisValidated(logger,key,value);
			if (timeout == 0) {
				timeout = DEFAULT_TIMEOUT;
			}
			getClient().setExpire(key, timeout, BaseStringUtil.stringValue(value));
			logger.info("[codis操作，存入key:{},value:{}]", key, BaseStringUtil.stringValue(value));
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
	}

	@Override
	public boolean contains(String key) throws RedisException {
		try {
			JedisValidUtil.codisValidated(logger,key);
			return getClient().exists(key);
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
	}

	@Override
	public long getKeyLastTime(String key) throws RedisException {
		try {
			JedisValidUtil.codisValidated(logger,key);
			long timeout = getClient().ttl(key);
			logger.info("codis操作，key:{},剩余超时时间为：{}", key, timeout);
			return timeout;
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
	}

	@Override
	public long delete(String key) throws RedisException {
		try {
			JedisValidUtil.codisValidated(logger,key);
			return getClient().del(new String[]{key});
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
	}

	@Override
	public void flushAll() throws RedisException {
		throw new RedisException("codis操作，unsupport method!");
	}

	@Override
	public long setnx(String key, Object value) throws RedisException {
		try {
			JedisValidUtil.codisValidated(logger,key,value);
			long result = getClient().setnx(key, value.toString());
			return result;
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
	}
	
	@Override
	public long rpush(String key, String... value) throws RedisException {
		try {
			JedisValidUtil.codisValidated(logger,key,value);
			long result = getClient().rpush(key,value);
			logger.debug("[存入队列key:{},value:{}]" ,key,stringValue(value));
			return result;
		} catch (Exception e) {
			logger.error("[codis错误:{}]",e);
			throw new RedisException(e);
		}
	}
	
	@Override
	public String rpop(String key) throws RedisException {
		try {
			JedisValidUtil.codisValidated(logger,key);
			String o = getClient().rpop(key);
			if(isEmpty(o)) {
				return null;
			}
			logger.debug("[根据key:{},获得:{}]",key,o);
			return o;
		} catch (Exception e) {
			logger.error("[codis错误:{}]",e);
			throw new RedisException(e);
		}
	}
	
	@Override
	public long lpush(String key, String... value) throws RedisException {
		try {
			JedisValidUtil.codisValidated(logger,key,value);
			long result = getClient().lpush(key,value);
			logger.debug("[存入队列key:{},value:{}]" ,key,stringValue(value));
			return result;
		} catch (Exception e) {
			logger.error("[codis错误:{}]",e);
			throw new RedisException(e);
		}
	}
	
	@Override
	public String lpop(String key) throws RedisException {
		try {
			JedisValidUtil.codisValidated(logger,key);
			String o = getClient().lpop(key);
			if(isEmpty(o)) {
				return null;
			}
			logger.debug("[根据key:{},获得:{}]",key,o);
			return o;
		} catch (Exception e) {
			logger.error("[codis错误:{}]",e);
			throw new RedisException(e);
		}
	}
	
	@Override
	public long expire(String key, int seconds) throws RedisException {
		JedisValidUtil.codisValidated(logger,key);
		if (seconds <= 0) {
			logger.info("[codis操作，超时时间应为大于零的整数,输入值为{}！]", seconds);
			throw new RedisException("存入值为空!");
		}
		Boolean result = getClient().expire(key, seconds);
		return result ? 1L : 0;
	}
	
	@Override
	public List<String> betweenRange(String key, long start, long end) throws RedisException {
		JedisValidUtil.codisValidated(logger,key);
		return getClient().lrange(key, start,end);
	}
	
	@Override
	public long addSet(String key, String... value) throws RedisException {
		JedisValidUtil.redisValidated(logger, key, value);
		return getClient().sadd(key, value);
	}
	
	@Override
	public long removeSet(String key, String... value) throws RedisException {
		JedisValidUtil.redisValidated(logger, key, value);
		return getClient().srem(key, value);
	}
	
	@Override
	public Set<String> getSets(String key) throws RedisException {
		JedisValidUtil.redisValidated(logger, key);
		return getClient().smembers(key);
	}
	
	@Override
	public long addHash(String key, String field, String value) throws RedisException {
		JedisValidUtil.redisValidated(logger, key, field);
		return getClient().hset(key, field, value);
	}
	
	@Override
	public long removeHash(String key, String field) throws RedisException {
		JedisValidUtil.redisValidated(logger, key, field);
		return getClient().hdel(key, new String[]{field});
	}
	
	@Override
	public String getHash(String key, String field) throws RedisException {
		JedisValidUtil.redisValidated(logger, key, field);
		return getClient().hget(key, field);
	}
	
	@Override
	public List<String> getHashs(String key) throws RedisException {
		JedisValidUtil.redisValidated(logger, key);
		return getClient().hvals(key);
	}
	
	@Override
	public void publish(String chanel, Object msg) {
		super.publish(chanel, msg);
	}

	@Override
	public void publish(String chanel, String msgJson) {
		super.publish(chanel, msgJson);
	}

	@Override
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		super.subscribe(jedisPubSub, channels);
	}

}
