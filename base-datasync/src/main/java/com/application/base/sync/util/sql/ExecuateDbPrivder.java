package com.application.base.sync.util.sql;

import com.alibaba.druid.pool.DruidDataSource;
import com.application.base.sync.util.xml.DestDbInfo;
import com.application.base.utils.json.JsonConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @desc DB操作类
 * @author admin
 */
@Component
public class ExecuateDbPrivder {
	
	private Logger logger = LoggerFactory.getLogger(getClass().getName());
	/**
	 * 数据库集合.
	 */
	final ConcurrentHashMap<String, DataSource> dataSourceMap = new ConcurrentHashMap();
	/**
	 * 初始化池子
	 * @param destDbInfo
	 */
	public boolean initPool(DestDbInfo destDbInfo){
		try {
			//通过直接创建连接池对象的方式创建连接池对象
			DruidDataSource druidDataSource = new DruidDataSource();
			druidDataSource.setUsername(destDbInfo.getUsername());
			druidDataSource.setPassword(destDbInfo.getPassword());
			druidDataSource.setUrl(destDbInfo.getUrl());
			druidDataSource.setDriverClassName(destDbInfo.getDriver());
			druidDataSource.setInitialSize(3);
			druidDataSource.setMinIdle(3);
			druidDataSource.setMaxActive(30);
			druidDataSource.setMaxWait(60000);
			DataSource druidPool = druidDataSource;
			String key = JsonConvertUtils.toJson(destDbInfo);
			dataSourceMap.put(key,druidPool);
			return true;
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 得到数据库连接
	 */
	public Connection getConn(DestDbInfo destDbInfo){
		if (destDbInfo==null){
			logger.error("传递的对象为空!");
			return null;
		}
		String key = JsonConvertUtils.toJson(destDbInfo);
		DataSource druidPool = dataSourceMap.get(key);
		try {
			if (druidPool!=null){
				return druidPool.getConnection();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 执行入库操作.
	 * @param destDbInfo
	 * @param sqls
	 * @return
	 */
	public boolean executeSql(DestDbInfo destDbInfo,List<String> sqls) throws SQLException {
		if (sqls.isEmpty()){
			return false;
		}
		Connection connection = getConn(destDbInfo);
		Statement statement = connection.createStatement();
		int count = sqls.size();
		for (int i = 0; i < count ; i++) {
			statement.addBatch(sqls.get(i));
			if (i % 10000 ==0){
				statement.executeBatch();
				statement.clearBatch();
			}
		}
		statement.executeBatch();
		statement.clearBatch();
		statement.close();
		connection.close();
		return true;
	}
	
	/**
	 * 执行入库操作.
	 * @param destDbInfo
	 * @param sql
	 * @return
	 */
	public boolean executeSql(DestDbInfo destDbInfo,String sql) throws SQLException {
		Connection connection = getConn(destDbInfo);
		PreparedStatement statement = connection.prepareStatement(sql);
		int count = statement.executeUpdate();
		statement.close();
		connection.close();
		if (count > 0){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 使用PreparedStatement查询数据
	 * @param destDbInfo
	 * @param sql
	 * @return 结果集   不要关闭连接
	 */
	public Integer selectSql(DestDbInfo destDbInfo,String sql){
		try {
			Connection connection = getConn(destDbInfo);
			PreparedStatement pstmt=connection.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			Integer value =null;
			while (rs.next()){
				value=rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			connection.close();
			return value;
		} catch (SQLException e1) {
			logger.error(e1.getMessage());
		}
		return null;
	}
}
