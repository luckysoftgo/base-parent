package com.application.base.elastic.elastic.restclient.pool;

import com.application.base.elastic.elastic.restclient.config.EsRestClientPoolConfig;
import com.application.base.elastic.elastic.restclient.factory.ElasticRestClientFactory;
import com.application.base.elastic.entity.NodeInfo;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.List;

/**
 * @NAME: ElasticTransportPool
 * @DESC:
 * @author: 孤狼
 **/
public class ElasticRestClientPool extends RestClientPool<RestHighLevelClient> {
	
	/**
	 * 集群名称.
	 */
	private String clusterName;
	/**
	 * 节点信息.
	 */
	private List<NodeInfo> clusterNodes;
	
	public ElasticRestClientPool(EsRestClientPoolConfig restPoolConfig){
		super(restPoolConfig, new ElasticRestClientFactory(restPoolConfig));
		this.clusterName=restPoolConfig.getClusterName();
		this.clusterNodes=restPoolConfig.getServerNodes();
	}
	
	
	public String getClusterName() {
		return clusterName;
	}
	
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
	public List<NodeInfo> getClusterNodes() {
		return clusterNodes;
	}
	
	public void setClusterNodes(List<NodeInfo> clusterNodes) {
		this.clusterNodes = clusterNodes;
	}
	
}
