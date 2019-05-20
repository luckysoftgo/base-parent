package com.application.base.all.util.jest;

import com.application.base.all.elastic.exception.ElasticException;
import com.application.base.all.util.ElasticData;
import com.application.base.utils.json.JsonConvertUtils;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * elasticsearch 相关操作工具类
 * <br> 数据对应关系】
 * <br> index (索引)    --> 数据库名
 * <br> type (类型)     --> 表名
 * <br> document (文档) --> 表中一行数据
 * @author 孤狼
 */

public class EsJestClientUtils {

	private static Logger logger = LoggerFactory.getLogger(EsJestClientUtils.class.getName());
	
	static Pattern badChars = Pattern.compile("\\s*[\\s~!\\^&\\(\\)\\-\\+:\\|\\\\\"\\\\$]+\\s*");

	/**
	 * 配置文件的 client
	 */
	private static RestHighLevelClient settingClient;
	
	/**
	 * 参数构造的client
	 */
	private static RestHighLevelClient paramClient;
	
	/**
	 * 获得连接对象
	 * @return
	 * @throws Exception
	 */
	public static RestHighLevelClient getSettingClient(String inputPath) throws Exception {
		if (settingClient==null) {
			EsJestClientBuilder client = new EsJestClientBuilder();
			settingClient = client.initSettingsClient(inputPath);
		}
		if (settingClient==null){
			throw new Exception("没有通过配置文件获得 RestHighLevelClient 对象的实例!");
		}
		return settingClient;
	}
	
	/**
	 * 获得连接对象
	 * @return
	 * @throws Exception
	 */
	public static RestHighLevelClient getParamClient(String serverIPs) throws Exception {
		if (paramClient==null) {
			EsJestClientBuilder client = new EsJestClientBuilder();
			paramClient = client.initParamsClient(serverIPs);
		}
		return paramClient;
	}
	
	/**
	 * 关闭对应client
	 * @param client
	 */
	public static void close(RestHighLevelClient client) {
		try {
			client.close();
		} catch (Exception e) {
			logger.error("关闭ES连接异常,异常信息是{}",e.getMessage());
		}
	}
	
	public boolean judgeIndexExist(RestHighLevelClient client,String index) throws ElasticException {
		try{
			GetRequest request = new GetRequest(index);
			return client.exists(request,RequestOptions.DEFAULT);
		}catch(Exception e){
			logger.error("检查index数据异常,异常信息为:{}",e);
		}
		return false;
	}
	
	
	public boolean judgeTypeExist(RestHighLevelClient client,String index, String type) throws ElasticException {
		try{
			GetRequest request =new GetRequest();
			request.index(index);
			request.type(type);
			return client.exists(request,RequestOptions.DEFAULT);
		}catch(Exception e){
			logger.error("检查index type异常,异常信息为:{}",e);
		}
		return false;
	}
	
	
	public boolean addEsIndex(RestHighLevelClient client,String index) throws ElasticException {
		try{
			IndexRequest request = new IndexRequest(index);
			IndexResponse response = client.index(request,RequestOptions.DEFAULT);
			if (response!=null && response.status().equals(RestStatus.CREATED)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			logger.error("添加index异常,异常信息为:{}",e);
		}
		return false;
	}
	
	
	public boolean addEsType(RestHighLevelClient client,String index, String type) throws ElasticException {
		try{
			IndexRequest request =new IndexRequest(index,type);
			IndexResponse response = client.index(request,RequestOptions.DEFAULT);
			if (response!=null && response.status().equals(RestStatus.CREATED)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			logger.error("添加index type异常,异常信息为:{}",e);
		}
		return false;
	}
	
	
	public boolean addEsData(RestHighLevelClient client, ElasticData data) throws ElasticException {
		try{
			IndexRequest request =new IndexRequest(data.getDbName(),data.getTableName(),data.getDocumentId());
			if (data.isJson()){
				request.source(XContentType.JSON,data.getJsonStr());
			}else{
				request.source(data.getJsonStr());
			}
			IndexResponse response = client.index(request,RequestOptions.DEFAULT);
			if (response!=null && response.status().equals(RestStatus.CREATED)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			logger.error("添加index type id异常,异常信息为:{}",e);
		}
		return false;
	}
	
	
	public boolean addEsDataList(RestHighLevelClient client,List<ElasticData> elasticData) throws ElasticException {
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = getDbNameRequest(client,elasticData);
		for (IndexRequest indexRequest : requests) {
			bulkRequest.add(indexRequest);
		}
		try {
			BulkResponse response = client.bulk(bulkRequest,RequestOptions.DEFAULT);
			// 处理错误信息
			if (response.hasFailures()) {
				long count = 0L;
				for (BulkItemResponse bulkItemResponse : response) {
					logger.debug("发生错误的 索引id为 : " + bulkItemResponse.getId() + " ，错误信息为：" + bulkItemResponse.getFailureMessage());
					count++;
				}
				logger.debug("====================批量创建索引过程中出现错误 上面是错误信息 共有: " + count + " 条记录==========================");
				return false;
			}else{
				return true;
			}
		} catch (IOException e) {
			logger.error("批量添加index type id异常,异常信息为:{}",e);
		}
		return false;
	}
	
	private List<IndexRequest> getDbNameRequest(RestHighLevelClient client,List<ElasticData> elasticData) {
		List<IndexRequest> requests = new ArrayList<>();
		for (ElasticData data : elasticData) {
			IndexRequest request =new IndexRequest(data.getDbName(),data.getTableName(),data.getDocumentId());
			if (data.isJson()){
				request.source(XContentType.JSON,data.getJsonStr());
			}else{
				request.source(data.getJsonStr());
			}
			requests.add(request);
		}
		return requests;
	}
	
	
	public String getDataById(RestHighLevelClient client,ElasticData data) throws ElasticException {
		try{
			GetRequest request = new GetRequest(data.getDbName(),data.getTableName(),data.getDocumentId());
			GetResponse response = client.get(request,RequestOptions.DEFAULT);
			if (response!=null){
				return response.getSourceAsString();
			}
			return null;
		}catch(Exception e){
			logger.error("检查index数据异常,异常信息为:{}",e);
		}
		return null;
	}
	
	
	public ElasticData getDataInfoById(RestHighLevelClient client,ElasticData data) throws ElasticException {
		try{
			GetRequest request = new GetRequest(data.getDbName(),data.getTableName(),data.getDocumentId());
			GetResponse response = client.get(request,RequestOptions.DEFAULT);
			if (response!=null){
				Map<String,Object> info=response.getSourceAsMap();
				data.setJsonStr(info);
			}
			return data;
		}catch(Exception e){
			logger.error("检查index数据异常,异常信息为:{}",e);
		}
		return null;
	}
	
	
	public boolean deleteEsData(RestHighLevelClient client,ElasticData data) throws ElasticException {
		try{
			DeleteRequest deleteRequest = new DeleteRequest(data.getDbName(), data.getTableName(), data.getDocumentId());
			//同步执行
			DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
			if (deleteResponse!=null && deleteResponse.status().equals(RestStatus.OK)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			logger.error("删除数据异常,异常信息为:{}",e);
		}
		return false;
	}
	
	
	public boolean deleteEsDataList(RestHighLevelClient client,List<ElasticData> elasticData) throws ElasticException {
		BulkRequest bulkRequest = new BulkRequest();
		List<DeleteRequest> requests = getDeleteRequest(client,elasticData);
		for (DeleteRequest deleteRequest : requests) {
			bulkRequest.add(deleteRequest);
		}
		try {
			BulkResponse response = client.bulk(bulkRequest,RequestOptions.DEFAULT);
			// 处理错误信息
			if (response.hasFailures()) {
				long count = 0L;
				for (BulkItemResponse bulkItemResponse : response) {
					logger.debug("发生错误的 索引id为 : " + bulkItemResponse.getId() + " ，错误信息为：" + bulkItemResponse.getFailureMessage());
					count++;
				}
				logger.debug("====================批量创建索引过程中出现错误 上面是错误信息 共有: " + count + " 条记录==========================");
				return false;
			}else{
				return true;
			}
		} catch (IOException e) {
			logger.error("批量删除index type id异常,异常信息为:{}",e);
		}
		return false;
	}
	
	private List<DeleteRequest> getDeleteRequest(RestHighLevelClient client,List<ElasticData> elasticData) {
		List<DeleteRequest> requests = new ArrayList<>();
		for (ElasticData data : elasticData) {
			requests.add(new DeleteRequest(data.getDbName(),data.getTableName(),data.getDocumentId()));
		}
		return requests;
	}
	
	
	public boolean deleteIndex(RestHighLevelClient client,String index) throws ElasticException {
		try{
			DeleteRequest request = new DeleteRequest(index);
			DeleteResponse response = client.delete(request,RequestOptions.DEFAULT);
			if (response!=null && response.status().equals(RestStatus.OK)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			logger.error("删除index数据异常,异常信息为:{}",e);
		}
		return false;
	}
	
	
	public boolean updateEsData(RestHighLevelClient client,ElasticData data) throws ElasticException {
		try{
			UpdateRequest request = new UpdateRequest(data.getDbName(),data.getTableName(),data.getDocumentId());
			UpdateResponse response = client.update(request,RequestOptions.DEFAULT);
			if (response!=null && response.status().equals(RestStatus.OK)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			logger.error("更新index数据异常,异常信息为:{}",e);
		}
		return false;
	}
	
	
	public List<ElasticData> searcher(RestHighLevelClient client,QueryBuilder queryBuilder, String index, String type) throws ElasticException {
		try{
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			sourceBuilder.query(queryBuilder);
			SearchRequest request = new SearchRequest(index);
			request.types(type);
			request.source(sourceBuilder);
			SearchResponse response=client.search(request,RequestOptions.DEFAULT);
			/**
			 * 遍历查询结果输出相关度分值和文档内容
			 */
			SearchHits searchHits =  response.getHits();
			logger.info("查询到记录数:{}条." ,searchHits.getTotalHits());
			List<ElasticData> dataList = new ArrayList<ElasticData>();
			tranList(index, type, searchHits, dataList);
			return dataList;
		}catch(Exception e){
			logger.error("查询index数据异常,异常信息为:{}",e);
		}
		return null;
	}
	
	
	public List<ElasticData> searcher(RestHighLevelClient client,String index, String type, QueryBuilder queryBuilder,
	                                                                          List<FieldSortBuilder> sortBuilders, int from, int size) throws ElasticException {
		try {
			/**
			 * 遍历查询结果输出相关度分值和文档内容
			 */
			SearchHits searchHits = searchHits(client,index, type, queryBuilder, sortBuilders, from, size);
			logger.info("查询到记录数:{}" + searchHits.getTotalHits());
			List<ElasticData> dataList = new ArrayList<ElasticData>();
			tranList(index, type, searchHits, dataList);
			return dataList;
		}catch (Exception e){
			logger.error("查询index数据异常,异常信息为:{}",e);
		}
		return  null;
	}
	
	
	public List<ElasticData> searcher(RestHighLevelClient client,String index, String type, String[] keyWords, String[] keyVals, int pageNo, int pageSize) throws ElasticException {
		try {
			/**
			 * 遍历查询结果输出相关度分值和文档内容
			 */
			SearchHits searchHits = search(client,index, type, keyWords, keyVals, pageNo, pageSize);
			logger.info("查询到记录数:{}" + searchHits.getTotalHits());
			List<ElasticData> dataList = new ArrayList<ElasticData>();
			tranList(index, type, searchHits, dataList);
			return dataList;
		}catch (Exception e){
			logger.error("查询index数据异常,异常信息为:{}",e);
		}
		return  null;
	}
	
	
	public SearchHits searchHits(RestHighLevelClient client,String index, String type, QueryBuilder boolQuery, List<FieldSortBuilder> sortBuilders, int from, int size) throws ElasticException {
		try {
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			sourceBuilder.from(from);
			sourceBuilder.size(size);
			sourceBuilder.query(boolQuery);
			if (sortBuilders!=null && sortBuilders.size()>0){
				for (FieldSortBuilder builder:sortBuilders) {
					sourceBuilder.sort(builder);
				}
			}
			SearchRequest searchRequest = new SearchRequest();
			searchRequest.indices(index);
			searchRequest.types(type);
			searchRequest.source(sourceBuilder);
			SearchResponse response = client.search(searchRequest,RequestOptions.DEFAULT);
			return response.getHits();
		}catch (Exception e){
			logger.error("查询index数据异常,异常信息为:{}",e);
		}
		return null;
	}
	
	
	public SearchHits search(RestHighLevelClient client,String index, String type, String[] keyWords, String[] keyVals, int pageNo, int pageSize) throws ElasticException {
		try {
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			sourceBuilder.from(pageNo);
			sourceBuilder.size(pageSize);
			sourceBuilder.query(QueryBuilders.termsQuery(keyWords[0],keyVals));
			SearchRequest searchRequest = new SearchRequest();
			searchRequest.indices(index);
			searchRequest.types(type);
			searchRequest.source(sourceBuilder);
			SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
			return response.getHits();
		}catch (Exception e){
			logger.error("查询index数据异常,异常信息为:{}",e);
		}
		return null;
	}
	
	/**
	 * 给集合中添加数据
	 * @param dbName
	 * @param tableName
	 * @param searchHits
	 * @param dataList
	 */
	public void tranList(String dbName, String tableName, SearchHits searchHits, List<ElasticData> dataList) {
		for (SearchHit searchHit : searchHits) {
			String json = searchHit.getSourceAsString();
			ElasticData model = new ElasticData();
			model.setDocumentId(searchHit.getId());
			model.setDbName(dbName);
			model.setTableName(tableName);
			model.setJsonStr(json);
			dataList.add(model);
		}
	}
	
}
