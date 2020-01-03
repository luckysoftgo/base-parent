package com.application.base.operapi.tool.hbase.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: AnnoatationsUtil
 * @DESC: 标识
 **/
public class AnnoatationsUtil {
	
	/**
	 * 注解反射.
	 * @param clazz
	 * @param annon
	 * @return
	 */
	public static String getFieldNameByFieldAnnoatation(Class clazz, Class<? extends Annotation> annon) {
		String fieldName = null;
		Field[] fields = clazz.getDeclaredFields();
		Field[] columns = fields;
		int count = fields.length;
		
		for(int i = 0; i < count; ++i) {
			Field field = columns[i];
			if (field.isAnnotationPresent(annon)) {
				fieldName = field.getName();
				break;
			}
		}
		return fieldName;
	}
	
	/**
	 * 反射得到對象.
	 * @param model
	 * @param annon
	 * @param <T>
	 * @return
	 */
	public static <T> Object getFieldValueByFieldAnnonation(T model, Class<? extends Annotation> annon) {
		String fieldName = getFieldNameByFieldAnnoatation(model.getClass(), annon);
		return getFieldValueByFieldName(model, fieldName);
	}
	
	/**
	 * 反射值
	 * @param model
	 * @param fieldName
	 * @param <T>
	 * @return
	 */
	public static <T> Object getFieldValueByFieldName(T model, String fieldName) {
		return HbaseCommonUtil.getProperty(model, fieldName);
	}
	
	/**
	 * 反射所有的字段
	 * @param clazz
	 * @param annon
	 * @return
	 */
	public static List<Field> getFieldsBySpecAnnoatation(Class clazz, Class<? extends Annotation> annon) {
		List<Field> list = new ArrayList();
		Field[] fields = clazz.getDeclaredFields();
		Field[] columns = fields;
		int count = fields.length;
		for(int i = 0; i < count; ++i) {
			Field field = columns[i];
			if (field.isAnnotationPresent(annon)) {
				list.add(field);
			}
		}
		return list;
	}
	
	/**
	 * 属性为string
	 * @param clazz
	 * @param annon
	 * @return
	 */
	public static List<String> getFieldsBySpecAnnoatationWithString(Class clazz, Class<? extends Annotation> annon) {
		List<Field> fields = getFieldsBySpecAnnoatation(clazz, annon);
		List<String> results = new ArrayList();
		Iterator iterator = fields.iterator();
		while(iterator.hasNext()) {
			Field field = (Field)iterator.next();
			results.add(field.getName());
		}
		return results;
	}
	
}
