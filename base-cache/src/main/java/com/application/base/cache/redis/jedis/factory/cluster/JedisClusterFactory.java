package com.application.base.cache.redis.jedis.factory.cluster;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @NAME: ElasticTransportFactory.
 * @DESC: ES 连接池的工厂.
 * @USER: 孤狼.
 **/
public class JedisClusterFactory implements PooledObjectFactory<JedisCluster> {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 集群实例
	 */
	private JedisCluster jedisCluster;
	
	/**
	 * 连接池参数 spring 注入
	 */
	private JedisPoolConfig poolConfig;
	/**
	 * 连接时间.
	 */
	private int timeout = 5000;
	/**
	 * 超时
	 */
	private int sotimeout = 6000;
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
	public JedisClusterFactory(JedisPoolConfig poolConfig,String hostInfos) {
		this.poolConfig =poolConfig;
		this.hostInfos = hostInfos;
	}
	
	/**
	 * 构造方法
	 */
	public JedisClusterFactory(JedisPoolConfig poolConfig, int timeout, int sotimeout, int maxattempts, String hostInfos) {
		this.poolConfig =poolConfig;
		this.timeout = timeout;
		this.sotimeout = sotimeout;
		this.maxattempts = maxattempts;
		this.hostInfos = hostInfos;
	}
	
	/**
	 * 构造方法
	 */
	public JedisClusterFactory(JedisPoolConfig poolConfig, int timeout, int sotimeout, int maxattempts, String passWord, String hostInfos) {
		this.poolConfig =poolConfig;
		this.timeout = timeout;
		this.sotimeout = sotimeout;
		this.maxattempts = maxattempts;
		this.passWord = passWord;
		this.hostInfos = hostInfos;
	}
	
	@Override
	public PooledObject<JedisCluster> makeObject() throws Exception {
		Set<HostAndPort> clusterNodes = new HashSet<HostAndPort>();
		try {
			if (!StringUtils.isNotBlank(hostInfos)) {
				logger.info("初始化 Redis 集群的IP和端口,没有传入IP和端口的字符串.");
				return null;
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
			jedisCluster = new JedisCluster(clusterNodes, timeout, sotimeout, maxattempts,poolConfig);
			if (StringUtils.isNotBlank(passWord)) {
				jedisCluster.auth(passWord);
			}
		}
		catch (Exception ex) {
			logger.error("格式化传入的ip端口异常了,请检查出传入的字符串信息,error:{}" , ex.getMessage());
		}
		return new DefaultPooledObject(jedisCluster);
	}
	
	@Override
	public void destroyObject(PooledObject<JedisCluster> pooledObject) throws Exception {
		JedisCluster cluster=pooledObject.getObject();
		if (cluster!=null){
			try {
				cluster.close();
			} catch (Exception e) {
				//ignore
			}
		}
	}
	
	@Override
	public boolean validateObject(PooledObject<JedisCluster> pooledObject) {
		JedisCluster client = pooledObject.getObject();
		if (client!=null){
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void activateObject(PooledObject<JedisCluster> pooledObject) throws Exception {
		JedisCluster client = pooledObject.getObject();
	}
	
	@Override
	public void passivateObject(PooledObject<JedisCluster> pooledObject) throws Exception {
	}
}