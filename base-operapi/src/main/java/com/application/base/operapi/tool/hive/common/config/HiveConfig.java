package com.application.base.operapi.tool.hive.common.config;

/**
 * @author : 孤狼
 * @NAME: HiveConfig
 * @DESC: 操作hive使用的配置.
 **/
public class HiveConfig {
	
	/**
	 * 连接的用户名,密码
	 */
	private String driver="org.apache.hive.jdbc.HiveDriver";
	/**
	 * 连接的url
	 */
	private String url;
	/**
	 * 连接的用户名
	 */
	private String username;
	/**
	 * 连接的密码.
	 */
	private String password;
	/**
	 * 远程文件存放路径.
	 */
	private String remoteFilePath;
	/**
	 * hdfs 的文件路径
	 */
	private String hdfsAddresss;
	
	public HiveConfig() {
	}
	
	public HiveConfig(String url) {
		this.url = url;
	}
	
	public HiveConfig(String url, String username, String password,String hdfsAddresss) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.hdfsAddresss =hdfsAddresss;
	}
	
	public HiveConfig(String driver, String url, String username, String password,String hdfsAddresss) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.hdfsAddresss =hdfsAddresss;
	}
	
	public HiveConfig(String driver, String url, String username, String password, String remoteFilePath,String hdfsAddresss) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.remoteFilePath = remoteFilePath;
		this.hdfsAddresss =hdfsAddresss;
	}
	
	public String getDriver() {
		return driver;
	}
	
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
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
	
	public String getRemoteFilePath() {
		return remoteFilePath;
	}
	
	public void setRemoteFilePath(String remoteFilePath) {
		this.remoteFilePath = remoteFilePath;
	}
	
	public String getHdfsAddresss() {
		return hdfsAddresss;
	}
	
	public void setHdfsAddresss(String hdfsAddresss) {
		this.hdfsAddresss = hdfsAddresss;
	}
}
