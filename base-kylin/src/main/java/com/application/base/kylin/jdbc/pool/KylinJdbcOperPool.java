package com.application.base.kylin.jdbc.pool;

import com.application.base.kylin.KylinObjPool;
import com.application.base.kylin.jdbc.config.KylinJdbcConfig;
import com.application.base.kylin.jdbc.core.KylinJdbcClient;
import com.application.base.kylin.jdbc.factory.KylinJdbcFactory;

/**
 * @author : 孤狼
 * @NAME: KylinJdbcOperPool
 * @DESC: 操作连接池
 **/
public class KylinJdbcOperPool extends KylinObjPool<KylinJdbcClient> {
	/**
	 * 构造函数.
	 * @param jdbcConfig
	 */
	public KylinJdbcOperPool(KylinJdbcConfig jdbcConfig){
		super(jdbcConfig, new KylinJdbcFactory(jdbcConfig));
	}

}
