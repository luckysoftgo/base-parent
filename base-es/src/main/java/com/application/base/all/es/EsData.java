package com.application.base.all.es;

import java.io.Serializable;

/**
 * @desc 用于存放在ES中的数据对象
 * @author 孤狼
 */

public class EsData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 索引(数据库名)
	 */
	private String dbName;
	/**
	 * 类型type(表)
	 */
	private String tableName; 
	/**
	 * id(document一行数据id)
	 */
	private String documentId;
	/**
	 * 对象json串
	 */
	private String jsonStr;
	
	
	public EsData() {
		super();
	}

	public EsData(String dbName, String tableName, String documentId, String jsonStr) {
		super();
		this.dbName = dbName;
		this.tableName = tableName;
		this.documentId = documentId;
		this.jsonStr = jsonStr;
	}
	
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
}
