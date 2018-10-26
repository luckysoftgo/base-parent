package com.application.base.message.activemq.point.producer;

import com.application.base.message.activemq.point.common.ActiveMqPointCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

/**
 * @author 孤狼
 */
@Service
public class ActiveMqPointMessageProducer {

	private static final Logger logger = LoggerFactory.getLogger(ActiveMqPointMessageProducer.class);
	
	/**
	 * 消息池工厂
	 */
	@Autowired
	private ActiveMqPointCommonUtil pointActiveMQ;
	
	/**
	 * 向目标队列发送消息
	 * @param message:消息
	 * @param destionQueue:目标队列
	 */
	public void sendMessage(final String message,final String queueName,boolean persistent,long timeToLive) {
		QueueSender queueSender = null;
		try {
			// 不持久化操作.
			queueSender = pointActiveMQ.getQueueSender(queueName,persistent,timeToLive);
			QueueSession queueSession = pointActiveMQ.getQueueSession();
			if (null!=queueSession && null!=queueSender) {
				sendMessage(queueSession, queueSender, message);
				// 提交事务.
				queueSession.commit();
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
	public static void sendMessage(QueueSession queueSession, QueueSender queueSender, String msg) {
		try {
			TextMessage message = queueSession.createTextMessage(msg);
			// 发送消息到目的地方
			logger.info("{}", "发送消息：" + "通过 ActiveMQ 发送的消息" + msg);
			queueSender.send(message);
		}
		catch (Exception e) {
			logger.error("{}", "发送消息失败了", e);
		}
	}

}
