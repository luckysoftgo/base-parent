package com.application.base.core.utils;

import com.application.base.utils.common.BaseStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @desc 获取配置文件的信息
 * @author 孤狼
 */
public class PropertiesUtils {
	
	private static final String DEFAULT_ENCODING = "UTF-8";

	private static String[] resourcesPaths = new String[] { "/properties/config.properties" };

	protected static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

	private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	private static Properties properties;

	/**
	 * 载入多个properties文件, 相同的属性在最后载入的文件中的值将会覆盖之前的载入. 文件路径使用Spring Resource格式,
	 * 文件编码使用UTF-8.
	 * PropertyPlaceholderConfigurer
	 */
	public static Properties loadProperties(String... resourcesPaths) throws IOException {
		Properties props = new Properties();
		for (String location : resourcesPaths) {
			logger.debug("Loading properties file from:" + location);
			InputStream is = null;
			try {
				Resource resource = resourceLoader.getResource(location);
				is = resource.getInputStream();
				propertiesPersister.load(props, new InputStreamReader(is, DEFAULT_ENCODING));
			} catch (IOException ex) {
				logger.info("Could not load properties from classpath:" + location + ": " + ex.getMessage());
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
		return props;
	}

	public static Properties load(String path) {
		
		Properties p = new Properties();
		try {
			InputStream in = PropertiesUtils.class.getResourceAsStream(path);
			p.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}

	public static String getString(String key) {
		try {
			if (properties == null) {
				properties = PropertiesUtils.loadProperties(resourcesPaths);
			}
			return properties.getProperty(key).trim();
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 获取整型配置项.
	 *
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public static Integer getInt(String key, Integer defaultVal) {
		try {
			String valStr = getString(key);
			return Integer.parseInt(valStr.trim());
		} catch (Exception e) {
		}
		return defaultVal;
	}

	/**
	 * 获取布尔配置.
	 *
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public static boolean getBoolean(String key, boolean defaultVal) {
		try {
			String valStr = getString(key);
			return Boolean.parseBoolean(valStr.trim());
		} catch (Exception e) {
		}
		return defaultVal;
	}

	/**
	 * 获取字符型配置项.
	 *
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public static String getString(String key, String defaultVal) {
		String val = getString(key).trim();
		if (!BaseStringUtil.isEmpty(val)) {
			return val;
		}
		return defaultVal;
	}
}
