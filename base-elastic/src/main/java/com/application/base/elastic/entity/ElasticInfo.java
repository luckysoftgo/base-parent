package com.application.base.elastic.entity;

import java.util.List;

/**
 * @author : 孤狼
 * @NAME: ElasticInfo
 * @DESC: ElasticInfo类设计
 **/
public class ElasticInfo {
	
	/**
	 * 集群名
	 */
	private String esClusterName;
	/**
	 * 节点数量
	 */
	private Integer numberOfDataNodes;
	/**
	 * 节点信息
	 */
	private List<EsItemInfo> elasticInfos;
	
	public String getEsClusterName() {
		return esClusterName;
	}
	
	public void setEsClusterName(String esClusterName) {
		this.esClusterName = esClusterName;
	}
	
	public Integer getNumberOfDataNodes() {
		return numberOfDataNodes;
	}
	
	public void setNumberOfDataNodes(Integer numberOfDataNodes) {
		this.numberOfDataNodes = numberOfDataNodes;
	}
	
	public List<EsItemInfo> getElasticInfos() {
		return elasticInfos;
	}
	
	public void setElasticInfos(List<EsItemInfo> elasticInfos) {
		this.elasticInfos = elasticInfos;
	}
	
	/**
	 * es 信息
	 */
	public class EsItemInfo{
		/**
		 * 索引名称
		 */
		private String indexName;
		/**
		 * 分片数量
		 */
		private Integer numberOfShards;
		/**
		 * 副本数量
		 */
		private Integer numberOfReplicas;
		/**
		 * 健康状态
		 */
		private String clusterHealthStatus;
		
		public String getIndexName() {
			return indexName;
		}
		
		public void setIndexName(String indexName) {
			this.indexName = indexName;
		}
		
		public Integer getNumberOfShards() {
			return numberOfShards;
		}
		
		public void setNumberOfShards(Integer numberOfShards) {
			this.numberOfShards = numberOfShards;
		}
		
		public Integer getNumberOfReplicas() {
			return numberOfReplicas;
		}
		
		public void setNumberOfReplicas(Integer numberOfReplicas) {
			this.numberOfReplicas = numberOfReplicas;
		}
		
		public String getClusterHealthStatus() {
			return clusterHealthStatus;
		}
		
		public void setClusterHealthStatus(String clusterHealthStatus) {
			this.clusterHealthStatus = clusterHealthStatus;
		}
	}
}
