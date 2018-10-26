package com.application.base.cache.redis.factory;

import com.application.base.cache.redis.api.RedisSession;
import com.application.base.cache.redis.exception.RedisException;

/**
 * @desc 获得 redis 实例.
 * @author 孤狼.
 */
public interface RedisSessionFactory {

	/**
	 * 获取默认（选取DB 0库）的redis会话,
	 * 可选取设置 DB库的 redis 数据存储
	 * @return
	 * @throws RedisException
	 */
	RedisSession getRedisSession() throws RedisException;

}
