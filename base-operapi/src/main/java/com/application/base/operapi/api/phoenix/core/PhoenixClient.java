package com.application.base.operapi.api.phoenix.core;


import com.application.base.operapi.api.phoenix.config.PhoenixConfig;
import com.application.base.operapi.api.phoenix.exception.PhoenixException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author : 孤狼
 * @NAME: PhoenixClient
 * @DESC: phoenix 操作数据客户端
 **/
public class PhoenixClient {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 时间格式化
	 */
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 配置信息
	 */
	private PhoenixConfig phoenixConfig;
	/**
	 * driver名称.
	 */
	private String phoenixDriver = "org.apache.phoenix.jdbc.PhoenixDriver";
	/**
	 * 存在的连接.
	 */
	private LinkedBlockingQueue<Connection> connections = new LinkedBlockingQueue<>(1024);
	
	/**
	 * 构造函数.
	 *
	 * @param phoenixConfig
	 */
	public PhoenixClient(PhoenixConfig phoenixConfig) {
		this.phoenixConfig = phoenixConfig;
	}
	
	/**
	 * 初始化 phoenix 的操作.
	 */
	public Connection phoenixConnect() {
		try {
			if (StringUtils.isNotBlank(phoenixConfig.getPhoenixDriver())) {
				phoenixDriver = phoenixConfig.getPhoenixDriver();
			}
			synchronized (PhoenixClient.class) {
				Class.forName(phoenixDriver);
				Connection connn = null;
				if (StringUtils.isNotBlank(phoenixConfig.getUserName()) && StringUtils.isNotBlank(phoenixConfig.getUserPass())) {
					if (null != phoenixConfig.getProperties()) {
						connn = DriverManager.getConnection(phoenixConfig.getPhoenixUrl(), phoenixConfig.getProperties());
					} else {
						connn = DriverManager.getConnection(phoenixConfig.getPhoenixUrl(), phoenixConfig.getUserName(), phoenixConfig.getUserPass());
					}
				} else {
					if (null != phoenixConfig.getProperties()) {
						connn = DriverManager.getConnection(phoenixConfig.getPhoenixUrl(), phoenixConfig.getProperties());
					} else {
						connn = DriverManager.getConnection(phoenixConfig.getPhoenixUrl());
					}
				}
				connections.put(connn);
				return connn;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("初始化连接异常了,异常信息是:{}", e.getMessage());
			throw new PhoenixException("phoenix获得连接异常了,异常信息是:{" + e.getMessage() + "}");
		}
	}
	
	/**
	 * 获取 connection 连接.
	 *
	 * @return
	 */
	public Connection getConnection() {
		try {
			int maxTotal = phoenixConfig.getMaxTotal();
			if (connections != null && connections.size() > 0) {
				int count = phoenixConfig.getMinIdle();
				if (connections.size() < count) {
					int addCount = maxTotal - count;
					for (int i = 0; i < addCount; i++) {
						phoenixConnect();
					}
				}
			} else {
				for (int i = 0; i < maxTotal; i++) {
					phoenixConnect();
				}
			}
			return connections.take();
		} catch (InterruptedException e) {
			logger.error("获取connect连接异常了,错误异常是:{}", e.getMessage());
			return null;
		}
	}
	
	/**
	 * 查询 hbase 的信息并返回.
	 *
	 * @param sql
	 * @return
	 */
	public LinkedList<Map<String, Object>> selectTable(String sql) {
		LinkedList<Map<String, Object>> dataList = new LinkedList();
		Connection connection = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			List<String> colNames = new ArrayList<String>();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			if (null == rs) {
				logger.info("得到的数据结果集合为空!");
				return dataList;
			}
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			List<String> colList = new ArrayList<String>();
			for (int i = 1; i <= colCount; i++) {
				String columnName = rsmd.getColumnName(i);
				colList.add(columnName);
			}
			while (rs.next()) {
				LinkedHashMap<String, Object> datamap = new LinkedHashMap<String, Object>();
				// 遍历每一列
				for (int i = 1; i <= colCount; i++) {
					String columnName = rsmd.getColumnName(i);
					if (colNames != null && colNames.size() > 0 && !colNames.contains(columnName)) {
						continue;
					}
					Object value = rs.getObject(i);
					if (value != null) {
						if (value.getClass() == Date.class || value.getClass() == Timestamp.class || value.getClass() == Time.class) {
							String msg = format.format(value);
							value = msg;
						}
						if (value.getClass() == BigDecimal.class) {
							String msg = new BigDecimal(value.toString()).stripTrailingZeros().toPlainString();
							value = msg;
						}
						datamap.put(columnName, value.toString());
					} else {
						datamap.put(columnName, "");
					}
				}
				dataList.add(datamap);
			}
			return dataList;
		} catch (Exception e) {
			logger.error("获取数据发生了异常,异常信息是:{}", e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
			}
		}
		return dataList;
	}
	
	/**
	 * 执行sql,其中包括创建,修改,删除的操作.
	 *
	 * @param sql
	 * @return
	 */
	public boolean execSql(String sql) {
		Connection connection = getConnection();
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			int count = stmt.executeUpdate(sql);
			if (count > 0) {
				logger.info("操作成功!");
				return true;
			}
		} catch (Exception e) {
			logger.error("获取数据发生了异常,异常信息是:{}", e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
			}
		}
		return false;
	}
	
}
