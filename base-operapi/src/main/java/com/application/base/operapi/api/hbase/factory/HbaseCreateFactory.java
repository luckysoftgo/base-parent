package com.application.base.operapi.api.hbase.factory;

import com.application.base.operapi.api.hbase.config.HbaseConfig;
import com.application.base.operapi.api.hbase.core.HbaseClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : 孤狼
 * @NAME: HbaseCreateFactory
 * @DESC: 工厂实例
 **/
public class HbaseCreateFactory extends BasePooledObjectFactory<HbaseClient> {
	
	private AtomicReference<HbaseConfig> nodesReference = new AtomicReference<HbaseConfig>();
	
	public HbaseCreateFactory(HbaseConfig hbaseConfig){
		this.nodesReference.set(hbaseConfig);
	}
	
	@Override
	public HbaseClient create() throws Exception {
		HbaseConfig config = nodesReference.get();
		return new HbaseClient(config);
	}
	
	@Override
	public PooledObject<HbaseClient> wrap(HbaseClient client) {
		//包装实际对象
		return new DefaultPooledObject<>(client);
	}
}