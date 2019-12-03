package com.application.base.elastic.transport;

import com.application.base.elastic.elastic.transport.config.EsTransportNodeConfig;
import com.application.base.elastic.elastic.transport.config.EsTransportPoolConfig;
import com.application.base.elastic.elastic.transport.factory.EsTransportSessionPoolFactory;
import com.application.base.elastic.elastic.transport.pool.ElasticTransportPool;
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
 * @NAME: EsTransPortAutoConfiguration
 * @DESC: 装配对象参数.
 **/
// 相当于一个普通的 java 配置类
@Configuration
// 当 EsTransportSessionPoolFactory 在类路径的条件下
@ConditionalOnClass({EsTransportSessionPoolFactory.class})
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(EsTransPortConfigProperties.class)
public class EsTransPortAutoConfiguration {

	/**
	 * 属性的配置.
	 */
	@Autowired
	private EsTransPortConfigProperties esTransPortConfig;
	
	/**
	 *  当容器没有这个 Bean 的时候才创建这个 Bean
	 */
	@Bean
	@ConditionalOnMissingBean(EsTransportSessionPoolFactory.class)
	public EsTransportSessionPoolFactory getEsTransPortFactory() {
		HashSet<EsTransportNodeConfig> esNodes = getNodeInfos();
		EsTransportPoolConfig poolConfig = new EsTransportPoolConfig();
		poolConfig.setClusterName(esTransPortConfig.getCluster().getName());
		poolConfig.setEsNodes(esNodes);
		GenericPool pool = esTransPortConfig.getPool();
		BeanUtils.copyProperties(pool,poolConfig);
		ElasticTransportPool transportPool = new ElasticTransportPool(poolConfig);
		EsTransportSessionPoolFactory transportSessionFactory = new EsTransportSessionPoolFactory(transportPool);
		return transportSessionFactory;
	}
	
	/**
	 * 获得 node 信息
	 * @return
	 */
	private HashSet<EsTransportNodeConfig> getNodeInfos(){
		HashSet<EsTransportNodeConfig> nodeConfigs = new HashSet<>();
		List<EsTransPortConfigProperties.TransPortInfo> transPortInfos = esTransPortConfig.getTransport();
		if (transPortInfos!=null && transPortInfos.size()>0){
			for (EsTransPortConfigProperties.TransPortInfo transPortInfo : transPortInfos) {
				EsTransportNodeConfig nodeConfig = new EsTransportNodeConfig();
				nodeConfig.setNodeName(transPortInfo.getName());
				nodeConfig.setNodeHost(transPortInfo.getHost());
				nodeConfig.setNodePort(transPortInfo.getPort());
				nodeConfig.setNodeSchema(transPortInfo.getSchema());
				nodeConfigs.add(nodeConfig);
			}
		}
		return nodeConfigs;
	}
}
