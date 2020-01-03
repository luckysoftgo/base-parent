package com.application.base.operapi.api.hbase.bean;

/**
 * @author : 孤狼
 * @NAME: HbaseBean
 * @DESC: 用來存儲hbase的信息.
 **/
public class HbaseBean {
	/**
	 * 所在的table
	 */
	private String tableName;
	/**
	 * 所属的rowkey
	 */
	private String rowKey;
	/**
	 * 所在的列
	 */
	private String columnFamily;
	/**
	 * 所对应的key
	 */
	private String columnKey;
	/**
	 * 所对应的value
	 */
	private String columnValue;
	
	/**
	 * 放入时间.
	 */
	private String dateTime;
	
	/**
	 * 放入时间.
	 */
	private Long timestamp;
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getRowKey() {
		return rowKey;
	}
	
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	
	public String getColumnFamily() {
		return columnFamily;
	}
	
	public void setColumnFamily(String columnFamily) {
		this.columnFamily = columnFamily;
	}
	
	public String getColumnKey() {
		return columnKey;
	}
	
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}
	
	public String getColumnValue() {
		return columnValue;
	}
	
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	
	public String getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}
