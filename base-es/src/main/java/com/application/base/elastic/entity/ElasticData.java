package com.application.base.elastic.entity;

import java.io.Serializable;
import java.util.Map;

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
	private String data;
	/**
	 * 表中的数据.
	 * 表中的数据.
	 * 表中的数据.
	 */
	private Map<String,Object> mapData;
	/**
	 * 是否是map集合
	 */
	private boolean mapFlag =false;
	
	public ElasticData() {
	}
	
	public ElasticData(String index, String type, String id, String data) {
		this.index = index;
		this.type = type;
		this.id = id;
		this.data = data;
	}
	
	public ElasticData(String index, String type, String id, String data, boolean mapFlag) {
		this.index = index;
		this.type = type;
		this.id = id;
		this.data = data;
		this.mapFlag = mapFlag;
	}
	
	public ElasticData(String index, String type, String id, Map<String, Object> mapData, boolean mapFlag) {
		this.index = index;
		this.type = type;
		this.id = id;
		this.mapData = mapData;
		this.mapFlag = mapFlag;
	}
	
	public ElasticData(String index, String type, String id, String data, Map<String, Object> mapData, boolean mapFlag) {
		this.index = index;
		this.type = type;
		this.id = id;
		this.data = data;
		this.mapData = mapData;
		this.mapFlag = mapFlag;
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
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public Map<String, Object> getMapData() {
		return mapData;
	}
	
	public void setMapData(Map<String, Object> mapData) {
		this.mapData = mapData;
	}
	
	public boolean isMapFlag() {
		return mapFlag;
	}
	
	public void setMapFlag(boolean mapFlag) {
		this.mapFlag = mapFlag;
	}
}
