package com.application.base.sync.util.xml;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : 孤狼
 * @NAME: BaseInfo
 * @DESC: 基础信息.
 **/
@Data
@NoArgsConstructor
public class BaseInfo implements Serializable {
	
	/**
	 * 具体请求的接口(可能相同：国票接口).
	 */
	private String apiKey;
	
	/**
	 * 接口解析项目(可能相同天眼查接口).
	 */
	private String key;
	
}
