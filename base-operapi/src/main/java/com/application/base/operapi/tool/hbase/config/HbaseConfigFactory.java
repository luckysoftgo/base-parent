package com.application.base.operapi.tool.hbase.config;

import com.application.base.operapi.api.hbase.core.HbaseClient;
import com.application.base.operapi.api.hbase.exception.HbaseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author : 孤狼
 * @NAME: HbaseConfigFactory
 * @DESC: 配置信息.
 **/
public class HbaseConfigFactory {
	
	private Logger logger = LoggerFactory.getLogger(HbaseConfigFactory.class.getName());
	
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
	 * 根目录:hdfs://192.168.10.185:8020/hbase
	 */
	public String rootDir;
	
	/**
	 * 最大最小值.
	 */
	private int maxTotal=100;
	private int minTotal=10;
	
	/**
	 * 声明静态配置.
	 */
	private Configuration configuration = null;
	/**
	 * postfix
	 */
	private final byte[] POSTFIX = new byte[] {0x00};
	
	/**
	 * 存在的连接.
	 */
	private LinkedBlockingQueue<Connection> connections = new LinkedBlockingQueue<>(1024);
	
	
	/**
	 * 初始化 hbase 的操作.
	 */
	public Connection hbaseConnect(){
		// windows环境下 解决hadoop调用本地库问题
		System.setProperty(HADOO_PHOME_DIR,hadoopDir);
		//使用默认的classpath下的hbase-site.xml配置
		configuration = HBaseConfiguration.create();
		// 集群的连接地址，在控制台页面的数据库连接界面获得(注意公网地址和VPC内网地址)
		configuration.set("hbase.zookeeper.quorum", zookeeperQuorum);
		configuration.set("hbase.zookeeper.property.clientPort",zookeeperPort);
		// 设置用户名密码，默认root:root，可根据实际情况调整
		if (StringUtils.isNotBlank(loginUser)){
			configuration.set("hbase.client.username",loginUser);
		}
		if (StringUtils.isNotBlank(loginPass)){
			configuration.set("hbase.client.password",loginPass);
		}
		if (StringUtils.isNotBlank(master)){
			configuration.set("hbase.master",master);
		}
		if (StringUtils.isNotBlank(rootDir)){
			configuration.set("hbase.rootdir",rootDir);
		}
		//先检查实例是否存在，如果不存在才进入下面的同步块
		Connection conn = null;
		try {
			synchronized (HbaseClient.class){
				conn = ConnectionFactory.createConnection(configuration);
				connections.put(conn);
			}
			return conn;
		} catch (Exception e) {
			logger.error("初始化连接异常了,异常信息是:{}",e.getMessage());
			throw new HbaseException("hbase获得连接异常了,异常信息是:{"+e.getMessage()+"}");
		}
	}
	
	/**
	 * 获取 connection 连接.
	 * @return
	 */
	public Connection getConnection(){
		try {
			if (connections!=null && connections.size()>0){
				if (connections.size() < minTotal){
					int addCount = maxTotal - maxTotal;
					for (int i = 0; i < addCount ; i++) {
						hbaseConnect();
					}
				}
			}else{
				for (int i = 0; i < maxTotal ; i++) {
					hbaseConnect();
				}
			}
			return connections.take();
		}catch (InterruptedException e){
			logger.error("获取connect连接异常了,错误异常是:{}",e.getMessage());
			return null;
		}
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
