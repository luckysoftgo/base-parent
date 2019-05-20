package com.application.base.all.util.transport;

import com.application.base.all.elastic.entity.ElasticData;
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
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * elasticsearch 相关操作工具类
 * <br> 数据对应关系】
 * <br> index (索引)    --> 数据库名
 * <br> type (类型)     --> 表名
 * <br> document (文档) --> 表中一行数据
 * @author 孤狼
 */

public class EsTransportClientUtils {

	private static Logger logger = LoggerFactory.getLogger(EsTransportClientUtils.class.getName());
	
	static Pattern badChars = Pattern.compile("\\s*[\\s~!\\^&\\(\\)\\-\\+:\\|\\\\\"\\\\$]+\\s*");

	/**
	 * 配置文件的 client
	 */
	private static TransportClient settingClient;
	
	/**
	 * 参数构造的client
	 */
	private static TransportClient paramClient;
	
	/**
	 * 获得连接对象
	 * @return
	 * @throws Exception
	 */
	public static TransportClient getSettingClient(String inputPath) throws Exception {
		if (settingClient==null) {
			EsTransportClientBuilder client = new EsTransportClientBuilder();
			settingClient = client.initSettingsClient(inputPath);
		}
		if (settingClient==null){
			throw new Exception("没有通过配置文件获得 TransportClient 对象的实例!");
		}
		return settingClient;
	}
	
	/**
	 * 获得连接对象
	 * @return
	 * @throws Exception
	 */
	public static TransportClient getParamClient(String clusterName,String serverIPs,boolean isAppend) throws Exception {
		if (paramClient==null) {
			EsTransportClientBuilder client = new EsTransportClientBuilder();
			paramClient = client.initParamsClient(clusterName, serverIPs, isAppend);
		}
		return paramClient;
	}
	
	/**
	 * 关闭对应client
	 * @param client
	 */
	public static void close(TransportClient client) {
		try {
			client.close();
		} catch (Exception e) {
			logger.error("关闭ES连接异常,异常信息是{}",e.getMessage());
		}
	}

	/**
	 * 刷新ES
	 * @param client
	 * @param dbName
	 * @param tableName
	 */
	public static void flush(TransportClient client, String dbName, String tableName) {
		try {
			client.admin().indices().flush(new FlushRequest(dbName.toLowerCase(), tableName)).actionGet();
		} catch (Exception e) {
			logger.error("刷新ES异常,异常信息是{}",e.getMessage());
		}
	}

	/**
	 * 判断指定的索引名是否存在
	 *
	 * @param dbName 索引名
	 * @return 存在：true; 不存在：false;
	 */
	public static boolean isExistsIndex(TransportClient client,String dbName) throws Exception {
		IndicesExistsResponse response=client.admin().indices().exists(new IndicesExistsRequest().indices(new String[]{dbName})).actionGet();
        return response.isExists();
	}

	/**
	 * 判断指定的索引的类型是否存在
	 *
	 * @param dbName 索引名
	 * @param tableName 索引类型
	 * @return 存在：true; 不存在：false;
	 */
	public static boolean isExistsType(TransportClient client,String dbName, String tableName) throws Exception {
		if(!isExistsIndex(client,dbName)){
			return false;
		}
		TypesExistsResponse response=client.admin().indices().typesExists(new TypesExistsRequest(new String[] { dbName }, tableName)).actionGet();
		return response.isExists();
	}
	
	/**
	 * 新增 dbName
	 * @param dbName
	 *            索引名称
	 * @throws Exception
	 */
	public static boolean addDBName(TransportClient client,String dbName) throws Exception {
	    CreateIndexRequestBuilder requestBuilder = client.admin().indices().prepareCreate(dbName);
	    CreateIndexResponse response = requestBuilder.execute().actionGet();
	    return response.isAcknowledged();
	}
	
	/**
	 * 新增 tableName
	 *
	 * @param dbName
	 *            索引名称
	 * @param tableName
	 *            索引类型
	 * @throws Exception
	 */
	public static boolean addTableName(TransportClient client,String dbName,String tableName) throws Exception {
		TypesExistsAction action = TypesExistsAction.INSTANCE;
		TypesExistsRequestBuilder requestBuilder  = new TypesExistsRequestBuilder(client, action, dbName);
		requestBuilder.setTypes(tableName);
		TypesExistsResponse response = requestBuilder.get();
		return response.isExists();
	}
	
	/**
	 * 新增 document
	 *
	 * @param dbName
	 *            索引名称
	 * @param tableName
	 *            类型名称
	 * @param data
	 *            存储模型对象
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static boolean addDocument(TransportClient client,String dbName, String tableName, ElasticData data) throws Exception {
		data.setIndex(dbName);
		data.setType(tableName);
		return addDocument(client,data);
	}

	/**
	 * 新增 document
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static boolean addDocument(TransportClient client, ElasticData data) throws Exception {
		if (data==null) {
			logger.info("传递的 ElasticData 的值为空,请重新设置参数.");
		}
		IndexResponse response = null;
		if (data.isMap()){
			response = client.prepareIndex(data.getIndex(), data.getType(), data.getId()).setSource(data.getMapData()).get();
		}else{
			response = client.prepareIndex(data.getIndex(), data.getType(), data.getId()).setSource(data.getData()).get();
		}
		if (response!=null && response.status().equals(RestStatus.CREATED)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 批量新增
	 * @param elasticData, es存储模型的列表(具体看EsData)
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static boolean addDocumentList(TransportClient client,List<ElasticData> elasticData) throws Exception {
		if (elasticData.isEmpty()) {
			logger.info("传递的 List<ElasticData> 的值为空,请重新设置参数.");
		}
		// 批量处理request
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (ElasticData data : elasticData) {
			if (data.isMap()){
				bulkRequest.add(new IndexRequest(data.getIndex(), data.getType(), data.getId()).source(data.getMapData()));
			}else{
				bulkRequest.add(new IndexRequest(data.getIndex(), data.getType(), data.getId()).source(data.getData()));
			}
		}
		// 执行批量处理request
		BulkResponse bulkResponse = bulkRequest.get();
		// 处理错误信息
		if (bulkResponse.hasFailures()) {
			logger.error("==================== 批量创建索引过程中出现错误 下面是错误信息  ==========================");
			long count = 0L;
			for (BulkItemResponse bulkItemResponse : bulkResponse) {
				logger.error("发生错误的 索引id为 : " + bulkItemResponse.getId() + " ，错误信息为：" + bulkItemResponse.getFailureMessage());
				count++;
			}
			logger.error("====================批量创建索引过程中出现错误 上面是错误信息 共有: " + count + " 条记录==========================");
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 通过文档 id 获取文档信息
	 * @param docId
	 * @return
	 * @throws Exception
	 */
	public static String getDocById(TransportClient client,String dbName,String tableName,String docId) throws Exception{
		GetResponse response = client.prepareGet(dbName, tableName, docId).get();
		if (response!=null){
			return response.getSourceAsString();
		}else{
			return "";
		}
	}
	
	/**
	 * 通过文档 id 获取文档信息
	 * @param docId
	 * @return
	 * @throws Exception
	 */
	public static ElasticData getDocInfoById(TransportClient client, String dbName, String tableName, String docId) throws Exception{
		GetResponse response = client.prepareGet(dbName, tableName, docId).get();
		if (response!=null){
			ElasticData data = new ElasticData();
			data.setIndex(response.getIndex());
			data.setType(response.getType());
			data.setId(response.getId());
			data.setData(response.getSourceAsString());
			data.setMapData(response.getSourceAsMap());
			return data;
		}else{
			return null;
		}
	}

	/**
	 * 删除document
	 *
	 * @param dbName
	 *            索引名称
	 * @param tableName
	 *            类型名称
	 * @param documentId
	 *            要删除存储模型对象的id
	 * @throws Exception
	 */
	public static boolean deleteDocument(TransportClient client,String dbName, String tableName, String documentId) throws Exception {
		DeleteResponse response =client.prepareDelete(dbName, tableName, documentId).get();
		if (response!=null && response.status().equals(RestStatus.OK)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除document
	 *
	 * @param data
	 *            索引对象
	 * @throws Exception
	 */
	public static boolean deleteDocument(TransportClient client, ElasticData data) throws Exception {
		DeleteResponse response = client.prepareDelete(data.getIndex(), data.getType(), data.getId()).get();
		if (response!=null && response.status().equals(RestStatus.OK)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量刪除索引
	 * @param elasticData, es存储模型的列表(具体看EsData)
	 * @return
	 */
	public static boolean deleteDocuments(TransportClient client,List<ElasticData> elasticData) throws Exception {
		BulkRequestBuilder deleteBulk = client.prepareBulk();
		for (ElasticData data : elasticData) {
			deleteBulk.add(client.prepareDelete(data.getIndex(), data.getType(), data.getId()));
		}
		BulkResponse response=deleteBulk.execute().actionGet();
		if (response!=null && response.status().equals(RestStatus.OK)){
			return true;
		}else{
			return false;
		}
	}
	

	/**
	 * 根据索引名称删除索引
	 * @param dbName 索引名称
	 * @throws Exception
	 */
	public static boolean deleteIndex(TransportClient client,String dbName) throws Exception {
		IndicesExistsRequest ier = new IndicesExistsRequest();
		ier.indices(new String[] { dbName });
		boolean exists = client.admin().indices().exists(ier).actionGet().isExists();
		if (exists) {
			AcknowledgedResponse response=client.admin().indices().prepareDelete(dbName.toLowerCase()).get();
			if (response!=null && response.isAcknowledged()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	

	/**
	 * 更新document
	 *
	 * @param dbName 索引名称
	 * @param tableName  类型名称
	 * @param data
	 *            商品dto
	 * @throws Exception
	 */
	public static boolean updateDocument(TransportClient client,String dbName, String tableName, ElasticData data)
			throws Exception {
		// 如果新增的时候index存在，就是更新操作
		return addDocument(client,dbName, tableName, data);
	}
	
	/**
	 * 更新document
	 * @param data
	 *            商品dto
	 * @throws Exception
	 */
	public static boolean updateDocument(TransportClient client, ElasticData data)
			throws Exception {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(data.getIndex());
		updateRequest.type(data.getType());
		updateRequest.id(data.getId());
		if (data.isMap()){
			updateRequest.doc(data.getMapData());
		}else{
			updateRequest.doc(data.getData());
		}
		UpdateResponse response=client.update(updateRequest).get();
		if (response.status().equals(RestStatus.OK)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 执行搜索
	 *
	 * @param queryBuilder
	 * @param dbName
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static List<ElasticData> searcher(TransportClient client, QueryBuilder queryBuilder, String dbName, String tableName) throws Exception {
		SearchResponse response = client.prepareSearch(dbName).setTypes(tableName).get();
		//非空设置.
		if (queryBuilder != null) {
			response = client.prepareSearch(dbName).setTypes(tableName).setQuery(queryBuilder).get();
		}
		/**
		 * 遍历查询结果输出相关度分值和文档内容
		 */
		SearchHits searchHits =  response.getHits();
		logger.info("查询到记录数:{} 条." ,searchHits.getTotalHits());
		
		List<ElasticData> dataList = new ArrayList<ElasticData>();
		tranList(dbName, tableName, searchHits, dataList);
		return dataList;
	}
	
	/**
	 * 分页 执行搜索
	 * @param dbName
	 * @param tableName
	 * @param boolQuery
	 * @param sortBuilders
	 * @param from
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public static List<ElasticData> searcher(TransportClient client, String dbName, String tableName, QueryBuilder boolQuery,
	                                         List<FieldSortBuilder> sortBuilders, int from, int size) throws Exception {
		List<ElasticData> list = new ArrayList<ElasticData>();
		/**
		 * 遍历查询结果输出相关度分值和文档内容
		 */
		SearchHits searchHits = searchHits(client,dbName, tableName, boolQuery, sortBuilders, from, size);
		logger.info("查询到记录数:{}" + searchHits.getTotalHits());
		
		List<ElasticData> dataList = new ArrayList<ElasticData>();
		tranList(dbName, tableName, searchHits, dataList);
		return list;
	}
	
	/**
	 *  关键字的分页搜索
	 * @param dbName
	 * @param tableName
	 * @param keyWords
	 * @param channelIdArr
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public static List<ElasticData> searcher(TransportClient client, String dbName, String tableName, String[] keyWords, String[] channelIdArr, int pageNo, int pageSize) throws Exception {
		List<ElasticData> list = new ArrayList<ElasticData>();
		/**
		 * 遍历查询结果输出相关度分值和文档内容
		 */
		SearchHits searchHits = search(client,dbName, tableName, keyWords, channelIdArr, pageNo, pageSize);
		logger.info("查询到记录数:{}" + searchHits.getTotalHits());
		
		List<ElasticData> dataList = new ArrayList<ElasticData>();
		tranList(dbName, tableName, searchHits, dataList);
		return list;
	}
	
	/**
	 * 按照条件分页查询数据
	 * @param dbName
	 * @param tableName
	 * @param boolQuery
	 * @param sortBuilders
	 * @param from
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public static SearchHits searchHits(TransportClient client,String dbName, String tableName, QueryBuilder boolQuery,
			List<FieldSortBuilder> sortBuilders, int from, int size) throws Exception {
		client = checkIndex(client, dbName);
		/**
		 * 查询请求建立
		 */
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(dbName).setTypes(tableName);
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
	
	/**
	 * 按照关键字查询.
	 * @param client
	 * @param dbName
	 * @param tableName
	 * @param keyWords
	 * @param channelIdArr
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public static SearchHits search(TransportClient client,String dbName, String tableName, String[] keyWords, String[] channelIdArr, int pageNo, int pageSize) throws Exception {
		client = checkIndex(client, dbName);
		/**
		 * 查询请求建立
		 */
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(dbName).setTypes(tableName);
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
				keyword = badChars.matcher(keyword).replaceAll("");
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
	
	/**
	 * 去掉不存在的索引
	 *
	 * @param client
	 * @param dbName
	 * @return
	 * @throws Exception
	 */
	private static TransportClient checkIndex(TransportClient client, String dbName) throws Exception {
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
		}
		return client;
	}
	
	/**======================================================================== << 以下是实例应用 >> ===============================================================================*/
	
	/**
	 * 删除es中位置信息
	 * @param client
	 * @param dbName
	 * @param tableName
	 * @param busUuid
	 * @param lineUuid
	 * @throws Exception
	 */
	public static boolean deletePostion(TransportClient client,String dbName, String tableName, String busUuid, String lineUuid) throws Exception {
		List<ElasticData> esModelList = searchPostion(client,dbName, tableName, busUuid, lineUuid);
		if(esModelList.size() > 0){
			return deleteDocuments(client,esModelList);
		}else{
			return false;
		}
	}
	
	/**
	 * 查询位置信息
	 * @param client
	 * @param dbName
	 * @param tableName
	 * @param busUuid
	 * @param lineUuid
	 * @return
	 * @throws Exception
	 */
	public static List<ElasticData> searchPostion(TransportClient client, String dbName, String tableName, String busUuid, String lineUuid) throws Exception {
		if(!isExistsType(client,dbName, tableName)){
			return new ArrayList<ElasticData>();
		}
		/**
		 * 构建查询条件
		 */
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery("busUuid", busUuid))
				.must(QueryBuilders.termQuery("lineUuid", lineUuid))
				.must(QueryBuilders.rangeQuery("gatherTime").from("1970-01-01").to("2017-08-16"));
		/**
		 * 构建查询相应
		 */
		SearchResponse response = client.prepareSearch(dbName)
				.setTypes(tableName)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(queryBuilder)
				.setSize(10000)
				.execute()
				.actionGet();
		/**
		 * 遍历查询结果输出相关度分值和文档内容
		 */
		SearchHits searchHits =  response.getHits();
		
		List<ElasticData> dataList = new ArrayList<ElasticData>();
		tranList(dbName, tableName, searchHits, dataList);
		return dataList;
	}
	
	/**
	 * 给集合中添加数据
	 * @param dbName
	 * @param tableName
	 * @param searchHits
	 * @param dataList
	 */
	private static void tranList(String dbName, String tableName, SearchHits searchHits, List<ElasticData> dataList) {
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
