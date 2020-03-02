package com.application.base;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author : 孤狼
 * @NAME: KylinJdbc
 * @DESC:
 * @DATE: 2019年11月11日
 **/
public class KylinJdbc {
	
	public Connection getConn() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Driver driver = (Driver) Class.forName("org.apache.kylin.jdbc.Driver").newInstance();
		Properties info = new Properties();
		info.put("user", "ADMIN");
		info.put("password", "KYLIN");
		Connection conn = driver.connect("jdbc:kylin://192.168.10.185:7070/Kkklin", info);
		return conn;
	}
	
	
	public void connentJdbc() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Driver driver = (Driver) Class.forName("org.apache.kylin.jdbc.Driver").newInstance();
		Properties info = new Properties();
		info.put("user", "ADMIN");
		info.put("password", "KYLIN");
		Connection conn = driver.connect("jdbc:kylin://192.168.10.185:7070/Kkklin", info);
		Statement state = conn.createStatement();
		
		String sql = "SELECT GRADE, HOME_OWNERSHIP, ZIP_CODE, APPLICATION_TYPE, DEFAULT_IND, PROCESSING_DTTM, count(1) AS CNT, SUM(LOAN_AMNT) AS SUM_LOAN_AMNT, SUM(FUNDED_AMNT) AS SUM_FUNDED_AMNT, SUM(FUNDED_AMNT_INV) AS SUM_FUNDED_AMNT_INV, SUM(INT_RATE) AS SUM_INT_RATE, SUM(INSTALLMENT) AS SUM_INSTALLMENT, SUM(LAST_PYMNT_AMNT) AS SUM_LAST_PYMNT_AMNT, MAX(LOAN_AMNT) AS MAX_LOAN_AMNT, MAX(FUNDED_AMNT) AS MAX_FUNDED_AMNT, MAX(FUNDED_AMNT_INV) AS MAX_FUNDED_AMNT_INV, MAX(INT_RATE) AS MAX_INT_RATE, MAX(INSTALLMENT) AS MAX_INSTALLMENT, MAX(LAST_PYMNT_AMNT) AS MAX_LAST_PYMNT_AMNT, MIN(LOAN_AMNT) AS MIN_LOAN_AMNT, MIN(FUNDED_AMNT) AS MIN_FUNDED_AMNT, MIN(FUNDED_AMNT_INV) AS MIN_FUNDED_AMNT_INV, MIN(INT_RATE) AS MIN_INT_RATE, MIN(INSTALLMENT) AS MIN_INSTALLMENT, MIN(LAST_PYMNT_AMNT) AS MIN_LAST_PYMNT_AMNT FROM UNI_DL.LOAN_DATA_80W GROUP BY GRADE, HOME_OWNERSHIP, ZIP_CODE, APPLICATION_TYPE, DEFAULT_IND, PROCESSING_DTTM";
		//sql = "select * from LOAN_DATA_80W limit 10 ";
		ResultSet resultSet = state.executeQuery(sql);
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int count = rsmd.getColumnCount();
		Set<String> columns = new HashSet<>();
		for (int i = 1; i <=count ; i++) {
			System.out.println("columnName:"+rsmd.getColumnName(i)+",columnType:"+rsmd.getColumnTypeName(i));
			columns.add(rsmd.getColumnName(i));
		}
		while (resultSet.next()) {
			System.out.print("values:");
			for (String column : columns ) {
				System.out.print("column:"+column+",value:"+resultSet.getObject(column)+";");
			}
			System.out.println();
		}
		
	}
	
	
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		KylinJdbc ky = new KylinJdbc();
		ky.connentJdbc();
	}
}
