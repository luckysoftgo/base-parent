package com.application.base.operapi.api.hbase.session;

import com.application.base.operapi.api.hbase.api.HbaseSession;
import com.application.base.operapi.api.hbase.core.HbaseClient;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Result;
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
	public boolean createTable(String tableName, String familyName){
		return hbaseClient.createTable(tableName,familyName);
	}
	
	@Override
	public boolean createTable(String tableName, List<String> fields){
		return hbaseClient.createTable(tableName,fields);
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
	public boolean deleteData(String tableName, String rowKey, String family, String columkey){
		return hbaseClient.deleteData(tableName,rowKey,family,columkey);
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
	public List<Result> getAllResult(String tableName){
		return hbaseClient.getAllResult(tableName);
	}
	
	@Override
	public Map<String, Map<String, String>> getResultScanner(String tableName){
		return hbaseClient.getResultScanner(tableName);
	}
	
	@Override
	public Map<String, Map<String, String>> getResultScanner(String tableName, String startRowKey, String stopRowKey){
		return hbaseClient.getResultScanner(tableName,startRowKey,stopRowKey);
	}
	
	@Override
	public Map<String, Map<String, String>> getResultScannerPrefixFilter(String tableName, String prefix){
		return hbaseClient.getResultScannerPrefixFilter(tableName,prefix);
	}
	
	@Override
	public Map<String, Map<String, String>> getResultScannerColumnPrefixFilter(String tableName, String prefix){
		return hbaseClient.getResultScannerColumnPrefixFilter(tableName,prefix);
	}
	
	@Override
	public Map<String, Map<String, String>> getResultScannerRowFilter(String tableName, String keyWord){
		return hbaseClient.getResultScannerRowFilter(tableName,keyWord);
	}
	
	@Override
	public Map<String, Map<String, String>> getResultScannerQualifierFilter(String tableName, String keyWord){
		return hbaseClient.getResultScannerQualifierFilter(tableName,keyWord);
	}
	
	@Override
	public boolean updateData(String tableName, String rowKey, String family, String columkey, String updateData){
		return hbaseClient.updateData(tableName,rowKey,family,columkey,updateData);
	}
	
	@Override
	public boolean insertOne(String tableName, String rowKey, String columnFamily, String column, String value){
		return hbaseClient.insertOne(tableName,rowKey,columnFamily,column,value);
	}
	
	@Override
	public boolean insertOne(String tableName, String rowKey, String family, Map<String, String> map){
		return hbaseClient.insertOne(tableName,rowKey,family,map);
	}
	
	@Override
	public boolean insertMore(String tableName, String rowKey, String familyName, String[] columns, String[] values){
		return hbaseClient.insertMore(tableName,rowKey,familyName,columns,values);
	}
	
	@Override
	public boolean insertBatchMore(String tableName, String rowKey, String familyName, String[] columns,
	                               String[] values) {
		return hbaseClient.insertBatchMore(tableName,rowKey,familyName,columns,values);
	}
	
	@Override
	public List<Map<String, Object>> getRowData(String tableName, String rowKey){
		return hbaseClient.getRowData(tableName,rowKey);
	}
	
	@Override
	public String getData(String tableName, String rowKey, String columnFamily, String column){
		return hbaseClient.getData(tableName,rowKey,columnFamily,column);
	}
	
	@Override
	public String getColumnValue(String tableName, String rowKey, String columnFamily, String columnName){
		return hbaseClient.getColumnValue(tableName,rowKey,columnFamily,columnName);
	}
	
	@Override
	public List<Map<String, Object>> getTableData(String tableName){
		return hbaseClient.getTableData(tableName);
	}
	
	@Override
	public List<Map<String, Object>> getDataByFamilyColumn(String tableName, String family, String column){
		return hbaseClient.getDataByFamilyColumn(tableName,family,column);
	}
	
	@Override
	public List<Map<String, Object>> getDataByFamilyColumn(String tableName, String rowKey, String familyName,
	                                                       String columnName){
		return hbaseClient.getDataByFamilyColumn(tableName,rowKey,familyName,columnName);
	}
	
	@Override
	public List<Map<String, Object>> getColumnValuesByVersion(String tableName, String rowKey, String familyName,
	                                                          String columnName, int versions){
		return hbaseClient.getColumnValuesByVersion(tableName,rowKey,familyName,columnName,versions);
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
