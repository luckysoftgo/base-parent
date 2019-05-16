package com.application.base.all.elastic.elastic.rest.session;

import com.application.base.all.elastic.core.ElasticSession;
import com.application.base.all.elastic.entity.ElasticData;
import com.application.base.all.elastic.exception.ElasticException;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author 孤狼
 * @ClassName : ElasticRestSession.
 * @Desc : 操作elastic 的实现.
 */
public class ElasticRestSession implements ElasticSession {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private RestHighLevelClient levelClient;
    
    public RestHighLevelClient getLevelClient() {
        return levelClient;
    }
    public void setLevelClient(RestHighLevelClient levelClient) {
        this.levelClient = levelClient;
    }
    
    @Override
    public void flushEs(String index, String type) throws ElasticException {
    
    }
    
    @Override
    public void close() throws ElasticException {
    
    }
    
    @Override
    public boolean judgeIndexExist(String index) throws ElasticException {
        return false;
    }
    
    @Override
    public boolean judgeTypeExist(String index, String type) throws ElasticException {
        return false;
    }
    
    @Override
    public boolean addEsIndex(String index) throws ElasticException {
        return false;
    }
    
    @Override
    public boolean addEsType(String index, String type) throws ElasticException {
        return false;
    }
    
    @Override
    public boolean addEsData(ElasticData data) throws ElasticException {
        return false;
    }
    
    @Override
    public boolean addEsDataList(List<ElasticData> elasticData) throws ElasticException {
        return false;
    }
    
    @Override
    public String getDataById(ElasticData data) throws ElasticException {
        return null;
    }
    
    @Override
    public ElasticData getDataInfoById(ElasticData data) throws ElasticException {
        return null;
    }
    
    @Override
    public boolean deleteEsData(ElasticData data) throws ElasticException {
        return false;
    }
    
    @Override
    public boolean deleteEsDataList(List<ElasticData> elasticData) throws ElasticException {
        return false;
    }
    
    @Override
    public boolean deleteIndex(String index) throws ElasticException {
        return false;
    }
    
    @Override
    public boolean updateEsData(ElasticData data) throws ElasticException {
        return false;
    }
    
    @Override
    public List<ElasticData> searcher(QueryBuilder queryBuilder, String index, String type) throws ElasticException {
        return null;
    }
    
    @Override
    public List<ElasticData> searcher(String index, String type, QueryBuilder boolQuery,
                                      List<FieldSortBuilder> sortBuilders, int from, int size) throws ElasticException {
        return null;
    }
    
    @Override
    public List<ElasticData> searcher(String index, String type, String[] keyWords, String[] channelIdArr, int pageNo, int pageSize) throws ElasticException {
        return null;
    }
    
    @Override
    public SearchHits searchHits(String index, String type, QueryBuilder boolQuery, List<FieldSortBuilder> sortBuilders, int from, int size) throws ElasticException {
        return null;
    }
    
    @Override
    public SearchHits search(String index, String type, String[] keyWords, String[] channelIdArr, int pageNo, int pageSize) throws ElasticException {
        return null;
    }
}
