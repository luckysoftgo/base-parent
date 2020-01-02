package com.application.base.operapi.api.hbase.pool;

import com.application.base.operapi.api.hbase.config.HbaseConfig;
import com.application.base.operapi.api.hbase.core.HbaseClient;
import com.application.base.operapi.api.hbase.factory.HbaseCreateFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author : 孤狼
 * @NAME: HbaseOperPool
 * @DESC: 连接的pool.
 **/
public class HbaseOperPool extends GenericObjectPool<HbaseClient> {
	
	/**
	 * 构造函数.
	 *
	 * @param hbaseConfig
	 */
	public HbaseOperPool(HbaseConfig hbaseConfig) {
		super(new HbaseCreateFactory(hbaseConfig), hbaseConfig);
	}
}