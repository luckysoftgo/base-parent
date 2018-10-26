package com.application.base.message.activemq.topic.main;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;

/**
 * @author 孤狼
 */
public class TopicReceiver {

	public static void main(String[] args) throws Exception {
		run(); // success
	}
	
	/**
	 * tcp 地址
	 */
	public static final String BROKER_URL = "tcp://localhost:61616";
	/**
	 * 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
	 */
	public static final String TARGET = "topic";

	public static void run() throws Exception {
		Connection connection = null;
		Session session = null;
		try {
			// 创建链接工厂
			ConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", BROKER_URL);
			// 通过工厂创建一个连接
			connection = factory.createConnection();
			connection.setClientID(TARGET);
			// 启动连接
			connection.start();
			// 创建一个session会话
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列
			ActiveMQTopic topic = new ActiveMQTopic(TARGET);
			// 创建消息制作者
			MessageConsumer consumer = session.createDurableSubscriber(topic,TARGET);
			System.out.println("发布完的消息是:");
			while (true) {
				// 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
				TextMessage message = (TextMessage) consumer.receive(5000);
				if (null != message ) {
					System.out.println("topic 收到消息=>:time=" + message.getText());
				}
				else {
					break;
				}
			}
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
