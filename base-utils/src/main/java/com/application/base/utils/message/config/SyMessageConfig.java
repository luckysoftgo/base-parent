package com.application.base.utils.message.config;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.application.base.utils.httpclient.ResponseContent;
import com.application.base.utils.json.JsonConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.utils.httpclient.HttpHelper;
import com.application.base.utils.message.constant.Constant;
import com.application.base.utils.message.util.MessageUtil;

/**
 * @desc 示远短信参数配置
 * @author 孤狼
 */
public class SyMessageConfig extends MessageConfig {

	private Logger logger = LoggerFactory.getLogger(SyMessageConfig.class.getName());
	
	/**
	 * 是否需要状态报告，取值true或false，true，表明需要状态报告；false不需要状态报告.
	 */
	private boolean needstatus = true;
	/**
	 * 用户订购的产品id，不填写（针对老用户）系统采用用户的默认产品，用户订购多个产品时必填，否则会发生计费错误.
	 */
	private String product = "";
	/**
	 * 可选参数，扩展码，用户定义扩展码，3位(默认不用填写).
	 */
	private String extno = "";

	private SyMessageConfig() {
	}

	/**
	 * 定义私有变量
	 */
	private static volatile SyMessageConfig msgInstance;
	
	/**
	 * 获得实例对象.
	 */
	public static SyMessageConfig getMsgInstance() {
		if (null==msgInstance) {
			synchronized (SyMessageConfig.class) {
				if (null==msgInstance) {
					msgInstance = new SyMessageConfig();
					msgInstance.setAccount(MessageUtil.getMsgVal("sy_account", Constant.SY_MESSAGE_NAME));
					msgInstance.setPassword(MessageUtil.getMsgVal("sy_password", Constant.SY_MESSAGE_PASS));
				}
			}
		}
		return msgInstance;
	}
	
	
	/**
	 * 初始化手机号,用 "," 分割.
	 * @return
	 */
	public void initConfig(String[] phones) {
		StringBuffer mobiles = new StringBuffer("");
		if (phones != null && phones.length > 0) {
			for (int i = 0; i < phones.length; i++) {
				if (11 == phones[i].length()) {
					if (i == phones.length - 1) {
						mobiles.append(phones[i]);
					}
					else {
						mobiles.append(phones[i] + ",");
					}
				}
			}
		}
		msgInstance.setMobiles(mobiles.toString());
		msgInstance.setNeedstatus(needstatus);
		msgInstance.setProduct(product);
		msgInstance.setExtno(extno);
	}

	public boolean getNeedstatus() {
		return needstatus;
	}

	public void setNeedstatus(boolean needstatus) {
		this.needstatus = needstatus;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getExtno() {
		return extno;
	}

	public void setExtno(String extno) {
		this.extno = extno;
	}

	/**
	 * 检查余额
	 * @return
	 */
	public Map<String, Object> toCheckBalance() {
		StringBuffer buffer = new StringBuffer("");
		buffer.append(MessageUtil.getMsgVal("sy_balanceUrl",Constant.SY_BALANCE_URL));
		buffer.append("?account=" + this.getAccount() + "&pswd=" + this.getPassword());
		Map<String, Object> resultMap = new HashMap<String, Object>(16);
		ResponseContent result = HttpHelper.postUrl(buffer.toString());
		try {
			String[] results = result.getContent().split("\n");
			logger.info("未来无线短信返回的数据是:" + result + ",结果串是:" + JsonConvertUtils.toJson(results));
			String zero ="0";
			if (zero.equalsIgnoreCase(results[0])) {
				resultMap.put("code", Constant.SUCCESS);
				resultMap.put("msg", "检查余额成功");
				resultMap.put("message", this.getContent());
				return resultMap;
			}
			else {
				resultMap.put("code", Constant.FAIL);
				resultMap.put("msg", "检查余额失败");
				resultMap.put("message", this.getContent());
				return resultMap;
			}
		}
		catch (UnsupportedEncodingException e) {
			logger.error("示远发送短信接收的返回异常了", e);
			resultMap.put("code", Constant.FAIL);
			resultMap.put("msg", "检查余额失败");
			resultMap.put("message", this.getContent());
			return resultMap;
		}
	}

	/**
	 * 发送消息.
	 */
	public String toBuildMsg() {
		StringBuffer phones = new StringBuffer("?account=" + this.getAccount() + "&pswd=" + this.getPassword() + "&mobile=");
		phones.append(this.getMobiles());
		phones.append("&msg=" + getContent() + "&needstatus=" + this.getNeedstatus() + "&product=" + this.getProduct() + "&extno=" + this.getExtno());
		logger.info("示远短信发送消息的文本是:" + phones.toString());
		return phones.toString();
	}

	/**
	 * 发送和处理信息.
	 * 
	 * @return
	 */
	public Map<String, Object> toSendMsg() {
		Map<String, Object> resultMap = new HashMap<String, Object>(16);
		String url = MessageUtil.getMsgVal("sy_messageUrl", Constant.SY_MESSAGE_URL) + toBuildMsg();
		ResponseContent result = HttpHelper.postUrl(url);
		try {
			String[] results = result.getContent().split("\n");
			logger.info("未来无线短信返回的数据是:" + result + ",结果串是:" + JsonConvertUtils.toJson(results));
			String zero ="0";
			if (zero.equalsIgnoreCase(results[0])) {
				resultMap.put("code", Constant.SUCCESS);
				resultMap.put("msg", "短信发送成功");
				resultMap.put("message", this.getContent());
				return resultMap;
			}
			else {
				resultMap.put("code", Constant.FAIL);
				resultMap.put("msg", "短信发送失败");
				resultMap.put("message", this.getContent());
				return resultMap;
			}
		}
		catch (UnsupportedEncodingException e) {
			logger.error("示远发送短信接收的返回异常了", e);
			resultMap.put("code", Constant.FAIL);
			resultMap.put("msg", "短信发送失败");
			resultMap.put("message", this.getContent());
			return resultMap;
		}
	}
}
