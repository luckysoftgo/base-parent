package com.application.test;

import com.application.base.all.util.ElasticData;
import com.application.base.all.util.EsClientUtils;
import com.application.base.utils.date.DateUtils;
import com.application.base.utils.json.JsonConvertUtils;
import org.elasticsearch.client.transport.TransportClient;

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
		test2();
	}
	
	private static void test2() throws Exception {
		TransportClient client = EsClientUtils.getSettingClient("/es.properties");
		ElasticData data = new ElasticData(dbName,tableName, "123456789",createJson1());
		EsClientUtils.addDocument(client, data);
		System.out.println("添加到ES中成功了...");
		client.close();
	}
	
	private static void test1() throws Exception {
		
		TransportClient client = EsClientUtils.getParamClient(clusterName, serverIPs, false);
		ElasticData data = new ElasticData(dbName,tableName, "123456789",createJson1());
		EsClientUtils.addDocument(client, data);
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
