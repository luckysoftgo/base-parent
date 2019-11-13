package com.application.base.kylin.jdbc.factory;

import com.application.base.kylin.jdbc.config.KylinJdbcConfig;
import com.application.base.kylin.jdbc.core.KylinJdbcClient;
import com.google.gson.Gson;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : 孤狼
 * @NAME: KylinJdbcFactory
 * @DESC: 工厂实例.
 **/
public class KylinJdbcFactory implements PooledObjectFactory<KylinJdbcClient> {
	
	/**
	 * 映射对象.
	 */
	private AtomicReference<KylinJdbcConfig> nodesReference = new AtomicReference<KylinJdbcConfig>();
	
	public KylinJdbcFactory(KylinJdbcConfig poolConfig) {
		this.nodesReference.set(poolConfig);
	}
	
	@Override
	public PooledObject<KylinJdbcClient> makeObject() throws Exception {
		KylinJdbcConfig poolConfig = nodesReference.get();
		KylinJdbcClient client = new KylinJdbcClient(poolConfig);
		return new DefaultPooledObject(client);
	}
	
	@Override
	public void destroyObject(PooledObject<KylinJdbcClient> pooledObject) throws Exception {
		KylinJdbcClient client = pooledObject.getObject();
		if (client != null) {
			try {
				client.close(client.getProjectName());
			} catch (Exception e) {
				//ignore
			}
		}
	}
	
	@Override
	public boolean validateObject(PooledObject<KylinJdbcClient> pooledObject) {
		KylinJdbcClient client = pooledObject.getObject();
		try {
			return client.check(client.getProjectName());
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public void activateObject(PooledObject<KylinJdbcClient> pooledObject) throws Exception {
		KylinJdbcClient client = pooledObject.getObject();
		System.out.println("得到的对象是:"+new Gson().toJson(client));
	}
	
	@Override
	public void passivateObject(PooledObject<KylinJdbcClient> pooledObject) throws Exception {
		//nothing
	}
}
