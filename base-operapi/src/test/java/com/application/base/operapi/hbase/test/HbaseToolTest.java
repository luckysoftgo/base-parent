package com.application.base.operapi.hbase.test;

import com.alibaba.fastjson.JSON;
import com.application.base.operapi.tool.hbase.config.HbaseConfigFactory;
import com.application.base.operapi.tool.hbase.core.impl.BasicHbaseClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : 孤狼
 * @NAME: HbaseToolTest
 * @DESC:
 **/
public class HbaseToolTest {
	/**
	 * 测试.
	 * @param args
	 */
	public static void main(String[] args) {
		HbaseConfigFactory factory =new HbaseConfigFactory();
		factory.setHadoopDir("");
		factory.setHadoopDir("D:\\installer\\hadoop-2.7.7");
		factory.setRootDir("hdfs://manager:8020/hbase");
		//zookeeper地址 node1 (follower), node2 (follower), node3 (leader)
		factory.setZookeeperQuorum("192.168.10.186,192.168.10.187,192.168.10.188");
		// zookeeper端口
		factory.setZookeeperPort("2181");
		
		BasicHbaseClient hbaseClient = new BasicHbaseClient(factory);
		boolean result = false;
		result = hbaseClient.hasTable(HbaseDemo.class);
		if (!result){
			hbaseClient.createTable(HbaseDemo.class);
		}
		//new实体类并设置相关属性
		HbaseDemo user = new HbaseDemo();
		user.setUserId(1010001L);
		user.setUserName("caesar.zhu");
		user.setAge(30);
		Map<String,Integer> scoresMap = new HashMap();
		scoresMap.put("history",95);
		scoresMap.put("geography",98);
		scoresMap.put("math",100);
		user.setScoresMap(scoresMap);
		List<String> hobiesList = new ArrayList<>();
		hobiesList.add("basketball");
		hobiesList.add("swimming");
		hobiesList.add("shoot");
		user.setHobiesList(hobiesList);
		Set<Integer> yearsSet = new HashSet<>();
		yearsSet.add(10);
		yearsSet.add(25);
		yearsSet.add(65);
		user.setYears(yearsSet);
		user.setEnd(true);
		//添加记录
		hbaseClient.put(user);
		
		//根据key进行查询
		HbaseDemo ins = hbaseClient.get(1010001L, HbaseDemo.class);
		System.out.println(JSON.toJSONString(ins));
		//根据key判断记录是否存在
		System.out.println(hbaseClient.hasRow(1010001L,HbaseDemo.class));
		//删除指定记录
		//System.out.println(hbaseClient.delete(1010001L,HbaseDemo.class));
	}
}
