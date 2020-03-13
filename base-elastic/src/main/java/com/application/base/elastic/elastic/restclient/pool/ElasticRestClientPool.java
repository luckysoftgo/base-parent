package com.application.base.elastic.elastic.restclient.pool;

import com.application.base.elastic.elastic.restclient.config.EsRestClientNodeConfig;
import com.application.base.elastic.elastic.restclient.config.EsRestClientPoolConfig;
import com.application.base.elastic.elastic.restclient.factory.ElasticRestClientFactory;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Set;

/**
 * @NAME: ElasticTransportPool
 * @DESC:
 * @USER: 孤狼
 * @DATE: 2019年5月16日
 **/
public class ElasticRestClientPool extends RestClientPool<RestHighLevelClient> {
	
	/**
	 * 集群名称.
	 */
	private String clusterName;
	/**
	 * 节点信息.
	 */
	private Set<EsRestClientNodeConfig> clusterNodes;
	
	public ElasticRestClientPool(EsRestClientPoolConfig restPoolConfig){
		super(restPoolConfig, new ElasticRestClientFactory(restPoolConfig));
		this.clusterName=restPoolConfig.getClusterName();
		this.clusterNodes=restPoolConfig.getEsNodes();
	}
	
	
	public String getClusterName() {
		return clusterName;
	}
	
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
	public Set<EsRestClientNodeConfig> getClusterNodes() {
		return clusterNodes;
	}
	
	public void setClusterNodes(Set<EsRestClientNodeConfig> clusterNodes) {
		this.clusterNodes = clusterNodes;
	}
	
}
