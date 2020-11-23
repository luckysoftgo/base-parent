package com.application.base.operapi.api.phoenix.core;

import com.application.base.operapi.api.phoenix.api.PhoenixSession;
import com.application.base.operapi.api.phoenix.exception.PhoenixException;

/**
 * @author : 孤狼
 * @NAME: PhoenixSessionFactory
 * @DESC: 执行的操作实例工厂
 **/
public interface PhoenixSessionFactory {
	
	/**
	 * 获得操作hbase的实例对象.
	 *
	 * @return
	 * @throws PhoenixException
	 */
	PhoenixSession getPhoenixxSession() throws PhoenixException;
	
}
