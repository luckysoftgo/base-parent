package com.application.base.elastic.elastic.rest.config;

import java.io.Serializable;

/**
 * @NAME: EsTransportNodeConfig
 * @DESC: 节点配置信息
 * @USER: 孤狼
 **/
public class EsJestNodeConfig implements Serializable {
	/**
	 * 节点名称
	 */
	private String nodeName;
	/**
	 * 节点host
	 */
	private String nodeHost;
	/**
	 * 节点端口
	 */
	private Integer nodePort;
	/**
	 * 节点schema
	 */
	private String nodeSchema;
	
	
	public String getNodeName() {
		return nodeName;
	}
	
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public String getNodeHost() {
		return nodeHost;
	}
	
	public void setNodeHost(String nodeHost) {
		this.nodeHost = nodeHost;
	}
	
	public Integer getNodePort() {
		return nodePort;
	}
	
	public void setNodePort(Integer nodePort) {
		this.nodePort = nodePort;
	}
	
	public String getNodeSchema() {
		return nodeSchema;
	}
	
	public void setNodeSchema(String nodeSchema) {
		this.nodeSchema = nodeSchema;
	}
}
