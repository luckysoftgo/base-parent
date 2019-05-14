package com.application.base.all.elastic.elastic.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @NAME: ElasticFactory
 * @DESC: elastic 工厂.
 * @USER: 孤狼
 **/
public class ElasticFactory implements PooledObjectFactory<TransportClient> {
	
	private String clusterName;
	private String host="127.0.0.1";
	private int port=9300;
	private String username="elastic";
	private String password="elastic";
	private String serverIps="127.0.0.1:9300";
	
	public ElasticFactory(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public ElasticFactory(String clusterName, String host, int port) {
		this.clusterName = clusterName;
		this.host = host;
		this.port = port;
	}
	
	public ElasticFactory(String host, int port, String username, String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	public ElasticFactory(String clusterName, String host, int port, String username, String password) {
		this.clusterName = clusterName;
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	public ElasticFactory(String serverIps) {
		this.serverIps = serverIps;
	}
	
	public ElasticFactory(String clusterName, String serverIps) {
		this.clusterName = clusterName;
		this.serverIps = serverIps;
	}
	
	public ElasticFactory(String username, String password, String serverIps) {
		this.username = username;
		this.password = password;
		this.serverIps = serverIps;
	}
	
	public ElasticFactory(String clusterName, String username, String password, String serverIps) {
		this.clusterName = clusterName;
		this.username = username;
		this.password = password;
		this.serverIps = serverIps;
	}
	
	@Override
	public PooledObject<TransportClient> makeObject() throws Exception {
		Settings settings = Settings.EMPTY;
		if (!StringUtils.isEmpty(clusterName)){
			settings = Settings.builder()
			// 集群名
				.put("cluster.name", clusterName)
					// 自动把集群下的机器添加到列表中:true.是;false.否
					//.put("client.transport.sniff", isAppend)
					// 忽略集群名字验证, 打开后集群名字不对也能连接上
					//.put("client.transport.ignore_cluster_name", true)
					.build();
		}
		TransportClient settingClient = null;
		if (StringUtils.isEmpty(serverIps)){
			settingClient = new PreBuiltTransportClient(settings);
			//节点信息
			Map<String, Integer> nodeMap = parseNodeIps(serverIps);
			for (Map.Entry<String, Integer> entry : nodeMap.entrySet()) {
				try {
					settingClient.addTransportAddress(new TransportAddress(InetAddress.getByName(entry.getKey()), entry.getValue()));
				} catch (UnknownHostException e) {
				}
			}
		}else{
			settingClient = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(InetAddress.getByName(host),port));
		}
		return new DefaultPooledObject(settingClient);
	}
	
	@Override
	public void destroyObject(PooledObject<TransportClient> pooledObject) throws Exception {
	
	}
	
	@Override
	public boolean validateObject(PooledObject<TransportClient> pooledObject) {
		return false;
	}
	
	@Override
	public void activateObject(PooledObject<TransportClient> pooledObject) throws Exception {
	
	}
	
	@Override
	public void passivateObject(PooledObject<TransportClient> pooledObject) throws Exception {
	
	}
	
	
	/**
	 * 解析节点IP信息,多个节点用逗号隔开,IP和端口用冒号隔开
	 * @return
	 */
	private Map<String, Integer> parseNodeIps(String serverIPs) {
		String[] nodeIpInfoArr = serverIPs.split(",");
		Map<String, Integer> resultMap = new HashMap<String, Integer>(nodeIpInfoArr.length);
		for (String ipInfo : nodeIpInfoArr) {
			String[] ipInfoArr = ipInfo.split(":");
			resultMap.put(ipInfoArr[0], Integer.parseInt(ipInfoArr[1]));
		}
		return resultMap;
	}
}