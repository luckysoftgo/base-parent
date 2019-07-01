package com.application.base.core.datasource.impl.cache;

import com.application.base.cache.redis.factory.RedisSessionFactory;
import com.application.base.core.datasource.api.CacheReadAndWriteDataSessionFactory;
import com.application.base.core.datasource.impl.common.SingleDefaultReadAndWriteDataSessionFactory;
import com.application.base.core.datasource.session.CacheDataSession;
import org.springframework.stereotype.Service;

/**
 * @desc 默认的带有缓存功能的读写分离 CacheDataSession 工厂,
 * 用于获取带有缓存功能的数据库访问会话
 * 单数据源操作数据库，实现读写分离
 * @author 孤狼
 */
@Service
public class SingleDefaultCacheReadAndWriteDataSessionFactory extends SingleDefaultReadAndWriteDataSessionFactory implements CacheReadAndWriteDataSessionFactory {
    
    /**
     * redis 工厂.
     */
    private RedisSessionFactory redisSessionFactory;
    
    /**
     * cache read session.
     */
    private CacheDataSession cacheReadDataSession;
    
    /**
     * cache write session.
     */
    private CacheDataSession cacheWriteDataSession;

    @Override
	public CacheDataSession getCacheReadDataSession() {
        if (cacheReadDataSession != null){
        	return cacheReadDataSession;
        }
        cacheReadDataSession = new DefaultCacheDataSession(getFactorySupport().getSqlSessionFacotry(getReadDataSource()),redisSessionFactory);
        return cacheReadDataSession;
    }
    
    @Override
	public CacheDataSession getCacheWriteDataSession() {
        if (cacheWriteDataSession != null){
            return cacheWriteDataSession;
        }
        cacheWriteDataSession = new DefaultCacheDataSession(getFactorySupport().getSqlSessionFacotry(getWriteDataSource()),redisSessionFactory);
        return cacheWriteDataSession;
    }
    
    
    @Override
	public RedisSessionFactory getRedisSessionFactory() {
        return redisSessionFactory;
    }

    public void setRedisSessionFactory(RedisSessionFactory redisSessionFactory) {
        this.redisSessionFactory = redisSessionFactory;
    }

}
