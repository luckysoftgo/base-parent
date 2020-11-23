package com.application.base.operapi.api.phoenix.session;

import com.application.base.operapi.api.phoenix.api.PhoenixSession;
import com.application.base.operapi.api.phoenix.core.PhoenixClient;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: PhoenixOperSession
 * @DESC: 实际的实现.
 **/
public class PhoenixOperSession implements PhoenixSession {
	
	private PhoenixClient phoenixClient;
	
	public PhoenixClient getPhoenixClient() {
		return phoenixClient;
	}
	
	public void setPhoenixClient(PhoenixClient phoenixClient) {
		this.phoenixClient = phoenixClient;
	}
	
	@Override
	public LinkedList<Map<String, Object>> selectTable(String sql) {
		return phoenixClient.selectTable(sql);
	}
	
	@Override
	public boolean execSql(String sql) {
		return phoenixClient.execSql(sql);
	}
}
