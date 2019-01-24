package com.application.base.config.zookeeper.zk.lock;

import com.application.base.config.zookeeper.exception.DistributedLockException;
import com.application.base.config.zookeeper.exception.ZookeeperException;

import java.util.concurrent.TimeUnit;

/**
 * @desc 分布式锁
 * @author 孤狼
 */
public interface DistributedLock {

    /**
     * 获取锁成功
     */
    long SUCCESS = 1;
    
    /**
     * 根据uniqueKey构建分布式锁,对key进行锁定,循环获取锁,直到获取到锁为止
     * @param uniqueKey:唯一的key,
     * @param uniqueValue:请求的value,可以使用UUID来获取.
     * @param expireTime:毫秒数.
     * @return
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean getDistLock(String uniqueKey, String uniqueValue, int expireTime) throws DistributedLockException,ZookeeperException;
    
    /**
     * 释放 uniqueKey 对应的锁资源.
     * @param uniqueKey
     * @param uniqueValue
     * @return
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean releaseDistLock(String uniqueKey, String uniqueValue) throws DistributedLockException,ZookeeperException;
    
    /**
     * 根据uniqueKey构建分布式锁,对key进行锁定,循环获取锁,直到获取到锁为止
     * @param uniqueKey
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean loopLock(String uniqueKey) throws DistributedLockException,ZookeeperException;
    
    /**
     * 根据uniqueKey构建分布式锁,如果锁可用   立即返回true，  否则返回false
     * @param uniqueKey
     * @return
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean tryLock(String uniqueKey) throws DistributedLockException,ZookeeperException;
    
    /**
     * 在给定时间内获取锁,如果获取锁则返回true,否则返回false
     * @param uniqueKey
     * @param timeout
     * @param unit
     * @return
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean tryLock(String uniqueKey, long timeout, TimeUnit unit) throws DistributedLockException,ZookeeperException;
    
    /**
     * 释放锁
     * @param uniqueKey
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean unLock(String uniqueKey) throws DistributedLockException,ZookeeperException;
    
    /**
     * 是否锁住的.
     * @param uniqueKey
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean isLock(String uniqueKey) throws DistributedLockException,ZookeeperException;
    
    
    /*************************************************************************************************************************************************************************/
    /******************************************************************************** 按照数据库选择分布式锁的实现 *******************************************************************/
    /*************************************************************************************************************************************************************************/
    
    /**
     * 根据uniqueKey构建分布式锁,对key进行锁定,循环获取锁,直到获取到锁为止
     * @param uniqueKey:唯一的key,
     * @param uniqueValue:请求的value,可以使用UUID来获取.
     * @param expireTime:毫秒数.
     * @return
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean getDistLock(int dbindex, String uniqueKey, String uniqueValue, int expireTime) throws DistributedLockException,ZookeeperException;
    
    /**
     * 释放 uniqueKey 对应的锁资源.
     * @param uniqueKey
     * @param uniqueValue
     * @return
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean releaseDistLock(int dbindex, String uniqueKey, String uniqueValue) throws DistributedLockException,ZookeeperException;
    
    /**
     * 根据uniqueKey构建分布式锁,对key进行锁定,循环获取锁,直到获取到锁为止
     * @param uniqueKey
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean loopLock(int dbindex, String uniqueKey) throws DistributedLockException,ZookeeperException;
    
    /**
     * 根据uniqueKey构建分布式锁,如果锁可用   立即返回true，  否则返回false
     * @param uniqueKey
     * @return
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean tryLock(int dbindex, String uniqueKey) throws DistributedLockException,ZookeeperException;
    
    /**
     * 在给定时间内获取锁,如果获取锁则返回true,否则返回false
     * @param uniqueKey
     * @param timeout
     * @param unit
     * @return
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean tryLock(int dbindex, String uniqueKey, long timeout, TimeUnit unit) throws DistributedLockException,ZookeeperException;
    
    /**
     * 释放锁
     * @param uniqueKey
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean unLock(int dbindex, String uniqueKey) throws DistributedLockException,ZookeeperException;
    
    /**
     * 是否锁住的.
     * @param uniqueKey
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean isLock(int dbindex, String uniqueKey) throws DistributedLockException,ZookeeperException;
    
}
