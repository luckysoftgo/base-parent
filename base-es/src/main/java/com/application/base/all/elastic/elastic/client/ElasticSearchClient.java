package com.application.base.all.elastic.elastic.client;

import com.application.base.all.elastic.elastic.util.Pool;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * @NAME: ElasticSearchClient.
 * @DESC: 客户端实例.
 * @USER: 孤狼.
 **/
public class ElasticSearchClient extends PreBuiltTransportClient {

    public ElasticSearchClient(Settings settings, Class<? extends Plugin>[] plugins) {
        super(settings, plugins);
    }

    protected Pool<ElasticSearchClient> dataSource = null;

    public Pool<ElasticSearchClient> getDataSource() {
        return dataSource;
    }

    public void setDataSource(ElasticPool elasticPool) {
        this.dataSource = dataSource;
    }
}
