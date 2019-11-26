package com.application.base.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : 孤狼
 * @NAME: Dimensions
 * @DESC: 表信息
 **/
@Data
@AllArgsConstructor
public class Dimensions implements Serializable {
	
	/**
	 * 列名
	 */
	private String name;
	/**
	 * 表名
	 */
	private String table;
	/**
	 * 列信息
	 */
	private String column;
	
}
