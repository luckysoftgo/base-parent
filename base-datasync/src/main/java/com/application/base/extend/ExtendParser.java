package com.application.base.extend;

import java.util.LinkedList;

/**
 * @author : 孤狼
 * @NAME: ExtendParser
 * @DESC: 可扩展的实现( 提供向外的接口,由实现人员实现,上传jar文件,通过获取实例来入库).
 **/
public interface ExtendParser {
	
	/**
	 * 传入具体api的名称
	 * @param apiKey
	 */
	public void setApiKey(String apiKey);
	
	/**
	 * 设置数据的关联Id
	 * @param relationId
	 */
	public void setRelationId(String relationId);
	
	/**
	 * 传入dataJson数据
	 * @param dataJson
	 */
	public void setDataJson(String dataJson);
	
	/**
	 * 传入生成的表名称
	 * @param tableNames
	 */
	public void setTableNames(String... tableNames);
	
	/**
	 * 获得插入的sql
	 */
	public LinkedList<String> toInsertSqls();
}
