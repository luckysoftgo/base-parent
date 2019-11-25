package com.application.base.elastic.elastic.transport.pool;

import com.application.base.elastic.elastic.transport.config.EsTransportNodeConfig;
import com.application.base.elastic.elastic.transport.config.EsTransportPoolConfig;
import com.application.base.elastic.elastic.transport.factory.ElasticTransportFactory;
import org.elasticsearch.client.transport.TransportClient;

import java.util.Set;

/**
 * @NAME: ElasticTransportPool
 * @DESC: es连接池.
 * @USER: 孤狼.
 **/
public class ElasticTransportPool extends TransportPool<TransportClient> {
	/**
	 * 集群名称.
	 */
	private String clusterName;
	/**
	 * 节点信息.
	 */
	private Set<EsTransportNodeConfig> clusterNodes;
	
	/**
	 * 登录连接串
	 */
	private String loginAuth;
	
	public ElasticTransportPool(){
	}
	
	public ElasticTransportPool(EsTransportPoolConfig transportPoolConfig){
		super(transportPoolConfig, new ElasticTransportFactory(transportPoolConfig.getClusterName(), transportPoolConfig.getEsNodes()));
		this.clusterName=transportPoolConfig.getClusterName();
		this.clusterNodes=transportPoolConfig.getEsNodes();
	}
	
	public ElasticTransportPool(EsTransportPoolConfig transportPoolConfig,String loginAuth){
		super(transportPoolConfig, new ElasticTransportFactory(transportPoolConfig.getClusterName(), transportPoolConfig.getEsNodes(),loginAuth));
		this.clusterName=transportPoolConfig.getClusterName();
		this.clusterNodes=transportPoolConfig.getEsNodes();
		this.loginAuth=loginAuth;
	}
	
	public String getClusterName() {
		return clusterName;
	}
	
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
	public Set<EsTransportNodeConfig> getClusterNodes() {
		return clusterNodes;
	}
	
	public void setClusterNodes(Set<EsTransportNodeConfig> clusterNodes) {
		this.clusterNodes = clusterNodes;
	}
	
	public String getLoginAuth() {
		return loginAuth;
	}
	
	public void setLoginAuth(String loginAuth) {
		this.loginAuth = loginAuth;
	}
}
