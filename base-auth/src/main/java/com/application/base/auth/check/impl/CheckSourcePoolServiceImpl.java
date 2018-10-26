package com.application.base.auth.check.impl;

import com.application.base.auth.check.SourcePoolService;
import com.application.base.auth.entity.SourceVO;
import com.application.base.cache.redis.api.RedisSession;
import com.application.base.auth.util.CacheType;
import com.application.base.auth.util.CheckConstant;
import com.application.base.auth.util.SourceType;
import com.application.base.utils.common.BaseStringUtil;
import com.application.base.utils.common.Md5Utils;
import com.application.base.utils.common.UUIDProvider;
import com.application.base.utils.json.JsonConvertUtils;
import org.springframework.stereotype.Service;

/**
 * @desc 检查容器,用来存放登录用户的相关信息.
 * @author  孤狼.
 */
@Service
public class CheckSourcePoolServiceImpl implements SourcePoolService {
	
	/**
	 * reids 对象.
	 */
	private RedisSession redisSession;
	
	
	@Override
	public SourceVO getSourceVOByKey(String key, SourceType sourceType, CacheType cacheType) {
		SourceVO sourceVO = null;
		switch (sourceType){
			case LOGIN_INFO:
				sourceVO = getSoureceInfo(key,sourceType.getValue(),cacheType);
				break;
			case USER_INFO:
				break;
			case AUTH_INFO:
				break;
			case TEMP_INFO:
				break;
		}
		return sourceVO;
	}
	
	/**
	 * 获取信息
	 * @param key
	 * @param keyLocal
	 * @param cacheType
	 * @return
	 */
	private SourceVO getSoureceInfo(String key,String keyLocal,CacheType cacheType) {
		SourceVO sourceVO = null;
		switch (cacheType){
			case REDIS_TYPE:
				key = CheckConstant.REDIS_DIR + keyLocal + ":" + key;
				String jsonStr = getRedisSession().getData(key);
				if (BaseStringUtil.isNotEmpty(jsonStr)){
					sourceVO = JsonConvertUtils.fromJson(jsonStr,SourceVO.class);
				}
				break;
			case EHCACHE_TYPE:
				break;
			case OSCACHE_TYPE:
				break;
			case MEMCACHED_TYPE:
				break;
		}
		return sourceVO;
	}
	
	@Override
	public String saveSourceVOInfo(SourceVO sourceVO, SourceType sourceType, CacheType cacheType,Integer expireTime) {
		String key = "";
		switch (sourceType){
			case LOGIN_INFO:
				key = saveSoureceInfo(sourceVO,sourceType.getValue(),cacheType,expireTime);
				break;
			case USER_INFO:
				break;
			case AUTH_INFO:
				break;
			case TEMP_INFO:
				break;
		}
		return key;
	}
	
	/**
	 * 保存资源
	 * @param sourceVO
	 * @param keyLocal
	 * @param cacheType
	 * @return
	 */
	private String saveSoureceInfo(SourceVO sourceVO, String keyLocal, CacheType cacheType,Integer expireTime) {
		String key = "";
		switch (cacheType){
			case REDIS_TYPE:
				String local = CheckConstant.REDIS_DIR+keyLocal+":";
				key = local + Md5Utils.md5Str(JsonConvertUtils.toJson(sourceVO)+ UUIDProvider.getId());
				if (expireTime==null){
					getRedisSession().setData(key,JsonConvertUtils.toJson(sourceVO));
				}else{
					getRedisSession().setData(key,JsonConvertUtils.toJson(sourceVO),expireTime);
				}
				break;
			case EHCACHE_TYPE:
				break;
			case OSCACHE_TYPE:
				break;
			case MEMCACHED_TYPE:
				break;
		}
		return key;
	}
	
	@Override
	public String updateSourceVOInfo(String key,SourceVO sourceVO, SourceType sourceType, CacheType cacheType,Integer expireTime) {
		switch (sourceType){
			case LOGIN_INFO:
				key = updateSoureceInfo(key,sourceVO,sourceType,cacheType,expireTime);
				break;
			case USER_INFO:
				break;
			case AUTH_INFO:
				break;
			case TEMP_INFO:
				break;
		}
		return key;
	}
	
	/**
	 * 修改 suorce 信息
	 * @param key
	 * @param sourceVO
	 * @param cacheType
	 * @param expireTime
	 * @return
	 */
	private String updateSoureceInfo(String key, SourceVO sourceVO, SourceType sourceType,CacheType cacheType, Integer expireTime) {
		String newKey = "";
		switch (cacheType){
			case REDIS_TYPE:
				//得到结果 json .
				String local = CheckConstant.REDIS_DIR+sourceType.getValue();
				key = local +":"+key;
				String jsonStr = getRedisSession().getData(key);
				//剩余的秒数 .
				long time  = getRedisSession().getKeyLastTime(key);
				if (BaseStringUtil.isNotEmpty(jsonStr)){
					newKey = local + Md5Utils.md5Str(JsonConvertUtils.toJson(sourceVO)+UUIDProvider.getId());
					if (expireTime==null){
						getRedisSession().setData(newKey,JsonConvertUtils.toJson(sourceVO),(int)time);
					}else{
						getRedisSession().setData(newKey,JsonConvertUtils.toJson(sourceVO),expireTime);
					}
				}
				break;
			case EHCACHE_TYPE:
				break;
			case OSCACHE_TYPE:
				break;
			case MEMCACHED_TYPE:
				break;
		}
		return newKey;
	}
	
	public RedisSession getRedisSession() {
		return redisSession;
	}
	
	public void setRedisSession(RedisSession redisSession) {
		this.redisSession = redisSession;
	}
	
}