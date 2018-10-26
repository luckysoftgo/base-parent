package com.application.base.cache.redisson.redisson.lock;


import com.application.base.cache.redisson.exception.JDistributedLockException;
import com.application.base.cache.redisson.exception.RedissonException;

import java.util.concurrent.TimeUnit;

/**
 * @desc 分布式锁
 * @author 孤狼
 */
public interface JDistributedLock {
    
    /**
     * 根据uniqueKey构建分布式锁,对key进行锁定,循环获取锁,直到获取到锁为止
     * @param uniqueKey
     * @throws JDistributedLockException
     * @throws RedissonException
     */
    boolean lock(String uniqueKey) throws JDistributedLockException,RedissonException;
    
    /**
     * 根据uniqueKey构建分布式锁,对key进行锁定,循环获取锁,直到获取到锁为止
     * @param uniqueKey
     * @param timeout
     * @param unit
     * @throws JDistributedLockException
     * @throws RedissonException
     */
    boolean lock(String uniqueKey,long timeout,TimeUnit unit) throws JDistributedLockException,RedissonException;
    
    /**
     * 根据uniqueKey构建分布式锁,如果锁可用   立即返回true，  否则返回false
     * @param uniqueKey
     * @return
     * @throws JDistributedLockException
     * @throws RedissonException
     */
    boolean tryLock(String uniqueKey) throws JDistributedLockException,RedissonException;
    
    /**
     * 在给定时间内获取锁,如果获取锁则返回true,否则返回false
     * @param uniqueKey
     * @param timeout
     * @param unit
     * @return
     * @throws JDistributedLockException
     * @throws RedissonException
     */
    boolean tryLock(String uniqueKey, long timeout, TimeUnit unit) throws JDistributedLockException,RedissonException;
    
    /**
     * 释放锁.
     * @param uniqueKey
     * @throws JDistributedLockException
     * @throws RedissonException
     */
    boolean unLock(String uniqueKey) throws JDistributedLockException,RedissonException;
    
    /**
     * 是否被锁住.
     * @param uniqueKey
     * @throws JDistributedLockException
     * @throws RedissonException
     */
    boolean isLock(String uniqueKey) throws JDistributedLockException,RedissonException;
    
}
