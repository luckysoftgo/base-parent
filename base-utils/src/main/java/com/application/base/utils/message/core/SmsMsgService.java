package com.application.base.utils.message.core;

import java.util.Map;

import com.application.base.utils.message.constant.MsgType;

/**
 * @desc 消息的顶级接口
 * @author 孤狼
 */
public interface SmsMsgService {
	
	/**
	 *查询账号余额
	 * @param type
	 * @return
	 */
	public Map<String, Object> getBalance(MsgType type);
	
	
	/**
	 * 单发/批量发送
	 * @param content
	 * @param phones 一个手机号就是一个 , 多个手机号就是多个发送 .
	 * @param type
	 * @return
	 */
	public Map<String, Object> sendMsg(String content,String[] phones,MsgType type);
		
}
