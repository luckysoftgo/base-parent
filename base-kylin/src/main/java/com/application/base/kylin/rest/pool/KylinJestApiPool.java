package com.application.base.kylin.rest.pool;


import com.application.base.kylin.rest.config.KylinRestConfig;
import com.application.base.kylin.rest.core.KylinRestApiClient;
import com.application.base.kylin.rest.factory.KylinJestFactory;

/**
 * @NAME: KylinJestApiPool
 * @DESC: 连接池配置
 * @author : 孤狼
 **/
public class KylinJestApiPool extends KylinObjPool<KylinRestApiClient> {
		
	/**
	 * 构造函数.
	 * @param restConfig
	 */
	public KylinJestApiPool(KylinRestConfig restConfig){
			super(restConfig, new KylinJestFactory(restConfig));
	}
}
