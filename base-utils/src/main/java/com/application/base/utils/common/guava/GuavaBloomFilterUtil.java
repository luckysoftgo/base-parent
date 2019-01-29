package com.application.base.utils.common.guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import orestes.bloomfilter.CountingBloomFilter;
import orestes.bloomfilter.FilterBuilder;
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
		String tag = "userId";
		
		//不能存在的用户Id集合.
		BloomFilter<String> values = (BloomFilter<String>) createInstance(InputType.STRING, 0.03);
		for (int i = start; i < end; i++) {
			values.put(tag + i);
		}
		//TODO 从数据库中获得登录的用户名称.
		for (int i = 0; i < end; i++) {
			if (values.mightContain(tag+i)) {
				System.out.println(tag +i+ ", 1 : 该用户不属于系统的用户");
			}
		}
		System.out.println("完成结果!" + appElementCount(values));
		
		
		CountingBloomFilter<String> filters =(CountingBloomFilter<String>) createCountInstance(InputType.STRING,100,0.03);
		for (int i = start; i < end; i++) {
			filters.add(tag + i);
		}
		//TODO 从数据库中获得登录的用户名称.
		for (int i = 0; i < end; i++) {
			if (filters.contains(tag + i)) {
				System.out.println(tag + i + ", 2 : 该用户不属于系统的用户");
			}
		}
		System.out.println("完成结果!" + filters.getCountingBits());
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
	 * @param type:String,Integer,Long.
	 * @param rate:精准率.
	 * @return
	 */
	public static BloomFilter<? extends Object> createInstance(InputType type,double rate) {
		Integer count = defaultCount;
		return createInstance(type,count,rate);
	}
	
	/**
	 * 创建一个 defaultCount 长度,精准率为rate的 BloomFilter 对象.
	 * @param type:String,Integer,Long
	 * @param count:过滤器个数大小
	 * @param rate:精准率 越小越好.
	 * @return
	 */
	public static BloomFilter<? extends Object> createInstance(InputType type,Integer count, double rate) {
		if (count == null || count == 0) {
			count = defaultCount;
		}
		BloomFilter<? extends Object> bloomFilter=null;
		switch (type){
			case STRING:
				bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), count, rate);
				break;
			case INTEGER:
				bloomFilter = BloomFilter.create(Funnels.integerFunnel(), count, rate);
				break;
			case LONG:
				bloomFilter = BloomFilter.create(Funnels.longFunnel(), count, rate);
				break;
			default:
				bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), count, rate);
				break;
		}
		//返回默认的过滤器.
		return bloomFilter;
	}
	
	/******************************************************************** CountingBloomFilter *********************************************************************************/
	
	/**
	 * 创建一个 defaultCount 长度,精准率为rate的 CountingBloomFilter 对象.
	 * @param type:String,Integer,Long.
	 * @param rate:精准率.
	 * @return
	 */
	public static CountingBloomFilter<? extends Object> createCountInstance(InputType type,double rate) {
		Integer count = defaultCount;
		return createCountInstance(type,count,rate);
	}
	
	/**
	 * 创建一个 defaultCount 长度,精准率为rate的 CountingBloomFilter 对象.
	 * @param type:String,Integer,Long
	 * @param count:过滤器个数大小
	 * @param rate:精准率 越小越好.
	 * @return
	 */
	public static CountingBloomFilter<? extends Object> createCountInstance(InputType type,Integer count, double rate) {
		if (count == null || count == 0) {
			count = defaultCount;
		}
		CountingBloomFilter<? extends Object> bloomFilter=null;
		switch (type){
			case STRING:
				bloomFilter = new FilterBuilder(count,rate).buildCountingBloomFilter();
				break;
			case INTEGER:
				bloomFilter = new FilterBuilder(count,rate).buildCountingBloomFilter();
				break;
			case LONG:
				bloomFilter = new FilterBuilder(count,rate).buildCountingBloomFilter();
				break;
			default:
				bloomFilter = new FilterBuilder(count,rate).buildCountingBloomFilter();
				break;
		}
		//返回默认的过滤器.
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
	
	/**
	 * 得到过滤器的类型
	 */
	enum InputType{
		/**
		 * 字符串类型
		 */
		STRING("String","字符串类型"),
		/**
		 * Integer类型
		 */
		INTEGER("Integer","Integer类型"),
		/**
		 * Long 类型
		 */
		LONG("Long","Long 类型"),
		;
		
		private String type;
		private String des;
		
		InputType(String type, String des) {
			this.type = type;
			this.des = des;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDes() {
			return des;
		}
		public void setDes(String des) {
			this.des = des;
		}
	}
}
