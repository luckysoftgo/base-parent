package com.application.base.config.zookeeper;

import com.application.base.config.zookeeper.config.ZooKeeperConfig;
import com.application.base.config.zookeeper.curator.factory.ZooKeeperSimpleSessionFactory;
import com.application.base.config.zookeeper.curator.lock.ZkDelegateDistributedLock;
import com.application.base.config.zookeeper.pool.ZooKeeperOperPool;
import com.application.base.pool.GenericPool;
import com.application.base.utils.json.JsonConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 孤狼
 * @NAME: ZooKeeperAutoConfiguration
 * @DESC: 装配对象参数.
 **/
// 相当于一个普通的 java 配置类
@Configuration
// 当 ZooKeeperSimpleSessionFactory 在类路径的条件下
@ConditionalOnClass({ZooKeeperSimpleSessionFactory.class,ZkDelegateDistributedLock.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(ZooKeeperConfigProperties.class)
public class ZooKeeperAutoConfiguration {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 属性的配置.
	 */
	@Autowired
	private ZooKeeperConfigProperties zooKeeperConfig;
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(ZooKeeperSimpleSessionFactory.class)
	public ZooKeeperSimpleSessionFactory getZooKeeperFactory() {
		return getZkSessionInstance();
	}
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(ZkDelegateDistributedLock.class)
	public ZkDelegateDistributedLock getZKDistributeLockFactory() {
		ZooKeeperSimpleSessionFactory zookeeperFactory = getZkSessionInstance();
		ZkDelegateDistributedLock distributedLock = new ZkDelegateDistributedLock();
		distributedLock.setSessionFactory(zookeeperFactory);
		return distributedLock;
	}
	
	/**
	 * 得到工厂实例.
	 *
	 * @return
	 */
	private ZooKeeperSimpleSessionFactory getZkSessionInstance(){
		logger.info("zookeeper init infos:{}", JsonConvertUtils.toJson(zooKeeperConfig));
		ZooKeeperConfig instanceConfig = new ZooKeeperConfig();
		instanceConfig.setNameSpace(zooKeeperConfig.getName().getSpace());
		instanceConfig.setSessionTimeoutMs(zooKeeperConfig.getSession().getTimeoutms());
		instanceConfig.setConnectionTimeoutMs(zooKeeperConfig.getConnection().getTimeoutms());
		instanceConfig.setConnectString(zooKeeperConfig.getConnect().getStrings());
		GenericPool pool = zooKeeperConfig.getPool();
		BeanUtils.copyProperties(pool,instanceConfig);
		ZooKeeperOperPool zooKeeperPool = new ZooKeeperOperPool(instanceConfig);
		ZooKeeperSimpleSessionFactory zookeeperFactory=new ZooKeeperSimpleSessionFactory();
		zookeeperFactory.setZooKeeperPool(zooKeeperPool);
		return zookeeperFactory;
	}
}
