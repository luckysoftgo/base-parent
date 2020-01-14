package com.application.base.operapi.hive.test;

import com.application.base.operapi.api.hive.config.HiveJdbcConfig;
import com.application.base.operapi.api.hive.factory.HiveJdbcSessionFactory;
import com.application.base.operapi.api.hive.pool.HiveJdbcOperPool;
import com.application.base.operapi.core.hive.core.HiveClient;
import com.application.base.operapi.core.hive.rdbs.RdbmsType;

/**
 * @author : 孤狼
 * @NAME: HiveTest
 * @DESC: Hive类设计
 **/
public class HiveTest {
	
	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		HiveJdbcConfig config = hiveConfig();
		HiveJdbcOperPool operPool = getPool(config);
		HiveJdbcSessionFactory factory = getFactory(operPool);

		String user = "root";
		String pwd = "123456";
		String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8";
		String table = "sum_data_dir";
		String sql = HiveClient.getCreateHiveTableSql(url,user,pwd,table,RdbmsType.MYSQL);
		boolean result = factory.getJdbcSession().createTable(sql);
		System.out.println("result = "+result);
		
	}
	
	/**
	 * 得到 config
	 */
	public static HiveJdbcConfig hiveConfig(){
		HiveJdbcConfig config = new HiveJdbcConfig();
		config.setHiveUrl("jdbc:hive2://192.168.10.185:10000/test");
		return config;
	}
	
	/**
	 * 得到连接池.
	 * @param config
	 * @return
	 */
	public static HiveJdbcOperPool getPool(HiveJdbcConfig config){
		HiveJdbcOperPool operPool = new HiveJdbcOperPool(config);
		return operPool;
	}
	
	/**
	 * 操作对象.
	 * @param operPool
	 * @return
	 */
	public static HiveJdbcSessionFactory getFactory(HiveJdbcOperPool operPool){
		HiveJdbcSessionFactory sessionFactory = new HiveJdbcSessionFactory(operPool);
		return sessionFactory;
	}
}
