package com.application.base.operapi.hive.test;

import com.alibaba.fastjson.JSON;
import com.application.base.operapi.core.ColumnInfo;
import com.application.base.operapi.core.hive.core.HiveClient;
import com.application.base.operapi.core.hive.rdbs.RdbmsType;

import java.sql.Connection;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: HiveTest
 * @DESC: HiveTest类设计
 **/
public class HiveTest {
	
	public static void main(String[] args) {
		Connection connection = HiveClient.getConnections("org.apache.hive.jdbc.HiveDriver","jdbc:hive2://192.168.10.185:10000/uni_dl","","");
		List<ColumnInfo>  columnInfos = HiveClient.getTableBeanInfo(connection,"klldata", RdbmsType.MYSQL);
		System.out.println(JSON.toJSONString(columnInfos));
	}
}
