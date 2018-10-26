package com.application.base.utils.common;

import com.application.base.utils.json.JsonConvertUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @desc 默认时间的处理
 * @author 孤狼
 */
public class BaseStringUtil {

	private static String ZERO = "0";
	private static String NULL = "";
	private static String DOT = ".";
	
	/**
	 * 转换成字符串值.
	 * @param value
	 * @return
	 */
	public static String stringValue(Object value) {
		String result = Objects.toString(value, NULL);
		return result.trim();
	}
	
	/**
	 * 转换为 int
	 * @param value
	 * @return
	 */
	public static int numberValue(Object value) {
		return intValue(value);
	}
	
	/**
	 * 转换为 int
	 * @param value
	 * @return
	 */
	public static int intValue(Object value) {
		String valueStr = Objects.toString(NULL.equals(value) ? ZERO : value, ZERO);
		return Integer.valueOf(valueStr.contains(DOT)
				? valueStr.substring(0, Objects.toString(value, ZERO).indexOf(DOT)) : valueStr);
	}
	
	/**
	 * 是否为空.
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Object value) {
		if (null==value){
			return true;
		}
		String result = stringValue(value);
		String str1= "null";
		if (NULL.equals(result) || result.length()==0 || str1.equalsIgnoreCase(result)){
			return true;
		}
		return  false;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isObjEmpty(Object value) {
		if (value == null) {
			return true;
		}
		if (value instanceof String){
			return ((String) value).length() == 0;
		}
		if (value instanceof Collection){
			return ((Collection<? extends Object>) value).size() == 0;
		}
		if (value instanceof Map){
			return ((Map<? extends Object, ? extends Object>) value).size() == 0;
		}
		if (value instanceof CharSequence){
			return ((CharSequence) value).length() == 0;
		}
		// These types would flood the log
		// Number covers: BigDecimal, BigInteger, Byte, Double, Float, Integer, Long, Short
		if (value instanceof Boolean){
			return false;
		}
		if (value instanceof Number) {
			return false;
		}
		if (value instanceof Character) {
			return false;
		}
		if (value instanceof java.util.Date) {
			return false;
		}
		return false;
	}
	
	/**
	 * 是否为空.
	 * @param value
	 * @return
	 */
	public static boolean isJsonEmpty(Object value) {
		if (null==value || "".equals(value)){
			return true;
		}
		if (value.equals(JsonConvertUtils.EMPTY_JSON_ARRAY)){
			return true;
		}
		if (value.equals(JsonConvertUtils.EMPTY_JSON)){
			return true;
		}
		return false;
	}
	
	/**
	 * 不为空.
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(Object value) {
		return  !isEmpty(value);
	}
	
}
