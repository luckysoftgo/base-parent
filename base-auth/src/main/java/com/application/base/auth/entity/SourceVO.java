package com.application.base.auth.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @desc 容器存储的实体对象.
 * @author 孤狼.
 */
public class SourceVO implements Serializable {
	
	/**
	 * 存储对象的名称
	 */
	private String voName;

	/**
	 *存储 key-value 信息的集合.
	 */
	private Map<String,Object>  mapValues;
	
	/**
	 *存储 entity 對象信息的集合.
	 */
	private List<SourceEntity>  beanValues;
	
	/**
	 * 存储的剩余时间:存储在缓冲中的剩余时间
	 */
	private long voTime;
	
	public SourceVO() {
	}
	
	public SourceVO(String voName, Map<String, Object> mapValues, long voTime) {
		this.voName = voName;
		this.mapValues = mapValues;
		this.voTime = voTime;
	}
	
	public SourceVO(String voName, List<SourceEntity> beanValues, long voTime) {
		this.voName = voName;
		this.beanValues = beanValues;
		this.voTime = voTime;
	}
	
	public SourceVO(String voName, Map<String, Object> mapValues, List<SourceEntity> beanValues, long voTime) {
		this.voName = voName;
		this.mapValues = mapValues;
		this.beanValues = beanValues;
		this.voTime = voTime;
	}
	
	public String getVoName() {
		return voName;
	}
	
	public void setVoName(String voName) {
		this.voName = voName;
	}
	
	public Map<String, Object> getMapValues() {
		return mapValues;
	}
	
	public void setMapValues(Map<String, Object> mapValues) {
		this.mapValues = mapValues;
	}
	
	public List<SourceEntity> getBeanValues() {
		return beanValues;
	}
	
	public void setBeanValues(List<SourceEntity> beanValues) {
		this.beanValues = beanValues;
	}
	
	public long getVoTime() {
		return voTime;
	}
	
	public void setVoTime(long voTime) {
		this.voTime = voTime;
	}
}
