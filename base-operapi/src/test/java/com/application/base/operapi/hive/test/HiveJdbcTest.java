package com.application.base.operapi.hive.test;

import com.application.base.operapi.api.hive.config.HiveJdbcConfig;
import com.application.base.operapi.api.hive.factory.HiveJdbcSessionFactory;
import com.application.base.operapi.api.hive.pool.HiveJdbcOperPool;
import com.application.base.operapi.core.hive.core.HiveClient;
import com.application.base.operapi.core.hive.rdbs.RdbmsType;

/**
 * @author : 孤狼
 * @NAME: HiveJdbcTest
 * @DESC: Hive类设计
 **/
public class HiveJdbcTest {
	
	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		HiveJdbcConfig config = hiveConfig();
		HiveJdbcOperPool operPool = getPool(config);
		HiveJdbcSessionFactory factory = getFactory(operPool);

		String user = "root";
		String pwd = "db#@!123WC";
		String url = "jdbc:mysql://192.168.10.143:3306/datax_web?useUnicode=true&characterEncoding=UTF-8";
		String table = "ent_reg_info";
		String sql = HiveClient.getCreateHiveTableSql(url,user,pwd,table, RdbmsType.MYSQL);
		System.out.println(sql);
		boolean result = factory.getJdbcSession().createTable(sql);
		System.out.println("result = "+result);
		if (!result){
			factory.getJdbcSession().excuteHiveSql(sql);
		}
		
		/*
		sql = factory.getJdbcSession().getRdbmsCreateTableSql("klldata","klldata");
		System.out.println(sql);
		sql = factory.getJdbcSession().getRdbmsCreateTableSql("ent_reg_info","ent_reg_info");
		System.out.println(sql);
		*/
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
