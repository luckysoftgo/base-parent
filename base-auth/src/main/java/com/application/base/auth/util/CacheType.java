package com.application.base.auth.util;

/**
 * @desc 缓存的类型
 * @author 孤狼
 */
public enum CacheType {
	
	REDIS_TYPE("REDIS","采用 redis 存储"),
	REDISSON_TYPE("REDISSON","采用 redisson 存储"),
	EHCACHE_TYPE("EHCACHE","采用 ehcache 存储"),
	OSCACHE_TYPE("OSCACHE","采用 oscache 存储"),
	MEMCACHED_TYPE("MEMCACHED","采用 Memcached 存储"),
	
	;
	
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 描述
	 */
	private String desc;
	
	CacheType(String type,String desc){
		this.type = type;
		this.desc = desc;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
