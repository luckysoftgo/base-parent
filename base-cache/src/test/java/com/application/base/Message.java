package com.application.base;

import java.io.Serializable;

/**
 * @desc 队列体
 * @author 孤狼
 */
public class Message implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private int msgId;
	private String msgTitle;
	private String msgcontent;
	
	public Message() {
	}
	
	public Message(int msgId, String msgTitle, String msgcontent) {
		this.msgId = msgId;
		this.msgTitle = msgTitle;
		this.msgcontent = msgcontent;
	}
	
	public int getMsgId() {
		return msgId;
	}
	
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
	
	public String getMsgTitle() {
		return msgTitle;
	}
	
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	
	public String getMsgcontent() {
		return msgcontent;
	}
	
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}
	
}
