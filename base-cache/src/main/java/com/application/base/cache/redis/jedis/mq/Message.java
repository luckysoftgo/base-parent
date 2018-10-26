package com.application.base.cache.redis.jedis.mq;

import java.io.Serializable;

/**
 * @desc 消息对象.
 * @author 孤狼
 */
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * //渠道
	 */
	private String chanel;
	
	/**
	 * 索引
	 */
    private int index;
	
	/**
	 * 消息
	 */
	private String msg;

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }
	
    public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}