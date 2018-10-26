package com.application.base.message.activemq.topic.main;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author 孤狼
 */
public class TopicSender {

	public static void main(String[] args) throws Exception {
		run(); 
	}
	
	/**
	 * 发送次数
	 */
	public static final int SEND_NUM = 5;
	/**
	 * tcp 地址
	 */
	public static final String BROKER_URL = "tcp://localhost:61616";
	/**
	 * 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
	 */
	public static final String DESTINATION = "topic";
	
	/**
	 * <b>function:</b> 发送消息
	 */
	public static void sendMessage(Session session, MessageProducer producer) throws Exception {
		for (int i = 0; i < SEND_NUM; i++) {
			String message = "topic 发送消息第" + (i + 1) + "条";
			TextMessage map = session.createTextMessage(message);
			System.out.println(map);
			producer.send(map);
		}
	}
	
	public static void run() throws Exception {
		Connection connection = null;
		Session session = null;
		try {
			// 创建链接工厂
			TopicConnectionFactory factory = new ActiveMQConnectionFactory("admin","admin", BROKER_URL);
			// 通过工厂创建一个连接
			connection = factory.createTopicConnection();
			// 启动连接
			connection.start();
			// 创建一个session会话
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列
			Topic topic = session.createTopic(DESTINATION);
			// 创建消息发送者
			MessageProducer producer = session.createProducer(topic);
			// 设置持久化模式
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			//发送.
			sendMessage(session, producer);
			// 提交会话
			session.commit();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			// 关闭释放资源
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

}
