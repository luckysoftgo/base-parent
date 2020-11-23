package com.application.base.operapi.api.phoenix.cont;

/**
 * @author : 孤狼
 * @NAME: PhoenixData
 * @DESC: hbase 结果集合
 **/
public class PhoenixData {
	/**
	 * 列的类型
	 */
	private String columnType;
	/**
	 * 列的名称
	 */
	private String columnName;
	/**
	 * 列的值
	 */
	private String columnValue;
	
	public String getColumnType() {
		return columnType;
	}
	
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnValue() {
		return columnValue;
	}
	
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
}
