package com.application.base.elastic.factory;

import com.application.base.elastic.core.ElasticSession;
import com.application.base.elastic.exception.ElasticException;

/**
 * @desc 获得 elastic 实例.
 * @author 孤狼.
 */
public interface ElasticSessionFactory {

	/**
	 * 获得操作elastic的实例.
	 * @return
	 * @throws ElasticException
	 */
	ElasticSession getElasticSession() throws ElasticException;

}
