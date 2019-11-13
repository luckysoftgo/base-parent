package com.application.base.kylin.jdbc.api;

import java.sql.ResultSet;

/**
 * @author : 孤狼
 * @NAME: KylinJdbcSession
 * @DESC: session 定义.
 **/
public interface KylinJdbcSession {

	/**
	 * 使用PreparedStatement查询数据
	 * @param projectName
	 * @param sql
	 * @param param
	 * @return
	 */
	public ResultSet selectSQL(String projectName,String sql, String [] param);
	
	/**
	 * 使用Statement查询数据
	 * @param projectName
	 * @param sql
	 * @return
	 */
	public ResultSet selectSQL(String projectName, String sql);
	/**
	 * 使用Statement查询数据
	 * @param projectName
	 * @param tableName
	 * @return
	 */
	public ResultSet selectMetaSQL(String projectName,String tableName);
	
	/**
	 * 关闭连接
	 * @param projectName
	 */
	public void close(String projectName);
}
