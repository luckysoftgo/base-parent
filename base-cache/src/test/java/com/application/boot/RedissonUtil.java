package com.application.boot;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.redisson.config.Config;

/**
 * @desc Redisson 的工具类设计.
 * @author 孤狼
 */
public class RedissonUtil {
	
	private static Logger logger= LoggerFactory.getLogger(RedissonUtil.class);
	
	private static RedissonUtil RedissonUtil;
	
	private RedissonUtil(){}
	
	/**
	 * 提供单例模式
	 * @return
	 */
	public static RedissonUtil getInstance(){
		if(RedissonUtil==null)
			synchronized (RedissonUtil.class) {
				if(RedissonUtil==null) RedissonUtil=new RedissonUtil();
			}
		return RedissonUtil;
	}
	
	
	/**
	 * 使用config创建Redisson
	 * Redisson是用于连接Redis Server的基础类
	 * @param config
	 * @return
	 */
	public RedissonClient getRedisson(Config config){
		RedissonClient redisson= Redisson.create(config);
		logger.info("成功连接Redis Server");
		return redisson;
	}
	
	/**
	 * 使用ip地址和端口创建Redisson
	 * @param ip
	 * @param port
	 * @return
	 */
	public RedissonClient getRedisson(String ip,String port){
		Config config=new Config();
		config.useSingleServer().setAddress("redis://"+ip+":"+port).setPassword("02681330-d47f-4e34-a0fc-4a2f8931c523");
		RedissonClient redisson=Redisson.create(config);
		logger.info("成功连接Redis Server"+"\t"+"连接"+ip+":"+port+"服务器");
		return redisson;
	}
	
	/**
	 * 关闭Redisson客户端连接
	 * @param redisson
	 */
	public void closeRedisson(RedissonClient redisson){
		redisson.shutdown();
		logger.info("成功关闭Redis Client连接");
	}
	
	/**
	 * 获取字符串对象
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <T> RBucket<T> getRBucket(RedissonClient redisson, String objectName){
		RBucket<T> bucket=redisson.getBucket(objectName);
		return bucket;
	}
	
	/**
	 * 获取Map对象
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <K,V> RMap<K, V> getRMap(RedissonClient redisson,String objectName){
		RMap<K, V> map=redisson.getMap(objectName);
		return map;
	}
	
	/**
	 * 获取有序集合
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <V> RSortedSet<V> getRSortedSet(RedissonClient redisson,String objectName){
		RSortedSet<V> sortedSet=redisson.getSortedSet(objectName);
		return sortedSet;
	}
	
	/**
	 * 获取集合
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <V> RSet<V> getRSet(RedissonClient redisson,String objectName){
		RSet<V> rSet=redisson.getSet(objectName);
		return rSet;
	}
	
	/**
	 * 获取列表
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <V> RList<V> getRList(RedissonClient redisson,String objectName){
		RList<V> rList=redisson.getList(objectName);
		return rList;
	}
	
	/**
	 * 获取队列
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <V> RQueue<V> getRQueue(RedissonClient redisson,String objectName){
		RQueue<V> rQueue=redisson.getQueue(objectName);
		return rQueue;
	}
	
	/**
	 * 获取双端队列
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <V> RDeque<V> getRDeque(RedissonClient redisson,String objectName){
		RDeque<V> rDeque=redisson.getDeque(objectName);
		return rDeque;
	}
	
	/**
	 * 此方法不可用在Redisson 1.2 中
	 * 在1.2.2版本中 可用
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	/**
	 public <V> RBlockingQueue<V> getRBlockingQueue(RedissonClient redisson,String objectName){
	 RBlockingQueue rb=redisson.getBlockingQueue(objectName);
	 return rb;
	 }*/
	
	/**
	 * 获取锁
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public RLock getRLock(RedissonClient redisson,String objectName){
		RLock rLock=redisson.getLock(objectName);
		return rLock;
	}
	
	/**
	 * 获取原子数
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public RAtomicLong getRAtomicLong(RedissonClient redisson,String objectName){
		RAtomicLong rAtomicLong=redisson.getAtomicLong(objectName);
		return rAtomicLong;
	}
	
	/**
	 * 获取记数锁
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public RCountDownLatch getRCountDownLatch(RedissonClient redisson,String objectName){
		RCountDownLatch rCountDownLatch=redisson.getCountDownLatch(objectName);
		return rCountDownLatch;
	}
	

	/**
	 * 获取消息的Topic
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public <M> RTopic getRTopic(RedissonClient redisson,String objectName){
		RTopic  rTopic=redisson.getTopic(objectName);
		return rTopic;
	}
	
	
	/**
	 * 获取 BitSet
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public RBitSet getRBitSet(RedissonClient redisson,String objectName){
		RBitSet rBitSet=redisson.getBitSet(objectName);
		return rBitSet;
	}
	
	/**
	 * 获取 RBinaryStream
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public RBinaryStream getRBinaryStream(RedissonClient redisson,String objectName){
		RBinaryStream stream = redisson.getBinaryStream(objectName);
		return stream;
	}
	
	/**
	 * 获取 RGeo
	 * @param redisson
	 * @param objectName
	 * @return
	 */
	public RGeo getRGeo(RedissonClient redisson,String objectName){
		RGeo stream = redisson.getGeo(objectName);
		return stream;
	}
}
