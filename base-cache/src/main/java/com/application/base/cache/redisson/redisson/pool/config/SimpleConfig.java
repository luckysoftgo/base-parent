package com.application.base.cache.redisson.redisson.pool.config;

import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.io.Serializable;

/**
 * @NAME: SimpleConfig
 * @DESC: 简单配置.
 * @USER: 孤狼
 **/
public class SimpleConfig extends BasicConfig implements Serializable {
	
	/**
	 * 连接地址:redis://127.0.0.1:26379
	 */
	private String address;
	
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
		//指定使用单节点部署方式
		config.useSingleServer().setAddress(address);
		//设置密码
		//config.setPassword("password")
		//设置对于master节点的连接池中连接数最大为500
		config.useSingleServer().setConnectionPoolSize(getConnectionPoolSize());
		//如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
		config.useSingleServer().setIdleConnectionTimeout(getIdleConnectionTimeout());
		//同任何节点建立连接时的等待超时。时间单位是毫秒。
		config.useSingleServer().setConnectTimeout(getConnectTimeout());
		//等待节点回复命令的时间。该时间从命令发送成功时开始计时。
		config.useSingleServer().setTimeout(getTimeout());
		config.useSingleServer().setPingTimeout(getPingTimeout());
		//当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
		config.useSingleServer().setReconnectionTimeout(getReconnectionTimeout());
				
		return config;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
}
