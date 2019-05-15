package com.application.base.all.elastic.elastic.rest.client;

import org.elasticsearch.client.RestHighLevelClient;

import java.io.Serializable;

/**
 * @NAME: ElasticSearchClient
 * @DESC: ES客户端实例.
 * @USER: 孤狼
 **/
public class ElasticSearchClient implements Serializable {
	
	private RestHighLevelClient levelClient;
	
	private String clusterName;
	
	public RestHighLevelClient getLevelClient() {
		return levelClient;
	}
	
	public void setLevelClient(RestHighLevelClient levelClient) {
		this.levelClient = levelClient;
	}
	
	public String getClusterName() {
		return clusterName;
	}
	
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
	public ElasticSearchClient(RestHighLevelClient client, String clusterName){
		this.levelClient = client;
		this.clusterName = clusterName;
	}
	
}
