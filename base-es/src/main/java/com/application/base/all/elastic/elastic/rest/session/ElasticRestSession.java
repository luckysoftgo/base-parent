package com.application.base.all.elastic.elastic.rest.session;

import com.application.base.all.elastic.core.ElasticSession;
import com.application.base.all.elastic.entity.ElasticData;
import com.application.base.all.elastic.exception.ElasticException;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
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
import org.elasticsearch.client.transport.TransportClient;
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
import java.util.Map;

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
    public boolean addEsData(ElasticData data) throws ElasticException {
        try{
            IndexRequest request =new IndexRequest(data.getIndex(),data.getType(),data.getId());
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
    
    @Override
    public boolean addEsDataList(List<ElasticData> elasticData) throws ElasticException {
        BulkRequest bulkRequest = new BulkRequest();
        List<IndexRequest> requests = getIndexRequest(elasticData);
        for (IndexRequest indexRequest : requests) {
            bulkRequest.add(indexRequest);
        }
        try {
            BulkResponse response = getLevelClient().bulk(bulkRequest,RequestOptions.DEFAULT);
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
    
    private List<IndexRequest> getIndexRequest(List<ElasticData> elasticData) {
        List<IndexRequest> requests = new ArrayList<>();
        for (ElasticData data : elasticData) {
            requests.add(new IndexRequest(data.getIndex(),data.getType(),data.getId()));
        }
        return requests;
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
                Map<String,Object> info =response.getSourceAsMap();
                data.setData(info);
            }
            return data;
        }catch(Exception e){
            logger.error("检查index数据异常,异常信息为:{}",e);
        }
        return null;
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
    public boolean deleteEsDataList(List<ElasticData> elasticData) throws ElasticException {
        BulkRequest bulkRequest = new BulkRequest();
        List<DeleteRequest> requests = getDeleteRequest(elasticData);
        for (DeleteRequest deleteRequest : requests) {
            bulkRequest.add(deleteRequest);
        }
        try {
            BulkResponse response = getLevelClient().bulk(bulkRequest,RequestOptions.DEFAULT);
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
    
    private List<DeleteRequest> getDeleteRequest(List<ElasticData> elasticData) {
        List<DeleteRequest> requests = new ArrayList<>();
        for (ElasticData data : elasticData) {
            requests.add(new DeleteRequest(data.getIndex(),data.getType(),data.getId()));
        }
        return requests;
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
