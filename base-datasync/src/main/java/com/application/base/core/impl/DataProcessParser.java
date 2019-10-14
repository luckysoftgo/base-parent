package com.application.base.core.impl;

import com.alibaba.fastjson.JSONObject;
import com.application.base.conts.DataConstant;
import com.application.base.core.DataParser;
import com.application.base.core.SyncContextPrivder;
import com.application.base.extend.impl.ExtendDataParser;
import com.application.base.util.sql.ExecuateDbPrivder;
import com.application.base.util.sql.PkProvider;
import com.application.base.util.xml.ColumnInfo;
import com.application.base.util.xml.DestDbInfo;
import com.application.base.util.xml.ExtendInfo;
import com.application.base.util.xml.ItemInfo;
import com.application.base.util.xml.TableInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: DataProcessParser
 * @DESC: 接口的实现.
 **/
@Service
public class DataProcessParser implements DataParser {
	
	private Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	@Autowired
	private JsonDataParser jsonDataParser;
	@Autowired
	private XmlDataParser xmlDataParser;
	@Autowired
	private CommonDataParser commonDataParser;
	@Autowired
	private SyncContextPrivder contextPrivder;
	@Autowired
	private ExecuateDbPrivder dbPrivder;
	@Autowired
	private ExtendDataParser extendDataParser;
	
	@Override
	public boolean executeExtend(String data, ExtendInfo extendInfo, String companyId) throws Exception {
		return extendDataParser.executeExtend(data,extendInfo,companyId);
	}
	
	@Override
	public boolean getCreateTablesSql() throws Exception {
		return getCreateTablesSql(null);
	}
	
	@Override
	public boolean getInsertSql(String data, String uniqueKey, String companyId) throws Exception {
		return getInsertSql(null,data,uniqueKey,companyId);
	}
	
	@Override
	public boolean getInsertSql(String data, String uniqueKey, String companyId, PkProvider provider) throws Exception {
		return getInsertSql(null,data,uniqueKey,companyId,provider);
	}
	
	@Override
	public boolean getCreateTableSql(String data, String uniqueKey) throws Exception {
		return getCreateTableSql(null,data,uniqueKey);
	}
	
	@Override
	public boolean getCreateTablesSql(String settingId) throws Exception {
		LinkedList<TableInfo> tableInfos = null;
		if (StringUtils.isNotBlank(settingId)){
			tableInfos = (LinkedList<TableInfo>) contextPrivder.get(settingId+DataConstant.SPLIT_TAG+DataConstant.TABLE_INFOS);
		}else{
			tableInfos = (LinkedList<TableInfo>) contextPrivder.get(DataConstant.TABLE_INFOS);
		}
		return createTablesSql(settingId,tableInfos);
	}
	
	@Override
	public boolean getInsertSql(String settingId, String data, String uniqueKey, String companyId) throws Exception {
		return getInsertSql(settingId,data,uniqueKey,companyId,null);
		
	}
	
	@Override
	public boolean getInsertSql(String settingId, String data, String uniqueKey, String companyId, PkProvider provider) throws Exception {
		TableInfo tableInfo = null;
		DestDbInfo destDbInfo = null;
		if (StringUtils.isNotBlank(settingId)){
			tableInfo = (TableInfo) contextPrivder.get(settingId+DataConstant.SPLIT_TAG+uniqueKey);
			destDbInfo = (DestDbInfo) contextPrivder.get(settingId+DataConstant.SPLIT_TAG+DataConstant.DESTDB_INFO);
		}else{
			tableInfo = (TableInfo) contextPrivder.get(uniqueKey);
			destDbInfo = (DestDbInfo) contextPrivder.get(DataConstant.DESTDB_INFO);
		}
		
		List<String> sqls = null;
		//主表是否存在(默认主表不存在,子表也不存在).
		boolean exists = tableExists(settingId,tableInfo,destDbInfo);
		if (!exists){
			createTableByJson(settingId,data,tableInfo, destDbInfo);
		}
		if (provider==null){
			sqls = insertsql(data,companyId,tableInfo);
		}else{
			sqls = insertSql(data,companyId, provider,tableInfo);
		}
		if (sqls==null || sqls.size()==0){
			logger.error("获得的数据插入语句为空");
			return false;
		}
		return dbPrivder.executeSql(destDbInfo,sqls);
	}
	
	@Override
	public boolean getCreateTableSql(String settingId, String data, String uniqueKey) throws Exception {
		boolean result= false;
		TableInfo tableInfo = null;
		DestDbInfo destDbInfo = null;
		if (StringUtils.isNotBlank(settingId)){
			tableInfo = (TableInfo) contextPrivder.get(settingId+DataConstant.SPLIT_TAG+uniqueKey);
			destDbInfo = (DestDbInfo) contextPrivder.get(settingId+DataConstant.SPLIT_TAG+DataConstant.DESTDB_INFO);
		}else{
			tableInfo = (TableInfo) contextPrivder.get(uniqueKey);
			destDbInfo = (DestDbInfo) contextPrivder.get(DataConstant.DESTDB_INFO);
		}
		LinkedList<String> operateSql= createTableJsonSql(data,tableInfo);
		result = dbPrivder.executeSql(destDbInfo,operateSql);
		if (result){
			if (StringUtils.isNotBlank(settingId)){
				contextPrivder.put(settingId+DataConstant.SPLIT_TAG+tableInfo.getTableName(), tableInfo.getTableName());
			}else{
				contextPrivder.put(tableInfo.getTableName(), tableInfo.getTableName());
			}
		}
		return result;
	}
	
	
	/**
	 * 调用生成sql的方式
	 * @param tableInfos
	 * @return
	 * @throws Exception
	 */
	private boolean createTablesSql(String settingId,LinkedList<TableInfo> tableInfos) throws Exception {
		if (tableInfos == null || tableInfos.size() == 0) {
			throw new Exception("读取xml中配置表的信息出错!");
		} else {
			DestDbInfo destDbInfo = null;
			if (StringUtils.isNotBlank(settingId)){
				destDbInfo = (DestDbInfo) contextPrivder.get(settingId+DataConstant.SPLIT_TAG+DataConstant.DESTDB_INFO);
			}else{
				destDbInfo = (DestDbInfo) contextPrivder.get(DataConstant.DESTDB_INFO);
			}
			//先查询有哪些表是创建的,哪些是没有创建的.
			LinkedList<TableInfo> needCreateTables = getNeedCreateTables(settingId,destDbInfo,tableInfos);
			if (needCreateTables==null || needCreateTables.size()==0){
				return true;
			}
			LinkedList<String> sqls = commonDataParser.getCreateTablesSql(needCreateTables);
			if (sqls != null && sqls.size() > 0) {
				
				boolean result = false;
				if (destDbInfo!=null){
					//删除表结构
					LinkedList<String> dels = getDeletes(tableInfos);
					//添加.
					dels.addAll(sqls);
					result =  dbPrivder.executeSql(destDbInfo,dels);
				}
				if (result){
					for (TableInfo tableInfo : tableInfos) {
						if (StringUtils.isNotBlank(settingId)){
							contextPrivder.put(settingId+DataConstant.SPLIT_TAG+tableInfo.getTableName(), tableInfo.getTableName());
						}else{
							contextPrivder.put(tableInfo.getTableName(), tableInfo.getTableName());
						}
					}
				}
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 判断哪些是需要建表的.
	 * @param destDbInfo
	 * @param tableInfos
	 * @return
	 */
	private LinkedList<TableInfo> getNeedCreateTables(String settingId,DestDbInfo destDbInfo, LinkedList<TableInfo> tableInfos) {
		LinkedList<TableInfo> needTables = new LinkedList<>();
		for (TableInfo info : tableInfos ) {
			boolean result = tableExists(settingId,info,destDbInfo);
			LinkedList<ColumnInfo>  columns = info.getColumns();
			if (!result && columns!=null && columns.size()>0){
				needTables.add(info);
			}
		}
		return needTables;
	}
	
	
	/**
	 * 生成的sql.
	 * @param data
	 * @param companyId
	 * @param tableInfo
	 * @return
	 * @throws Exception
	 */
	private List<String> insertsql(String data,String companyId,TableInfo tableInfo) throws Exception {
		if (tableInfo == null) {
			logger.info("传递的tableInfo信息为空!");
			throw new Exception("读取xml中配置信息出错!");
		}
		if (StringUtils.isEmpty(data) || StringUtils.isEmpty(companyId)){
			logger.info("传入的data:{},companyId:{}有一项为空!",data,companyId);
			throw new Exception("传入的参数有空值!");
		}
		List<String> sqls = null;
		if (isJson(data)) {
			sqls = jsonDataParser.getInsertSql(data, companyId, tableInfo);
		} else {
			sqls = xmlDataParser.getInsertSql(data, companyId, tableInfo);
		}
		return sqls;
	}
	
	/**
	 * 生成的sql.
	 * @param data
	 * @param companyId
	 * @param provider
	 * @param tableInfo
	 * @return
	 * @throws Exception
	 */
	private List<String>  insertSql(String data,String companyId, PkProvider provider,TableInfo tableInfo) throws Exception {
		if (tableInfo == null) {
			logger.info("传递的tableInfo信息为空!");
			throw new Exception("读取xml中配置信息出错!");
		}
		if (StringUtils.isEmpty(data) || StringUtils.isEmpty(companyId)|| provider==null){
			logger.info("传入的data:{},companyId:{}有一项为空!",data,companyId);
			throw new Exception("传入的参数有空值!");
		}
		List<String> sqls = null;
		if (isJson(data)) {
			sqls = jsonDataParser.getInsertSql(data, companyId, tableInfo, provider);
		} else {
			sqls = xmlDataParser.getInsertSql(data, companyId, tableInfo, provider);
		}
		return sqls;
	}
	
	/**
	 * 生成建表语句.
	 * @param data
	 * @param tableInfo
	 * @return
	 * @throws Exception
	 */
	private LinkedList<String> createTableJsonSql(String data, TableInfo tableInfo) throws Exception {
		if (tableInfo == null) {
			logger.info("传递的tableInfo信息为空!");
			throw new Exception("读取xml中配置信息出错!");
		}
		if (StringUtils.isEmpty(data)){
			logger.info("传入的data:{}为空!",data);
			throw new Exception("传入的参数有空值!");
		}
		LinkedList<String> sqls = null;
		if (isJson(data)) {
			sqls = jsonDataParser.getCreateTableSql(data, tableInfo);
		} else {
			sqls = xmlDataParser.getCreateTableSql(data, tableInfo);
		}
		return sqls;
	}
	
	/**
	 * 判断是否是json
	 * @param data
	 * @return
	 */
	private boolean isJson(String data){
		try {
			JSONObject jsonStr = JSONObject.parseObject(data);
			return  true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * 判断表是否存在
	 * @param tableInfo
	 * @return
	 */
	private boolean tableExists(String settingId,TableInfo tableInfo,DestDbInfo destDbInfo) {
		//判断表名是否存在
		if (StringUtils.isNotBlank(settingId)){
			String tableName = (String) contextPrivder.get(settingId+DataConstant.SPLIT_TAG+tableInfo.getTableName());
			if (StringUtils.isNotBlank(tableName)){
				return true;
			}
		}else{
			String tableName = (String) contextPrivder.get(tableInfo.getTableName());
			if (StringUtils.isNotBlank(tableName)){
				return true;
			}
		}
		if (destDbInfo.getDbtype().equalsIgnoreCase(DataConstant.MYSQL)){
			String sql = "select count(1) from information_schema.TABLES where table_schema ='"+destDbInfo.getDbname()+"' and table_name = '"+tableInfo.getTableName()+"'";
			Integer count=dbPrivder.selectSql(destDbInfo,sql);
			if (count>0){
				if (StringUtils.isNotBlank(settingId)){
					contextPrivder.put(settingId+DataConstant.SPLIT_TAG+tableInfo.getTableName(), tableInfo.getTableName());
				}else{
					contextPrivder.put(tableInfo.getTableName(), tableInfo.getTableName());
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断表是否存在
	 * @param itemInfo
	 * @return
	 */
	private boolean tableExists(String settingId,ItemInfo itemInfo,DestDbInfo destDbInfo) {
		//判断表名是否存在
		if (StringUtils.isNotBlank(settingId)){
			String tableName = (String) contextPrivder.get(settingId+DataConstant.SPLIT_TAG+itemInfo.getTableName());
			if (StringUtils.isNotBlank(tableName)){
				return true;
			}
		}else{
			String tableName = (String) contextPrivder.get(itemInfo.getTableName());
			if (StringUtils.isNotBlank(tableName)){
				return true;
			}
		}
		if (destDbInfo.getDbtype().equalsIgnoreCase(DataConstant.MYSQL)){
			String sql = "select count(1) from information_schema.TABLES where table_schema ='"+destDbInfo.getDbname()+"' and table_name = '"+itemInfo.getTableName()+"'";
			Integer count=dbPrivder.selectSql(destDbInfo,sql);
			if (count>0){
				if (StringUtils.isNotBlank(settingId)){
					contextPrivder.put(settingId+DataConstant.SPLIT_TAG+itemInfo.getTableName(), itemInfo.getTableName());
				}else{
					contextPrivder.put(itemInfo.getTableName(), itemInfo.getTableName());
				}
				return true;
			}
		}
		return false;
	}
	
	/**创建表结构.
	 *
	 * @param data
	 * @param tableInfo
	 * @param destDbInfo
	 * @throws Exception
	 */
	private void createTableByJson(String settingId, String data, TableInfo tableInfo, DestDbInfo destDbInfo) throws Exception {
		LinkedList<String> operatesSql = createTableJsonSql(data,tableInfo);
		logger.info("得到的删除语句是:{},\n\t",operatesSql);
		boolean execFlag = dbPrivder.executeSql(destDbInfo, operatesSql);
		logger.info("创建表后的结果是:{}", execFlag);
		if (execFlag) {
			List<String> tableNames = getTableNames(tableInfo);
			for (String tableName: tableNames) {
				if (StringUtils.isNotBlank(settingId)){
					contextPrivder.put(settingId+DataConstant.SPLIT_TAG+tableName, tableName);
				}else{
					contextPrivder.put(tableName, tableName);
				}
			}
		}
	}
	
	/**
	 * 获取标记的表的名字.
	 * @param tableInfo
	 * @return
	 */
	private LinkedList<String> getTableNames(TableInfo tableInfo) {
		LinkedList<String> tableNames = new LinkedList<>();
		tableNames.add(tableInfo.getTableName());
		if (tableInfo.isItems() && tableInfo.getItemInfos().size()>0){
			for (ItemInfo itemInfo : tableInfo.getItemInfos()) {
				tableNames.add(itemInfo.getTableName());
			}
		}
		return tableNames;
	}
	
	/**
	 * 删除表的操作.
	 * @param tableInfos
	 * @return
	 */
	private LinkedList<String> getDeletes(LinkedList<TableInfo> tableInfos) {
		LinkedList<String> delList = new LinkedList<>();
		if (tableInfos==null || tableInfos.size()==0){
			return null;
		}
		for (TableInfo tableInfo :tableInfos) {
			delList.add(deleteTableSql(tableInfo));
		}
		return delList;
	}
	
	/**
	 * 删除表结构的语句
	 * @param tableInfo
	 * @return
	 */
	private String deleteTableSql(TableInfo tableInfo) {
		return "drop table if exists "+tableInfo.getTableName()+";";
	}
	
}
