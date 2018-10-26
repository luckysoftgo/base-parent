package com.application.base.message.activemq.point.main;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author 孤狼
 */
public class PointReceiver {
	
    
    public static void main(String[] args) throws Exception {
    	PointReceiver.run();
    }
	
	/**
	 * tcp 地址
	 */
	public static final String BROKER_URL = "tcp://localhost:61616";
	/**
	 * 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
	 */
	public static final String TARGET = "point";
    
    
    public static void run() throws Exception {
        QueueConnection connection = null;
        QueueSession session = null;
        try {
            // 创建链接工厂
            QueueConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", BROKER_URL);
            // 通过工厂创建一个连接
            connection = factory.createQueueConnection();
            // 启动连接
            connection.start();
            // 创建一个session会话
            session = connection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Queue queue = session.createQueue(TARGET);
            // 创建消息制作者
            QueueReceiver receiver = session.createReceiver(queue);
            
            while (true) {
				// 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
				TextMessage message = (TextMessage) receiver.receive(10000);
				if (null != message) {
					System.out.println("收到消息=>" + message.getText());
				}
				else {
					break;
				}
			}
            
            // 休眠100ms再关闭
            Thread.sleep(1000 * 100); 
            
			// 提交会话
			session.commit();
            
        } catch (Exception e) {
            throw e;
        } finally {
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
