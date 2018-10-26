package com.application.base.message.springmq.handler;

import com.application.base.message.springmq.msg.CommonMessage;

/**
 * @author 孤狼
 */

public class MessageHandler {
	
	/**
	 * 处理信息
	 * 
	 */
    public void handleMessage(CommonMessage message) {
        try{  
            System.out.println("...." + message);  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
	
}
