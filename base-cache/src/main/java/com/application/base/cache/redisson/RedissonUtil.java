package com.application.base.cache.redisson;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * @desc 工具类使用.
 * @author 孤狼.
 */
public class RedissonUtil {
	
	private static String NULL = "";
	
	/**
	 * 换行符操作.
	 *
	 * @return
	 */
	public static String getLineSep() {
		return System.getProperties().getProperty("line.separator");
	}
	
	/**
	 * 文件链接符.
	 *
	 * @return
	 */
	public static String getFileSep() {
		return System.getProperties().getProperty("file.separator");
	}
	
	/**
	 * 获得系统的名称.
	 *
	 * @return
	 */
	public static String getOSName() {
		return System.getProperties().getProperty("os.name");
	}
	
	/**
	 * 获得主机名称.
	 *
	 * @return
	 */
	public static String getHostName() {
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			return addr.getHostName().toString();
		} catch (UnknownHostException e) {
			return "";
		}
	}
	
	/**
	 * 得到字符串信息
	 *
	 * @param value
	 * @return
	 */
	public static String stringValue(Object value) {
		String result = Objects.toString(value, "");
		return result.trim();
	}
	
	/**
	 * 得到数字信息
	 *
	 * @param value
	 * @return
	 */
	public static String numberValue(Object value) {
		String result = Objects.toString(value, "0");
		return result.trim();
	}
	
	/**
	 * 判断为空.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Object value) {
		if (null == value) {
			return true;
		}
		String result = stringValue(value);
		String str1 = "null";
		if (NULL.equals(result) || result.length() == 0 || str1.equalsIgnoreCase(result)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断不为空.
	 *
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(Object value) {
		return !isEmpty(value);
	}
	
}
