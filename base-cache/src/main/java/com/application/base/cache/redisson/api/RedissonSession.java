package com.application.base.cache.redisson.api;

import com.application.base.cache.redisson.exception.RedissonException;
import org.redisson.api.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @desc Redisson 的抽象 API 接口
 * @author 孤狼.
 */
public interface RedissonSession {
	
	/**
	 * 获得 redisson 实例
	 * @return
	 * @throws RedissonException
	 */
	RedissonClient getCurrentClient() throws RedissonException;
	
	/**
	 * 关闭 Redisson 客户端连接.
	 */
	void close() throws RedissonException;
	
	/**
	 * 存取 二进制流 对象.
	 * @param objectKey
	 * @param values
	 * @return
	 * @throws RedissonException
	 */
	boolean setRBinaryStream( String objectKey,byte[] values,Integer timeout) throws RedissonException;
	
	/**
	 * 获取 二进制流 对象.
	 * @param objectKey
	 * @return
	 * @throws RedissonException
	 */
	RBinaryStream getRBinaryStream(String objectKey) throws RedissonException;
	
	/**
	 * 存取 地理信息 对象.
	 * @param objectKey
	 * @param value
	 * @return
	 * @throws RedissonException
	 */
	boolean setRGeo( String objectKey,GeoEntry... value) throws RedissonException;
	
	/**
	 * 获取 地理信息 对象.
	 * @param objectKey
	 * @param <V>
	 * @return
	 * @throws RedissonException
	 */
	<V> RGeo<V> getRGeo(String objectKey) throws RedissonException;
	
	/**
	 * 获取 String 字符串对象.
	 * @param objectKey
	 * @param value
	 * @return
	 * @throws RedissonException
	 */
	boolean setRString( String objectKey,Object value,Integer timeout) throws RedissonException;
	
	/**
	 * 获取 String 字符串对象.
	 * @param objectKey
	 * @return
	 * @throws RedissonException
	 */
	String getRString( String objectKey) throws RedissonException;
	
	/**
	 * 获取 String集合 对象.
	 * @return
	 * @throws RedissonException
	 */
	RBuckets getBuckets() throws RedissonException;
	
	/**
	 * 设置 List 集合
	 * @param objectKey
	 * @param values
	 * @return
	 * @throws RedissonException
	 */
	boolean setRList(String objectKey, List<Object> values) throws RedissonException;
	
	/**
	 * 获得 List 集合
	 * @param objectKey
	 * @return
	 * @throws RedissonException
	 */
	List<Object> getRList(String objectKey) throws RedissonException;
	
	/**
	 * 保存 Map 对象.
	 * @param objectKey
	 * @param params
	 * @return
	 * @throws RedissonException
	 */
	boolean setRMap(String objectKey, Map<String,Object> params) throws RedissonException;
	
	/**
	 * 获取 Map 对象.
	 * @param objectKey
	 * @return
	 * @throws RedissonException
	 */
	Map<Object, Object> getRMap(String objectKey) throws RedissonException;
	
	/**
	 * 設置 SortedSet 有序集合.
	 * @param objectKey
	 * @param sets
	 * @return
	 * @throws RedissonException
	 */
	boolean setRSet(String objectKey, Set<Object> sets) throws RedissonException;
	
	/**
	 * 获取 SortedSet 有序集合.
	 * @param objectKey
	 * @return
	 * @throws RedissonException
	 */
	Set<Object> getRSet( String objectKey) throws RedissonException;
	
	/**
	 * 設置 SortedSet 有序集合.
	 * @param objectKey
	 * @param sets
	 * @return
	 * @throws RedissonException
	 */
	boolean setRSortedSet(String objectKey, Set<Object> sets) throws RedissonException;
	
	/**
	 * 获取 SortedSet 有序集合.
	 * @param objectKey
	 * @return
	 * @throws RedissonException
	 */
	LinkedHashSet<Object> getRSortedSet(String objectKey) throws RedissonException;
	
	/**
	 * 设置 Queue 队列
	 * @param objectKey
	 * @param values
	 * @return
	 * @throws RedissonException
	 */
	boolean setRQueue(String objectKey,List<Object> values) throws RedissonException;
	
	/**
	 * 获取 Queue 队列
	 * @param objectKey
	 * @return
	 */
	List<Object> getRQueue(String objectKey) throws RedissonException;
	
	/**
	 * 设置 双端队列 RDeque 队列
	 * @param objectKey
	 * @return
	 * @throws RedissonException
	 */
	boolean setRDeque(String objectKey,List<Object> values) throws RedissonException;
	
	/**
	 * 获取 双端队列 RDeque 队列
	 * @param objectKey
	 * @return
	 * @throws RedissonException
	 */
	List<Object> getRDeque(String objectKey) throws RedissonException;
	
	/**
	 * 获取 BlockingQueue 阻塞队列
	 * @param objectKey
	 * @return
	 * @throws RedissonException
	 */
	boolean setRBlockingQueue(String objectKey,List<Object> values) throws RedissonException;
	
	/**
	 * 获取 BlockingQueue 阻塞队列
	 * @param objectKey
	 * @return
	 * @throws RedissonException
	 */
	List<Object> getRBlockingQueue(String objectKey) throws RedissonException;
	
	/**
	 * 获取 Lock 锁
	 * @param objectKey
	 * @return
	 * @throws RedissonException
	 */
	RLock getRLock(String objectKey) throws RedissonException;
	
	/**
	 * 设置 AtomicLong 原子数
	 * @param objectKey
	 * @return
	 */
	boolean setRAtomicLong(String objectKey, Long value) throws RedissonException;
	
	/**
	 * 获取 AtomicLong 原子数
	 * @param objectKey
	 * @return
	 */
	Long getRAtomicLong(String objectKey) throws RedissonException;
	
	/**
	 * 设置 RAtomicDouble 浮点型
	 * @param objectKey
	 * @return
	 */
	boolean setRAtomicDouble(String objectKey,Double value) throws RedissonException;
	
	/**
	 * 获取 RAtomicDouble 浮点型
	 * @param objectKey
	 * @return
	 */
	Double getRAtomicDouble(String objectKey) throws RedissonException;
	
	/**
	 * 设置 CountDownLatch 记数锁
	 * @param objectKey
	 * @param value
	 * @return
	 * @throws RedissonException
	 */
	boolean setRCountDownLatch(String objectKey,Long value) throws RedissonException;
	
	/**
	 * 获取 CountDownLatch 记数
	 * @param objectKey
	 * @return
	 */
	Long getRCountDownLatch(String objectKey) throws RedissonException;
	
	/**
	 * 设置消息的 Topic
	 * @param objectKey
	 * @param message
	 * @return
	 * @throws RedissonException
	 */
	boolean messagePub(String objectKey,Object message) throws RedissonException;
	
	/**
	 * 获取消息的 Topic
	 * @param objectKey
	 * @return
	 */
	void messageSub(String objectKey) throws RedissonException;
	
	/**
	 * 设置 RBitSet
	 * @param objectKey
	 * @param value
	 * @return
	 * @throws RedissonException
	 */
	boolean setRBitSet(String objectKey,Long value) throws RedissonException;
	
	/**
	 * 获得: RBitSet
	 * @param objectKey
	 * @return
	 */
	RBitSet getRBitSet(String objectKey) throws RedissonException;
	
	/**
	 * 获得: redis 中的所有key
	 * @return
	 * @throws RedissonException
	 */
	RKeys getRKeys() throws RedissonException;
	
	/**
	 * 是否关闭了 netty 连接
	 * @return
	 * @throws RedissonException
	 */
	boolean isShutdown() throws RedissonException;
	
}
