package com.application.base.starter.elastic;

import com.application.base.elastic.elastic.restclient.config.EsRestClientNodeConfig;
import com.application.base.elastic.elastic.restclient.config.EsRestClientPoolConfig;
import com.application.base.elastic.elastic.restclient.factory.EsRestClientSessionPoolFactory;
import com.application.base.elastic.elastic.restclient.pool.ElasticRestClientPool;
import com.application.base.elastic.elastic.transport.config.EsTransportNodeConfig;
import com.application.base.elastic.elastic.transport.config.EsTransportPoolConfig;
import com.application.base.elastic.elastic.transport.factory.EsTransportSessionPoolFactory;
import com.application.base.elastic.elastic.transport.pool.ElasticTransportPool;
import com.application.base.pool.GenericPool;
import com.application.base.utils.json.JsonConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: EsRestClientAutoConfiguration
 * @DESC: 装配对象参数.
 **/
// 相当于一个普通的 java 配置类
@Configuration
// 当 EsRestClientSessionPoolFactory,EsTransportSessionPoolFactory 在类路径的条件下
@ConditionalOnClass({EsRestClientSessionPoolFactory.class, EsTransportSessionPoolFactory.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(ElasticSearchConfigProperties.class)
public class ElasticSearchAutoConfiguration {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 属性的配置.
	 */
	@Autowired
	private ElasticSearchConfigProperties elasticSearchConfig;
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(EsRestClientSessionPoolFactory.class)
	public EsRestClientSessionPoolFactory getEsRestClientFactory() {
		HashSet<EsRestClientNodeConfig> esNodes = getRestClientNodeInfos();
		if (esNodes.isEmpty()){
			logger.info("elasticSearch get restclient settings is null, properties is :{}", JsonConvertUtils.toJson(elasticSearchConfig));
			return null;
		}
		EsRestClientPoolConfig poolConfig = new EsRestClientPoolConfig();
		if (StringUtils.isNotBlank(elasticSearchConfig.getCluster().getName())){
			poolConfig.setClusterName(elasticSearchConfig.getCluster().getName());
		}
		poolConfig.setEsNodes(esNodes);
		GenericPool pool = elasticSearchConfig.getPool();
		BeanUtils.copyProperties(pool,poolConfig);
		ElasticRestClientPool restClientPool = new ElasticRestClientPool(poolConfig);
		EsRestClientSessionPoolFactory restClientSessionFactory = new EsRestClientSessionPoolFactory(restClientPool);
		return restClientSessionFactory;
	}
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(EsTransportSessionPoolFactory.class)
	public EsTransportSessionPoolFactory getEsTransportFactory() {
		HashSet<EsTransportNodeConfig> esNodes = getTransportNodeInfos();
		if (esNodes.isEmpty()){
			logger.info("elasticSearch get transport settings is null, properties is :{}", JsonConvertUtils.toJson(elasticSearchConfig));
			return null;
		}
		EsTransportPoolConfig poolConfig = new EsTransportPoolConfig();
		if (StringUtils.isNotBlank(elasticSearchConfig.getCluster().getName())){
			poolConfig.setClusterName(elasticSearchConfig.getCluster().getName());
		}
		poolConfig.setEsNodes(esNodes);
		GenericPool pool = elasticSearchConfig.getPool();
		BeanUtils.copyProperties(pool,poolConfig);
		ElasticTransportPool transportPool = new ElasticTransportPool(poolConfig);
		EsTransportSessionPoolFactory transportSessionFactory = new EsTransportSessionPoolFactory(transportPool);
		return transportSessionFactory;
	}
	
	/**
	 * 获得 node 信息
	 * @return
	 */
	private HashSet<EsRestClientNodeConfig> getRestClientNodeInfos(){
		HashSet<EsRestClientNodeConfig> nodeConfigs = new HashSet<>();
		List<ElasticSearchConfigProperties.ConnectClientInfo> restClientInfos = elasticSearchConfig.getRestclient();
		if (restClientInfos!=null && restClientInfos.size()>0){
			for (ElasticSearchConfigProperties.ConnectClientInfo restClientInfo : restClientInfos) {
				EsRestClientNodeConfig nodeConfig = new EsRestClientNodeConfig();
				nodeConfig.setNodeName(restClientInfo.getName());
				nodeConfig.setNodeHost(restClientInfo.getHost());
				nodeConfig.setNodePort(restClientInfo.getPort());
				nodeConfig.setNodeSchema(restClientInfo.getSchema());
				nodeConfigs.add(nodeConfig);
			}
		}
		return nodeConfigs;
	}
	
	/**
	 * 获得 node 信息
	 * @return
	 */
	private HashSet<EsTransportNodeConfig> getTransportNodeInfos(){
		HashSet<EsTransportNodeConfig> nodeConfigs = new HashSet<>();
		List<ElasticSearchConfigProperties.ConnectClientInfo> transportInfos = elasticSearchConfig.getTransport();
		if (transportInfos!=null && transportInfos.size()>0){
			for (ElasticSearchConfigProperties.ConnectClientInfo transportInfo : transportInfos) {
				EsTransportNodeConfig nodeConfig = new EsTransportNodeConfig();
				nodeConfig.setNodeName(transportInfo.getName());
				nodeConfig.setNodeHost(transportInfo.getHost());
				nodeConfig.setNodePort(transportInfo.getPort());
				nodeConfig.setNodeSchema(transportInfo.getSchema());
				nodeConfigs.add(nodeConfig);
			}
		}
		return nodeConfigs;
	}
}
