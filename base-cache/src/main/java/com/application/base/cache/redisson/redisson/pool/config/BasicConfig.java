package com.application.base.cache.redisson.redisson.pool.config;

import org.redisson.config.Config;

/**
 * @NAME: BasicConfig
 * @DESC: 基础数据装配层.
 * @USER: 孤狼
 **/
public abstract class BasicConfig {
	
	/**
	 * 设置对于master节点的连接池中连接数最大为500
	 */
	private int masterConnectionPoolSize=500;
	/**
	 * 最大连接数(设置对于master节点的连接池中连接数最大为500).
	 */
	private int connectionPoolSize = 500;
	/**
	 * 如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
	 */
	private int idleConnectionTimeout = 1000;
	/**
	 * 设置对于slave节点的连接池中连接数最大为500
	 */
	private int slaveConnectionPoolSize=500;
	/**
	 * 同任何节点建立连接时的等待超时。时间单位是毫秒
	 */
	private int connectTimeout = 5000;
	
	/**
	 * 等待节点回复命令的时间。该时间从命令发送成功时开始计时。
	 */
	private int timeout = 3000;
	/**
	 *
	 */
	private int pingTimeout = 30000;
	
	/**
	 * 当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
	 */
	private int reconnectionTimeout = 3000;
	
	/**
	 * 获得对象实例.
	 * @return
	 */
	public Config getInstance(){
		//子类重新.
		return  null;
	}
	
	public BasicConfig() {
	
	}
	
	/**
	 * 单机版
	 * @param connectionPoolSize
	 * @param idleConnectionTimeout
	 * @param connectTimeout
	 * @param timeout
	 * @param pingTimeout
	 * @param reconnectionTimeout
	 */
	public BasicConfig(int connectionPoolSize, int idleConnectionTimeout, int connectTimeout, int timeout,
	                   int pingTimeout, int reconnectionTimeout) {
		this.connectionPoolSize = connectionPoolSize;
		this.idleConnectionTimeout = idleConnectionTimeout;
		this.connectTimeout = connectTimeout;
		this.timeout = timeout;
		this.pingTimeout = pingTimeout;
		this.reconnectionTimeout = reconnectionTimeout;
	}
	
	/**
	 * 集群,主从,哨兵版
	 * @param masterConnectionPoolSize
	 * @param idleConnectionTimeout
	 * @param slaveConnectionPoolSize
	 * @param connectTimeout
	 * @param timeout
	 * @param pingTimeout
	 * @param reconnectionTimeout
	 */
	public BasicConfig(int masterConnectionPoolSize, int idleConnectionTimeout, int slaveConnectionPoolSize,
	                   int connectTimeout, int timeout, int pingTimeout, int reconnectionTimeout) {
		this.masterConnectionPoolSize = masterConnectionPoolSize;
		this.idleConnectionTimeout = idleConnectionTimeout;
		this.slaveConnectionPoolSize = slaveConnectionPoolSize;
		this.connectTimeout = connectTimeout;
		this.timeout = timeout;
		this.pingTimeout = pingTimeout;
		this.reconnectionTimeout = reconnectionTimeout;
	}
	
	public int getMasterConnectionPoolSize() {
		return masterConnectionPoolSize;
	}
	
	public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
		this.masterConnectionPoolSize = masterConnectionPoolSize;
	}
	
	public int getConnectionPoolSize() {
		return connectionPoolSize;
	}
	
	public void setConnectionPoolSize(int connectionPoolSize) {
		this.connectionPoolSize = connectionPoolSize;
	}
	
	public int getIdleConnectionTimeout() {
		return idleConnectionTimeout;
	}
	
	public void setIdleConnectionTimeout(int idleConnectionTimeout) {
		this.idleConnectionTimeout = idleConnectionTimeout;
	}
	
	public int getSlaveConnectionPoolSize() {
		return slaveConnectionPoolSize;
	}
	
	public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
		this.slaveConnectionPoolSize = slaveConnectionPoolSize;
	}
	
	public int getConnectTimeout() {
		return connectTimeout;
	}
	
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	
	public int getTimeout() {
		return timeout;
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	public int getPingTimeout() {
		return pingTimeout;
	}
	
	public void setPingTimeout(int pingTimeout) {
		this.pingTimeout = pingTimeout;
	}
	
	public int getReconnectionTimeout() {
		return reconnectionTimeout;
	}
	
	public void setReconnectionTimeout(int reconnectionTimeout) {
		this.reconnectionTimeout = reconnectionTimeout;
	}
}
