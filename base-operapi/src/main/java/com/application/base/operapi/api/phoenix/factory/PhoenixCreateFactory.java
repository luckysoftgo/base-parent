package com.application.base.operapi.api.phoenix.factory;

import com.application.base.operapi.api.phoenix.config.PhoenixConfig;
import com.application.base.operapi.api.phoenix.core.PhoenixClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : 孤狼
 * @NAME: PhoenixCreateFactory
 * @DESC: 工厂实例
 **/
public class PhoenixCreateFactory extends BasePooledObjectFactory<PhoenixClient> {
	
	private AtomicReference<PhoenixConfig> nodesReference = new AtomicReference<PhoenixConfig>();
	
	public PhoenixCreateFactory(PhoenixConfig hbaseConfig) {
		this.nodesReference.set(hbaseConfig);
	}
	
	@Override
	public PhoenixClient create() throws Exception {
		PhoenixConfig config = nodesReference.get();
		return new PhoenixClient(config);
	}
	
	@Override
	public PooledObject<PhoenixClient> wrap(PhoenixClient client) {
		//包装实际对象
		return new DefaultPooledObject<>(client);
	}
}