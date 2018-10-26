package com.application.base.core.datasource.impl.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 内存存储对象的 map,只操作本应用中的数据连接对象.
 * 主要作用：省去多次 new SqlSessionFactory 和 new SqlSession 的操作,提高系统的性能.
 * @author 孤狼
 */
public class ObjectMapUtil {

	/**
	 * 存储用到的对象.
	 */
	public static Map<String, SqlSessionFactory> sessionFactoryKey = new ConcurrentHashMap<String,SqlSessionFactory>(); 
	public static Map<SqlSessionFactory, String> sessionFactoryVal = new ConcurrentHashMap<SqlSessionFactory, String>(); 

	/**
	 * 存储用到的对象.
	 */
	public static Map<String, SqlSession> sessionKey = new ConcurrentHashMap<String, SqlSession>(); 
	public static Map<SqlSession, String> sessionVal = new ConcurrentHashMap<SqlSession, String>(); 
	
}
