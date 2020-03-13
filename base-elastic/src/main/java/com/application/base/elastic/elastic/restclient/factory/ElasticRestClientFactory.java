package com.application.base.elastic.elastic.restclient.factory;

import com.application.base.elastic.elastic.restclient.config.EsRestClientNodeConfig;
import com.application.base.elastic.elastic.restclient.config.EsRestClientPoolConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
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
	/**
	 * 认证用户名
	 */
	private String authName;
	/**
	 * 认证密码
	 */
	private String authPass;
	
	public ElasticRestClientFactory(EsRestClientPoolConfig restPoolConfig) {
		this.clusterName = restPoolConfig.getClusterName();
		this.nodesReference.set(restPoolConfig.getEsNodes());
		this.authName = restPoolConfig.getAuthName();
		this.authPass = restPoolConfig.getAuthPass();
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
		//安全认证.
		if (StringUtils.isNotBlank(authName) && StringUtils.isNotBlank(authPass)){
			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			//es账号密码（默认用户名为elastic）
			credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(authName, authPass));
			//添加认证信息.
			clientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
				@Override
				public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
					httpClientBuilder.disableAuthCaching();
					return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
				}
			});
		}
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