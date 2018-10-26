package com.application.base.auth.check;

import com.application.base.auth.entity.SourceVO;
import com.application.base.auth.util.CacheType;
import com.application.base.auth.util.SourceType;

/**
 * @desc 資源池信息
 * @author  孤狼.
 */
public interface SourcePoolService {
	
	/**
	 * 获取的内容.
	 * @param key
	 * @param sourceType
	 * @param cacheType
	 *
	 * @return SourceVO 对象 .
	 */
	SourceVO getSourceVOByKey(String key, SourceType sourceType, CacheType cacheType);
	
	/**
	 * 保存信息
	 * @param sourceVO
	 * @param sourceType
	 * @param cacheType
	 * @param expireTime
	 *
	 * @return 缓存中的 KEY .
	 */
	String saveSourceVOInfo(SourceVO sourceVO,SourceType sourceType,CacheType cacheType,Integer expireTime);
	
	/**
	 * 修改信息
	 * @param key
	 * @param sourceVO
	 * @param sourceType
	 * @param cacheType
	 * @param expireTime
	 * @return 更新后的 KEY .
	 */
	String updateSourceVOInfo(String key,SourceVO sourceVO,SourceType sourceType,CacheType cacheType,Integer expireTime);
	
}
