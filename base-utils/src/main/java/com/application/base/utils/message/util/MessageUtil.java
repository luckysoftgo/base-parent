package com.application.base.utils.message.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *@desc 配置文件信息读取.
 * @author 孤狼
 */
public class MessageUtil {

	private static Logger logger = LoggerFactory.getLogger(MessageUtil.class.getName());
    
    /**
     * 属性配置文件
     */
    private static Properties properties = null;
    
    /**
     * 初始化配置文件.
     */
    public static void initProperties() {
        if (properties==null) {
            properties = new Properties();
            InputStream inputStream = null;
            try {
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/message.properties");
                if (inputStream==null) {
                    inputStream = MessageUtil.class.getClassLoader().getResourceAsStream("config/message.properties");
                }
                if (inputStream != null ) {
                    properties.load(inputStream);
                }
            }
            catch (IOException e) {
            	logger.error("读取配置文件,获取配置信息失败",e);
                e.printStackTrace();
            }
        }
    }
    
    public static String getMsgVal(String status) {
        if (properties==null) {
            initProperties();
        }
         return properties.getProperty(status);
    }
    
    public static String getMsgVal(String status,String defVal) {
        if (properties==null) {
            initProperties();
        }
         return properties.getProperty(status,defVal);
    }
    
    public static String getProperty(String status) {
        if (properties==null) {
            initProperties();
        }
         return properties.getProperty(status);
    }
    
    public static String getProperty(String status,String defVal) {
        if (properties==null) {
            initProperties();
        }
         return properties.getProperty(status,defVal);
    }
    
}
