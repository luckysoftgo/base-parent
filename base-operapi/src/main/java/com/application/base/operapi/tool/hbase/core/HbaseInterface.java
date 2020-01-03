package com.application.base.operapi.tool.hbase.core;

import java.io.IOException;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: HbaseInterface
 * @DESC: 接口定义.
 **/
public interface HbaseInterface {
	/**
	 * 判断表是否存在.
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	<T>boolean hasTable(Class<T> clazz) ;
	
	/**
	 * 创建表结构
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	<T>boolean createTable(Class<T> clazz);
	
	/**
	 * 刪除表結構.
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	<T>boolean deleteTable(Class<T> clazz) ;
	
	/**
	 * 放入数据.
	 * @param t
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	<T>boolean put(T t) ;
	
	/**
	 * 批量放入数据.
	 * @param clazz
	 * @param list
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	<T> boolean putBatch(Class<T> clazz, List<T> list) throws IOException;
	
	/**
	 * 获取数据
	 * @param rowkey
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	<T> T get(Object rowkey, Class<T> clazz) ;
	
	/**
	 * 获取数据.
	 * @param rowkey
	 * @param clazz
	 * @param columns
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	<T> T get(Object rowkey, Class<T> clazz, List<String> columns) ;
	
	/**
	 * 获取集合数据
	 * @param startrowkey
	 * @param stoprowkey
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	<T> List<T> scan(Object startrowkey, Object stoprowkey, Class<T> clazz) ;
	
	/**
	 * 获取集合数据
	 * @param startrowkey
	 * @param stoprowkey
	 * @param clazz
	 * @param columns
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	<T> List<T> scan(Object startrowkey, Object stoprowkey, Class<T> clazz, List<String> columns) ;
	
	/**
	 * 删除数据
	 * @param rowkey
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	<T> boolean delete(Object rowkey, Class<T> clazz) ;
	
	/**
	 * 批量删除数据.
	 * @param clazz
	 * @param list
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	<T> boolean deleteBatch(Class<T> clazz, List<T> list) throws IOException;
	
	/**
	 * 是否存在行
	 * @param rowkey
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	<T> boolean hasRow(Object rowkey, Class<T> clazz) ;
}
