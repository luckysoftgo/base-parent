package com.application.base.utils.common;

import java.io.Serializable;

/**
 * @desc 接收参数的基类。
 * @author 孤狼
 */
public class BaseDto implements Serializable,Cloneable {
	
	/**
	 * 平台 token
	 */
	private String token;
	
	/**
	 *请求来源: 1.APP ; 2. 平台;
	 */
	private String type;
	
	/**
	 * android or ios
	 */
	private String deviceType;
	
	/**
	 * android传设备id; ios 传 idfa
	 */
	private String deviceToken;
	
	/***************************************** 请求访问 ***************************************/
	
	/**
	 * 当前多少页
	 */
	private Integer pageNo;
	
	/**
	 * 没有多少条
	 */
	private Integer pageSize;
	
	/**
	 * 开始创建时间
	 */
	private String startCreateTime;
	
	/**
	 *结束创建时间
	 */
	private String endCreateTime;
	
	/**
	 * 开始更新时间
	 */
	private String startUpdateTime;
	
	/**
	 * 结束更新时间
	 */
	private String endUpdateTime;
	
	
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDeviceType() {
		return deviceType;
	}
	
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	public String getDeviceToken() {
		return deviceToken;
	}
	
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getStartCreateTime() {
		return startCreateTime;
	}
	
	public void setStartCreateTime(String startCreateTime) {
		this.startCreateTime = startCreateTime;
	}
	
	public String getEndCreateTime() {
		return endCreateTime;
	}
	
	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	
	public String getStartUpdateTime() {
		return startUpdateTime;
	}
	
	public void setStartUpdateTime(String startUpdateTime) {
		this.startUpdateTime = startUpdateTime;
	}
	
	public String getEndUpdateTime() {
		return endUpdateTime;
	}
	
	public void setEndUpdateTime(String endUpdateTime) {
		this.endUpdateTime = endUpdateTime;
	}
}
