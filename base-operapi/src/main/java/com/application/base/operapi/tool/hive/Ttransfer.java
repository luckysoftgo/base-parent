package com.application.base.operapi.tool.hive;

import com.application.base.operapi.tool.hive.common.config.HiveConfig;
import com.application.base.operapi.tool.hive.common.config.JdbcConfig;
import com.application.base.operapi.tool.hive.common.config.OperateConfig;
import com.application.base.operapi.tool.hive.common.config.SshConfig;
import com.application.base.operapi.tool.hive.core.DataImportClient;

/**
 * @author : 孤狼
 * @NAME: Ttransfer
 *
 * `    必须要有hadoop的安装路径.
 *      核心思想是:将mysql中的数据抽出来,放入到一个文件中去,然后创建hive的表,把生成的数据文件上传到hdfs上去,然后挂载到hive上
 *
 * @DESC: 提交转换.
 **/
public class Ttransfer {
	
	/**
	 * 转换提交.
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("","");
		HiveConfig hiveConfig = new HiveConfig();
		hiveConfig.setDriver("org.apache.hive.jdbc.HiveDriver");
		hiveConfig.setUrl("jdbc:hive2://192.168.10.185:10000/test");
		hiveConfig.setRemoteFilePath("/user/hdfs/test/");
		hiveConfig.setUsername("");
		hiveConfig.setPassword("");
		hiveConfig.setHdfsAddresss("hdfs://192.168.10.185:9000");
		
		JdbcConfig jdbcConfig = new JdbcConfig();
		jdbcConfig.setDataBase("test");
		jdbcConfig.setRdbsType("mysql");
		jdbcConfig.setTableName("sum_data_dir");
		jdbcConfig.setUserName("root");
		jdbcConfig.setPassWord("123456");
		jdbcConfig.setDriver("com.mysql.jdbc.Driver");
		jdbcConfig.setHost("127.0.0.1");
		jdbcConfig.setPort(3306);
		jdbcConfig.setLocalTmpPath("E://data/");
		
		SshConfig sshConfig = new SshConfig();
		sshConfig.setHost("192.168.10.185");
		sshConfig.setPort(22);
		sshConfig.setUsername("root");
		sshConfig.setPassword("admin123com");
		
		OperateConfig operateConfig = new OperateConfig();
		operateConfig.setHiveConfig(hiveConfig);
		operateConfig.setJdbcConfig(jdbcConfig);
		operateConfig.setSshConfig(sshConfig);
		
		DataImportClient importClient = new DataImportClient(operateConfig);
		importClient.run();
		try {
			importClient.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
