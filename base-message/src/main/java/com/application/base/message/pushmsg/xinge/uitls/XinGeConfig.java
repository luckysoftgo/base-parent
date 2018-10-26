package com.application.base.message.pushmsg.xinge.uitls;

import org.springframework.stereotype.Component;


/**
 * @desc 消息账号信息配置
 * @author 孤狼
 */
@Component
public class XinGeConfig {
	/**
	 * android accessId and secretKey
	 */
	private long androidAccessId=Long.parseLong("2100249687");
	private String androidAccessKey="A95U627WUTUT";
	private String androidSecretKey="69cf040682df048df98e82d209aa46c7";
	/**
	 * ios accessId and secretKey
	 */
	private long iosAccessId=Long.parseLong("2200271444");
	private String iosAccessKey="I6LM627BMJ3Z";
	private String iosSecretKey="adfb3aee026e5635acc9871c69dd104f";
	/**
	 * ios appStore accessId and secretKey
	 */
	private long iosAccessIdAppStore=Long.parseLong("2200256968");
	private String iosAccessKeyAppStore="I93GHGM36J2Y";
	private String iosSecretKeyAppStore="112976b3fa9651c57d6e4e1384494ba4";
	
	/**
	 * Ios环境   开发证书：1 生产证书：2
	 */
	private String  iosConfigurationEnvironment="1";
	
	public long getAndroidAccessId() {
		return androidAccessId;
	}
	
	public void setAndroidAccessId(long androidAccessId) {
		this.androidAccessId = androidAccessId;
	}
	
	public String getAndroidAccessKey() {
		return androidAccessKey;
	}
	
	public void setAndroidAccessKey(String androidAccessKey) {
		this.androidAccessKey = androidAccessKey;
	}
	
	public String getAndroidSecretKey() {
		return androidSecretKey;
	}
	
	public void setAndroidSecretKey(String androidSecretKey) {
		this.androidSecretKey = androidSecretKey;
	}
	
	public long getIosAccessId() {
		return iosAccessId;
	}
	
	public void setIosAccessId(long iosAccessId) {
		this.iosAccessId = iosAccessId;
	}
	
	public String getIosAccessKey() {
		return iosAccessKey;
	}
	
	public void setIosAccessKey(String iosAccessKey) {
		this.iosAccessKey = iosAccessKey;
	}
	
	public String getIosSecretKey() {
		return iosSecretKey;
	}
	
	public void setIosSecretKey(String iosSecretKey) {
		this.iosSecretKey = iosSecretKey;
	}
	
	public long getIosAccessIdAppStore() {
		return iosAccessIdAppStore;
	}
	
	public void setIosAccessIdAppStore(long iosAccessIdAppStore) {
		this.iosAccessIdAppStore = iosAccessIdAppStore;
	}
	
	public String getIosAccessKeyAppStore() {
		return iosAccessKeyAppStore;
	}
	
	public void setIosAccessKeyAppStore(String iosAccessKeyAppStore) {
		this.iosAccessKeyAppStore = iosAccessKeyAppStore;
	}
	
	public String getIosSecretKeyAppStore() {
		return iosSecretKeyAppStore;
	}
	
	public void setIosSecretKeyAppStore(String iosSecretKeyAppStore) {
		this.iosSecretKeyAppStore = iosSecretKeyAppStore;
	}
	
	public String getIosConfigurationEnvironment() {
		return iosConfigurationEnvironment;
	}
	
	public void setIosConfigurationEnvironment(String iosConfigurationEnvironment) {
		this.iosConfigurationEnvironment = iosConfigurationEnvironment;
	}
}
