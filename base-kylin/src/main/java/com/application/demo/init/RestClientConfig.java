package com.application.demo.init;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : 孤狼
 * @NAME: RestClientConfig
 * @DESC:
 **/
@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "jest")
public class RestClientConfig {
	
	private String clusterNname;
	private String serverIps;
	private String http;
	
	public String getClusterNname() {
		return clusterNname;
	}
	
	public void setClusterNname(String clusterNname) {
		this.clusterNname = clusterNname;
	}
	
	public String getServerIps() {
		return serverIps;
	}
	
	public void setServerIps(String serverIps) {
		this.serverIps = serverIps;
	}
	
	public String getHttp() {
		return http;
	}
	
	public void setHttp(String http) {
		this.http = http;
	}
}
