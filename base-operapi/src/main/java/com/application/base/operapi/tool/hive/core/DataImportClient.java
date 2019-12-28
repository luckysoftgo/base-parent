package com.application.base.operapi.tool.hive.core;

import com.application.base.operapi.core.ColumnInfo;
import com.application.base.operapi.core.hive.core.HiveClient;
import com.application.base.operapi.core.hive.rdbs.RdbmsType;
import com.application.base.operapi.tool.hive.common.FileWrite;
import com.application.base.operapi.tool.hive.common.config.HadoopConfig;
import com.application.base.operapi.tool.hive.common.config.JdbcConfig;
import com.application.base.operapi.tool.hive.common.config.OperateConfig;

import java.io.File;
import java.sql.Connection;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: DataImportClient
 * @DESC: 数据导入客户端.
 **/
public class DataImportClient extends Thread {
	
	private static final String split = "\t";
	
	FileWrite fileWrite = new FileWrite();
	
	/**
	 * 操作的配置
	 */
	private OperateConfig operateConfig;
	
	/**
	 * 构造方法
	 * @param operateConfig
	 */
	public DataImportClient(OperateConfig operateConfig) {
		this.operateConfig = operateConfig;
	}
	
	/**
	 * 导入数据到 hive 中去
	 * @return
	 */
	public boolean importData() {
		JdbcConfig jdbcConfig = operateConfig.getJdbcConfig();
		HadoopConfig hadoopConfig = operateConfig.getHadoopConfig();
		RdbmsType dataSourceType = jdbcConfig.getRdbsType();
		String sourceTableName = jdbcConfig.getTableName();
		
		String reqUrl = HiveClient.getRequestUrl(jdbcConfig.getHost(),jdbcConfig.getPort(),jdbcConfig.getDataBase(),dataSourceType);
		//1、获取数据库连接
		Connection conn = HiveClient.getConnections(jdbcConfig.getDriver(),reqUrl,jdbcConfig.getUserName(),jdbcConfig.getPassWord());
		//2、获取表的信息
		List<ColumnInfo> columnMapList = HiveClient.getTableColumnInfo(sourceTableName, conn);
		//3、转换为hive表的对应类型
		HiveClient.convertTableColumnInfos(columnMapList,dataSourceType);
		
		columnMapList.forEach(columnInfo -> System.out.println("数据库的列信息是:"+columnInfo.toString()));
		//4、获取表的所有数据
		List<List<Object>> resultList = HiveClient.getTableDatas(sourceTableName, conn);
		int dataCount = resultList.size();
		//5、写入文件 并导入到hive
		if (dataCount > 0) {
			String tmpFile = sourceTableName;
			String localFilePath = hadoopConfig.getLocalFilePath();
			try {
				fileWrite.writeMapList2File(resultList,  localFilePath + File.separator + tmpFile,split);
				HiveOperateUtil hiveOperateUtil = HiveOperateUtil.getInstance(operateConfig);
				hiveOperateUtil.executeHiveOperate(sourceTableName,tmpFile,columnMapList,split,localFilePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public void run() {
		importData();
	}
}
