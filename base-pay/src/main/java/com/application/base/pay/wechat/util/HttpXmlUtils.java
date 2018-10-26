package com.application.base.pay.wechat.util;

import com.application.base.pay.wechat.bean.WechatRequestData;
import com.application.base.pay.wechat.constant.WechatConstant;
import com.application.base.utils.json.JsonConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * @desc post提交xml格式的参数
 * @author 孤狼
 */
public class HttpXmlUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpXmlUtils.class.getName());
	
	/**
	 * 解析xml,并将值放入 Map 集合中.
	 * @param xmlStr:xml字符串
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static SortedMap<Object, Object> parseXmlStr(String xmlStr) {
		logger.debug("HttpXmlUtils-->parseXmlStr:传入的xml文件是:{}",xmlStr);
		SortedMap<Object, Object> resultMap = new TreeMap<Object, Object>();
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new ByteArrayInputStream(xmlStr.getBytes(WechatConstant.ENCODING)));
			Element root=document.getRootElement();
			List<Element> childElems = root.elements();
			getNodeElementValues(childElems,resultMap);
		}
		catch (Exception e) {
			logger.error("解析XML文件异常{}",e);
			e.printStackTrace();
		} 
		return resultMap;
	}

	/**
	 * 获取节点信息
	 * @param childElements
	 * @param resultMap
	 */
	 @SuppressWarnings("unchecked")
	 private static SortedMap<Object, Object> getNodeElementValues(List<Element> childElements,SortedMap<Object, Object> resultMap) {
	    for (Element ele : childElements) {
	    	resultMap.put(ele.getName(), ele.getText());
	        if(ele.elements().size()>0){
	        	resultMap = getNodeElementValues(ele.elements(), resultMap);
	        }
	    }
	    return resultMap;
	}

	 /**
	 * 微信通知的 xml 
	 * @return
	 */
	public static String builderWechatNoticeResponeXml(String returnCode,String returnMsg) {
		logger.debug("HttpXmlUtils-->builderWechatNoticeResponeXml,微信通知的:传入对象转换成return_code:{},return_msg:{}",returnCode,returnMsg);
		StringBuffer buffer= new StringBuffer();
		buffer.append(WechatConstant.BuilderXml.XML_START);
		//appId
		buffer.append(WechatConstant.BuilderXml.RETURN_CODE_START);
		buffer.append(WechatConstant.BuilderXml.CDATA_START).append(returnCode).append(WechatConstant.BuilderXml.CDATA_END);
		buffer.append(WechatConstant.BuilderXml.RETURN_CODE_END);	
		//bill_date
		buffer.append(WechatConstant.BuilderXml.RETURN_MSG_START);
		buffer.append(WechatConstant.BuilderXml.CDATA_START).append(returnMsg).append(WechatConstant.BuilderXml.CDATA_END);
		buffer.append(WechatConstant.BuilderXml.RETURN_MSG_END);
		//结束.
		buffer.append(WechatConstant.BuilderXml.XML_END);
		return buffer.toString();
	}
		
	
	 /**
	 * 下载对账单的 xml 
	 * @param requestData:构建xml文件的对象.
	 * @return
	 */
	public static String builderDownloadBillRequestXml(WechatRequestData requestData) {
		logger.debug("HttpXmlUtils-->builderDownloadBillRequestXml,下载对账单:传入对象转换成json:", JsonConvertUtils.toJson(requestData));
		
		StringBuffer buffer= new StringBuffer();
		if (requestData != null) {
			buffer.append(WechatConstant.BuilderXml.XML_START);
			//appId
			buffer.append(WechatConstant.BuilderXml.APPID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getAppId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.APPID_END);	
			//bill_date
			buffer.append(WechatConstant.BuilderXml.BILL_DATE_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getBillDate()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.BILL_DATE_START);
			//bill_type
			buffer.append(WechatConstant.BuilderXml.BILL_TYPE_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getBillType()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.BILL_TYPE_END);
			//mchId
			buffer.append(WechatConstant.BuilderXml.MCH_ID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getMchId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.MCH_ID_END);
			//nonce_str
			buffer.append(WechatConstant.BuilderXml.NONOCE_STR_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getNonceStr()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.NONOCE_STR_END);
			//sign
			buffer.append(WechatConstant.BuilderXml.SIGN_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getSign()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.SIGN_END);
			//结束.
			buffer.append(WechatConstant.BuilderXml.XML_END);
			return buffer.toString();
		}
		return buffer.toString();
	}
			
		
	 /**
	 * 退款查询的 xml 
	 * @param requestData:构建xml文件的对象.
	 * @return
	 */
	public static String builderRefundQueryRequestXml(WechatRequestData requestData) {
		logger.debug("HttpXmlUtils-->builderRefundQueryRequestXml,退款查询:传入对象转换成json:",JsonConvertUtils.toJson(requestData));
		StringBuffer buffer= new StringBuffer();
		if (requestData != null) {
			buffer.append(WechatConstant.BuilderXml.XML_START);
			//appId
			buffer.append(WechatConstant.BuilderXml.APPID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getAppId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.APPID_END);	
			//mchId
			buffer.append(WechatConstant.BuilderXml.MCH_ID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getMchId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.MCH_ID_END);
			//nonce_str
			buffer.append(WechatConstant.BuilderXml.NONOCE_STR_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getNonceStr()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.NONOCE_STR_END);
			//out_refund_no
			buffer.append(WechatConstant.BuilderXml.OUT_REFUND_NO_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getOutRefundNo()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.OUT_REFUND_NO_END);
			//out_trade_no
			buffer.append(WechatConstant.BuilderXml.OUT_TRADE_NO_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getOutTradeNo()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.OUT_TRADE_NO_END);
			//refund_id
			buffer.append(WechatConstant.BuilderXml.REFUND_ID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getRefundId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.REFUND_ID_START);
			//transaction_id
			buffer.append(WechatConstant.BuilderXml.TRANSACTION_ID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getTransactionId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.TRANSACTION_ID_END);
			//sign
			buffer.append(WechatConstant.BuilderXml.SIGN_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getSign()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.SIGN_END);
			//结束.
			buffer.append(WechatConstant.BuilderXml.XML_END);
			return buffer.toString();
		}
		return buffer.toString();
	}
		
	/**
	 * 订单退款的 xml 
	 * @param requestData:构建xml文件的对象.
	 * @return
	 */
	public static String builderOrderRefundRequestXml(WechatRequestData requestData) {
		logger.debug("HttpXmlUtils-->builderOrderRefundRequestXml,订单退款:传入对象转换成json:",JsonConvertUtils.toJson(requestData));
		StringBuffer buffer= new StringBuffer();
		if (requestData != null) {
			buffer.append(WechatConstant.BuilderXml.XML_START);
			//appId
			buffer.append(WechatConstant.BuilderXml.APPID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getAppId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.APPID_END);	
			//mchId
			buffer.append(WechatConstant.BuilderXml.MCH_ID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getMchId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.MCH_ID_END);
			//nonce_str
			buffer.append(WechatConstant.BuilderXml.NONOCE_STR_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getNonceStr()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.NONOCE_STR_END);
			//op_user_id
			buffer.append(WechatConstant.BuilderXml.OP_USER_ID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getOpUserId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.OP_USER_ID_START);
			//out_refund_no
			buffer.append(WechatConstant.BuilderXml.OUT_REFUND_NO_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getOutRefundNo()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.OUT_REFUND_NO_END);
			//out_trade_no
			buffer.append(WechatConstant.BuilderXml.OUT_TRADE_NO_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getOutTradeNo()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.OUT_TRADE_NO_END);
			//refund_fee
			buffer.append(WechatConstant.BuilderXml.REFUND_FEE_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getRefundFee()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.REFUND_FEE_START);
			//total_fee
			buffer.append(WechatConstant.BuilderXml.TOTAL_FEE_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getTotalFee()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.TOTAL_FEE_END);
			//transaction_id
			buffer.append(WechatConstant.BuilderXml.TRANSACTION_ID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getTransactionId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.TRANSACTION_ID_END);
			//sign
			buffer.append(WechatConstant.BuilderXml.SIGN_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getSign()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.SIGN_END);

			//结束.
			buffer.append(WechatConstant.BuilderXml.XML_END);
			return buffer.toString();
		}
		return buffer.toString();
	}
		
	 
	/**
	 * 订单关闭的 xml 
	 * @param requestData:构建xml文件的对象.
	 * @return
	 */
	public static String builderOrderCloseRequestXml(WechatRequestData requestData) {
		logger.debug("HttpXmlUtils-->builderOrderCloseRequestXml,订单关闭:传入对象转换成json:",JsonConvertUtils.toJson(requestData));
		StringBuffer buffer= new StringBuffer();
		if (requestData != null) {
			buffer.append(WechatConstant.BuilderXml.XML_START);
			//appId
			buffer.append(WechatConstant.BuilderXml.APPID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getAppId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.APPID_END);	
			//mchId
			buffer.append(WechatConstant.BuilderXml.MCH_ID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getMchId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.MCH_ID_END);
			//nonce_str
			buffer.append(WechatConstant.BuilderXml.NONOCE_STR_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getNonceStr()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.NONOCE_STR_END);
			//out_trade_no
			buffer.append(WechatConstant.BuilderXml.OUT_TRADE_NO_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getOutTradeNo()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.OUT_TRADE_NO_END);
			//sign
			buffer.append(WechatConstant.BuilderXml.SIGN_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getSign()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.SIGN_END);
			
			buffer.append(WechatConstant.BuilderXml.XML_END);
			return buffer.toString();
		}
		return buffer.toString();
	}
		
	
	/**
	 * 订单查询的 xml 
	 * @param requestData:构建xml文件的对象.
	 * @return
	 */
	public static String builderOrderQueryRequestXml(WechatRequestData requestData) {
		logger.debug("HttpXmlUtils-->builderOrderQueryRequestXml,订单查询:传入对象转换成json:",JsonConvertUtils.toJson(requestData));
		StringBuffer buffer= new StringBuffer();
		if (requestData != null) {
			buffer.append(WechatConstant.BuilderXml.XML_START);
			//appId
			buffer.append(WechatConstant.BuilderXml.APPID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getAppId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.APPID_END);	
			//mchId
			buffer.append(WechatConstant.BuilderXml.MCH_ID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getMchId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.MCH_ID_END);
			//nonce_str
			buffer.append(WechatConstant.BuilderXml.NONOCE_STR_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getNonceStr()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.NONOCE_STR_END);
			//out_trade_no
			if (StringUtils.isNotBlank(requestData.getOutTradeNo())) {
				buffer.append(WechatConstant.BuilderXml.OUT_TRADE_NO_START);
				buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getOutTradeNo()).append(WechatConstant.BuilderXml.CDATA_END);
				buffer.append(WechatConstant.BuilderXml.OUT_TRADE_NO_END);
			}
			//transaction_id
			if (StringUtils.isNotBlank(requestData.getTransactionId())) {
				buffer.append(WechatConstant.BuilderXml.TRANSACTION_ID_START);
				buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getTransactionId()).append(WechatConstant.BuilderXml.CDATA_END);
				buffer.append(WechatConstant.BuilderXml.TRANSACTION_ID_END);
			}
			//sign
			buffer.append(WechatConstant.BuilderXml.SIGN_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getSign()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.SIGN_END);
			
			buffer.append(WechatConstant.BuilderXml.XML_END);
			return buffer.toString();
		}
		return buffer.toString();
	}
	
	/**
	 * 构建统一下单xml
	 * @param requestData:构建xml文件的对象.
	 * @return
	 */
	public static String builderUnifiedOrderRequestXml(WechatRequestData requestData) {
		logger.debug("HttpXmlUtils-->builderUnifiedOrderRequestXml,构建统一下:传入对象转换成json:",JsonConvertUtils.toJson(requestData));
		StringBuffer buffer= new StringBuffer();
		if (requestData != null) {
			buffer.append(WechatConstant.BuilderXml.XML_START);
			//appId
			buffer.append(WechatConstant.BuilderXml.APPID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getAppId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.APPID_END);	
			//mchId
			buffer.append(WechatConstant.BuilderXml.MCH_ID_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getMchId()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.MCH_ID_END);
			//nonce_str
			buffer.append(WechatConstant.BuilderXml.NONOCE_STR_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getNonceStr()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.NONOCE_STR_END);
			//sign
			buffer.append(WechatConstant.BuilderXml.SIGN_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getSign()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.SIGN_END);
			//body
			buffer.append(WechatConstant.BuilderXml.BODY_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getBody()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.BODY_END);
			//detail
			if (StringUtils.isNotBlank(requestData.getDetail())) {
				buffer.append(WechatConstant.BuilderXml.DETAIL_START);
				buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getDetail()).append(WechatConstant.BuilderXml.CDATA_END);
				buffer.append(WechatConstant.BuilderXml.DETAIL_END);
			}
			//attach
			if (StringUtils.isNotBlank(requestData.getAttach())) {
				buffer.append(WechatConstant.BuilderXml.ATTACH_START);
				buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getAttach()).append(WechatConstant.BuilderXml.CDATA_END);
				buffer.append(WechatConstant.BuilderXml.ATTACH_END);
			}
			//out_trade_no
			buffer.append(WechatConstant.BuilderXml.OUT_TRADE_NO_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getOutTradeNo()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.OUT_TRADE_NO_END);
			//total_fee
			buffer.append(WechatConstant.BuilderXml.TOTAL_FEE_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getTotalFee()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.TOTAL_FEE_END);
			//spbill_create_ip
			buffer.append(WechatConstant.BuilderXml.SPBILL_CREATE_IP_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getSpbillCreateIp()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.SPBILL_CREATE_IP_END);
			//time_start
			if (StringUtils.isNotBlank(requestData.getTimeStart())) {
				buffer.append(WechatConstant.BuilderXml.TIME_START_START);
				buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getTimeStart()).append(WechatConstant.BuilderXml.CDATA_END);
				buffer.append(WechatConstant.BuilderXml.TIME_START_END);
			}
			//time_expire
			if (StringUtils.isNotBlank(requestData.getTimeExpire())) {
				buffer.append(WechatConstant.BuilderXml.TIME_EXPIRE_START);
				buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getTimeExpire()).append(WechatConstant.BuilderXml.CDATA_END);
				buffer.append(WechatConstant.BuilderXml.TIME_EXPIRE_END);
			}
			//goods_tag
			if (StringUtils.isNotBlank(requestData.getGoodsTag())) {
				buffer.append(WechatConstant.BuilderXml.GOODS_TAG_START);
				buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getGoodsTag()).append(WechatConstant.BuilderXml.CDATA_END);
				buffer.append(WechatConstant.BuilderXml.GOODS_TAG_END);
			}
			//notifyUrl
			buffer.append(WechatConstant.BuilderXml.NOTIFY_URL_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getNotifyUrl()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.NOTIFY_URL_END);
			//trade_type
			buffer.append(WechatConstant.BuilderXml.TRADE_TYPE_START);
			buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getTradeType()).append(WechatConstant.BuilderXml.CDATA_END);
			buffer.append(WechatConstant.BuilderXml.TRADE_TYPE_END);
			//limit_pay
			if (StringUtils.isNotBlank(requestData.getLimitPay())) {
				buffer.append(WechatConstant.BuilderXml.LIMIT_PAY_START);
				buffer.append(WechatConstant.BuilderXml.CDATA_START).append(requestData.getLimitPay()).append(WechatConstant.BuilderXml.CDATA_END);
				buffer.append(WechatConstant.BuilderXml.LIMIT_PAY_END);
			}
			buffer.append(WechatConstant.BuilderXml.XML_END);
			return buffer.toString();
		}
		return buffer.toString();
	}
	
}
