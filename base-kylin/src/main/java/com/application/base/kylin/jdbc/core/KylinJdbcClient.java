package com.application.base.kylin.jdbc.core;

import com.application.base.kylin.constant.KylinConstant;
import com.application.base.kylin.exception.KylinException;
import com.application.base.kylin.jdbc.config.KylinJdbcConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : 孤狼
 * @NAME: KylinJdbcClient
 * @DESC: jdbc 客户端操作.
 *  http://kylin.apache.org/docs23/howto/howto_jdbc.html
 **/
public class KylinJdbcClient {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * kylin的driver
	 */
	private String kylinDriver="org.apache.kylin.jdbc.Driver";
	/**
	 * 配置信息
	 */
	private KylinJdbcConfig jdbcConfig;
	/**
	 * 数据所在的项目
	 */
	private String projectName;
	
	/**
	 * 存在的连接.
	 */
	private ConcurrentHashMap<String,ArrayBlockingQueue<Connection>> connsMap = new ConcurrentHashMap<>(16);
	
	/**
	 * 构造函数.
	 * @param jdbcConfig
	 */
	public KylinJdbcClient(KylinJdbcConfig jdbcConfig) {
		this.jdbcConfig = jdbcConfig;
	}
	
	/**
	 * 构造函数.
	 * @param jdbcConfig
	 * @param projectName
	 */
	public KylinJdbcClient(KylinJdbcConfig jdbcConfig,String projectName) {
		this.jdbcConfig = jdbcConfig;
		this.projectName = projectName;
	}
	
	/**
	 * 创建连接池.
	 */
	public Connection initConnect(String projectName){
		try {
			if (StringUtils.isNotBlank(jdbcConfig.getKylinDriver())){
				kylinDriver = jdbcConfig.getKylinDriver();
			}
			Driver driver = (Driver) Class.forName(kylinDriver).newInstance();
			Properties info = new Properties();
			info.put(KylinConstant.USER, jdbcConfig.getUserName());
			info.put(KylinConstant.PASSWORD, jdbcConfig.getUserPass());
			Connection connection = driver.connect(jdbcConfig.getKylinUrl()+ KylinConstant.SPLIT+projectName,info);
			ArrayBlockingQueue<Connection> connections = connsMap.get(projectName);
			if (connections==null || connections.size()==0){
				int maxTotal = jdbcConfig.getMaxTotal();
				connections = new ArrayBlockingQueue<>(maxTotal);
			}
			connections.offer(connection);
			connsMap.put(projectName,connections);
			return connection;
		}catch (Exception e){
			logger.error("kylin获得连接异常了,异常信息是:{}",e.getMessage());
			throw new KylinException("kylin获得连接异常了,异常信息是:{"+e.getMessage()+"}");
		}
	}

	
	/**
	 * 获取连接
	 * @return
	 */
	public Connection getConnection(String projectName) {
		ArrayBlockingQueue<Connection> connections = connsMap.get(projectName);
		try {
			int maxTotal = jdbcConfig.getMaxTotal();
			if (connections!=null && connections.size()>0){
				int count = jdbcConfig.getMinIdle();
				if (connections.size() < count){
					int addCount = maxTotal - count;
					for (int i = 0; i < addCount ; i++) {
						initConnect(projectName);
					}
				}
				return connections.take();
			}else{
				for (int i = 0; i < maxTotal ; i++) {
					initConnect(projectName);
				}
				return connsMap.get(projectName).take();
			}
		}catch (InterruptedException e){
			logger.error("获取connect连接失败了,传递的项目名称是:{},错误异常是:{}",projectName,e.getMessage());
			return null;
		}
	}
	
	/**
	 * 使用PreparedStatement查询数据
	 * @param projectName
	 * @param sql
	 * @param param
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectSQL(String projectName, String sql, String [] param) throws KylinException{
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		LinkedList<Map<String,Object>> finalList = new LinkedList<>();
		try {
			connection = getConnection(projectName);
			if (connection==null){
				return finalList;
			}
			pstmt = connection.prepareStatement(sql);
			if (param!=null && param.length>0){
				for (int i = 1; i <= param.length; i++) {
					pstmt.setString(i, param[i]);
				}
			}
			resultSet = pstmt.executeQuery();
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
				finalList.add(data);
			}
		} catch (SQLException e) {
			logger.error("kylin通过PrepareStatement获得数据异常了,异常信息是:{}",e.getMessage());
			throw new KylinException("kylin通过PrepareStatement获得数据异常了,异常信息是:{"+e.getMessage()+"}");
		}finally {
			close(connection,pstmt,resultSet);
		}
		return finalList;
	}
	
	/**
	 * 使用Statement查询数据
	 * @param projectName
	 * @param sql
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectSQL(String projectName,String sql) throws KylinException{
		return selectSQL(projectName,sql,null);
	}
	
	/**
	 * 使用PreparedStatement查询数据
	 * @param projectName
	 * @param sql
	 * @param param
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectSQL(String projectName, String sql, String [] param,boolean nullback) throws KylinException{
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		LinkedList<Map<String,Object>> finalList = new LinkedList<>();
		try {
			connection = getConnection(projectName);
			if (connection==null){
				return finalList;
			}
			pstmt = connection.prepareStatement(sql);
			if (param!=null && param.length>0){
				for (int i = 1; i <= param.length; i++) {
					pstmt.setString(i, param[i]);
				}
			}
			resultSet = pstmt.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int count = rsmd.getColumnCount();
			Set<String> columns = new HashSet<>();
			for (int i = 1; i <= count ; i++) {
				columns.add(rsmd.getColumnName(i));
			}
			while (resultSet.next()) {
				Map<String,Object> data = new HashMap<>();
				for (String column : columns ) {
					String value = Objects.toString(resultSet.getObject(column),"");
					if (StringUtils.isBlank(value)){
						if (nullback){
							data.put(column,resultSet.getObject(column));
						}
					}else{
						data.put(column,resultSet.getObject(column));
					}
				}
				finalList.add(data);
			}
		} catch (SQLException e) {
			logger.error("kylin通过PrepareStatement获得数据异常了,异常信息是:{}",e.getMessage());
			throw new KylinException("kylin通过PrepareStatement获得数据异常了,异常信息是:{"+e.getMessage()+"}");
		}finally {
			close(connection,pstmt,resultSet);
		}
		return finalList;
	}
	
	/**
	 * 使用Statement查询数据
	 * @param projectName
	 * @param sql
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectSQL(String projectName,String sql,boolean nullback) throws KylinException{
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		LinkedList<Map<String,Object>> finalList = new LinkedList<>();
		try {
			connection = getConnection(projectName);
			if (connection==null){
				return finalList;
			}
			pstmt = connection.prepareStatement(sql);
			resultSet = pstmt.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int count = rsmd.getColumnCount();
			Set<String> columns = new HashSet<>();
			for (int i = 1; i <= count ; i++) {
				columns.add(rsmd.getColumnName(i));
			}
			while (resultSet.next()) {
				Map<String,Object> data = new HashMap<>();
				for (String column : columns ) {
					String value = Objects.toString(resultSet.getObject(column),"");
					if (StringUtils.isBlank(value)){
						if (nullback){
							data.put(column,resultSet.getObject(column));
						}
					}else{
						data.put(column,resultSet.getObject(column));
					}
				}
				finalList.add(data);
			}
		} catch (SQLException e) {
			logger.error("kylin通过PrepareStatement获得数据异常了,异常信息是:{}",e.getMessage());
			throw new KylinException("kylin通过PrepareStatement获得数据异常了,异常信息是:{"+e.getMessage()+"}");
		}finally {
			close(connection,pstmt,resultSet);
		}
		return finalList;
	}
	
	/**
	 * 使用Statement查询数据
	 * @param projectName
	 * @param tableName
	 * @return
	 */
	public LinkedList<Map<String,Object>> selectMetaSQL(String projectName,String tableName) throws KylinException{
		ResultSet resultSet = null;
		Connection connection = null;
		LinkedList<Map<String,Object>> finalList = new LinkedList<>();
		try {
			connection = getConnection(projectName);
			if (connection==null){
				return finalList;
			}
			resultSet = connection.getMetaData().getTables(null,null,tableName,null);
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
				finalList.add(data);
			}
		} catch (SQLException e) {
			logger.error("kylin通過Statement获得数据异常了,异常信息是:{}",e.getMessage());
			throw new KylinException("kylin通過Statement获得数据异常了,异常信息是:{"+e.getMessage()+"}");
		}
		return finalList;
	}
	
	/**
	 * 检测是否连接
	 */
	public boolean check(String projectName){
		boolean result = false;
		try {
			result = getConnection(projectName).isClosed();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 关闭连接
	 * @param connection
	 * @param pstmt
	 * @param resultSet
	 */
	public void close(Connection connection,PreparedStatement pstmt,ResultSet resultSet){
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
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
