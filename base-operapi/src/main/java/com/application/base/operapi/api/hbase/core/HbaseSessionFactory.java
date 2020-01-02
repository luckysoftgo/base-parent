package com.application.base.operapi.api.hbase.core;

import com.application.base.operapi.api.hbase.api.HbaseSession;
import com.application.base.operapi.api.hbase.exception.HbaseException;

/**
 * @author : 孤狼
 * @NAME: HbaseSessionFactory
 * @DESC: 执行的操作实例工厂
 **/
public interface HbaseSessionFactory {
	
	/**
	 * 获得操作hbase的实例对象.
	 * @return
	 * @throws HbaseException
	 */
	HbaseSession getHbaseSession() throws HbaseException;
	
}
