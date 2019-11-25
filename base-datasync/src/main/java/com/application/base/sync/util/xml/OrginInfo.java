package com.application.base.sync.util.xml;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 孤狼
 * @NAME: OrginInfo
 * @DESC: 获得 orgin 的信息.
 **/
@Data
@NoArgsConstructor
public class OrginInfo {
	
	/**
	 * 源描述.
	 */
	private String sourceDesc;
	
	/**
	 * 请求地址.
	 */
	private String url;
	/**
	 * 扩展的url地址.
	 */
	private String extendUrl;
	/**
	 * 请求的用户.
	 */
	private String clientId;
	
	/**
	 * 请求的密码.
	 */
	private String clientSecret;
	
	/**
	 * 鉴权地址(重启接口).
	 */
	private String reqUrl;
	
	/**
	 * 用户类型(重启接口)..
	 */
	private String ctype;
	
	/**
	 * 令牌.
	 */
	private String token;
	/**
	* 查询关键字值
	*/
	private String keywordValue;
}
