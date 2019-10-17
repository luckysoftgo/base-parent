package com.application.base.util.xml;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author : 孤狼
 * @NAME: ColumnInfo
 * @DESC: 列的描述信息
 **/
@Data
@NoArgsConstructor
public class ColumnInfo implements Serializable {
	/**
	 * 列名
	 */
	private String name;
	/**
	 * 已经定义好的列名
	 */
	private String owner;
	/**
	 * 列类型
	 */
	private String type;
	/**
	 * 列长度
	 */
	private String length;
	/**
	 * 默认值
	 */
	private String defaultValue;
	/**
	 * 字段描述
	 */
	private String comments;
	/**
	 * 是否必填
	 */
	private String required;
	
}
