package com.application.base.pay.quickbill.entity;

/**
 * @desc 支付信息描述
 * @author 孤狼
 */
public class MerchantInfo {

	private String merchantId;
	private String password;
	private String xml;
	private String url;
	private String certPass;
	private String certPath;
	private int timeOut;
	private String domainName;
	private String sslPort;

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getSslPort() {
		return sslPort;
	}

	public void setSslPort(String sslPort) {
		this.sslPort = sslPort;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getUrl() {
		return this.domainName + ":" + this.sslPort + url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCertPass() {
		return certPass;
	}

	public void setCertPass(String certPass) {
		this.certPass = certPass;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = Integer.parseInt(timeOut);
	}

}
