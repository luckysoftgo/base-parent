package com.application.base.kylin.jdbc.core;

import com.application.base.constant.KylinConstant;
import com.application.base.exception.KylinException;
import com.application.base.kylin.jdbc.config.KylinJdbcConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
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
	private ConcurrentHashMap<String,Connection> connsMap = new ConcurrentHashMap<>(16);
	/**
	 * 操作对象.
	 */
	private ConcurrentHashMap<String,PreparedStatement> pstmtMap = new ConcurrentHashMap<>(16);
	/**
	 * 操作结果集合.
	 */
	private ConcurrentHashMap<String,ResultSet> resultMap = new ConcurrentHashMap<>(16);
	
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
	 * 获取连接
	 * @return
	 */
	public Connection getConnection(String projectName) throws KylinException {
		Connection connection = connsMap.get(projectName);
		if (connection == null) {
			try {
				Driver driver = (Driver) Class.forName(kylinDriver).newInstance();
				Properties info = new Properties();
				info.put(KylinConstant.USER, jdbcConfig.getUserName());
				info.put(KylinConstant.PASSWORD, jdbcConfig.getUserPass());
				connection = driver.connect(jdbcConfig.getKylinUrl()+ KylinConstant.SPLIT+projectName,info);
				connsMap.put(projectName,connection);
			}catch (Exception e){
				logger.error("kylin获得连接异常了,异常信息是:{}",e.getMessage());
				throw new KylinException("kylin获得连接异常了,异常信息是:{"+e.getMessage()+"}");
			}
		}
		return connection;
	}
	
	/**
	 * 使用PreparedStatement查询数据
	 * @param projectName
	 * @param sql
	 * @param param
	 * @return
	 */
	public ResultSet selectSQL(String projectName,String sql, String [] param) throws KylinException{
		ResultSet rs = null;
		try {
			Connection connection = getConnection(projectName);
			PreparedStatement pstmt = connection.prepareStatement(sql);
			if (param!=null && param.length>0){
				for (int i = 0; i < param.length; i++) {
					pstmt.setString(i+1, param[i]);
				}
			}
			rs = pstmt.executeQuery();
			pstmtMap.put(projectName,pstmt);
			resultMap.put(projectName,rs);
		} catch (SQLException e) {
			logger.error("kylin通过PrepareStatement获得数据异常了,异常信息是:{}",e.getMessage());
			throw new KylinException("kylin通过PrepareStatement获得数据异常了,异常信息是:{"+e.getMessage()+"}");
		}
		return rs;
	}
	
	/**
	 * 使用Statement查询数据
	 * @param projectName
	 * @param sql
	 * @return
	 */
	public ResultSet selectSQL(String projectName,String sql) throws KylinException{
		ResultSet rs = null;
		try {
			Connection connection = getConnection(projectName);
			PreparedStatement pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			pstmtMap.put(projectName,pstmt);
			resultMap.put(projectName,rs);
		} catch (SQLException e) {
			logger.error("kylin通過Statement获得数据异常了,异常信息是:{}",e.getMessage());
			throw new KylinException("kylin通過Statement获得数据异常了,异常信息是:{"+e.getMessage()+"}");
		}
		return rs;
	}
	
	/**
	 * 使用Statement查询数据
	 * @param projectName
	 * @param tableName
	 * @return
	 */
	public ResultSet selectMetaSQL(String projectName,String tableName) throws KylinException{
		ResultSet rs = null;
		try {
			Connection connection = getConnection(projectName);
			rs = connection.getMetaData().getTables(null,null,tableName,null);
			resultMap.put(projectName,rs);
		} catch (SQLException e) {
			logger.error("kylin通過Statement获得数据异常了,异常信息是:{}",e.getMessage());
			throw new KylinException("kylin通過Statement获得数据异常了,异常信息是:{"+e.getMessage()+"}");
		}
		return rs;
	}
	
	/**
	 * 关闭连接
	 * @param projectName
	 */
	public void close(String projectName){
		 Connection conn = connsMap.get(projectName);
		 PreparedStatement pstmt = pstmtMap.get(projectName);
		 ResultSet rs = resultMap.get(projectName);
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
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
