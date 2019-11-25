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
     * 获取zk 的分布式锁.
     * @param baseNodeName:上锁的节点=(根节点+节点:/locks/00001--10000)
     * @param expireTime:上锁时间
     * @param unit:时间单位
     * @return
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean getDistLock(String baseNodeName, int expireTime,TimeUnit unit) throws DistributedLockException,ZookeeperException;
    
    /**
     * 释放锁资源.
     * @param baseNodeName:上锁的节点=(根节点+节点:/locks/00001--10000)
     * @return
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean releaseDistLock(String baseNodeName) throws DistributedLockException,ZookeeperException;
    
    /**
     * 是否锁住的.
     * @param baseNodeName
     * @return
     * @throws DistributedLockException
     * @throws ZookeeperException
     */
    boolean isLock(String baseNodeName) throws DistributedLockException,ZookeeperException;
    
}
