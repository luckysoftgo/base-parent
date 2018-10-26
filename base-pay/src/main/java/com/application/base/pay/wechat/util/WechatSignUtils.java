package com.application.base.pay.wechat.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * @desc 微信支付签名
 * @author 孤狼
 */
public class WechatSignUtils {
	
	/**
	 * 微信支付签名算法 sign
	 * 
	 * @param characterEncoding
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		// 所有参与传参的参数按照accsii排序（升序）
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			Object value = entry.getValue();
			if (null != value && !"".equals(value) && !"sign".equals(key) && !"key".equals(key)) {
				sb.append(key + "=" + value + "&");
			}
		}
		sb.append("key=" + WechatConfigUtils.key);
		if (StringUtils.isBlank(characterEncoding)) {
			characterEncoding = "UTF-8";
		}
		//转换为大写
		return Md5Util.md5Encode(sb.toString(), characterEncoding).toUpperCase();
	}
}
