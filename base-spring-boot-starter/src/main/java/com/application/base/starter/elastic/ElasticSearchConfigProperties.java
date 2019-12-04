package com.application.base.starter.elastic;

import com.application.base.pool.GenericPool;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: EsRestClientConfigProperties
 * @DESC: 属性获取 https://blog.csdn.net/qq_32924343/article/details/100108734
 **/
@ConfigurationProperties(prefix = "elastic")
public class ElasticSearchConfigProperties {
	/**
	 * 连接池对象
	 */
	private GenericPool pool =new GenericPool();
	
	/**
	 * 登录
	 */
	private ElasticSearchLogin login =new ElasticSearchLogin();
	
	/**
	 * cluster 信息
	 */
	private ElasticCluster cluster =new ElasticCluster();
	
	/**
	 * 连接配置
	 */
	private List<ConnectClientInfo> transport =new ArrayList<>();
	/**
	 * 连接配置
	 */
	private List<ConnectClientInfo> restclient =new ArrayList<>();
	
	public GenericPool getPool() {
		return pool;
	}
	
	public void setPool(GenericPool pool) {
		this.pool = pool;
	}
	
	public ElasticSearchLogin getLogin() {
		return login;
	}
	
	public void setLogin(ElasticSearchLogin login) {
		this.login = login;
	}
	
	public ElasticCluster getCluster() {
		return cluster;
	}
	
	public void setCluster(ElasticCluster cluster) {
		this.cluster = cluster;
	}
	
	public List<ConnectClientInfo> getTransport() {
		return transport;
	}
	
	public void setTransport(List<ConnectClientInfo> transport) {
		this.transport = transport;
	}
	
	public List<ConnectClientInfo> getRestclient() {
		return restclient;
	}
	
	public void setRestclient(List<ConnectClientInfo> restclient) {
		this.restclient = restclient;
	}
	
	/**
	 * Cluster 会话
	 */
	public static class ElasticCluster {
		/**
		 * 集群名称
		 */
		private String name;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 登录信息.
	 */
	public static class ElasticSearchLogin {
		
		/**
		 * 登录验证信息:如 elastic:123456
		 */
		private String auth;
		
		public String getAuth() {
			return auth;
		}
		
		public void setAuth(String auth) {
			this.auth = auth;
		}
	}
	
	/**
	 * 连接对象
	 */
	public static class ConnectClientInfo {
		/**
		 * 节点名称
		 */
		private String name;
		/**
		 * 主机地址
		 */
		private String host;
		/**
		 * 端口
		 */
		private Integer port;
		/**
		 * schema
		 */
		private String schema;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getHost() {
			return host;
		}
		
		public void setHost(String host) {
			this.host = host;
		}
		
		public Integer getPort() {
			return port;
		}
		
		public void setPort(Integer port) {
			this.port = port;
		}
		
		public String getSchema() {
			return schema;
		}
		
		public void setSchema(String schema) {
			this.schema = schema;
		}
	}
	
}
