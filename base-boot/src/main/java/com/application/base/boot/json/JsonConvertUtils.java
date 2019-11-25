package com.application.base.boot.json;

import com.application.base.boot.json.adapter.GsonMapTypeAdapter;
import com.application.base.boot.json.adapter.SqlDateAdapter;
import com.application.base.boot.json.adapter.SqlTimestampAdapter;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.*;

/**
 * @desc json字符串处理.
 * @author 孤狼
 */
public class JsonConvertUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonConvertUtils.class);
	
	/**
	 * 空值
	 */
	public static final String EMPTY = "";
	/**
	 * 空的 {@code JSON} 数据 - <code>"{}"</code>。
	 */
	public static final String EMPTY_JSON = "{}";
	/**
	 * 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}
	 */
	public static final String EMPTY_JSON_ARRAY = "[]";
	/**
	 * 默认的 {@code JSON} 完整日期/时间字段的格式化模式。
	 */
	public static final String DEFAULT_ALL_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	/**
	 * 默认的 {@code JSON} 日期/时间字段的格式化模式。
	 */
	public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 *默认的 {@code JSON} 日期字段的格式化模式。
	 */
	public static final String DEFAULT_DATE_DAY_PATTERN = "yyyy-MM-dd";
	/**
	 *默认的 {@code JSON} 日期字段的格式化模式。
	 */
	public static final String DEFAULT_DATE_SECOND_PATTERN = "HH:mm:ss";
	/**
	 * 默认的 {@code JSON} 日期/时间/小时/分钟字段的格式化模式。
	 */
	public static final String DEFAULT_DATE_NO_SECOND = "yyyy-MM-dd HH:mm";
	
	/**
	 * {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.0}
	 */
	public static final Double SINCE_VERSION_10 = 1.0d;
	/**
	 * {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.1}
	 */
	public static final Double SINCE_VERSION_11 = 1.1d;
	/**
	 * {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.2}
	 */
	public static final Double SINCE_VERSION_12 = 1.2d;
	
	/**
	 * String串null和""的互转功能
	 */
	private static final JsonAdapterFactory<String> STRING_NULL_CONVERTOR = new JsonAdapterFactory<String>();
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target) {
		return toBaseConvertJson(target, null, true, null, DEFAULT_DATE_TIME_PATTERN, false, false);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON}
	 * 格式的字符串。<strong>此方法会将String的null值输出为空字符串("")。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法将会String字段的 {@code null} 值字段转换为空字符串；其它对象字段的 {@code null} 值不变</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJsonHasNull(Object target) {
		return toBaseConvertJson(target, null, true, null, DEFAULT_DATE_TIME_PATTERN, false, true);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param datePattern
	 *            日期字段的格式化模式。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, String datePattern) {
		return toBaseConvertJson(target, null, false, null, datePattern, false, false);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param datePattern
	 *            日期字段的格式化模式。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJsonHasNull(Object target, String datePattern) {
		return toBaseConvertJson(target, null, false, null, datePattern, false, true);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Double version) {
		return toBaseConvertJson(target, null, false, version, DEFAULT_DATE_TIME_PATTERN, false, false);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJsonHasNull(Object target, Double version) {
		return toBaseConvertJson(target, null, false, version, DEFAULT_DATE_TIME_PATTERN, false, true);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType) {
		return toBaseConvertJson(target, targetType, false, null, DEFAULT_DATE_TIME_PATTERN, false, false);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJsonHasNull(Object target, Type targetType) {
		return toBaseConvertJson(target, targetType, false, null, DEFAULT_DATE_TIME_PATTERN, false, true);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param nullAble
	 *            是否排除为NULL的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, boolean nullAble) {
		return toBaseConvertJson(target, null, nullAble, null, DEFAULT_DATE_TIME_PATTERN, false, false);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param nullAble
	 *            是否排除为NULL的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJsonHasNull(Object target, boolean nullAble) {
		return toBaseConvertJson(target, null, nullAble, null, DEFAULT_DATE_TIME_PATTERN, false, true);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Double version, boolean excludesFieldsWithoutExpose) {
		return toBaseConvertJson(target, null, false, version, DEFAULT_DATE_TIME_PATTERN, excludesFieldsWithoutExpose, false);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJsonHasNull(Object target, Double version, boolean excludesFieldsWithoutExpose) {
		return toBaseConvertJson(target, null, false, version, DEFAULT_DATE_TIME_PATTERN, excludesFieldsWithoutExpose, true);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType, Double version) {
		return toBaseConvertJson(target, targetType, false, version, DEFAULT_DATE_TIME_PATTERN, false, false);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJsonHasNull(Object target, Type targetType, Double version) {
		return toBaseConvertJson(target, targetType, false, version, DEFAULT_DATE_TIME_PATTERN, false, false);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType, boolean excludesFieldsWithoutExpose) {
		return toBaseConvertJson(target, targetType, false, null, DEFAULT_DATE_TIME_PATTERN, excludesFieldsWithoutExpose, false);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJsonHasNull(Object target, Type targetType, boolean excludesFieldsWithoutExpose) {
		return toBaseConvertJson(target, targetType, false, null, DEFAULT_DATE_TIME_PATTERN, excludesFieldsWithoutExpose, true);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType, Double version, boolean excludesFieldsWithoutExpose) {
		return toBaseConvertJson(target, targetType, false, version, DEFAULT_DATE_TIME_PATTERN, excludesFieldsWithoutExpose,false);
	}
	
	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 *
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJsonHasNull(Object target, Type targetType, Double version, boolean excludesFieldsWithoutExpose) {
		return toBaseConvertJson(target, targetType, false, version, DEFAULT_DATE_TIME_PATTERN, excludesFieldsWithoutExpose,true);
	}
	
	/**
	 * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
	 * <p />
	 * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，曾通对象返回 <code>"{}"</code>； 集合或数组对象返回
	 * <code>"[]"</code></strong>
	 *
	 * @param target
	 *            目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param isSerializeNulls
	 *            是否序列化 {@code null} 值字段。
	 * @param version
	 *            字段的版本号注解。
	 * @param datePattern
	 *            日期字段的格式化模式。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @param isStringNull2Empty
	 *            是否将null值转换为空字符串。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toBaseConvertJson(Object target, Type targetType, boolean isSerializeNulls, Double version,
										   String datePattern, boolean excludesFieldsWithoutExpose, boolean isStringNull2Empty) {
		GsonBuilder builder = new GsonBuilder();
		if (ComJsonUtil.isBlank(target)) {
			return EMPTY_JSON;
		}
		// 防止将数据库中的特殊字符如&转码的问题
		builder.disableHtmlEscaping();

		if (isSerializeNulls) {
			builder.serializeNulls();
		}
		if (ComJsonUtil.isNotBlank(version)) {
			builder.setVersion(version.doubleValue());
		}
		if (ComJsonUtil.isBlank(datePattern)) {
			datePattern = DEFAULT_ALL_DATE_PATTERN;
		}
		builder.setDateFormat(datePattern);
		if (excludesFieldsWithoutExpose) {
			builder.excludeFieldsWithoutExposeAnnotation();
		}
		String result = EMPTY;
		if (isStringNull2Empty) {
			builder.registerTypeAdapterFactory(STRING_NULL_CONVERTOR);
		}
		Gson gson = builder.registerTypeAdapter(Timestamp.class, new SqlTimestampAdapter())
				.registerTypeAdapter(java.sql.Date.class, new SqlDateAdapter())
				.registerTypeAdapter(Object.class,new GsonMapTypeAdapter())
				.create();
		try {
			if (ComJsonUtil.isNotBlank(targetType)) {
				result = gson.toJson(target, targetType);
			}
			else {
				result = gson.toJson(target);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.warn("目标对象{}转换 JSON 字符串时,发生异常！", target.getClass().getName());
			if (target instanceof Collection || target instanceof Iterator || target instanceof Enumeration || target.getClass().isArray()) {
				result = EMPTY_JSON_ARRAY;
			}
			else {
				result = EMPTY_JSON;
			}
		}
		return result;
	}
	
	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 *
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		return fromBaseConvertJson(json, clazz, DEFAULT_DATE_TIME_PATTERN, false);
	}
	
	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象，但会把空字符串转为null。</strong>
	 *
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJsonHasNull(String json, Class<T> clazz) {
		return fromBaseConvertJson(json, clazz, DEFAULT_DATE_TIME_PATTERN, true);
	}
	
	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 *
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, TypeToken<T> token) {
		return fromBaseConvertJson(json, token, DEFAULT_DATE_TIME_PATTERN, false);
	}
	
	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 *
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJsonHasNull(String json, TypeToken<T> token) {
		return fromBaseConvertJson(json, token, DEFAULT_DATE_TIME_PATTERN, true);
	}
	
	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 *
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param datePattern
	 * 			   時間格式化串
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, Class<T> clazz,String datePattern) {
		return fromBaseConvertJson(json, clazz, datePattern, false);
	}
	
	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 *
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param datePattern
	 * 			   時間格式化串
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJsonHasNull(String json, Class<T> clazz,String datePattern) {
		return fromBaseConvertJson(json, clazz, datePattern, true);
	}
	
	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 *
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @param datePattern
	 * 			   時間格式化串
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, TypeToken<T> token,String datePattern) {
		return fromBaseConvertJson(json, token, datePattern, false);
	}
	
	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 *
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @param datePattern
	 * 			   時間格式化串
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJsonHasNull(String json, TypeToken<T> token,String datePattern) {
		return fromBaseConvertJson(json, token, datePattern, true);
	}
	
	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 *
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param datePattern
	 *            日期格式模式。
	 * @param isAdapter
	 *            是否将空字符串转为null。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromBaseConvertJson(String json, Class<T> clazz, String datePattern, boolean isAdapter) {
		GsonBuilder builder = new GsonBuilder();
		if (ComJsonUtil.isBlank(json)) {
			return null;
		}
		if (ComJsonUtil.isBlank(datePattern)) {
			datePattern = DEFAULT_ALL_DATE_PATTERN;
		}
		if (isAdapter) {
			builder.registerTypeAdapterFactory(STRING_NULL_CONVERTOR);
		}
		
		builder.setDateFormat(datePattern);
		
		Gson gson = builder.registerTypeAdapter(Timestamp.class, new SqlTimestampAdapter())
				.registerTypeAdapter(java.sql.Date.class, new SqlDateAdapter())
				.registerTypeAdapter(Object.class,new GsonMapTypeAdapter())
				.create();
		
		try {
			return gson.fromJson(json, clazz);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error(" {}无法转换为{} 对象!", json, clazz.getName());
			return null;
		}
	}
	
	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 *
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @param datePattern
	 *            日期格式模式。
	 * @param isAdapter
	 *            是否将空字符串转为null。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromBaseConvertJson(String json, TypeToken<T> token, String datePattern, boolean isAdapter) {
		GsonBuilder builder = new GsonBuilder();
		if (ComJsonUtil.isBlank(json)) {
			return null;
		}
		if (ComJsonUtil.isBlank(datePattern)) {
			datePattern = DEFAULT_ALL_DATE_PATTERN;
		}
		if (isAdapter) {
			builder.registerTypeAdapterFactory(STRING_NULL_CONVERTOR);
		}
		
		builder.setDateFormat(datePattern);
		
		Gson gson = builder.registerTypeAdapter(Timestamp.class, new SqlTimestampAdapter())
				.registerTypeAdapter(java.sql.Date.class, new SqlDateAdapter())
				.registerTypeAdapter(Object.class,new GsonMapTypeAdapter())
				.create();
		
		try {
			return gson.fromJson(json, token.getType());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.error("{}无法转换为{} 对象!", json, token.getRawType().getName());
			return null;
		}
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> List<T> fromListJson(String json, Class<T> clazz) {
		return fromBaseConvertListJson(json,clazz,DEFAULT_DATE_TIME_PATTERN,false);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> List<T> fromListJsonHasNull(String json, Class<T> clazz) {
		return fromBaseConvertListJson(json,clazz,DEFAULT_DATE_TIME_PATTERN,true);
	}
	
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> List<T> fromListJson(String json, TypeToken<T> token) {
		return fromBaseConvertListJson(json,token,DEFAULT_DATE_TIME_PATTERN,false);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> List<T> fromListJsonHasNull(String json, TypeToken<T> token) {
		return fromBaseConvertListJson(json,token,DEFAULT_DATE_TIME_PATTERN,true);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param datePattern
	 *            日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> List<T> fromListJson(String json, Class<T> clazz,String datePattern) {
		return fromBaseConvertListJson(json,clazz,datePattern,false);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param datePattern
	 *            日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> List<T> fromListJsonHasNull(String json, Class<T> clazz,String datePattern) {
		return fromBaseConvertListJson(json,clazz,datePattern,true);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            要转换的目标类。
	 * @param datePattern
	 *            日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> List<T> fromListJson(String json, TypeToken<T> token,String datePattern) {
		return fromBaseConvertListJson(json,token,datePattern,false);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            要转换的目标类。
	 * @param datePattern
	 *            日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> List<T> fromListJsonHasNull(String json, TypeToken<T> token,String datePattern) {
		return fromBaseConvertListJson(json,token,datePattern,true);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param datePattern
	 * 			  时间的格式化方式
	 * @param isAdapter
	 *            是否将空字符串转为null。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> List<T> fromBaseConvertListJson(String json, Class<T> clazz,String datePattern, boolean isAdapter) {
		GsonBuilder builder = new GsonBuilder();
		if (ComJsonUtil.isBlank(json)) {
			return null;
		}
		if (ComJsonUtil.isBlank(datePattern)) {
			datePattern = DEFAULT_ALL_DATE_PATTERN;
		}
		if (isAdapter) {
			builder.registerTypeAdapterFactory(STRING_NULL_CONVERTOR);
		}
		builder.setDateFormat(datePattern);
		
		Gson gson = builder.registerTypeAdapter(Timestamp.class, new SqlTimestampAdapter())
				.registerTypeAdapter(java.sql.Date.class, new SqlDateAdapter())
				.registerTypeAdapter(Object.class,new GsonMapTypeAdapter())
				.create();
		try {
			JsonArray array = new JsonParser().parse(json).getAsJsonArray();
			List<T> result = new ArrayList<>(array.size());
			for (final JsonElement element : array) {
				result.add(gson.fromJson(element, clazz));
			}
			return result;
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error("{}无法转换为{} 对象!", json, clazz.getName());
			return null;
		}
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            要转换的目标类。
	 * @param datePattern
	 * 			  时间的格式化方式
	 * @param isAdapter
	 *            是否将空字符串转为null。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> List<T> fromBaseConvertListJson(String json, TypeToken<T> token,String datePattern, boolean isAdapter) {
		GsonBuilder builder = new GsonBuilder();
		if (ComJsonUtil.isBlank(json)) {
			return null;
		}
		if (ComJsonUtil.isBlank(datePattern)) {
			datePattern = DEFAULT_ALL_DATE_PATTERN;
		}
		if (isAdapter) {
			builder.registerTypeAdapterFactory(STRING_NULL_CONVERTOR);
		}
		
		builder.setDateFormat(datePattern);
		
		Gson gson = builder.registerTypeAdapter(Timestamp.class, new SqlTimestampAdapter())
				.registerTypeAdapter(java.sql.Date.class, new SqlDateAdapter())
				.registerTypeAdapter(Object.class,new GsonMapTypeAdapter())
				.create();
		
		try {
			JsonArray array = new JsonParser().parse(json).getAsJsonArray();
			List<T> result = new ArrayList<>(array.size());
			for (final JsonElement element : array) {
				result.add(gson.fromJson(element, token.getType()));
			}
			return result;
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error("{}无法转换为{} 对象!", json, token.getRawType().getName());
			return null;
		}
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> T fromMapJson(String json, Class<T> clazz) {
		return fromBaseConvertMapJson(json,clazz,DEFAULT_DATE_TIME_PATTERN,false);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> T fromMapJsonHasNull(String json, Class<T> clazz) {
		return fromBaseConvertMapJson(json,clazz,DEFAULT_DATE_TIME_PATTERN,true);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> T fromMapJson(String json, TypeToken<T> token) {
		return fromBaseConvertMapJson(json,token,DEFAULT_DATE_TIME_PATTERN,false);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> T fromMapJsonHasNull(String json, TypeToken<T> token) {
		return fromBaseConvertMapJson(json,token,DEFAULT_DATE_TIME_PATTERN,true);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param datePattern
	 * 			  时间的格式化方式
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> T fromMapJson(String json, Class<T> clazz,String datePattern) {
		return fromBaseConvertMapJson(json,clazz,datePattern,false);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param datePattern
	 * 			  时间的格式化方式
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> T fromMapJsonHasNull(String json, Class<T> clazz,String datePattern) {
		return fromBaseConvertMapJson(json,clazz,datePattern,true);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            要转换的目标类。
	 * @param datePattern
	 * 			  时间的格式化方式
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> T fromMapJson(String json, TypeToken<T> token,String datePattern) {
		return fromBaseConvertMapJson(json,token,datePattern,false);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            要转换的目标类。
	 * @param datePattern
	 * 			  时间的格式化方式
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> T fromMapJsonHasNull(String json, TypeToken<T> token,String datePattern) {
		return fromBaseConvertMapJson(json,token,datePattern,true);
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param datePattern
	 * 			  时间的格式化方式
	 * @param isAdapter
	 *            是否将空字符串转为null。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> T fromBaseConvertMapJson(String json, Class<T> clazz,String datePattern, boolean isAdapter) {
		GsonBuilder builder = new GsonBuilder();
		if (ComJsonUtil.isBlank(json)) {
			return null;
		}
		//如果在序列化Map类型的对象时，如果Key是复杂数据类型（不是基本数据类型或者String，即自定义POJO）;
		builder.enableComplexMapKeySerialization();
		
		if (ComJsonUtil.isBlank(datePattern)) {
			datePattern = DEFAULT_ALL_DATE_PATTERN;
		}
		if (isAdapter) {
			builder.registerTypeAdapterFactory(STRING_NULL_CONVERTOR);
		}
		builder.setDateFormat(datePattern);
		
		Gson gson = builder.registerTypeAdapter(Timestamp.class, new SqlTimestampAdapter())
				.registerTypeAdapter(java.sql.Date.class, new SqlDateAdapter())
				.registerTypeAdapter(Object.class,new GsonMapTypeAdapter())
				.create();
		
		try {
			return gson.fromJson(json,clazz);
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error("{}无法转换为{} 对象!", json, clazz.getName());
			return null;
		}
	}
	
	/**
	 * json字符串转list对象
	 *
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            要转换的目标类。
	 * @param datePattern
	 * 			  时间的格式化方式
	 * @param isAdapter
	 *            是否将空字符串转为null。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> T fromBaseConvertMapJson(String json, TypeToken<T> token,String datePattern, boolean isAdapter) {
		GsonBuilder builder = new GsonBuilder();
		if (ComJsonUtil.isBlank(json)) {
			return null;
		}
		//如果在序列化Map类型的对象时，如果Key是复杂数据类型（不是基本数据类型或者String，即自定义POJO）;
		builder.enableComplexMapKeySerialization();
		
		if (ComJsonUtil.isBlank(datePattern)) {
			datePattern = DEFAULT_ALL_DATE_PATTERN;
		}
		if (isAdapter) {
			builder.registerTypeAdapterFactory(STRING_NULL_CONVERTOR);
		}
		
		builder.setDateFormat(datePattern);
		
		Gson gson = builder.registerTypeAdapter(Timestamp.class, new SqlTimestampAdapter())
				.registerTypeAdapter(java.sql.Date.class, new SqlDateAdapter())
				.registerTypeAdapter(Object.class,new GsonMapTypeAdapter())
				.create();
		
		try {
			return gson.fromJson(json,token.getType());
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error("{}无法转换为{} 对象!", json, token.getRawType().getName());
			return null;
		}
	}
	
}
