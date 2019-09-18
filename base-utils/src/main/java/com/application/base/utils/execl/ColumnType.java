package com.application.base.utils.execl;

/**
 * @author : 孤狼
 * @NAME: ColumnType
 * @DESC: 列类型
 **/
public enum  ColumnType {
	
	/**
	 * 数字类型
	 */
	NUMERIC(0),
	
	/**
	 * 字符串类型
	 */
	STRING(1);
	
	private final int value;
	
	ColumnType(int value){
		this.value = value;
	}
	
	public int value(){
		return this.value;
	}
}
