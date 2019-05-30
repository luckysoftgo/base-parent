package com.application.base.all.elastic.elastic.rest.session;

import com.application.base.all.elastic.core.ElasticSession;
import com.application.base.all.elastic.entity.ElasticData;
import com.application.base.all.elastic.exception.ElasticException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
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
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @author 孤狼
 * @ClassName : ElasticRestSession.
 * @Desc : 操作elastic 的实现.
 */
public class ElasticRestSession implements ElasticSession {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private RestHighLevelClient levelClient;
    
    public RestHighLevelClient getLevelClient() {
        if (null==levelClient){
            logger.error("[elastic错误:{}]","获得client操作实例对象为空");
            throw new ElasticException("获得restHighLevelClient实例对象为空");
        }
        return levelClient;
    }
    public void setLevelClient(RestHighLevelClient levelClient) {
        this.levelClient = levelClient;
    }
    
    @Override
    public void flushEs(String index, String type) throws ElasticException {
        try{
            getLevelClient().indices().flush(new FlushRequest(index),RequestOptions.DEFAULT);
        }catch(Exception e){
            logger.error("刷新 es 异常,异常信息为:{}",e);
        }
    }
    
    @Override
    public void close() throws ElasticException {
        try{
            getLevelClient().close();
        }catch(Exception e){
            logger.error("关闭es rest连接异常,异常信息为:{}",e);
        }
    }
    
    @Override
    public boolean judgeIndexExist(String index) throws ElasticException {
        try{
            GetRequest request = new GetRequest(index);
            return getLevelClient().exists(request,RequestOptions.DEFAULT);
        }catch(Exception e){
            logger.error("检查index数据异常,异常信息为:{}",e);
        }
        return false;
    }
    
    @Override
    public boolean judgeTypeExist(String index, String type) throws ElasticException {
        try{
            GetRequest request =new GetRequest();
            request.index(index);
            request.type(type);
            return getLevelClient().exists(request,RequestOptions.DEFAULT);
        }catch(Exception e){
            logger.error("检查index type异常,异常信息为:{}",e);
        }
        return false;
    }
    
    @Override
    public boolean addEsIndex(String index) throws ElasticException {
        try{
            IndexRequest request = new IndexRequest(index);
            IndexResponse response = getLevelClient().index(request,RequestOptions.DEFAULT);
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
    
    @Override
    public boolean deleteIndex(String index) throws ElasticException {
        try{
            DeleteRequest request = new DeleteRequest(index);
            DeleteResponse response = getLevelClient().delete(request,RequestOptions.DEFAULT);
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
    
    @Override
    public boolean addEsType(String index, String type) throws ElasticException {
        try{
            IndexRequest request =new IndexRequest(index,type);
            IndexResponse response = getLevelClient().index(request,RequestOptions.DEFAULT);
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
    
    @Override
    public String getDataById(ElasticData data) throws ElasticException {
        try{
            GetRequest request = new GetRequest(data.getIndex(),data.getType(),data.getId());
            GetResponse response = getLevelClient().get(request,RequestOptions.DEFAULT);
            if (response!=null){
                return response.getSourceAsString();
            }
            return null;
        }catch(Exception e){
            logger.error("检查index数据异常,异常信息为:{}",e);
        }
        return null;
    }
    
    @Override
    public ElasticData getDataInfoById(ElasticData data) throws ElasticException {
        try{
            GetRequest request = new GetRequest(data.getIndex(),data.getType(),data.getId());
            GetResponse response = getLevelClient().get(request,RequestOptions.DEFAULT);
            if (response!=null){
                if (data.isMapFlag()){
                    data.setData(response.getSourceAsString());
                }else{
                    data.setMapData(response.getSourceAsMap());
                }
            }
            return data;
        }catch(Exception e){
            logger.error("检查index数据异常,异常信息为:{}",e);
        }
        return null;
    }
    
    @Override
    public boolean addEsData(ElasticData data) throws ElasticException {
        try{
            IndexRequest request =new IndexRequest(data.getIndex(),data.getType(),data.getId());
            if (data.isMapFlag()){
                request.source(data.getMapData());
            }else{
                request.source(data.getData(),XContentType.JSON);
            }
            IndexResponse response = getLevelClient().index(request,RequestOptions.DEFAULT);
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
    
    /**
     * 获得结果.
     * @param elasticData
     * @return
     */
    private List<IndexRequest> getIndexList(List<ElasticData> elasticData){
        List<IndexRequest> resultList=new ArrayList<>();
        for (ElasticData data : elasticData) {
            IndexRequest request = new IndexRequest(data.getIndex(),data.getType(),data.getId());
            if (data.isMapFlag()){
                request.source(data.getMapData());
            }else{
                request.source(data.getData(),XContentType.JSON);
            }
            resultList.add(request);
        }
        return resultList;
    }
    
    @Override
    public boolean addEsDataList(List<ElasticData> elasticData, boolean async) throws ElasticException {
        BulkRequest bulkRequest = getBulkRequest();
        List<IndexRequest> indexList=getIndexList(elasticData);
        for (IndexRequest data : indexList) {
            bulkRequest.add(data);
        }
        //处理操作.
        return dealBulkRequest(bulkRequest,async);
    }
    
    @Override
    public boolean addEsDataListByProcessor(List<ElasticData> elasticData, boolean async) throws ElasticException {
        RestHighLevelClient levelClient=getLevelClient();
        BulkProcessor bulkProcessor = getBulkProcessor(levelClient,async);
        List<IndexRequest> indexList=getIndexList(elasticData);
        for (IndexRequest data : indexList) {
            bulkProcessor.add(data);
        }
        return dealBulkProcessor(bulkProcessor);
    }
    
    /**
     * 批量处理器处理.
     * @param bulkProcessor
     * @return
     */
    private boolean dealBulkProcessor(BulkProcessor bulkProcessor) {
        //将数据刷新到es, 注意这一步执行后并不会立即生效，取决于bulkProcessor设置的刷新时间
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
    
    /**
     * 获得请求的实例.
     * @return
     */
    private BulkRequest getBulkRequest() {
        BulkRequest bulkRequest = new BulkRequest();
        //3分钟.
        bulkRequest.timeout(TimeValue.timeValueMinutes(3));
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        bulkRequest.waitForActiveShards(ActiveShardCount.ALL);
        return bulkRequest;
    }
    
    /**
     * @desc: 创建bulkProcessor并初始化配置信息.
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.6/java-rest-high-document-bulk.html
     * @param highLevelClient
     * @param async true:异步,否则是同步
     * @return
     */
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
    
    /**
     * 处理返回信息.
     * @param bulkRequest
     * @return
     */
    private boolean dealBulkRequest(BulkRequest bulkRequest, boolean async) {
        BulkResponse response =null;
        try {
            if (async){
                ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
                    @Override
                    public void onResponse(BulkResponse bulkResponse) {
                        logger.debug("操作elasticserach处理成功!");
                    }
                    @Override
                    public void onFailure(Exception e) {
                        logger.debug("操作处理失败,失败信息是:{}",e);
                    }
                };
                getLevelClient().bulkAsync(bulkRequest, RequestOptions.DEFAULT,listener);
                return true;
            }else{
                response = getLevelClient().bulk(bulkRequest, RequestOptions.DEFAULT);
                // 处理操作返回信息
                /* *
                //用来处理返回的结果信息.
                for (BulkItemResponse bulkItemResponse : response) {
                    DocWriteResponse itemResponse = bulkItemResponse.getResponse();
                    switch (bulkItemResponse.getOpType()) {
                        case INDEX:
                        case CREATE:
                            IndexResponse indexResponse = (IndexResponse) itemResponse;
                            break;
                        case UPDATE:
                            UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                            break;
                        case DELETE:
                            DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                    }
                }
                */
                
                if (response!=null && response.hasFailures()) {
                    long count = 0L;
                    for (BulkItemResponse bulkItemResponse : response) {
                        logger.debug("操作发生异常,异常的索引id为:" + bulkItemResponse.getId() + ",错误信息为:" + bulkItemResponse.getFailureMessage());
                        count++;
                    }
                    logger.debug("====================操作elasticserach异常,异常信息共有: " + count + " 条记录==========================");
                    return false;
                }else{
                    return true;
                }
            }
        } catch (IOException e) {
            logger.error("批量操作elasticserach异常,异常信息为:{}",e);
            return false;
        }
    }
    
    @Override
    public boolean deleteEsData(ElasticData data) throws ElasticException {
        try{
            DeleteRequest deleteRequest = new DeleteRequest(data.getIndex(), data.getType(), data.getId());
            //同步执行
            DeleteResponse deleteResponse = getLevelClient().delete(deleteRequest, RequestOptions.DEFAULT);
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
    
    @Override
    public boolean deleteEsDataList(List<ElasticData> elasticData, boolean async) throws ElasticException {
        BulkRequest bulkRequest = getBulkRequest();
        for (ElasticData data : elasticData) {
            bulkRequest.add(new DeleteRequest(data.getIndex(),data.getType(),data.getId()));
        }
        //处理操作
        return dealBulkRequest(bulkRequest,async);
    }
    
    @Override
    public boolean deleteEsDataListByProcessor(List<ElasticData> elasticData, boolean async) throws ElasticException {
        RestHighLevelClient levelClient=getLevelClient();
        BulkProcessor bulkProcessor = getBulkProcessor(levelClient,async);
        for (ElasticData data : elasticData) {
            bulkProcessor.add(new DeleteRequest(data.getIndex(),data.getType(),data.getId()));
        }
        return dealBulkProcessor(bulkProcessor);
    }
    
    @Override
    public boolean updateEsData(ElasticData data) throws ElasticException {
        try{
            UpdateRequest request = new UpdateRequest(data.getIndex(),data.getType(),data.getId());
            UpdateResponse response = getLevelClient().update(request,RequestOptions.DEFAULT);
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
    
    @Override
    public boolean updateEsDataList(List<ElasticData> elasticData, boolean async) throws ElasticException {
        BulkRequest bulkRequest = getBulkRequest();
        for (ElasticData data : elasticData) {
            bulkRequest.add(new UpdateRequest(data.getIndex(),data.getType(),data.getId()));
        }
        //处理操作
        return dealBulkRequest(bulkRequest,async);
    }
    
    @Override
    public boolean updateEsDataListByProcessor(List<ElasticData> elasticData, boolean async) throws ElasticException {
        RestHighLevelClient levelClient=getLevelClient();
        BulkProcessor bulkProcessor = getBulkProcessor(levelClient,async);
        for (ElasticData data : elasticData) {
            bulkProcessor.add(new UpdateRequest(data.getIndex(),data.getType(),data.getId()));
        }
        return dealBulkProcessor(bulkProcessor);
    }
    
    @Override
    public List<ElasticData> searcher(QueryBuilder queryBuilder, String index, String type) throws ElasticException {
        try{
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(queryBuilder);
            SearchRequest request = new SearchRequest(index);
            request.types(type);
            request.source(sourceBuilder);
            SearchResponse response=getLevelClient().search(request,RequestOptions.DEFAULT);
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
    
    @Override
    public List<ElasticData> searcher(String index, String type, QueryBuilder queryBuilder,
                                      List<FieldSortBuilder> sortBuilders, int from, int size) throws ElasticException {
        try {
            /**
             * 遍历查询结果输出相关度分值和文档内容
             */
            SearchHits searchHits = searchHits(index, type, queryBuilder, sortBuilders, from, size);
            logger.info("查询到记录数:{}" + searchHits.getTotalHits());
            List<ElasticData> dataList = new ArrayList<ElasticData>();
            tranList(index, type, searchHits, dataList);
            return dataList;
        }catch (Exception e){
            logger.error("查询index数据异常,异常信息为:{}",e);
        }
        return  null;
    }
    
    @Override
    public List<ElasticData> searcher(String index, String type, String[] keyWords, String[] keyVals, int pageNo, int pageSize) throws ElasticException {
        try {
            /**
             * 遍历查询结果输出相关度分值和文档内容
             */
            SearchHits searchHits = search(index, type, keyWords, keyVals, pageNo, pageSize);
            logger.info("查询到记录数:{}" + searchHits.getTotalHits());
            List<ElasticData> dataList = new ArrayList<ElasticData>();
            tranList(index, type, searchHits, dataList);
            return dataList;
        }catch (Exception e){
            logger.error("查询index数据异常,异常信息为:{}",e);
        }
        return  null;
    }
    
    @Override
    public SearchHits searchHits(String index, String type, QueryBuilder boolQuery, List<FieldSortBuilder> sortBuilders, int from, int size) throws ElasticException {
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
            SearchResponse response = getLevelClient().search(searchRequest,RequestOptions.DEFAULT);
            return response.getHits();
        }catch (Exception e){
            logger.error("查询index数据异常,异常信息为:{}",e);
        }
        return null;
    }
    
    @Override
    public SearchHits search(String index, String type, String[] keyWords, String[] keyVals, int pageNo, int pageSize) throws ElasticException {
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.from(pageNo);
            sourceBuilder.size(pageSize);
            sourceBuilder.query(QueryBuilders.termsQuery(keyWords[0],keyVals));
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(index);
            searchRequest.types(type);
            searchRequest.source(sourceBuilder);
            SearchResponse response = getLevelClient().search(searchRequest,RequestOptions.DEFAULT);
            return response.getHits();
        }catch (Exception e){
            logger.error("查询index数据异常,异常信息为:{}",e);
        }
        return null;
    }
    
    @Override
    @Deprecated
    public TransportClient getTransClient() {
        return null;
    }
    
    @Override
    public RestHighLevelClient getHighClient() {
        return getLevelClient();
    }
}
