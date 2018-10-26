package com.application.base.message.activemq.jms.main;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author 孤狼
 */
public class JmsSender {

	public static void main(String[] args) throws Exception {
		run();
	}
	
	/**
	 * 发送次数
	 */
	public static final int SEND_NUM = 10;
	/**
	 *  tcp 地址
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
		// MessageProducer：消息发送者
		MessageProducer producer;
		// TextMessage message;
		// 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
		connectionFactory = new ActiveMQConnectionFactory("admin","admin", BROKER_URL);
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接 : true 表示是transaction,false : 表示不是;
			//AUTO_ACKNOWLEDGE:自动接收消息;
			//CLIENT_ACKNOWLEDGE 客户端程序将确认收到的一则消息，调用这则消息的确认方法;
			//DUPS_OK_ACKNOWLEDGE 这个选项命令session“懒散的”确认消息传递，可以想到，这将导致消息提供者传递的一些复制消息可能出错;
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
			destination = session.createQueue(DESTINATION);
			// 得到消息生成者【发送者】
			producer = session.createProducer(destination);
			// 设置不持久化，此处学习，实际根据项目决定
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			// 构造消息，此处写死，项目就是参数，或者方法获取
			sendMessage(session, producer);
			session.commit();
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

	public static void sendMessage(Session session, MessageProducer producer) throws Exception {
		for (int i = 1; i <= SEND_NUM; i++) {
			TextMessage message = session.createTextMessage("jms ActiveMq 发送的消息" + i);
			// 发送消息到目的地方
			System.out.println("发送消息:" + "ActiveMq 发送的消息" + i);
			producer.send(message);
		}
	}
}
