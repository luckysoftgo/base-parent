package com.application.base.pay.wechat.util;

import com.application.base.utils.common.PropStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


/**
 * 微信的配置参数
 *  @author 孤狼
 */
public class WechatConfigUtils {

	private static final Logger logger = LoggerFactory.getLogger(WechatConfigUtils.class.getName());

	/**
	 * 设置默认值.
	 */
	/**
	 * appId
	 */
	public static String appId ="wx13b503e5bad3478e";
	/**
	 * mchId
	 */
	public static String mchId ="1460819302";
	
	/**
	 *通知地址
	 */
	public static String notifyUrl ="http://www.huaxi0.com/JavaBase/wechat/notice.html";
	/**
	 * 秘钥(商户自己设置的)
	 */
	public static String key="dongfei2005yishuaJPLB046884jipAI";
	/**
	 * 统一下单地址
	 */
	public static String unifiedorderCallUrl ="https://api.mch.weixin.qq.com/pay/unifiedorder";
	/**
	 * 订单查询
	 */
	public static String orderqueryCallUrl ="https://api.mch.weixin.qq.com/pay/orderquery";
	/**
	 * 订单关闭
	 */
	public static String closeorderCallUrl ="https://api.mch.weixin.qq.com/pay/closeorder";
	/**
	 * 申请退款
	 */
	public static String refundCallUrl ="https://api.mch.weixin.qq.com/secapi/pay/refund";
	/**
	 * 退款查询
	 */
	public static String refundqueryCallUrl ="https://api.mch.weixin.qq.com/pay/refundquery";
	/**
	 * 下载对账单
	 */
	public static String downloadbillCallUrl ="https://api.mch.weixin.qq.com/pay/downloadbill";
	/**
	 * 测速上报
	 */
	public static String reportCallUrl ="https://api.mch.weixin.qq.com/payitil/report";

	/**
	 * 初始化操作
	 */
	static {
		try {
			Properties properties = PropStringUtils.getProperties("/configPros/wechat.properties");
			
			appId = properties.getProperty("appId", appId);
			
			mchId = properties.getProperty("mchId", mchId);
			
			notifyUrl = properties.getProperty("notifyUrl", notifyUrl);
			
			key = properties.getProperty("key",key);
			
			unifiedorderCallUrl = properties.getProperty("unifiedorder_call_url", unifiedorderCallUrl);
			
			orderqueryCallUrl = properties.getProperty("orderquery_call_url", orderqueryCallUrl);
			
			closeorderCallUrl = properties.getProperty("closeorder_call_url", closeorderCallUrl);
			
			refundCallUrl = properties.getProperty("refund_call_url", refundCallUrl);
			
			refundqueryCallUrl = properties.getProperty("refundquery_call_url", refundqueryCallUrl);
			
			downloadbillCallUrl = properties.getProperty("downloadbill_call_url", downloadbillCallUrl);
			
			reportCallUrl = properties.getProperty("report_call_url", reportCallUrl);
		}
		catch (Exception ex) {
			logger.error("加载配置文件：" + ex);
		}
	}
}
