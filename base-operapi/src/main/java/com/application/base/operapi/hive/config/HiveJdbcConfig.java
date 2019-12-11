package com.application.base.operapi.hive.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author : 孤狼
 * @NAME: HiveJdbcConfig
 * @DESC: 配置信息.
 **/
public class HiveJdbcConfig extends GenericObjectPoolConfig {
	/**
	 * 请求的用户名.
	 */
	private String userName;
	/**
	 * 请求的密码.
	 */
	private String userPass;
	/**
	 * driver名称.
	 */
	private String hiveDriver="org.apache.hive.jdbc.HiveDriver";
	/**
	 * hive的连接地址.
	 */
	private String hiveUrl;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPass() {
		return userPass;
	}
	
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	
	public String getHiveDriver() {
		return hiveDriver;
	}
	
	public void setHiveDriver(String hiveDriver) {
		this.hiveDriver = hiveDriver;
	}
	
	public String getHiveUrl() {
		return hiveUrl;
	}
	
	public void setHiveUrl(String hiveUrl) {
		this.hiveUrl = hiveUrl;
	}
}
