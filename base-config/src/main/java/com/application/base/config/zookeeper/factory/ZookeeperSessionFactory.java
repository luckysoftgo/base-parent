package com.application.base.config.zookeeper.factory;

import com.application.base.config.zookeeper.api.ZkApiSession;
import com.application.base.config.zookeeper.exception.ZookeeperException;

/**
 * @desc 获得 zookeeper 接口实例.
 * @author 孤狼.
 */
public interface ZookeeperSessionFactory {

	/**
	 * 获得操作 zookeeper 的服务session接口实例 .
	 * @return
	 * @throws ZookeeperException
	 */
	ZkApiSession getZookeeperSession() throws ZookeeperException;

}
