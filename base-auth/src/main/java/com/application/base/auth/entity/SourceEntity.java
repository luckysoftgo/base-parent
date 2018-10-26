package com.application.base.auth.entity;

import com.application.base.utils.common.BaseEntity;

/**
 * @desc 容器存储的实体对象.
 * @author 孤狼.
 */
public class SourceEntity extends BaseEntity {
	
	/**
	 * 存储对象的名称
	 */
	private String entityName;
	
	/**
	 * 存储的剩余时间:存储在缓冲中的剩余时间
	 */
	private long entityTime;
	
	public SourceEntity() {
	}
	
	public SourceEntity(String entityName, long entityTime) {
		this.entityName = entityName;
		this.entityTime = entityTime;
	}
	
	public String getEntityName() {
		return entityName;
	}
	
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public long getEntityTime() {
		return entityTime;
	}
	
	public void setEntityTime(long entityTime) {
		this.entityTime = entityTime;
	}
}
