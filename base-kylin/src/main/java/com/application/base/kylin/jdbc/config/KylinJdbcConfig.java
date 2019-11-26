package com.application.base.kylin.jdbc.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author : 孤狼
 * @NAME: KylinJdbcConfig
 * @DESC: 配置信息.
 **/
public class KylinJdbcConfig  extends GenericObjectPoolConfig {
	/**
	 * 请求的用户名.
	 */
	private String userName;
	/**
	 * 请求的密码.
	 */
	private String userPass;
	/**
	 * 项目名称.
	 */
	private String projectName;
	/**
	 * kylin的连接地址.
	 */
	private String kylinUrl;
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("userName:"+getUserName()+"\n");
		buffer.append("userPass:"+getUserPass()+"\n");
		buffer.append("kylinUrl:"+getKylinUrl()+"\n");
		buffer.append("projectName:"+getProjectName()+"\n");
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
	
	public String getKylinUrl() {
		return kylinUrl;
	}
	
	public void setKylinUrl(String kylinUrl) {
		this.kylinUrl = kylinUrl;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
