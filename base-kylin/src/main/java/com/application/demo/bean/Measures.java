package com.application.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : 孤狼
 * @NAME: Measures
 * @DESC: 函数
 **/
@Data
@AllArgsConstructor
public class Measures implements Serializable {
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 函数信息.
	 */
	private Function function;
	
	/**
	 * 函数信息
	 */
	@Data
	@AllArgsConstructor
	public class Function{
		/**
		 * 表达式.
		 */
		private String expression;
		/**
		 * 返回类型
		 */
		private String returntype;
		/**
		 * 参数
		 */
		private Parameter parameter;
		
	}
	
	/**
	 * 参数.
	 */
	@Data
	@AllArgsConstructor
	public class Parameter {
		/**
		 * 类型
		 */
		private String type;
		/**
		 * 值
		 */
		private String value;
		
	}
}
