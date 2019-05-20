package com.application.base.all.elastic.elastic.transport.session;

import com.application.base.all.elastic.core.ElasticSession;
import com.application.base.all.elastic.entity.ElasticData;
import com.application.base.all.elastic.exception.ElasticException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsAction;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author 孤狼
 * @ClassName : ElasticRestSession.
 * @Desc : 操作elastic 的实现.
 */
public class ElasticTransportSession implements ElasticSession {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 调用的实例.
	 */
	private TransportClient transportClient;
	
	public TransportClient getTransportClient() {
		if (null==transportClient){
			logger.error("[elastic错误:{}]","获得client操作实例对象为空");
			throw new ElasticException("获得transportClient实例对象为空");
		}
		return transportClient;
	}
	public void setTransportClient(TransportClient transportClient) {
		this.transportClient = transportClient;
	}
	
	@Override
	public void flushEs(String index, String type) throws ElasticException {
		try {
			getTransportClient().admin().indices().flush(new FlushRequest(index.toLowerCase(), type)).actionGet();
		} catch (Exception e) {
			logger.error("刷新ES异常,异常信息是{}",e.getMessage());
			throw new ElasticException(e);
		}
	}
	
	@Override
	public void close() throws ElasticException {
		getTransportClient().close();
	}
	
	@Override
	public boolean judgeIndexExist(String index) throws ElasticException {
		IndicesExistsResponse response=getTransportClient().admin().indices().exists(new IndicesExistsRequest().indices(new String[]{index})).actionGet();
		return response.isExists();
	}
	
	@Override
	public boolean judgeTypeExist(String index, String type) throws ElasticException {
		if(!judgeIndexExist(index)){
			return false;
		}
		TypesExistsResponse response=getTransportClient().admin().indices().typesExists(new TypesExistsRequest(new String[] { index }, type)).actionGet();
		return response.isExists();
	}
	
	@Override
	public boolean addEsIndex(String index) throws ElasticException {
		CreateIndexRequestBuilder requestBuilder = getTransportClient().admin().indices().prepareCreate(index);
		CreateIndexResponse response = requestBuilder.execute().actionGet();
		return response.isAcknowledged();
	}
	
	@Override
	public boolean addEsType(String index, String type) throws ElasticException {
		TypesExistsAction action = TypesExistsAction.INSTANCE;
		TypesExistsRequestBuilder requestBuilder  = new TypesExistsRequestBuilder(getTransportClient(), action, index);
		requestBuilder.setTypes(type);
		TypesExistsResponse response = requestBuilder.get();
		return response.isExists();
	}
	
	@Override
	public boolean addEsData(ElasticData data) throws ElasticException {
		if (data==null) {
			logger.info("传递的 ElasticData 的值为空,请重新设置参数.");
		}
		IndexResponse response = null;
		if (data.isMapFlag()){
			getTransportClient().prepareIndex(data.getIndex(), data.getType(), data.getId()).setSource(data.getMapData()).get();
		}else{
			getTransportClient().prepareIndex(data.getIndex(), data.getType(), data.getId()).setSource(data.getData(),XContentType.JSON).get();
		}
		
		if (response!=null && response.status().equals(RestStatus.CREATED)) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public boolean addEsDataList(List<ElasticData> elasticData) throws ElasticException {
		if (elasticData.isEmpty()) {
			logger.info("传递的 List<ElasticData> 的值为空,请重新设置参数.");
		}
		// 批量处理request
		BulkRequestBuilder bulkRequest = getTransportClient().prepareBulk();
		for (ElasticData data : elasticData) {
			if (data.isMapFlag()){
				bulkRequest.add(new IndexRequest(data.getIndex(), data.getType(), data.getId()).source(data.getMapData()));
			}else{
				bulkRequest.add(new IndexRequest(data.getIndex(), data.getType(), data.getId()).source(data.getData(),XContentType.JSON));
			}
		}
		// 执行批量处理request
		BulkResponse bulkResponse = bulkRequest.get();
		// 处理错误信息
		if (bulkResponse.hasFailures()) {
			long count = 0L;
			for (BulkItemResponse bulkItemResponse : bulkResponse) {
				logger.debug("发生错误的 索引id为 : " + bulkItemResponse.getId() + " ，错误信息为：" + bulkItemResponse.getFailureMessage());
				count++;
			}
			logger.debug("====================批量创建索引过程中出现错误 上面是错误信息 共有: " + count + " 条记录==========================");
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public String getDataById(ElasticData data) throws ElasticException {
		GetResponse response = getTransportClient().prepareGet(data.getIndex(), data.getType(), data.getId()).get();
		if (response!=null){
			return response.getSourceAsString();
		}else{
			return "";
		}
	}
	
	@Override
	public ElasticData getDataInfoById(ElasticData data) throws ElasticException {
		GetResponse response = getTransportClient().prepareGet(data.getIndex(), data.getType(), data.getId()).get();
		if (response!=null){
			data.setIndex(response.getIndex());
			data.setType(response.getType());
			data.setId(response.getId());
			data.setData(response.getSourceAsString());
			return data;
		}else{
			return null;
		}
	}
	
	@Override
	public boolean deleteEsData(ElasticData data) throws ElasticException {
		DeleteResponse response =getTransportClient().prepareDelete(data.getIndex(), data.getType(), data.getId()).get();
		if (response!=null && response.status().equals(RestStatus.OK)){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean deleteEsDataList(List<ElasticData> elasticData) throws ElasticException {
		BulkRequestBuilder deleteBulk = getTransportClient().prepareBulk();
		for (ElasticData data : elasticData) {
			deleteBulk.add(getTransportClient().prepareDelete(data.getIndex(), data.getType(), data.getId()));
		}
		BulkResponse response=deleteBulk.execute().actionGet();
		if (response!=null && response.status().equals(RestStatus.OK)){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean deleteIndex(String index) throws ElasticException {
		IndicesExistsRequest ier = new IndicesExistsRequest();
		ier.indices(new String[] { index });
		boolean exists = getTransportClient().admin().indices().exists(ier).actionGet().isExists();
		if (exists) {
			AcknowledgedResponse response=getTransportClient().admin().indices().prepareDelete(index.toLowerCase()).get();
			if (response!=null && response.isAcknowledged()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	@Override
	public boolean updateEsData(ElasticData data) throws ElasticException {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(data.getIndex());
		updateRequest.type(data.getType());
		updateRequest.id(data.getId());
		updateRequest.doc(data.getData());
		try {
			UpdateResponse response=getTransportClient().update(updateRequest).get();
			if (response.status().equals(RestStatus.OK)){
				return true;
			}else{
				return false;
			}
		}catch (Exception e){
			logger.error("修改索引信息失败了,请查找原因：{}",e);
			throw new ElasticException(e);
		}
		
	}
	
	@Override
	public List<ElasticData> searcher(QueryBuilder queryBuilder, String index, String type) throws ElasticException {
		SearchResponse response = getTransportClient().prepareSearch(index).setTypes(type).get();
		//非空设置.
		if (queryBuilder != null) {
			response = getTransportClient().prepareSearch(index).setTypes(type).setQuery(queryBuilder).get();
		}
		/**
		 * 遍历查询结果输出相关度分值和文档内容
		 */
		SearchHits searchHits =  response.getHits();
		logger.info("查询到记录数:{} 条." ,searchHits.getTotalHits());
		List<ElasticData> dataList = new ArrayList<ElasticData>();
		tranList(index, type, searchHits, dataList);
		return dataList;
	}
	
	@Override
	public List<ElasticData> searcher(String index, String type, QueryBuilder queryBuilder, List<FieldSortBuilder> sortBuilders, int from, int size) throws ElasticException {
		/**
		 * 遍历查询结果输出相关度分值和文档内容
		 */
		SearchHits searchHits = searchHits(index, type, queryBuilder, sortBuilders, from, size);
		logger.info("查询到记录数:{}" + searchHits.getTotalHits());
		List<ElasticData> dataList = new ArrayList<ElasticData>();
		tranList(index, type, searchHits, dataList);
		return dataList;
	}
	
	@Override
	public List<ElasticData> searcher(String index, String type, String[] keyWords, String[] channelIdArr, int pageNo,
	                                  int pageSize) throws ElasticException {
		/**
		 * 遍历查询结果输出相关度分值和文档内容
		 */
		SearchHits searchHits = search(index, type, keyWords, channelIdArr, pageNo, pageSize);
		logger.info("查询到记录数:{}" + searchHits.getTotalHits());
		List<ElasticData> dataList = new ArrayList<ElasticData>();
		tranList(index, type, searchHits, dataList);
		return dataList;
	}
	
	@Override
	public SearchHits searchHits(String index, String type, QueryBuilder boolQuery,
	                             List<FieldSortBuilder> sortBuilders, int from, int size) throws ElasticException {
		TransportClient client = checkIndex(getTransportClient(), index);
		/**
		 * 查询请求建立
		 */
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
		searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		searchRequestBuilder.setFrom(from);
		searchRequestBuilder.setSize(size);
		searchRequestBuilder.setExplain(false);
		if (boolQuery!=null) {
			searchRequestBuilder.setQuery(boolQuery);
		}
		if (sortBuilders != null && sortBuilders.size() > 0) {
			for (FieldSortBuilder sortBuilder : sortBuilders) {
				searchRequestBuilder.addSort(sortBuilder);
			}
		}
		return searchRequestBuilder.execute().actionGet().getHits();
	}
	
	Pattern badPattern = Pattern.compile("\\s*[\\s~!\\^&\\(\\)\\-\\+:\\|\\\\\"\\\\$]+\\s*");
	
	@Override
	public SearchHits search(String index, String type, String[] keyWords, String[] channelIdArr, int pageNo, int pageSize) throws ElasticException {
		TransportClient client= checkIndex(getTransportClient(), index);
		/**
		 * 查询请求建立
		 */
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
		searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		searchRequestBuilder.setFrom(pageNo);
		searchRequestBuilder.setSize(pageSize);
		searchRequestBuilder.setExplain(true);
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		StringBuffer totalKeys = new StringBuffer();
		for (String keyword : keyWords) {
			totalKeys.append(keyword);
		}
		String str = "*";
		if (!str.equals(totalKeys.toString())) {
			
			for (String keyword : keyWords) {
				if (keyword == null || keyword.trim().length() == 0) {
					continue;
				}
				keyword = badPattern.matcher(keyword).replaceAll("");
				if (keyword == null || keyword.trim().length() == 0) {
					continue;
				}
				if (keyword.indexOf("*") != -1 || keyword.indexOf("×") != -1 || keyword.indexOf("?") != -1 || keyword.indexOf("？") != -1) {
					keyword = keyword.replaceAll("×", "*").replaceAll("？", "?");
					BoolQueryBuilder subBoolQuery = QueryBuilders.boolQuery();
					String[] indexColumnNames = new String[0];
					for (String indexColumnName : indexColumnNames) {
						subBoolQuery.should(QueryBuilders.wildcardQuery(indexColumnName.toLowerCase(), keyword));
					}
					boolQuery.must(subBoolQuery);
				} else {
					QueryStringQueryBuilder qb = QueryBuilders.queryStringQuery("\"" + keyword + "\"");
					boolQuery.must(qb);
				}
			}
		} else {
			boolQuery.should(QueryBuilders.queryStringQuery("*"));
		}
		if (channelIdArr != null && channelIdArr.length > 0) {
			TermQueryBuilder inQuery = QueryBuilders.termQuery("channelid_", channelIdArr);
			boolQuery.must(inQuery);
		}
		searchRequestBuilder.setQuery(boolQuery);
		return searchRequestBuilder.execute().actionGet().getHits();
	}
	
	@Override
	public TransportClient getTransClient() {
		return getTransportClient();
	}
	
	@Override
	@Deprecated
	public RestHighLevelClient getHighClient() {
		return null;
	}
	
	/**
	 * 去掉不存在的索引
	 *
	 * @param client
	 * @param dbName
	 * @return
	 * @throws Exception
	 */
	private TransportClient checkIndex(TransportClient client, String dbName){
		// 去掉不存在的索引
		IndicesExistsRequest ier = new IndicesExistsRequest();
		ier.indices(new String[]{dbName});
		boolean exists = client.admin().indices().exists(ier).actionGet().isExists();
		if (exists) {
			client.admin().indices().open(new OpenIndexRequest(dbName)).actionGet();
		} else {
			throw new IndexNotFoundException(dbName);
		}
		try {
			client.admin().indices().refresh(new RefreshRequest(dbName)).actionGet();
		} catch (IndexNotFoundException e) {
			logger.error("重新刷新索引库异常,异常信息是：{}", e.getMessage());
			throw new ElasticException(e);
		}
		return client;
	}
}
