package com.application.base.all.elastic.elastic.rest.config;

import com.application.base.all.elastic.elastic.rest.client.ElasticSearchClient;
import com.application.base.all.elastic.elastic.rest.factory.ElasticSearchPoolFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @NAME: ElasticSearchPool.
 * @DESC: ES 客户连接池.
 * @USER: 孤狼.
 **/
public class ElasticSearchPool {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 得到实例化池对象.
	 */
	private volatile GenericKeyedObjectPool<String, ElasticSearchClient> elasticSearchPool;
	/**
	 * 是否默认集群
	 */
	public String DEFAULT_CLUSTER = "";
	
	
	/**
	 * 客户连接池的初始化操作.
	 * @param elasticSearchPoolFactory
	 * @param elasticSearchPoolConfig
	 */
	private ElasticSearchPool(ElasticSearchPoolFactory elasticSearchPoolFactory, ElasticSearchPoolConfig elasticSearchPoolConfig) {
		logger.info("读取elasticsearch连接池池配置成功：" + elasticSearchPoolConfig.toString() + "，当前默认集群：" + elasticSearchPoolConfig.getUseClusterName());
		//防止多例模式下出现一个连接池被初始化两次的问题
		synchronized (this) {
			if (elasticSearchPool == null) {
				synchronized (this) {
					this.elasticSearchPool = new GenericKeyedObjectPool<String, ElasticSearchClient>(elasticSearchPoolFactory, elasticSearchPoolConfig);
				}
			}
			DEFAULT_CLUSTER = elasticSearchPoolConfig.getUseClusterName();
			for (ElasticSearchClusterConfig elasticSearchClusterConfig : elasticSearchPoolConfig.getPoolConfig()) {
				try {
					String clusterName = elasticSearchClusterConfig.getClusterName();
					elasticSearchPool.preparePool(clusterName);
					logger.info("预热elasticsearch连接池" + clusterName + "成功,当前连接数:" + elasticSearchPool.getNumActivePerKey().get(clusterName));
				} catch (Exception e) {
					logger.error("预热elasticsearch连接池失败，索引名：" + elasticSearchClusterConfig.getClusterName(), e);
				}
			}
		}
	}
	
	/**
	 * 获得实例
	 * @return
	 */
	public ElasticSearchClient getSearchClient() {
		return getSearchClient(DEFAULT_CLUSTER);
	}
	
	/**
	 * 获得实例
	 * @param clusterName
	 * @return
	 */
	public ElasticSearchClient getSearchClient(String clusterName) {
		try {
			ElasticSearchClient elasticSearchClient = elasticSearchPool.borrowObject(clusterName);
			logger.info("es获取客户端:" + elasticSearchClient);
			return elasticSearchClient;
		} catch (Exception e) {
			logger.error("获取es客户端失败！", e);
		}
		return null;
	}
	
	/**
	 * 关闭客户端信息
	 * @param searchClient
	 * @return
	 */
	public void returnClient(ElasticSearchClient searchClient) {
		try {
			String clientInfo = searchClient.toString();
			elasticSearchPool.returnObject(searchClient.getClusterName(), searchClient);
			logger.info("es关闭客户端:" + clientInfo);
		} catch (Exception e) {
			logger.error("归还es客户端失败！", e);
		}
	}
}
