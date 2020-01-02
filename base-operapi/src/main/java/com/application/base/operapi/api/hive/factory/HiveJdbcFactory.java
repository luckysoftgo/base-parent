package com.application.base.operapi.api.hive.factory;

import com.application.base.operapi.api.hive.config.HiveJdbcConfig;
import com.application.base.operapi.api.hive.core.HiveJdbcClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : 孤狼
 * @NAME: HiveJdbcFactory
 * @DESC: 工厂实例,继承他人的实现.
 **/
public class HiveJdbcFactory extends BasePooledObjectFactory<HiveJdbcClient> {
	
	private AtomicReference<HiveJdbcConfig> nodesReference = new AtomicReference<HiveJdbcConfig>();
	
	public HiveJdbcFactory(HiveJdbcConfig jdbcConfig){
		this.nodesReference.set(jdbcConfig);
	}
	
	@Override
	public HiveJdbcClient create() throws Exception {
		HiveJdbcConfig config = nodesReference.get();
		return new HiveJdbcClient(config);
	}
	
	@Override
	public PooledObject<HiveJdbcClient> wrap(HiveJdbcClient client) {
		//包装实际对象
		return new DefaultPooledObject<>(client);
	}
}
