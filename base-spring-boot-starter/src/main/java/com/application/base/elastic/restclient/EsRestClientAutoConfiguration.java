package com.application.base.elastic.restclient;

import com.application.base.elastic.elastic.restclient.config.EsRestClientNodeConfig;
import com.application.base.elastic.elastic.restclient.config.EsRestClientPoolConfig;
import com.application.base.elastic.elastic.restclient.factory.EsRestClientSessionPoolFactory;
import com.application.base.elastic.elastic.restclient.pool.ElasticRestClientPool;
import com.application.base.pool.GenericPool;
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
// 当 EsRestClientSessionPoolFactory 在类路径的条件下
@ConditionalOnClass({EsRestClientSessionPoolFactory.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(EsRestClientConfigProperties.class)
public class EsRestClientAutoConfiguration {
	
	/**
	 * 属性的配置.
	 */
	@Autowired
	private EsRestClientConfigProperties esRestClientConfig;
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(EsRestClientSessionPoolFactory.class)
	public EsRestClientSessionPoolFactory getEsRestClientFactory() {
		HashSet<EsRestClientNodeConfig> esNodes = getNodeInfos();
		EsRestClientPoolConfig poolConfig = new EsRestClientPoolConfig();
		poolConfig.setClusterName(esRestClientConfig.getCluster().getName());
		poolConfig.setEsNodes(esNodes);
		GenericPool pool = esRestClientConfig.getPool();
		BeanUtils.copyProperties(pool,poolConfig);
		ElasticRestClientPool restClientPool = new ElasticRestClientPool(poolConfig);
		EsRestClientSessionPoolFactory restClientSessionFactory = new EsRestClientSessionPoolFactory(restClientPool);
		return restClientSessionFactory;
	}
	
	/**
	 * 获得 node 信息
	 * @return
	 */
	private HashSet<EsRestClientNodeConfig> getNodeInfos(){
		HashSet<EsRestClientNodeConfig> nodeConfigs = new HashSet<>();
		List<EsRestClientConfigProperties.RestClientInfo>  restClientInfos = esRestClientConfig.getRestclient();
		if (restClientInfos!=null && restClientInfos.size()>0){
			for (EsRestClientConfigProperties.RestClientInfo restClientInfo : restClientInfos) {
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
}
