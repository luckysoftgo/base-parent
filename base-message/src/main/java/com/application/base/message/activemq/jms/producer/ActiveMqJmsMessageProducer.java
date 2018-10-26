package com.application.base.message.activemq.jms.producer;

import com.application.base.message.activemq.jms.common.ActiveMqJmsCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author 孤狼
 */
@Service
public class ActiveMqJmsMessageProducer {

	private static final Logger logger = LoggerFactory.getLogger(ActiveMqJmsMessageProducer.class);
	
	/**
	 * 消息池工厂
	 */
	@Autowired
	private ActiveMqJmsCommonUtil jmsActiveMQ;
	
	/**
	 * 向目标队列发送消息
	 * @param message:消息
	 * @param destionQueue:目标队列
	 */
	public void sendMessage(final String message,final String destionQueue,boolean persistent,long timeToLive) {
		MessageProducer producer = null;
		try {
			// 不持久化操作.
			producer = jmsActiveMQ.getMessageProducer(destionQueue,persistent,timeToLive);
			Session session = jmsActiveMQ.getSession();
			if (null!=session && null!=producer) {
				sendMessage(session, producer, message);
				// 提交事务.
				session.commit();
			}
		}
		catch (Exception e) {
			logger.error("向队列发送消息失败了.", e);
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param session
	 * @param producer
	 */
	public static void sendMessage(Session session, MessageProducer producer, String msg) {
		try {
			TextMessage message = session.createTextMessage(msg);
			// 发送消息到目的地方
			logger.info("{}", "发送消息：" + "通过 ActiveMQ 发送的消息" + msg);
			producer.send(message);
		}
		catch (Exception e) {
			logger.error("{}", "发送消息失败了", e);
		}
	}

}
