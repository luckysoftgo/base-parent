package com.application.base.utils.json;

import java.util.Objects;

/**
 *@desc json 处理的json串
 *@author 孤狼
 */
public class ComJsonUtil {
	
	/**
	 * 判断String是否是空 为空的情况包含：对象为空,为空字符串(""),为字符串"null"
	 * @param value
	 * @return
	 */
	public static boolean isNotBlank(Object value) {
		return !isBlank(value);
	}
	
	/**
	 * 判断String是否是空 为空的情况包含：对象为空,为空字符串(""),为字符串"null"
	 * @param value
	 * @return
	 */
	public static boolean isBlank(Object value) {
		if (null==value){
			return true;
		}
		String result = stringValue(value);
		String nullStr = "null",bigNullStr = "NULL";
		if ("".equals(result) || result.length()==0 || nullStr.equals(result) || bigNullStr.equals(result)){
			return true;
		}
		return  false;
	}
	
	/**
	 * 转换成字符串值.
	 * @param value
	 * @return
	 */
	public static String stringValue(Object value) {
		String result = Objects.toString(value, "");
		return result.trim();
	}
	
}
