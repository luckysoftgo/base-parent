package com.application.base.pay.quickbill.entity;

/**
 * @desc 交易信息提示
 * @author 孤狼
 */
public class TransInfo {

	private String postUrl;
	private boolean flag;
	private String recordetext1;
	private String recordeText2;

	private String txnType;

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getRecordetext1() {
		return recordetext1;
	}

	public void setRecordetext1(String recordetext1) {
		this.recordetext1 = recordetext1;
	}

	public String getRecordeText2() {
		return recordeText2;
	}

	public void setRecordeText2(String recordeText2) {
		this.recordeText2 = recordeText2;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

}
