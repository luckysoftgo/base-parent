package com.application.base.operapi.tool.hive.common.config;

/**
 * @author : 孤狼
 * @NAME: SshConfig
 * @DESC: ssh 连接用的配置.
 **/
public class SshConfig {
	/**
	 * 主机地址.
	 */
	private String host;
	/**
	 * 默认 ssh 端口
	 */
	private int port=22;
	/**
	 * 用户名.
	 */
	private String username;
	/**
	 * 密码.
	 */
	private String password;
	
	public SshConfig() {
	}
	
	public SshConfig(String host, String username, String password) {
		this.host = host;
		this.username = username;
		this.password = password;
	}
	
	public SshConfig(String host, int port, String username, String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
