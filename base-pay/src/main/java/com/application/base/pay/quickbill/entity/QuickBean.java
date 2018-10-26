package com.application.base.pay.quickbill.entity;

/**
 * @des 快捷支付 bean 设置
 * @author 孤狼
 */
public class QuickBean {

	public static final String EXT_MAP = "extMap"; 
	public static final String EXT_DATE = "extDate";
	public static final String KEY = "key";
	public static final String VALUE = "value";

	static final String ONE_PAY = "1";
	
	static final String TWO_PAY = "2";

	static final String SAVE_PCI_YES = "1";
	
	static final String SAVE_PCI_NO = "0";

	private String phone;
	
	private String validCode;
	
	private String savePciFlag;
	
	private String token;
	
	private String payBatch;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSavePciFlag() {
		return savePciFlag;
	}

	public void setSavePciFlag(String savePciFlag) {
		this.savePciFlag = savePciFlag;
	}

	public String getPayBatch() {
		return payBatch;
	}

	public void setPayBatch(String payBatch) {
		this.payBatch = payBatch;
	}

}
