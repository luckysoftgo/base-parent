package com.application.base.message.activemq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息监听器
 * @author 孤狼
 */
public class JmsMessageListener implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(JmsMessageListener.class.getName());
	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage tm = (TextMessage) message;
	        try {
	        	logger.info(" JMSMessageListener 监听到了文本消息:" + tm.getText());
	            //do something ...
	        } catch (JMSException e) {
	            e.printStackTrace();
	        }
		}
	}
}
