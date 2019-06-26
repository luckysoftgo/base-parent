package com.application.base.cache.redisson.redisson.pool;

import com.application.base.cache.redisson.redisson.pool.config.BasicConfig;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @NAME: ElasticTransportFactory.
 * @DESC: ES 连接池的工厂.
 * @USER: 孤狼.
 **/
public class RedissonInstanceFactory implements PooledObjectFactory<RedissonClient> {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 实例
	 */
	private RedissonClient redissonClient;
	
	/**
	 * 连接池参数 spring 注入
	 */
	private Config poolConfig;
	
	/**
	 * 构造方法
	 */
	public RedissonInstanceFactory() {}
	
	/**
	 * 构造方法
	 */
	public RedissonInstanceFactory(BasicConfig config) {
		this.poolConfig = config.getInstance();
	}
	
	@Override
	public PooledObject<RedissonClient> makeObject() throws Exception {
		try {
			redissonClient = Redisson.create(poolConfig);
		}
		catch (Exception ex) {
			logger.error("格式化传入的ip端口异常了,请检查出传入的字符串信息,error:{}" , ex.getMessage());
		}
		return new DefaultPooledObject(redissonClient);
	}
	
	@Override
	public void destroyObject(PooledObject<RedissonClient> pooledObject) throws Exception {
		RedissonClient client=pooledObject.getObject();
		if (client!=null){
			try {
				client.shutdown();
			} catch (Exception e) {
				//ignore
			}
		}
	}
	
	@Override
	public boolean validateObject(PooledObject<RedissonClient> pooledObject) {
		RedissonClient client = pooledObject.getObject();
		if (client!=null){
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void activateObject(PooledObject<RedissonClient> pooledObject) throws Exception {
	}
	
	@Override
	public void passivateObject(PooledObject<RedissonClient> pooledObject) throws Exception {
	}
}