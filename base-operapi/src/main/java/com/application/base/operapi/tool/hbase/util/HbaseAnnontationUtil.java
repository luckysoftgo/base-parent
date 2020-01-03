package com.application.base.operapi.tool.hbase.util;


import com.application.base.operapi.tool.hbase.annotation.HbaseRowKey;
import com.application.base.operapi.tool.hbase.annotation.HbaseTable;

/**
 * @author : 孤狼
 * @NAME: HbaseAnnontationUtil
 * @DESC: annontion
 **/
public class HbaseAnnontationUtil {
	
	/**
	 * 获取表名
	 * @param clazz
	 * @return
	 */
	public static String getTableName(Class<?> clazz){
        return clazz.getAnnotation(HbaseTable.class).table();
    }
	
	/**
	 * 获取列簇名
	 * @param clazz
	 * @return
	 */
	public static String getColumnFamily(Class<?> clazz){
        return clazz.getAnnotation(HbaseTable.class).family();
    }
	
	/**
	 * 获得model的HbaseRowKey字段名
	 * @param clazz
	 * @return
	 */
	public static String getRowKey(Class clazz) {
        return AnnoatationsUtil.getFieldNameByFieldAnnoatation(clazz, HbaseRowKey.class);
    }
	
	/**
	 * 获得model的HbaseRowKey的值
	 * @param model
	 * @param <T>
	 * @return
	 */
	public static <T> Object getRowKeyValue(T model){
        return AnnoatationsUtil.getFieldValueByFieldAnnonation(model,HbaseRowKey.class);
    }

}
