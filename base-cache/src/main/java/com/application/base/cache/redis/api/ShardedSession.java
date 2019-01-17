package com.application.base.cache.redis.api;

import com.application.base.cache.redis.exception.RedisException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ShardedJedis;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @desc Redis 访问会话
 * @author 孤狼
 */
public interface ShardedSession {
    
    /**
     * 返回的标识
     */
    long LOCK_SUCCESS = 1;
    /**
     * 这个参数我们填的是NX，意思是SET IF NOT EXIST,即当key不存在时,我们进行set操作;若key已经存在,则不做任何操作.
     */
    String SET_IF_NOT_EXIST = "NX";
    /**
     * 这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置，具体时间由第五个参数决定
     */
    String SET_WITH_EXPIRE_TIME = "PX";
    
    /**
     * 默认时间设置,一天时间
     */
    int DEFAULT_TIMEOUT = 60 * 60 * 24;
    
    /**
     * 是否为空.
     * @param value
     * @return
     */
    default boolean isEmpty(Object value) {
        if (null==value || "".equals(value)){
            return true;
        }
        String result = stringValue(value);
        String str1= "null";
        if ("".equals(result) || result.length()==0 || str1.equalsIgnoreCase(result)){
            return true;
        }
        return  false;
    }
    
    /**
     * 转换成字符串值.
     * @param value
     * @return
     */
    default String stringValue(Object value) {
        String result = Objects.toString(value, "");
        return result.trim();
    }
    
    
    /**
     * 根据key取出值，并根据Class类型转化为相应类对象
     * 取出的值为JSON字符串，将字符串转化为对象，使用前请确认存数数据类型
     * 可能会抛出转化失败异常
     * @param clazz
     * @param key
     * @param <T>
     * @return
     * @throws RedisException
     */
    <T> T getTypeObject(Class<T> clazz, String key) throws RedisException;

    /**
     * 根据key获取redis存入的值，String类型
     * 如果存入的是Object对象则取出对象的JSON串
     * @param key
     * @return
     * @throws RedisException
     */
    String getData(String key) throws RedisException;

    /**
     * 根据key存入值Value
     * 将Object类型的值转化为JSON字符串存入redis
     * @param key
     * @param value
     * @throws RedisException
     */
    void setData(String key, Object value) throws RedisException;
    
    /**
     * 根据key将值value存入redis,过期时间为timeout，单位：秒
     * 默认86400秒
     * @param key
     * @param value
     * @param timeout
     * @throws RedisException
     */
    void setData(String key, Object value, int timeout) throws RedisException;

    /**
     * 判断是否存在key
     * @param key
     * @return
     * @throws RedisException
     */
    boolean contains(String key) throws RedisException;

    /**
     * 获得当前键剩余过期时间：(秒)
     * @param key
     * @return
     * @throws RedisException
     */
    long getKeyLastTime(String key) throws RedisException;

    /**
     * 删除缓存
     * @param key
     * @return
     * @throws RedisException
     */
    long delete(String key) throws RedisException;
    
    /**
     * 1. 当前没有锁（key不存在），那么就进行加锁操作，并对锁设置个有效期，同时value表示加锁的客户端。2. 已有锁存在，不做任何操作。
     * @param key
     * @param value
     * @return
     * @throws RedisException
     */
    String set(String key, String value) throws RedisException;
    
    /**
     * 1. 当前没有锁（key不存在），那么就进行加锁操作，并对锁设置个有效期，同时value表示加锁的客户端。2. 已有锁存在，不做任何操作。
     * @param key ：我们使用key来当锁，因为key是唯一的
     * @param value ：
     * @param nxxx ：这个参数我们填的是NX，意思是SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
     * @param expx ：这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置，具体时间由第五个参数决定。
     * @param expireTime ：
     * @return
     */
    String set(String key, String value, String nxxx, String expx, long expireTime) throws RedisException;
    
    /**
     * 当且仅当 key 不存在，将 key 的值设为 value ，并返回1；若给定的 key 已经存在，则 SETNX 不做任何动作，并返回0。
     * @param key
     * @param value
     * @return
     * @throws RedisException
     */
    long setnx(String key, Object value) throws RedisException;
    
    /**
     * 从队列的右边入队一个元素或多个元素.
     * @param key
     * @param value
     * @return
     * @throws RedisException
     */
    long rpush(String key, String... value) throws RedisException;
    
    /**
     * 从队列的右边出队一个元素.
     * @param key
     * @return
     * @throws RedisException
     */
    String rpop(String key) throws RedisException;
    
    /**
     * 从队列的左边入队一个或多个元素
     * @param key
     * @param value
     * @return
     * @throws RedisException
     */
    long lpush(String key, String... value) throws RedisException;

    /**
     * 从队列的左边出队一个元素
     * @param key
     * @return
     * @throws RedisException
     */
    String lpop(String key) throws RedisException;

    /**
     * 设定键有效期,到期时间后键不会在Redis中使用。
     * @param key
     * @param seconds
     * @return
     * @throws RedisException
     */
    long expire(String key, int seconds) throws RedisException;

    /**
     * 发布消息
     * @param chanel
     * @param msg
     * @throws RedisException
     */
    void publish(String chanel, Object msg) throws RedisException;
    
    /**
     * 给指定的 key 增加1
     * @param key
     * @return
     * @throws RedisException
     */
    long incrNum(String key) throws RedisException;

    /**
     * 给指定的key 的值:(index) 增加1
     * @param key
     * @param index
     * @return
     * @throws RedisException
     */
    long incrByNum(String key, long index) throws RedisException;

    /**
     * 给指定的 key 减少 1
     * @param key
     * @return
     * @throws RedisException
     */
    long decrNum(String key) throws RedisException;
    
    /**
     * 给指定的key 的值:(index) 减少1
     * @param key
     * @param index
     * @return
     * @throws RedisException
     */
    long decrByNum(String key, long index) throws RedisException;
    
    /**
     * 返回名称为key的list中start至end之间的元素
     * @param key
     * @param start
     * @param end
     * @return
     * @throws RedisException
     */
    List<String> betweenRange(String key, long start, long end) throws RedisException;
    
    /**
     * 向名称为key的set中添加元素value
     * @param key
     * @param value
     * @return
     * @throws RedisException
     */
    long addSet(String key, String... value) throws RedisException;
    
    /**
     * 删除名称为key的set中的元素value
     * @param key
     * @param value
     * @return
     * @throws RedisException
     */
    long removeSet(String key, String... value) throws RedisException;
    
    /**
     * 返回名称为key的set的所有元素
     * @param key
     * @return
     * @throws RedisException
     */
    Set<String> getSets(String key) throws RedisException;
    
    /**
     * 向名称为key的hash中添加元素field
     * @param key
     * @param field
     * @param value
     * @return
     * @throws RedisException
     */
    long addHash(String key, String field, String value) throws RedisException;
    
    /**
     * 删除名称为key的hash中键为field的域
     * @param key
     * @param value
     * @return
     * @throws RedisException
     */
    long removeHash(String key, String value) throws RedisException;
    
    /**
     * @param key
     * @param field
     * @return
     * @throws RedisException
     */
    String getHash(String key, String field) throws RedisException;
    
    /**
     * 返回名称为key的hash中field对应的value
     * @param key
     * @return
     * @throws RedisException
     */
    List<String> getHashs(String key) throws RedisException;
    
    /**
     * 获得 jedis 的分片客户端
     * @return
     * @throws RedisException
     */
    ShardedJedis getShardedClient() throws RedisException;
    
}
