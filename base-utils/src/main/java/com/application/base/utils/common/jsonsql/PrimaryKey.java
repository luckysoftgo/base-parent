package com.application.base.utils.common.jsonsql;

/**
 * @author : 孤狼
 * @NAME: PrimaryKey
 * @DESC: 主键的描述.
 **/
public class PrimaryKey {
	
	/**
	 * 主键类型:1:Integer 自增类型;2.String类型.
	 */
	private String primType;
	/**
	 * 主键
	 */
	private String primKey;
	/**
	 * 主键长度.
	 */
	private int charLen;
	
	public PrimaryKey(String primKey,String primType){
		this.primKey = primKey;
		this.primType = primType;
	}
	
	public PrimaryKey(String primKey,String primType,int charLen){
		this.primKey = primKey;
		this.primType = primType;
		this.charLen = charLen;
	}
	
	public String getPrimType() {
		return primType;
	}
	
	public void setPrimType(String primType) {
		this.primType = primType;
	}
	
	public int getCharLen() {
		return charLen;
	}
	
	public void setCharLen(int charLen) {
		this.charLen = charLen;
	}
	
	public String getPrimKey() {
		return primKey;
	}
	
	public void setPrimKey(String primKey) {
		this.primKey = primKey;
	}
	
}
