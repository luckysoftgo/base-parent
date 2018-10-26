package com.application.base.utils.common;

import java.io.Serializable;

/**
 * @desc 对比属性
 * @author 孤狼
 */
public class BeanCompared implements Serializable{
	
	/**
	 * 字段名字
	 */
	private String name;
	/**
	 * 字段类型
	 */
	private String type;
	/**
	 * 原始值
	 */
	private String oldVal;
	/**
	 * 新值
	 */
	private String newVal;
	
	public BeanCompared() {
	}
	
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
	
	public String getOldVal() {
		return oldVal;
	}
	
	public void setOldVal(String oldVal) {
		this.oldVal = oldVal;
	}
	
	public String getNewVal() {
		return newVal;
	}
	
	public void setNewVal(String newVal) {
		this.newVal = newVal;
	}
}
