package com.application.base.core.utils;

import java.lang.reflect.Field;
import java.util.*;

import com.application.base.utils.common.BaseStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @desc bean对象的操作类
 * @author 孤狼
 */
public class BeanColumnUtil {

	private static Logger logger = LoggerFactory.getLogger(BeanColumnUtil.class.getName());
	
	/**
	 * 字符校验.
	 * @param object 哪个bean对象.
	 * @param columns 校验哪些列.
	 * @param attachments 附件list校验.
	 */
	public static boolean validateEmpty(Object object, List<String> columns, List<Object> attachments){
		if (attachments==null || attachments.size()==0) {
			return true;
		}else{
			return validateEmpty(object, columns);
		}
	} 
	
	/**
	 * 获取数据
	 * @param data
	 * @param filterNames
	 * @return
	 */
	public static boolean validateEmpty(Object data, List<String> filterNames) {
		try {
			boolean result = false;
			Class<?> cls = data.getClass();
			Field[] fields = cls.getDeclaredFields();
			String name = null;
			for (Field field : fields) {
				name = field.getName();
				if (filterNames.contains(name)) {
					field.setAccessible(true);
					String val = Objects.toString(field.get(data),"");
					if (BaseStringUtil.isEmpty(val)) {
						result = true;
					}
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("非空验证失败了",e);
			return true;
		}
	}
	
	
	/**
	 * 获取传入的两个对象的value是否相同.
	 * 
	 * @param newData
	 * @param oldData
	 * @param filterNames
	 * @return
	 */
	public static List<?> diffValus(Object newData, Object oldData, List<String> filterNames) {
		List<?> resultData = new ArrayList<>();
		Map<String, Object> newMap = getBeanValus(newData, filterNames);
		Map<String, Object> oldMap = getBeanValus(oldData, filterNames);
		for (String column : filterNames) {
			String ndata = Objects.toString(newMap.get(column),"");
			ndata = dealDecimal(ndata);
			String odata = Objects.toString(oldMap.get(column),"");
			odata = dealDecimal(odata);
			if (!ndata.equals(odata)) {
				Object data = new Object();
				//TODO //两个类的字段的比较方式实现.
			}
		}
		return resultData;
	}

	/**
	 * 获取对象指定列的 value 集合
	 * @param data
	 * @param coloums
	 * @return
	 */
	public static Map<String, Object> getBeanValus(Object data,List<String> coloums) {
		try {
			Class<?> cls = data.getClass();
			Field[] fields = cls.getDeclaredFields();
			Map<String, Object> dataMap = new HashMap<String, Object>(16);
			String name = null;
			for (Field field : fields) {
				name = field.getName();
				if (coloums.contains(name)) {
					field.setAccessible(true);
					Object val = field.get(data);
					if (!BaseStringUtil.isEmpty(val)) {
						dataMap.put(name, val);
					}
				}
			}
			return dataMap;
		} catch (Exception e) {
			logger.error("获取信息异常了.");
			return null;
		}
	}
	
	/**
	 * 处理,"0"
	 * @param value
	 * @return
	 */
	private static String dealDecimal(String value) {
		if (BaseStringUtil.isEmpty(value)) {
			return "";
		}
		String tmpStr = ".";
		if (value.indexOf(tmpStr) > 0) {
			// 正则表达
			value = value.replaceAll("0+?$", "");
			value = value.replaceAll("[.]$", "");
		}
		return value;
	}
	
}
