package com.application.base.cache.codis.architecture.enumer;

/**
 * @desc 缓存的标识.
 * @author bruce.
 */
public enum CacheEnvironment {
	
	/**
	 * 用户体系
	 */
	USER("user"),
	/**
	 * 正式环境
	 */
	PROD("prod"),
	;
	
	private String value;

	private CacheEnvironment(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public String encode(String key) {
		return this.value + ":" + key;
	}

	public String decode(String key) {
		return key.substring(key.indexOf(":") + 1);
	}

	public static CacheEnvironment env(String value) {
		value = value.toLowerCase();
		switch (value) {
		case "user":
			return USER;
		case "prod":
			return PROD;
		default:
			return USER;
		}
	}
}
