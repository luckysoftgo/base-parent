package com.application.base.elastic.entity;

import java.io.Serializable;

/**
 * @author : 孤狼
 * @NAME: NodeInfo
 * @DESC: 节点信息.
 **/
public class NodeInfo implements Serializable {
	
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
	
	public NodeInfo() {
	}
	
	public NodeInfo(String nodeHost, Integer nodePort) {
		this.nodeHost = nodeHost;
		this.nodePort = nodePort;
	}
	
	public NodeInfo(String nodeHost, Integer nodePort, String nodeSchema) {
		this.nodeHost = nodeHost;
		this.nodePort = nodePort;
		this.nodeSchema = nodeSchema;
	}
	
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
