package com.application.base.auth.util;

/**
 * @desc 资源的类型
 * @author 孤狼
 */
public enum SourceType {
	
	LOGIN_INFO("LOGIN","主要记录登录的信息"),
	USER_INFO("USER","主要记录用户的信息"),
	AUTH_INFO("AUTH","主要记录认证的信息"),
	TEMP_INFO("TEMP","主要记录临时信息"),
	
	;
	
	/**
	 * 值
	 */
	private String value;
	/**
	 * 描述
	 */
	private String desc;
	
	SourceType(String value,String desc){
		this.value = value;
		this.desc = desc;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
