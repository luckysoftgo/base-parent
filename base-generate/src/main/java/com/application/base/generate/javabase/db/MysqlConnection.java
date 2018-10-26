package com.application.base.generate.javabase.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @desc 连接conn
 * @author 孤狼
 */
public class MysqlConnection {

	/**
	 * 创建数据库连接
	 * 
	 * @return
	 */
	public static Connection newConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DbInfoConfig.URL_VALUE, DbInfoConfig.USERNAME_VALUE, DbInfoConfig.PASSWORD_VALUE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}


	public static boolean closeConnection(Connection conn) {
		return closeConnection(conn, null, null);
	}

	public static boolean closeConnection(Connection conn, Statement pamStatement, ResultSet rs) {
		boolean flag = true;
		try {
			if (rs != null) {
				rs.close();
			}
			if (pamStatement != null) {
				pamStatement.close();
			}
			
		}
		catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
}
