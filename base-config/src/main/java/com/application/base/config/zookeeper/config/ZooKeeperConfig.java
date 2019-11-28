package com.application.base.config.zookeeper.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.curator.RetryPolicy;

/**
 * @author : 孤狼
 * @NAME: ZooKeeperConfig
 * @DESC: 配置设置.
 **/
public class ZooKeeperConfig extends GenericObjectPoolConfig {
	
	/**
	 * 连接字符串：192.168.1.1:2181,192.168.2.2:2181
 	 */
	private String connectString;
	
	/**
	 * session超时时间(毫秒)
	 */
	private Integer sessionTimeoutMs;

	/**
	 * 连接超时时间(毫秒)
	 */
	private Integer connectionTimeoutMs;

	/**
	 * 重试策略设置.org.apache.curator.retry 包下有配置.
	 */
	private RetryPolicy retryPolicy;
	
	/**
	 * 节点的命名空间.
	 */
	private String nameSpace;
	
	
	public String getConnectString() {
		return connectString;
	}
	
	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}
	
	public Integer getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}
	
	public void setSessionTimeoutMs(Integer sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}
	
	public Integer getConnectionTimeoutMs() {
		return connectionTimeoutMs;
	}
	
	public void setConnectionTimeoutMs(Integer connectionTimeoutMs) {
		this.connectionTimeoutMs = connectionTimeoutMs;
	}
	
	public RetryPolicy getRetryPolicy() {
		return retryPolicy;
	}
	
	public void setRetryPolicy(RetryPolicy retryPolicy) {
		this.retryPolicy = retryPolicy;
	}
	
	public String getNameSpace() {
		return nameSpace;
	}
	
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
}
