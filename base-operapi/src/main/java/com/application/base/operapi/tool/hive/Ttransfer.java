package com.application.base.operapi.tool.hive;

import com.application.base.operapi.tool.hive.common.config.HadoopConfig;
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
		//设置hadoop的环境.如果没有设置,会报错如下:
		//java.io.IOException: Could not locate executable null\bin\winutils.exe in the Hadoop binaries.
		//解决方案 1: https://www.cnblogs.com/hyl8218/p/5492450.html
		//解决方案 2: https://www.cnblogs.com/mdlcw/p/11106218.html
		
		HadoopConfig hadoopConfig = new HadoopConfig();
		hadoopConfig.setHadoopInstallDir("D:\\installer\\hadoop-2.7.7");
		hadoopConfig.setHadoopUserName("hdfs");
		hadoopConfig.setLocalFilePath("E:\\data\\");
		hadoopConfig.setHdfsFilePath("/tmp/");
		hadoopConfig.setHdfsRequestUrl("hdfs://manager:8020");
		hadoopConfig.setDeleteFile(true);
		
		System.setProperty(HadoopConfig.HADOO_PHOME_DIR, hadoopConfig.getHadoopInstallDir());
		//設置hadoop的用户名称: hdfs
		System.setProperty(HadoopConfig.HADOOP_USER_NAME,hadoopConfig.getHadoopUserName()) ;
		
		HiveConfig hiveConfig = new HiveConfig();
		hiveConfig.setDriver("org.apache.hive.jdbc.HiveDriver");
		hiveConfig.setUrl("jdbc:hive2://192.168.10.185:10000/test");
		hiveConfig.setUsername("");
		hiveConfig.setPassword("");
		
		JdbcConfig jdbcConfig = new JdbcConfig();
		jdbcConfig.setDataBase("test");
		jdbcConfig.setRdbsType("mysql");
		jdbcConfig.setTableName("sum_data_dir");
		jdbcConfig.setUserName("root");
		jdbcConfig.setPassWord("123456");
		jdbcConfig.setDriver("com.mysql.jdbc.Driver");
		jdbcConfig.setHost("127.0.0.1");
		jdbcConfig.setPort(3306);
		
		SshConfig sshConfig = new SshConfig();
		sshConfig.setHost("192.168.10.185");
		sshConfig.setPort(22);
		sshConfig.setUsername("root");
		sshConfig.setPassword("admin123com");
		
		/**
		 * 配置对象.
		 */
		OperateConfig operateConfig = new OperateConfig();
		operateConfig.setHadoopConfig(hadoopConfig);
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
