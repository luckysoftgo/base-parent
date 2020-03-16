package com.application.test;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @Ddes:
 *    通过BulkProcess批量将Mysql数据导入ElasticSearch中.
 */
public class BulkProcessDemo {
	
	private static final Logger logger = LogManager.getLogger(BulkProcessDemo.class);
	
	/**
	 * 测试.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			long startTime = System.currentTimeMillis();
			String tableName = "testTable";
			createIndex(tableName);
			writeMysqlDataToES(tableName);
			logger.info(" use time: " + (System.currentTimeMillis() - startTime) / 1000 + "s");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建索引
	 *
	 * @param indexName
	 * @throws IOException
	 */
	public static void createIndex(String indexName) throws IOException {
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("hdp06", 8577, "http")));
		
		// ES 索引默认需要小写，故笔者将其转为小写
		CreateIndexRequest requestIndex = new CreateIndexRequest(indexName.toLowerCase());
		// 注： 设置副本数为0，索引刷新时间为-1对大批量索引数据效率的提升有不小的帮助
		requestIndex.settings(
				Settings.builder()
						.put("index.number_of_shards", 5)
						.put("index.number_of_replicas", 0)
						.put("index.refresh_interval", "-1")
		);
		CreateIndexResponse createIndexResponse = client.indices().create(requestIndex, RequestOptions.DEFAULT);
		if (createIndexResponse.isAcknowledged()) {
			System.out.println("创建索引成功!");
		} else {
			System.out.println("创建索引失败!");
		}
		client.close();
	}
	
	/**
	 * 将mysql 数据查出组装成es需要的map格式，通过批量写入es中
	 *
	 * @param tableName
	 */
	private static void writeMysqlDataToES(String tableName) {
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("eshost", 9200, "http")));// 初始化
		BulkProcessor bulkProcessor = getBulkProcessor(client);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			logger.info("Start handle data :" + tableName);
			
			String sql = "SELECT * from " + tableName;
			
			ps = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ps.setFetchSize(Integer.MIN_VALUE);
			rs = ps.executeQuery();
			
			ResultSetMetaData colData = rs.getMetaData();
			
			ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
			
			// bulkProcessor 添加的数据支持的方式并不多，查看其api发现其支持map键值对的方式，故笔者在此将查出来的数据转换成hashMap方式
			HashMap<String, String> map = null;
			int count = 0;
			String c = null;
			String v = null;
			while (rs.next()) {
				count++;
				map = new HashMap<String, String>(100);
				for (int i = 1; i <= colData.getColumnCount(); i++) {
					c = colData.getColumnName(i);
					v = rs.getString(c);
					map.put(c, v);
				}
				dataList.add(map);
				// 每10万条写一次，不足的批次的最后再一并提交
				if (count % 100000 == 0) {
					logger.info("Mysql handle data number : " + count);
					// 将数据添加到 bulkProcessor 中
					for (HashMap<String, String> hashMap2 : dataList) {
						bulkProcessor.add(new IndexRequest(tableName.toLowerCase(), "gzdc", hashMap2.get("S_GUID"))
								.source(hashMap2));
					}
					// 每提交一次便将map与list清空
					map.clear();
					dataList.clear();
				}
			}
			
			// count % 100000 处理未提交的数据
			for (HashMap<String, String> hashMap2 : dataList) {
				bulkProcessor.add(
						new IndexRequest(tableName.toLowerCase(), "gzdc", hashMap2.get("S_GUID")).source(hashMap2));
			}
			
			logger.info("-------------------------- Finally insert number total : " + count);
			// 将数据刷新到es, 注意这一步执行后并不会立即生效，取决于bulkProcessor设置的刷新时间
			bulkProcessor.flush();
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
				boolean terminatedFlag = bulkProcessor.awaitClose(150L, TimeUnit.SECONDS);
				client.close();
				logger.info(terminatedFlag);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	/**
	 * 创建bulkProcessor并初始化
	 *
	 * @param client
	 * @return
	 */
	private static BulkProcessor getBulkProcessor(RestHighLevelClient client) {
		
		BulkProcessor bulkProcessor = null;
		try {
			
			BulkProcessor.Listener listener = new BulkProcessor.Listener() {
				@Override
				public void beforeBulk(long executionId, BulkRequest request) {
					logger.info("Try to insert data number:" + request.numberOfActions());
				}
				@Override
				public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
					logger.info("************** Success insert data number:" + request.numberOfActions() + ",id:"+ executionId);
				}
				@Override
				public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
					logger.error("Bulk is unsuccess:" + failure + ",executionId:" + executionId);
				}
			};
			BiConsumer<BulkRequest, ActionListener<BulkResponse>> bulkConsumer = (request, bulkListener) -> client
					.bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
			//bulkProcessor = BulkProcessor.builder(bulkConsumer, listener).build();
			BulkProcessor.Builder builder = BulkProcessor.builder(bulkConsumer, listener);
			builder.setBulkActions(5000);
			//
			builder.setBulkSize(new ByteSizeValue(100L, ByteSizeUnit.MB));
			builder.setConcurrentRequests(10);
			builder.setFlushInterval(TimeValue.timeValueSeconds(100L));
			builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L), 3));
			// 注意点：在这里感觉有点坑，官网样例并没有这一步，而笔者因一时粗心也没注意，在调试时注意看才发现，上面对builder设置的属性没有生效
			bulkProcessor = builder.build();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				bulkProcessor.awaitClose(100L, TimeUnit.SECONDS);
				client.close();
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
		return bulkProcessor;
	}
}
