package com.application.base.operapi.api.hive.api;

import com.application.base.operapi.core.ColumnInfo;

import java.util.LinkedList;
import java.util.List;
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
	 * 仅执行hivesql，不返回数据，只返回成功失败，比如执行创建表，加载数据等
	 * @param hivesql
	 * @return
	 */
	public String excuteHiveSql(String hivesql);
	
	/**
	 * 创建表操作.
	 * @param createSql
	 * @return
	 */
	public boolean createTable(String createSql);
	
	/**
	 * 获取生成hive表的hiveql
	 * @param tableName
	 * @param columnList : 列的名称.
	 * @return
	 */
	public boolean createStrTable(String tableName, List<String> columnList);
	
	/**
	 * 获取生成hive表的hiveql
	 * @param tableName
	 * @param columnMapList : 列和类型的集合.
	 * @return
	 */
	public boolean createTable(String tableName, List<Map<String,String >> columnMapList);
	
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
	public LinkedList<Map<String,String>> descTable(String tableName);
	
	/**
	 * 获得hive存储的信息.
	 * @param tableName
	 * @return
	 */
	public Map<String,String> getHiveInfo(String tableName);
	
	/**
	 * 获得hive表的列结构信息.
	 * @param tableName
	 * @return
	 */
	public List<ColumnInfo> getHiveColumns(String tableName);
	
	/**
	 * 给固定的表添加数据文件.
	 * @param filePath
	 * @param tableName
	 * @return
	 */
	public boolean loadDataByPath(String filePath, String tableName);
	
	/**
	 * 给固定的表添加数据文件.
	 * @param hdfsPath
	 * @param tableName
	 * @return
	 */
	public boolean loadDataByHdfsPath(String hdfsPath,String tableName);
	
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
	 * 根据表名称、查询字段、条件、限制条数返回数据,若参数为空,请填入"";
	 * @param tableName
	 * @param columnList
	 * @param condition 条件.
	 * @param limitInfo 分页条件.
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectTable(String tableName, List<String> columnList, String condition,String limitInfo);
	
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
	 * 刪除数据.
	 * @param tableName
	 * @return
	 */
	public boolean deleteTable(String tableName);
	
	/**
	 * 刪除数据库表.
	 * @param tableName
	 * @return
	 */
	public boolean dropTable(String tableName);
	
}
