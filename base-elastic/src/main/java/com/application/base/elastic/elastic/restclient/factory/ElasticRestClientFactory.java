package com.application.base.elastic.elastic.restclient.factory;

import com.application.base.elastic.elastic.restclient.config.EsRestClientNodeConfig;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @NAME: ElasticTransportFactory.
 * @DESC: ES 连接池的工厂.
 * @USER: 孤狼.
 **/
public class ElasticRestClientFactory implements PooledObjectFactory<RestHighLevelClient> {
	
	private AtomicReference<Set<EsRestClientNodeConfig>> nodesReference = new AtomicReference<Set<EsRestClientNodeConfig>>();
	
	/**
	 * 群集名称
	 */
	private String clusterName;
	
	public ElasticRestClientFactory(String clusterName, Set<EsRestClientNodeConfig> clusterNodes) {
		this.clusterName = clusterName;
		this.nodesReference.set(clusterNodes);
	}
	
	@Override
	public PooledObject<RestHighLevelClient> makeObject() throws Exception {
		HttpHost[] nodes = new HttpHost[nodesReference.get().size()];
		List<HttpHost> nodeList = new ArrayList<HttpHost>();
		for (EsRestClientNodeConfig each : nodesReference.get()) {
			nodeList.add(new HttpHost(each.getNodeHost(), each.getNodePort(), each.getNodeSchema()));
		}
		nodes = nodeList.toArray(nodes);
		RestClientBuilder clientBuilder = RestClient.builder(nodes);
		RestHighLevelClient client = new RestHighLevelClient(clientBuilder);
		return new DefaultPooledObject(client);
	}
	
	@Override
	public void destroyObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
		RestHighLevelClient client = pooledObject.getObject();
		if (client != null && client.ping(RequestOptions.DEFAULT)) {
			try {
				client.close();
			} catch (Exception e) {
				//ignore
			}
		}
	}
	
	@Override
	public boolean validateObject(PooledObject<RestHighLevelClient> pooledObject) {
		RestHighLevelClient client = pooledObject.getObject();
		try {
			return client.ping(RequestOptions.DEFAULT);
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public void activateObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
		RestHighLevelClient client = pooledObject.getObject();
		boolean response = client.ping(RequestOptions.DEFAULT);
	}
	
	@Override
	public void passivateObject(PooledObject<RestHighLevelClient> pooledObject) throws Exception {
		//nothing
	}
	
	public String getClusterName() {
		return clusterName;
	}
	
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
}