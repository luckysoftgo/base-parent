package com.application.base.elastic.elastic.transport.config;

import com.application.base.elastic.entity.NodeInfo;
import com.application.base.elastic.util.NodeExecUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.List;

/**
 * @NAME: EsTransportPoolConfig
 * @DESC: ES连接池配置.
 * @author: 孤狼.
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
	 * 登录认证信息.
	 */
	private String authLogin;
	/**
	 * 节点schema
	 */
	private String nodeSchema="http";
	
	/**
	 * 节点配置信息
	 * restcient.serverInfos=192.168.1.1:9300,192.168.1.2:9300,192.168.1.3:9300
	 */
	private String transportServerInfos;
	/**
	 * 节点上的配置信息.
	 */
	private List<NodeInfo> serverNodes;
	
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
	
	/**
	 * 构造函数.
	 */
	public EsTransportPoolConfig() {
		List<NodeInfo> nodeInfos = NodeExecUtil.getNodes(getTransportServerInfos());
		if (nodeInfos!=null && nodeInfos.size()>0){
			serverNodes = nodeInfos;
		}
	}
	
	/**
	 * 构造函数.
	 */
	public EsTransportPoolConfig(String restcientInfos) {
		if (StringUtils.isNotBlank(restcientInfos)){
			transportServerInfos = restcientInfos;
		}
		List<NodeInfo> nodeInfos = NodeExecUtil.getNodes(restcientInfos);
		if (nodeInfos!=null && nodeInfos.size()>0){
			serverNodes = nodeInfos;
		}
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		List<NodeInfo> nodeInfos = NodeExecUtil.getNodes(transportServerInfos);
		int i = 0;
		for (NodeInfo config : nodeInfos) {
			buffer.append("\n集群").append(i).append(":").append(clusterName).append("初始化:\n");
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
	
	public String getAuthLogin() {
		return authLogin;
	}
	
	public void setAuthLogin(String authLogin) {
		this.authLogin = authLogin;
	}
	
	public String getNodeSchema() {
		return nodeSchema;
	}
	
	public void setNodeSchema(String nodeSchema) {
		this.nodeSchema = nodeSchema;
	}
	
	public String getTransportServerInfos() {
		return transportServerInfos;
	}
	
	public void setTransportServerInfos(String transportServerInfos) {
		this.transportServerInfos = transportServerInfos;
	}
	
	public List<NodeInfo> getServerNodes() {
		return serverNodes;
	}
	
	public void setServerNodes(List<NodeInfo> serverNodes) {
		this.serverNodes = serverNodes;
	}
}