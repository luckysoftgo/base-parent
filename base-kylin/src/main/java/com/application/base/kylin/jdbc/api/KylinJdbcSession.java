package com.application.base.kylin.jdbc.api;

import java.util.LinkedList;
import java.util.Map;

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
	public LinkedList<Map<String,Object>> selectSQL(String projectName, String sql, String [] param);
	
	/**
	 * 使用Statement查询数据
	 * @param projectName
	 * @param sql
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectSQL(String projectName, String sql);
	/**
	 * 使用Statement查询数据
	 * @param projectName
	 * @param tableName
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectMetaSQL(String projectName,String tableName);

}
