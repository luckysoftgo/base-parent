package com.application.base.operapi.phoenix.test;

import com.alibaba.fastjson.JSON;
import com.application.base.operapi.api.phoenix.config.PhoenixConfig;
import com.application.base.operapi.api.phoenix.factory.PhoenixOperSessionFactory;
import com.application.base.operapi.api.phoenix.pool.PhoenixOperPool;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author ：admin
 * @date ：2020/11/24
 * @description: 服务测试.
 * @modified By：
 * @version: 1.0.0
 */
public class PhoenixTest {
	
	public static void main(String[] args) {
		PhoenixConfig config = phoenixConfig();
		PhoenixOperPool operPool = getPool(config);
		PhoenixOperSessionFactory factory = getFactory(operPool);
		String sql = " select * from hive_dest_production.SHYX_FC_DEPT limit 89000";
		System.out.println(sql);
		LinkedList<Map<String, Object>> data = factory.getPhoenixxSession().selectTable(sql);
		System.out.println(JSON.toJSONString(data));
	}
	
	/**
	 * 得到 config
	 */
	public static PhoenixConfig phoenixConfig() {
		PhoenixConfig config = new PhoenixConfig();
		config.setPhoenixDriver("org.apache.phoenix.jdbc.PhoenixDriver");
		config.setPhoenixUrl("jdbc:phoenix:172.32.1.7:2181");
		return config;
	}
	
	/**
	 * 得到连接池.
	 *
	 * @param config
	 * @return
	 */
	public static PhoenixOperPool getPool(PhoenixConfig config) {
		PhoenixOperPool operPool = new PhoenixOperPool(config);
		return operPool;
	}
	
	/**
	 * 操作对象.
	 *
	 * @param operPool
	 * @return
	 */
	public static PhoenixOperSessionFactory getFactory(PhoenixOperPool operPool) {
		PhoenixOperSessionFactory sessionFactory = new PhoenixOperSessionFactory(operPool);
		return sessionFactory;
	}
}
