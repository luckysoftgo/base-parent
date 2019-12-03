package com.application.base.elastic;

import com.application.base.pool.GenericPool;

/**
 * @author : 孤狼
 * @NAME: BasicElasticPoolInfo
 * @DESC: 连接配置的共有信息
 **/
public class BasicElasticPoolInfo {
	
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
	private TransPortCluster cluster =new TransPortCluster();
	
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
	
	public TransPortCluster getCluster() {
		return cluster;
	}
	
	public void setCluster(TransPortCluster cluster) {
		this.cluster = cluster;
	}
	
	
	/**
	 * Cluster 会话
	 */
	public class TransPortCluster {
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
	public class ElasticSearchLogin {
		
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
	
}
