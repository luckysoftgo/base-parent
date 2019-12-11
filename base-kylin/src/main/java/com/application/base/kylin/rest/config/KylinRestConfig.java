package com.application.base.kylin.rest.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author : 孤狼
 *
 * @NAME: KylinRestConfig
 *
 * @DESC: 配置信息
 **/
public class KylinRestConfig extends GenericObjectPoolConfig {
	
	/**
	 * Driver
	 */
	private String kylinDriver;
	/**
	 * 请求的用户名
	 */
	private String userName;
	/**
	 * 请求的密码
	 */
	private String userPass;
	/**
	 * 请求的url
	 */
	private String requestUrl;
	
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("kylinDriver:"+getKylinDriver()+"\n");
		buffer.append("userName:"+getUserName()+"\n");
		buffer.append("userPass:"+getUserPass()+"\n");
		buffer.append("requestUrl:"+getRequestUrl()+"\n");
		buffer.append("testOnBorrow:").append(getTestOnBorrow()).append("\n")
				.append("testWhileIdle:").append(getTestWhileIdle()).append("\n")
				.append("testOnReturn:").append(getTestOnReturn()).append("\n")
				.append("testOnCreate:").append(getTestOnCreate()).append("\n")
				.append("maxWaitMillis:").append(getMaxWaitMillis()).append("\n")
				.append("maxTotal:").append(getMaxTotal()).append("\n");
		return buffer.toString();
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
	
	public String getRequestUrl() {
		return requestUrl;
	}
	
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	public String getKylinDriver() {
		return kylinDriver;
	}
	
	public void setKylinDriver(String kylinDriver) {
		this.kylinDriver = kylinDriver;
	}
}
