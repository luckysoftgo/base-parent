package com.application.base.core.datasource.impl.common;

import com.application.base.elastic.elastic.query.EsQueryBuilderInstance;
import com.application.base.elastic.entity.ElasticData;
import com.application.base.elastic.exception.ElasticException;
import com.application.base.elastic.factory.ElasticSessionFactory;
import com.application.base.core.datasource.session.EsSession;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;

import java.util.List;

/**
 * @NAME: DefaultEsDataSession
 * @DESC:
 * @USER: 孤狼
 * @DATE: 2019年7月1日
 **/
public class DefaultEsDataSession implements EsSession {
	
	/**
	 * 操作的实例工厂.
	 */
	private ElasticSessionFactory sessionFactory;
	
	public ElasticSessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(ElasticSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public DefaultEsDataSession(ElasticSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public boolean isIndexExist(String index) throws ElasticException {
		return sessionFactory.getElasticSession().judgeIndexExist(index);
	}
	
	@Override
	public boolean isTypeExist(String index, String type) throws ElasticException {
		return sessionFactory.getElasticSession().judgeTypeExist(index,type);
	}
	
	@Override
	public boolean addIndex(String index) throws ElasticException {
		return sessionFactory.getElasticSession().addEsIndex(index);
	}
	
	@Override
	public boolean delIndex(String index) throws ElasticException {
		return sessionFactory.getElasticSession().deleteIndex(index);
	}
	
	@Override
	public boolean addType(String index, String type) throws ElasticException {
		return sessionFactory.getElasticSession().addEsType(index,type);
	}
	
	@Override
	public String getDataById(ElasticData data) throws ElasticException {
		return sessionFactory.getElasticSession().getDataById(data);
	}
	
	@Override
	public ElasticData getDataInfoById(ElasticData data) throws ElasticException {
		return sessionFactory.getElasticSession().getDataInfoById(data);
	}
	
	@Override
	public boolean addData(ElasticData data) throws ElasticException {
		return sessionFactory.getElasticSession().addEsData(data);
	}
	
	@Override
	public boolean addDataList(List<ElasticData> elasticData, boolean async) throws ElasticException {
		return sessionFactory.getElasticSession().addEsDataList(elasticData,async);
	}
	
	@Override
	public boolean addDataListByProcessor(List<ElasticData> elasticData, boolean async) throws ElasticException {
		return sessionFactory.getElasticSession().addEsDataListByProcessor(elasticData,async);
	}
	
	@Override
	public boolean updateData(ElasticData data) throws ElasticException {
		return sessionFactory.getElasticSession().updateEsData(data);
	}
	
	@Override
	public boolean updateDataList(List<ElasticData> elasticData, boolean async) throws ElasticException {
		return sessionFactory.getElasticSession().updateEsDataList(elasticData,async);
	}
	
	@Override
	public boolean updateDataListByProcessor(List<ElasticData> elasticData, boolean async) throws ElasticException {
		return sessionFactory.getElasticSession().updateEsDataListByProcessor(elasticData,async);
	}
	
	@Override
	public boolean deleteData(ElasticData data) throws ElasticException {
		return sessionFactory.getElasticSession().deleteEsData(data);
	}
	
	@Override
	public boolean deleteDataList(List<ElasticData> elasticData, boolean async) throws ElasticException {
		return sessionFactory.getElasticSession().deleteEsDataList(elasticData,async);
	}
	
	@Override
	public boolean deleteDataListByProcessor(List<ElasticData> elasticData, boolean async) throws ElasticException {
		return sessionFactory.getElasticSession().deleteEsDataListByProcessor(elasticData,async);
	}
	
	@Override
	public List<ElasticData> searcher(QueryBuilder queryBuilder, String index, String type) throws ElasticException {
		return sessionFactory.getElasticSession().searcher(queryBuilder,index,type);
	}
	
	@Override
	public List<ElasticData> searcher(String index, String type, QueryBuilder boolQuery, List<FieldSortBuilder> sortBuilders, int pageNo, int pageSize) throws ElasticException {
		return sessionFactory.getElasticSession().searcher(index,type,boolQuery,sortBuilders,pageNo,pageSize);
	}
	
	@Override
	public List<ElasticData> searcher(String index, String type, String[] keyWords, String[] keyVals, int pageNo,
	                                  int pageSize) throws ElasticException {
		return sessionFactory.getElasticSession().searcher(index,type,keyWords,keyVals,pageNo,pageSize);
	}
	
	@Override
	public List<ElasticData> searcher(EsQueryBuilderInstance builderInstance, String index, String type) throws ElasticException {
		return sessionFactory.getElasticSession().searcher(builderInstance,index,type);
	}
	
	@Override
	public TransportClient getTransClient() {
		return sessionFactory.getElasticSession().getTransClient();
	}
	
	@Override
	public RestHighLevelClient getHighClient() {
		return sessionFactory.getElasticSession().getHighClient();
	}
}
