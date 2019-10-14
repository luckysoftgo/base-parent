package com.application.base.core;

import com.application.base.util.sql.PkProvider;
import com.application.base.util.xml.ExtendInfo;

/**
 * @author : 孤狼
 * @NAME: DataParser
 * @DESC: 数据解析接口
 **/
public interface DataParser {
	
	/**
	 * 执行可扩展的实现.
	 * @param data
	 * @param extendInfo
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	public boolean executeExtend(String data, ExtendInfo extendInfo,String companyId) throws Exception;
	
	/**
	 * 生成创建表的语句(创建create table sql 的方法(告诉列的所有信息,xml中配置了具体的 field 字段).
	 * @return
	 * @throws Exception
	 */
	public boolean getCreateTablesSql() throws Exception;
	
	/**
	 * 生成insert into 语句(告诉列的所有信息,xml中配置了具体的 field 字段).
	 * @param data
	 * @param uniqueKey
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	public boolean getInsertSql(String data, String uniqueKey,String companyId) throws Exception;
	
	/**
	 * 生成insert into 语句(告诉列的所有信息,xml中配置了具体的 field 字段).
	 * @param data
	 * @param uniqueKey
	 * @param companyId
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	public boolean getInsertSql(String data, String uniqueKey,String companyId, PkProvider provider) throws Exception;
	
	/**
	 * 创建create table sql 的方法('没有':告诉列的所有信息,xml中 '没有' 配置了具体的 field 字段).
	 * @param data
	 * @param uniqueKey
	 * @return
	 * @throws Exception
	 */
	public boolean getCreateTableSql(String data,String uniqueKey) throws Exception;
	
	/**
	 * 生成创建表的语句(创建create table sql 的方法(告诉列的所有信息,xml中配置了具体的 field 字段).
	 * @param settingId
	 * @return
	 * @throws Exception
	 */
	public boolean getCreateTablesSql(String settingId) throws Exception;
	
	/**
	 * 生成insert into 语句(告诉列的所有信息,xml中配置了具体的 field 字段).
	 * @param settingId
	 * @param data
	 * @param uniqueKey
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	public boolean getInsertSql(String settingId,String data, String uniqueKey,String companyId) throws Exception;
	
	/**
	 * 生成insert into 语句(告诉列的所有信息,xml中配置了具体的 field 字段).
	 * @param settingId
	 * @param data
	 * @param uniqueKey
	 * @param companyId
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	public boolean getInsertSql(String settingId,String data, String uniqueKey,String companyId, PkProvider provider) throws Exception;
	
	/**
	 * 创建create table sql 的方法('没有':告诉列的所有信息,xml中 '没有' 配置了具体的 field 字段).
	 * @param settingId
	 * @param data
	 * @param uniqueKey
	 * @return
	 * @throws Exception
	 */
	public boolean getCreateTableSql(String settingId,String data,String uniqueKey) throws Exception;
	
}

