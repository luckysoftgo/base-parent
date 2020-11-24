package com.application.test;

import com.application.base.elastic.elastic.restclient.config.EsRestClientPoolConfig;
import com.application.base.elastic.elastic.restclient.factory.EsRestClientSessionPoolFactory;
import com.application.base.elastic.elastic.restclient.pool.ElasticRestClientPool;
import com.application.base.elastic.entity.ElasticData;
import com.application.base.elastic.entity.NodeInfo;
import com.application.base.elastic.util.jest.EsJestClientBuilder;
import com.application.base.utils.DbCommandUtil;
import com.application.base.utils.json.JsonConvertUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: FactoryTest
 * @DESC:
 **/
public class CreditTest {
	
	static final String PREX_TAG="code_";
	
	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		EsJestClientBuilder builder =new EsJestClientBuilder();
		RestHighLevelClient client = builder.initParamsClient("192.168.10.216:9200","elastic:elastic");
		try {
			GetIndexRequest getRequest = new GetIndexRequest();
			GetResponse response = null;
			System.out.println(JsonConvertUtils.toJson(response.getSource()));
			System.out.println(JsonConvertUtils.toJson(response.getSourceAsString()));
			System.out.println(JsonConvertUtils.toJson(response.getSourceAsMap()));
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		EsRestClientSessionPoolFactory poolFactory = getFactory();
		//data1(poolFactory);
		/*
		data2(poolFactory);
		data3(poolFactory);
		data4(poolFactory);
		*/
		System.exit(0);
	}
	
	private static void data(EsRestClientSessionPoolFactory transportSessionFactory){
		try {
		
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private static void data1(EsRestClientSessionPoolFactory transportSessionFactory){
		DbCommandUtil manager = new DbCommandUtil();
		String sql="select * from credit_education ";
		try {
			ResultSet rest = manager.selectSQL(sql);
			List<Map<String,Object>> datas = manager.resultSetToList(rest);
			List<ElasticData> list = new ArrayList<>();
			int index=1;
			for (Map<String,Object> map : datas ) {
				ElasticData data = new ElasticData();
				data.setIndex(PREX_TAG+"credit_education");
				data.setType("doc");
				data.setId(index+"");
				data.setMapData(convertMap(map));
				data.setMapFlag(true);
				list.add(data);
				index++;
			}
			boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
			System.out.println("执行 EsData1 完成!flag="+flag);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private static void data2(EsRestClientSessionPoolFactory transportSessionFactory){
		DbCommandUtil manager = new DbCommandUtil();
		String sql="select * from credit_promise ";
		try {
			ResultSet rest = manager.selectSQL(sql);
			List<Map<String,Object>> datas = manager.resultSetToList(rest);
			List<ElasticData> list = new ArrayList<>();
			int index=1;
			for (Map<String,Object> map : datas ) {
				ElasticData data = new ElasticData();
				data.setIndex(PREX_TAG+"credit_promise");
				data.setType("doc");
				data.setId(index+"");
				data.setMapData(convertMap(map));
				data.setMapFlag(true);
				list.add(data);
				index++;
			}
			boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
			System.out.println("执行 EsData1 完成!flag="+flag);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private static void data3(EsRestClientSessionPoolFactory transportSessionFactory){
		DbCommandUtil manager = new DbCommandUtil();
		String sql="select * from credit_shixin_action ";
		try {
			ResultSet rest = manager.selectSQL(sql);
			List<Map<String,Object>> datas = manager.resultSetToList(rest);
			List<ElasticData> list = new ArrayList<>();
			int index=1;
			for (Map<String,Object> map : datas ) {
				ElasticData data = new ElasticData();
				data.setIndex(PREX_TAG+"credit_shixin_action");
				data.setType("doc");
				data.setId(index+"");
				data.setMapData(convertMap(map));
				data.setMapFlag(true);
				list.add(data);
				index++;
			}
			boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
			System.out.println("执行 EsData1 完成!flag="+flag);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private static void data4(EsRestClientSessionPoolFactory transportSessionFactory){
		DbCommandUtil manager = new DbCommandUtil();
		String sql="select * from credit_type_rate ";
		try {
			ResultSet rest = manager.selectSQL(sql);
			List<Map<String,Object>> datas = manager.resultSetToList(rest);
			List<ElasticData> list = new ArrayList<>();
			int index=1;
			for (Map<String,Object> map : datas ) {
				ElasticData data = new ElasticData();
				data.setIndex(PREX_TAG+"credit_type_rate");
				data.setType("doc");
				data.setId(index+"");
				data.setMapData(convertMap(map));
				data.setMapFlag(true);
				list.add(data);
				index++;
			}
			boolean flag = transportSessionFactory.getElasticSession().addEsDataListByProcessor(list,false);
			System.out.println("执行 EsData1 完成!flag="+flag);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 类型转换
	 * @param sourceMap
	 * @return
	 */
	public static Map<String,Object> convertMap(Map<String,Object> sourceMap){
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.putAll(sourceMap);
		for (Map.Entry<String,Object> entry : sourceMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof java.util.Date || value instanceof java.sql.Date) {
				value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
				returnMap.put(key, value);
			}
		}
		return returnMap;
	}
	
	
	/**
	 * 获得结果.
	 * @return
	 */
	public static EsRestClientSessionPoolFactory getFactory(){
		List<NodeInfo> esNodes = getTransportNodeInfos();
		EsRestClientPoolConfig poolConfig = new EsRestClientPoolConfig();
		poolConfig.setClusterName("elasticsearch");
		poolConfig.setServerNodes(esNodes);
		ElasticRestClientPool transportPool = new ElasticRestClientPool(poolConfig);
		EsRestClientSessionPoolFactory transportSessionFactory = new EsRestClientSessionPoolFactory(transportPool);
		return transportSessionFactory;
	}
	
	/**
	 * 获得 node 信息
	 * @return
	 */
	private static List<NodeInfo> getTransportNodeInfos(){
		List<NodeInfo> nodeConfigs = new ArrayList<>();
		NodeInfo nodeConfig = new NodeInfo();
		nodeConfig.setNodeName("es0");
		nodeConfig.setNodeHost("192.168.10.216");
		nodeConfig.setNodePort(9200);
		nodeConfig.setNodeSchema("http");
		nodeConfigs.add(nodeConfig);
		return nodeConfigs;
	}
	
}
