package com.application.base.pay.wechat;

import com.application.base.pay.wechat.bean.WechatRequestData;
import com.application.base.pay.wechat.constant.WechatConstant;
import com.application.base.pay.wechat.util.*;
import com.application.base.utils.json.JsonConvertUtils;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 微信支付测试
 *  @author 孤狼
 */
public class WechatPayTest {

	public static void main(String[] args) {
		
		// 参数组
		String appid = WechatConfigUtils.appId;
		String mch_id = WechatConfigUtils.mchId;
		String nonce_str = RandCharsUtils.getRandomString(32);
		String body = "AAAAA测试微信支付0.01_2";
		String detail = "AAAAA0.01_元测试开始";
		String attach = "备用参数，先留着，后面会有用的";
		//商户订单号.
		String out_trade_no = OrderNoCreator.createOrderNo();
		int total_fee = 1;
		String spbill_create_ip = "127.0.0.1";
		String time_start = RandCharsUtils.timeStart();
		String time_expire = RandCharsUtils.timeExpire();
		String notify_url = WechatConfigUtils.notifyUrl;
		String trade_type = "APP";

		// 参数：开始生成签名
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str", nonce_str);
		parameters.put("body", body);
		//parameters.put("detail", detail);
		//parameters.put("attach", attach);
		parameters.put("out_trade_no", out_trade_no);
		parameters.put("total_fee", total_fee);
		parameters.put("time_start", time_start);
		parameters.put("time_expire", time_expire);
		parameters.put("notify_url", notify_url);
		parameters.put("trade_type", trade_type);
		parameters.put("spbill_create_ip", spbill_create_ip);

		String sign = WechatSignUtils.createSign(WechatConstant.ENCODING, parameters);
		System.out.println("签名是：" + sign);

		WechatRequestData requestData = new WechatRequestData();
		requestData.setAppId(appid);
		requestData.setMchId(mch_id);
		requestData.setNonceStr(nonce_str);
		requestData.setSign(sign);
		requestData.setBody(body);
		//requestData.setDetail(detail);
		//requestData.setAttach(attach);
		requestData.setOutTradeNo(out_trade_no);
		requestData.setTotalFee(total_fee);
		requestData.setSpbillCreateIp(spbill_create_ip);
		requestData.setTimeStart(time_start);
		requestData.setTimeExpire(time_expire);
		requestData.setNotifyUrl(notify_url);
		requestData.setTradeType(trade_type);
		System.out.println("传输的数据格式成json:"+ JsonConvertUtils.toJson(requestData));
		
		// 构造xml参数
		String request_xml = HttpXmlUtils.builderUnifiedOrderRequestXml(requestData);
		
		String reponse_xml = SocketRequestUtils.sendHttpPostRequest(WechatConfigUtils.unifiedorderCallUrl, request_xml,true);
		SortedMap<Object, Object> data = HttpXmlUtils.parseXmlStr(reponse_xml);
		System.out.println("生成的预支付订单数据json:\n"+JsonConvertUtils.toJson(data));
		
	}
}
