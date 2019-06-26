package com.application.base.cache.redisson.redisson.pool.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.io.Serializable;

/**
 * @NAME: ClusterConfig
 * @DESC: 集群配置.
 * @USER: 孤狼
 **/
public class ClusterConfig extends BasicConfig implements Serializable {
	
	/**
	 * 连接地址:redis://127.0.0.1:6379;redis://127.0.0.1:26379
	 */
	private String hostInfos;
	
	
	/**
	 * 密码分割符号(和 hostInfos 是一一对应的)
	 */
	private String passSplit=";";
	
	/**
	 * 获得实例的方法.
	 * @return
	 */
	@Override
	public Config getInstance(){
		Config config = new Config();
		//指定编码，默认编码为org.redisson.codec.JsonJacksonCodec
		//之前使用的spring-data-redis，用的客户端jedis，编码为org.springframework.data.redis.serializer.StringRedisSerializer
		//改用redisson后为了之间数据能兼容，这里修改编码为org.redisson.client.codec.StringCodec
		config.setCodec(new StringCodec());
		// 集群状态扫描间隔时间，单位是毫秒
		config.useClusterServers().setScanInterval(2000);
		String[] address=initConfig();
		if(address!=null && address.length>0){
			config.useClusterServers().addNodeAddress(address);
		}
		//设置对于master节点的连接池中连接数最大为500
		config.useClusterServers().setMasterConnectionPoolSize(getMasterConnectionPoolSize());
		//设置对于slave节点的连接池中连接数最大为500
		config.useClusterServers().setSlaveConnectionPoolSize(getSlaveConnectionPoolSize());
		//如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
		config.useClusterServers().setIdleConnectionTimeout(getIdleConnectionTimeout());
		//同任何节点建立连接时的等待超时。时间单位是毫秒。
		config.useClusterServers().setConnectTimeout(getConnectTimeout());
		//等待节点回复命令的时间。该时间从命令发送成功时开始计时。
		config.useClusterServers().setTimeout(getTimeout());
		config.useClusterServers().setPingTimeout(getPingTimeout());
		//当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
		config.useClusterServers().setReconnectionTimeout(getReconnectionTimeout());
		
				
		return config;
	}
	
	/**
	 * 连接配置.
	 * @return
	 */
	private String[] initConfig(){
		if (StringUtils.isNotBlank(hostInfos)){
			String[] address=hostInfos.split(passSplit);
			if (address.length>0){
				return address;
			}
		}
		return null;
	}
	
	public String getHostInfos() {
		return hostInfos;
	}
	
	public void setHostInfos(String hostInfos) {
		this.hostInfos = hostInfos;
	}
	
}
