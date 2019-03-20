package com.application.base.core.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.application.base.cache.redis.api.RedisSession;
import com.application.base.cache.redis.api.ShardedSession;
import com.application.base.cache.redis.factory.RedisSessionFactory;
import com.application.base.core.apisupport.CacheLoadable;
import com.application.base.utils.common.BaseStringUtil;
import com.application.base.utils.json.JsonConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: 孤狼
 * @desc: 通用模板实现.
 */

@Component("cacheTemplate")
public class CommonCacheTemplate {

    @Autowired
    private RedisSessionFactory sessionFactory;

    /**
     * 避免缓存被穿透的通用模板(防止异常情况下的缓存击穿问题的处理方案).
     * @param key
     * @param expireTime
     * @param clazz
     * @param loadable
     * @param <T>
     */
    public <T> T getDataByCache(String key, int expireTime,Class<T> clazz, CacheLoadable<T> loadable) {
        RedisSession redisSession=sessionFactory.getRedisSession();
        ShardedSession shardedSession=sessionFactory.getShardedSession();
        if (redisSession==null && shardedSession==null){
            return null;
        }
        String value=redisSession.getData(key);
         //如果缓存存在数据，就返回.
        if (BaseStringUtil.isNotEmpty(value)){
            if (redisSession!=null){
                return redisSession.getTypeObject(clazz,key);
            }
            if (shardedSession!=null){
                return shardedSession.getTypeObject(clazz,key);
            }
        }else{
            //如果缓存不存在数据，从DB获取,保存并就返回.
            synchronized (this){
                value=redisSession.getData(key);
                if (BaseStringUtil.isNotEmpty(value)){
                    if (redisSession!=null){
                        return redisSession.getTypeObject(clazz,key);
                    }
                    if (shardedSession!=null){
                        return shardedSession.getTypeObject(clazz,key);
                    }
                }
                //获取数据,核心业务逻辑,从数据库中获取数据
                T result=loadable.load();
                if (redisSession!=null){
                    if (expireTime>0){
                        redisSession.setData(key, JsonConvertUtils.toJson(result),expireTime);
                    }else{
                        redisSession.setData(key, JsonConvertUtils.toJson(result));
                    }
                }
                if (shardedSession!=null){
                    if (expireTime>0){
                        shardedSession.setData(key, JsonConvertUtils.toJson(result),expireTime);
                    }else{
                        shardedSession.setData(key, JsonConvertUtils.toJson(result));
                    }
                }
                return result;
            }
        }
        return null;
    }

    /**
     * 这是个测试方法的伪代码。
     * @param args
     */
    public static void main(String[] args) {
        CommonCacheTemplate template=new CommonCacheTemplate();
        template.getDataByCache("test", 6000, String.class, new CacheLoadable<String>() {
            @Override
            public String load(){
                return "这是模板测试的demo";
            }
        });
    }
}
