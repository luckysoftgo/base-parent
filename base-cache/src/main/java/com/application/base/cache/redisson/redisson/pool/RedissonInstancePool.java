package com.application.base.cache.redisson.redisson.pool;

import com.application.base.cache.redisson.redisson.pool.config.RedissonBasicConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.api.RedissonClient;

/**
 * @NAME: RedissonSimplePool
 * @DESC: es连接池.
 * @USER: 孤狼.
 **/
public class RedissonInstancePool extends RedissonPool<RedissonClient> {
	
	/**
	 * 构造方法
	 */
	public RedissonInstancePool(GenericObjectPoolConfig genericPoolConfig, RedissonBasicConfig basicConfig) {
		super(genericPoolConfig,new RedissonInstanceFactory(basicConfig));
	}
}
