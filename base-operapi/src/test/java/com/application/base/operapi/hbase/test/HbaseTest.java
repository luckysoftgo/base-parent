package com.application.base.operapi.hbase.test;

import com.alibaba.fastjson.JSON;
import com.application.base.operapi.api.hbase.config.HbaseConfig;
import com.application.base.operapi.api.hbase.core.HbaseBean;
import com.application.base.operapi.api.hbase.factory.HbaseOperSessionFactory;
import com.application.base.operapi.api.hbase.pool.HbaseOperPool;

import java.util.List;

/**
 * @author : 孤狼
 * @NAME: HbaseTest
 * @DESC: hbase 测试
 **/
public class HbaseTest {
	
	/**
	 * 测试.
	 * @param args
	 */
	public static void main(String[] args) {
		HbaseConfig config = hbaseConfig();
		HbaseOperPool operPool = getPool(config);
		HbaseOperSessionFactory sessionFactory = getFactory(operPool);
		boolean result = sessionFactory.getHbaseSession().tableExist("wangbo");
		/*
		System.out.println("result 1 ="+result);
		result = sessionFactory.getHbaseSession().tableExist("Test");
		System.out.println("result 2 ="+result);
		result = sessionFactory.getHbaseSession().createTable("hbase_test","hbase");
		System.out.println("result 3 ="+result);
		result=sessionFactory.getHbaseSession().deleteTable( "hbase_test");
		System.out.println("result 4 ="+result);
		List<String> columnFamilies = new ArrayList<>();
		columnFamilies.add("cf1");
		columnFamilies.add("cf2");
		result=sessionFactory.getHbaseSession().createTable("tbl_abc",columnFamilies);
		System.out.println("result 5 ="+result);
		String[] columns =new String[]{"value1","value2","value3","value4","value5","value6"};
		String[] values =new String[]{"10001","10002","10003","10004","10005","10006"};
		result=sessionFactory.getHbaseSession().insertOrUpdate( "tbl_abc","rowKey1","cf1",columns,values);
		System.out.println("result 6 ="+result);
		List<HbaseBean> dataMap = sessionFactory.getHbaseSession().getResultScanner("tbl_abc");
		System.out.println(dataMap);
		String resultStr = sessionFactory.getHbaseSession().getValData("tbl_abc","rowKey1","cf1","value1");
		System.out.println(resultStr);
		List<HbaseBean> dataList = sessionFactory.getHbaseSession().getRowData("tbl_abc","rowKey1");
		System.out.println(dataList);
		List<HbaseBean> dataList = sessionFactory.getHbaseSession().getResultScannerRowFilter("tbl_abc", CompareOperator.GREATER_OR_EQUAL,"rowKey1");
		System.out.println(JSON.toJSONString(dataList));
		String[] columns =new String[]{"value11","value12","value13","value14","value15","value16"};
		String[] values =new String[]{"100011","100012","100013","100014","100015","100016"};
		result=sessionFactory.getHbaseSession().insertBatchMore( "tbl_abc","rowKey1","cf1",columns,values);
		System.out.println(result);
		result = sessionFactory.getHbaseSession().insertOne("tbl_abc","rowKey1","cf1","value22","100022");
		System.out.println("result="+result);
		result = sessionFactory.getHbaseSession().addColumnFamily("tbl_abc","cf3");
		System.out.println("result="+result);
		String[] columns =new String[]{"value1","value2","value3","value4","value5","value6"};
		String[] values =new String[]{"10001","10002","10003","10004","10005","10006"};
		sessionFactory.getHbaseSession().insertOrUpdate( "tbl_abc","rowKey1","cf2",columns,values);
		sessionFactory.getHbaseSession().insertOrUpdate( "tbl_abc","rowKey1","cf3",columns,values);
		*/
		List<HbaseBean> dataList = sessionFactory.getHbaseSession().getTableData("tbl_abc");
		System.out.println(JSON.toJSONString(dataList));
	}
	
	/**
	 * 得到config
	 */
	public static HbaseConfig hbaseConfig(){
		HbaseConfig config = new HbaseConfig();
		config.setHadoopDir("D:\\installer\\hadoop-2.7.7");
		config.setRootDir("hdfs://manager:8020/hbase");
		//zookeeper地址 node1 (follower), node2 (follower), node3 (leader)
		config.setZookeeperQuorum("192.168.10.186,192.168.10.187,192.168.10.188");
		// zookeeper端口
		config.setZookeeperPort("2181");
		return config;
	}
	
	/**
	 * 得到连接池.
	 * @param config
	 * @return
	 */
	public static HbaseOperPool getPool(HbaseConfig config){
		HbaseOperPool operPool = new HbaseOperPool(config);
		return operPool;
	}
	
	/**
	 * 操作对象.
	 * @param operPool
	 * @return
	 */
	public static HbaseOperSessionFactory getFactory(HbaseOperPool operPool){
		HbaseOperSessionFactory sessionFactory = new HbaseOperSessionFactory(operPool);
		return sessionFactory;
	}
	
}