package com.application.base.message.activemq.jms.main;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author 孤狼
 */
public class JmsReceiver {


	public static void main(String[] args) throws Exception {
		run();
	}
	
	/**
	 * 发送次数
	 */
	public static final int SEND_NUM = 10;
	/**
	 * tcp 地址
	 */
	public static final String BROKER_URL = "tcp://118.24.157.96:61616";
	/**
	 * 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
	 */
	public static final String DESTINATION = "jms";
	
	
	public static void run() throws Exception {
		// ConnectionFactory ：连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory;
		// Connection ：JMS 客户端到JMS Provider 的连接
		Connection connection = null;
		// Session： 一个发送或接收消息的线程
		Session session;
		// Destination ：消息的目的地;消息发送给谁.
		Destination destination;
		// 消费者，消息接收者
		MessageConsumer consumer;
		connectionFactory = new ActiveMQConnectionFactory("admin","admin", BROKER_URL);
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
			destination = session.createQueue(DESTINATION);
			consumer = session.createConsumer(destination);
			while (true) {
				// 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
				TextMessage message = (TextMessage) consumer.receive(10000);
				if (null != message) {
					System.out.println("收到消息=>" + message.getText());
				}
				else {
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (null != connection) {
					connection.close();
				}
			}
			catch (Throwable ignore) {
			}
		}
	}
}
