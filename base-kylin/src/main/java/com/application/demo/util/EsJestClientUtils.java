package com.application.demo.util;

import com.application.base.elastic.entity.ElasticData;
import com.application.base.elastic.exception.ElasticException;
import com.application.demo.init.RestClientConfig;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
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
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;


/**
 * elasticsearch 相关操作工具类
 * <br> 数据对应关系】
 * <br> index (索引)    --> 数据库名
 * <br> type (类型)     --> 表名
 * <br> document (文档) --> 表中一行数据
 * @author 孤狼
 */
@Component
public class EsJestClientUtils {

	private Logger logger = LoggerFactory.getLogger(EsJestClientUtils.class.getName());

	/**
	 * 获得连接对象
	 * @return
	 * @throws Exception
	 */
	public RestHighLevelClient getClient(RestClientConfig config) throws Exception {
		EsJestClientBuilder client = new EsJestClientBuilder();
		RestHighLevelClient paramClient = client.initParamsClient(config.getServerIps());
		return paramClient;
	}
	
	/**
	 * 关闭对应client
	 * @param client
	 */
	public void close(RestHighLevelClient client) {
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
			IndexRequest request =new IndexRequest(data.getIndex(),data.getType(),data.getId());
			if (data.isMapFlag()){
				request.source(data.getMapData());
			}else{
				request.source(data.getData());
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
		List<IndexRequest> requests = getIndexRequest(client,elasticData);
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
	
	public boolean addEsDataListByProcessor(RestHighLevelClient levelClient,List<ElasticData> elasticData, boolean async) throws ElasticException {
		BulkProcessor bulkProcessor = getBulkProcessor(levelClient,async);
		List<IndexRequest> indexList=getIndexList(elasticData);
		for (IndexRequest data : indexList) {
			bulkProcessor.add(data);
		}
		return dealBulkProcessor(bulkProcessor);
	}
	
	private boolean dealBulkProcessor(BulkProcessor bulkProcessor) {
		bulkProcessor.flush();
		try {
			boolean terminatedFlag = bulkProcessor.awaitClose(200L, TimeUnit.SECONDS);
			logger.info("是否终端关闭了链接:",terminatedFlag);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			bulkProcessor.close();
		}
		return true;
	}
	
	private List<IndexRequest> getIndexList(List<ElasticData> elasticData){
		List<IndexRequest> resultList=new ArrayList<>();
		for (ElasticData data : elasticData) {
			IndexRequest request = new IndexRequest(data.getIndex(),data.getType(),data.getId());
			if (data.isMapFlag()){
				request.source(data.getMapData());
			}else{
				request.source(data.getData(), XContentType.JSON);
			}
			resultList.add(request);
		}
		return resultList;
	}
	
	private BulkProcessor getBulkProcessor(RestHighLevelClient highLevelClient,boolean async) {
		BulkProcessor bulkProcessor = null;
		try {
			BulkProcessor.Listener listener = new BulkProcessor.Listener() {
				@Override
				public void beforeBulk(long executionId, BulkRequest request) {
					logger.debug("Try to executing bulk [{}] with {} requests",executionId, request.numberOfActions());
				}
				@Override
				public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
					if (response.hasFailures()) {
						logger.warn("Bulk [{}] executed with failures", executionId);
					} else {
						logger.debug("Bulk [{}] completed in {} milliseconds",executionId, response.getTook().getMillis());
					}
				}
				@Override
				public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
					logger.error("Failed to execute bulk:" + failure + ",executionId:" + executionId);
				}
			};
			
			BiConsumer<BulkRequest, ActionListener<BulkResponse>> bulkConsumer = null;
			if (async){
				bulkConsumer = (request, bulkListener) -> highLevelClient.bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
			}else{
				bulkConsumer = (request, bulkListener) -> {
					try {
						highLevelClient.bulk(request, RequestOptions.DEFAULT);
					} catch (IOException e) {
						e.printStackTrace();
					}
				};
			}
			BulkProcessor.Builder builder = BulkProcessor.builder(bulkConsumer, listener);
			builder.setBulkActions(10000);
			//设置批量的大小
			builder.setBulkSize(new ByteSizeValue(100L, ByteSizeUnit.MB));
			builder.setConcurrentRequests(100);
			builder.setFlushInterval(TimeValue.timeValueSeconds(100L));
			builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(2L), 3));
			// 注意点：在这里感觉有点坑，官网样例并没有这一步，而笔者因一时粗心也没注意，在调试时注意看才发现，上面对builder设置的属性没有生效
			bulkProcessor = builder.build();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				//失效时间.
				bulkProcessor.awaitClose(100L, TimeUnit.SECONDS);
				highLevelClient.close();
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
		return bulkProcessor;
	}
	
	
	private List<IndexRequest> getIndexRequest(RestHighLevelClient client,List<ElasticData> elasticData) {
		List<IndexRequest> requests = new ArrayList<>();
		for (ElasticData data : elasticData) {
			IndexRequest request =new IndexRequest(data.getIndex(),data.getType(),data.getId());
			if (data.isMapFlag()){
				request.source(data.getMapData());
			}else{
				request.source(data.getData());
			}
			requests.add(request);
		}
		return requests;
	}
	
	
	public String getDataById(RestHighLevelClient client,ElasticData data) throws ElasticException {
		try{
			GetRequest request = new GetRequest(data.getIndex(),data.getType(),data.getId());
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
			GetRequest request = new GetRequest(data.getIndex(),data.getType(),data.getId());
			GetResponse response = client.get(request,RequestOptions.DEFAULT);
			if (response!=null){
				if (data.isMapFlag()){
					data.setMapData(response.getSourceAsMap());
				}else{
					data.setData(response.getSourceAsString());
				}
			}
			return data;
		}catch(Exception e){
			logger.error("检查index数据异常,异常信息为:{}",e);
		}
		return null;
	}
	
	
	public boolean deleteEsData(RestHighLevelClient client,ElasticData data) throws ElasticException {
		try{
			DeleteRequest deleteRequest = new DeleteRequest(data.getIndex(), data.getType(), data.getId());
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
			requests.add(new DeleteRequest(data.getIndex(),data.getType(),data.getId()));
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
			UpdateRequest request = new UpdateRequest(data.getIndex(),data.getType(),data.getId());
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
			ElasticData model = new ElasticData();
			model.setId(searchHit.getId());
			model.setIndex(dbName);
			model.setType(tableName);
			model.setMapData(searchHit.getSourceAsMap());
			model.setData(searchHit.getSourceAsString());
			dataList.add(model);
		}
	}
	
}
