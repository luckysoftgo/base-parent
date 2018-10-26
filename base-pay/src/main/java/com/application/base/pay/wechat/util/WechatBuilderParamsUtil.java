package com.application.base.pay.wechat.util;

import com.application.base.pay.wechat.bean.WechatRequestData;
import com.application.base.pay.wechat.constant.WechatConstant;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 构建参数
 *  @author 孤狼
 */
public class WechatBuilderParamsUtil {

	/**
	 * 创建统一订单
	 * @return
	 */
	public static WechatRequestData builderUnifiedOrder(String body, String appIp) {
		// 参数组
		String appId = WechatConfigUtils.appId;
		String mchId = WechatConfigUtils.mchId;
		String nonceStr = RandCharsUtils.getRandomString(32);
		nonceStr= nonceStr.toUpperCase();
		String outTradeNo = OrderNoCreator.createOrderNo();
		// 单位是分，即是0.01元
		int totalFee = 1;
		String timeStart = RandCharsUtils.timeStart();
		String timeExpire = RandCharsUtils.timeExpire();
		String notifyUrl = WechatConfigUtils.notifyUrl;
		String tradeType = WechatConstant.TRADE_TYPE;

		// 参数：开始生成签名
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", appId);
		parameters.put("mch_id", mchId);
		parameters.put("nonce_str", nonceStr);
		parameters.put("body", body);
		parameters.put("out_trade_no", outTradeNo);
		parameters.put("total_fee", totalFee);
		parameters.put("time_start", timeStart);
		parameters.put("time_expire", timeExpire);
		parameters.put("notify_url", notifyUrl);
		parameters.put("trade_type", tradeType);
		parameters.put("spbill_create_ip", appIp);
		
		String sign = WechatSignUtils.createSign(WechatConstant.ENCODING, parameters);
		WechatRequestData requestData = new WechatRequestData();
		requestData.setAppId(appId);
		requestData.setMchId(mchId);
		requestData.setNonceStr(nonceStr);
		requestData.setSign(sign);
		requestData.setBody(body);
		requestData.setOutTradeNo(outTradeNo);
		requestData.setTotalFee(totalFee);
		requestData.setSpbillCreateIp(appIp);
		requestData.setTimeStart(timeStart);
		requestData.setTimeExpire(timeExpire);
		requestData.setNotifyUrl(notifyUrl);
		requestData.setTradeType(tradeType);
		
		return requestData;
	}
}
