package com.application.base.message.pushmsg.xinge.uitls;

/**
 * @desc 设备的类型
 * @author 孤狼
 */
public enum DeviceType {
	
	/**
	 * 安卓
	 */
	ANDROID("android"),
	
	/**
	 * ios
	 */
	IOS("ios");

	private String name;
	
	DeviceType(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
