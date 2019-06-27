package com.application.base.message.activemq.jms.common;

import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * 获取连接的主要信息
 * @author 孤狼
 */
@Component
public class ActiveMqJmsCommonUtil {

	private Logger logger = LoggerFactory.getLogger(ActiveMqJmsCommonUtil.class.getName());
	
	/**
	 * 消息池工厂
	 */
	@Autowired
	private PooledConnectionFactory pooledConnectionFactory;
	
	/**
	 * ConnectionFactory ：连接工厂，JMS 用它创建连接
	 */
	private ConnectionFactory connectionFactory;
	
	/**
	 * Connection ：JMS 客户端到JMS Provider 的连接
	 */
	private Connection connection;
	
	/**
	 * Session：一个发送或接收消息的线程
	 */
	private Session session;
	
	/**
	 * Destination ：消息的目的地;消息发送给谁.
	 */
	private Destination destination;
	
	/**
	 * 默认消息保存时间10 minutes
	 */
	private long defaultTimeToLive=10*60*1000;
	
	/**
	 * 初始化
	 */
	private void initCommon(String destionQueue) {
		try {
			if (connectionFactory == null) {
				connectionFactory = pooledConnectionFactory.getConnectionFactory();
			}
			if (connection == null) {
				connection = connectionFactory.createConnection();
			}
			connection.start();
			if (session == null) {
				session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			}
			if (destination == null) {
				destination = session.createQueue(destionQueue);
			}
		}
		catch (Exception e) {
			logger.error("{}", "初始化失败了", e);
		}
	}
	
	/**
	 * 初始化消息工厂失败
	 */
	public ConnectionFactory getConnectionFactory() {
		ConnectionFactory nconnectionFactory = null;
		try {
			if (null==connectionFactory) {
				connectionFactory = pooledConnectionFactory.getConnectionFactory();
			}
			nconnectionFactory = connectionFactory;
		}
		catch (Exception e) {
			logger.error("{}", "初始化消息工厂失败", e);
		}
		return nconnectionFactory;
	}
	

	/**
	 * 初始化消息连接
	 */
	public Connection getConnection() {
		Connection nconnection = null;
		try {
			if (null==connection) {
				connection = getConnectionFactory().createConnection();
			}
			connection.start();
			nconnection = connection;
		}
		catch (Exception e) {
			logger.error("{}", "初始化消息连接失败", e);
		}
		return nconnection;
	}
	
	
	/**
	 * 初始化消息会话
	 * @return
	 */
	public Session getSession(){
		Session nsession = null;
		try {
			if (null==session) {
				session = getConnection().createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			}
			nsession = session;
		}
		catch (Exception e) {
			logger.error("{}","初始化消息会话失败",e);
		}
		return nsession;
	}
	
	
	/**
	 * 初始化消息队列对象
	 * @return
	 */
	public Destination getDestination(String destionQueue){
		 Destination ndestination = null;
		try {
			if (null==destination) {
				destination = getSession().createQueue(destionQueue);
			}
			ndestination = destination;
		}
		catch (Exception e) {
			logger.error("{}","初始化消息队列对象失败",e);
		}
		return ndestination;
	}
	
	/**
	 * 获得消息生产者
	 * @return
	 */
	public MessageProducer getMessageProducer(String destionQueue,boolean persistent,long timeToLive){
		MessageProducer producer = null;
		try {
			if (null==producer) {
				producer = getSession().createProducer(getDestination(destionQueue));
				if (persistent) {
					producer.setDeliveryMode(DeliveryMode.PERSISTENT);
				}else {
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
	 * 获得消息消费者
	 * @return
	 */
	public MessageConsumer getMessageConsumer(String destionQueue){
		MessageConsumer consumer = null;
		try {
			if (null==consumer) {
				consumer = getSession().createConsumer(getDestination(destionQueue));
			}
		}
		catch (Exception e) {
			logger.error("{}","获得消息消费者失败",e);
		}
		return consumer;
	}
}
