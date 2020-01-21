package com.application.base.operapi.hive.test;

import com.application.base.operapi.core.hive.core.HiveClient;
import com.application.base.operapi.core.hive.rdbs.RdbmsType;

import java.sql.Connection;

/**
 * @author : 孤狼
 * @NAME: HiveTest
 * @DESC: HiveTest类设计
 **/
public class HiveTest {
	
	public static void main(String[] args) {
		Connection connection = HiveClient.getConnections("org.apache.hive.jdbc.HiveDriver","jdbc:hive2://192.168.10.185:10000/uni_dl","","");
		//List<ColumnInfo>  columnInfos = HiveClient.getTableBeanInfo(connection,"klldata", RdbmsType.MYSQL);
		connection = HiveClient.getConnections(RdbmsType.MYSQL.getDriver(),"jdbc:mysql://192.168.10.143:3306/datax_web?serverTimezone=Asia/Shanghai&useLegacyDatetimeCode=false&useSSL=false&nullNamePatternMatchesAll=true&useUnicode=true&characterEncoding=UTF-8","root","db#@!123WC");
		String sql = HiveClient.getCreateHiveTableSql(connection,"klldata",RdbmsType.MYSQL);
		System.out.println("sql = "+sql);
	}
}
