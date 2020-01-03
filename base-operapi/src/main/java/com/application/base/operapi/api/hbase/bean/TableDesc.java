package com.application.base.operapi.api.hbase.bean;

import java.util.List;

/**
 * @author : 孤狼
 * @NAME: TableDesc
 * @DESC:
 **/
public class TableDesc {
	
	/**
	 * 表名称.
	 */
	private final String tableName;
	/**
	 * 表中所有的列簇名称
	 */
	private final List<String> columnFamilies;
	
	public TableDesc() {
		this.tableName = null;
		this.columnFamilies = null;
	}
	
	public TableDesc(String tableName) {
		this.tableName = tableName;
		this.columnFamilies = null;
	}
	
	public TableDesc(String tableName, List<String> columnFamilies) {
		this.tableName = tableName;
		this.columnFamilies = columnFamilies;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public List<String> getColumnFamilies() {
		return columnFamilies;
	}
	
	@Override
	public String toString() {
		return "TableDescription{" +
				"tableName='" + tableName + '\'' +
				", columnFamilies=" + columnFamilies +
				'}';
	}
}
