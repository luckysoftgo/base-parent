package com.application.base.config.zookeeper.core;

import com.application.base.config.zookeeper.config.ZooKeeperConfig;
import com.application.base.config.zookeeper.exception.ZooKeeperException;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : 孤狼
 * @NAME: ZooKeeperConfigFactory
 * @DESC: 工厂实例,继承他人的实现.
 **/
public class ZooKeeperConfigFactory extends BasePooledObjectFactory<CuratorFramework> {
	/**
	 * 会话超时时间，单位毫秒，默认60000ms
	 */
	int SESSION_TIMEOUT_MS=60000;
	
	/**
	 * 连接创建超时时间，单位毫秒，默认60000ms
	 */
	int CONNECTION_TIMEOUT_MS=60000;
	
	/**
	 * 连接串
	 */
	String connectString="127.0.0.1:2181";
	
	private AtomicReference<ZooKeeperConfig> nodesReference = new AtomicReference<ZooKeeperConfig>();
	
	public ZooKeeperConfigFactory(ZooKeeperConfig keeperConfig){
		this.nodesReference.set(keeperConfig);
	}
	
	@Override
	public CuratorFramework create() throws Exception {
		ZooKeeperConfig config = nodesReference.get();
		CuratorFramework client = null;
		RetryPolicy retryPolicy = config.getRetryPolicy();
		Integer sessionTimeout=config.getSessionTimeoutMs();
		Integer connectionTimeout=config.getConnectionTimeoutMs();
		String connectStr = config.getConnectString();
		String nameSpace=config.getNameSpace();
		if (null==config.getRetryPolicy()){
			retryPolicy = getPolicy();
		}
		if (null==config.getSessionTimeoutMs()){
			sessionTimeout=SESSION_TIMEOUT_MS;
		}
		if (null==config.getConnectionTimeoutMs()){
			connectionTimeout=CONNECTION_TIMEOUT_MS;
		}
		if (null==config.getConnectString()){
			connectStr=connectString;
		}
		if (null==nameSpace){
			try {
				client = CuratorFrameworkFactory
						.newClient(connectStr,sessionTimeout,connectionTimeout,retryPolicy);
			} catch (Exception e) {
				throw new ZooKeeperException("创建 CuratorFramework 对象失败,error:"+e);
			}
		}else{
			try {
				client = CuratorFrameworkFactory.builder()
						.connectString(connectStr)
						.sessionTimeoutMs(sessionTimeout)
						.connectionTimeoutMs(connectionTimeout)
						.retryPolicy(retryPolicy)
						.namespace(nameSpace).build();
			} catch (Exception e) {
				throw new ZooKeeperException("创建 CuratorFramework 对象失败,error:"+e);
			}
		}
		return client;
	}
	
	@Override
	public PooledObject<CuratorFramework> wrap(CuratorFramework framework) {
		//包装实际对象
		return new DefaultPooledObject<>(framework);
	}
	
	
	/**
	 * 连接重试策略:刚开始重试间隔为1秒，之后重试间隔逐渐增加，最多重试不超过三次
	 * 重试策略,内建有四种重试策略,也可以自行实现RetryPolicy接口:
	 * @return
	 */
	private RetryPolicy getPolicy(){
		
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
