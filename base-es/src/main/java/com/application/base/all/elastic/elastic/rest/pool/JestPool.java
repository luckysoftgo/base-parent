package com.application.base.all.elastic.elastic.rest.pool;

import com.application.base.all.elastic.exception.ElasticException;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @NAME: TransportPool
 * @DESC: 连接池接口.
 * @USER: 孤狼
 **/
public class JestPool<T> implements Cloneable {
	
	protected GenericObjectPool<T> internalPool ;
	
	public JestPool(){
		super();
	}
	
	public JestPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory){
		initPool(poolConfig, factory);
	}
	
	public void initPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {
		if (this.internalPool != null) {
			try {
				closeInternalPool();
			} catch (Exception e) {
			}
		}
		
		this.internalPool = new GenericObjectPool<T>(factory, poolConfig);
	}
	
	protected void closeInternalPool(){
		try {
			internalPool.close();
		} catch (Exception e) {
			throw new ElasticException("Could not destroy the pool", e);
		}
	}
	
	public T getResource(){
		try {
			return internalPool.borrowObject();
		} catch (Exception e) {
			throw new ElasticException("Could not get a resource from the pool", e);
		}
	}
	
	
	public void returnResource(final T resource){
		if (resource != null) {
			returnResourceObject(resource);
		}
	}
	
	private void returnResourceObject(final T resource){
		if (resource == null) {
			return;
		}
		try {
			internalPool.returnObject(resource);
		} catch (Exception e) {
			throw new ElasticException("Could not return the resource to the pool", e);
		}
	}
	
	public void returnBrokenResource(final T resource){
		if (resource != null) {
			returnBrokenResourceObject(resource);
		}
	}
	
	private void returnBrokenResourceObject(T resource) {
		try {
			internalPool.invalidateObject(resource);
		} catch (Exception e) {
			throw new ElasticException("Could not return the resource to the pool", e);
		}
	}
	
	public void destroy(){
		closeInternalPool();
	}
	
	
	public int getNumActive() {
		if (poolInactive()) {
			return -1;
		}
		
		return this.internalPool.getNumActive();
	}
	
	public int getNumIdle() {
		if (poolInactive()) {
			return -1;
		}
		
		return this.internalPool.getNumIdle();
	}
	
	public int getNumWaiters() {
		if (poolInactive()) {
			return -1;
		}
		
		return this.internalPool.getNumWaiters();
	}
	
	public long getMeanBorrowWaitTimeMillis() {
		if (poolInactive()) {
			return -1;
		}
		
		return this.internalPool.getMeanBorrowWaitTimeMillis();
	}
	
	public long getMaxBorrowWaitTimeMillis() {
		if (poolInactive()) {
			return -1;
		}
		
		return this.internalPool.getMaxBorrowWaitTimeMillis();
	}
	
	private boolean poolInactive() {
		return this.internalPool == null || this.internalPool.isClosed();
	}
	
	public void addObjects(int count) throws Exception {
		try {
			for (int i = 0; i < count; i++) {
				this.internalPool.addObject();
			}
		} catch (Exception e) {
			throw new Exception("Error trying to add idle objects", e);
		}
	}
}
