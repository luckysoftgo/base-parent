package com.application.base.cache.redis.api;

import java.util.Objects;

/**
 * @Author: 孤狼
 * @desc: 顶级的接口,定义通用的接口或服务实现.
 */
public interface ApiSession {
	
	/**
	 * 返回的标识
	 */
	long LOCK_SUCCESS = 1;
    
    /*
        EX seconds -- Set the specified expire time, in seconds.
        PX milliseconds -- Set the specified expire time, in milliseconds.
        NX -- Only set the key if it does not already exist.
        XX -- Only set the key if it already exist.
    */
	
	/**
	 * 这个参数我们填的是NX，意思是SET IF NOT EXIST,即当key不存在时,我们进行set操作;若key已经存在,则不做任何操作.
	 */
	String SET_IF_NOT_EXIST = "NX";
	/**
	 * 这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置（单位毫秒），具体时间由第五个参数决定
	 */
	String SET_WITH_EXPIRE_TIME = "PX";
	
	/**
	 * 默认时间设置,一天时间
	 */
	int DEFAULT_TIMEOUT = 60 * 60 * 24;
	
	/**
	 * 是否为空.
	 * @param value
	 * @return
	 */
	default boolean isEmpty(Object value) {
		if (null==value || "".equals(value)){
			return true;
		}
		String result = stringValue(value);
		String str1= "null";
		if ("".equals(result) || result.length()==0 || str1.equalsIgnoreCase(result)){
			return true;
		}
		return  false;
	}
	
	/**
	 * 转换成字符串值.
	 * @param value
	 * @return
	 */
	default String stringValue(Object value) {
		String result = Objects.toString(value, "");
		return result.trim();
	}
	
}
