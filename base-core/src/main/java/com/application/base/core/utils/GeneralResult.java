package com.application.base.core.utils;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * @desc 通用VO
 * @author 孤狼
 */
public class GeneralResult<T> implements Serializable{

	@Expose
	private T body;
	@Expose
	private Integer code;
	@Expose
	private String msg;

	/**
	 * @param code	返回码数组
	 * @param msg	错误信息数组
	 * @param body	返回正文内容
	 */
	public GeneralResult(Integer code, String msg, T body) {
		this.setCode(code);
		this.setMsg(msg);
		this.setBody(body);
	}
	
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
