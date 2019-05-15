package com.application.base.all.elastic.elastic.transport.client;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @NAME: ElasticPoolConfig
 * @DESC: TransportClient 的连接池.
 * @USER: 孤狼
 **/
public class ElasticPoolConfig extends GenericObjectPoolConfig {
	
	/**
	 * 数据初始化信息
	 */
	public ElasticPoolConfig() {
		this.setTestWhileIdle(true);
		this.setMinEvictableIdleTimeMillis(60000L);
		this.setTimeBetweenEvictionRunsMillis(30000L);
		this.setNumTestsPerEvictionRun(-1);
	}
	
}
