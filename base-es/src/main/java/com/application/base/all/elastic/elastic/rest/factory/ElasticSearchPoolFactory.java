package com.application.base.all.elastic.elastic.rest.factory;

import com.application.base.all.elastic.elastic.rest.client.ElasticSearchClient;
import com.application.base.all.elastic.elastic.rest.config.ElasticSearchClusterConfig;
import com.application.base.all.elastic.elastic.rest.config.ElasticSearchNodeConfig;
import com.application.base.all.elastic.elastic.rest.config.ElasticSearchPoolConfig;
import com.application.base.all.elastic.exception.ElasticException;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @NAME: ElasticSearchPoolFactory.
 * @DESC: ES 连接池的工厂.
 * @USER: 孤狼.
 **/
public class ElasticSearchPoolFactory implements KeyedPooledObjectFactory<String, ElasticSearchClient> {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 线程池配置信息.
	 */
	private ElasticSearchPoolConfig elasticSearchPoolConfig;
	
	public ElasticSearchPoolConfig getElasticSearchPoolConfig() {
		return elasticSearchPoolConfig;
	}
	public void setElasticSearchPoolConfig(ElasticSearchPoolConfig elasticSearchPoolConfig) {
		this.elasticSearchPoolConfig = elasticSearchPoolConfig;
	}
	
	/**
	 * 获取连接
	 * @param clusterName 集群名称
	 * @return
	 * @throws Exception
	 */
	@Override
	public PooledObject<ElasticSearchClient> makeObject(String clusterName) throws Exception {
		try {
			List<HttpHost> httpHosts = new ArrayList<>();
			for (ElasticSearchClusterConfig clusterConfig : elasticSearchPoolConfig.getPoolConfig()) {
				if (clusterName.equalsIgnoreCase(clusterConfig.getClusterName())) {
					for (ElasticSearchNodeConfig nodeConfig : clusterConfig.getNodeConfig()) {
						httpHosts.add(new HttpHost(nodeConfig.getNodeHost(), nodeConfig.getNodePort(), nodeConfig.getNodeSchema()));
					}
				}
			}
			HttpHost[] hostArray = new HttpHost[httpHosts.size()];
			hostArray = httpHosts.toArray(hostArray);
			RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(hostArray));
			ElasticSearchClient elasticSearchClient = new ElasticSearchClient(restHighLevelClient,clusterName);
			return new DefaultPooledObject(elasticSearchClient);
		}catch (Exception e){
			logger.error("获取ElasticSearch的连接信息失败,失败的具体信息是:{}",e);
			throw new ElasticException(e);
		}
	}
	
	/**
	 * 销毁客户端
	 *
	 * @param s
	 * @param pooledObject
	 * @throws Exception
	 */
	@Override
	public void destroyObject(String s, PooledObject<ElasticSearchClient> pooledObject) throws Exception {
		ElasticSearchClient elasticSearchClient = pooledObject.getObject();
		if(elasticSearchClient==null){
			pooledObject=null;
			return;
		}
		if(elasticSearchClient.getLevelClient()==null){
			pooledObject=null;
			return;
		}
		RestHighLevelClient restHighLevelClient = elasticSearchClient.getLevelClient();
		if (restHighLevelClient.ping(RequestOptions.DEFAULT)) {
			restHighLevelClient.close();
			pooledObject=null;
		}
	}
	
	/**ElasticSearchPoolFactory
	 * 验证客户端有效性
	 * @param s
	 * @param pooledObject
	 * @return
	 */
	@Override
	public boolean validateObject(String s, PooledObject<ElasticSearchClient> pooledObject) {
		ElasticSearchClient elasticSearchClient = pooledObject.getObject();
		if(elasticSearchClient==null){
			return false;
		}
		if(elasticSearchClient.getLevelClient()==null){
			return false;
		}
		RestHighLevelClient restHighLevelClient = elasticSearchClient.getLevelClient();
		try {
			return restHighLevelClient.ping(RequestOptions.DEFAULT);
		} catch (Exception e) {
			logger.error("集群" + s + "验证es客户端有效性失败！", e);
		}
		return false;
	}
	
	/**
	 * 唤醒客户端
	 * @param s
	 * @param pooledObject
	 * @throws Exception
	 */
	@Override
	public void activateObject(String s, PooledObject<ElasticSearchClient> pooledObject) throws Exception {
		ElasticSearchClient elasticSearchClient = pooledObject.getObject();
	}
	
	/**
	 * 挂起客户端
	 * @param s
	 * @param pooledObject
	 * @throws Exception
	 */
	@Override
	public void passivateObject(String s, PooledObject<ElasticSearchClient> pooledObject) throws Exception {
		ElasticSearchClient elasticSearchClient = pooledObject.getObject();
	}
}
