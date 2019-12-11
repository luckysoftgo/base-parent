package com.application.base.operapi.hive.session;

import com.application.base.operapi.hive.api.HiveJdbcSession;
import com.application.base.operapi.hive.core.HiveJdbcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: HiveJdbcOperSession
 * @DESC: jdbc操作数据库实现
 **/
public class HiveJdbcOperSession implements HiveJdbcSession {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private HiveJdbcClient jdbcClient;
	
	public HiveJdbcClient getJdbcClient() {
		return jdbcClient;
	}
	
	public void setJdbcClient(HiveJdbcClient jdbcClient) {
		this.jdbcClient = jdbcClient;
	}
	
	@Override
	public boolean createDatabase(String dbName) {
		return jdbcClient.createDatabase(dbName);
	}
	
	@Override
	public LinkedList<String> showDatabases() {
		return jdbcClient.showDatabases();
	}
	
	@Override
	public boolean createTable(String createSql) {
		return jdbcClient.createTable(createSql);
	}
	
	@Override
	public LinkedList<String> showTables() {
		return jdbcClient.showTables();
	}
	
	@Override
	public LinkedList<String> descTable(String tableName) {
		return jdbcClient.descTable(tableName);
	}
	
	@Override
	public boolean loadDataByPath(String filePath, String tableName) {
		return jdbcClient.loadDataByPath(filePath,tableName);
	}
	
	@Override
	public LinkedList<Map<String, Object>> selectTable(String tableName) {
		return jdbcClient.selectTable(tableName);
	}
	
	@Override
	public LinkedList<Map<String, Object>> selectSqlTable(String sql) {
		return jdbcClient.selectSqlTable(sql);
	}
	
	@Override
	public LinkedList<Map<String, Object>> selectTable(String sql, String[] param) {
		return jdbcClient.selectTable(sql,param);
	}
	
	@Override
	public Integer countData(String tableName) {
		return jdbcClient.countData(tableName);
	}
	
	@Override
	public boolean deleteDatabase(String dbName) {
		return jdbcClient.deleteDatabase(dbName);
	}
	
	@Override
	public boolean deleteTable(String tableName) {
		return jdbcClient.deleteTable(tableName);
	}
}
