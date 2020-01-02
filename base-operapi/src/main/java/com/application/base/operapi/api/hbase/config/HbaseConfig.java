package com.application.base.operapi.api.hbase.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author : 孤狼
 * @NAME: HbaseConfig
 * @DESC: hbase 的配置.
 **/
public class HbaseConfig extends GenericObjectPoolConfig {
	
	/**
	 * hadoop 根信息配置.
	 */
	public final static  String HADOO_PHOME_DIR = "hadoop.home.dir";
	
	/**
	 * hadoop的安装地址.
	 */
	public String hadoopDir;
	/**
	 * zookeeper的连接地址.
	 */
	public String zookeeperQuorum;
	
	/**
	 * zookeeper的端口.
	 */
	public String zookeeperPort;
	
	/**
	 * 登录的用户名.
	 */
	public String loginUser;
	
	/**
	 * 登录的密码.
	 */
	public String loginPass;
	/**
	 * 主机设置.
	 */
	public String master;
	/**
	 * 根目录:hdfs://10.23.12.183:8020/hbase
	 */
	public String rootDir;
	
	public HbaseConfig() {
	}
	
	public HbaseConfig(String zookeeperQuorum, String zookeeperPort) {
		this.zookeeperQuorum = zookeeperQuorum;
		this.zookeeperPort = zookeeperPort;
	}
	
	public HbaseConfig(String hadoopDir, String zookeeperQuorum, String zookeeperPort) {
		this.hadoopDir = hadoopDir;
		this.zookeeperQuorum = zookeeperQuorum;
		this.zookeeperPort = zookeeperPort;
	}
	
	public HbaseConfig(String zookeeperQuorum, String zookeeperPort, String loginUser, String loginPass) {
		this.zookeeperQuorum = zookeeperQuorum;
		this.zookeeperPort = zookeeperPort;
		this.loginUser = loginUser;
		this.loginPass = loginPass;
	}
	
	public HbaseConfig(String hadoopDir, String zookeeperQuorum, String zookeeperPort, String loginUser,
	                   String loginPass) {
		this.hadoopDir = hadoopDir;
		this.zookeeperQuorum = zookeeperQuorum;
		this.zookeeperPort = zookeeperPort;
		this.loginUser = loginUser;
		this.loginPass = loginPass;
	}
	
	public HbaseConfig(String hadoopDir, String zookeeperQuorum, String zookeeperPort, String loginUser,
	                   String loginPass, String master, String rootDir) {
		this.hadoopDir = hadoopDir;
		this.zookeeperQuorum = zookeeperQuorum;
		this.zookeeperPort = zookeeperPort;
		this.loginUser = loginUser;
		this.loginPass = loginPass;
		this.master = master;
		this.rootDir =rootDir;
	}
	
	public String getHadoopDir() {
		return hadoopDir;
	}
	
	public void setHadoopDir(String hadoopDir) {
		this.hadoopDir = hadoopDir;
	}
	
	public String getZookeeperQuorum() {
		return zookeeperQuorum;
	}
	
	public void setZookeeperQuorum(String zookeeperQuorum) {
		this.zookeeperQuorum = zookeeperQuorum;
	}
	
	public String getZookeeperPort() {
		return zookeeperPort;
	}
	
	public void setZookeeperPort(String zookeeperPort) {
		this.zookeeperPort = zookeeperPort;
	}
	
	public String getLoginUser() {
		return loginUser;
	}
	
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	
	public String getLoginPass() {
		return loginPass;
	}
	
	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}
	
	public String getMaster() {
		return master;
	}
	
	public void setMaster(String master) {
		this.master = master;
	}
	
	public String getRootDir() {
		return rootDir;
	}
	
	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}
}
