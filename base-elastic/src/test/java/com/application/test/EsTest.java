package com.application.test;

import com.application.base.elastic.entity.ElasticData;
import com.application.base.elastic.entity.ElasticInfo;
import com.application.base.elastic.util.transport.EsTransportClientUtils;
import com.application.base.utils.date.DateUtils;
import com.application.base.utils.json.JsonConvertUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc es 测试
 * @author 孤狼
 */
public class EsTest {
	
	static String clusterName = "my-application";
	//index
	static String dbName = "bruce1";
	//type
	static String tableName = "brucetest1" ;
	//ips
	static String serverIPs = "192.168.2.169:9300";

	/**
	 * test 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//test1();
		//test2();
		testInfo();
	}
	
	public static void testInfo() {
		ElasticInfo  info = new ElasticInfo();
		try {
			//指定集群
			Settings settings = Settings.builder().put("cluster.name","elasticsearch").build();
			//创建客户端
			TransportClient client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.10.216"),9300));
			ClusterHealthResponse healths = client.admin().cluster().prepareHealth().get();
			String clusterName = healths.getClusterName();
			info.setEsClusterName(clusterName);
			//输出集群名
			int numberOfDataNodes = healths.getNumberOfDataNodes();
			//输出节点数量
			info.setNumberOfDataNodes(numberOfDataNodes);
			//输出每个索引信息
			List<ElasticInfo.EsItemInfo> elasticInfos = new ArrayList<>();
			for(ClusterIndexHealth health:healths.getIndices().values()) {
				String indexName = health.getIndex();
				if (indexName.startsWith(".mon") || indexName.startsWith(".kiban")){
					continue;
				}
				ElasticInfo.EsItemInfo itemInfo = new ElasticInfo().new EsItemInfo();
				int numberOfShards = health.getNumberOfShards();
				int numberOfReplicas = health.getNumberOfReplicas();
				ClusterHealthStatus clusterHealthStatus = health.getStatus();
				itemInfo.setIndexName(indexName);
				itemInfo.setNumberOfShards(numberOfShards);
				itemInfo.setNumberOfReplicas(numberOfReplicas);
				itemInfo.setClusterHealthStatus(clusterHealthStatus.toString());
				elasticInfos.add(itemInfo);
			}
			info.setElasticInfos(elasticInfos);
			client.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		System.out.println(JsonConvertUtils.toJson(info));
	}
	
	private static void test2() throws Exception {
		TransportClient client = EsTransportClientUtils.getSettingClient("/es.properties");
		ElasticData data = new ElasticData(dbName,tableName, "123456789",createJson1(),false);
		EsTransportClientUtils.addDocument(client, data);
		System.out.println("添加到ES中成功了...");
		client.close();
	}
	
	private static void test1() throws Exception {
		
		TransportClient client = EsTransportClientUtils.getParamClient(clusterName, serverIPs, false);
		ElasticData data = new ElasticData(dbName,tableName, "123456789",createJson1(),false);
		EsTransportClientUtils.addDocument(client, data);
		System.out.println("添加到ES中成功了...");
		client.close();
	}
	
	/**
	 * test data
	 * @return
	 */
	public static String createJson1() {
		Map<String, Object> map = new HashMap<String, Object>(16);
		map.put("user", "bruce");
		map.put("date", DateUtils.getCurrentTimeStr());
		map.put("msg", " trying out Elasticsearch index");
		return JsonConvertUtils.toJson(map);
	}
	
	/**
	 * test data 
	 * @return
	 */
	public static String createJson2(int count) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < count; i++) {
			Map<String, Object> map = new HashMap<String, Object>(16);
			map.put("user", "bruce"+i);
			map.put("date", DateUtils.getCurrentTimeStr());
			map.put("msg", " trying out Elasticsearch index "+i);
			list.add(map);
		}
		return JsonConvertUtils.toJson(list);
	}
}
