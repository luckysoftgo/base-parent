package com.application.base.elastic.transport;

import com.application.base.elastic.BasicElasticPoolInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: EsTransPortConfigProperties
 * @DESC: 属性获取 https://blog.csdn.net/qq_32924343/article/details/100108734
 **/
@ConfigurationProperties("elastic")
public class EsTransPortConfigProperties extends BasicElasticPoolInfo {
	/**
	 * 连接配置
	 */
	private List<TransPortInfo> transport =new ArrayList<>();
	
	public List<TransPortInfo> getTransport() {
		return transport;
	}
	
	public void setTransport(List<TransPortInfo> transport) {
		this.transport = transport;
	}

	/**
	 * 连接对象
	 */
	public class TransPortInfo {
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
