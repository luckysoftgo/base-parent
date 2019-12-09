package com.application.base.cache.redis.jedis.factory.cluster;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @NAME: RedisClusterPool
 * @DESC: es连接池.
 * @USER: 孤狼.
 **/
public class RedisClusterPool extends ClusterPool<JedisCluster> {
	
	/**
	 * 构造方法
	 */
	public RedisClusterPool(JedisPoolConfig poolConfig,String hostInfos) {
		super(poolConfig, new JedisClusterFactory(poolConfig,hostInfos));
	}
	
	/**
	 * 构造方法
	 */
	public RedisClusterPool(JedisPoolConfig poolConfig, int timeout, int sotimeout, int maxattempts, String hostInfos) {
		super(poolConfig, new JedisClusterFactory(poolConfig,timeout,sotimeout,maxattempts,hostInfos));
	}
	
	/**
	 * 构造方法
	 */
	public RedisClusterPool(JedisPoolConfig poolConfig, int timeout, int sotimeout, int maxattempts, String passWord, String hostInfos) {
		super(poolConfig, new JedisClusterFactory(poolConfig,timeout,sotimeout,maxattempts,passWord,hostInfos));
	}
}
