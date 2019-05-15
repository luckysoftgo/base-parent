package com.application.base.all.elastic.elastic.rest.config;

import java.io.Serializable;

/**
 * @NAME: ElasticSearchClusterConfig.
 * @DESC: 集群配置信息.
 * @USER: 孤狼.
 **/
public class ElasticSearchClusterConfig implements Serializable {
	
	/**
	 * 集群名称
	 */
	private String clusterName;
	/**
	 * 节点下的配置信息集
	 */
	private ElasticSearchNodeConfig[] nodeConfig;
	
	
	public String getClusterName() {
		return clusterName;
	}
	
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
	public ElasticSearchNodeConfig[] getNodeConfig() {
		return nodeConfig;
	}
	
	public void setNodeConfig(ElasticSearchNodeConfig[] nodeConfig) {
		this.nodeConfig = nodeConfig;
	}
}
