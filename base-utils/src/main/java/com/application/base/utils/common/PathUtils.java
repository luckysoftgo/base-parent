package com.application.base.utils.common;

import java.io.File;


/**
 * @desc 路径处理工具类
 * @author 孤狼
 */
public class PathUtils {
	
	final static String TAG = ":";
	/**
	 *
	 * @param str
	 * @param param
	 * @return
	 */
	private static String splitString(String str, String param) {
		String result = str;
		if (str.contains(param)) {
			int start = str.indexOf(param);
			result = str.substring(0, start);
		}

		return result;
	}
	
	/**
	 *获取classpath1
	 * @return
	 */
	public static String getClasspath(){
		String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../").replaceAll("file:/", "").replaceAll("%20", " ").trim();	
		if(path.indexOf(TAG) != 1){
			path = File.separator + path;
		}
		return path;
	}
	
	/**
	 *获取classpath2
	 * @return
	 */
	public static String getClassResources(){
		String path =  (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:/", "").replaceAll("%20", " ").trim();	
		if(path.indexOf(TAG) != 1){
			path = File.separator + path;
		}
		return path;
	}
	
}
