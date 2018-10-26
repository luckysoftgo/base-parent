package com.application.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.application.base.all.es.EsData;
import com.application.base.utils.json.JsonConvertUtils;
import org.elasticsearch.client.transport.TransportClient;

import com.application.base.all.es.EsClientUtils;
import com.application.base.utils.date.DateUtils;

/**
 * @desc es 测试
 * @author 孤狼
 */
public class EsTest {

	/**
	 * test 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String clusterName = "elasticsearch";
		String dbName = "testes";
		String tableName = "testtable" ;
		String serverIPs = "127.0.0.1:9300";
		
		TransportClient client = EsClientUtils.getParamClient(clusterName, serverIPs, false);
		boolean result = EsClientUtils.isExistsIndex(client, dbName);
		if (!result) {
			EsClientUtils.addDBName(client, dbName);
		}
		result = EsClientUtils.isExistsType(client, dbName, tableName);
		if (!result) {
			EsClientUtils.addTableName(client, dbName, tableName);
		}
		result = EsClientUtils.isExistsType(client, dbName, tableName);
		
		EsData data = new EsData(dbName,tableName, "123456789",createJson(5));
		EsClientUtils.addDocument(client, data);
		System.out.println("添加到ES中成功了...");
		
		//获取已经放入的 es data
		List<EsData> datas = EsClientUtils.searcher(client,null, dbName, tableName);
		System.out.println(JsonConvertUtils.toJson(datas));
		for (int i = 0; i < datas.size(); i++) {
			System.out.println(JsonConvertUtils.toJson(datas.get(i)));
		}
		
	}
	
	/**
	 * test data 
	 * @return
	 */
	public static String createJson(int count) {
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
