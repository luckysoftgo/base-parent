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
	 * 使用PreparedStatement查询数据
	 * @param projectName
	 * @param sql
	 * @param param
	 * @param nullback null 值是否返回,true 返回,false 不返回.
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectSQL(String projectName, String sql, String [] param,boolean nullback);
	/**
	 * 使用Statement查询数据
	 * @param projectName
	 * @param sql
	 * @param nullback null 值是否返回,true 返回,false 不返回.
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectSQL(String projectName, String sql,boolean nullback);
	
	/**
	 * 使用Statement查询数据
	 * @param projectName
	 * @param tableName
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectMetaSQL(String projectName,String tableName);

}
