package com.application.base.common.result;

/**
 * @desc 系统结果信息实体，该实体封装系统运行消息，为系统信息xml配置文件对应对象
 * @ClassName:  ResultInfo   
 * @author 孤狼
 */
public class ResultInfo {
	
	/**
	 * 结果信息键值，通过键值从结果信息容器中提取结果信息
	 */
	private String key;
	/**
	 * 结果信息码
	 */
	private String code;
	/**
	 * 结果信息内容
	 */
	private String msg;
	
	public ResultInfo() {
	}
	
	public ResultInfo(String key, String code, String msg) {
		this.key = key;
		this.code = code;
		this.msg = msg;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
}
