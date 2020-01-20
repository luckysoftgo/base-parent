package com.application.base.operapi.hive.test;

import com.alibaba.fastjson.JSON;
import com.application.base.operapi.api.hive.config.HiveJdbcConfig;
import com.application.base.operapi.api.hive.factory.HiveJdbcSessionFactory;
import com.application.base.operapi.api.hive.pool.HiveJdbcOperPool;
import com.application.base.operapi.core.ColumnInfo;
import com.application.base.operapi.core.hive.core.HiveClient;
import com.application.base.operapi.core.hive.rdbs.RdbmsType;

import java.util.List;

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
		String pwd = "123456";
		String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8";
		String table = "sum_data_dir";
		String sql = HiveClient.getCreateHiveTableSql(url,user,pwd,table, RdbmsType.MYSQL);
		sql="create table if not exists `auto_test_str` ( `ID` string  COMMENT '主键id'," +
				"`SORT_NO` string  COMMENT ' 排序number', " +
				"`CLASS_NAME` string  COMMENT '分类名称', " +
				"`DATA_TYPE` string  COMMENT '数据类型', " +
				"`DATA_VOLUME` string COMMENT '分类结果值', " +
				"`SECOND_LEVEL_NAME` string  COMMENT '二级分类', " +
				"`FIRST_LEVEL_NAME` string  COMMENT " +
				"'一级分类', `CREATE_TIME` string  COMMENT 'CREATE_TIME'" +
				") " +
				"row format delimited fields terminated by '\t' " +
				"null defined as '' stored as textfile \n";
		//boolean result = factory.getJdbcSession().createTable(sql);
		//System.out.println("result = "+result);
		List<ColumnInfo> datas = factory.getJdbcSession().getHiveColumns("klldata");
		System.out.println(JSON.toJSONString(datas));
	}
	
	/**
	 * 得到 config
	 */
	public static HiveJdbcConfig hiveConfig(){
		HiveJdbcConfig config = new HiveJdbcConfig();
		config.setHiveUrl("jdbc:hive2://192.168.10.185:10000/uni_dl");
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
