package com.application.base.operapi.tool.hive.core;

import com.application.base.operapi.tool.hive.common.DbHelper;
import com.application.base.operapi.tool.hive.common.FileWrite;
import com.application.base.operapi.tool.hive.common.config.JdbcConfig;
import com.application.base.operapi.tool.hive.common.config.OperateConfig;
import com.application.base.operapi.tool.hive.model.ColumnInfo;
import com.application.base.operapi.tool.hive.rdbs.DataSourceType;

import java.sql.Connection;
import java.sql.SQLException;
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
		String dataSourceType = jdbcConfig.getRdbsType();
		String sourceTableName = jdbcConfig.getTableName();
		
		String reqUrl = DataSourceType.getRequestUrl(jdbcConfig.getHost(),jdbcConfig.getPort(),jdbcConfig.getDataBase(),dataSourceType);
		//1、获取数据库连接
		Connection conn = null;
		try {
			conn = DbHelper.getConn(jdbcConfig.getDriver(),reqUrl,jdbcConfig.getUserName(),jdbcConfig.getPassWord());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//2、获取表的信息
		List<ColumnInfo> columnMapList = DbHelper.getTableColumnInfo(sourceTableName, conn);
		//3、转换为hive表的对应类型
		DbHelper.tableColumnInfoToHiveColnmnInfo(columnMapList,dataSourceType);
		columnMapList.forEach(columnInfo -> System.out.println("数据库的列信息是:"+columnInfo.toString()));
		//4、获取表的所有数据
		List<List<Object>> resultList = DbHelper.getTableDatas(sourceTableName, conn);
		int dataCount = resultList.size();
		//5、写入文件 并导入到hive
		if (dataCount > 0) {
			String tmpFile = sourceTableName;
			try {
				fileWrite.writeMapList2File(resultList,  jdbcConfig.getLocalTmpPath() + tmpFile,split);
				HiveOperateUtil hiveOperateUtil = HiveOperateUtil.getInstance(operateConfig);
				hiveOperateUtil.executeHiveOperate(sourceTableName,tmpFile,columnMapList,split,jdbcConfig.getLocalTmpPath());
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
