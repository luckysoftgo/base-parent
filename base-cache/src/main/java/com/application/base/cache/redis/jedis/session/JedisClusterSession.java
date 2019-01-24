package com.application.base.cache.redis.jedis.session;

import com.application.base.cache.redis.api.RedisSession;
import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redis.jedis.JedisValidUtil;
import com.application.base.utils.json.JsonConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.Set;

/**
 * @desc :redis 集群配置
 * @author 孤狼
 */
public class JedisClusterSession implements RedisSession {

	private Logger logger = LoggerFactory.getLogger(getClass());
    
	/** 默认的 {@code JSON} 完整日期/时间字段的格式化模式。 */
    /**
     *  被 装配到  Spring 工厂
     */
	private JedisCluster jedisCluster;

	public JedisCluster getClusterJedis() {
        if (null== jedisCluster){
            logger.error("[redis错误:{}]","获得redis集群实例对象为空");
            throw new RedisException("获得redis集群实例对象为空");
        }
		return jedisCluster;
	}

	public void setClusterJedis(JedisCluster jedis) {
		this.jedisCluster = jedis;
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
            Object o = getClusterClient().get(key);
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
    public List<String> getData(String... keys) throws RedisException {
        JedisValidUtil.redisValidated(logger,keys);
        return getClusterClient().mget(keys);
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
            getClusterClient().setex(key, timeout,stringValue(value));
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
            return getClusterClient().exists(key);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
   
    @Override
    public long getKeyLastTime(String key)  throws RedisException {
        try {
            JedisValidUtil.redisValidated(logger,key);
            long timeout = getClusterClient().ttl(key);
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
            return this.getClusterClient().del(key);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }

   
    @Override
    public void flushAll() throws RedisException {
        try {
            //Delete currently selected DB ...
        	this.getClusterClient().flushDB();
            //Delete all the keys of all the existing databases ...
            this.getClusterClient().flushAll();
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    @Override
    public String set(String key, String value) throws RedisException {
        try {
            JedisValidUtil.redisValidated(logger,key,value);
            return this.getClusterClient().set(key,value);
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
            return this.getClusterClient().set(key,value,nxxx,expx,expireTime);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    
    @Override
    public long setnx(String key, Object value) throws RedisException {
        try {
            JedisValidUtil.redisValidated(logger,key,value);
            long result = getClusterClient().setnx(key,value.toString());
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
            long result =  getClusterClient().rpush(key,value);
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
            String o = getClusterClient().rpop(key);
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
            JedisValidUtil.redisValidated(logger,key,value);
            long result =  getClusterClient().lpush(key,value);
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
            String o = getClusterClient().lpop(key);
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
        JedisValidUtil.redisValidated(logger,key);
        if (seconds <= 0) {
            logger.info("[超时时间应为大于零的整数,输入值为{}！]", seconds);
            throw new RedisException("存入值为空!");
        }
        return getClusterClient().expire(key, seconds);
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

    /**
     * 发布消息
     */
    @Override
    public void publish(String chanel, String msgJson) {
        if (isEmpty(chanel)) {
            logger.info("[chanel:{}为空！]", chanel);
            throw new RedisException("chanel为空!");
        }
        if (isEmpty(msgJson)) {
            logger.info("[msgJson:{}为空！]", msgJson);
            throw new RedisException("发送msgJson为空!");
        }
    	getClusterClient().publish(chanel, msgJson);
    }

    /**
     * 注册监听
     */
    @Override
    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
    	if (jedisPubSub == null) {
            logger.info("[jedisPubSub:{}为空！]", jedisPubSub);
            throw new RedisException("jedisPubSub为空!");
        }
    	if (channels == null) {
            logger.info("[channels:{}为空！]", channels+"");
            throw new RedisException("发送channels为空!");
        }
    	getClusterClient().subscribe(jedisPubSub, channels);
    }
    
    @Override
    public void psubscribe(JedisPubSub jedisPubSub, String... patterns) throws RedisException {
        if (jedisPubSub == null) {
            logger.info("[jedisPubSub:{}为空！]", jedisPubSub);
            throw new RedisException("jedisPubSub为空!");
        }
        if (patterns == null) {
            logger.info("[channels:{}为空！]", patterns+"");
            throw new RedisException("发送channels为空!");
        }
        getClusterClient().psubscribe(jedisPubSub, patterns);
    }
    
    @Override
	public long incrNum(String key) throws RedisException {
        JedisValidUtil.redisValidated(logger,key);
        return getClusterClient().incr(key);
	}

	@Override
	public long incrByNum(String key, long index) throws RedisException {
        JedisValidUtil.redisValidated(logger,key);
        return getClusterClient().incrBy(key, index);
	}

	@Override
	public long decrNum(String key) throws RedisException {
        JedisValidUtil.redisValidated(logger,key);
        return getClusterClient().decr(key);
	}

	@Override
	public long decrByNum(String key, long index) throws RedisException {
        JedisValidUtil.redisValidated(logger,key);
        return getClusterClient().decrBy(key, index);
	}
    
    @Override
    public List<String> betweenRange(String key, long start, long end) throws RedisException {
        JedisValidUtil.redisValidated(logger,key);
        return getClusterClient().lrange(key, start,end);
    }
    
    @Override
    public long addSet(String key, String... value) throws RedisException {
        JedisValidUtil.redisValidated(logger, key, value);
        return getClusterClient().sadd(key, value);
    }
    
    @Override
    public long removeSet(String key, String... value) throws RedisException {
        JedisValidUtil.redisValidated(logger, key, value);
        return getClusterClient().srem(key, value);
    }
    
    @Override
    public Set<String> getSets(String key) throws RedisException {
        JedisValidUtil.redisValidated(logger, key);
        return getClusterClient().smembers(key);
    }
    
    @Override
    public long addHash(String key, String field, String value) throws RedisException {
        JedisValidUtil.redisValidated(logger, key, field);
        return getClusterClient().hset(key, field, value);
    }
    
    @Override
    public long removeHash(String key, String field) throws RedisException {
        JedisValidUtil.redisValidated(logger, key, field);
        return getClusterClient().hdel(key, field);
    }
    
    @Override
    public String getHash(String key, String field) throws RedisException {
        JedisValidUtil.redisValidated(logger, key, field);
        return getClusterClient().hget(key, field);
    }
    
    @Override
    public List<String> getHashs(String key) throws RedisException {
        JedisValidUtil.redisValidated(logger, key);
        return getClusterClient().hvals(key);
    }
    
    @Override
    @Deprecated
    public Jedis getJedisClient() throws RedisException {
        return null;
    }
    
    @Override
    public JedisCluster getClusterClient() throws RedisException {
        return getClusterJedis();
    }
    
    /*************************************************************************************************************************************************************************/
    /******************************************************************************** 按照数据库选择分布式锁的实现 *******************************************************************/
    /*************************************************************************************************************************************************************************/
    
    /**
     * Redis数据库切换
     * @param dbIndex
     */
    private synchronized void changeDB(int dbIndex) {
        try {
            getClusterClient().select(dbIndex);
        } catch (Exception e) {
            logger.error("[redis错误:{}]", e);
            throw new RedisException(e);
        }
    }

    @Override
    public synchronized void setData(int dbIndex, String key, Object value) throws RedisException {
        changeDB(dbIndex);
        setData(key, value);
    }

    @Override
    public synchronized  <T> T getTypeObject(int dbIndex, Class<T> clazz, String key) throws RedisException {
        changeDB(dbIndex);
        return getTypeObject(clazz, key);
    }

    @Override
    public synchronized  String getData(int dbIndex, String key) throws RedisException {
        changeDB(dbIndex);
        return getData(key);
    }
    
    @Override
    public synchronized List<String> getData(int dbIndex, String... keys) throws RedisException {
        changeDB(dbIndex);
        return getData(keys);
    }
    
    @Override
    public synchronized  void setData(int dbIndex, String key, Object value, int timeout) throws RedisException {
        changeDB(dbIndex);
        setData(key, value, timeout);
    }

    @Override
    public synchronized  boolean contains(int dbIndex, String key) throws RedisException {
        changeDB(dbIndex);
        return contains(key);
    }

    @Override
    public synchronized  long getKeyLastTime(int dbIndex, String key) throws RedisException {
        changeDB(dbIndex);
        return getKeyLastTime(key);
    }

    @Override
    public synchronized  long delete(int dbIndex, String key) throws RedisException {
        changeDB(dbIndex);
        return delete(key);
    }
    
    @Override
    public synchronized void flushAll(int dbIndex) throws RedisException {
        changeDB(dbIndex);
        flushAll();
    }
    
    @Override
    public synchronized String set(int dbIndex, String key, String value) throws RedisException {
        changeDB(dbIndex);
        return set(key,value);
    }
    
    @Override
    public synchronized String set(int dbIndex, String key, String value, String nxxx, String expx, long expireTime) throws RedisException {
        changeDB(dbIndex);
        return set(key,value,nxxx,expx,expireTime);
    }
    
    @Override
    public synchronized  long setnx(int dbIndex, String key, Object value) throws RedisException {
        changeDB(dbIndex);
        return setnx(key, value);
    }
    
    @Override
    public synchronized long rpush(int dbIndex, String key, String... value) throws RedisException {
        changeDB(dbIndex);
        return rpush(key,value);
    }
    
    @Override
    public synchronized String rpop(int dbIndex, String key) throws RedisException {
        changeDB(dbIndex);
        return rpop(key);
    }
    
    @Override
    public synchronized long lpush(int dbIndex, String key, String... value) throws RedisException {
        changeDB(dbIndex);
        return lpush(key,value);
    }
    
    @Override
    public synchronized String lpop(int dbIndex, String key) throws RedisException {
        changeDB(dbIndex);
        return lpop(key);
    }

    @Override
    public synchronized  long expire(int dbIndex, String key, int seconds) throws RedisException {
        changeDB(dbIndex);
        return expire(key, seconds);
    }

    @Override
    public synchronized  void publish(int dbIndex, String chanel, Object msg) {
        changeDB(dbIndex);
        publish(chanel, msg);
    }

    @Override
    public synchronized  void publish(int dbIndex, String chanel, String msgJson) {
        changeDB(dbIndex);
        publish(chanel, msgJson);
    }

    @Override
    public synchronized  void subscribe(int dbIndex, JedisPubSub jedisPubSub, String... channels) {
        changeDB(dbIndex);
        subscribe(jedisPubSub, channels);
    }
    
    @Override
    public synchronized void psubscribe(int dbIndex, JedisPubSub jedisPubSub, String... patterns) throws RedisException {
        changeDB(dbIndex);
        psubscribe(jedisPubSub, patterns);
    }
    
    @Override
	public synchronized  long incrNum(int dbIndex, String key) throws RedisException {
		changeDB(dbIndex);
		return incrNum(key);
	}

	@Override
	public synchronized  long incrByNum(int dbIndex, String key, long index) throws RedisException {
		changeDB(dbIndex);
		return incrByNum(key, index);
	}

	@Override
	public synchronized  long decrNum(int dbIndex, String key) throws RedisException {
		changeDB(dbIndex);
		return decrNum(key);
	}

	@Override
	public synchronized  long decrByNum(int dbIndex, String key, long index) throws RedisException {
		changeDB(dbIndex);
		return decrByNum(key, index);
	}
    
    @Override
    public synchronized List<String> betweenRange(int dbIndex, String key, long start, long end) throws RedisException {
        changeDB(dbIndex);
        return betweenRange(key, start, end);
    }
    
    @Override
    public synchronized long addSet(int dbIndex, String key, String... value) throws RedisException {
        changeDB(dbIndex);
        return addSet(key,value);
    }
    
    @Override
    public synchronized long removeSet(int dbIndex, String key, String... value) throws RedisException {
        changeDB(dbIndex);
        return removeSet(key,value);
    }
    
    @Override
    public synchronized Set<String> getSets(int dbIndex, String key) throws RedisException {
        changeDB(dbIndex);
        return getSets(key);
    }
    
    @Override
    public synchronized long addHash(int dbIndex, String key, String field, String value) throws RedisException {
        changeDB(dbIndex);
        return addHash(key,field,value);
    }
    
    @Override
    public synchronized long removeHash(int dbIndex, String key, String field) throws RedisException {
        changeDB(dbIndex);
        return removeHash(key,field);
    }
    
    @Override
    public synchronized String getHash(int dbIndex, String key, String field) throws RedisException {
        changeDB(dbIndex);
        return getHash(key,field);
    }
    
    @Override
    public synchronized List<String> getHashs(int dbIndex, String key) throws RedisException {
        changeDB(dbIndex);
        return getHashs(key);
    }
}
