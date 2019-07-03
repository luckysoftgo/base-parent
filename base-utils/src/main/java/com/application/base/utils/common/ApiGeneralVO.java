package com.application.base.utils.common;

import org.apache.poi.ss.formula.functions.T;

/**
 * @NAME= ApiGeneralVO
 * @DESC= 数据返回描述
 * @USER= 孤狼
 **/
public final class ApiGeneralVO {
	
	public static void main(String[] args) {
	
	}
	
	/**
	 * 请求头中的信息.
	 */
	private Header header;
	/**
	 * 请求体的描述.
	 */
	private Body body;
	
	public Header getHeader() {
		return header;
	}
	
	public void setHeader(Header header) {
		this.header = header;
	}
	
	public Body getBody() {
		return body;
	}
	
	public void setBody(Body body) {
		this.body = body;
	}
	
	/**
	 * 请求头中的信息.
	 */
	final class Header{
		/**
		 * 访问语音
		 */
		private String localLang="zh_CN";
		/**
		 * 扩展内容
		 */
		private String extendContent="";
		/**
		 * 认证:可以是登录后的token,访问的ip等,也可以是一个具体的code值
		 */
		private String authrTellerNo="50000";
		/**
		 * 交易日期
		 */
		private String tranDate="19700101";
		/**
		 * 交易码
 		 */
		private String tranCode="REQUEST";
		/**
		 * 人员编号
		 */
		private String tranTeller="10000";
		/**
		 * 交易时间:秒
		 */
		private String tranTime="";
		/**
		 *交易的编号
		 */
		private String tranSeq="88888";
		/**
		 *访问认证密码
		 */
		private String authrPwd="";
		/**
		 * 认证flag
		 */
		private String authFlag="OK";
		/**
		 * 通道
		 */
		private String channel="HTTP";
		
		/**
		 * 关键编号
		 */
		private String keyId="10000";
		/**
		 *分支ID
		 */
		private String branchId="400100";
		/**
		 * 访问流水号:每次访问都不一样
		 */
		private String globalSeq="100001";
		/**
		 *端访问的mac地址
		 */
		private String mac="fe80::14c:a182:15eb:350d%15";
		/**
		 * 请求的服务code.默认servicecode 和servicename 是一样的
		 */
		private String serviceCode="";
		/**
		 *端的code值
		 */
		private String terminalCode="";
		/**
		 * 请求的服务名称:默认servicecode 和servicename 是一样的
		 */
		private String serviceName="";
		/**
		 * 消费者ID
		 */
		private String consumerId="99999";
		/**
		 * 系统ID
		 */
		private String sourceSysId="";
		/**
		 * 合法账户code
		 */
		private String legalRepCode="ADMIN";
		
		public Header() {
		}
		
		public Header(String serviceCode, String serviceName) {
			this.serviceCode = serviceCode;
			this.serviceName = serviceName;
		}
		
		public Header(String tranDate, String serviceCode, String serviceName) {
			this.tranDate = tranDate;
			this.serviceCode = serviceCode;
			this.serviceName = serviceName;
		}
		
		public Header(String tranDate, String tranCode, String serviceCode, String serviceName) {
			this.tranDate = tranDate;
			this.tranCode = tranCode;
			this.serviceCode = serviceCode;
			this.serviceName = serviceName;
		}
		
		public Header(String authrTellerNo, String tranDate, String tranCode, String serviceCode, String serviceName,
		              String consumerId) {
			this.authrTellerNo = authrTellerNo;
			this.tranDate = tranDate;
			this.tranCode = tranCode;
			this.serviceCode = serviceCode;
			this.serviceName = serviceName;
			this.consumerId = consumerId;
		}
		
		public String getLocalLang() {
			return localLang;
		}
		
		public void setLocalLang(String localLang) {
			this.localLang = localLang;
		}
		
		public String getExtendContent() {
			return extendContent;
		}
		
		public void setExtendContent(String extendContent) {
			this.extendContent = extendContent;
		}
		
		public String getAuthrTellerNo() {
			return authrTellerNo;
		}
		
		public void setAuthrTellerNo(String authrTellerNo) {
			this.authrTellerNo = authrTellerNo;
		}
		
		public String getTranDate() {
			return tranDate;
		}
		
		public void setTranDate(String tranDate) {
			this.tranDate = tranDate;
		}
		
		public String getTranCode() {
			return tranCode;
		}
		
		public void setTranCode(String tranCode) {
			this.tranCode = tranCode;
		}
		
		public String getTranTeller() {
			return tranTeller;
		}
		
		public void setTranTeller(String tranTeller) {
			this.tranTeller = tranTeller;
		}
		
		public String getTranTime() {
			return tranTime;
		}
		
		public void setTranTime(String tranTime) {
			this.tranTime = tranTime;
		}
		
		public String getTranSeq() {
			return tranSeq;
		}
		
		public void setTranSeq(String tranSeq) {
			this.tranSeq = tranSeq;
		}
		
		public String getAuthrPwd() {
			return authrPwd;
		}
		
		public void setAuthrPwd(String authrPwd) {
			this.authrPwd = authrPwd;
		}
		
		public String getAuthFlag() {
			return authFlag;
		}
		
		public void setAuthFlag(String authFlag) {
			this.authFlag = authFlag;
		}
		
		public String getChannel() {
			return channel;
		}
		
		public void setChannel(String channel) {
			this.channel = channel;
		}
		
		public String getKeyId() {
			return keyId;
		}
		
		public void setKeyId(String keyId) {
			this.keyId = keyId;
		}
		
		public String getBranchId() {
			return branchId;
		}
		
		public void setBranchId(String branchId) {
			this.branchId = branchId;
		}
		
		public String getGlobalSeq() {
			return globalSeq;
		}
		
		public void setGlobalSeq(String globalSeq) {
			this.globalSeq = globalSeq;
		}
		
		public String getMac() {
			return mac;
		}
		
		public void setMac(String mac) {
			this.mac = mac;
		}
		
		public String getServiceCode() {
			return serviceCode;
		}
		
		public void setServiceCode(String serviceCode) {
			this.serviceCode = serviceCode;
		}
		
		public String getTerminalCode() {
			return terminalCode;
		}
		
		public void setTerminalCode(String terminalCode) {
			this.terminalCode = terminalCode;
		}
		
		public String getServiceName() {
			return serviceName;
		}
		
		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}
		
		public String getConsumerId() {
			return consumerId;
		}
		
		public void setConsumerId(String consumerId) {
			this.consumerId = consumerId;
		}
		
		public String getSourceSysId() {
			return sourceSysId;
		}
		
		public void setSourceSysId(String sourceSysId) {
			this.sourceSysId = sourceSysId;
		}
		
		public String getLegalRepCode() {
			return legalRepCode;
		}
		
		public void setLegalRepCode(String legalRepCode) {
			this.legalRepCode = legalRepCode;
		}
	}
	
	/**
	 * 请求体的描述.
	 */
	final class Body{
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
		
		public Body() {
		}
		
		public Body(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public Body(String code, String msg, T data) {
			this.code = code;
			this.msg = msg;
			this.data = data;
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
	}
	
}
