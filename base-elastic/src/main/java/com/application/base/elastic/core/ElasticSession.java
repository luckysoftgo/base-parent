package com.application.base.elastic.core;


import com.application.base.elastic.elastic.query.EsQueryBuilderInstance;
import com.application.base.elastic.entity.ElasticData;
import com.application.base.elastic.entity.ElasticInfo;
import com.application.base.elastic.exception.ElasticException;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;

import java.util.List;
import java.util.Map;

/**
 * @desc 操作ElasticSearch的接口服务类.
 * 详细请查看api:https://www.elastic.co/guide/cn/elasticsearch/guide/current/bulk.html
 * client对比:http://blog.florian-hopf.de/2016/11/java-clients-elasticsearch.html
 * @author 孤狼
 */
public interface ElasticSession {
	
	/**
	 * 刷新ES
	 * @param index
	 * @param type
	 */
	public void flushEs(String index,String type) throws ElasticException;
	
	/**
	 * 关闭连接
	 * @return
	 * @throws ElasticException
	 */
	public void close() throws ElasticException;
	
	/**
	 * 判断指定的索引名是否存在
	 * @param index
	 * @return
	 */
	public boolean judgeIndexExist(String index) throws ElasticException;
	
	/**
	 * 获得所有的索引.
	 * @return
	 */
	public ElasticInfo getIndexs() throws ElasticException;
	
	/**
	 * 判断指定的索引的类型是否存在
	 * @param index 索引名
	 * @param type 索引类型
	 * @return 存在：true; 不存在：false;
	 */
	public boolean judgeTypeExist(String index, String type) throws ElasticException;
	
	/**
	 * 新增 index
	 * @param index
	 *            索引名称
	 * @throws ElasticException
	 */
	public  boolean addEsIndex(String index) throws ElasticException;
	
	/**
	 * 根据索引名称删除索引
	 * @param index 索引名称
	 * @throws ElasticException
	 */
	public  boolean deleteIndex(String index) throws ElasticException;
	
	/**
	 * 新增 type
	 *
	 * @param index
	 *            索引名称
	 * @param type
	 *            索引类型
	 * @throws ElasticException
	 */
	public  boolean addEsType(String index,String type) throws ElasticException;
	
	/**
	 * 通过文档 id 获取文档信息
	 * @return
	 * @throws ElasticException
	 */
	public String getDataById(ElasticData data) throws ElasticException;
	
	/**
	 * 通过文档 id 获取文档信息
	 * @return
	 * @throws ElasticException
	 */
	public ElasticData getDataInfoById(ElasticData data) throws ElasticException;
	
	/**
	 * 新增 document
	 * @param data
	 * @throws ElasticException
	 */
	@SuppressWarnings("deprecation")
	public  boolean addEsData(ElasticData data) throws ElasticException;
	
	/**
	 * 批量新增
	 * @param elasticData, es存储模型的列表(具体看EsData)
	 * @param async, 是否异步执行.
	 * @throws ElasticException
	 */
	@SuppressWarnings("deprecation")
	public  boolean addEsDataList(List<ElasticData> elasticData,boolean async) throws ElasticException;
	
	/**
	 * 批量处理器批量新增
	 * @param elasticData, es存储模型的列表(具体看EsData)
	 * @param async, 是否异步执行.
	 * @throws ElasticException
	 */
	@SuppressWarnings("deprecation")
	public  boolean addEsDataListByProcessor(List<ElasticData> elasticData,boolean async) throws ElasticException;
	
	/**
	 * 更新document
	 * @param data
	 *            商品dto
	 * @throws ElasticException
	 */
	public  boolean updateEsData(ElasticData data) throws ElasticException;
	
	/**
	 * 批量更新
	 * @param elasticData
	 * @param async, 是否异步执行.
	 *            商品dto
	 * @throws ElasticException
	 */
	public  boolean updateEsDataList(List<ElasticData> elasticData,boolean async) throws ElasticException;
	
	/**
	 * 批量处理器批量更新
	 * @param elasticData
	 * @param async, 是否异步执行.
	 *            商品dto
	 * @throws ElasticException
	 */
	public  boolean updateEsDataListByProcessor(List<ElasticData> elasticData,boolean async) throws ElasticException;
	
	/**
	 * 删除document
	 *
	 * @param data
	 *            索引对象
	 * @throws ElasticException
	 */
	public  boolean deleteEsData( ElasticData data) throws ElasticException;
	
	/**
	 * 批量刪除索引
	 * @param elasticData, es存储模型的列表(具体看EsData)
	 * @param async, 是否异步执行.
	 * @return
	 */
	public  boolean deleteEsDataList(List<ElasticData> elasticData,boolean async) throws ElasticException;
	
	/**
	 * 批量处理器批量刪除
	 * @param elasticData, es存储模型的列表(具体看EsData)
	 * @param async, 是否异步执行.
	 * @return
	 */
	public  boolean deleteEsDataListByProcessor(List<ElasticData> elasticData,boolean async) throws ElasticException;
	
	/**
	 * 执行搜索
	 * @param queryBuilder
	 * @param index
	 * @param type
	 * @return
	 * @throws ElasticException
	 */
	public  List<ElasticData> searcher(QueryBuilder queryBuilder,String index, String type) throws ElasticException;
	
	/**
	 * 分页 执行搜索
	 * @param index
	 * @param type
	 * @param boolQuery
	 * @param sortBuilders
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ElasticException
	 */
	public  List<ElasticData> searcher(String index, String type, QueryBuilder boolQuery,List<FieldSortBuilder> sortBuilders, int pageNo, int pageSize) throws ElasticException;
	
	/**
	 *  关键字的分页搜索
	 * @param index
	 * @param type
	 * @param keyWords
	 * @param keyVals
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ElasticException
	 */
	public  List<ElasticData> searcher(String index, String type, String[] keyWords, String[] keyVals, int pageNo, int pageSize) throws ElasticException;
	
	/**
	 * 执行搜索
	 * @param builderInstance
	 * @param index
	 * @param type
	 * @return
	 * @throws ElasticException
	 */
	public  List<ElasticData> searcher(EsQueryBuilderInstance builderInstance, String index, String type) throws ElasticException;
	
	/**
	 * 返回处理的对象,需要自己关闭连接
	 * @return
	 */
	public TransportClient getTransClient();
	
	/**
	 * 返回处理的对象,需要自己关闭连接
	 * @return
	 */
	public RestHighLevelClient getHighClient();
	
	 /**
	 * 给集合中添加数据
	 * @param dbName
	 * @param tableName
	 * @param searchHits
	 * @param dataList
	 */
	 default void tranList(String dbName, String tableName, SearchHits searchHits, List<ElasticData> dataList) {
		for (SearchHit searchHit : searchHits) {
			Map<String,Object> values=searchHit.getSourceAsMap();
			String json=searchHit.getSourceAsString();
			ElasticData model=new ElasticData();
			model.setIndex(dbName);
			model.setType(tableName);
			model.setId(searchHit.getId());
			model.setData(json);
			model.setMapData(values);
			dataList.add(model);
		}
	}

}
