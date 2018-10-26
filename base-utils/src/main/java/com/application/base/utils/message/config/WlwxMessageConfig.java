package com.application.base.utils.message.config;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.application.base.utils.message.constant.Constant;
import com.application.base.utils.message.util.MessageUtil;
import com.application.base.utils.json.JsonConvertUtils;
import com.application.base.utils.message.util.Md5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @desc 无线未来短信参数配置
 * @author 孤狼
 */
public class WlwxMessageConfig extends MessageConfig {

	private Logger logger = LoggerFactory.getLogger(WlwxMessageConfig.class.getName());

	private static final long serialVersionUID = 1L;
	
	/**
	 * 接入码（扩展码）//死值
	 */
	protected String spcode = "106904020107808";

	protected int taskId;

	private WlwxMessageConfig() {
	}

	/**
	 * 定义私有变量
	 */
	private static volatile WlwxMessageConfig msgInstance;

	/**
	 * 获得实例对象
	 * @return
	 */
	public static WlwxMessageConfig getMsgInstance() {
		if (null == msgInstance) {
			synchronized (WlwxMessageConfig.class) {
				if (null == msgInstance) {
					msgInstance = new WlwxMessageConfig();
					msgInstance.setAccount(MessageUtil.getMsgVal("wlwx_account", Constant.WLWX_MESSAGE_NAME));
					msgInstance.setPassword(MessageUtil.getMsgVal("wlwx_password", Constant.WLWX_MESSAGE_PASS));
				}
			}
		}
		return msgInstance;
	}

	/**
	 * 初始化手机号,用 "," 分割.
	 * 
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
		msgInstance.setSpcode(spcode);
	}

	public String getSpcode() {
		return spcode;
	}

	public void setSpcode(String spcode) {
		this.spcode = spcode;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	/**
	 * 检查余额
	 * @return
	 */
	public Map<String, Object> toCheckBalance() {
		Map<String, Object> resultMap = new HashMap<String, Object>(16);
		try {
			String encoding = "UTF-8";
			String postData = this.getAccount();
			logger.info("未来无线短信发送的数据 : " + postData);
			URL myurl = new URL(MessageUtil.getMsgVal("wlwx_balanceUrl", Constant.WLWX_BALANCE_URL));
			URLConnection urlc = myurl.openConnection();
			urlc.setReadTimeout(1000 * 30);
			urlc.setDoOutput(true);
			urlc.setDoInput(true);
			urlc.setAllowUserInteraction(false);
			DataOutputStream server = new DataOutputStream(urlc.getOutputStream());
			server.write(postData.getBytes(encoding));
			server.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream(),encoding));
			int index = 0;
			String result = "";
			while ((result = in.readLine()) != null) {
				if (index == 0) {
					break;
				}
			}
			in.close();
			String[] results = result.split(":");
			logger.info("未来无线短信返回的数据是:" + result + ",结果串是:" + JsonConvertUtils.toJson(results));
			String success = "SUCCESS";
			if (success.equalsIgnoreCase(results[0])) {
				resultMap.put("code", Constant.SUCCESS);
				resultMap.put("msg", "查询账号余额成功");
				resultMap.put("message", this.getContent());
				return resultMap;
			}
			else {
				resultMap.put("code", Constant.FAIL);
				resultMap.put("msg", "查询账号余额失败");
				resultMap.put("message", this.getContent());
				return resultMap;
			}
		}
		catch (Exception e) {
			logger.error("未来无线发送短信接收的返回异常了", e);
			resultMap.put("code", Constant.FAIL);
			resultMap.put("msg", "查询账号余额失败");
			resultMap.put("message", this.getContent());
			return resultMap;
		}
	}
	/**
	 * 发送消息.
	 */
	public Map<String, Object> toSendMsg() {
		Map<String, Object> resultMap = new HashMap<String, Object>(16);
		try {
			String encoding = "UTF-8";
			String urlencContent = URLEncoder.encode(this.getContent(), encoding);
			String sign = Md5.sign(urlencContent, this.getPassword(), encoding);
			String postData = "content=" + urlencContent + "&destMobiles=" + this.getMobiles() + "&sign=" + sign
					+ "&cust_code=" + this.getAccount() + "&sp_code=" + this.getSpcode() + "&task_id=" + getTaskId();
			logger.info("未来无线短信发送的数据 : " + postData);
			URL myurl = new URL(MessageUtil.getMsgVal("wlwx_messageUrl", Constant.WLWX_MESSAGE_URL));
			URLConnection urlc = myurl.openConnection();
			urlc.setReadTimeout(1000 * 30);
			urlc.setDoOutput(true);
			urlc.setDoInput(true);
			urlc.setAllowUserInteraction(false);
			DataOutputStream server = new DataOutputStream(urlc.getOutputStream());
			server.write(postData.getBytes(encoding));
			server.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream(),encoding));
			int index = 0;
			String result = "";
			while ((result = in.readLine()) != null) {
				if (index == 0) {
					break;
				}
			}
			in.close();
			String[] results = result.split(":");
			logger.info("未来无线短信返回的数据是:" + result + ",结果串是:" + JsonConvertUtils.toJson(results));
			String success = "SUCCESS";
			if (success.equalsIgnoreCase(results[0])) {
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
		catch (Exception e) {
			logger.error("未来无线发送短信接收的返回异常了", e);
			resultMap.put("code", Constant.FAIL);
			resultMap.put("msg", "短信发送失败");
			resultMap.put("message", this.getContent());
			return resultMap;
		}
	}
}
