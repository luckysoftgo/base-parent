package com.application.base.elastic.elastic.rest.pool;

import com.application.base.elastic.elastic.rest.config.EsJestNodeConfig;
import com.application.base.elastic.elastic.rest.config.EsJestPoolConfig;
import com.application.base.elastic.elastic.rest.factory.ElasticJestFactory;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Set;

/**
 * @NAME: ElasticTransportPool
 * @DESC:
 * @USER: 孤狼
 * @DATE: 2019年5月16日
 **/
public class ElasticJestPool extends JestPool<RestHighLevelClient> {
	
	/**
	 * 集群名称.
	 */
	private String clusterName;
	/**
	 * 节点信息.
	 */
	private Set<EsJestNodeConfig> clusterNodes;
	
	public ElasticJestPool(EsJestPoolConfig restPoolConfig){
		super(restPoolConfig, new ElasticJestFactory(restPoolConfig.getClusterName(), restPoolConfig.getEsNodes()));
		this.clusterName=restPoolConfig.getClusterName();
		this.clusterNodes=restPoolConfig.getEsNodes();
	}
	
	
	public String getClusterName() {
		return clusterName;
	}
	
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
	public Set<EsJestNodeConfig> getClusterNodes() {
		return clusterNodes;
	}
	
	public void setClusterNodes(Set<EsJestNodeConfig> clusterNodes) {
		this.clusterNodes = clusterNodes;
	}
	
}
