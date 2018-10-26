package com.application.base.auth.util;

/**
 * @desc 公共常量
 * @author 孤狼
 */
public class CheckConstant {
	
	/**
	 * source 存储的 redis 地址
	 */
	public static final String REDIS_DIR = "source:check:";
	
	/**
	 * 失效时间
	 */
	public static final int DEFAULT_TIMEOUT = 60 * 60 * 24;
	
}
