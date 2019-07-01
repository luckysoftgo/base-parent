package com.application.base.core.datasource.impl.cache;

import com.application.base.cache.redis.factory.RedisSessionFactory;
import com.application.base.core.datasource.api.CacheReadAndWriteDataSessionFactory;
import com.application.base.core.datasource.impl.common.MutilDefaultReadAndWriteDataSessionFactory;
import com.application.base.core.datasource.session.CacheDataSession;
import com.application.base.utils.common.BaseStringUtil;
import org.springframework.stereotype.Service;

/**
 * @desc 默认的带有缓存功能的读写分离CacheDataSession工厂,用于获取带有缓存功能的数据库访问会话。
 * 多数据源操作数据库，实现读写分离
 * @author 孤狼
 */
@Service
public class MutilDefaultCacheReadAndWriteDataSessionFactory extends MutilDefaultReadAndWriteDataSessionFactory implements CacheReadAndWriteDataSessionFactory {
	
	/**
	 * redis 工厂
	 */
    private RedisSessionFactory redisSessionFactory;
    
	/**
	 * cache read session
	 */
	private CacheDataSession cacheReadDataSession;
	
	/**
	 * cache write session
	 */
	private CacheDataSession cacheWriteDataSession;

	
	private String cacheTag = "";
	
	/**
	 * 读取 session 创建
	 */
	@Override
	public CacheDataSession getCacheReadDataSession() {
		if (cacheReadDataSession==null) {
			return createDefaultCacheDataSession(true);
		}else {
			//两次调用方式相同
			if (getCacheTag().equals(getFactoryTag())) {
				return cacheReadDataSession;
			}else {
				return createDefaultCacheDataSession(true);
			}
		}
    }

	/**
	 * 写入 session 创建
	 */
    @Override
	public CacheDataSession getCacheWriteDataSession() {
    	if (cacheWriteDataSession==null) {
			return createDefaultCacheDataSession(false);
		}else {
			//两次调用方式相同
			if (getCacheTag().equals(getFactoryTag())) {
				return cacheWriteDataSession;
			}else {
				return createDefaultCacheDataSession(false);
			}
		}
    }
	
	/**
	 * 创建缓存 session.
	 * @param tag:true
	 * @return
	 */
	private CacheDataSession createDefaultCacheDataSession(boolean tag) {
		if (tag) {
			cacheReadDataSession = new DefaultCacheDataSession(getFactorySupport().getSqlSessionFacotry(getReadDataSource()),redisSessionFactory);
        	//设置当前的缓存tag.
        	setCacheTag(getFactoryTag());
        	return cacheReadDataSession;
		}else {
			cacheWriteDataSession = new DefaultCacheDataSession(getFactorySupport().getSqlSessionFacotry(getWriteDataSource()),redisSessionFactory);
        	//设置当前的缓存tag.
        	setCacheTag(getFactoryTag());
        	return cacheWriteDataSession;			
		}
	}

    @Override
	public RedisSessionFactory getRedisSessionFactory() {
        return redisSessionFactory;
    }

    public void setRedisSessionFactory(RedisSessionFactory redisSessionFactory) {
        this.redisSessionFactory = redisSessionFactory;
    }
	
	/**
     * 获取读取数据库的数据源信息
     * @return
     */
    public String getCacheTag() {
    	if (BaseStringUtil.isEmpty(cacheTag)) {
			return "";
		}
    	return cacheTag;
	}

    /**
     * 设置:
     * 增删改查操作时候使用,判断使用的数据源.
     * 如果没有手动设置,就使用默认的数据源.
     * @param cacheTag
     */
	public void setCacheTag(String cacheTag) {
		this.cacheTag = cacheTag;
	}
}
