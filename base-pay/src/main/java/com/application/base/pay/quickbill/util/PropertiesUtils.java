package com.application.base.pay.quickbill.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 孤狼
 */
public class PropertiesUtils {
    
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
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/99bill.properties");
                if (inputStream==null) {
                    inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("config/99bill.properties");
                }
                if (inputStream != null ) {
                    properties.load(inputStream);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String getValue(String status) {
        if (properties==null) {
            initProperties();
        }
         return properties.getProperty(status);
    }
    
    public static String getValue(String status,String defVal) {
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
