package com.application.base.cache.redis.jedis.lock;

import com.application.base.cache.redis.exception.DistributedLockException;
import com.application.base.cache.redis.exception.RedisException;

/**
 * @desc 分布式锁,按道理说只适用单机或者哨兵模式,集群性能欠佳.
 * https://redis.io/commands/set set命令详解.
 * @author 孤狼
 */
public interface DistributedLock {
    
    /**
     * 返回的标识
     */
    String LOCK_SUCCESS = "OK";
    /**
     * 这个参数我们填的是NX，意思是SET IF NOT EXIST,即当key不存在时,我们进行set操作;否则,则不做任何操作.
     */
    String SET_IF_NOT_EXIST = "NX";
    /**
     * 这个参数我们填的是XX，意思是SET IF ALREADY EXIST,即当key存在时,我们进行set操作;否则,则不做任何操作.
     */
    String SET_IF_ALREADY_EXIST = "XX";
    /**
     * 这个参数我们传的是EX，意思是我们要给这个key加一个过期的设置，时间单位是秒
     */
    String SET_WITH_EXPIRE_SECONDS = "EX";
    /**
     * 这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置，时间单位是毫秒
     */
    String SET_WITH_EXPIRE_MILLISECONDS = "PX";
    
    /**
     * 默认超时时间
     */
    int DEFAULT_SINGLE_EXPIRE_TIME = 500;
    
    /**
     * 获取锁成功
     */
    long SUCCESS = 1;
    
    /**
     * 根据uniqueKey构建分布式锁,对key进行锁定,循环获取锁,直到获取到锁为止
     * @param uniqueKey:唯一的key,
     * @param uniqueValue:请求的value,可以使用UUID来获取.
     * @return
     * @throws DistributedLockException
     * @throws RedisException
     */
    boolean getDistLock(String uniqueKey,String uniqueValue) throws DistributedLockException,RedisException;
    /**
     * 根据uniqueKey构建分布式锁,对key进行锁定,循环获取锁,直到获取到锁为止
     * @param uniqueKey:唯一的key,
     * @param uniqueValue:请求的value,可以使用UUID来获取.
     * @param expireTime:毫秒数.
     * @return
     * @throws DistributedLockException
     * @throws RedisException
     */
    boolean getDistLock(String uniqueKey,String uniqueValue,int expireTime) throws DistributedLockException,RedisException;
    /**
     * 释放 uniqueKey 对应的锁资源.
     * @param uniqueKey
     * @param uniqueValue
     * @return
     * @throws DistributedLockException
     * @throws RedisException
     */
    boolean releaseDistLock(String uniqueKey,String uniqueValue) throws DistributedLockException,RedisException;
    
    /**
     * 是否锁住的.
     * @param uniqueKey
     * @throws DistributedLockException
     * @throws RedisException
     */
    boolean isLock(String uniqueKey) throws DistributedLockException,RedisException;
    
    
    /*************************************************************************************************************************************************************************/
    /******************************************************************************** 按照数据库选择分布式锁的实现 *******************************************************************/
    /*************************************************************************************************************************************************************************/
    
    /**
     * 根据uniqueKey构建分布式锁,对key进行锁定,循环获取锁,直到获取到锁为止
     * @param uniqueKey:唯一的key,
     * @param uniqueValue:请求的value,可以使用UUID来获取.
     * @return
     * @throws DistributedLockException
     * @throws RedisException
     */
    boolean getDistLock(int dbIndex,String uniqueKey,String uniqueValue) throws DistributedLockException,RedisException;
    
    /**
     * 根据uniqueKey构建分布式锁,对key进行锁定,循环获取锁,直到获取到锁为止
     * @param uniqueKey:唯一的key,
     * @param uniqueValue:请求的value,可以使用UUID来获取.
     * @param expireTime:毫秒数.
     * @return
     * @throws DistributedLockException
     * @throws RedisException
     */
    boolean getDistLock(int dbIndex,String uniqueKey,String uniqueValue,int expireTime) throws DistributedLockException,RedisException;
    
    /**
     * 释放 uniqueKey 对应的锁资源.
     * @param uniqueKey
     * @param uniqueValue
     * @return
     * @throws DistributedLockException
     * @throws RedisException
     */
    boolean releaseDistLock(int dbIndex,String uniqueKey,String uniqueValue) throws DistributedLockException,RedisException;
    
    /**
     * 是否锁住的.
     * @param uniqueKey
     * @throws DistributedLockException
     * @throws RedisException
     */
    boolean isLock(int dbIndex,String uniqueKey) throws DistributedLockException,RedisException;
    
}
