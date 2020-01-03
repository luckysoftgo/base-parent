package com.application.base.operapi.tool.hbase.base;

/**
 * @author : 孤狼
 * @NAME: BasicHbase
 * @DESC: hbase document 的介绍.
 **/
public class BasicHbase {
	/**
	 * 表名称.
	 */
    private String tableName;
	/**
	 * 行主键.
	 */
	private String rowKey;
	/**
	 *列簇名称
	 */
	private String columnFamily;
	/**
	 * 列的key
	 */
    private String columnKey;
	/**
	 * 列的value
	 */
    private String columnValue;
	/**
	 * 时间戳.
	 */
	private String timesamp;

    public BasicHbase clear() {
        this.tableName = null;
        this.columnFamily = null;
        this.rowKey = null;
        this.columnKey = null;
        this.timesamp = null;
        this.columnValue = null;
        return this;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public BasicHbase setColumnValue(String columnValue) {
        this.columnValue = columnValue;
        return this;
    }

    public String getRowKey() {
        return rowKey;
    }

    public BasicHbase setRowKey(String rowKey) {
        this.rowKey = rowKey;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public BasicHbase setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getColumnFamily() {
        return columnFamily;
    }

    public BasicHbase setColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
        return this;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public BasicHbase setColumnKey(String columnKey) {
        this.columnKey = columnKey;
        return this;
    }

    public String getTimesamp() {
        return timesamp;
    }

    public BasicHbase setTimesamp(String timesamp) {
        this.timesamp = timesamp;
        return this;
    }

}
