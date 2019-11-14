package com.application.base.kylin.jdbc.factory;

import com.application.base.kylin.jdbc.config.KylinJdbcConfig;
import com.application.base.kylin.jdbc.core.KylinJdbcClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : 孤狼
 * @NAME: KylinJdbcFactory
 * @DESC: 工厂实例,继承他人的实现.
 **/
public class KylinJdbcFactory extends BasePooledObjectFactory<KylinJdbcClient> {
	
	private AtomicReference<KylinJdbcConfig> nodesReference = new AtomicReference<KylinJdbcConfig>();
	
	public KylinJdbcFactory(KylinJdbcConfig jdbcConfig){
		this.nodesReference.set(jdbcConfig);
	}
	
	@Override
	public KylinJdbcClient create() throws Exception {
		KylinJdbcConfig config = nodesReference.get();
		return new KylinJdbcClient(config);
	}
	
	@Override
	public PooledObject<KylinJdbcClient> wrap(KylinJdbcClient conn) {
		//包装实际对象
		return new DefaultPooledObject<>(conn);
	}
}
