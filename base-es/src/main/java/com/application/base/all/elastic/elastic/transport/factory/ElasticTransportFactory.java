package com.application.base.all.elastic.elastic.transport.factory;

import com.application.base.all.elastic.elastic.transport.config.EsTransportNodeConfig;
import com.application.base.all.elastic.exception.ElasticException;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @NAME: ElasticTransportFactory.
 * @DESC: ES 连接池的工厂.
 * @USER: 孤狼.
 **/
public class ElasticTransportFactory implements PooledObjectFactory<TransportClient> {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private AtomicReference<Set<EsTransportNodeConfig>> nodesReference = new AtomicReference<Set<EsTransportNodeConfig>>();
	
	/**
	 * 群集名称
	 */
	private String clusterName;
	
	
	public ElasticTransportFactory(String clusterName, Set<EsTransportNodeConfig> clusterNodes) {
		this.clusterName = clusterName;
		this.nodesReference.set(clusterNodes);
	}
	
	@Override
	public PooledObject<TransportClient> makeObject() throws Exception {
		Settings settings = Settings.builder()
				// 集群名
				.put("cluster.name", clusterName)
				// 自动把集群下的机器添加到列表中:true.是;false.否
				//.put("client.transport.sniff", isAppend)
				// 忽略集群名字验证, 打开后集群名字不对也能连接上
				//.put("client.transport.ignore_cluster_name", true)
				.build();
		TransportClient settingClient = new PreBuiltTransportClient(settings);
		try {
			for (EsTransportNodeConfig each : nodesReference.get()) {
				try {
					settingClient.addTransportAddress(new TransportAddress(InetAddress.getByName(each.getNodeHost()), each.getNodePort()));
				} catch (UnknownHostException e) {
					logger.error("添加索引IP,Port出现异常,异常信息是{}",e.getMessage());
				}
			}
		}catch (Exception e){
			logger.error("初始化对象实例失败:{}",e);
			throw  new ElasticException(e);
		}
		return new DefaultPooledObject(settingClient);
	}
	
	@Override
	public void destroyObject(PooledObject<TransportClient> pooledObject) throws Exception {
		TransportClient client = pooledObject.getObject();
		if (client != null) {
			try {
				client.close();
			} catch (Exception e) {
				//ignore
			}
		}
	}
	
	@Override
	public boolean validateObject(PooledObject<TransportClient> pooledObject) {
		TransportClient client = pooledObject.getObject();
		if (client!=null){
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void activateObject(PooledObject<TransportClient> pooledObject) throws Exception {
		TransportClient client = pooledObject.getObject();
	}
	
	@Override
	public void passivateObject(PooledObject<TransportClient> pooledObject) throws Exception {
		//nothing
	}
	
	public String getClusterName() {
		return clusterName;
	}
	
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
}