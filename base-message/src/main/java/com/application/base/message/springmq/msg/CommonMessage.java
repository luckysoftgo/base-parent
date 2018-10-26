package com.application.base.message.springmq.msg;

/**
 * @author 孤狼
 */
public class CommonMessage {

	/**
	 * 约定的几个消息源名称
	 */
	private String source;
	/**
	 * 实体表名
	 */
	private String table;
	/**
	 * 主键
	 */
	private String primaryKey;
	/**
	 * 消息实体bean
	 */
	private Object message;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "source="+source+",table="+table+",primaryKey="+primaryKey+",message="+message;
	}

}
