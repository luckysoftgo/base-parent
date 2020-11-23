package com.application.base.operapi.api.phoenix.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * @author : 孤狼
 * @NAME: PhoenixConfig
 * @DESC: phoenix 的配置.
 **/
public class PhoenixConfig extends GenericObjectPoolConfig {
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
	private String phoenixDriver = "org.apache.phoenix.jdbc.PhoenixDriver";
	/**
	 * phoenix 的连接地址.
	 */
	private String phoenixUrl;
	/**
	 * 附带的属性:为了提高phoinx的性能
	 * Properties props = new Properties();
	 * props.setProperty("phoenix.query.timeoutMs", "1200000");
	 * props.setProperty("hbase.rpc.timeout", "1200000");
	 * props.setProperty("hbase.client.scanner.timeout.period", "1200000");
	 */
	private Properties properties;
	
	@PostConstruct
	public void init() {
		properties = new Properties();
		properties.setProperty("phoenix.query.timeoutMs", "1200000");
		properties.setProperty("hbase.rpc.timeout", "1200000");
		properties.setProperty("hbase.client.scanner.timeout.period", "1200000");
	}
	
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
	
	public String getPhoenixDriver() {
		return phoenixDriver;
	}
	
	public void setPhoenixDriver(String phoenixDriver) {
		this.phoenixDriver = phoenixDriver;
	}
	
	public String getPhoenixUrl() {
		return phoenixUrl;
	}
	
	public void setPhoenixUrl(String phoenixUrl) {
		this.phoenixUrl = phoenixUrl;
	}
}
