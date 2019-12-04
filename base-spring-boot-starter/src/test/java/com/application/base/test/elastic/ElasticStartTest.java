package com.application.base.test.elastic;

import com.application.base.elastic.elastic.restclient.factory.EsRestClientSessionPoolFactory;
import com.application.base.elastic.elastic.transport.factory.EsTransportSessionPoolFactory;
import com.application.base.elastic.entity.ElasticData;
import com.application.base.test.BasicStartTest;
import com.application.base.utils.json.JsonConvertUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: ElasticStartTest
 * @DESC: es 测试
 **/
public class ElasticStartTest extends BasicStartTest {
	
	@Autowired
	private EsRestClientSessionPoolFactory restClient;
	
	@Autowired
	private EsTransportSessionPoolFactory transport;
	
	@Test
	public void restClient(){
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
			boolean flag = restClient.getElasticSession().addEsData(data);
			System.out.printf("index="+i+",flag="+flag);
		}
	}
	
	@Test
	public void transport(){
		try {
			for (int i = 500; i <1000 ; i++) {
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
				boolean flag = transport.getElasticSession().addEsData(data);
				System.out.printf("index="+i+",flag="+flag);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
