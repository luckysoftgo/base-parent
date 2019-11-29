package com.application.base.config.zookeeper.api;

import com.application.base.config.zookeeper.exception.ZooKeeperException;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @Author: 孤狼
 * @desc: zookeeper 的 java 封装接口
 */
public interface ZkApiSession {
	
	/**
	 * 编码集
	 */
	String ENDCODING="UTF-8";
	
	/**
	 * 连接重试策略:刚开始重试间隔为1秒，之后重试间隔逐渐增加，最多重试不超过三次
	 * 重试策略,内建有四种重试策略,也可以自行实现RetryPolicy接口:
	 * @return
	 */
	default RetryPolicy getPolicy(){
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
	
	/**
	 * 获得 Curator 的 Client 端.
	 * @return
	 * @throws ZooKeeperException
	 */
	CuratorFramework getCuratorClient() throws ZooKeeperException;
	
	/**
	 * 创建 zk 的节点.
	 * @param nodeName:节点名称.
	 * @return
	 * @throws ZooKeeperException
	 */
	boolean createNode(String nodeName) throws ZooKeeperException;
	
	/**
	 * 创建 zk 的节点.
	 * @param mode:创建节点模式.
	 *      PERSISTENT：持久化
			PERSISTENT_SEQUENTIAL：持久化并且带序列号
			EPHEMERAL：临时
			EPHEMERAL_SEQUENTIAL：临时并且带序列号
	 * @param nodeName:节点名称.
	 * @return
	 * @throws ZooKeeperException
	 */
	boolean createNode(CreateMode mode,String nodeName) throws ZooKeeperException;
	
	/**
	 * 创建 zk 的节点并设置值.
	 * @param mode:创建模式.
	 *      PERSISTENT：持久化
			PERSISTENT_SEQUENTIAL：持久化并且带序列号
			EPHEMERAL：临时
			EPHEMERAL_SEQUENTIAL：临时并且带序列号
	 * @param nodeName:节点名称.
	 * @param data:节点放入的值.
	 * @return
	 * @throws ZooKeeperException
	 */
	boolean createNodeAndVal(CreateMode mode,String nodeName,String data) throws ZooKeeperException;
	
	/**
	 * 更新节点信息.
	 * @param nodeName:节点名称
	 * @param data: 更新的value
	 * @return
	 * @throws ZooKeeperException
	 */
	boolean updateNodeData(String nodeName,String data) throws ZooKeeperException;
	
	/**
	 * 更新节点信息.
	 * @param nodeName:节点名称
	 * @param value: 更新的value
	 * @param version 指定版本号
	 * @return
	 * @throws ZooKeeperException
	 */
	boolean updateNodeData(String nodeName,String value,Integer version) throws ZooKeeperException;
	
	/**
	 * 删除一个节点
	 * @param nodeName:节点名称
	 * @param children:是否删除子节点,默认false
	 * @return
	 * @throws ZooKeeperException
	 */
	boolean deleteNode(String nodeName,boolean children) throws  ZooKeeperException;
	
	/**
	 * 删除一个节点
	 * @param nodeName:节点名称
	 * @param version:指定删除的版本号
	 * @param children:是否删除子节点,默认false
	 * @param guaranteed:是否保证一定删除
	 * @return
	 * @throws ZooKeeperException
	 */
	boolean deleteNodeByPros(String nodeName,Integer version,boolean children,boolean guaranteed) throws  ZooKeeperException;
	
	/**
	 *获取节点的内容
	 * @param nodeName:节点名称
	 * @return
	 * @throws ZooKeeperException
	 */
	String getNodeData(String nodeName) throws ZooKeeperException;
	
	/**
	 *获取节点的内容
	 * @param nodeName:节点名称
	 * @param stat:节点的状态
	 * @return
	 * @throws ZooKeeperException
	 */
	String getNodeData(String nodeName,Stat stat) throws ZooKeeperException;
	
	/**
	 * 检查节点是否存在
	 * @param nodeName:节点名称
	 * @return
	 * @throws ZooKeeperException
	 */
	boolean exists(String nodeName) throws ZooKeeperException;
	
	/**
	 * 获取节点下的子节点集合
	 * @param nodeName:节点名称
	 * @return
	 * @throws ZooKeeperException
	 */
	List<String> getNodeChildren(String nodeName)  throws ZooKeeperException;
	
}
