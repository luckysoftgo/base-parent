package com.application.demo.task;


import com.application.base.elastic.entity.ElasticData;
import com.application.base.kylin.jdbc.factory.KylinJdbcSessionFactory;
import com.application.base.kylin.rest.api.KylinRestSession;
import com.application.base.kylin.rest.factory.KylinJestSessionFactory;
import com.application.demo.bean.CubeDescInfo;
import com.application.demo.bean.CubeInfo;
import com.application.demo.bean.DataMonitor;
import com.application.demo.cont.KylinConstant;
import com.application.demo.init.CubeInfoProvider;
import com.application.demo.init.RestClientConfig;
import com.application.demo.service.IDataMonitorService;
import com.application.demo.util.EsJestClientUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author:孤狼
 * @NAME: CubesInfoTask
 * @DESC: 获得所有的cube desc信息
 **/

@Component
@JobHandler(value="cubeDescJobHandler")
public class CubeDescInfoTask extends IJobHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private KylinJestSessionFactory jestSessionFactory;
	
	@Autowired
	private KylinJdbcSessionFactory jdbcSessionFactory;
	
	@Autowired
	private CubeInfoProvider infoProvider;
	
	@Autowired
	private EsJestClientUtils esJestClient;
	
	@Autowired
	private RestClientConfig clientConfig;
	
	@Autowired
	private IDataMonitorService dataMonitorService;
	
	@Override
	public ReturnT<String> execute(String param){
		ArrayList<CubeInfo> list = (ArrayList<CubeInfo>) infoProvider.get(KylinConstant.COBE_ARRAY);
		List<CubeDescInfo> finalList = new ArrayList<>();
		if (list!=null && list.size()>0){
			KylinRestSession restSession = jestSessionFactory.getRestSession();
			for (CubeInfo info : list) {
				String json = restSession.getCubeDesc(info.getName());
				List<CubeDescInfo> descList = infoProvider.getCubeDescInfo(json,info);
				if (descList!=null && descList.size()>0){
					finalList.addAll(descList);
				}
			}
		}
		//推送数据
		executePushData(finalList);
		//关闭连接.
		logger.info("同步数据的执行数据插入操作完成了!");
		return new ReturnT<>(ReturnT.SUCCESS_CODE,"返回成功!");
	}
	
	/**
	 * 推送数据.
	 * @param finalList
	 */
	private void executePushData(List<CubeDescInfo> finalList) {
		//拼装sql
		for (CubeDescInfo descInfo : finalList) {
			try {
				DataMonitor dataMonitor = new DataMonitor();
				dataMonitor.setCubeName(descInfo.getName());
				dataMonitor.setSchemName(descInfo.getSchem_name());
				dataMonitor.setTableName(descInfo.getTable_name());
				dataMonitor.setProjectName(descInfo.getProject());
				List<DataMonitor> list = dataMonitorService.selectDataMonitorList(dataMonitor);
				String sql = "" ;
				boolean result = false;
				if (list!=null && list.size()>0){
					dataMonitor = list.get(0);
					sql = descInfo.getFromsql()+" WHERE PROCESSING_DTTM > "+dataMonitor.getProcessingDttm()+descInfo.getGroupby();
					//修改 PROCESSING_DTTM
				}else{
					sql = descInfo.getSql();
					//添加
					result = true;
				}
				RestHighLevelClient client = esJestClient.getClient(clientConfig);
				logger.info("project="+descInfo.getProject()+",sql="+sql);
				LinkedList<Map<String, Object>> datas = jdbcSessionFactory.getJdbcSession().selectSQL(descInfo.getProject(),sql);
				List<ElasticData> esList = new ArrayList<>();
				if (datas!=null && datas.size()>0) {
					logger.info("index="+descInfo.getName()+",count = "+datas.size());
					for (int i = 0; i < datas.size(); i++) {
						Map<String, Object> data = datas.get(i);
						ElasticData elasticData = new ElasticData();
						elasticData.setMapFlag(true);
						elasticData.setMapData(data);
						elasticData.setId((i + 1 + ""));
						elasticData.setType(descInfo.getName().toLowerCase());
						elasticData.setIndex(descInfo.getName().toLowerCase());
						esList.add(elasticData);
					}
					esJestClient.addEsDataListByProcessor(client, esList, true);
					client.close();
					logger.info("es index=" + descInfo.getName() + "执行已经完成了!");
					executeProcessing(result,dataMonitor,descInfo);
				}
			}catch (Exception e){
				e.printStackTrace();
				logger.info("导入数据出现了异常,异常信息是:"+e.getMessage());
			}
		}
	}
	
	/**
	 * 操作数据.
	 * @param result
	 */
	private void executeProcessing(boolean result,DataMonitor dataMonitor,CubeDescInfo descInfo) {
		String sql = descInfo.getWhereis();
		logger.info(sql);
		LinkedList<Map<String, Object>> datas = jdbcSessionFactory.getJdbcSession().selectSQL(descInfo.getProject(),sql);
		String value = "";
		if (datas!=null && datas.size()>0){
			value = Objects.toString(datas.get(0).get("PROCESSING_DTTM"),value);
			dataMonitor.setProcessingDttm(value);
		}
		if (StringUtils.isNotBlank(value)){
			if (result){
				dataMonitor.setDisabled(0);
				dataMonitor.setCreateUser("Admin");
				dataMonitor.setCreateTime(new Date());
				dataMonitorService.insertDataMonitor(dataMonitor);
			}else{
				dataMonitor.setUpdateUser("System");
				dataMonitor.setUpdateTime(new Date());
				dataMonitorService.updateDataMonitor(dataMonitor);
			}
		}
	}
}
