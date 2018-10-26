package com.application.base.core.datasource.param;

/**
 * @desc 常用的客户构建类
 * @author 孤狼
 */
public interface CustomSql {
	
	/**
	 * 获得 coloumn .
	 * @param column
	 * @return
	 */
	CustomSql cloumn(String column);
	
	/**
	 * 操作符号 .
	 * @param operator
	 * @return
	 */
	CustomSql operator(ESQLOperator operator);
	
	/**
	 * value 值 .
	 * @param value
	 * @return
	 */
	CustomSql value(Object value);

}
