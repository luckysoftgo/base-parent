package com.application.base.cache.redis.jedis.factory.sharded;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Pool;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 分片的工厂,实现分片的原子性，数据会根据 hashing 算法，放入到不同的片(机器)上
 * @author 孤狼
 */
public class JedisSimpleShardedPool extends Pool<ShardedJedis> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	/**
	 * 分片集群实例:它只会选择一个服务器存放你set的数据，选择是根据你的key值按哈希算法决定哪台服务器。
	 * 所以只会有一台有数据，而且一样的key，基本是只会在某台redis服务器存放;
	 * 如果采用的是：使用 sharded 做 HA 操作.则换成 : ShardedJedisSentinelPool .
	 */
	private ShardedJedisPool shardedPool;
	
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
	private String hostInfos="127.0.0.1:6379:";
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
	public JedisSimpleShardedPool() {}
	
	/**
	 * 构造方法
	 */
	public JedisSimpleShardedPool(JedisPoolConfig poolConfig,String hostInfos) {
		this.poolConfig =poolConfig;
		this.hostInfos = hostInfos;
		initFactory();
	}
	/**
	 * 构造方法
	 */
	public JedisSimpleShardedPool(JedisPoolConfig poolConfig, int timeout, int sotimeout, int maxattempts, String hostInfos) {
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
	public JedisSimpleShardedPool(JedisPoolConfig poolConfig, int timeout, int sotimeout, int maxattempts, String passWords, String hostInfos) {
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
		List<JedisShardInfo> clusterNodes = new ArrayList<JedisShardInfo>();
		try {
			if (!StringUtils.isNotBlank(hostInfos)) {
				logger.info("初始化 Redis 集群的IP和端口,没有传入IP和端口的字符串.");
				return;
			}
			boolean isAuth=false;
			String[] authPassWord=null;
			if (StringUtils.isNotBlank(passWords)) {
				isAuth=true;
				authPassWord=passWords.split(passSplit);
			}
			// 以";"分割成"ip:post"
			String[] ipAndPorts = hostInfos.split(passSplit);
			JedisShardInfo instance = null ;
			for (int i = 0; i <ipAndPorts.length ; i++) {
				String[] ipAndPortArray = ipAndPorts[i].split(":");
				String tmpAuth=authPassWord[i];
				if (isAuth && StringUtils.isNotBlank(tmpAuth)){
					instance.setPassword(tmpAuth);
				}
				if (ipAndPortArray.length>=3){
					instance=new JedisShardInfo(ipAndPortArray[0],Integer.parseInt(ipAndPortArray[1]),Integer.parseInt(ipAndPortArray[2]));
				}else{
					instance=new JedisShardInfo(ipAndPortArray[0],Integer.parseInt(ipAndPortArray[1]));
				}
				clusterNodes.add(instance);
			}
			//得到实例.
			shardedPool = new ShardedJedisPool(getPoolConfig(),clusterNodes);
		}
		catch (Exception ex) {
			logger.error("格式化传入的ip端口异常了,请检查出传入的字符串信息,error:{}" , ex.getMessage());
		}
	}
	
	/**
	 * 获得对象实例
	 * @return
	 */
	public ShardedJedis getShardedResource() {
		if (null== shardedPool) {
			initFactory();
		}
		return shardedPool.getResource();
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
