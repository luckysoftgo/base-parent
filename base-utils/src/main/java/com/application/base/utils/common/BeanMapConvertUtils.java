package com.application.base.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 *@desc 对象 和 Map 的转换
 *@author 孤狼
 */
public class BeanMapConvertUtils {
	
	static Logger logger = LoggerFactory.getLogger(BeanMapConvertUtils.class.getName());
	
	/**
	 * Map转换成对象
	 * @param type
	 * @param source
	 * @return
	 */
	public static Object convertMap2Bean(Class type, Map<String, Object> source) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			Object obj = type.newInstance();
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (source.containsKey(propertyName)) {
					Object value = source.get(propertyName);
					Object[] args = new Object[1];
					args[0] = value;
					descriptor.getWriteMethod().invoke(obj, args);
				}
			}
			return obj;
		}
		catch (Exception ex) {
			logger.error("Map转换成Bean对象异常了,异常信息是:{}",ex.getMessage());
		}
		return null;
	}

	
	/**
	 * 对象转换成 Map
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> convertBean2Map(Object bean) {
		try {
			Class<? extends Object> type = bean.getClass();
			Map<String,Object> returnMap = new HashMap<>(Constants.MapSize.MAP_MIN_SIZE);
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!"class".equals(propertyName)) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (!isEmpty(result)) {
						returnMap.put(propertyName, result);
					}
				}
			}
			return returnMap;
		}
		catch (Exception ex) {
			logger.error("Bean转换成Map对象异常了,异常信息是:{}",ex.getMessage());
		}
		return null;
	}
	
	/**
	 * 是否为空.
	 * @param value
	 * @return
	 */
	private static boolean isEmpty(Object value) {
		if (null==value){
			return true;
		}
		String result = stringValue(value);
		String str1= "null";
		if ("".equals(result) || result.length()==0 || str1.equalsIgnoreCase(result)){
			return true;
		}
		return  false;
	}
	
	/**
	 * 转换成字符串值.
	 * @param value
	 * @return
	 */
	private static String stringValue(Object value) {
		String result = Objects.toString(value, "");
		return result.trim();
	}
}
