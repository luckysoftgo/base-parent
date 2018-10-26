package com.application.base.message.activemq.topic.producer;

import com.application.base.message.activemq.topic.common.ActiveMqTopicCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import javax.jms.TopicSession;

/**
 * @author 孤狼
 */
@Service
public class ActiveMqTopicMessageProducer {

	private static final Logger logger = LoggerFactory.getLogger(ActiveMqTopicMessageProducer.class);
	
	/**
	 * 消息池工厂
	 */
	@Autowired
	private ActiveMqTopicCommonUtil topicActiveMQ;
	
	/**
	 * 向目标队列发送消息
	 * @param message:消息
	 * @param destionQueue:目标队列
	 */
	public void sendMessage(final String message,final String topicName,boolean persistent,long timeToLive) {
		MessageProducer producer = null;
		try {
			// 不持久化操作.
			producer = topicActiveMQ.getMessageProducer(topicName,persistent,timeToLive);
			TopicSession topicSession = topicActiveMQ.getTopicSession(topicName);
			if (null!=topicSession && null!=producer) {
				sendMessage(topicSession, producer, message);
				// 提交事务.
				topicSession.commit();
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
	public static void sendMessage(TopicSession topicSession, MessageProducer producer, String msg) {
		try {
			TextMessage message = topicSession.createTextMessage(msg);
			// 发送消息到目的地方
			logger.info("{}", "发送消息：" + "通过 ActiveMQ 发送的消息" + msg);
			producer.send(message);
		}
		catch (Exception e) {
			logger.error("{}", "发送消息失败了", e);
		}
	}

}
