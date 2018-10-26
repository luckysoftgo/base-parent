package com.application.base.generate.javabase.bean;

/**
 * @desc 表对应的列
 * @author 孤狼
 */
public class GenerateColumn {

	/** 变量名 */
	private String name;

	/** 对应的静态变量名 */
	private String staticFinalName;

	/** 首字母大写 */
	private String firstUpperName;

	/** 数据库字段原始名 */
	private String dbName;

	/** 类型 */
	private String type;

	/** 数据库字段原始类型 */
	private String dbType;

	/** sqlmap中对应的类型 */
	private String sqlMapColumnType;

	/** 描述 */
	private String remarks;

	/** 默认值 */
	private String defaultValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getSqlMapColumnType() {
		return sqlMapColumnType;
	}

	public void setSqlMapColumnType(String sqlMapColumnType) {
		this.sqlMapColumnType = sqlMapColumnType;
	}

	public String getFirstUpperName() {
		return firstUpperName;
	}

	public void setFirstUpperName(String firstUpperName) {
		this.firstUpperName = firstUpperName;
	}

	public String getStaticFinalName() {
		return staticFinalName;
	}

	public void setStaticFinalName(String staticFinalName) {
		this.staticFinalName = staticFinalName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
