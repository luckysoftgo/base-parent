package com.application.base.config.zookeeper.pool;


import com.application.base.config.zookeeper.config.ZooKeeperConfig;
import com.application.base.config.zookeeper.core.ZooKeeperConfigFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.curator.framework.CuratorFramework;

/**
 * @author : 孤狼
 * @NAME: ZooKeeperOperPool
 * @DESC: 操作连接池
 **/
public class ZooKeeperOperPool extends GenericObjectPool<CuratorFramework> {
	
	/**
	 * 构造函数.
	 * @param keeperConfig
	 */
	public ZooKeeperOperPool(ZooKeeperConfig keeperConfig){
		super(new ZooKeeperConfigFactory(keeperConfig),keeperConfig);
	}
	
	/**
	 * 操作连接池.
	 * @param keeperConfig
	 * @param abandonedConfig
	 */
	/*
	public ZooKeeperOperPool(ZooKeeperConfig keeperConfig, AbandonedConfig abandonedConfig) {
		super(new ZooKeeperConfigFactory(keeperConfig),keeperConfig,abandonedConfig);
	}
	*/
}
