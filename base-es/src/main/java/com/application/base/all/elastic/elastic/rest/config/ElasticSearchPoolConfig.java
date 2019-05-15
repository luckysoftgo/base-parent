package com.application.base.all.elastic.elastic.rest.config;

import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * @NAME: ElasticSearchPoolConfig
 * @DESC: ES连接池配置.
 * @USER: 孤狼
 **/
public class ElasticSearchPoolConfig extends GenericKeyedObjectPoolConfig {
	
	/**
	 * 集群的配置信息.
	 */
	private ElasticSearchClusterConfig[] poolConfig;
	/**
	 * 正在使用的集群名称
	 */
	private String useClusterName;
	
	public String getUseClusterName() {
		return useClusterName;
	}
	public void setUseClusterName(String useClusterName) {
		this.useClusterName = useClusterName;
	}
	
	public ElasticSearchClusterConfig[] getPoolConfig() {
		return poolConfig;
	}
	
	public void setPoolConfig(ElasticSearchClusterConfig[] poolConfig) {
		this.poolConfig = poolConfig;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		for (ElasticSearchClusterConfig clusterConfig : poolConfig) {
			buffer.append("\n集群").append(i).append(":").append(clusterConfig.getClusterName()).append("初始化：\n");
			int j = 0;
			for (ElasticSearchNodeConfig nodeConfig : clusterConfig.getNodeConfig()) {
				buffer.append("节点").append(j).append("host:").append(nodeConfig.getNodeHost()).append(",port:").append(nodeConfig.getNodePort()).append("\n");
				j++;
			}
			i++;
		}
		buffer.append("testOnBorrow:").append(getTestOnBorrow()).append("\n")
				.append("testWhileIdle:").append(getTestWhileIdle()).append("\n")
				.append("testOnReturn:").append(getTestOnReturn()).append("\n")
				.append("testOnCreate:").append(getTestOnCreate()).append("\n")
				.append("maxWaitMillis:").append(getMaxWaitMillis()).append("\n")
				.append("minIdlePerKey:").append(getMinIdlePerKey()).append("\n")
				.append("maxIdlePerKey:").append(getMaxIdlePerKey()).append("\n")
				.append("maxTotalPerKey:").append(getMaxTotalPerKey()).append("\n")
				.append("maxTotal:").append(getMaxTotal()).append("\n");
		return buffer.toString();
	}

}