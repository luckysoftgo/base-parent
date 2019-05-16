package com.application.base.all.elastic.elastic.transport.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @NAME: EsTransportPoolConfig
 * @DESC: ES连接池配置.
 * @USER: 孤狼.
 **/
public class EsTransportPoolConfig extends GenericObjectPoolConfig {
	/**
	 * 链接的毫秒数.
	 */
	private long connectTimeMillis;
	/**
	 * 集群的名称.
	 */
	private String clusterName;
	/**
	 * 节点数据
	 */
	Set<EsTransportNodeConfig> esNodes = new HashSet<EsTransportNodeConfig>();
	
	public long getConnectTimeMillis() {
		return connectTimeMillis;
	}
	public void setConnectTimeMillis(long connectTimeMillis) {
		this.connectTimeMillis = connectTimeMillis;
	}
	
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
	public Set<EsTransportNodeConfig> getEsNodes() {
		return esNodes;
	}
	public void setEsNodes(Set<EsTransportNodeConfig> esNodes) {
		this.esNodes = esNodes;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		for (EsTransportNodeConfig config : esNodes) {
			buffer.append("\n集群").append(i).append(":").append(clusterName).append("初始化：\n");
			buffer.append("节点nodeName:").append(config.getNodeName()).append(",nodeHost:").append(config.getNodeHost()).append(",nodePort:").append(config.getNodePort()).append(",nodeSchema:").append(config.getNodeSchema()).append("\n");
		}
		buffer.append("testOnBorrow:").append(getTestOnBorrow()).append("\n")
				.append("testWhileIdle:").append(getTestWhileIdle()).append("\n")
				.append("testOnReturn:").append(getTestOnReturn()).append("\n")
				.append("testOnCreate:").append(getTestOnCreate()).append("\n")
				.append("maxWaitMillis:").append(getMaxWaitMillis()).append("\n")
				.append("maxTotal:").append(getMaxTotal()).append("\n");
		return buffer.toString();
	}

}