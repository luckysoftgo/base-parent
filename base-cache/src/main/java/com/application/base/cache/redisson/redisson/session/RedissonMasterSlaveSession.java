package com.application.base.cache.redisson.redisson.session;

import org.redisson.api.RedissonClient;

/**
 * @desc 主从 redisson 的操作.
 * @author 孤狼
 */
public class RedissonMasterSlaveSession extends CommonSession {
	
	/**
	 * Redisson 的客户端操作.
	 */
	private RedissonClient client;
	
	public RedissonClient getClient() {
		return client;
	}
	
	public void setClient(RedissonClient client) {
		this.client = client;
	}
	
	@Override
	public void setCurrentClient(RedissonClient client){
		this.client = client;
	}
	
	@Override
	public RedissonClient getCurrentClient(){
		return client;
	}
	
}
