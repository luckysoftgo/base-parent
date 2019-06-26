package com.application.base.message.rocketmq.test;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @NAME: Consumer
 * @DESC: 消费者
 * @USER: 孤狼
 **/
public class Consumer {
	
	public static void main(String[] args) throws InterruptedException, MQClientException {
		// Instantiate with specified consumer group name.
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");
		// Specify name server addresses.
		consumer.setNamesrvAddr("127.0.0.1:9876");
		// Subscribe one more more topics to consume.
		consumer.subscribe("PushTopic", "*");
		// Register callback to execute on arrival of messages fetched from brokers.
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				msgs.forEach(item ->{
					System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), new String(item.getBody()));
				});
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		//Launch the consumer instance.
		consumer.start();
		System.out.printf("Consumer Started.%n");
	}
}
