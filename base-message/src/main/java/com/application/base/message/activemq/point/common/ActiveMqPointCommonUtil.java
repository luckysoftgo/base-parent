package com.application.base.message.activemq.point.common;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.*;

/**
 * 获取连接的主要信息
 * @author 孤狼
 */
public class ActiveMqPointCommonUtil {

	private Logger logger = LoggerFactory.getLogger(ActiveMqPointCommonUtil.class.getName());
	
	/**
	 * 消息池工厂
	 */
	@Autowired
	private ActiveMQConnectionFactory activeMQConnectionFactory;
	
	/**
	 * QueueConnectionFactory ：连接工厂，queue 用它创建连接
	 */
	private QueueConnectionFactory queueConnectionFactory;
	
	/**
	 * QueueConnection ：queue 客户端到 Point Provider 的连接
	 */
	private QueueConnection queueConnection;
	
	/**
	 *  QueueSession：一个发送或接收消息的线程
	 */
	private QueueSession queueSession;
	
	/**
	 *  Queue ：消息的队列
	 */
	private Queue queue;
	
	/**
	 * 默认消息保存时间10 minutes
	 */
	private long defaultTimeToLive=10*60*1000;
		
	/**
	 * 初始化
	 */
	private void initCommon(String queueName) {
		try {
			if (null==queueConnectionFactory) {
				queueConnectionFactory = activeMQConnectionFactory;
			}
			if (null==queueConnection ) {
				queueConnection = queueConnectionFactory.createQueueConnection();
			}
			queueConnection.start();
			if ( null==queueSession) {
				queueSession = queueConnection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			}
			if (null==queue) {
				queue = queueSession.createQueue(queueName);
			}
		}
		catch (Exception e) {
			logger.error("{}", "初始化失败了", e);
		}
	}
	
	/**
	 * 初始化消息工厂
	 */
	public QueueConnectionFactory getQueueConnectionFactory() {
		QueueConnectionFactory nqueueConnectionFactory = null;
		try {
			if (null==queueConnectionFactory) {
				queueConnectionFactory = activeMQConnectionFactory;
			}
			nqueueConnectionFactory = queueConnectionFactory;
		}
		catch (Exception e) {
			logger.error("{}", "初始化消息工厂失败", e);
		}
		return nqueueConnectionFactory;
	}
	

	/**
	 * 初始化消息连接
	 */
	public QueueConnection getQueueConnection() {
		QueueConnection nqueueConnection = null;
		try {
			if (null==queueConnection) {
				queueConnection = getQueueConnectionFactory().createQueueConnection();
			}
			queueConnection.start();
			nqueueConnection = queueConnection;
		}
		catch (Exception e) {
			logger.error("{}", "初始化消息连接失败", e);
		}
		return nqueueConnection;
	}
	
	
	/**
	 * 初始化消息会话
	 * @return
	 */
	public QueueSession getQueueSession(){
		QueueSession nqueueSession = null;
		try {
			if (null==queueSession) {
				queueSession = getQueueConnection().createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			}
			nqueueSession = queueSession;
		}
		catch (Exception e) {
			logger.error("{}","初始化消息会话失败",e);
		}
		return nqueueSession;
	}
	
	
	/**
	 * 初始化消息队列对象
	 * @return
	 */
	public Queue getQueue(String queueName){
		Queue nqueue = null;
		try {
			if (null==queue) {
				queue = getQueueSession().createQueue(queueName);
			}
			nqueue = queue;
		}
		catch (Exception e) {
			logger.error("{}","初始化消息队列对象失败",e);
		}
		return nqueue;
	}
	
	/**
	 * 获得消息生产者
	 * @return
	 */
	public QueueSender getQueueSender(String queueName,boolean persistent,long timeToLive){
		QueueSender queueSender = null;
		try {
			if (null==queueSender) {
				queueSender = getQueueSession().createSender(getQueue(queueName));
				if (persistent) {
					queueSender.setDeliveryMode(DeliveryMode.PERSISTENT);
				}else{
					queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				}
				if (timeToLive==0L || timeToLive==0 ) {
					queueSender.setTimeToLive(defaultTimeToLive);
				}else{
					queueSender.setTimeToLive(timeToLive);
				}
			}
		}
		catch (Exception e) {
			logger.error("{}","获得消息生产者失败",e);
		}
		return queueSender;
	}
	
	
	/**
	 * 获得消息消费者
	 * @return
	 */
	public QueueReceiver getQueueReceiver(String queueName){
		QueueReceiver queueReceiver = null;
		try {
			if (null==queueReceiver) {
				queueReceiver = getQueueSession().createReceiver(getQueue(queueName));
			}
		}
		catch (Exception e) {
			logger.error("{}","获得消息消费者失败",e);
		}
		return queueReceiver;
	}
}
