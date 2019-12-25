package com.application.base.operapi.core;

/**
 * @author : 孤狼
 * @NAME: ColumnInfo
 * @DESC: column 类型信息.
 **/
public class ColumnInfo {
	/**
	 * 列名.
	 */
    private String columnName ;
	/**
	 * 列类型.
	 */
	private String columnDbType ;
	/**
	 * 列描述.
	 */
	private String columnComment ;
	/**
	 * 对应java类型.
	 */
	private String columnJavaType ;
	/**
	 * 对应hive类型.
	 */
	private String columnHiveType ;
	/**
	 * 定义值类型
	 */
	private String columnValueType ;
	
	public ColumnInfo() {
	}
	
	public ColumnInfo(String columnName, String columnDbType) {
		this.columnName = columnName;
		this.columnDbType = columnDbType;
	}
	
	public ColumnInfo(String columnName, String columnDbType, String columnComment, String columnJavaType,
	                  String columnHiveType) {
		this.columnName = columnName;
		this.columnDbType = columnDbType;
		this.columnComment = columnComment;
		this.columnJavaType = columnJavaType;
		this.columnHiveType = columnHiveType;
	}
	
	public ColumnInfo(String columnName, String columnDbType, String columnComment, String columnJavaType,
	                  String columnHiveType, String columnValueType) {
		this.columnName = columnName;
		this.columnDbType = columnDbType;
		this.columnComment = columnComment;
		this.columnJavaType = columnJavaType;
		this.columnHiveType = columnHiveType;
		this.columnValueType = columnValueType;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnDbType() {
		return columnDbType;
	}
	
	public void setColumnDbType(String columnDbType) {
		this.columnDbType = columnDbType;
	}
	
	public String getColumnComment() {
		return columnComment;
	}
	
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	
	public String getColumnJavaType() {
		return columnJavaType;
	}
	
	public void setColumnJavaType(String columnJavaType) {
		this.columnJavaType = columnJavaType;
	}
	
	public String getColumnHiveType() {
		return columnHiveType;
	}
	
	public void setColumnHiveType(String columnHiveType) {
		this.columnHiveType = columnHiveType;
	}
	
	public String getColumnValueType() {
		return columnValueType;
	}
	
	public void setColumnValueType(String columnValueType) {
		this.columnValueType = columnValueType;
	}
	
	@Override
    public boolean equals(Object obj) {
	    if (this == obj) {
		    return true;
	    }
	    if (obj == null || getClass() != obj.getClass()){
		    return false;
        }
        ColumnInfo columnInfo = (ColumnInfo)obj;
        return this.columnName.equals(columnInfo.columnName);
    }

    @Override
    public int hashCode() {
        return columnName != null ? columnName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ColumnInfo desc {" +
            "columnName='" + columnName + '\'' +
            ", columnDbType='" + columnDbType + '\'' +
	        ", columnComment='" + columnComment + '\'' +
	        ", columnJavaType='" + columnJavaType + '\'' +
		    ", columnHiveType='" + columnHiveType + '\'' +
	        ", columnValueType='" + columnValueType + '\'' +
            '}';
    }
}
