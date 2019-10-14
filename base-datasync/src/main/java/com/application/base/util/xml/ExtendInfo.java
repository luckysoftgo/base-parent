package com.application.base.util.xml;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : 孤狼
 * @NAME: ExtendInfo
 * @DESC: 基础信息.
 **/
@Data
@NoArgsConstructor
public class ExtendInfo implements Serializable {
	
	/**
	 * 具体请求的接口(可能相同：国票接口).
	 */
	private String apiKey;
	
	/**
	 * 对应解析的类路径.
	 */
	private String classPath;
	
	/**
	 * 唯一标识
	 */
	private String setttingId;
	
	/**
	 * 表名
	 */
	private String[] tableName;
	
}
