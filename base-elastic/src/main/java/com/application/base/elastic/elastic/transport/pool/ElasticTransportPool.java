package com.application.base.elastic.elastic.transport.pool;

import com.application.base.elastic.elastic.transport.config.EsTransportPoolConfig;
import com.application.base.elastic.elastic.transport.factory.ElasticTransportFactory;
import com.application.base.elastic.entity.NodeInfo;
import org.elasticsearch.client.transport.TransportClient;

import java.util.List;

/**
 * @NAME: ElasticTransportPool
 * @DESC: es连接池.
 * @AUTHOR : 孤狼.
 **/
public class ElasticTransportPool extends TransportPool<TransportClient> {
	/**
	 * 集群名称.
	 */
	private String clusterName;
	/**
	 * 节点信息.
	 */
	private List<NodeInfo> clusterNodes;
	
	/**
	 * 登录连接串
	 */
	private String loginAuth;
	
	public ElasticTransportPool(){
	}
	
	public ElasticTransportPool(EsTransportPoolConfig transportPoolConfig){
		super(transportPoolConfig, new ElasticTransportFactory(transportPoolConfig));
		this.clusterName=transportPoolConfig.getClusterName();
		this.clusterNodes=transportPoolConfig.getServerNodes();
		this.loginAuth = transportPoolConfig.getAuthLogin();
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
	
	public String getLoginAuth() {
		return loginAuth;
	}
	
	public void setLoginAuth(String loginAuth) {
		this.loginAuth = loginAuth;
	}
}
