package com.application.base.utils.message.constant;

/**
 * @desc 短信类型.
 * @author 孤狼
 */
public enum MsgType {
	
	/**
	 * 示远短信.
	 */
	SY("SY"),
	
	/**
	 * 未来无线短信
	 */
	WLWX("WLWX");
	
	/**
	 * 类型
	 */
	private String type;

	MsgType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
