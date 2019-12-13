package com.application.base.operapi.tool.hive.common;

import com.application.base.operapi.tool.hive.core.HiveDataType;
import com.application.base.operapi.tool.hive.model.ColumnInfo;
import com.application.base.operapi.tool.hive.rdbs.DataSourceType;
import com.application.base.operapi.tool.hive.rdbs.MysqlDataType;
import com.application.base.operapi.tool.hive.rdbs.OracleDataType;
import com.application.base.operapi.tool.hive.rdbs.SqlServerDataType;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: DbHelper
 * @DESC: 数据处理类.
 **/
public class DbHelper {
	
	/**
	 * 获取数据库表字段 类型
	 * @param tableName
	 * @param con
	 * @return
	 */
	public static List<ColumnInfo> getTableColumnInfo(String tableName, Connection con){
		List<ColumnInfo> resultList = new ArrayList<>();
		try {
			Statement stmt = con.createStatement();
			String sql = "select * from "+tableName;
			ResultSet res = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = res.getMetaData();
			for(int i=1;i<=rsmd.getColumnCount();i++) {
				resultList.add(new ColumnInfo(rsmd.getColumnName(i), rsmd.getColumnTypeName(i)));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return resultList ;
	}
	
	/**
	 * 获取全表表数据
	 * @param tableName
	 * @param con
	 * @return
	 */
	public static List<List<Object>> getTableDatas(String tableName,Connection con){
		List<List<Object>> resultList = new ArrayList<>();
		ResultSet res = null ;
		Statement stmt = null ;
		try {
			stmt = con.createStatement();
			String sql = "select * from "+tableName;
			res = stmt.executeQuery(sql);
			ResultSetMetaData metaData = res.getMetaData();
			int var =0 ;
			while (res.next()){
				List<Object> row = new ArrayList<>();
				for (int i=1;i<=metaData.getColumnCount();i++){
					row.add(getResultSetValue(res,i));
				}
				resultList.add(row);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			try {
				close(res);
				close(stmt);
				close(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultList ;
	}
	
	/**
	 * 获得数据类型.
	 * @param rs
	 * @param index
	 * @return
	 * @throws SQLException
	 */
	public static Object getResultSetValue(ResultSet rs, int index) throws SQLException {
		Object obj = rs.getObject(index);
		String className = null;
		if (obj != null) {
			className = obj.getClass().getName();
		}
		
		if (obj instanceof Blob) {
			Blob blob = (Blob)obj;
			obj = blob.getBytes(1L, (int)blob.length());
		} else if (obj instanceof Clob) {
			Clob clob = (Clob)obj;
			obj = clob.getSubString(1L, (int)clob.length());
		} else if (!"oracle.sql.TIMESTAMP".equals(className) && !"oracle.sql.TIMESTAMPTZ".equals(className)) {
			if (className != null && className.startsWith("oracle.sql.DATE")) {
				String metaDataClassName = rs.getMetaData().getColumnClassName(index);
				if (!"java.sql.Timestamp".equals(metaDataClassName) && !"oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
					obj = rs.getDate(index);
				} else {
					obj = rs.getTimestamp(index);
				}
			} else if (obj instanceof Date && "java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
				obj = rs.getTimestamp(index);
			}
		} else {
			obj = rs.getTimestamp(index);
		}
		
		return obj;
	}
	
	/**
	 * 类型转换.
	 * @param list
	 * @param dataSourceType
	 * @return
	 */
	public static List<ColumnInfo> tableColumnInfoToHiveColnmnInfo(List<ColumnInfo> list,String dataSourceType){
		list.forEach(columnInfo -> columnInfo.setColumnType(dataBaseDataTypeToHiveDataType(columnInfo.getColumnType(),dataSourceType)));
		return list ;
		
	}
	
	/**
	 * 数据库数据类型转hive数据类型
	 * @param dataBaseDataType
	 * @param dataSourceType
	 * @return
	 */
	public static String dataBaseDataTypeToHiveDataType(String dataBaseDataType,String dataSourceType){
		switch (dataSourceType){
			case DataSourceType.MYSQL_TYPE:
				return MysqlDataType.dataTypeConvertToHiveType(dataBaseDataType);
			case DataSourceType.SQLSERVER_TYPE:
				return SqlServerDataType.dataTypeConvertToHiveType(dataBaseDataType);
			case DataSourceType.ORACLE_TYPE:
				return OracleDataType.dataTypeConvertToHiveType(dataBaseDataType);
		}
		return HiveDataType.STRING;
	}
	
	/**
	 * 获取所有的表名.
	 * @param conn
	 * @param database
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getAlltables(Connection conn,String database) throws SQLException {
		List tables=new ArrayList();
		DatabaseMetaData dbMetaData=conn.getMetaData();
		String[]   types   =   {"TABLE"};
		ResultSet   tabs   =   dbMetaData.getTables(database,null,"%",types);
		while(tabs.next()){
			//只要表名这一列
			tables.add(tabs.getObject("TABLE_NAME"));
		}
		return   tables;
	}
	
	/**
	 * 得到连接
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConn(String driverName, String url, String user, String password) throws SQLException,ClassNotFoundException{
		Class.forName(driverName);
		return DriverManager.getConnection(url, user, password);
	}
	
	/**
	 * 关闭连接
	 * @param conn
	 * @throws SQLException
	 */
	public static void close(Connection conn) throws SQLException{
		if(conn != null){
			conn.close();
		}
	}
	
	/**
	 * 关闭Statement
	 * @param stmt
	 * @throws SQLException
	 */
	public static void close(Statement stmt) throws SQLException{
		if(stmt != null){
			stmt.close();
		}
	}
	
	/**
	 * 关闭结果集
	 * @param rs
	 * @throws
	 */
	public static void close(ResultSet rs) throws SQLException{
		if(rs != null){
			rs.close();
		}
	}
	
	public static void close(ResultSet rs,Statement statement,Connection connection){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
