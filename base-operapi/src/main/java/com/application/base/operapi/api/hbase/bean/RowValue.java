package com.application.base.operapi.api.hbase.bean;

import java.util.List;

/**
 * @author : 孤狼
 * @NAME: RowValue
 * @DESC:
 **/
public class RowValue {

    private final String rowKey;
    
    private final List<ColumnFamily> columnFamilies;

    public RowValue() {
        this.rowKey = null;
        this.columnFamilies = null;
    }

    public RowValue(String rowKey, List<ColumnFamily> columnFamilies) {
        this.rowKey = rowKey;
        this.columnFamilies = columnFamilies;
    }

    public String getRowKey() {
        return rowKey;
    }

    public List<ColumnFamily> getColumnFamilies() {
        return columnFamilies;
    }
}
