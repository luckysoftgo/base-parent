package com.application.base.core.utils;

import com.application.base.utils.common.BaseEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @desc 通用公共 Bean 工具类
 * @author 孤狼
 */
public class CommonBeanUtils {

	private static final Logger logger = LoggerFactory.getLogger(CommonBeanUtils.class.getName());

	static {
		// 在封装之前 注册转换器
		ConvertUtils.register(new DateTimeConverter(), Date.class);
	}

	/**
	 * 获取map中有用的值.
	 * @param inputMap
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	public static Map<String, Object> getValueMap(Map<String, Object> inputMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>(16);
		try {
			for (Map.Entry<String, Object>  entry : inputMap.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (StringUtils.isEmpty(value)) {
					continue;
				}
				if ("-1".equalsIgnoreCase(value.toString())) {
					continue;
				}
				resultMap.put(key, value);
			}
		} catch (Exception e) {
			throw e;
		}
		return resultMap;
	}
	
	/**
	 * 获取要操作的列
	 * @param clazz:要操作的列.
	 * @param coloums:要保留值的列.
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	public static <T extends BaseEntity> T getUpdateBeanInfo(Class<? extends BaseEntity> clazz, List<String> coloums) {
		logger.info("get Bean's info from " + clazz.getName() + " start ...");
		T instance = null;
		T returnInstance = (T) T.getSimpleInstance(clazz);
		try {
			instance = (T) T.getInstance(clazz);
			Map<String, Object > resultMap = BeanColumnUtil.getBeanValus(instance, coloums);
			BeanUtils.populate(returnInstance, resultMap);
		} catch (Exception e) {
			logger.error("get Bean's info from " + clazz.getName() + " failure ... " + e);
			e.printStackTrace();
		} finally {
			logger.info("get Bean's info from " + clazz.getName() + " end ... ");
		}
		return returnInstance;
	}

	
	
	/**
	 * map转换basePO
	 * @param map
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	public static <T extends BaseEntity> T transMap2BasePO(Map<String, Object> map, Class<? extends BaseEntity> clazz) {
		logger.info("trans Map to " + clazz.getName() + " start ...");
		T instance = null;
		if (map == null) {
			logger.warn("map is null. trans failure");
			return instance;
		}
		try {
			instance = (T) T.getInstance(clazz);
			BeanUtils.populate(instance, map);
		} catch (Exception e) {
			logger.error("trans Map to " + clazz.getName() + " failure ... " + e);
			e.printStackTrace();
		} finally {
			logger.info("trans Map to " + clazz.getName() + " end ... ");
		}
		return instance;
	}

	/**
	 * map转换po
	 * @param map
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("finally")
	public static <T extends BaseEntity> T transMap2BasePO(Map<String, Object> map, T po) {
		if (map == null || po == null) {
			logger.warn("map is null. trans failure");
			return null;
		}
		T newPo = po;
		try {
			newPo.setUpdateTime(new Date());
			BeanUtils.populate(newPo, map);
		} catch (Exception e) {
			logger.error("trans Map to " + po.getClass().getName() + " failure ... " + e);
			e.printStackTrace();
		} finally {
			logger.info("trans Map to " + po.getClass().getName() + " end ... ");
		}
		return newPo;
	}

	/**
	 * 基于反射 将map转换成javaBean
	 * @param map
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("finally")
	public static <T> T transMap2Bean(Map<String, Object> map, Class<T> clazz) {
		logger.info("trans Map to " + clazz.getName() + " start ...");
		T instance = null;
		if (map == null) {
			logger.warn("map is null. trans failure ...");
			return instance;
		}
		try {
			instance = clazz.newInstance();
			BeanUtils.populate(instance, map);
		} catch (Exception e) {
			logger.error("trans Map to " + clazz.getName() + " failure ... " + e);
			e.printStackTrace();
		} finally {
			logger.info("trans Map to " + clazz.getName() + " end ... ");
		}
		return instance;

	}

	/**
	 * 基于反射将javabean 转换成 Map<String, Object>
	 * @param obj
	 * @return
	 */
	@SuppressWarnings({ "unused", "finally" })
	public static Map<String, Object> transBean2Map(Object obj) {
		logger.info("trans " + obj.getClass().getName() + " to map start ...");
		Map<String, Object> map = new HashMap<String, Object>(16);
		if (obj == null) {
			logger.warn(obj.getClass().getName() + " instance is null ... ");
			return null;
		}
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!"class".equals(key)) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
					logger.info("property:" + key + " success success ... ");
				}
			}
		} catch (Exception e) {
			logger.error("trans" + obj.getClass().getName() + " to  Map failure ... " + e);
			e.printStackTrace();
		} finally {
			logger.info("trans " + obj.getClass().getName() + " to map start ...");
		}
		return map;
	}

	/**
	 * 获取可能为空的字符串的值，如果对象为空则返回空字符串，如果不为空则返回字符串值
	 * @param o
	 * @return
	 * String
	 *
	 */
	public static String getIfNullStringValue(Object o) {
		return o == null ? "" : o.toString();
	}

	/**
	 * 将sourceBean中与T同名的属性 set到T的实例中并返回T的实例
	 * @param clazz
	 * @param sourceBean
	 * @param <T>
	 * @param <S>
	 * @return
	 */
	@SuppressWarnings("finally")
	public static <T, S> T getBeanBySameProperty(Class<T> clazz, S sourceBean) {
		logger.debug("method : getBeanBySameProperty start");
		T instance = null;
		try {
			instance = clazz.newInstance();
			org.apache.commons.beanutils.BeanUtils.copyProperties(instance, sourceBean);
		} catch (Exception e) {
			logger.error("类：" + clazz.getName() + "创建实例失败...");
			e.printStackTrace();
		} finally {
			logger.debug("method : getBeanBySameProperty end");
		}
		return instance;
	}

	/**
	 * 将sourceBean中与T同名的属性 set到T的实例中并返回T的实例
	 * @param instance
	 * @param sourceBean
	 * @param <T>
	 * @param <S>
	 * @return
	 */
	@SuppressWarnings("finally")
	public static <T, S> T getBeanBySameProperty(T instance, S sourceBean) {
		logger.debug("method : getBeanBySameProperty start");
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(instance, sourceBean);
		} catch (Exception e) {
			logger.error("复制属性失败：" + e.getMessage());
			e.printStackTrace();
		} finally {
			logger.debug("method : getBeanBySameProperty end");
		}
		return instance;
	}
}

@SuppressWarnings({ "unchecked", "rawtypes" })
class DateTimeConverter implements Converter {

	private static final String DATE = "yyyy-MM-dd";
	private static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
	private static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

	
	@Override
	public Object convert(Class type, Object value) {
		return toDate(type, value);
	}

	public static Object toDate(Class type, Object value) {
		if (value == null || "".equals(value)) {
			return null;
		}
		if (value instanceof String) {
			String dateValue = value.toString().trim();
			int length = dateValue.length();
			if (type.equals(Date.class)) {
				try {
					DateFormat formatter = null;
					int num1= 10,num2=19,num3=23;
					if (length <= num1) {
						formatter = new SimpleDateFormat(DATE, new DateFormatSymbols(Locale.CHINA));
						return formatter.parse(dateValue);
					}
					if (length <= num2) {
						formatter = new SimpleDateFormat(DATETIME, new DateFormatSymbols(Locale.CHINA));
						return formatter.parse(dateValue);
					}
					if (length <= num3) {
						formatter = new SimpleDateFormat(TIMESTAMP, new DateFormatSymbols(Locale.CHINA));
						return formatter.parse(dateValue);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
}