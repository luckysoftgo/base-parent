package com.application.base.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @desc 操作类
 * @author 孤狼
 */
public class DbCommandUtil {

	private Logger logger = LoggerFactory.getLogger(DbCommandUtil.class.getClass().getName());
	
	/**	纯 java 式的连接
	 * 定义常量来存储配置
	 */
	public static String DRIVER=null; 
	public static String URL=null; 	
	public static String USER=null;   
	public static String PASS=null;   
	
	Connection conn=null;			
	PreparedStatement pstmt=null;	
	ResultSet rs=null;						
	
	//获得数据连接信息.
	static{
		URL = "jdbc:mysql://127.0.0.1:3306/test1?useUnicode=true&characterEncoding=utf8" ;
		USER = "root" ;
		PASS = "root" ;
		DRIVER = "com.mysql.jdbc.Driver" ;
	}
	
	
	/**
	 * 得到数据库连接
	 */
	public Connection getConn() throws ClassNotFoundException,SQLException{	
		Class.forName(DRIVER);                       
		conn=(Connection) DriverManager.getConnection(URL,USER,PASS);
		return conn;
	}
	
	
	/**
	 * 要执行的增 ，删 ，改 的操作，不执行查询 (注意参数的使用)
	 */
	public int executeSQL(String preparedSql,String [] param){
		int count =0;
		/**
		 * 执行的操作
		 */
		try {
			conn=this.getConn();
			pstmt=conn.prepareStatement(preparedSql);
			if(param!=null){						
				for (int i = 0; i < param.length; i++) {
					pstmt.setString(1+i, param [i]);
				}				
			}
			count=pstmt.executeUpdate();
		}catch(ClassNotFoundException e){
			logger.error(e.getMessage());
		}catch (SQLException e) {
			logger.error(e.getMessage());
		}		
		return count;								  
	}
	
	/**
	 * 要执行的增 ，删 ，改 的操作，不执行查询 (注意参数的使用)
	 */
	public int executeSQL(String preparedSql){
		int count =0;
		/**
		 * 执行的操作
		 */
		try {
			conn=this.getConn();					
			pstmt=conn.prepareStatement(preparedSql);
			count=pstmt.executeUpdate();			
		}catch(ClassNotFoundException e){
			logger.error(e.getMessage());
		}catch (SQLException e) {
			logger.error(e.getMessage());					 
		}		
		return count;								  
	}
	
	/**
	 * 要执行的复杂操作
	 */
	public boolean executeAllSQL(String preparedSql){
		boolean result = false;
		try {
			conn=this.getConn();					
			pstmt=conn.prepareStatement(preparedSql);
			result = pstmt.execute();
		}catch(ClassNotFoundException e){
			logger.error(e.getMessage());
		}catch (SQLException e) {
			logger.error(e.getMessage());					 
		}		
		return result;								  
	}
	
	/**
	 * 使用PreparedStatement查询数据
	 * @param sql 
	 * @param params 参数列表
	 * @return 结果集   不要关闭连接
	 */
	public ResultSet selectSQL(String sql,String [] param){
		try {
			conn=this.getConn();
			pstmt=conn.prepareStatement(sql);           
			for (int i = 0; i < param.length; i++) {
				pstmt.setString(i+1, param[i]);
			}
			rs=pstmt.executeQuery();                    
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			logger.error(e1.getMessage());
		}
		return rs;
	}

	/**
	 * 关闭所有的接口 (注意括号中的参数)
	 */
	public void closeAll(){
		//判断是否关闭，要时没有关闭，就让它关闭，并给它附一空值（null）,下同
		if(pstmt!=null){
			try {
				pstmt.close();
				pstmt=null;
			} catch (SQLException e) {
				logger.error(e.getMessage());   
			}
		}
		if(rs!=null){
			try {
				rs.close();
				rs=null;
			} catch (SQLException e) {
				logger.error(e.getMessage());   
			}
		}
		if(conn!=null){
			try {
				conn.close();
				conn=null;
			} catch (SQLException e) {
				logger.error(e.getMessage());  
			}
		}
	}	
	
	
	/**
	 * 将ResultSet转换成Map
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getResultMap(ResultSet rs)
			throws SQLException {
		Map<String, String> hm = new HashMap<String, String>(16);
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		for (int i = 1; i <= count; i++) {
			String key = rsmd.getColumnLabel(i);
			String value = rs.getString(i);
			hm.put(key, value);
		}
		return hm;
	}

	/**
	 * 将ResultSet转换成list
	 * @param rs
	 * @return
	 * @throws java.sql.SQLException
	 */
	public List resultSetToList(ResultSet rs)
			throws java.sql.SQLException {
		if (rs == null) {
			return Collections.EMPTY_LIST;
		}
		ResultSetMetaData md = rs.getMetaData(); 
		int columnCount = md.getColumnCount(); 
		List list = new ArrayList();
		Map rowData = new HashMap(16);
		while (rs.next()) {
			rowData = new HashMap(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(rowData);
			System.out.println("list:" + list.toString());
		}
		return list;
	}
	
	/**
	 * 使用statement执行查询
	 * 
	 * @param sql
	 *            执行的SQL语句
	 * @return 不可关闭连接
	 */
	public ResultSet selectSQL(String sql){
		try {
			conn=this.getConn();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
		} catch (ClassNotFoundException e1) {
			logger.error(e1.getMessage());
		} catch (SQLException e1) {
			logger.error(e1.getMessage());
		}		
		return rs;
	}
	
	/**
	 * 检查数据库连接
	 * @param manager
	 * @return true:无法连接;false:正常
	 */
	public boolean checkCon(DbCommandUtil manager) {
		boolean result = false;
		try {
			result = manager.getConn().isClosed();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
		return result;
	}
	
}
