package com.application.base.core.datasource.session;

import com.application.base.elastic.elastic.query.EsQueryBuilderInstance;
import com.application.base.elastic.entity.ElasticData;
import com.application.base.elastic.exception.ElasticException;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;

import java.util.List;

/**
 * @NAME: EsSession
 * @DESC:
 * @USER: 孤狼
 **/
public interface EsSession {
	/**
	 * 判断指定的索引名是否存在
	 * @param index
	 * @return
	 */
	public boolean isIndexExist(String index) throws ElasticException;
	
	/**
	 * 判断指定的索引的类型是否存在
	 * @param index 索引名
	 * @param type 索引类型
	 * @return 存在：true; 不存在：false;
	 */
	public boolean isTypeExist(String index, String type) throws ElasticException;
	
	/**
	 * 新增 index
	 * @param index
	 *            索引名称
	 * @throws ElasticException
	 */
	public  boolean addIndex(String index) throws ElasticException;
	
	/**
	 * 根据索引名称删除索引
	 * @param index 索引名称
	 * @throws ElasticException
	 */
	public  boolean delIndex(String index) throws ElasticException;
	
	/**
	 * 新增 type
	 *
	 * @param index
	 *            索引名称
	 * @param type
	 *            索引类型
	 * @throws ElasticException
	 */
	public  boolean addType(String index,String type) throws ElasticException;
	
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
	public  boolean addData(ElasticData data) throws ElasticException;
	
	/**
	 * 批量新增
	 * @param elasticData, es存储模型的列表(具体看EsData)
	 * @param async, 是否异步执行.
	 * @throws ElasticException
	 */
	@SuppressWarnings("deprecation")
	public  boolean addDataList(List<ElasticData> elasticData,boolean async) throws ElasticException;
	
	/**
	 * 批量处理器批量新增
	 * @param elasticData, es存储模型的列表(具体看EsData)
	 * @param async, 是否异步执行.
	 * @throws ElasticException
	 */
	@SuppressWarnings("deprecation")
	public  boolean addDataListByProcessor(List<ElasticData> elasticData,boolean async) throws ElasticException;
	
	/**
	 * 更新document
	 * @param data
	 *            商品dto
	 * @throws ElasticException
	 */
	public  boolean updateData(ElasticData data) throws ElasticException;
	
	/**
	 * 批量更新
	 * @param elasticData
	 * @param async, 是否异步执行.
	 *            商品dto
	 * @throws ElasticException
	 */
	public  boolean updateDataList(List<ElasticData> elasticData,boolean async) throws ElasticException;
	
	/**
	 * 批量处理器批量更新
	 * @param elasticData
	 * @param async, 是否异步执行.
	 *            商品dto
	 * @throws ElasticException
	 */
	public  boolean updateDataListByProcessor(List<ElasticData> elasticData,boolean async) throws ElasticException;
	
	/**
	 * 删除document
	 *
	 * @param data
	 *            索引对象
	 * @throws ElasticException
	 */
	public  boolean deleteData( ElasticData data) throws ElasticException;
	
	/**
	 * 批量刪除索引
	 * @param elasticData, es存储模型的列表(具体看EsData)
	 * @param async, 是否异步执行.
	 * @return
	 */
	public  boolean deleteDataList(List<ElasticData> elasticData,boolean async) throws ElasticException;
	
	/**
	 * 批量处理器批量刪除
	 * @param elasticData, es存储模型的列表(具体看EsData)
	 * @param async, 是否异步执行.
	 * @return
	 */
	public  boolean deleteDataListByProcessor(List<ElasticData> elasticData, boolean async) throws ElasticException;
	
	/**
	 * 执行搜索
	 * @param queryBuilder
	 * @param index
	 * @param type
	 * @return
	 * @throws ElasticException
	 */
	public  List<ElasticData> searcher(QueryBuilder queryBuilder, String index, String type) throws ElasticException;
	
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
	public  List<ElasticData> searcher(String index, String type, QueryBuilder boolQuery, List<FieldSortBuilder> sortBuilders, int pageNo, int pageSize) throws ElasticException;
	
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
	
}
