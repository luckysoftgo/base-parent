package com.application.base;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author : 孤狼
 * @NAME: KylinJdbc
 * @DESC:
 * @DATE: 2019年11月11日
 **/
public class KylinJdbc {
	
	public void connentJdbc() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Driver driver = (Driver) Class.forName("org.apache.kylin.jdbc.Driver").newInstance();
		Properties info = new Properties();
		info.put("user", "ADMIN");
		info.put("password", "KYLIN");
		Connection conn = driver.connect("jdbc:kylin://192.168.10.185:7070/Kkklin", info);
		Statement state = conn.createStatement();
		ResultSet resultSet = state.executeQuery("select * from LOAN_DATA_80W limit 10 ");
		while (resultSet.next()) {
			System.out.println("values:"+resultSet.getString("GRADE")+","+resultSet.getString("ZIP_CODE")+","+resultSet.getString("APPLICATION_TYPE"));
		}
	}
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		KylinJdbc ky = new KylinJdbc();
		ky.connentJdbc();
	}
}
