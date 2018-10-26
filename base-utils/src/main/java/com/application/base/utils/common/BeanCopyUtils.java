package com.application.base.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @desc 对象之间属性拷贝.
 * @author 孤狼
 */
public class BeanCopyUtils {
	
	static Logger logger = LoggerFactory.getLogger(BeanCopyUtils.class.getName());
	
	/**
	 * 利用反射实现对象之间属性复制
	 * @param source 源头
	 * @param desc 目标
	 */
	public static void copyProperties(Object source, Object desc){
		try {
			copyPropertiesExclude(source, desc, null);
		}catch (Exception ex){
			logger.error("对象属性拷贝异常,异常信息是:{}",ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * 利用反射实现对象之间属性复制
	 * @param source 源头
	 * @param desc 目标
	 * @param excludsArray 排除的列
	 */
	public static void copyPropertiesExcluds(Object source, Object desc, String[] excludsArray){
		try {
			copyPropertiesExclude(source, desc, excludsArray);
		}catch (Exception ex){
			logger.error("对象属性拷贝异常,异常信息是:{}",ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * 利用反射实现对象之间属性复制
	 * @param source 源头
	 * @param desc 目标
	 * @param includsArray 只拷贝指定的列
	 */
	public static void copyPropertiesIncluds(Object source, Object desc, String[] includsArray){
		try {
			copyPropertiesInclude(source, desc, includsArray);
		}catch (Exception ex){
			logger.error("对象属性拷贝异常,异常信息是:{}",ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * 复制对象属性
	 * @param source 源头
	 * @param desc 目标
	 * @param excludsArray 排除属性列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static void copyPropertiesExclude(Object source, Object desc, String[] excludsArray) throws Exception {
		List<String> excludesList = null;
		if(excludsArray != null && excludsArray.length > 0) {
			//构造列表对象
			excludesList = Arrays.asList(excludsArray);
		}
		List<Method> fromMethods = getObjMethods(source);
		List<Method>  toMethods = getObjMethods(desc);
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;
		for (int i = 0; i < fromMethods.size(); i++) {
			fromMethod = fromMethods.get(i);
			fromMethodName = fromMethod.getName();
			if (!fromMethodName.contains("get")) {
				continue;
			}
			//排除列表检测
			if(excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())) {
				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);
			if (toMethod == null){
				continue;
			}
			Object value = fromMethod.invoke(source, new Object[0]);
			if(value == null) {
				continue;
			}
			//集合类判空处理
			if(value instanceof Collection) {
				Collection newValue = (Collection) value;
				if(newValue.size() <= 0) {
					continue;
				}
			}
			toMethod.invoke(desc, new Object[] {value});
		}
	}
	
	/**
	 * 对象属性值复制，仅复制指定名称的属性值
	 * @param source 源头
	 * @param desc 目标
	 * @param includsArray
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static void copyPropertiesInclude(Object source, Object desc, String[] includsArray) throws Exception {
		List<String> includesList = null;
		if(includsArray != null && includsArray.length > 0) {
			//构造列表对象
			includesList = Arrays.asList(includsArray);
		} else {
			return;
		}
		List<Method> fromMethods = getObjMethods(source);
		List<Method>  toMethods = getObjMethods(desc);
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;
		for (int i = 0; i < fromMethods.size(); i++) {
			fromMethod = fromMethods.get(i);
			fromMethodName = fromMethod.getName();
			if (!fromMethodName.contains("get")) {
				continue;
			}
			//排除列表检测
			String str = fromMethodName.substring(3);
			if (!includesList.contains(str.substring(0, 1).toLowerCase() + str.substring(1))) {
				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);
			if (toMethod == null) {
				continue;
			}
			Object value = fromMethod.invoke(source, new Object[0]);
			if (value == null){
				continue;
			}
			//集合类判空处理
			if(value instanceof Collection) {
				Collection newValue = (Collection)value;
				if(newValue.size() <= 0) {
					continue;
				}
			}
			toMethod.invoke(desc, new Object[] {value});
		}
	}
	
	/**
	 * 获得返回的 Method
	 * @param object
	 * @return
	 */
	private static List<Method> getObjMethods(Object object){
		List<Method> methodList = new ArrayList<>();
		Class clazz = object.getClass();
		while (clazz != null){
			Method[] methods = clazz.getDeclaredMethods();
			methodList.addAll(Arrays.asList(methods));
			clazz = clazz.getSuperclass();
		}
		return methodList;
	}
	
	
	/**
	 * 从方法数组中获取指定名称的方法
	 * @param methods
	 * @param name
	 * @return
	 */
	public static Method findMethodByName(List<Method> methods, String name) {
		for (int j = 0; j < methods.size(); j++) {
			Method method = methods.get(j);
			if (method.getName().equals(name)) {
				return method;
			}
		}
		return null;
	}
}
