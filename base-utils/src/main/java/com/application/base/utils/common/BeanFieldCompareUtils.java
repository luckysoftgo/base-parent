package com.application.base.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;


/**
 * @desc 判断两个对象的属性差异
 * @author 孤狼
 */
public class BeanFieldCompareUtils {
	
	static Logger logger = LoggerFactory.getLogger(BeanFieldCompareUtils.class.getName());
	
	/**
	 * 获取两个对戏的不同: 沒有继承情况的存在.
	 * @param source
	 * @param target
	 * @return
	 */
	public static List<BeanCompared> comparedBeanChars(Object source, Object target){
		List<BeanCompared> resultList = new ArrayList<BeanCompared>();
		try {
			Class<?> clazz = source.getClass();
			Field[] fields = clazz.getDeclaredFields();
			resultList = comparedBeanChars(clazz, fields,source, target);
		} catch (Exception ex) {
			logger.error("获取属性对比异常,异常信息是:{}",ex.getMessage());
		}
		return  resultList;
	}
	
	/**
	 * 获取两个对戏的不同: 有继承情况的存在.
	 * @param source
	 * @param target
	 * @return
	 */
	public static List<BeanCompared> comparedObjectChars(Object source, Object target) {
		List<BeanCompared> resultList = new ArrayList<BeanCompared>();
		Map<Class<?>,Field[]> resultMap = getAllFields(source);
		try {
			if (resultMap!=null && resultMap.size()>0){
				for (Map.Entry<Class<?>,Field[]> entry : resultMap.entrySet()){
					Class<?> clazz = entry.getKey();
					Field[] fields = entry.getValue();
					resultList.addAll(comparedBeanChars(clazz,fields,source,target));
				}
			}
		}catch (Exception ex){
			logger.error("获取属性对比异常,异常信息是:{}",ex.getMessage());
		}
		return resultList;
	}
	
	/**
	 * 获得对象的 Field
	 * @param object
	 * @return
	 */
	public static Map<Class<?>,Field[]> getAllFields(Object object){
		Map<Class<?>,Field[]> resultMap = new HashMap<>(Constants.MapSize.MAP_MIN_SIZE);
		try {
			Class clazz = object.getClass();
			while (clazz != null){
				Field[] fields = clazz.getDeclaredFields();
				resultMap.put(clazz,fields);
				clazz = clazz.getSuperclass();
			}
		}catch (Exception ex){
			logger.error("获得对象的属性和类信息异常,错误信息是:{}",ex.getMessage());
		}
		return resultMap;
	}
	
	/**
	 * 获取两个对戏的不同: 沒有继承情况的存在.
	 * @param source
	 * @param target
	 * @return
	 */
	private static List<BeanCompared> comparedBeanChars(Class<?> clazz,Field[] fields,Object source, Object target) throws Exception{
		List<BeanCompared> resultList = new ArrayList<BeanCompared>();
		for (Field field : fields) {
			field.setAccessible(true);
			String beanProp = field.getName();
			Class<?> type = field.getType();
			//序列号不对比
			if ("serialVersionUID".equals(field.getName()) || beanProp.startsWith("FIELD")){
				continue;
			}
			PropertyDescriptor descriptor = new PropertyDescriptor(beanProp, clazz);
			Method getMethod = descriptor.getReadMethod();
			Object obj1 = getMethod.invoke(source);
			Object obj2 = getMethod.invoke(target);
			String sourceResult = stringValue(obj1);
			String targetResult = stringValue(obj2);
			if (!equals(sourceResult,targetResult)){
				BeanCompared compared = new BeanCompared();
				compared.setName(beanProp);
				compared.setType(type.getTypeName());
				if ("java.util.Date".equals(type.getName())) {
					compared.setOldVal(formatDate(obj1));
					compared.setNewVal(formatDate(obj2));
				}else {
					compared.setOldVal(sourceResult);
					compared.setNewVal(targetResult);
				}
				resultList.add(compared);
			}
		}
		return  resultList;
	}
	
	/**
	 *对比是否相同
	 * @param src
	 * @param des
	 * @return
	 */
	private static boolean equals(Object src, Object des) {
		return src.equals(des) || src == des;
	}
	
	/**
	 * 字符串值.
	 * @param value
	 * @return
	 */
	public static String stringValue(Object value) {
		String result = Objects.toString(value, "");
		return result.trim();
	}
	
	/**
	 * 格式化時間
	 * @param value
	 * @return
	 */
	public static String formatDate(Object value) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(value);
		}catch (Exception ex){
			return null;
		}
	}
	
}
