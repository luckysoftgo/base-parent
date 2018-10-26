package com.application.base.pay.quickbill.entity;

/**
 * @des 快钱返回码的消息提示
 * @author 孤狼
 */
public class QuickBillCodeDesc {
	
	/**
	 * 处理返回的消息
	 * @param responseCode
	 * @param responseMsg
	 * @return
	 */
	public static String transResponseCode( String responseCode, String responseMsg ) {
		String str1= "",str2="G0",str3="L9",str4="21016",str5="21000",
				str6="CC",str7="OG",str8="OR",str9="TA",str10="01",str11="61",str12="51";
		if ( responseCode == null || str1.equals(responseCode)) {
			return responseMsg;
		}
		
		if (str2.equals(responseCode)) {
			responseMsg = "超出该银行卡单笔金额上限";
		} else if (str3.equals(responseCode)) {
			responseMsg = "银行卡号输入错误";
		} else if (str4.equals(responseCode)) {
			responseMsg = "认证信息不匹配";
		} else if (str5.equals(responseCode)) {
			try {
				responseMsg = responseMsg.split( "-" )[1].trim();
			} catch ( Exception e ) {
				responseMsg = "鉴权失败";
			}
		} else if (str6.equals(responseCode)) {
			responseMsg = "预留手机号输入错误";
		} else if (str7.equals(responseCode)) {
			responseMsg = "单笔金额或日限额超过上限";
		} else if (str8.equals(responseCode)) {
			responseMsg = "短时间内请勿重复提交";
		} else if (str9.equals(responseCode)) {
			responseMsg = "操作失败,请确认输入信息";
		} else if (str10.equals(responseCode)) {
			responseMsg = "请核对卡信息后重新输入";
		} else if (str11.equals(responseCode)) {
			responseMsg = "超出取款转账金额限制";
		} else if (str12.equals(responseCode)) {
			responseMsg = "卡内余额不足";
		}
		return responseMsg;
	}
}
