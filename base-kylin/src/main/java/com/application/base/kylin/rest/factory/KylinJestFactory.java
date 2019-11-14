package com.application.base.kylin.rest.factory;

import com.application.base.kylin.rest.config.KylinRestConfig;
import com.application.base.kylin.rest.core.KylinRestApiClient;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : 孤狼
 * @NAME: KylinJdbcFactory
 * @DESC: 工厂实例,自己实现接口信息.
 **/
public class KylinJestFactory implements PooledObjectFactory<KylinRestApiClient> {
	
	private AtomicReference<KylinRestConfig> nodesReference = new AtomicReference<KylinRestConfig>();
	
	public KylinJestFactory(KylinRestConfig poolConfig) {
		this.nodesReference.set(poolConfig);
	}
	
	@Override
	public PooledObject<KylinRestApiClient> makeObject() throws Exception {
		KylinRestConfig poolConfig = nodesReference.get();
		KylinRestApiClient client = new KylinRestApiClient(poolConfig);
		return new DefaultPooledObject(client);
	}
	
	@Override
	public void destroyObject(PooledObject<KylinRestApiClient> pooledObject) throws Exception {
		KylinRestApiClient client = pooledObject.getObject();
		if (client != null) {
			try {
				client= null;
			} catch (Exception e) {
				//ignore
			}
		}
	}
	
	@Override
	public boolean validateObject(PooledObject<KylinRestApiClient> pooledObject) {
		KylinRestApiClient client = pooledObject.getObject();
		try {
			return client.authToken();
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public void activateObject(PooledObject<KylinRestApiClient> pooledObject) throws Exception {
		KylinRestApiClient client = pooledObject.getObject();
		String response = client.login();
		System.out.println("login info:"+response);
	}
	
	@Override
	public void passivateObject(PooledObject<KylinRestApiClient> pooledObject) throws Exception {
		//nothing
	}
}
