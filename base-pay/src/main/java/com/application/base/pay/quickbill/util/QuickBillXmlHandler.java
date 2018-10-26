package com.application.base.pay.quickbill.util;

import com.application.base.pay.quickbill.entity.QuickBillCodeDesc;

import java.util.HashMap;
import java.util.Map;

/**
 * 对快钱返回的结果进行出来的 handler
 * @author 孤狼
 */
public class QuickBillXmlHandler {

	static String responseCode="responseCode",zero="00",externalRefNumber="externalRefNumber",storableCardNo="storableCardNo",
			token="token",storablePan="storablePan",responseTextMessage="responseTextMessage",errorCode="errorCode";
	
	public static Map<String, Object> responseCodeHandle(Map<String, Object> respXml) {
		Map<String, Object> resultData = new HashMap<String, Object>(16);
		String responseCode = "";
		String responseMsg = "";
		String msg = "";
		if (respXml == null) {
			msg = "调用银行接口异常,请稍后再试!";
			resultData.put("responseCode", 0);
			resultData.put("responseMsg", msg);
			return resultData;
		}
		// responseCode: {00, !00, null(errorCode)}
		if (respXml.get(responseCode) != null && zero.equals(respXml.get(responseCode).toString())) {
			// responseCode: 00 回调成功
			String orderId = "";
			String storablePan = "";
			String token = "";
			responseCode = respXml.get("responseCode").toString();
			if (respXml.get(externalRefNumber) != null && !"".equals(respXml.get(externalRefNumber).toString())) {
				orderId = respXml.get("externalRefNumber").toString();
				resultData.put("orderId", orderId);
			}
			if (respXml.get(storableCardNo) != null && !"".equals(respXml.get(storableCardNo).toString())) {
				storablePan = respXml.get("storableCardNo").toString();
				resultData.put("storablePan", storablePan);
			}
			if (respXml.get(token) != null && !"".equals(respXml.get(token).toString())) {
				token = respXml.get("token").toString();
				resultData.put("token", token);
			}
			if (respXml.get(storablePan) != null && !"".equals(respXml.get(storablePan).toString())) {
				storablePan = respXml.get("storablePan").toString();
				resultData.put("storablePan", storablePan);
			}
			resultData.put("responseCode", responseCode);
			return resultData;

		}
		else if (respXml.get(responseCode) != null && !zero.equals(respXml.get(responseCode).toString())) {
			// responseCode: !00 回调失败 获取: responseTextMessage
			responseCode = respXml.get("responseCode").toString();
			if (respXml.get(responseTextMessage) != null) {
				responseMsg = respXml.get("responseTextMessage").toString();
			}
			else {
				responseMsg = "null";
			}
			//获取快钱返回的消息提示.
			responseMsg = QuickBillCodeDesc.transResponseCode(responseCode, responseMsg);
			resultData.put("responseCode", responseCode);
			resultData.put("responseMsg", responseMsg);
			return resultData;
		}
		else if (respXml.get(errorCode) != null && !"".equals(respXml.get(errorCode).toString())) {
			// 回调失败 获取errorCode
			responseCode = respXml.get("errorCode").toString();
			responseMsg = "系统异常,请稍后再试!(" + responseCode + ")";
			resultData.put("responseCode", responseCode);
			resultData.put("responseMsg", responseMsg);
			return resultData;
		}
		else {
			// 回调失败 系统异常
			responseMsg = "系统异常,请稍后再试!";
			resultData.put("responseCode", responseCode);
			resultData.put("responseMsg", responseMsg);
			return resultData;
		}
	}
}
