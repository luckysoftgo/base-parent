package com.application.base.cache.codis.architecture.enumer;

import redis.clients.jedis.BinaryClient;

/**
 * @des 设置
 * @author 孤狼
 */
public enum ListPosition {
	
	/**
	 * 之前
	 */
	BEFORE,
	
	/**
	 * 之后
	 */
	AFTER;

	public BinaryClient.LIST_POSITION warp() {
		if (BEFORE.equals(this)) {
			return BinaryClient.LIST_POSITION.BEFORE;
		}
		return BinaryClient.LIST_POSITION.AFTER;
	}
}