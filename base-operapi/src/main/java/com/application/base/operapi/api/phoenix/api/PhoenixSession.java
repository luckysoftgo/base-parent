package com.application.base.operapi.api.phoenix.api;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: PhoenixSession
 * @DESC: phoenix 的接口 api
 **/
public interface PhoenixSession {
	
	/**
	 * 查询 hbase 的信息并返回.
	 *
	 * @param sql
	 * @return
	 */
	public LinkedList<Map<String, Object>> selectTable(String sql);
	
	/**
	 * 执行sql,其中包括创建,修改,删除的操作.
	 *
	 * @param sql
	 * @return
	 */
	public boolean execSql(String sql);
	
}
