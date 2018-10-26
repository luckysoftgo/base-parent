package com.application.base.cache.codis.architecture.util;

import com.application.base.utils.common.BaseStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * @desc 读取配置文件
 * @author 孤狼
 */
public class PropertiesLoader {

	private static Logger log = LoggerFactory.getLogger(PropertiesLoader.class);

	/**
	 * 获取配置文件信息
	 * @param path
	 * @param byPath
	 * @return
	 */
	public static Properties load(String path, boolean byPath) {
		if (!BaseStringUtil.isEmpty(path)) {
			if (byPath) {
				File file = new File(path);
				if (file.isFile()) {
					try {
						Properties pros = new Properties();
						InputStream in = new FileInputStream(file);
						pros.load(in);
						in.close();
						return pros;
					} catch (FileNotFoundException e) {
						log.error("load file {} error,file is not exit!", path);
					} catch (IOException e) {
						log.error("properties file {} formate error,please check !" + path);
					}
				}
			}
			else {
				InputStream in = PropertiesLoader.class.getClassLoader().getResourceAsStream(path);
				Properties pros = new Properties();
				try {
					pros.load(in);
					in.close();
					return pros;
				}
				catch (Exception e) {
					log.error("properties file {} formate error,please check !", path);
				}
			}
		}
		return null;
	}
}
