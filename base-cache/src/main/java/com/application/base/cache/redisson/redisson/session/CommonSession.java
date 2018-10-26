package com.application.base.cache.redisson.redisson.session;

import com.application.base.cache.redisson.RedissonUtil;
import com.application.base.cache.redisson.api.RedissonSession;
import com.application.base.cache.redisson.exception.RedissonException;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @desc 抽象类的设置.
 * @author 孤狼.
 */
public class CommonSession implements RedissonSession {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 默认时间设置,一天时间
	 */
	protected static final int DEFAULT_TIMEOUT = 60 * 60 * 24;
	
	/**
	 * 当前的 client .
	 */
	private RedissonClient currentClient;
	
	public void setCurrentClient(RedissonClient currentClient) {
		this.currentClient = currentClient;
	}
	
	/**
	 * 获得当前的:客户端信息
	 * @return
	 */
	@Override
	public RedissonClient getCurrentClient(){
		try {
			return currentClient;
		} catch (Exception e) {
			logger.error("[ redission getCurrentClient 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public void close() throws RedissonException {
		try {
			if (getCurrentClient()!=null && !getCurrentClient().isShutdown()){
				getCurrentClient().shutdown();
			}
		} catch (Exception e) {
			logger.error("[ redission close 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRBinaryStream(String objectKey, byte[] values, Integer timeout) throws RedissonException {
		try {
			RBinaryStream stream = getCurrentClient().getBinaryStream(objectKey);
			stream.clearExpire();
			if (timeout==null){
				stream.set(values,DEFAULT_TIMEOUT, TimeUnit.SECONDS);
			}else{
				stream.set(values,timeout,TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRBinaryStream 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public RBinaryStream getRBinaryStream(String objectKey) throws RedissonException {
		try {
			RBinaryStream stream = getCurrentClient().getBinaryStream(objectKey);
			return stream;
		} catch (Exception e) {
			logger.error("[ redission getRBinaryStream 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRGeo(String objectKey,GeoEntry... value) throws RedissonException {
		try {
			RGeo geo = getCurrentClient().getGeo(objectKey);
			geo.add(value);
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRGeo 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public <V> RGeo<V> getRGeo(String objectKey) throws RedissonException {
		try {
			return getCurrentClient().getGeo(objectKey);
		} catch (Exception e) {
			logger.error("[ redission getRGeo 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRString(String objectKey, Object value, Integer timeout) throws RedissonException {
		try {
			RBucket bucket = getCurrentClient().getBucket(objectKey);
			bucket.clearExpire();
			if (timeout==null){
				bucket.set(value,DEFAULT_TIMEOUT,TimeUnit.SECONDS);
			}else{
				bucket.set(value,timeout,TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRString 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public String getRString(String objectKey) throws RedissonException {
		try {
			RBucket bucket = getCurrentClient().getBucket(objectKey);
			return RedissonUtil.stringValue(bucket.get());
		} catch (Exception e) {
			logger.error("[ redission getRString 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public RBuckets getBuckets() throws RedissonException {
		try {
			RBuckets buckets = getCurrentClient().getBuckets();
			return buckets;
		} catch (Exception e) {
			logger.error("[ redission getBuckets 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRList(String objectKey, List<Object> values) throws RedissonException {
		try {
			RList list = getCurrentClient().getList(objectKey);
			list.clear();
			list.addAll(values);
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRList 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public List<Object> getRList(String objectKey) throws RedissonException {
		List<Object> resultList = new ArrayList<>();
		try {
			RList list = getCurrentClient().getList(objectKey);
			if (list!=null){
				Iterator iterator = list.iterator();
				while (iterator.hasNext()){
					resultList.add(iterator.next());
				}
			}
			return resultList;
		} catch (Exception e) {
			logger.error("[ redission getRList 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRMap(String objectKey, Map<String, Object> params) throws RedissonException {
		try {
			RMap map = getCurrentClient().getMap(objectKey);
			map.clear();
			map.putAll(params);
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRMap 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public Map<Object, Object> getRMap(String objectKey) throws RedissonException {
		Map<Object, Object> resultMap = new HashMap<>(256);
		try {
			RMap map = getCurrentClient().getMap(objectKey);
			if (map!=null){
				for(Object key : map.keySet()){
					resultMap.put(key,map.get(key));
				}
			}
			return resultMap;
		} catch (Exception e) {
			logger.error("[ redission getRMap 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRSet(String objectKey, Set<Object> sets) throws RedissonException {
		try {
			RSet set = getCurrentClient().getSet(objectKey);
			set.clear();
			set.addAll(sets);
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRSet 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public Set<Object> getRSet(String objectKey) throws RedissonException {
		Set<Object> sets = new HashSet<>();
		try {
			RSet set = getCurrentClient().getSet(objectKey);
			if (set!=null){
				Iterator iterator = set.iterator();
				while(iterator.hasNext()){
					sets.add(iterator.next());
				}
			}
			return sets;
		} catch (Exception e) {
			logger.error("[ redission getRSet 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRSortedSet(String objectKey, Set<Object> sets) throws RedissonException {
		try {
			RSortedSet set = getCurrentClient().getSortedSet(objectKey);
			set.clear();
			set.addAll(sets);
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRSortedSet 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public LinkedHashSet<Object> getRSortedSet(String objectKey) throws RedissonException {
		LinkedHashSet<Object> resultSet = new LinkedHashSet<Object>();
		try {
			RSortedSet set = getCurrentClient().getSortedSet(objectKey);
			if (set!=null){
				Iterator iterator = set.iterator();
				while(iterator.hasNext()){
					resultSet.add(iterator.next());
				}
			}
			return resultSet;
		} catch (Exception e) {
			logger.error("[ redission getRSortedSet 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRQueue(String objectKey, List<Object> values) throws RedissonException {
		try {
			RQueue queue = getCurrentClient().getQueue(objectKey);
			queue.clear();
			queue.addAll(values);
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRQueue 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public List<Object>  getRQueue(String objectKey) throws RedissonException {
		List<Object> resultList = new LinkedList<>();
		try {
			RQueue queue = getCurrentClient().getQueue(objectKey);
			if (queue != null){
				resultList = queue.readAll();
			}
			return resultList;
		} catch (Exception e) {
			logger.error("[ redission getRQueue 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRDeque(String objectKey, List<Object> values) throws RedissonException {
		try {
			RDeque queue = getCurrentClient().getDeque(objectKey);
			queue.clear();
			queue.addAll(values);
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRDeque 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public List<Object> getRDeque(String objectKey) throws RedissonException {
		List<Object> resultList = new LinkedList<>();
		try {
			RDeque queue = getCurrentClient().getDeque(objectKey);
			if (queue != null){
				resultList = queue.readAll();
			}
			return resultList;
		} catch (Exception e) {
			logger.error("[ redission getRDeque 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRBlockingQueue(String objectKey,List<Object> values) throws RedissonException {
		try {
			RBlockingDeque blockingDeque = getCurrentClient().getBlockingDeque(objectKey);
			blockingDeque.clear();
			blockingDeque.addAll(values);
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRBlockingQueue 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public List<Object> getRBlockingQueue(String objectKey) throws RedissonException {
		List<Object> resultList = new LinkedList<>();
		try {
			RBlockingDeque blockingDeque = getCurrentClient().getBlockingDeque(objectKey);
			if (blockingDeque!=null){
				resultList = blockingDeque.readAll();
			}
			return resultList;
		} catch (Exception e) {
			logger.error("[ redission getRBlockingQueue 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public RLock getRLock(String objectKey) throws RedissonException {
		try {
			RLock rLock = getCurrentClient().getLock(objectKey);
			return rLock;
		} catch (Exception e) {
			logger.error("[ redission getRLock 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRAtomicLong(String objectKey, Long value) throws RedissonException {
		try {
			RAtomicLong atomicLong = getCurrentClient().getAtomicLong(objectKey);
			atomicLong.clearExpire();
			atomicLong.set(value);
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRAtomicLong 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public Long getRAtomicLong(String objectKey) throws RedissonException {
		try {
			RAtomicLong atomicLong = getCurrentClient().getAtomicLong(objectKey);
			return atomicLong.get();
		} catch (Exception e) {
			logger.error("[ redission getRAtomicLong 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRAtomicDouble(String objectKey,Double value) throws RedissonException {
		try {
			RAtomicDouble atomicDouble = getCurrentClient().getAtomicDouble(objectKey);
			atomicDouble.clearExpire();
			atomicDouble.set(value);
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRAtomicDouble 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public Double getRAtomicDouble(String objectKey) throws RedissonException {
		try {
			RAtomicDouble atomicDouble = getCurrentClient().getAtomicDouble(objectKey);
			return atomicDouble.get();
		} catch (Exception e) {
			logger.error("[ redission getRAtomicDouble 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRCountDownLatch(String objectKey, Long value) throws RedissonException {
		try {
			RCountDownLatch downLatch = getCurrentClient().getCountDownLatch(objectKey);
			downLatch.trySetCount(value);
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRCountDownLatch 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public Long getRCountDownLatch(String objectKey) throws RedissonException {
		try {
			RCountDownLatch downLatch = getCurrentClient().getCountDownLatch(objectKey);
			return downLatch.getCount();
		} catch (Exception e) {
			logger.error("[ redission getRCountDownLatch 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean messagePub(String objectKey, Object message) throws RedissonException {
		try {
			RTopic topic = getCurrentClient().getTopic(objectKey);
			topic.publish(message);
			return true;
		} catch (Exception e) {
			logger.error("[ redission messagePub 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public void messageSub(String objectKey) throws RedissonException {
		try {
			RTopic topic = getCurrentClient().getTopic(objectKey);
			topic.addListener(new MessageListener<String>() {
				@Override
				public void onMessage(String channel, String message) {
					System.out.println("channel = "+channel+",message = "+message);
				}
			});
		} catch (Exception e) {
			logger.error("[ redission messageSub 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean setRBitSet(String objectKey, Long value) throws RedissonException {
		try {
			RBitSet bitSet = getCurrentClient().getBitSet(objectKey);
			bitSet.clear();
			bitSet.set(value);
			return true;
		} catch (Exception e) {
			logger.error("[ redission setRBitSet 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public RBitSet getRBitSet(String objectKey) throws RedissonException {
		try {
			RBitSet bitSet = getCurrentClient().getBitSet(objectKey);
			return bitSet;
		} catch (Exception e) {
			logger.error("[ redission getRBitSet 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public RKeys getRKeys() throws RedissonException {
		try {
			RKeys keys = getCurrentClient().getKeys();
			return keys;
		} catch (Exception e) {
			logger.error("[ redission getRKeys 错误:{}]",e);
			throw new RedissonException(e);
		}
	}
	
	@Override
	public boolean isShutdown() throws RedissonException {
		return getCurrentClient().isShutdown();
	}
	
}
