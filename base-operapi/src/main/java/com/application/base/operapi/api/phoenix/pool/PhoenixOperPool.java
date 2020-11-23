package com.application.base.operapi.api.phoenix.pool;

import com.application.base.operapi.api.phoenix.config.PhoenixConfig;
import com.application.base.operapi.api.phoenix.core.PhoenixClient;
import com.application.base.operapi.api.phoenix.factory.PhoenixCreateFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author : 孤狼
 * @NAME: PhoenixOperPool
 * @DESC: 连接的pool.
 **/
public class PhoenixOperPool extends GenericObjectPool<PhoenixClient> {
	
	/**
	 * 构造函数.
	 *
	 * @param phoenixConfig
	 */
	public PhoenixOperPool(PhoenixConfig phoenixConfig) {
		super(new PhoenixCreateFactory(phoenixConfig), phoenixConfig);
	}
}