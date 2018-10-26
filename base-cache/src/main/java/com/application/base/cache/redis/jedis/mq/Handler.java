package com.application.base.cache.redis.jedis.mq;

/**
 * @desc 消息列表
 * @author 孤狼
 *
 */
@FunctionalInterface
public interface Handler {
	
    /**
     * 处理消息
     * @param msg
     */
    void handle(Message msg);
    
}

