package com.application.base.utils.common;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.apache.commons.io.Charsets;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 孤狼
 * @desc: bloomFliter 过滤器,利用Guava创建 bloomFilter.
 *
 * 此工具的的主要目的是:用于请求过来时候,减轻系统中对于第三方缓存(redis,redession,memcahced)的压力。
 * 主要是使用位图的方式来标记存在的元素,存在的使用"0"标记,否则使用"1"标记.
 */
public class GuavaBloomFilterUtil implements Serializable {
	
	/**
	 * 默认一个亿的位图大小的容量.
	 */
	public static final int defaultCount = 100000000;
	
	/**
	 * 当前实例下的可以被用作拦截 BloomFilter 的集合.
	 */
	public static final Map<String,BloomFilter> bloomFiltersMap = new ConcurrentHashMap<String,BloomFilter>(16);
	
	/**
	 * 测试数据.
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		int count = 10000, start = 9990, end = 11000;
		//不能存在的用户Id集合.
		BloomFilter<String> values = createStringInstance(count, 0.0001);
		String tag = "userId";
		for (int i = start; i < end; i++) {
			values.put(tag + i);
		}
		//TODO 从数据库中获得登录的用户名称.
		for (int i = 0; i < end; i++) {
			if (values.mightContain(tag + i)) {
				System.out.println(tag + i + ":该用户不属于系统的用户");
			}
		}
		System.out.println("完成结果!" + appElementCount(values));
	}
	
	/**
	 * 是否已经有了存储的Key
	 * @param bloomKey
	 * @return
	 */
	public static boolean existsBloomFilter(String bloomKey) {
		for (Map.Entry<String,BloomFilter> entry : bloomFiltersMap.entrySet()) {
			if (entry.getKey().trim().equals(bloomKey)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 往 实例的 BloomFilter 集合中添加新的BloomFilter
	 * @param bloomKey
	 * @param bloomFilter
	 * @return
	 */
	public static void putBloomFilter(String bloomKey,BloomFilter bloomFilter) {
		synchronized (GuavaBloomFilterUtil.class){
			if (!bloomFiltersMap.containsKey(bloomKey)){
				bloomFiltersMap.put(bloomKey,bloomFilter);
			}
		}
	}
	
	/**
	 * 往 实例的 BloomFilter 集合中添加新的BloomFilter
	 * @param bloomKey
	 * @return
	 */
	public static void removeBloomFilter(String bloomKey) {
		synchronized (GuavaBloomFilterUtil.class){
			if (bloomFiltersMap.containsKey(bloomKey)){
				bloomFiltersMap.remove(bloomKey);
			}
		}
	}
	
	/**
	 * 往 bloomFilter 中添加数据.
	 * @param value
	 * @return
	 */
	public static boolean putVal(String bloomKey,Object value) {
		synchronized (GuavaBloomFilterUtil.class){
			BloomFilter bloomFilter= bloomFiltersMap.get(bloomKey);
			if (bloomFilter!=null){
				return bloomFilter.put(value);
			}
		}
		return false;
	}
	
	/**
	 * 判断bloomFilter中是否包含数据
	 * @return
	 */
	public static boolean mightContain(String bloomKey,Object value) {
		synchronized (GuavaBloomFilterUtil.class){
			BloomFilter bloomFilter= bloomFiltersMap.get(bloomKey);
			if (bloomFilter!=null){
				return bloomFilter.mightContain(value);
			}
		}
		return false;
	}

	/**
	 * 创建一个 defaultCount 长度,精准率为rate的 BloomFilter 对象.
	 * @param rate
	 * @return
	 */
	public static BloomFilter<String> createStringInstance(double rate) {
		BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), defaultCount, rate);
		return bloomFilter;
	}
	
	/**
	 * 创建一个count长度,精准率为rate的 BloomFilter 对象.
	 *
	 * @param count
	 * @param rate
	 * @return
	 */
	public static BloomFilter<String> createStringInstance(Integer count, double rate) {
		if (count == null || count == 0) {
			count = defaultCount;
		}
		BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), count, rate);
		return bloomFilter;
	}
	
	/**
	 * 创建一个defaultCount长度,精准率为rate的 BloomFilter 对象.
	 *
	 * @param rate
	 * @return
	 */
	public static BloomFilter<Integer> createIntegerInstance(double rate) {
		BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), defaultCount, rate);
		return bloomFilter;
	}
	
	/**
	 * 创建一个count长度,精准率为rate的 BloomFilter 对象.
	 *
	 * @param count
	 * @param rate
	 * @return
	 */
	public static BloomFilter<Integer> createIntegerInstance(Integer count, double rate) {
		if (count == null || count == 0) {
			count = defaultCount;
		}
		BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), count, rate);
		return bloomFilter;
	}
	
	
	/**
	 * 创建一个defaultCount长度,精准率为rate的 BloomFilter 对象.
	 *
	 * @param rate
	 * @return
	 */
	public static BloomFilter<Long> createLongInstance(double rate) {
		BloomFilter<Long> bloomFilter = BloomFilter.create(Funnels.longFunnel(), defaultCount, rate);
		return bloomFilter;
	}
	
	/**
	 * 创建一个count长度,精准率为rate的 BloomFilter 对象.
	 *
	 * @param count
	 * @param rate
	 * @return
	 */
	public static BloomFilter<Long> createLongInstance(Integer count, double rate) {
		if (count == null || count == 0) {
			count = defaultCount;
		}
		BloomFilter<Long> bloomFilter = BloomFilter.create(Funnels.longFunnel(), count, rate);
		return bloomFilter;
	}
	
	/**
	 * 往 bloomFilter 中添加数据.
	 * @param bloomFilter
	 * @param value
	 * @return
	 */
	public static boolean putVal(BloomFilter bloomFilter,Object value) {
		return bloomFilter.put(value);
	}
	
	/**
	 * 判断bloomFilter中是否包含数据
	 * @param bloomFilter
	 * @param value
	 * @return
	 */
	public static boolean mightContain(BloomFilter bloomFilter,Object value) {
		return bloomFilter.mightContain(value);
	}
	
	/**
	 * 获得匹配的位图数
	 * @param bloomFilter
	 * @return
	 */
	public static long appElementCount(BloomFilter bloomFilter) {
		return bloomFilter.approximateElementCount();
	}
	
}
