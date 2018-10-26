package com.application.base.core.result;

import com.application.base.utils.json.JsonConvertUtils;

import java.io.Serializable;

/**
 * @desc 各个独立系统返回的结果数据对象，将返回结构封装为Result对象，转化为JSON返回
 * @ClassName:  ResultData   
 * @author 孤狼
 */
public class ResultDataVO<T> implements Serializable {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 结果返回码
	 */
	private String code;
	
	/**
	 * 结果返回信息
	 */
	private String msg;
	/**
	 * 结果返回内容
	 */
	private T data;
	
	public ResultDataVO() {
	}
	
	public ResultDataVO(String code,String msg) {
		setCode(code);
		setMsg(msg);
	}
	public ResultDataVO(ResultInfo info) {
		setCode(info.getCode());
		setMsg(info.getMsg());
	}

	public ResultDataVO(ResultInfo info, T result) {
		this(info);
		setData(result);
	}

	public void setResultInfo(ResultInfo info) {
		setCode(info.getCode());
		setMsg(info.getMsg());
	}
	
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "[ code = "+getCode()+",msg="+getMsg()+",data="+ JsonConvertUtils.toJson(getData())+"]";
	}
	
}
