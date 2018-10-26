package com.application.base.pay.quickbill.util;

import com.application.base.pay.quickbill.entity.MerchantInfo;
import com.application.base.pay.quickbill.entity.QuickBean;
import com.application.base.pay.quickbill.entity.TransInfo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author 孤狼
 */
public class SendTr1 {

	/**
	 * 组装发送参数
	 * @param tr1XML
	 * @param transInfo
	 * @return
	 */
	public static HashMap sendTR1(String tr1XML, TransInfo transInfo) {
		MerchantInfo merchantInfo = null;
		try {
			merchantInfo = new MerchantInfo();
			merchantInfo.setCertPass(PropertiesUtils.getProperty("certPassword"));
			merchantInfo.setCertPath(PropertiesUtils.getProperty("certFileName"));
			merchantInfo.setMerchantId(PropertiesUtils.getProperty("merchantId"));
			merchantInfo.setPassword(PropertiesUtils.getProperty("merchantLoginKey"));
			merchantInfo.setTimeOut(PropertiesUtils.getProperty("timeout"));
			merchantInfo.setDomainName(PropertiesUtils.getProperty("domainName"));
			merchantInfo.setSslPort(PropertiesUtils.getProperty("sslPort"));
			merchantInfo.setUrl(transInfo.getPostUrl());
			merchantInfo.setXml(tr1XML);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		PostTr1Processor ptp = new PostTr1Processor();
		HashMap respXml = null;
		try {
			InputStream is = ptp.post(merchantInfo);
			if (is != null) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] receiveBuffer = new byte[2048];
				int readBytesSize = is.read(receiveBuffer);
				while (readBytesSize != -1) {
					bos.write(receiveBuffer, 0, readBytesSize);
					readBytesSize = is.read(receiveBuffer);
				}
				String reqData = new String(bos.toByteArray(), "UTF-8");
				respXml = ParseUtils.parseXML(reqData, transInfo);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}

	
	/**
	 * 追加参数设置
	 * @param reqXml
	 * @param paraName
	 * @param paraValue
	 * @param transInfo
	 * @return
	 */
	public static StringBuffer appendParam(StringBuffer reqXml, String paraName, String paraValue,
			TransInfo transInfo) {
		if (paraValue == null) {
			paraValue = "";
		}
		if ((!"".equals(paraName)) && (!"".equals(paraValue))) {
		}
		if (reqXml == null) {
			reqXml = new StringBuffer();
			String contentXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><MasMessage xmlns=\"http://www.99bill.com/mas_cnp_merchant_interface\"><version>"
					+ PropertiesUtils.getProperty("version") + "</version>";
			reqXml.append(contentXML).append("<").append(transInfo.getRecordetext1()).append(">")
					.append("<merchantId>").append(PropertiesUtils.getProperty("merchantId")).append("</merchantId>");
			if (!"".equals(paraValue)) {
				reqXml.append("<").append(paraName).append(">").append(paraValue).append("</").append(paraName)
						.append(">");
			}
		}
		else if ((!"".equals(paraName)) && (!"".equals(paraValue))) {
			reqXml.append("<").append(paraName).append(">").append(paraValue).append("</").append(paraName).append(">");
		}
		else if (("".equals(paraName)) && ("".equals(paraValue))) {
			reqXml.append("</").append(transInfo.getRecordetext1()).append(">").append("</MasMessage>");
		}
		return reqXml;
	}

	/**
	 * 交易的参数设置
	 * @param reqXml
	 * @param paraName
	 * @param paraValue
	 * @param transInfo
	 * @return
	 */
	public static StringBuffer transParams(StringBuffer reqXml, String paraName, String paraValue,
			TransInfo transInfo) {
		if (paraValue == null) {
			paraValue = "";
		}
		if (reqXml == null) {
			reqXml = new StringBuffer();
			String contentXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><MasMessage xmlns=\"http://www.99bill.com/mas_cnp_merchant_interface\"><version>"
					+ PropertiesUtils.getProperty("version") + "</version>";
			reqXml.append(contentXML).append("<").append(transInfo.getRecordetext1()).append(">")
					.append("<interactiveStatus>TR1</interactiveStatus>").append("<txnType>")
					.append(transInfo.getTxnType()).append("</txnType>").append("<merchantId>")
					.append(PropertiesUtils.getProperty("merchantId")).append("</merchantId>")
					// .append("<settleMerchantId>").append(p.getProperty("settleMerchantId")).append("</settleMerchantId>")
					.append("<terminalId>").append(PropertiesUtils.getProperty("terminalId")).append("</terminalId>")
					.append("<tr3Url>").append(PropertiesUtils.getProperty("tr3Url")).append("</tr3Url>")
					.append("<entryTime>").append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
					.append("</entryTime>");
			if (!"".equals(paraValue)) {
				reqXml.append("<").append(paraName).append(">").append(paraValue).append("</").append(paraName)
						.append(">");
			}
		}
		else if ((!"".equals(paraName)) && (!"".equals(paraValue))) {
			reqXml.append("<").append(paraName).append(">").append(paraValue).append("</").append(paraName).append(">");
		}
		else if (("".equals(paraName)) && ("".equals(paraValue))) {
			reqXml.append("</").append(transInfo.getRecordetext1()).append(">").append("</MasMessage>");
		}
		return reqXml;
	}
	
	
	/**
	 * 拼装报文.
	 * @param reqXml
	 * @param quickBean
	 * @return
	 */
	public static StringBuffer appendQuickPay(StringBuffer reqXml, QuickBean quickBean) {
		reqXml.append("<").append(QuickBean.EXT_MAP).append(">").append("<").append(QuickBean.EXT_DATE).append(">")
				.append("<").append(QuickBean.KEY).append(">").append("phone").append("</").append(QuickBean.KEY)
				.append(">").append("<").append(QuickBean.VALUE).append(">").append(quickBean.getPhone()).append("</")
				.append(QuickBean.VALUE).append(">").append("</").append(QuickBean.EXT_DATE).append(">").append("<")
				.append(QuickBean.EXT_DATE).append(">").append("<").append(QuickBean.KEY).append(">")
				.append("validCode").append("</").append(QuickBean.KEY).append(">").append("<").append(QuickBean.VALUE)
				.append(">").append(quickBean.getValidCode()).append("</").append(QuickBean.VALUE).append(">")
				.append("</").append(QuickBean.EXT_DATE).append(">").append("<").append(QuickBean.EXT_DATE).append(">")
				.append("<").append(QuickBean.KEY).append(">").append("savePciFlag").append("</").append(QuickBean.KEY)
				.append(">").append("<").append(QuickBean.VALUE).append(">").append(quickBean.getSavePciFlag())
				.append("</").append(QuickBean.VALUE).append(">").append("</").append(QuickBean.EXT_DATE).append(">")
				.append("<").append(QuickBean.EXT_DATE).append(">").append("<").append(QuickBean.KEY).append(">")
				.append("token").append("</").append(QuickBean.KEY).append(">").append("<").append(QuickBean.VALUE)
				.append(">").append(quickBean.getToken()).append("</").append(QuickBean.VALUE).append(">").append("</")
				.append(QuickBean.EXT_DATE).append(">").append("<").append(QuickBean.EXT_DATE).append(">").append("<")
				.append(QuickBean.KEY).append(">").append("payBatch").append("</").append(QuickBean.KEY).append(">")
				.append("<").append(QuickBean.VALUE).append(">").append(quickBean.getPayBatch()).append("</")
				.append(QuickBean.VALUE).append(">").append("</").append(QuickBean.EXT_DATE).append(">").append("</")
				.append(QuickBean.EXT_MAP).append(">");
		return reqXml;
	}
	
	
	/**
	 * 组装发送的报文. 
	 * @param reqXml
	 * @param paraName
	 * @param paraValue
	 * @param transInfo
	 * @return
	 */
	public static StringBuffer buildTransParams(StringBuffer reqXml, String paraName, String paraValue,
			TransInfo transInfo) {
		if (paraValue == null) {
			paraValue = "";
		}
		if (reqXml == null) {
			reqXml = new StringBuffer();
			String contentXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><MasMessage xmlns=\"http://www.99bill.com/mas_cnp_merchant_interface\"><version>"
					+ PropertiesUtils.getProperty("version") + "</version>";
			reqXml.append(contentXML).append("<").append(transInfo.getRecordetext1()).append(">").append("<txnType>")
					.append(transInfo.getTxnType()).append("</txnType>").append("<merchantId>")
					.append(PropertiesUtils.getProperty("merchantId")).append("</merchantId>").append("<terminalId>")
					.append(PropertiesUtils.getProperty("terminalId")).append("</terminalId>");
			if (!"".equals(paraValue)) {
				reqXml.append("<").append(paraName).append(">").append(paraValue).append("</").append(paraName)
						.append(">");
			}
		}
		else if ((!"".equals(paraName)) && (!"".equals(paraValue))) {
			reqXml.append("<").append(paraName).append(">").append(paraValue).append("</").append(paraName).append(">");
		}
		else if (("".equals(paraName)) && ("".equals(paraValue))) {
			reqXml.append("</").append(transInfo.getRecordetext1()).append(">").append("</MasMessage>");
		}
		return reqXml;
	}

}
