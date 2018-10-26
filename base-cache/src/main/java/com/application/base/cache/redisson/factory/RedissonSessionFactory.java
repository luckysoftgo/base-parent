package com.application.base.cache.redisson.factory;

import com.application.base.cache.redisson.api.RedissonSession;
import com.application.base.cache.redisson.exception.RedissonException;

/**
 * @desc 获得 redisson 实例.
 * @author 孤狼.
 */
public interface RedissonSessionFactory {

	/**
	 * 获得 redisson 的 接口 实例.
	 * @return
	 * @throws RedissonException
	 */
	RedissonSession getRedissonSession() throws RedissonException;

}
