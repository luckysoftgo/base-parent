package com.application.base.cache.redis.jedis.factory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.util.Pool;

import java.util.HashSet;
import java.util.Set;

/**
 * @desc 哨兵设置JedisSentinelPool.
 * @author 孤狼
 */
public class JedisSentinelFactory extends Pool<Jedis> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	/**
	 * 哨兵模式
	 */
	private JedisSentinelPool jedisPool;
	
	/**
	 * redis结点列表127.0.0.1:16379;127.0.0.1:26379;
	 */
	private Set<String> sentinels = new HashSet<>();
	
	/**
	 * 连接池参数 spring 注入
	 */
	private JedisPoolConfig poolConfig;
	
	/**
	 * 哨兵的名称
	 */
	private String masterName="masterName";
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
	public JedisSentinelFactory() {}
	
	/**
	 * 构造方法
	 */
	public JedisSentinelFactory(String masterName, JedisPoolConfig poolConfig, int timeout, int sotimeout, int maxattempts, String hostInfos) {
		this.masterName=masterName;
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
	public JedisSentinelFactory(String masterName, JedisPoolConfig poolConfig, int timeout, int sotimeout, int maxattempts, String passWords, String hostInfos) {
		this.masterName=masterName;
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
				logger.info("初始化 Redis 哨兵的IP和端口,没有传入IP和端口的字符串.");
				return;
			}
			boolean isAuth=false;
			if (StringUtils.isNotBlank(getPassWords())) {
				isAuth=true;
			}
			// 以";"分割成"ip:post"
			String[] ipAndPorts = hostInfos.split(passSplit);
			for (int i = 0; i <ipAndPorts.length ; i++) {
				sentinels.add(ipAndPorts[i]);
			}
			//得到实例.
			if (isAuth) {
				jedisPool=new JedisSentinelPool(getMasterName(),sentinels,getPoolConfig(),getPassWords());
			} else {
				jedisPool=new JedisSentinelPool(getMasterName(),sentinels,getPoolConfig());
			}
		}
		catch (Exception ex) {
			logger.error("格式化传入的ip端口异常了,请检查出传入的字符串信息,error:{}" , ex.getMessage());
		}
	}
	
	/**
	 * 获得对象实例
	 * @return
	 */
	@Override
	public Jedis getResource() {
		if (null==jedisPool) {
			initFactory();
		}
		return jedisPool.getResource();
	}
	
	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}
	
	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}
	
	public String getMasterName() {
		return masterName;
	}
	
	public void setMasterName(String masterName) {
		this.masterName = masterName;
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
