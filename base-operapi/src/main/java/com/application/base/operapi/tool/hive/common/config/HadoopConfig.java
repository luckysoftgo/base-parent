package com.application.base.operapi.tool.hive.common.config;

/**
 * @author : 孤狼
 * @NAME: HadoopConfig
 * @DESC: 关于hadoop 的配置设置.
 **/
public class HadoopConfig {
	
	/**
	 * hadoop 根信息配置.
	 */
	public final static  String HADOO_PHOME_DIR = "hadoop.home.dir";
	/**
	 * hadoop 用户名.
	 */
	public final static String HADOOP_USER_NAME = "HADOOP_USER_NAME";
	
	/**
	 * hadoop 安装地址.
	 */
	private String hadoopInstallDir;
	/**
	 * hadoop 的访问用户名
	 */
	private String hadoopUserName="hdfs";
	/**
	 * 本地文件路径.
	 */
	private String localFilePath="";
	/**
	 * 文件上传的 hdfs 上的路径.
	 */
	private String hdfsFilePath="/tmp/";
	/**
	 * hdfs 的访问地址.
	 */
	private String hdfsRequestUrl="hdfs://manager:8020";
	/**
	 * 是否保留文件.默认保留
	 */
	private boolean deleteFile=false;
	
	public HadoopConfig() {
	
	}
	
	public String getHadoopInstallDir() {
		return hadoopInstallDir;
	}
	
	public void setHadoopInstallDir(String hadoopInstallDir) {
		this.hadoopInstallDir = hadoopInstallDir;
	}
	
	public String getHadoopUserName() {
		return hadoopUserName;
	}
	
	public void setHadoopUserName(String hadoopUserName) {
		this.hadoopUserName = hadoopUserName;
	}
	
	public String getLocalFilePath() {
		return localFilePath;
	}
	
	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}
	
	public String getHdfsFilePath() {
		return hdfsFilePath;
	}
	
	public void setHdfsFilePath(String hdfsFilePath) {
		this.hdfsFilePath = hdfsFilePath;
	}
	
	public String getHdfsRequestUrl() {
		return hdfsRequestUrl;
	}
	
	public void setHdfsRequestUrl(String hdfsRequestUrl) {
		this.hdfsRequestUrl = hdfsRequestUrl;
	}
	
	public boolean isDeleteFile() {
		return deleteFile;
	}
	
	public void setDeleteFile(boolean deleteFile) {
		this.deleteFile = deleteFile;
	}
}
