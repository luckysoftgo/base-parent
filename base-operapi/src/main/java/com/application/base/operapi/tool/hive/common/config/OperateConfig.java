package com.application.base.operapi.tool.hive.common.config;

import com.application.base.operapi.core.hdfs.HadoopConfig;

/**
 * @author : 孤狼
 * @NAME: OperateConfig
 * @DESC: 操作要使用的对象配置.
 **/
public class OperateConfig {
	
	/**
	 * hadoop相关的配置.
	 */
	private HadoopConfig hadoopConfig;
	
	/**
	 * jdbc 的配置
	 */
	private JdbcConfig jdbcConfig;
	
	/**
	 * hive 配置
	 */
	private HiveConfig hiveConfig;
	
	/**
	 * ssh配置
	 */
	private SshConfig sshConfig;
	
	public OperateConfig() {
	}
	
	public OperateConfig(HadoopConfig hadoopConfig, JdbcConfig jdbcConfig, HiveConfig hiveConfig,
	                     SshConfig sshConfig) {
		this.hadoopConfig = hadoopConfig;
		this.jdbcConfig = jdbcConfig;
		this.hiveConfig = hiveConfig;
		this.sshConfig = sshConfig;
	}
	
	public HadoopConfig getHadoopConfig() {
		return hadoopConfig;
	}
	
	public void setHadoopConfig(HadoopConfig hadoopConfig) {
		this.hadoopConfig = hadoopConfig;
	}
	
	public JdbcConfig getJdbcConfig() {
		return jdbcConfig;
	}
	
	public void setJdbcConfig(JdbcConfig jdbcConfig) {
		this.jdbcConfig = jdbcConfig;
	}
	
	public HiveConfig getHiveConfig() {
		return hiveConfig;
	}
	
	public void setHiveConfig(HiveConfig hiveConfig) {
		this.hiveConfig = hiveConfig;
	}
	
	public SshConfig getSshConfig() {
		return sshConfig;
	}
	
	public void setSshConfig(SshConfig sshConfig) {
		this.sshConfig = sshConfig;
	}
}
