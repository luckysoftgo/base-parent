package com.application.base.all.elastic.elastic.util;

import com.application.base.all.elastic.elastic.client.ElasticFactory;
import com.application.base.all.elastic.exception.ElasticException;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


import java.io.Closeable;
import java.util.NoSuchElementException;

/**
 * @NAME: Pool
 * @DESC: 池信息描述
 * @USER: 孤狼
 **/
public abstract class Pool<T> implements Closeable {
	
	protected GenericObjectPool<T> internalPool;
	
	public Pool() {
	}
	
	public Pool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {
		initPool(poolConfig, factory);
	}
	
	@Override
	public void close() {
		destroy();
	}
	
	public boolean isClosed() {
		return this.internalPool.isClosed();
	}
	
	public void initPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {
		if (this.internalPool != null) {
			try {
				this.closeInternalPool();
			} catch (Exception var4) {
			}
		}
		this.internalPool = new GenericObjectPool(factory, poolConfig);
	}
	
	public T getResource() {
		try {
			return internalPool.borrowObject();
		} catch (NoSuchElementException e) {
			throw new ElasticException("Could not get a resource from the pool", e);
		} catch (Exception e) {
			throw new ElasticException("Could not get a resource from the pool", e);
		}
	}
	
	/** @deprecated */
	@Deprecated
	public void returnResourceObject(T resource) {
		if (resource != null) {
			try {
				internalPool.returnObject(resource);
			} catch (Exception e) {
				throw new ElasticException("Could not return the resource to the pool", e);
			}
		}
	}
	
	/** @deprecated */
	@Deprecated
	public void returnBrokenResource(T resource) {
		if (resource != null) {
			returnBrokenResourceObject(resource);
		}
		
	}
	
	/** @deprecated */
	@Deprecated
	public void returnResource(T resource) {
		if (resource != null) {
			returnResourceObject(resource);
		}
		
	}
	
	public void destroy() {
		closeInternalPool();
	}
	
	protected void returnBrokenResourceObject(T resource) {
		try {
			internalPool.invalidateObject(resource);
		} catch (Exception var3) {
			throw new ElasticException("Could not return the broken resource to the pool", var3);
		}
	}
	
	protected void closeInternalPool() {
		try {
			internalPool.close();
		} catch (Exception e) {
			throw new ElasticException("Could not destroy the pool", e);
		}
	}
	
	public int getNumActive() {
		if (poolInactive()){
			return -1;
		}
		return this.internalPool.getNumActive();
	}
	
	public int getNumIdle() {
		if (poolInactive()){
			return -1;
		}
		return this.internalPool.getNumIdle();
	}
	
	public int getNumWaiters() {
		if (poolInactive()){
			return -1;
		}
		return this.internalPool.getNumWaiters();
	}
	
	public long getMeanBorrowWaitTimeMillis() {
		if (poolInactive()){
			return -1;
		}
		return this.internalPool.getMeanBorrowWaitTimeMillis();
	}
	
	public long getMaxBorrowWaitTimeMillis() {
		if (poolInactive()){
			return -1;
		}
		return this.internalPool.getMaxBorrowWaitTimeMillis();
	}
	
	private boolean poolInactive() {
		return this.internalPool == null || this.internalPool.isClosed();
	}
	
	public void addObjects(int count) {
		try {
			for(int i = 0; i < count; ++i) {
				this.internalPool.addObject();
			}
			
		} catch (Exception e) {
			throw new ElasticException("Error trying to add idle objects", e);
		}
	}
}