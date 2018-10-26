package com.application.base.core.datasource.session;

import com.application.base.cache.redis.api.RedisSession;

/**
 * @desc redis 缓存Session,所有的缓存信息都放在这个 redis 中.
 * @author 孤狼
 */
public interface CacheDataSession extends DataSession{
	
	/**
	 * 默认从redis 的 db0 开始放数据.
	 * @return
	 */
    RedisSession getRedisSession();
    
}
