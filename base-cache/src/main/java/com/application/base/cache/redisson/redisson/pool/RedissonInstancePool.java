package com.application.base.cache.redisson.redisson.pool;

import com.application.base.cache.redisson.redisson.pool.config.BasicConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.api.RedissonClient;

/**
 * @NAME: RedissonSimplePool
 * @DESC: es连接池.
 * @USER: 孤狼.
 **/
public class RedissonInstancePool extends RedissonPool<RedissonClient> {
	/**
	 * 配置连接池信息
	 */
	private GenericObjectPoolConfig poolConfig;
	/**
	 * 配置config信息
	 */
	private BasicConfig config;
	
	/**
	 * 构造方法
	 */
	public RedissonInstancePool() {
	
	}
	
	/**
	 * 构造方法
	 */
	public RedissonInstancePool(GenericObjectPoolConfig poolConfig, BasicConfig config) {
		super(poolConfig,new RedissonInstanceFactory(config));
		this.poolConfig = poolConfig;
		this.config = config;
	}
	
	public GenericObjectPoolConfig getPoolConfig() {
		return poolConfig;
	}
	
	public void setPoolConfig(GenericObjectPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}
	
	public BasicConfig getConfig() {
		return config;
	}
	
	public void setConfig(BasicConfig config) {
		this.config = config;
	}
}
