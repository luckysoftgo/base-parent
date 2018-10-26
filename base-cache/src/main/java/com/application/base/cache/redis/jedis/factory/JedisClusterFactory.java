package com.application.base.cache.redis.jedis.factory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @desc 集群设置pool 模仿 : JedisClusterFactory.java
 * @author 孤狼
 */
public class JedisClusterFactory {
	
	protected Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	/**
	 * 集群实例
	 */
	private JedisCluster jedisCluster;
	
	/**
	 * redis结点列表
	 */
	private Set<HostAndPort> clusterNodes = new HashSet<HostAndPort>();
	
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
	 * 密码
	 */
	private String password;
	/**
	 * 存放 ip 和 port 的 Str
	 */
	private String hostInfos="127.0.0.1:6379";

	/**
	 * 构造方法
	 */
	public JedisClusterFactory() {}
	
	/**
	 * 构造方法
	 */
	public JedisClusterFactory(JedisPoolConfig poolConfig,int timeout,int sotimeout,int maxattempts,String hostInfos) {
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
	public JedisClusterFactory(JedisPoolConfig poolConfig,int timeout,int sotimeout,int maxattempts,String password,String hostInfos) {
		this.poolConfig =poolConfig;
		this.timeout = timeout;
		this.sotimeout = sotimeout;
		this.maxattempts = maxattempts;
		this.password = password;
		this.hostInfos = hostInfos;
		initFactory();
	}
	
	@SuppressWarnings("deprecation")
	public void initFactory() {
		try {
			initHostAndPort();
			if (StringUtils.isNotBlank(password)) {
				jedisCluster = new JedisCluster(clusterNodes, timeout, sotimeout, maxattempts, password,poolConfig);
				//密码设置.
				jedisCluster.auth(password);
			}else {
				jedisCluster = new JedisCluster(clusterNodes, timeout, sotimeout, maxattempts,poolConfig);
			}
		}
		catch (Exception e) {
			logger.error("初始化jedis集群异常了,error:{}" ,e);
		}
	}
	
	/**
	 * 格式化ip和端口的设置.
	 * 
	 * @return
	 * @throws Exception
	 */
	private void initHostAndPort() {
		try {
			if (!StringUtils.isNotBlank(hostInfos)) {
				logger.info("初始化 Redis 集群的IP和端口,没有传入IP和端口的字符串.");
				return;
			}
			// 以";"分割成"ip:post"
			String[] ipAndPostes = hostInfos.split(";");
			HostAndPort instance = null ;
			if (ipAndPostes != null) {
				for (String ipAndPost : ipAndPostes) {
					// ipAndPost 值：(ip:端口)
					String[] ipAndPostArray = ipAndPost.split(":");
					instance = new HostAndPort(ipAndPostArray[0], Integer.parseInt(ipAndPostArray[1]));
					//添加
					clusterNodes.add(instance);
				}
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
	public JedisCluster getResource() {
		if (null==jedisCluster) {
			initFactory();
		}
		return jedisCluster;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHostInfos() {
		return hostInfos;
	}

	public void setHostInfos(String hostInfos) {
		this.hostInfos = hostInfos;
	}
}
