package com.application.base.kylin.rest.session;

import com.application.base.kylin.exception.KylinException;
import com.application.base.kylin.rest.api.KylinRestSession;
import com.application.base.kylin.rest.core.KylinRestApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : 孤狼
 * @NAME: KylinRestApiSession
 * @DESC: 接口的实现.
 **/
public class KylinRestApiSession implements KylinRestSession {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private KylinRestApiClient kylinRestApiClient;

	public KylinRestApiClient getKylinRestApiClient() {
		return kylinRestApiClient;
	}
	
	public void setKylinRestApiClient(KylinRestApiClient kylinRestApiClient) {
		this.kylinRestApiClient = kylinRestApiClient;
	}
	
	@Override
	public boolean authToken() {
		try {
			return kylinRestApiClient.authToken();
		}catch (KylinException e){
			logger.error("认证出错,错误信息是:{}",e.getMessage());
		}
		return false;
	}
	
	@Override
	public String login() {
		try {
			return kylinRestApiClient.login();
		}catch (KylinException e){
			logger.error("登录出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String query(String sql) {
		try {
			return kylinRestApiClient.query(sql);
		}catch (KylinException e){
			logger.error("查询出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String query(String sql,Integer offset,Integer limit,Boolean acceptPartial, String projectName) {
		try {
			return kylinRestApiClient.query(sql,offset,limit,acceptPartial,projectName);
		}catch (KylinException e){
			logger.error("查询出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String queryTables(String projectName) {
		try {
			return kylinRestApiClient.queryTables(projectName);
		}catch (KylinException e){
			logger.error("列出可供查询的表出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String listCubes(int offset, int limit, String cubeName, String projectName) {
		try {
			return kylinRestApiClient.listCubes(offset,limit,cubeName,projectName);
		}catch (KylinException e){
			logger.error("列出可供查询的cube名字出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String getCube(String cubeName) {
		try {
			return kylinRestApiClient.getCube(cubeName);
		}catch (KylinException e){
			logger.error("获取cube出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String getCubeDesc(String cubeName) {
		try {
			return kylinRestApiClient.getCubeDesc(cubeName);
		}catch (KylinException e){
			logger.error("获取cube的描述信息出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String getDataModel(String modelName) {
		try {
			return kylinRestApiClient.getDataModel(modelName);
		}catch (KylinException e){
			logger.error("获取数据的模式出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String buildCube(String cubeName) {
		try {
			return kylinRestApiClient.buildCube(cubeName);
		}catch (KylinException e){
			logger.error("构建cube出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String enableCube(String cubeName) {
		try {
			return kylinRestApiClient.enableCube(cubeName);
		}catch (KylinException e){
			logger.error("开启cube出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String disableCube(String cubeName) {
		try {
			return kylinRestApiClient.disableCube(cubeName);
		}catch (KylinException e){
			logger.error("关闭cube出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String purgeCube(String cubeName) {
		try {
			return kylinRestApiClient.purgeCube(cubeName);
		}catch (KylinException e){
			logger.error("禁用cube出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String resumeJob(String jobId) {
		try {
			return kylinRestApiClient.resumeJob(jobId);
		}catch (KylinException e){
			logger.error("恢复任务出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String pauseJob(String jobId) {
		try {
			return kylinRestApiClient.pauseJob(jobId);
		}catch (KylinException e){
			logger.error("停用job出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String cancelJob(String jobId) {
		try {
			return kylinRestApiClient.cancelJob(jobId);
		}catch (KylinException e){
			logger.error("取消job出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String getJobStatus(String jobId) {
		try {
			return kylinRestApiClient.getJobStatus(jobId);
		}catch (KylinException e){
			logger.error("获取job状态出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String getJobStepOutput(String jobId, String stepId) {
		try {
			return kylinRestApiClient.getJobStepOutput(jobId,stepId);
		}catch (KylinException e){
			logger.error("获取job每一步的输出出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String getHiveTable(String tableName) {
		try {
			return kylinRestApiClient.getHiveTable(tableName);
		}catch (KylinException e){
			logger.error("获取hive的表出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String getHiveTableInfo(String tableName) {
		try {
			return kylinRestApiClient.getHiveTableInfo(tableName);
		}catch (KylinException e){
			logger.error("获取hive的表出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String getHiveTables(String project, boolean extend) {
		try {
			return kylinRestApiClient.getHiveTables(project,extend);
		}catch (KylinException e){
			logger.error(" 获取hive的所有表出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String loadHiveTables(String tables, String projectName) {
		try {
			return kylinRestApiClient.loadHiveTables(tables,projectName);
		}catch (KylinException e){
			logger.error("加载hive表的所有信息出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String initCubeStartOffSet(String cubeName) {
		try {
			return kylinRestApiClient.initCubeStartOffSet(cubeName);
		}catch (KylinException e){
			logger.error("设置cube的启动位置出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String buildSteam(String cubeName) {
		try {
			return kylinRestApiClient.buildSteam(cubeName);
		}catch (KylinException e){
			logger.error("构建流式cube出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String checkCubeHole(String cubeName) {
		try {
			return kylinRestApiClient.checkCubeHole(cubeName);
		}catch (KylinException e){
			logger.error("检查阶段孔出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String fillCubeHole(String cubeName) {
		try {
			return kylinRestApiClient.fillCubeHole(cubeName);
		}catch (KylinException e){
			logger.error("填充段孔出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
	
	@Override
	public String getCubeSql(String cubeName) throws KylinException {
		try {
			return kylinRestApiClient.getCubeSql(cubeName);
		}catch (KylinException e){
			logger.error("获得Cube中的SQL 出错,错误信息是:{}",e.getMessage());
		}
		return null;
	}
}
