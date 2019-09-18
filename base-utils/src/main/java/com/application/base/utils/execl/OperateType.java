package com.application.base.utils.execl;

/**
 * @author : 孤狼
 * @NAME: OperateType
 * @DESC: 标识作用.
 **/
public enum OperateType {
	
	/**
	 * 导出导入.
	 */
	ALL(0),
	
	/**
	 *仅导出.
	 */
	EXPORT(1),
	
	/**
	 * 仅导入.
	 */
	IMPORT(2);
	
	private final int value;
	
	OperateType(int value){
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}
}
