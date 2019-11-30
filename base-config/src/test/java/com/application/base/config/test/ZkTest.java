package com.application.base.config.test;

import com.application.base.config.zookeeper.config.ZooKeeperConfig;
import com.application.base.config.zookeeper.curator.factory.ZooKeeperSimpleSessionFactory;
import com.application.base.config.zookeeper.pool.ZooKeeperOperPool;
import org.apache.curator.RetryPolicy;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author : 孤狼
 * @NAME: ZkTest
 * @DESC:
 **/
public class ZkTest {
	
	public static void main(String[] args) {
		ZooKeeperConfig config = getConfig();
		ZooKeeperOperPool pool = getPool(config);
		ZooKeeperSimpleSessionFactory factory = getFactory(pool);
		for (int i = 0; i <2000 ; i++) {
			boolean result = factory.getZooKeeperSession().exists("/super/testNode");
			if (result){
				System.out.println("节点存在!index ="+i);
			}else{
				System.out.println("不存在!index ="+i);
			}
		}
		
	}
	
	private static ZooKeeperSimpleSessionFactory getFactory(ZooKeeperOperPool pool){
		ZooKeeperSimpleSessionFactory factory = new ZooKeeperSimpleSessionFactory(pool);
		return factory;
	}
	
	private static ZooKeeperOperPool getPool(ZooKeeperConfig config){
		ZooKeeperOperPool pool = new ZooKeeperOperPool(config);
		return  pool;
	}
	
	private static ZooKeeperConfig getConfig(){
		ZooKeeperConfig config = new ZooKeeperConfig();
		config.setConnectionTimeoutMs(6000);
		config.setConnectString("192.168.10.216:2181");
		config.setRetryPolicy(getPolicy());
		config.setSessionTimeoutMs(5000);
		config.setNameSpace("workspace");
		return config;
	}
	
	private static RetryPolicy getPolicy(){
		
		/**
		 * curator链接zookeeper的策略:RetryUntilElapsed
		 *
		 * 构造器参数：
		 * maxElapsedTimeMs:最大重试时间
		 * sleepMsBetweenRetries:每次重试间隔
		 * 重试时间超过maxElapsedTimeMs后，就不再重试
		 */
		//RetryPolicy retryPolicy4 = new RetryUntilElapsed(2000, 3000);
		
		/**
		 * 永远重试，不推荐使用
		 */
		//RetryPolicy retryPolicy3 = new RetryForever(retryIntervalMs);
		
		/**
		 * （不推荐）
		 * curator链接zookeeper的策略:RetryOneTime
		 *
		 * 构造器参数：
		 * sleepMsBetweenRetry:每次重试间隔的时间
		 * 这个策略只会重试一次
		 */
		//RetryPolicy retryPolicy2 = new RetryOneTime(3000);
		
		/**
		 * （推荐）
		 * curator链接zookeeper的策略:RetryNTimes
		 *
		 * 构造器参数：
		 * n：重试的次数
		 * sleepMsBetweenRetries：每次重试间隔的时间
		 */
		//RetryPolicy retryPolicy = new RetryNTimes(3, 5000);
		
		/**
		 * （推荐）
		 * 同步创建zk示例，原生api是异步的
		 * 这一步是设置重连策略
		 *
		 * 构造器参数：
		 *  curator链接zookeeper的策略:ExponentialBackoffRetry
		 *  baseSleepTimeMs：初始sleep的时间
		 *  maxRetries：最大重试次数
		 *  maxSleepMs：最大重试时间
		 */
		//RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
		
		
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
		return retryPolicy;
	}
	
}
