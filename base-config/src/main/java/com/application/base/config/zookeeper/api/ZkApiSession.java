package com.application.base.config.zookeeper.api;

import com.application.base.config.zookeeper.exception.ZookeeperException;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryUntilElapsed;
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
	 * 会话超时时间，单位毫秒，默认60000ms
	 */
	int SESSION_TIMEOUT_MS=60000;
	
	/**
	 * 连接创建超时时间，单位毫秒，默认60000ms
	 */
	int CONNECTION_TIMEOUT_MS=60000;
	
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
	 * 获得zookeeper的实例
	 * @param connectString:连接字符串：192.168.1.1:2181,192.168.2.2:2181
	 * @param sessionTimeout:会话超时时间，单位毫秒，默认60000ms
	 * @param connectionTimeout:连接创建超时时间，单位毫秒，默认60000ms
	 * @param retryPolicy:连接机制
	 * @return
	 * @throws ZookeeperException
	 */
	CuratorFramework getClient(String connectString,Integer sessionTimeout,Integer connectionTimeout,RetryPolicy retryPolicy) throws ZookeeperException;
	
	/**
	 * 获得zookeeper的实例
	 * @param connectString:连接字符串：192.168.1.1:2181,192.168.2.2:2181
	 * @param sessionTimeout:会话超时时间，单位毫秒，默认60000ms
	 * @param connectionTimeout:连接创建超时时间，单位毫秒，默认60000ms
	 * @param retryPolicy:连接机制
	 * @param nameSpace:节点的命名空间.
	 * @return
	 * @throws ZookeeperException
	 */
	CuratorFramework getClient(String connectString,Integer sessionTimeout,Integer connectionTimeout,RetryPolicy retryPolicy,String nameSpace) throws ZookeeperException;
	
	/**
	 * 创建 zk 的节点.
	 * @param nodeName:节点名称.
	 * @return
	 * @throws ZookeeperException
	 */
	boolean createNode(String nodeName) throws ZookeeperException;
	
	/**
	 * 创建 zk 的节点.
	 * @param mode:创建节点模式.
	 *      PERSISTENT：持久化
			PERSISTENT_SEQUENTIAL：持久化并且带序列号
			EPHEMERAL：临时
			EPHEMERAL_SEQUENTIAL：临时并且带序列号
	 * @param nodeName:节点名称.
	 * @return
	 * @throws ZookeeperException
	 */
	boolean createNode(CreateMode mode,String nodeName) throws ZookeeperException;
	
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
	 * @throws ZookeeperException
	 */
	boolean createNodeAndVal(CreateMode mode,String nodeName,String data) throws ZookeeperException;
	
	/**
	 * 更新节点信息.
	 * @param nodeName:节点名称
	 * @param data: 更新的value
	 * @return
	 * @throws ZookeeperException
	 */
	boolean updateNodeData(String nodeName,String data) throws ZookeeperException;
	
	/**
	 * 更新节点信息.
	 * @param nodeName:节点名称
	 * @param value: 更新的value
	 * @param version 指定版本号
	 * @return
	 * @throws ZookeeperException
	 */
	boolean updateNodeData(String nodeName,String value,Integer version) throws ZookeeperException;
	
	/**
	 * 删除一个节点
	 * @param nodeName:节点名称
	 * @param children:是否删除子节点,默认false
	 * @return
	 * @throws ZookeeperException
	 */
	boolean deleteNode(String nodeName,boolean children) throws  ZookeeperException;
	
	/**
	 * 删除一个节点
	 * @param nodeName:节点名称
	 * @param version:指定删除的版本号
	 * @param children:是否删除子节点,默认false
	 * @param guaranteed:是否保证一定删除
	 * @return
	 * @throws ZookeeperException
	 */
	boolean deleteNodeByPros(String nodeName,Integer version,boolean children,boolean guaranteed) throws  ZookeeperException;
	
	/**
	 *获取节点的内容
	 * @param nodeName:节点名称
	 * @return
	 * @throws ZookeeperException
	 */
	String getNodeData(String nodeName) throws ZookeeperException;
	
	/**
	 *获取节点的内容
	 * @param nodeName:节点名称
	 * @param stat:节点的状态
	 * @return
	 * @throws ZookeeperException
	 */
	String getNodeData(String nodeName,Stat stat) throws ZookeeperException;
	
	/**
	 * 检查节点是否存在
	 * @param nodeName:节点名称
	 * @return
	 * @throws ZookeeperException
	 */
	boolean exists(String nodeName) throws ZookeeperException;
	
	/**
	 * 获取节点下的子节点集合
	 * @param nodeName:节点名称
	 * @return
	 * @throws ZookeeperException
	 */
	List<String> getNodeChildren(String nodeName)  throws ZookeeperException;
	
}
