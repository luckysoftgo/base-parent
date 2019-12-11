package com.application.base.operapi.hive.api;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: HiveJdbcSession
 * @DESC: session 定义.
 **/
public interface HiveJdbcSession {

	/**
	 * 创建数据库.
	 * @param dbName
	 * @return
	 */
	public boolean createDatabase(String dbName);
	
	/**
	 * 获取hive上库的信息.
	 * @return
	 */
	public LinkedList<String> showDatabases();
	
	/**
	 * 创建表操作.
	 * @param createSql
	 * @return
	 */
	public boolean createTable(String createSql);
	
	/**
	 * 获取hive上表的信息.
	 * @return
	 */
	public LinkedList<String> showTables();
	
	/**
	 * 显示表的结构
	 * @param tableName
	 * @return
	 */
	public LinkedList<String> descTable(String tableName);
	
	/**
	 * 给固定的表添加数据文件.
	 * @param filePath
	 * @param tableName
	 * @return
	 */
	public boolean loadDataByPath(String filePath,String tableName);
	
	/**
	 * 查询表中的数据.
	 * @param tableName
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectTable(String tableName);
	
	/**
	 * 查询表中的数据.
	 * @param sql
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectSqlTable(String sql);
	
	/**
	 * 使用PreparedStatement查询数据
	 * @param sql
	 * @param param
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectTable(String sql, String[] param);
	
	/**
	 * 统计数据条数.
	 * @param tableName
	 * @return
	 */
	public Integer countData(String tableName);
	
	/**
	 * 刪除数据库.
	 * @param dbName
	 * @return
	 */
	public boolean deleteDatabase(String dbName);
	
	/**
	 * 刪除数据库表.
	 * @param tableName
	 * @return
	 */
	public boolean deleteTable(String tableName);
	
}
