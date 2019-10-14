package com.application.base.util.xml;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author : 孤狼
 * @NAME: DestDbInfo
 * @DESC: 目的表信息描述
 **/
@Data
@NoArgsConstructor
public class DestDbInfo implements Serializable {

	/**
	 * 连接的url
	 */
	private String url;
	
	/**
	 * 用户名.
	 */
	private String username;
	
	/**
	 * 密码.
	 */
	private String password;
	
	/**
	 * driver类型: mysql,oracle,db2,sqlserver 等.
	 */
	private String dbtype;
	/**
	 * 数据库的名称
	 */
	private String dbname;
	
	/**
	 * Driver 类型.
	 * com.mysql.jdbc.Driver,
	 * com.oracle.jdbc.driver,
	 */
	private String driver;
	
	
}
