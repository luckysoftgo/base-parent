package com.application.base.utils.common.guava;

import com.google.common.cache.*;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 孤狼
 * @desc: guava 本地缓存使用
 */
public class GuavaCacheUtil {
	
	/**
	 * 当前发生异常的类.
	 */
	public static final String MODULE = Thread.currentThread().getStackTrace()[1].getClassName();
	private  static Logger logger= LoggerFactory.getLogger(MODULE);
	
	/**
	 * 缓存项最大数量
	 */
	private static final long GUAVA_CACHE_SIZE = 100000;
	
	/**
	 * 缓存时间：分钟
	 */
	private static final long GUAVA_CACHE_TIME = 60*24;
	
	/**
	 * 缓存操作对象
	 */
	private static LoadingCache<String, Object> GUAVA_GLOBAL_CACHE = null;
	
	/**
	 * 初始化操作.
	 */
	static {
		try {
			GUAVA_GLOBAL_CACHE = loadCache(new CacheLoader<String, Object>() {
				@Override
				public Object load(String key) throws Exception {
					// 该方法主要是处理缓存键不存在缓存值时的处理逻辑
					return ObjectUtils.NULL;
				}
			});
		} catch (Exception e) {
			logger.error("初始化Guava Cache出错", MODULE);
		}
	}
	
	/**
	 * 全局缓存设置
	 * <ul>
	 * <li>缓存项最大数量：100000</li>
	 * <li>缓存有效时间（分钟）：60*24</li>
	 * </ul>
	 * @param cacheLoader
	 * @return
	 * @throws Exception
	 */
	private static <K, V> LoadingCache<K, V> loadCache(CacheLoader<K, V> cacheLoader) throws Exception {
        /*
         * maximumSize 缓存池大小，在缓存项接近该大小时， Guava开始回收旧的缓存项 expireAfterAccess 表示最后一次使用该缓存项多长时间后失效 removalListener 移除缓存项时执行的逻辑方法 recordStats 开启Guava Cache的统计功能
         */
		LoadingCache<K, V> cache = CacheBuilder.newBuilder().maximumSize(GUAVA_CACHE_SIZE).expireAfterAccess(GUAVA_CACHE_TIME, TimeUnit.MINUTES)
				.removalListener(new RemovalListener<K, V>() {
					@Override
					public void onRemoval(RemovalNotification<K, V> rn) {
					
					}
				}).recordStats().build(cacheLoader);
		return cache;
	}
	
	/**
	 * 设置缓存值
	 *
	 * @param key
	 * @param value
	 */
	public static void put(String key, Object value) {
		try {
			GUAVA_GLOBAL_CACHE.put(key, value);
		} catch (Exception e) {
			logger.error("设置缓存值出错",MODULE);
		}
	}
	
	/**
	 * 批量设置缓存值
	 *
	 * @param map
	 */
	public static void putAll(Map<? extends String, ? extends Object> map) {
		try {
			GUAVA_GLOBAL_CACHE.putAll(map);
		} catch (Exception e) {
			logger.error("批量设置缓存值出错", MODULE);
		}
	}
	
	/**
	 * 获取缓存值
	 * <p>注：如果键不存在值，将调用CacheLoader的load方法加载新值到该键中</p>
	 *
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		Object obj = null;
		try {
			obj = GUAVA_GLOBAL_CACHE.get(key);
		} catch (Exception e) {
			logger.error("获取缓存值出错", MODULE);
		}
		return obj;
	}
	
	/**
	 * 获取缓存值
	 * <p>注：如果键不存在值，将直接返回 NULL</p>
	 *
	 * @param key
	 * @return
	 */
	public static Object getIfPresent(String key) {
		Object obj = null;
		try {
			obj = GUAVA_GLOBAL_CACHE.getIfPresent(key);
		} catch (Exception e) {
			logger.error("获取缓存值出错", MODULE);
		}
		return obj;
	}
	
	/**
	 * 移除缓存
	 *
	 * @param key
	 */
	public static void remove(String key) {
		try {
			GUAVA_GLOBAL_CACHE.invalidate(key);
		} catch (Exception e) {
			logger.error("移除缓存出错", MODULE);
		}
	}
	
	/**
	 * 批量移除缓存
	 *
	 * @param keys
	 */
	public static void removeAll(Iterable<String> keys) {
		try {
			GUAVA_GLOBAL_CACHE.invalidateAll(keys);
		} catch (Exception e) {
			logger.error("批量移除缓存出错", MODULE);
		}
	}
	
	/**
	 * 清空所有缓存
	 */
	public static void removeAll() {
		try {
			GUAVA_GLOBAL_CACHE.invalidateAll();
		} catch (Exception e) {
			logger.error("清空所有缓存出错", MODULE);
		}
	}
	
	/**
	 * 获取缓存项数量
	 *
	 * @return
	 */
	public static long size() {
		long size = 0;
		try {
			size = GUAVA_GLOBAL_CACHE.size();
		} catch (Exception e) {
			logger.error("获取缓存项数量出错", MODULE);
		}
		return size;
	}
	
	/**
	 * 获取所有缓存项的键
	 *
	 * @return
	 */
	public static List<String> keys() {
		List<String> list = new ArrayList<String>();
		try {
			ConcurrentMap<String, Object> map = GUAVA_GLOBAL_CACHE.asMap();
			for (Map.Entry<String, Object> item : map.entrySet()) {
				list.add(item.getKey());
			}
		} catch (Exception e) {
			logger.error("获取所有缓存项的键出错", MODULE);
		}
		return list;
	}
	
	/**
	 * 缓存命中率
	 *
	 * @return
	 */
	public static double getHitRate() {
		return GUAVA_GLOBAL_CACHE.stats().hitRate();
	}
	
	/**
	 * 加载新值的平均时间，单位为纳秒
	 *
	 * @return
	 */
	public static double getAverageLoadPenalty() {
		return GUAVA_GLOBAL_CACHE.stats().averageLoadPenalty();
	}
	
	/**
	 * 缓存项被回收的总数，不包括显式清除
	 *
	 * @return
	 */
	public static long getEvictionCount() {
		return GUAVA_GLOBAL_CACHE.stats().evictionCount();
	}
	
	/**
	 *获取缓存信息.
	 * @return
	 */
	public static String getCacheInfo(){
		return  "缓存命中率是:"+getHitRate()+",加载新值的平均时间:"+getAverageLoadPenalty()+"秒,缓存被回收的总数是:"+getEvictionCount();
	}
}
