package com.application.base.kylin.jdbc.session;

import com.application.base.exception.KylinException;
import com.application.base.kylin.jdbc.api.KylinJdbcSession;
import com.application.base.kylin.jdbc.core.KylinJdbcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: KylinJdbcOperSession
 * @DESC: jdbc操作数据库实现
 **/
public class KylinJdbcOperSession implements KylinJdbcSession {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private KylinJdbcClient kylinJdbcClient;
	
	public KylinJdbcClient getKylinJdbcClient() {
		return kylinJdbcClient;
	}
	
	public void setKylinJdbcClient(KylinJdbcClient kylinJdbcClient) {
		this.kylinJdbcClient = kylinJdbcClient;
	}
	
	@Override
	public LinkedList<Map<String,Object>> selectSQL(String projectName, String sql, String[] param) {
		try {
			return kylinJdbcClient.selectSQL(projectName,sql,param);
		}catch (KylinException e){
			logger.error("kylin执行jdbc方式查询数据失败,项目名称是:{},失败信息是:{}",projectName,e.getMessage());
		}
		return null;
	}
	
	@Override
	public LinkedList<Map<String,Object>> selectSQL(String projectName, String sql) {
		try {
			return kylinJdbcClient.selectSQL(projectName,sql);
		}catch (KylinException e){
			logger.error("kylin执行jdbc方式查询数据失败,项目名称是:{},失败信息是:{}",projectName,e.getMessage());
		}
		return null;
	}
	
	@Override
	public LinkedList<Map<String,Object>> selectMetaSQL(String projectName, String tableName) {
		try {
			return kylinJdbcClient.selectMetaSQL(projectName,tableName);
		}catch (KylinException e){
			logger.error("kylin执行jdbc方式查询meta数据失败,项目名称是:{},失败信息是:{}",projectName,e.getMessage());
		}
		return null;
	}

}
