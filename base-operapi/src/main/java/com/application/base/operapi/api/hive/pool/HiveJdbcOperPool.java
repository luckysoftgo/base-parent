package com.application.base.operapi.api.hive.pool;

import com.application.base.operapi.api.hive.config.HiveJdbcConfig;
import com.application.base.operapi.api.hive.core.HiveJdbcClient;
import com.application.base.operapi.api.hive.factory.HiveJdbcFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author : 孤狼
 * @NAME: HiveJdbcOperPool
 * @DESC: 操作连接池
 **/
public class HiveJdbcOperPool extends GenericObjectPool<HiveJdbcClient> {
	
	/**
	 * 构造函数.
	 * @param jdbcConfig
	 */
	public HiveJdbcOperPool(HiveJdbcConfig jdbcConfig){
		super(new HiveJdbcFactory(jdbcConfig),jdbcConfig);
	}
	
}
