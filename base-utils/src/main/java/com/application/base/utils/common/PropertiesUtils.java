package com.application.base.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.*;

/**
 * @desc 读取配置文件.
 * @author 孤狼
 */
@SuppressWarnings("serial")
public class PropertiesUtils extends Properties{

    Logger logger = LoggerFactory.getLogger(PropertiesUtils.class.getName());
    
    /**
     * 存储 key 和 value 的值
     */
    private Map<String,String> properties = new HashMap<String,String>();
    /**
     * 获取urls
     */
    private List<String> urls = new ArrayList<String>();
    
    public PropertiesUtils() {}

    public String get(String key){
        return properties.get(key);
    }
    
    @SuppressWarnings("rawtypes")
	public String get(Enum key){
        return properties.get(key.name());
    }

    public List<String> getUrls() {
        return urls;
    }

    @SuppressWarnings("rawtypes")
	public void setUrls(List<String> urls) throws Exception{
        ClassLoader currentThreadClassLoader = Thread.currentThread().getContextClassLoader();
        for (String url : urls){
            logger.debug("[<=配置文件:{}=>]",url.substring(url.lastIndexOf(Constants.Separator.SLASH)+1,url.length()));
            InputStream inputStream  = currentThreadClassLoader.getResourceAsStream(url);
            if(inputStream == null) {
				inputStream = getClass().getResourceAsStream(url);
			}
            load(inputStream);
            Enumeration names = propertyNames();
            while (names.hasMoreElements()){
                String name;
                properties.put(name = ((String) names.nextElement()),getProperty(name));
                logger.debug("[{}:{}]",name,getProperty(name));
            }
        }
        this.urls = urls;
    }

}
