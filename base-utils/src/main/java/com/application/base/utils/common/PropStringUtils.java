package com.application.base.utils.common;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * @desc 读取配置文件信息
 * @author 孤狼
 */
public class PropStringUtils {
	
	/**
	 * 属性文件的文件地址.
	 */
	public static String propsFilePath = "config/config.properties";
	
	/**
	 * 空字符串定义.
	 */
	private static String NULL = "";
	
	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(PropStringUtils.getValue("appId","config/wechat.properties"));
		
		Properties prop = new Properties();
		InputStream in = PropStringUtils.class.getClassLoader().getResourceAsStream(propsFilePath);
		try {
			if (in==null){
				in=Thread.currentThread().getContextClassLoader().getResourceAsStream(propsFilePath);
			}
			if (in!=null){
				prop.load(in);
			}
			Iterator<Entry<Object, Object>> itr = prop.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<Object, Object> e = (Entry<Object, Object>) itr.next();
				System.out.println((e.getKey().toString() + "=" + e.getValue().toString()));
			}
		} catch (Exception e) {
			System.out.println("测试失败!");
		}
		System.out.println("------------------------------------------------------");
		Map<String, String> resultMap = getValues(propsFilePath);
		for (Map.Entry<String, String> entry:resultMap.entrySet()) {
			System.out.println("key = "+entry.getKey()+",value="+entry.getValue());
		}
	}
	
	/**
	 * 获得 key - value
	 * @param propPath:文件夹的路径
	 * @return
	 */
	public static Map<String, String> getValues(String propPath) {
		Properties prop = getProperties(propPath);
		return getPropValue(prop);
	}
	
	/**
	 * 获得 key - value
	 * @param filePath:文件夹的路径.
	 * @param propsFileName:属性文件.
	 * @return
	 */
	public static Map<String, String> getValues(String filePath,String propsFileName) {
		Properties prop = getProperties(filePath,propsFileName);
		return getPropValue(prop);
	}
	
	/**
	 * 获取properties资源信息
	 * @param prop
	 * @return
	 */
	private static Map<String, String> getPropValue(Properties prop) {
		Map<String, String> resultMap = new HashMap<>(16);
		if (prop != null) {
			Iterator<Entry<Object, Object>> itr = prop.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<Object, Object> e = (Entry<Object, Object>) itr.next();
				resultMap.put(e.getKey().toString(), e.getValue().toString());
			}
		}
		return resultMap;
	}
	
	/**
	 * 获得 value
	 * @param key
	 * @param propPath:文件夹的路径
	 * @return
	 */
	public static String getValue(String key,String propPath) {
		Properties prop = getProperties(propPath);
		String result = null;
		if (prop!=null) {
			result = prop.getProperty(key);
		}
		return result;
	}
	
	/**
	 * 获得 value
	 * @param key
	 * @param propPath:文件夹的路径
	 * @return
	 */
	public static String getProperty(String key,String propPath) {
		Properties prop = getProperties(propPath);
		String result = null;
		if (prop!=null) {
			result = prop.getProperty(key);
		}
		return result;
	}
	
	/**
	 * 获得 value
	 * @param key
	 * @param defaultVal
	 * @param propPath:文件夹的路径
	 * @return
	 */
	public static String getValue(String key, String defaultVal,String propPath) {
		Properties prop = getProperties(propPath);
		String result = null;
		if (prop!=null) {
			result = prop.getProperty(key, defaultVal);
		}
		return result;
	}
	
	/**
	 * 获得 value
	 * @param key
	 * @param defaultVal
	 * @param propPath:文件夹的路径
	 * @return
	 */
	public static String getProperty(String key, String defaultVal,String propPath) {
		Properties prop = getProperties(propPath);
		String result = null;
		if (prop!=null) {
			result = prop.getProperty(key, defaultVal);
		}
		return result;
	}
	
	/**
	 * 获得 value
	 * @param key
	 * @param defaultVal
	 * @param filePath
	 * @param propsFileName
	 * @return
	 */
	public static String getValue(String key, String defaultVal,String filePath,String propsFileName) {
		Properties prop = getProperties(filePath, propsFileName);
		String result = null;
		if (prop!=null) {
			result = prop.getProperty(key, defaultVal);
		}
		return result;
	}
	
	/**
	 * 返回　Properties
	 * @param filePath:文件夹的路径
	 * @return
	 */
	public static Properties getProperties(String filePath){
		Properties prop = new Properties();
		try {
			InputStream in = PropStringUtils.class.getClassLoader().getResourceAsStream(filePath);
			if (in==null){
				in=Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
			}
			if (in!=null){
				prop.load(in);
			}
		} catch (Exception e) {
			return null;
		}
		return prop;
	}
	
	/**
	 * 返回　Properties
	 * @param fileName:文件夹的值
	 * @param propsFileName:props文件名字
	 * @return
	 */
	public static Properties getProperties(String fileName,String propsFileName){
		Properties prop = new Properties();
		try {
			InputStream in = PropStringUtils.class.getClassLoader().getResourceAsStream(fileName+File.separator+propsFileName);
			if (in==null){
				in=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName+File.separator+propsFileName);
			}
			if (in!=null){
				prop.load(in);
			}
		} catch (Exception e) {
			return null;
		}
		return prop;
	}
	
	/**
	 * 给配置文件添加属性.
	 * @param key:key
	 * @param value:value
	 * @param filePath:文件夹的路径
	 */
	public static boolean modifyProperties(String key, String value,String filePath) {
		try {
			// 从输入流中读取属性列表（键和元素对）
			Properties prop = getProperties(filePath);
			prop.setProperty(key, value);
			String path = PropStringUtils.class.getResource(filePath).getPath();
			FileOutputStream outputFile = new FileOutputStream(path);
			prop.store(outputFile, "modify");
			outputFile.close();
			outputFile.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 给配置文件添加属性.
	 * @param key:key
	 * @param value:value
	 * @param fileName:文件夹的值
	 * @param propsFileName:props文件名字
	 */
	public static void modifyProperties(String key, String value,String fileName,String propsFileName) {
		try {
			// 从输入流中读取属性列表（键和元素对）
			Properties prop = getProperties(fileName,propsFileName);
			prop.setProperty(key, value);
			String path = PropStringUtils.class.getResource(File.separator+fileName+File.separator+propsFileName).getPath();
			FileOutputStream outputFile = new FileOutputStream(path);
			prop.store(outputFile, "modify");
			outputFile.close();
			outputFile.flush();
		} catch (Exception e) {
			System.out.println("修改"+propsFileName+"文件属性失败!"+e.getLocalizedMessage());
		}
	}
	
	/**
	 * 获得系统的属性文件存放的位置
	 * @param folderName String 属性文件所属的文件夹名称
	 * @return String
	 */
	public String getConfigPath(String folderName){
		//获得系统的路径
		String filePath = System.getProperty("user.dir");
		if(!isEmpty(folderName)){
			filePath += File.separator +folderName;
		}
		filePath += File.separator;
		return filePath;
	}
	
	/**
	 * 判断为空.
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Object value) {
		if (null==value){
			return true;
		}
		String result = Objects.toString(value, NULL);
		String str1= "null";
		if (NULL.equals(result) || result.length()==0 || str1.equalsIgnoreCase(result)){
			return true;
		}
		return  false;
	}
}
