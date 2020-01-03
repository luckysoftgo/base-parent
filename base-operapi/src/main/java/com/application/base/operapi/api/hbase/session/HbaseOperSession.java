package com.application.base.operapi.api.hbase.session;

import com.application.base.operapi.api.hbase.api.HbaseSession;
import com.application.base.operapi.api.hbase.bean.HbaseBean;
import com.application.base.operapi.api.hbase.core.HbaseClient;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.TableDescriptor;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author : 孤狼
 * @NAME: HbaseOperSession
 * @DESC: 实际的实现.
 **/
public class HbaseOperSession implements HbaseSession {
	
	private HbaseClient hbaseClient;
	
	public HbaseClient getHbaseClient() {
		return hbaseClient;
	}
	
	public void setHbaseClient(HbaseClient hbaseClient) {
		this.hbaseClient = hbaseClient;
	}
	
	@Override
	public boolean createNamespace(String namespace) {
		return hbaseClient.createNamespace(namespace);
	}
	
	@Override
	public boolean deleteNamespace(String namespace) {
		return hbaseClient.deleteNamespace(namespace);
	}
	
	@Override
	public boolean tableExist(String tableName){
		return hbaseClient.tableExist(tableName);
	}
	
	@Override
	public List<String> listTables(Pattern pattern){
		return hbaseClient.listTables(pattern);
	}
	
	@Override
	public List<TableDescriptor> listTablesDesc(Pattern pattern){
		return hbaseClient.listTablesDesc(pattern);
	}
	
	@Override
	public List<String> listTableNames(Pattern pattern){
		return hbaseClient.listTables(pattern);
	}
	
	@Override
	public List<TableName> listTNTableNames(Pattern pattern){
		return hbaseClient.listTNTableNames(pattern);
	}
	
	@Override
	public TableDescriptor getTableDesc(String tableName){
		return hbaseClient.getTableDesc(tableName);
	}
	
	@Override
	public boolean isTableDisabled(String tableName){
		return hbaseClient.isTableDisabled(tableName);
	}
	
	@Override
	public boolean isTableAvailable(String tableName){
		return hbaseClient.isTableAvailable(tableName);
	}
	
	@Override
	public boolean enableTable(String tableName, boolean isAsync){
		return hbaseClient.enableTable(tableName,isAsync);
	}
	
	@Override
	public boolean disableTable(String tableName, boolean isAsync){
		return hbaseClient.disableTable(tableName,isAsync);
	}
	
	@Override
	public boolean addColumnFamily(String tableName, String columnFamily){
		return hbaseClient.addColumnFamily(tableName,columnFamily);
	}
	
	@Override
	public boolean updateColumnFamily(String tableName, String columnFamily){
		return hbaseClient.updateColumnFamily(tableName,columnFamily);
	}
	
	@Override
	public boolean createTable(String tableName, String columnFamily){
		return hbaseClient.createTable(tableName,columnFamily);
	}
	
	@Override
	public boolean createTable(String tableName, List<String> columnFamilies){
		return hbaseClient.createTable(tableName,columnFamilies);
	}
	
	@Override
	public boolean createTableBySplitKeys(String tableName, List<String> columnFamily, byte[][] splitKeys){
		return hbaseClient.createTableBySplitKeys(tableName,columnFamily,splitKeys);
	}
	
	@Override
	public boolean deleteTable(String tableName){
		return hbaseClient.deleteTable(tableName);
	}
	
	@Override
	public boolean deleteTable(String tableName, boolean isAsync){
		return hbaseClient.deleteTable(tableName,isAsync);
	}
	
	@Override
	public boolean deleteByRowKey(String tableName, String rowKey){
		return hbaseClient.deleteByRowKey(tableName,rowKey);
	}
	
	@Override
	public boolean deleteData(String tableName, String rowKey, String columnFamily, String columkey){
		return hbaseClient.deleteData(tableName,rowKey,columnFamily,columkey);
	}
	
	@Override
	public boolean deleteColumnFamily(String tableName, String columnFamily, boolean isAysnc){
		return hbaseClient.deleteColumnFamily(tableName,columnFamily,isAysnc);
	}
	
	@Override
	public boolean deleteMultiRows(String tableName, String[] rowKeys){
		return hbaseClient.deleteMultiRows(tableName,rowKeys);
	}
	
	@Override
	public boolean truncateTable(String tableName, boolean preserveSplits){
		return hbaseClient.truncateTable(tableName,preserveSplits);
	}
	
	@Override
	public List<HbaseBean> getAllResult(String tableName){
		return hbaseClient.getAllResult(tableName);
	}
	
	@Override
	public List<HbaseBean> getResultScanner(String tableName){
		return hbaseClient.getResultScanner(tableName);
	}
	
	@Override
	public List<HbaseBean> getResultScanner(String tableName, String startRowKey, String stopRowKey){
		return hbaseClient.getResultScanner(tableName,startRowKey,stopRowKey);
	}
	
	@Override
	public List<HbaseBean> getResultScannerPrefixFilter(String tableName, String prefix){
		return hbaseClient.getResultScannerPrefixFilter(tableName,prefix);
	}
	
	@Override
	public List<HbaseBean> getResultScannerColumnPrefixFilter(String tableName, String prefix){
		return hbaseClient.getResultScannerColumnPrefixFilter(tableName,prefix);
	}
	
	@Override
	public List<HbaseBean> getResultScannerRowFilter(String tableName, CompareOperator compareOperator, String keyWord){
		return hbaseClient.getResultScannerRowFilter(tableName,compareOperator,keyWord);
	}
	
	@Override
	public List<HbaseBean> getResultScannerQualifierFilter(String tableName, CompareOperator compareOperator, String keyWord){
		return hbaseClient.getResultScannerQualifierFilter(tableName,compareOperator,keyWord);
	}
	
	@Override
	public List<HbaseBean> selectTableDataByFilter(String tableName, String columnFamily, List<String> queryParam,
	                                               String regex, boolean bool) {
		return hbaseClient.selectTableDataByFilter(tableName,columnFamily,queryParam,regex,bool);
	}
	
	@Override
	public List<HbaseBean> selectColumnValueByFilter(String tableName, String columnFamily, List<String> queryParam,
	                                                 String regex, String column, boolean bool) {
		return hbaseClient.selectColumnValueByFilter(tableName,columnFamily,queryParam,regex,column,bool);
	}
	
	@Override
	public List<HbaseBean> selectTableDataByFilterPage(String tableName, String columnFamily, List<String> queryParam,
	                                                   String regex, boolean bool, int pageSize, String lastRow) {
		return hbaseClient.selectTableDataByFilterPage(tableName,columnFamily,queryParam,regex,bool,pageSize,lastRow);
	}
	
	@Override
	public List<HbaseBean> selectTableDataByPage(String tableName, int pageSize, String lastRow) {
		return hbaseClient.selectTableDataByPage(tableName,pageSize,lastRow);
	}
	
	@Override
	public boolean updateData(String tableName, String rowKey, String columnFamily, String columkey, String updateData){
		return hbaseClient.updateData(tableName,rowKey,columnFamily,columkey,updateData);
	}
	
	@Override
	public boolean insertOne(String tableName, String rowKey, String columnFamily, String column, String value){
		return hbaseClient.insertOne(tableName,rowKey,columnFamily,column,value);
	}
	
	@Override
	public boolean insertOne(String tableName, String rowKey, String columnFamily, Map<String, String> map){
		return hbaseClient.insertOne(tableName,rowKey,columnFamily,map);
	}
	
	@Override
	public boolean insertOrUpdate(String tableName, String rowKey, String columnFamily, String[] columns, String[] values){
		return hbaseClient.insertOrUpdate(tableName,rowKey,columnFamily,columns,values);
	}
	
	@Override
	public boolean insertBatchMore(String tableName, String rowKey, String columnFamily, String[] columns,
	                               String[] values) {
		return hbaseClient.insertBatchMore(tableName,rowKey,columnFamily,columns,values);
	}
	
	@Override
	public List<HbaseBean> getRowData(String tableName, String rowKey){
		return hbaseClient.getRowData(tableName,rowKey);
	}
	
	@Override
	public String getValData(String tableName, String rowKey, String columnFamily, String column){
		return hbaseClient.getValData(tableName,rowKey,columnFamily,column);
	}
	
	@Override
	public String getColumnValue(String tableName, String rowKey, String columnFamily, String columnName){
		return hbaseClient.getColumnValue(tableName,rowKey,columnFamily,columnName);
	}
	
	@Override
	public List<HbaseBean> getTableData(String tableName){
		return hbaseClient.getTableData(tableName);
	}
	
	@Override
	public List<HbaseBean> getDataByFamilyColumn(String tableName, String columnFamily, String column){
		return hbaseClient.getDataByFamilyColumn(tableName,columnFamily,column);
	}
	
	@Override
	public List<HbaseBean> getDataByFamilyColumn(String tableName, String rowKey, String columnFamily,
	                                                       String columnName){
		return hbaseClient.getDataByFamilyColumn(tableName,rowKey,columnFamily,columnName);
	}
	
	@Override
	public List<HbaseBean> getColumnValuesByVersion(String tableName, String rowKey, String columnFamily,
	                                                          String columnName, int versions){
		return hbaseClient.getColumnValuesByVersion(tableName,rowKey,columnFamily,columnName,versions);
	}
	
	@Override
	public byte[][] getSplitKeys(String[] keys){
		return hbaseClient.getSplitKeys(keys);
	}
	
	@Override
	public byte[][] getHexSplits(String startKey, String endKey, int numRegions){
		return hbaseClient.getHexSplits(startKey,endKey,numRegions);
	}
}
