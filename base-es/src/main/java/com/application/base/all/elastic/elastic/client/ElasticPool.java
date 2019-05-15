package com.application.base.all.elastic.elastic.client;

import com.application.base.all.elastic.elastic.util.Pool;
import com.application.base.all.elastic.elastic.util.Protocol;
import com.application.base.all.elastic.exception.ElasticException;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * @NAME: ElasticPool
 * @DESC: Es 的连接池
 * @USER: 孤狼
 **/
public class ElasticPool extends Pool<PreBuiltTransportClient> {
	
	public ElasticPool() {
		this(Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT);
	}
	
	public ElasticPool(String host, int port) {
		this(new GenericObjectPoolConfig(), host, port);
	}
	public ElasticPool(GenericObjectPoolConfig poolConfig, String host, int port) {
		super(poolConfig,new ElasticFactory(host,port));
	}
	
	public ElasticPool(String clusterName, String host, int port) {
		this(new GenericObjectPoolConfig(),clusterName,host, port);
	}
	public ElasticPool(GenericObjectPoolConfig poolConfig, String clusterName, String host, int port) {
		super(poolConfig,new ElasticFactory(clusterName,host,port));
	}
	
	public ElasticPool(String host, int port, String username, String password) {
		this(new GenericObjectPoolConfig(),host, port,username,password);
	}
	public ElasticPool(GenericObjectPoolConfig poolConfig, String host, int port, String username, String password) {
		super(poolConfig,new ElasticFactory(host, port,username,password));
	}
	
	public ElasticPool(String clusterName, String host, int port, String username, String password) {
		this(new GenericObjectPoolConfig(),clusterName,host, port,username,password);
	}
	public ElasticPool(GenericObjectPoolConfig poolConfig, String clusterName, String host, int port, String username, String password) {
		super(poolConfig,new ElasticFactory(clusterName,host,port,username,password));
	}
	
	public ElasticPool(String serverIPs) {
		this(new GenericObjectPoolConfig(),serverIPs);
	}
	public ElasticPool(GenericObjectPoolConfig poolConfig, String serverIPs) {
		super(poolConfig,new ElasticFactory(serverIPs));
	}
	
	public ElasticPool(String clusterName, String serverIPs) {
		this(new GenericObjectPoolConfig(),clusterName,serverIPs);
	}
	public ElasticPool(GenericObjectPoolConfig poolConfig, String clusterName, String serverIPs) {
		super(poolConfig,new ElasticFactory(clusterName,serverIPs));
	}
	
	public ElasticPool(String username, String password, String serverIPs) {
		this(new GenericObjectPoolConfig(),username,password,serverIPs);
	}
	public ElasticPool(GenericObjectPoolConfig poolConfig, String username, String password, String serverIPs) {
		super(poolConfig,new ElasticFactory(username,password,serverIPs));
	}
	
	public ElasticPool(String clusterName, String username, String password, String serverIPs) {
		this(new GenericObjectPoolConfig(),clusterName,username,password,serverIPs);
	}
	public ElasticPool(GenericObjectPoolConfig poolConfig, String clusterName, String username, String password, String serverIPs) {
		super(poolConfig,new ElasticFactory(clusterName,username,password,serverIPs));
	}
	
	@Override
	public PreBuiltTransportClient getResource() {
		PreBuiltTransportClient transportClient = (PreBuiltTransportClient)super.getResource();
		//transportClient.setDataSource(this);
		return transportClient;
	}
	
	/** @deprecated */
	@Deprecated
	@Override
	public void returnBrokenResource(PreBuiltTransportClient resource) {
		if (resource != null) {
			this.returnBrokenResourceObject(resource);
		}
		
	}
	
	/** @deprecated */
	@Deprecated
	@Override
	public void returnResource(PreBuiltTransportClient resource) {
		if (resource != null) {
			try {
				this.returnResourceObject(resource);
			} catch (Exception e) {
				this.returnBrokenResource(resource);
				throw new ElasticException("Resource is returned to the pool as broken", e);
			}
		}
	}
}
