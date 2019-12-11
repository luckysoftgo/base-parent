package com.application.base.operapi.hive.core;

import com.application.base.operapi.hive.config.HiveJdbcConfig;
import com.application.base.operapi.hive.exception.HiveException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author : 孤狼
 * @NAME: HiveJdbcClient
 * @DESC: jdbc 客户端操作.
 * http://www.manongjc.com/article/100162.html
 **/
public class HiveJdbcClient {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * hive的driver
	 */
	private String hiveDriver="org.apache.hive.jdbc.HiveDriver";
	/**
	 * 配置信息
	 */
	private HiveJdbcConfig jdbcConfig;
	
	/**
	 * 存在的连接.
	 */
	private ArrayBlockingQueue<Connection> connections = new ArrayBlockingQueue<>(16);
	
	/**
	 * 构造函数.
	 * @param jdbcConfig
	 */
	public HiveJdbcClient(HiveJdbcConfig jdbcConfig) {
		this.jdbcConfig = jdbcConfig;
	}
	
	/**
	 * 创建连接池.
	 */
	public Connection initConnect(){
		try {
			if(StringUtils.isNotBlank(jdbcConfig.getHiveDriver())){
				hiveDriver = jdbcConfig.getHiveDriver();
			}
			Class.forName(hiveDriver);
			Connection connn = DriverManager.getConnection(jdbcConfig.getHiveUrl(),jdbcConfig.getUserName(),jdbcConfig.getUserPass());
			connections.offer(connn);
			return connn;
		}catch (ClassNotFoundException | SQLException e){
			logger.error("初始化连接异常了,异常信息是:{}",e.getMessage());
			throw new HiveException("hive获得连接异常了,异常信息是:{"+e.getMessage()+"}");
		}
	}
	
	
	/**
	 * 获取连接
	 * @return
	 */
	public Connection getConnection() {
		try {
			int maxTotal = jdbcConfig.getMaxTotal();
			if (connections!=null && connections.size()>0){
				int count = jdbcConfig.getMinIdle();
				if (connections.size() < count){
					int addCount = maxTotal - count;
					for (int i = 0; i < addCount ; i++) {
						initConnect();
					}
				}
			}else{
				for (int i = 0; i < maxTotal ; i++) {
					initConnect();
				}
			}
			return connections.take();
		}catch (InterruptedException e){
			logger.error("获取connect连接失败了,错误异常是:{}",e.getMessage());
			return null;
		}
	}
	
	/**
	 * 创建数据库.
	 * @param dbName
	 * @return
	 */
	public boolean createDatabase(String dbName) throws HiveException {
		String sql = "create database "+dbName;
		Connection connn = null;
		Statement statement = null;
		try {
			connn = getConnection();
			statement = connn.createStatement();
			return statement.execute(sql);
		} catch (SQLException e) {
			logger.error("創建数据库:{}失败,失败原因是:{}",dbName,e.getMessage());
			return false;
		}finally {
			close(connn,statement,null,null);
		}
	}
	
	/**
	 * 获取hive上库的信息.
	 * @return
	 */
	public LinkedList<String> showDatabases(){
		String sql = "show databases";
		Connection connn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		LinkedList<String> dataBases = new LinkedList<>();
		try {
			connn = getConnection();
			statement = connn.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String tableName = resultSet.getString(1);
				dataBases.add(tableName);
			}
			return dataBases;
		} catch (SQLException e) {
			logger.error("查询数据库失败,失败原因是:{}",e.getMessage());
			return dataBases;
		}finally {
			close(connn,statement,null,resultSet);
		}
	}
	
	/**
	 * 创建表操作.
	 * @param createSql:
	 * create table test_hive(
		 * 	empno int,
		 * 	ename string,
		 * 	job string,
		 * 	mgr int,
		 * 	hiredate string,
		 * 	sal double,
		 * 	comm double,
		 * 	deptno int
		 * 	)
	 * row format delimited fields terminated by stored as textfile ;
	 * @return
	 */
	public boolean createTable(String createSql){
		Connection connn = null;
		Statement statement = null;
		try {
			connn = getConnection();
			statement = connn.createStatement();
			return statement.execute(createSql);
		} catch (SQLException e) {
			logger.error("查询数据库失败,失败原因是:{}",e.getMessage());
			return false;
		}finally {
			close(connn,statement,null,null);
		}
	}
	
	/**
	 * 获取hive上表的信息.
	 * @return
	 */
	public LinkedList<String> showTables(){
		String sql = "show tables";
		Connection connn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		LinkedList<String> tables = new LinkedList<>();
		try {
			connn = getConnection();
			statement = connn.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String tableName = resultSet.getString(1);
				tables.add(tableName);
			}
			return tables;
		} catch (SQLException e) {
			logger.error("查询数据库表失败,失败原因是:{}",e.getMessage());
			return tables;
		}finally {
			close(connn,statement,null,resultSet);
		}
	}
	
	/**
	 * 显示表的结构
	 * @param tableName
	 * @return
	 */
	public LinkedList<String> descTable(String tableName){
		String sql = "desc "+tableName;
		Connection connn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		LinkedList<String> tableDesc = new LinkedList<>();
		try {
			connn = getConnection();
			statement = connn.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String desc = resultSet.getString(1)+" "+resultSet.getString(2);
				tableDesc.add(desc);
			}
			return tableDesc;
		} catch (SQLException e) {
			logger.error("查询数据库表结构失败,失败原因是:{}",e.getMessage());
			return tableDesc;
		}finally {
			close(connn,statement,null,resultSet);
		}
	}
	
	/**
	 * 给固定的表添加数据文件.
	 * @param filePath
	 * @param tableName
	 * @return
	 */
	public boolean loadDataByPath(String filePath,String tableName){
		String sql = "load data local inpath '" + filePath + "' overwrite into table "+tableName;
		Connection connn = null;
		Statement statement = null;
		try {
			connn = getConnection();
			statement = connn.createStatement();
			return statement.execute(sql);
		} catch (SQLException e) {
			logger.error("将文件路径为:{}的文件放入表:{}失败,失败原因是:{}",filePath,tableName,e.getMessage());
			return false;
		}finally {
			close(connn,statement,null,null);
		}
	}
	
	/**
	 * 查询表中的数据.
	 * @param tableName
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectTable(String tableName){
		String sql = "select * from "+tableName;
		return selectSqlTable(sql);
	}
	
	/**
	 * 查询表中的数据.
	 * @param sql
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectSqlTable(String sql){
		Connection connn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		LinkedList<Map<String,Object>> tableDatas = new LinkedList<>();
		try {
			connn = getConnection();
			statement = connn.createStatement();
			resultSet = statement.executeQuery(sql);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int count = rsmd.getColumnCount();
			Set<String> columns = new HashSet<>();
			for (int i = 1; i <= count ; i++) {
				columns.add(rsmd.getColumnName(i));
			}
			while (resultSet.next()) {
				Map<String,Object> data = new HashMap<>();
				for (String column : columns ) {
					data.put(column,resultSet.getObject(column));
				}
				tableDatas.add(data);
			}
			return tableDatas;
		} catch (SQLException e) {
			logger.error("查询数据库表结构失败,失败原因是:{}",e.getMessage());
			return tableDatas;
		}finally {
			close(connn,statement,null,resultSet);
		}
	}
	
	/**
	 * 使用PreparedStatement查询数据
	 * @param sql
	 * @param param
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectTable(String sql, String[] param){
		Connection connn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		LinkedList<Map<String,Object>> tableDatas = new LinkedList<>();
		try {
			connn = getConnection();
			statement=connn.prepareStatement(sql);
			if(param!=null){
				for (int i = 0; i < param.length; i++) {
					statement.setString(1+i, param [i]);
				}
			}
			resultSet = statement.executeQuery(sql);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int count = rsmd.getColumnCount();
			Set<String> columns = new HashSet<>();
			for (int i = 1; i <= count ; i++) {
				columns.add(rsmd.getColumnName(i));
			}
			while (resultSet.next()) {
				Map<String,Object> data = new HashMap<>();
				for (String column : columns ) {
					data.put(column,resultSet.getObject(column));
				}
				tableDatas.add(data);
			}
			return tableDatas;
		} catch (SQLException e) {
			logger.error("查询数据库表结构失败,失败原因是:{}",e.getMessage());
			return tableDatas;
		}finally {
			close(connn,statement,null,resultSet);
		}
	}
	
	/**
	 * 统计数据条数.
	 * @param tableName
	 * @return
	 */
	public Integer countData(String tableName){
		String sql = "select count(1) from "+tableName;
		Connection connn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		int count=0;
		try {
			connn = getConnection();
			statement = connn.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			logger.error("查询数据库总条数失败,失败原因是:{}",e.getMessage());
			return count;
		}finally {
			close(connn,statement,null,resultSet);
		}
	}
	
	/**
	 * 刪除数据库.
	 * @param dbName
	 * @return
	 */
	public boolean deleteDatabase(String dbName){
		String sql = "drop database if exists "+dbName;
		Connection connn = null;
		Statement statement = null;
		try {
			connn = getConnection();
			statement = connn.createStatement();
			return statement.execute(sql);
		} catch (SQLException e) {
			logger.error("删除数据库:{}失败,失败原因是:{}",dbName,e.getMessage());
			return false;
		}finally {
			close(connn,statement,null,null);
		}
	}
	
	/**
	 * 刪除数据库表.
	 * @param tableName
	 * @return
	 */
	public boolean deleteTable(String tableName){
		String sql = "drop table if exists  "+tableName;
		Connection connn = null;
		Statement statement = null;
		try {
			connn = getConnection();
			statement = connn.createStatement();
			return statement.execute(sql);
		} catch (SQLException e) {
			logger.error("删除数据库表:{}失败,失败原因是:{}",tableName,e.getMessage());
			return false;
		}finally {
			close(connn,statement,null,null);
		}
	}
	
	/**
	 * 关闭连接
	 * @param connection
	 * @param pstmt
	 * @param resultSet
	 */
	public void close(Connection connection,Statement stmt,PreparedStatement pstmt,ResultSet resultSet){
		if(stmt!=null){
			try {
				stmt.close();
				stmt=null;
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
		if(pstmt!=null){
			try {
				pstmt.close();
				pstmt=null;
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
		if(resultSet!=null){
			try {
				resultSet.close();
				resultSet=null;
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
		if(connection!=null){
			try {
				connection.close();
				connection=null;
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
