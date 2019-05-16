package com.application.base.all.elastic.elastic.rest.client;

import com.application.base.all.elastic.elastic.rest.pool.ElasticJestPool;
import com.application.base.all.elastic.exception.ElasticException;
import org.apache.http.Header;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.Serializable;

/**
 * @NAME: EsJestClient
 * @DESC: ES客户端实例.
 * @USER: 孤狼
 **/
public class EsJestClient implements Serializable {
	
	private ElasticJestPool jestPool;
	
	/**
	 * 是否能ping通服务
	 * @return
	 */
	public boolean ping(){
		RestHighLevelClient client = null;
		try{
			client = getResource();
			boolean result = client.ping();
			returnResource(client);
			return result;
		}catch(Exception e){
			returnBrokenResource(client);
			return false;
		}
	}
	
	/**
	 * 主要信息.
	 * @return
	 */
	public MainResponse info(){
		RestHighLevelClient client = null;
		try{
			client = getResource();
			MainResponse result = client.info();
			returnResource(client);
			return result;
		}catch(Exception e){
			returnBrokenResource(client);
			throw new ElasticException(e);
		}
	}
	
	/**
	 * 是否存在
	 * @param getRequest
	 * @param headers
	 * @return
	 */
	public boolean exists(GetRequest getRequest, Header... headers){
		RestHighLevelClient client = null;
		try{
			client = getResource();
			boolean result = client.exists(getRequest,headers);
			returnResource(client);
			return result;
		}catch(Exception e){
			returnBrokenResource(client);
			throw new ElasticException(e);
		}
	}
	
	/**
	 * 查询结果.
	 * @param getRequest
	 * @param headers
	 * @return
	 */
	public GetResponse get(GetRequest getRequest, Header... headers){
		RestHighLevelClient client = null;
		try{
			client = getResource();
			GetResponse result = client.get(getRequest,headers);
			returnResource(client);
			return result;
		}catch(Exception e){
			returnBrokenResource(client);
			throw new ElasticException(e);
		}
	}
	
	/**
	 * 获取资源.
	 * @return
	 */
	public RestHighLevelClient getResource(){
		RestHighLevelClient client = null;
		try{
			client = jestPool.getResource();
			return client;
		}catch(RuntimeException e){
			if(client!=null) {
				returnBrokenResource(client);
			}
			throw new ElasticException(e);
		}
	}
	
	public void returnResource(RestHighLevelClient client){
		try{
			if(client!=null){
				this.jestPool.returnResource(client);
			}
		}catch(Exception e){
			this.jestPool.returnBrokenResource(client);
		}
	}
	
	public void returnBrokenResource(RestHighLevelClient client){
		jestPool.returnBrokenResource(client);
	}
	
	public ElasticJestPool getJestPool() {
		return jestPool;
	}
	public void setJestPool(ElasticJestPool jestPool) {
		this.jestPool = jestPool;
	}
	
}
