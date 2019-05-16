package com.application.base.all.elastic.core;


import com.application.base.all.elastic.entity.ElasticData;
import com.application.base.all.elastic.exception.ElasticException;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;

import java.util.List;

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
	 * 新增 document
	 * @param data
	 * @throws ElasticException
	 */
	@SuppressWarnings("deprecation")
	public  boolean addEsData(ElasticData data) throws ElasticException;
	
	/**
	 * 批量新增
	 * @param elasticData, es存储模型的列表(具体看EsData)
	 * @throws ElasticException
	 */
	@SuppressWarnings("deprecation")
	public  boolean addEsDataList(List<ElasticData> elasticData) throws ElasticException;
	
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
	 * @return
	 */
	public  boolean deleteEsDataList(List<ElasticData> elasticData) throws ElasticException;
	
	/**
	 * 根据索引名称删除索引
	 * @param index 索引名称
	 * @throws ElasticException
	 */
	public  boolean deleteIndex(String index) throws ElasticException;
	
	/**
	 * 更新document
	 * @param data
	 *            商品dto
	 * @throws ElasticException
	 */
	public  boolean updateEsData(ElasticData data) throws ElasticException;
	
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
	 * @param from
	 * @param size
	 * @return
	 * @throws ElasticException
	 */
	public  List<ElasticData> searcher(String index, String type, QueryBuilder boolQuery,
	                                         List<FieldSortBuilder> sortBuilders, int from, int size) throws ElasticException;
	/**
	 *  关键字的分页搜索
	 * @param index
	 * @param type
	 * @param keyWords
	 * @param channelIdArr
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ElasticException
	 */
	public  List<ElasticData> searcher( String index, String type, String[] keyWords, String[] channelIdArr, int pageNo, int pageSize) throws ElasticException;
	
	/**
	 * 按照条件分页查询数据
	 * @param index
	 * @param type
	 * @param boolQuery
	 * @param sortBuilders
	 * @param from
	 * @param size
	 * @return
	 * @throws ElasticException
	 */
	public  SearchHits searchHits(String index, String type, QueryBuilder boolQuery,
	                                    List<FieldSortBuilder> sortBuilders, int from, int size) throws ElasticException;
	
	/**
	 * 按照关键字查询.
	 * @param index
	 * @param type
	 * @param keyWords
	 * @param channelIdArr
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ElasticException
	 */
	public  SearchHits search(String index, String type, String[] keyWords, String[] channelIdArr, int pageNo, int pageSize) throws ElasticException;
	
}
