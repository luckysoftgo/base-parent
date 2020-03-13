package com.application.base.elastic.elastic.restclient.config;

import java.io.Serializable;

/**
 * @NAME: EsTransportNodeConfig
 * @DESC: 节点配置信息
 * @USER: 孤狼
 **/
public class EsRestClientNodeConfig implements Serializable {
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
	/**
	 * 认证用户名
	 */
	private String authName;
	/**
	 * 认证密码
	 */
	private String authPass;
	
	
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
	
	public String getAuthName() {
		return authName;
	}
	
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	
	public String getAuthPass() {
		return authPass;
	}
	
	public void setAuthPass(String authPass) {
		this.authPass = authPass;
	}
}
