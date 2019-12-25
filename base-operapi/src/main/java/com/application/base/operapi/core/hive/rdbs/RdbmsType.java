package com.application.base.operapi.core.hive.rdbs;

/**
 * @author : 孤狼
 * @NAME: RdbmsType
 * @DESC: 数据库的类型信息.
 **/
public enum RdbmsType {
	/**
	 *mysql数据库
	 */
	MYSQL("mysql","com.mysql.jdbc.Driver","mysql数据库"),
	/**
	 *oracle数据库
	 */
	ORACLE("oracle","oracle.jdbc.driver.OracleDriver","oracle数据库"),
	/**
	 * sqlserver数据库
	 */
	SQLSERVER("sqlserver","com.microsoft.sqlserver.jdbc.SQLServerDriver","sqlserver数据库"),

	;
	
	private String name;
	private String driver;
	private String desc;
	
	RdbmsType(String name,String driver,String desc){
		this.name = name;
		this.driver = driver;
		this.desc = desc;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDriver() {
		return driver;
	}
	
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
