package com.application.base.cache.redis.jedis.factory.cluster;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @NAME: RedisClusterPool
 * @DESC: es连接池.
 * @USER: 孤狼.
 **/
public class RedisClusterPool extends ClusterPool<JedisCluster> {
	
	/**
	 * 连接池参数 spring 注入
	 */
	private JedisPoolConfig poolConfig;
	/**
	 * 连接时间.
	 */
	private int timeout = 2000;
	/**
	 * 超时
	 */
	private int sotimeout = 3000;
	/**
	 * 最大尝试次数
	 */
	private int maxattempts = 10;
	/**
	 * 存放 ip 和 port 的 Str
	 */
	private String hostInfos="127.0.0.1:6379";
	/**
	 * 密码
	 */
	private String passWord="";
	
	
	/**
	 * 构造方法
	 */
	public RedisClusterPool() {}
	
	/**
	 * 构造方法
	 */
	public RedisClusterPool(JedisPoolConfig poolConfig,int timeout,int sotimeout,int maxattempts,String hostInfos) {
		super(poolConfig, new JedisClusterFactory(poolConfig,timeout,sotimeout,maxattempts,hostInfos));
		this.poolConfig =poolConfig;
		this.timeout = timeout;
		this.sotimeout = sotimeout;
		this.maxattempts = maxattempts;
		this.hostInfos = hostInfos;
	}
	
	/**
	 * 构造方法
	 */
	public RedisClusterPool(JedisPoolConfig poolConfig,int timeout,int sotimeout,int maxattempts,String passWord,String hostInfos) {
		super(poolConfig, new JedisClusterFactory(poolConfig,timeout,sotimeout,maxattempts,passWord,hostInfos));
		this.poolConfig =poolConfig;
		this.timeout = timeout;
		this.sotimeout = sotimeout;
		this.maxattempts = maxattempts;
		this.passWord = passWord;
		this.hostInfos = hostInfos;
	}
	
	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}
	
	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}
	
	public int getTimeout() {
		return timeout;
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	public int getSotimeout() {
		return sotimeout;
	}
	
	public void setSotimeout(int sotimeout) {
		this.sotimeout = sotimeout;
	}
	
	public int getMaxattempts() {
		return maxattempts;
	}
	
	public void setMaxattempts(int maxattempts) {
		this.maxattempts = maxattempts;
	}
	
	public String getHostInfos() {
		return hostInfos;
	}
	
	public void setHostInfos(String hostInfos) {
		this.hostInfos = hostInfos;
	}
	
	public String getPassWord() {
		return passWord;
	}
	
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
}
