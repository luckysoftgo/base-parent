package com.application.base.all.util;

import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;

/**
 * @desc 用于存放在ES中的数据对象
 * 文档地址: https://www.elastic.co/guide/cn/elasticsearch/guide/current/bulk.html
 * @author 孤狼
 */
public class ElasticData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 索引 index (数据库名)
	 */
	private String dbName;
	/**
	 * 类型 type (表)
	 */
	private String tableName;
	/**
	 * id (document一行数据id)
	 */
	private String documentId;
	
	/**
	 * 对象.
	 */
	private Object jsonStr;
	
	/**
	 * 是否json串.
	 */
	private boolean json=false;
	
	
	public ElasticData() {
		super();
	}
	
	public ElasticData(String dbName, String tableName, String documentId, Object jsonStr) {
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
	public Object getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(Object jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	public boolean isJson() {
		return json;
	}
	
	public void setJson(boolean json) {
		this.json = json;
	}
}
