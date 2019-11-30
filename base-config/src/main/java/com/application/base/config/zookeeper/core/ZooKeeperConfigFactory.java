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
		/*
		//刚开始重试间隔为1秒，之后重试间隔逐渐增加，最多重试不超过三次
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		//最大重试次数，和两次重试间隔时间
        RetryPolicy retryPolicy1 = new RetryNTimes(3, 1000);
		//会一直重试直到达到规定时间，第一个参数整个重试不能超过时间，第二个参数重试间隔
        RetryPolicy retryPolicy2 = new RetryUntilElapsed(5000, 1000);
		*/
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
		return retryPolicy;
	}
}
