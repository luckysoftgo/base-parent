package com.application.base.operapi.util;

import com.application.base.operapi.util.bean.ColumnInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author : 孤狼
 * @NAME: DBHeloper
 * @DESC: 操作数据库:
 *  数据库可以是Oracle、Mysql、DB2、SqlServer 等.
 **/
public class DBHeloper {
	
	/**
	 * 测试是否可以使用
	 * @param args
	 */
	public static void main(String[] args) {
		
		//这里是Oracle连接方法
		/*
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@192.168.12.44:1521:orcl";
		String user = "bdc";
		String pwd = "bdc123";
		String table = "FZ_USER_T";
		*/
		
		//这里是SQL Server连接方法
		/*
		String url = "jdbc:sqlserver://localhost:1433;DateBaseName=数据库名";
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
		String uid = "sa";
		String pwd = "sa";
		String table = "FZ_USER_T";
		*/
		
		//mysql
		String driver = "com.mysql.jdbc.Driver";
		String user = "root";
		String pwd = "123456";
		String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8";
		String table = "sum_data_dir";
		
		List list = getTableMapInfo(driver,url,user,pwd,table);
		System.out.println(list);
	}
	
	/**
	 * 获取数据库表的信息.
	 * @param conn
	 * @param table
	 * @return
	 */
	public static List<Map<String,Object>> getTableMapInfo(Connection conn, String table){
		List<Map<String,Object>> result = new ArrayList();
		DatabaseMetaData dbmd = null;
		ResultSet resultSet = null;
		ResultSet rs = null;
		try {
			dbmd = conn.getMetaData();
			resultSet = dbmd.getTables(null, "%", table, new String[] { "TABLE" });
			while (resultSet.next()) {
				String tableName=resultSet.getString("TABLE_NAME");
				System.out.println(tableName);
				if(tableName.equals(table)){
					rs = conn.getMetaData().getColumns(null, getSchema(conn),tableName.toUpperCase(), "%");
					while(rs.next()){
						System.out.println("字段名："+rs.getString("COLUMN_NAME")+"--字段注释："+rs.getString("REMARKS")+"--字段数据类型："+rs.getString("TYPE_NAME"));
						Map<String,Object> map = new HashMap();
						String colName = rs.getString("COLUMN_NAME");
						map.put("columnName", colName);
						String remarks = rs.getString("REMARKS");
						if(remarks == null || remarks.equals("")){
							remarks = colName;
						}
						map.put("columnComment",remarks);
						String dbType = rs.getString("TYPE_NAME");
						map.put("columnDbType",dbType);
						
						map.put("columnJavaType",changeJavaType(dbType));
						map.put("columnHiveType",changeHiveType(dbType));
						map.put("columnValueType",changeValueType(dbType));
						result.add(map);
					}
				}
			}
			rs.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 根据数据库的连接参数，获取指定表的基本信息：字段名、字段类型、字段注释
	 * @param driver 数据库连接驱动
	 * @param url 数据库连接url
	 * @param user	数据库登陆用户名
	 * @param pwd 数据库登陆密码
	 * @param table	表名
	 * @return Map集合
	 */
	public static List<Map<String,Object>> getTableMapInfo(String driver, String url, String user, String pwd, String table){
		Connection conn = null;
		List<Map<String,Object>> dataList = null;
		try {
			conn = getConnections(driver,url,user,pwd);
			dataList = getTableMapInfo(conn,table);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}
	
	/**
	 * 根据数据库的连接参数，获取指定表的基本信息：字段名、字段类型、字段注释
	 * @param conn 连接
	 * @param table	表名
	 * @return Map集合
	 */
	public static List<ColumnInfo> getTableBeanInfo(Connection conn, String table){
		List<ColumnInfo> beanList = new ArrayList();
		DatabaseMetaData dbmd = null;
		ResultSet resultSet = null;
		ResultSet rs = null;
		try {
			dbmd = conn.getMetaData();
			resultSet = dbmd.getTables(null, "%", table, new String[] { "TABLE" });
			while (resultSet.next()) {
				String tableName=resultSet.getString("TABLE_NAME");
				System.out.println(tableName);
				if(tableName.equals(table)){
					rs = conn.getMetaData().getColumns(null, getSchema(conn),tableName.toUpperCase(), "%");
					while(rs.next()){
						ColumnInfo columnInfo = new ColumnInfo();
						System.out.println("字段名："+rs.getString("COLUMN_NAME")+"--字段注释："+rs.getString("REMARKS")+"--字段数据类型："+rs.getString("TYPE_NAME"));
						Map<String,Object> map = new HashMap();
						String colName = rs.getString("COLUMN_NAME");
						columnInfo.setColumnName(colName);
						String remarks = rs.getString("REMARKS");
						if(remarks == null || remarks.equals("")){
							remarks = colName;
						}
						columnInfo.setColumnComment(remarks);
						String dbType = rs.getString("TYPE_NAME");
						columnInfo.setColumnDbType(dbType);
						String javaType = changeJavaType(dbType);
						columnInfo.setColumnJavaType(javaType);
						String hiveType = changeHiveType(dbType);
						columnInfo.setColumnHiveType(hiveType);
						String valueType = changeValueType(dbType);
						columnInfo.setColumnValueType(valueType);
						beanList.add(columnInfo);
					}
				}
			}
			rs.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return beanList;
	}
	
	/**
	 * 根据数据库的连接参数，获取指定表的基本信息：字段名、字段类型、字段注释
	 * @param driver 数据库连接驱动
	 * @param url 数据库连接url
	 * @param user	数据库登陆用户名
	 * @param pwd 数据库登陆密码
	 * @param table	表名
	 * @return Map集合
	 */
	public static List<ColumnInfo> getTableBeanInfo(String driver, String url, String user, String pwd, String table){
		List<ColumnInfo> dataList = new ArrayList();
		Connection conn = null;
		try {
			conn = getConnections(driver,url,user,pwd);
			dataList = getTableBeanInfo(conn,table);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}
	
	/**
	 * 类型转换.
	 * @param dbType
	 * @return
	 */
	private static String changeValueType(String dbType) {
		dbType = dbType.toUpperCase();
		switch(dbType){
			case "VARCHAR":
			case "VARCHAR2":
			case "CHAR":
				return "1";
			case "FLOAT":
			case "DOUBLE":
			case "NUMBER":
			case "DECIMAL":
				return "4";
			case "INT":
			case "SMALLINT":
			case "INTEGER":
				return "2";
			case "BIGINT":
			case "LONG":
				return "6";
			case "DATETIME":
			case "TIMESTAMP":
			case "DATE":
				return "7";
			default:
				return "1";
		}
	}
	
	/**
	 * 类型转换.
	 * @param dbType
	 * https://www.cnblogs.com/HelloBigTable/p/10675258.html
	 * https://www.jianshu.com/p/b9168e48ec09
	 * @return
	 */
	private static String changeJavaType(String dbType) {
		dbType = dbType.toUpperCase();
		switch(dbType){
			case "VARCHAR":
			case "VARCHAR2":
			case "CHAR":
				return "java.lang.String";
			case "FLOAT":
			case "DOUBLE":
			case "NUMBER":
			case "DECIMAL":
				return "java.math.BigDecimal";
			case "INT":
			case "SMALLINT":
			case "INTEGER":
				return "java.lang.Integer";
			case "BIGINT":
			case "LONG":
				return "java.lang.Long";
			case "DATETIME":
			case "TIMESTAMP":
			case "DATE":
				return "java.util.Date";
			default:
				return "java.lang.String";
		}
	}
	
	/**
	 * hive类型转换.
	 * @param dbType
	 * https://www.cnblogs.com/HelloBigTable/p/10675258.html
	 * https://www.jianshu.com/p/b9168e48ec09
	 * @return
	 */
	private static String changeHiveType(String dbType) {
		dbType = dbType.toUpperCase();
		switch(dbType){
			case "VARCHAR":
			case "VARCHAR2":
			case "CHAR":
				return "string";
			case "FLOAT":
				return "float";
			case "DOUBLE":
			case "NUMBER":
				return "double";
			case "DECIMAL":
				return "decimal";
			case "INT":
			case "SMALLINT":
			case "INTEGER":
				return "int";
			case "BIGINT":
			case "LONG":
				return "bigint";
			case "DATETIME":
			case "TIMESTAMP":
				return "timestamp";
			case "DATE":
				return "date";
			default:
				return "string";
		}
	}
	
	/**
	 * 获取连接
	 * @param driver
	 * @param url
	 * @param user
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	private static Connection getConnections(String driver,String url,String user,String pwd) throws Exception {
		Connection conn = null;
		try {
			Properties props = new Properties();
			props.put("remarksReporting", "true");
			props.put("user", user);
			props.put("password", pwd);
			Class.forName(driver);
			conn = DriverManager.getConnection(url, props);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return conn;
	}
	
	/**
	 * 其他数据库不需要这个方法 oracle和db2需要
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private static String getSchema(Connection conn) throws Exception {
		String schema;
		schema = conn.getMetaData().getUserName();
		if ((schema == null) || (schema.length() == 0)) {
			throw new Exception("ORACLE数据库模式不允许为空");
		}
		return schema.toUpperCase().toString();
		
	}
}
