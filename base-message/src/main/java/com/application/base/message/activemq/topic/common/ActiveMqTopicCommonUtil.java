package com.application.base.message.activemq.topic.common;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.*;

/**
 * 获取连接的主要信息
 * @author 孤狼
 */
public class ActiveMqTopicCommonUtil {

	private Logger logger = LoggerFactory.getLogger(ActiveMqTopicCommonUtil.class.getName());
	
	/**
	 * 消息池工厂
	 */
	@Autowired
	private ActiveMQConnectionFactory activeMQConnectionFactory;
	
	/**
	 * TopicConnectionFactory ：连接工厂，queue 用它创建连接
	 */
	private TopicConnectionFactory topicConnectionFactory;
	
	/**
	 * TopicConnection ：queue 客户端到 topic Provider 的连接
	 */
	private TopicConnection topicConnection;
	
	/**
	 * TopicSession：一个发送或接收消息的线程
	 */
	private TopicSession topicSession;
	
	/**
	 * Topic ：消息的队列
	 */
	private Topic topic;
	
	/**
	 * 默认消息保存时间10 minutes
	 */
	private long defaultTimeToLive=10*60*1000;
	
	/**
	 * 初始化
	 */
	private void initCommon(String topicName) {
		try {
			if (null==topicConnectionFactory) {
				topicConnectionFactory = activeMQConnectionFactory;
			}
			if (null==topicConnection ) {
				topicConnection = topicConnectionFactory.createTopicConnection();
			}
			topicConnection.start();
			if ( null==topicSession) {
				topicSession = topicConnection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			}
			if (null==topic) {
				topic = topicSession.createTopic(topicName);
			}
		}
		catch (Exception e) {
			logger.error("{}", "初始化失败了", e);
		}
	}
	
	/**
	 * 初始化消息工厂
	 */
	public TopicConnectionFactory getTopicConnectionFactory() {
		TopicConnectionFactory ntopicConnectionFactory = null;
		try {
			if (null==topicConnectionFactory) {
				topicConnectionFactory = activeMQConnectionFactory;
			}
			ntopicConnectionFactory = topicConnectionFactory;
		}
		catch (Exception e) {
			logger.error("{}", "初始化消息工厂失败", e);
		}
		return ntopicConnectionFactory;
	}
	

	/**
	 * 初始化消息连接
	 */
	public TopicConnection getTopicConnection(String topicName) {
		TopicConnection ntopicConnection = null;
		try {
			if (null==topicConnection) {
				topicConnection = getTopicConnectionFactory().createTopicConnection();
				//设置唯一的clientID
				topicConnection.setClientID(topicName);
			}
			topicConnection.start();
			ntopicConnection = topicConnection;
		}
		catch (Exception e) {
			logger.error("{}", "初始化消息连接失败", e);
		}
		return ntopicConnection;
	}
	
	
	/**
	 * 初始化消息会话
	 * @return
	 */
	public TopicSession getTopicSession(String topicName){
		TopicSession ntopicSession = null;
		try {
			if (null==topicSession) {
				topicSession = getTopicConnection(topicName).createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			}
			ntopicSession = topicSession;
		}
		catch (Exception e) {
			logger.error("{}","初始化消息会话失败",e);
		}
		return ntopicSession;
	}
	
	
	/**
	 * 初始化消息队列对象
	 * @return
	 */
	public Topic getTopic(String topicName){
		Topic ntopic = null;
		try {
			if (null==topic) {
				topic = getTopicSession(topicName).createTopic(topicName);
			}
			ntopic = topic;
		}
		catch (Exception e) {
			logger.error("{}","初始化消息队列对象失败",e);
		}
		return ntopic;
	}
	
	/**
	 * 获得消息生产者
	 * @return
	 */
	public MessageProducer getMessageProducer(String topicName,boolean persistent,long timeToLive){
		MessageProducer producer  = null;
		try {
			if (null==producer) {
				producer = getTopicSession(topicName).createProducer(getTopic(topicName));
				if (persistent) {
					producer.setDeliveryMode(DeliveryMode.PERSISTENT);
				}else{
					producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				}
				if (timeToLive==0L || timeToLive==0 ) {
					producer.setTimeToLive(defaultTimeToLive);
				}else{
					producer.setTimeToLive(timeToLive);
				}
			}
		}
		catch (Exception e) {
			logger.error("{}","获得消息生产者失败",e);
		}
		return producer;
	}
	
	
	/**
	 * 获得消息订阅者
	 * @return
	 */
	public MessageConsumer getMessageConsumer(String topicName){
		MessageConsumer consumer = null;
		try {
			if (null==consumer) {
				consumer = getTopicSession(topicName).createDurableSubscriber(getTopic(topicName),topicName);
			}
		}
		catch (Exception e) {
			logger.error("{}","获得消息消费者失败",e);
		}
		return consumer;
	}
}
