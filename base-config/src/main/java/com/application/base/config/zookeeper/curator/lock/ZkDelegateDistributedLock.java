package com.application.base.config.zookeeper.curator.lock;

import com.application.base.config.zookeeper.exception.*;
import com.application.base.config.zookeeper.factory.ZooKeeperSessionFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @desc zk 分布式锁实现.
 * @author 孤狼
 */
public class ZkDelegateDistributedLock implements ZkDistributedLock {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * session 工厂
     */
    private ZooKeeperSessionFactory sessionFactory;
    
    /**
     * 可重入排它锁
     */
    private InterProcessMutex processMutex;
    
    public ZooKeeperSessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public void setSessionFactory(ZooKeeperSessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public boolean getDistLock(String baseNodeName, int expireTime, TimeUnit unit) throws ZkDistributedLockException, ZooKeeperException {
        CuratorFramework client = sessionFactory.getZooKeeperSession().getCuratorClient();
        processMutex = new InterProcessMutex(client,baseNodeName);
        int count = 0,max=5;
        //重试5次，每次最大等待2s，也就是最大等待10s
        try {
            do{
                boolean flag = processMutex.acquire(2, TimeUnit.SECONDS);
                if (flag){
                    break;
                }
                //自增长"1"
                count++;
            }while (count>max);
        }catch (Exception e){
            logger.error("distributed lock acquire exception:{}",e);
        }
        if(count>max){
            logger.info("Thread:"+Thread.currentThread().getId()+" acquire distributed lock  busy");
            return false;
        }else{
            logger.info("Thread:"+Thread.currentThread().getId()+" acquire distributed lock  success");
            return true;
        }
    }
    
    @Override
    public boolean releaseDistLock(String baseNodeName) throws ZkDistributedLockException, ZooKeeperException {
        CuratorFramework client = sessionFactory.getZooKeeperSession().getCuratorClient();
        try {
            if(processMutex != null && processMutex.isAcquiredInThisProcess()){
                processMutex.release();
                client.delete().inBackground().forPath(baseNodeName);
                logger.info("Thread:"+Thread.currentThread().getId()+" release distributed lock  success");
            }
            return true;
        }catch (Exception e){
            logger.error("Thread:"+Thread.currentThread().getId()+" release distributed lock  exception:",e);
        }
        return false;
    }
    
    @Override
    public boolean isLock(String baseNodeName) throws ZkDistributedLockException, ZooKeeperException {
        CuratorFramework client = sessionFactory.getZooKeeperSession().getCuratorClient();
        try {
            Stat stat = client.checkExists().forPath(baseNodeName);
            if (stat==null){
                return false;
            }
            return true;
        }catch (Exception e){
            logger.error("Thread:"+Thread.currentThread().getId()+" release distributed lock  exception:",e);
        }
        return false;
    }
}
