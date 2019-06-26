package com.application.base.message.rocketmq.test;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @NAME: SyncProducer
 * @DESC: 消息生产者
 * @USER: 孤狼
 **/
public class SyncProducer {
	private static final Logger logger = LogManager.getLogger(SyncProducer.class);
	public static void main(String[] args){
		
		Boolean isloadconfig = Boolean.parseBoolean(System.getProperty("rocketmq.client.log.loadconfig", "true"));
		System.setProperty("rocketmq.client.log.loadconfig","false");
		System.out.println(isloadconfig);
		DefaultMQProducer producer = new DefaultMQProducer("Producer");
		producer.setNamesrvAddr("127.0.0.1:9876");
		
		/*
		try {
			producer.start();
			Message msg = new Message("PushTopic","push","1","这是测试消息一".getBytes());
			SendResult result = producer.send(msg);
			logger.info("id:" + result.getMsgId() + " result:" + result.getSendStatus());
			
			msg = new Message("PushTopic","push","2","这是测试消息二".getBytes());
			result = producer.send(msg);
			logger.info("id:" + result.getMsgId() +" result:" + result.getSendStatus());
			
			msg = new Message("PullTopic","pull","3","这是测试消息三".getBytes());
			result = producer.send(msg);
			logger.info("id:" + result.getMsgId() +	" result:" + result.getSendStatus());
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			producer.shutdown();
		}
		*/
		try {
			producer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i <Integer.MAX_VALUE ; i++) {
			try {
				Message msg = new Message("PushTopic","push",i+"",("这是测试消息"+i).getBytes());
				SendResult result = producer.send(msg);
				logger.info("id:" + result.getMsgId() + " result:" + result.getSendStatus()+",sendMsg:"+msg.toString());
				Thread.sleep(50);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		producer.shutdown();
	}
}
