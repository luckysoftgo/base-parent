package com.application.test;

import com.application.base.elastic.elastic.transport.config.EsTransportNodeConfig;
import com.application.base.elastic.elastic.transport.config.EsTransportPoolConfig;
import com.application.base.elastic.elastic.transport.factory.EsTransportSessionPoolFactory;
import com.application.base.elastic.elastic.transport.pool.ElasticTransportPool;
import com.application.base.elastic.entity.ElasticData;
import com.application.base.utils.json.JsonConvertUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: FactoryTest
 * @DESC:
 **/
public class FactoryTest {
	
	public static void main(String[] args) {
		
		test();
	}
	
	public static void test(){
		HashSet<EsTransportNodeConfig> esNodes = getTransportNodeInfos();
		EsTransportPoolConfig poolConfig = new EsTransportPoolConfig();
		poolConfig.setClusterName("elasticsearch");
		poolConfig.setEsNodes(esNodes);
		ElasticTransportPool transportPool = new ElasticTransportPool(poolConfig);
		EsTransportSessionPoolFactory transportSessionFactory = new EsTransportSessionPoolFactory(transportPool);
		for (int i = 0; i <500 ; i++) {
			ElasticData data = new ElasticData();
			data.setIndex("hahaha");
			data.setType("hahaha");
			data.setId("hahaha"+i);
			Map<String,Object> info=new HashMap<>();
			info.put("info1","测试"+i);
			info.put("info2","调试"+i);
			info.put("info3","上线"+i);
			info.put("info4","读写"+i);
			info.put("info5","分库"+i);
			data.setMapFlag(false);
			data.setData(JsonConvertUtils.toJson(info));
			boolean flag = transportSessionFactory.getElasticSession().addEsData(data);
			System.out.printf("index="+i+",flag="+flag);
		}
	}
	
	/**
	 * 获得 node 信息
	 * @return
	 */
	private static HashSet<EsTransportNodeConfig> getTransportNodeInfos(){
		HashSet<EsTransportNodeConfig> nodeConfigs = new HashSet<>();
		EsTransportNodeConfig nodeConfig = new EsTransportNodeConfig();
		nodeConfig.setNodeName("es0");
		nodeConfig.setNodeHost("192.168.10.216");
		nodeConfig.setNodePort(9300);
		nodeConfig.setNodeSchema("http");
		nodeConfigs.add(nodeConfig);
		return nodeConfigs;
	}
}
