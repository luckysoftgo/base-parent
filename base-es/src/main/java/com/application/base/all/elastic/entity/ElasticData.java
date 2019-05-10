package com.application.base.all.elastic.entity;

import java.io.Serializable;

/**
 * @desc 用于存放在ES中的数据对象
 * 文档地址: https://www.elastic.co/guide/cn/elasticsearch/guide/current/bulk.html
 * @author 孤狼
 */
public class ElasticData<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 索引 index (数据库名DbName)
	 */
	private String index;
	/**
	 * 类型 type (表tableName)
	 */
	private String type;
	/**
	 * id (表中的行id)
	 */
	private String id;
	/**
	 * 表中的数据.
	 */
	private T data;
	
	public ElasticData() {
	}
	
	public ElasticData(String index, String type, String id, T data) {
		this.index = index;
		this.type = type;
		this.id = id;
		this.data = data;
	}
	
	public String getIndex() {
		return index;
	}
	
	public void setIndex(String index) {
		this.index = index;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
}
