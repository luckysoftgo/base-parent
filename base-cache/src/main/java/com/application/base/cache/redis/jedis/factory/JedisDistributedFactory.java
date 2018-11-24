package com.application.base.cache.redis.jedis.factory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 集群设置 ShardedJedisPool 模仿 : JedisDistributedFactory.java
 * @author 孤狼
 */
public class JedisDistributedFactory {
	
	protected Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	/**
	 * 集群实例
	 */
	private ShardedJedisPool jedisPool;
	
	/**
	 * redis结点列表
	 */
	private List<JedisShardInfo> clusterNodes = new ArrayList<JedisShardInfo>();
	
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
	private String passWords="";
	
	/**
	 * 密码分割符号(和 hostInfos 是一一对应的)
	 */
	private String passSplit=";";
	
	/**
	 * 构造方法
	 */
	public JedisDistributedFactory() {}
	
	/**
	 * 构造方法
	 */
	public JedisDistributedFactory(JedisPoolConfig poolConfig, int timeout, int sotimeout, int maxattempts, String hostInfos) {
		this.poolConfig =poolConfig;
		this.timeout = timeout;
		this.sotimeout = sotimeout;
		this.maxattempts = maxattempts;
		this.hostInfos = hostInfos;
		initFactory();
	}
	
	/**
	 * 构造方法
	 */
	public JedisDistributedFactory(JedisPoolConfig poolConfig, int timeout, int sotimeout, int maxattempts, String passWords, String hostInfos) {
		this.poolConfig =poolConfig;
		this.timeout = timeout;
		this.sotimeout = sotimeout;
		this.maxattempts = maxattempts;
		this.passWords = passWords;
		this.hostInfos = hostInfos;
		initFactory();
	}
	
	@SuppressWarnings("deprecation")
	public void initFactory() {
		try {
			if (!StringUtils.isNotBlank(hostInfos)) {
				logger.info("初始化 Redis 集群的IP和端口,没有传入IP和端口的字符串.");
				return;
			}
			boolean isAuth=false;
			String[] authPassWord=null;
			if (!StringUtils.isNotBlank(passWords)) {
				isAuth=true;
				authPassWord=passWords.split(passSplit);
			}
			// 以";"分割成"ip:post"
			String[] ipAndPorts = hostInfos.split(passSplit);
			JedisShardInfo instance = null ;
			for (int i = 0; i <ipAndPorts.length ; i++) {
				String[] ipAndPortArray = ipAndPorts[i].split(":");
				instance=new JedisShardInfo(ipAndPortArray[0],Integer.parseInt(ipAndPortArray[1]));
				String tmpAuth=authPassWord[i];
				if (isAuth && StringUtils.isNotBlank(tmpAuth)){
					instance.setPassword(tmpAuth);
				}
				clusterNodes.add(instance);
			}
			//得到实例.
			jedisPool=new ShardedJedisPool(poolConfig, clusterNodes, Hashing.MURMUR_HASH,Sharded.DEFAULT_KEY_TAG_PATTERN);
		}
		catch (Exception ex) {
			logger.error("格式化传入的ip端口异常了,请检查出传入的字符串信息,error:{}" , ex.getMessage());
		}
	}
	
	/**
	 * 获得对象实例
	 * @return
	 */
	public ShardedJedis getResource() {
		if (null==jedisPool) {
			initFactory();
		}
		return jedisPool.getResource();
	}
	
	public void setJedisPool(ShardedJedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	public List<JedisShardInfo> getClusterNodes() {
		return clusterNodes;
	}
	
	public void setClusterNodes(List<JedisShardInfo> clusterNodes) {
		this.clusterNodes = clusterNodes;
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
	
	public String getPassWords() {
		return passWords;
	}
	
	public void setPassWords(String passWords) {
		this.passWords = passWords;
	}
	
	public String getHostInfos() {
		return hostInfos;
	}
	
	public void setHostInfos(String hostInfos) {
		this.hostInfos = hostInfos;
	}
	
	public String getPassSplit() {
		return passSplit;
	}
	
	public void setPassSplit(String passSplit) {
		this.passSplit = passSplit;
	}
}
