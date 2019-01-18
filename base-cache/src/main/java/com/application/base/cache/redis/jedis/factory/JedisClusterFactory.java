package com.application.base.cache.redis.jedis.factory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @desc 集群设置:这种放值方式,每个节点都会有值存入.相当说是做的数据多机器备份.
 * @author 孤狼
 */
public class JedisClusterFactory extends JedisPool {
	
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
	 * 存放 ip 和 port 的 Str
	 */
	private String hostInfos="127.0.0.1:6379";
	/**
	 * 密码
	 */
	private String passWord="";
	
	/**
	 * 密码分割符号(和 hostInfos 是一一对应的)
	 */
	private String passSplit=";";
	
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
	public JedisClusterFactory(JedisPoolConfig poolConfig,int timeout,int sotimeout,int maxattempts,String passWord,String hostInfos) {
		this.poolConfig =poolConfig;
		this.timeout = timeout;
		this.sotimeout = sotimeout;
		this.maxattempts = maxattempts;
		this.passWord = passWord;
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
			// 以";"分割成"ip:post"
			String[] ipAndPorts = hostInfos.split(passSplit);
			HostAndPort instance = null ;
			for (int i = 0; i <ipAndPorts.length ; i++) {
				String[] ipAndPortArray = ipAndPorts[i].split(":");
				instance=new HostAndPort(ipAndPortArray[0],Integer.parseInt(ipAndPortArray[1]));
				clusterNodes.add(instance);
			}
			
			//得到实例.
			jedisCluster = new JedisCluster(clusterNodes, timeout, sotimeout, maxattempts,getPoolConfig());
			if (StringUtils.isNotBlank(passWord)) {
				jedisCluster.auth(passWord);
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
	public JedisCluster getClusterResource() {
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
	
	
	public String getPassWord() {
		return passWord;
	}
	
	public void setPassWord(String passWord) {
		this.passWord = passWord;
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
