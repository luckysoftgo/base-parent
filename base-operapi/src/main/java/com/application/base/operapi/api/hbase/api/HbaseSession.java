package com.application.base.operapi.api.hbase.api;

import com.application.base.operapi.api.hbase.core.HbaseBean;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.TableDescriptor;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author : 孤狼
 * @NAME: HbaseSession
 * @DESC: hbase 的接口 api
 **/
public interface HbaseSession {
	
	/**
	 * 创建namespace
	 * @param namespace
	 * @return
	 */
	public boolean createNamespace(String namespace);
	
	/**
	 * 删除namespace
	 * @param namespace
	 * @return
	 */
	public boolean deleteNamespace(String namespace);
	
	/**
	 * 判断表是否存在
	 * @param tableName
	 * @return
	 */
	public boolean tableExist(String tableName);
	
	/**
	 * 列出所有用户空间表
	 * @param pattern
	 * @return
	 */
	public List<String> listTables(Pattern pattern);
	
	/**
	 * 列出所有用户空间表
	 * @param pattern
	 * @return
	 */
	public List<TableDescriptor> listTablesDesc(Pattern pattern);
	
	/**
	 * 列出用户空间表的所有名称
	 * @param pattern
	 * @return
	 */
	public List<String> listTableNames(Pattern pattern);
	
	/**
	 * 列出用户空间表的所有名称
	 * @param pattern
	 * @return
	 */
	public List<TableName> listTNTableNames(Pattern pattern);
	
	/**
	 * 获取表描述
	 * @param tableName
	 * @return
	 */
	public TableDescriptor getTableDesc(String tableName);
	
	/**
	 * 是否禁用表
	 * @param tableName
	 * @return
	 */
	public boolean isTableDisabled(String tableName);
	
	/**
	 * 是否可用表
	 * @param tableName
	 * @return
	 */
	public boolean isTableAvailable(String tableName);
	
	/**
	 * 启用表
	 * @param tableName
	 * @return
	 */
	public boolean enableTable(String tableName,boolean isAsync);
	
	
	/**
	 * 禁用表
	 * @param tableName
	 * @return
	 */
	public boolean disableTable(String tableName,boolean isAsync);
	
	/**
	 * 将列族添加到现有表
	 * @param tableName
	 * @return
	 */
	public boolean addColumnFamily(String tableName,String columnFamily);
	
	/**
	 * 修改将列族
	 * @param tableName
	 * @return
	 */
	public boolean updateColumnFamily(String tableName,String columnFamily);
	
	/**
	 * 创建表结构
	 * @param tableName
	 * @return
	 */
	public boolean createTable(String tableName,String columnFamily);
	
	/**
	 * 创建表结构
	 * @param tableName
	 * @param columnFamilies
	 * @return
	 */
	public boolean createTable(String tableName,List<String> columnFamilies);
	
	/**
	 * 预分区创建表
	 * @param tableName 表名
	 * @param columnFamily 列族名的集合
	 * @param splitKeys 预分期region
	 * @return 是否创建成功
	 */
	public boolean createTableBySplitKeys(String tableName, List<String> columnFamily, byte[][] splitKeys);
	
	/**
	 * 刪除hbase的表
	 * @param tableName
	 */
	public boolean deleteTable(String tableName);
	
	/**
	 * 刪除hbase的表
	 * @param tableName
	 */
	public boolean deleteTable(String tableName,boolean isAsync);
	
	/**
	 * 通过row删除
	 * @param tableName
	 * @param rowKey
	 */
	public boolean deleteByRowKey(String tableName,String rowKey);
	
	/**
	 * 删除某条记录
	 * @param tableName  表名
	 * @param rowKey    rowKey
	 * @param columnFamily    列族名
	 * @param columkey  列名
	 */
	public boolean deleteData(String tableName,String rowKey,String columnFamily,String columkey);
	
	/**
	 * 根据columnFamily删除指定的列族
	 * @param tableName 表名
	 * @param columnFamily 列族
	 * @return boolean
	 */
	public boolean deleteColumnFamily(String tableName, String columnFamily,boolean isAysnc);
	
	/**
	 * 通过rows 删除多条.
	 * @param tableName
	 * @param rowKeys
	 */
	public boolean deleteMultiRows(String tableName,String[] rowKeys);
	
	
	/**
	 * 截断表,让表全线下线
	 * @param tableName
	 * @param preserveSplits
	 */
	public boolean truncateTable(String tableName,boolean preserveSplits);
	
	/**
	 * 获取所有result的信息
	 * @param tableName
	 * @return
	 */
	public List<HbaseBean> getAllResult(String tableName);
	
	/***
	 * 遍历查询指定表中的所有数据
	 * @param tableName
	 * @return
	 */
	public List<HbaseBean> getResultScanner(String tableName);
	
	/**
	 * 根据startRowKey和stopRowKey遍历查询指定表中的所有数据
	 * @param tableName 表名
	 * @param startRowKey 起始rowKey
	 * @param stopRowKey 结束rowKey
	 */
	public List<HbaseBean> getResultScanner(String tableName, String startRowKey, String stopRowKey);
	
	/**
	 * 通过行前缀过滤器查询数据
	 * @param tableName 表名
	 * @param prefix 以prefix开始的行键
	 */
	public List<HbaseBean> getResultScannerPrefixFilter(String tableName, String prefix);
	
	/**
	 * 通过列前缀过滤器查询数据
	 * @param tableName 表名
	 * @param prefix 以prefix开始的列名
	 */
	public List<HbaseBean> getResultScannerColumnPrefixFilter(String tableName, String prefix);
	
	/**
	 * 查询行键中包含特定字符的数据
	 * @param tableName 表名
	 * @param compareOperator 表名
	 * @param keyWord 包含指定关键词的行键
	 */
	public List<HbaseBean> getResultScannerRowFilter(String tableName, CompareOperator compareOperator, String keyWord);
	
	/**
	 * 查询列名中包含特定字符的数据
	 * @author zifangsky
	 * @param tableName 表名
	 * @param compareOperator 表名
	 * @param keyWord 包含指定关键词的列名
	 */
	public List<HbaseBean> getResultScannerQualifierFilter(String tableName,CompareOperator compareOperator, String keyWord);
	
	/**
	 * 根据不同条件查询数据
	 * @param tableName      表名
	 * @param columnFamily   列簇
	 * @param queryParam     过滤列集合   ("topicFileId,6282")=>("列,值")
	 * @param regex          分隔字符
	 * @param bool           查询方式：true:and ; false:or
	 *
	 * @return
	 */
	public List<HbaseBean> selectTableDataByFilter(String tableName,String columnFamily,List<String> queryParam,String regex,boolean bool);
	
	/**
	 * 查根据不同条件查询数据,并返回想要的单列 =>返回的列必须是过滤中存在
	 * @param tableName         表名
	 * @param columnFamily      列簇
	 * @param queryParam        过滤列集合   ("topicFileId,6282")=>("列,值")
	 * @param regex             分隔字符
	 * @param column            返回的列
	 * @param bool              查询方式：and 或 or | true : and ；false：or
	 * @return
	 */
	public List<HbaseBean> selectColumnValueByFilter(String tableName,String columnFamily,List<String> queryParam,String regex,String column,boolean bool);
	
	/**
	 * 分页的根据不同条件查询数据
	 * @param tableName         表名
	 * @param columnFamily      列簇
	 * @param queryParam        过滤列集合   ("topicFileId,6282")=>("列,值")
	 * @param regex             分隔字符
	 * @param bool              查询方式：and 或 or | true : and ；false：or
	 * @param pageSize          每页显示的数量
	 * @param lastRow           当前页的最后一行
	 * @return
	 */
	public List<HbaseBean> selectTableDataByFilterPage(String tableName,String columnFamily,List<String> queryParam,String regex,boolean bool,int pageSize,String lastRow);
	
	/**
	 * 分页查询表中所有数据信息
	 * @param tableName     表名
	 * @param pageSize      每页数量
	 * @param lastRow       当前页的最后一行
	 * @return
	 */
	public  List<HbaseBean> selectTableDataByPage(String tableName,int pageSize,String lastRow);
	
	/**
	 * 更新数据 为表的某个单元格赋值
	 * @param tableName  表名
	 * @param rowKey    rowKey
	 * @param columnFamily    列族名
	 * @param columkey  列名
	 * @param updateData 列值
	 */
	public boolean updateData(String tableName,String rowKey,String columnFamily,String columkey,String updateData);

	
	/**
	 *  插入数据
	 * @param tableName
	 * @param rowKey
	 * @param columnFamily
	 * @param column
	 * @param value
	 */
	public boolean insertOne(String tableName, String rowKey,String columnFamily, String column, String value);

	
	/**
	 * 往表中添加单条数据
	 * @param tableName 表明
	 * @param rowKey  指key
	 * @param columnFamily 列族名
	 * @param map    数据源
	 * @return
	 */
	public boolean insertOne(String tableName,String rowKey,String columnFamily,Map<String, String> map);

	/**
	 * 往表中添加多数据
	 * @param tableName Table
	 * @param rowKey rowKey
	 * @param tableName 表名
	 * @param columnFamily 列族名
	 * @param columns 列名数组
	 * @param values 列值得数组
	 * @return
	 */
	public boolean insertOrUpdate(String tableName,String rowKey,String columnFamily, String[] columns, String[] values);
	
	/**
	 * 往表中添加多数据
	 * @param tableName Table
	 * @param rowKey rowKey
	 * @param tableName 表名
	 * @param columnFamily 列族名
	 * @param columns 列名数组
	 * @param values 列值得数组
	 * @return
	 */
	public boolean insertBatchMore(String tableName,String rowKey,String columnFamily, String[] columns, String[] values);
	
	/**
	 * 根据tableName和rowKey精确查询一行的数据
	 * @param tableName
	 * @param rowKey
	 */
	public List<HbaseBean> getRowData(String tableName, String rowKey);

	
	/**
	 * 获取指定行的数据
	 * @param tableName 表名
	 * @param rowKey 行键key
	 * @param columnFamily 列族
	 * @param column 列名
	 */
	public String getValData(String tableName,String rowKey,String columnFamily,String column);
	
	/**
	 * 根据tableName、rowKey、columnFamily、column查询指定单元格的数据
	 * @param tableName 表名
	 * @param rowKey rowKey
	 * @param columnFamily 列族名
	 * @param columnName 列名
	 */
	public String getColumnValue(String tableName, String rowKey, String columnFamily, String columnName);
	
	/**
	 * 获取指定表的所有数据
	 * @param tableName 表名
	 * @throws IOException
	 */
	public List<HbaseBean> getTableData(String tableName);
	
	/**
	 * 通过列簇获取下面所有的列的数据
	 * @param tableName
	 * @param columnFamily
	 */
	public List<HbaseBean> getDataByFamilyColumn(String tableName,String columnFamily,String column);
	
	/**
	 * 根据tableName、rowKey、columnFamily、column查询指定单元格的数据
	 * @param tableName 表名
	 * @param rowKey rowKey
	 * @param columnFamily 列族名
	 * @param columnName 列名
	 */
	public List<HbaseBean> getDataByFamilyColumn(String tableName, String rowKey, String columnFamily, String columnName);
	
	/**
	 * 根据tableName、rowKey、columnFamily、column查询指定单元格多个版本的数据
	 * @param tableName 表名
	 * @param rowKey rowKey
	 * @param columnFamily 列族名
	 * @param columnName 列名
	 * @param versions 需要查询的版本数
	 */
	public List<HbaseBean> getColumnValuesByVersion(String tableName, String rowKey, String columnFamily, String columnName,int versions) ;
	
	/**
	 * 自定义获取分区splitKeys
	 * @param keys
	 * @return
	 */
	public byte[][] getSplitKeys(String[] keys);
	
	/**
	 * 按startKey和endKey，分区数获取分区
	 * @param startKey
	 * @param endKey
	 * @param numRegions
	 * @return
	 */
	public byte[][] getHexSplits(String startKey, String endKey, int numRegions) ;
}
