package com.application.base.operapi.tool.hive.common.config;

import com.application.base.operapi.core.hive.rdbs.RdbmsType;

/**
 * @author : 孤狼
 * @NAME: JdbcConfig
 * @DESC: 操作jdbc使用的配置.
 **/
public class JdbcConfig {
	/**
	 * 主机地址.
	 */
	private String host;
	/**
	 * 端口.
	 */
	private int port;
	/**
	 * 连接的用户名
	 */
	private String userName;
	/**
	 * 连接的密码.
	 */
	private String passWord;
	/**
	 * 数据库类型:mysql; sqlserver; oracle
	 */
	private RdbmsType rdbsType;
	/**
	 * driver 配置.
	 */
	private String driver;
	/**
	 *rdbs 数据库名称
	 */
	private String dataBase;
	/**
	 * 数据库的表名称.
	 */
	private String tableName;

	
	public JdbcConfig() {
	
	}
	
	public JdbcConfig(String host, int port, String userName, String passWord, RdbmsType rdbsType, String driver,
	                  String dataBase, String tableName) {
		this.host = host;
		this.port = port;
		this.userName = userName;
		this.passWord = passWord;
		this.rdbsType = rdbsType;
		this.driver = driver;
		this.dataBase = dataBase;
		this.tableName = tableName;
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
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassWord() {
		return passWord;
	}
	
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public RdbmsType getRdbsType() {
		return rdbsType;
	}
	
	public void setRdbsType(RdbmsType rdbsType) {
		this.rdbsType = rdbsType;
	}
	
	public String getDriver() {
		return driver;
	}
	
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getDataBase() {
		return dataBase;
	}
	
	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
