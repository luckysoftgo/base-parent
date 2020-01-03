package com.application.base.operapi.api.hbase.core;

import com.application.base.operapi.api.hbase.bean.HbaseBean;
import com.application.base.operapi.api.hbase.bean.TableDesc;
import com.application.base.operapi.api.hbase.config.HbaseConfig;
import com.application.base.operapi.api.hbase.exception.HbaseException;
import com.application.base.operapi.api.hbase.util.HbaseConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * @author : 孤狼
 * @NAME: HbaseClient
 * @DESC: hbase 操作数据客户端
 * HBase的查询实现只提供两种方式：
 *    1、按指定RowKey获取唯一一条记录，get方法（org.apache.hadoop.hbase.client.Get）
 *    2、按指定的条件获取一批记录，scan方法（org.apache.Hadoop.Hbase.client.Scan）
 * 在线 API 查看.
 *    https://blog.csdn.net/super_ozman/article/details/48009561
 *    https://help.aliyun.com/document_detail/119570.html
 *    https://blog.csdn.net/u010391342/article/details/81624914
 *    https://blog.csdn.net/qq_39192827/article/details/95459083
 *    https://yq.aliyun.com/articles/674755
 **/
public class HbaseClient {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 声明静态配置.
	 */
	private Configuration configuration = null;
	/**
	 * 配置信息
	 */
	private HbaseConfig hbaseConfig;
	/**
	 * postfix
	 */
	private final byte[] POSTFIX = new byte[] {0x00};
	
	/**
	 * 存在的连接.
	 */
	private LinkedBlockingQueue<Connection> connections = new LinkedBlockingQueue<>(1024);
	
	/**
	 * 构造函数.
	 * @param hbaseConfig
	 */
	public HbaseClient(HbaseConfig hbaseConfig) {
		this.hbaseConfig = hbaseConfig;
	}
	
	/**
	 * 初始化 hbase 的操作.
	 */
	public Connection hbaseConnect(){
		// windows环境下 解决hadoop调用本地库问题
		System.setProperty(HbaseConfig.HADOO_PHOME_DIR,hbaseConfig.getHadoopDir());
		//使用默认的classpath下的hbase-site.xml配置
		configuration = HBaseConfiguration.create();
		// 集群的连接地址，在控制台页面的数据库连接界面获得(注意公网地址和VPC内网地址)
		configuration.set("hbase.zookeeper.quorum", hbaseConfig.getZookeeperQuorum());
		configuration.set("hbase.zookeeper.property.clientPort", hbaseConfig.getZookeeperPort());
		// 设置用户名密码，默认root:root，可根据实际情况调整
		if (StringUtils.isNotBlank(hbaseConfig.getLoginUser())){
			configuration.set("hbase.client.username", hbaseConfig.getLoginUser());
		}
		if (StringUtils.isNotBlank(hbaseConfig.getLoginPass())){
			configuration.set("hbase.client.password", hbaseConfig.getLoginPass());
		}
		if (StringUtils.isNotBlank(hbaseConfig.getMaster())){
			configuration.set("hbase.master", hbaseConfig.getMaster());
		}
		if (StringUtils.isNotBlank(hbaseConfig.getRootDir())){
			configuration.set("hbase.rootdir", hbaseConfig.getRootDir());
		}
		//先检查实例是否存在，如果不存在才进入下面的同步块
		Connection conn = null;
		try {
			synchronized (HbaseClient.class){
				conn = ConnectionFactory.createConnection(configuration);
				connections.put(conn);
			}
			return conn;
		} catch (Exception e) {
			logger.error("初始化连接异常了,异常信息是:{}",e.getMessage());
			throw new HbaseException("hbase获得连接异常了,异常信息是:{"+e.getMessage()+"}");
		}
	}
	
	/**
	 * 获取 connection 连接.
	 * @return
	 */
	public Connection getConnection(){
		try {
			int maxTotal = hbaseConfig.getMaxTotal();
			if (connections!=null && connections.size()>0){
				int count = hbaseConfig.getMinIdle();
				if (connections.size() < count){
					int addCount = maxTotal - count;
					for (int i = 0; i < addCount ; i++) {
						hbaseConnect();
					}
				}
			}else{
				for (int i = 0; i < maxTotal ; i++) {
					hbaseConnect();
				}
			}
			return connections.take();
		}catch (InterruptedException e){
			logger.error("获取connect连接异常了,错误异常是:{}",e.getMessage());
			return null;
		}
	}
	
	/**
	 * 创建namespace
	 * @param namespace
	 * @return
	 */
	public boolean createNamespace(String namespace){
		boolean exist = true;
		Admin admin = null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			NamespaceDescriptor descriptor = NamespaceDescriptor.create(namespace).build();
			admin.createNamespace(descriptor);
		}catch (Exception e){
			exist = false;
			logger.error("创建目录异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,admin,null,null);
		}
		return exist;
	}
	
	/**
	 * 删除namespace
	 * @param namespace
	 * @return
	 */
	public boolean deleteNamespace(String namespace){
		boolean exist = true;
		Admin admin = null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			admin.deleteNamespace(namespace);
		}catch (Exception e){
			exist = false;
			logger.error("删除目录发生了异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,admin,null,null);
		}
		return exist;
	}
	
	/**
	 * 判断表是否存在
	 * @param tableName
	 * @return
	 */
	public boolean tableExist(String tableName){
		boolean exist = true;
		Admin admin = null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			exist = admin.tableExists(TableName.valueOf(tableName));
		}catch (Exception e){
			exist = false;
			logger.error("判断表是否存在发生了异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,admin,null,null);
		}
		return exist;
	}
	
	/**
	 * 列出所有用户空间表
	 * @param pattern
	 * @return
	 */
	public List<String> listTables(Pattern pattern){
		List<String> tableList=new ArrayList<>();
		try {
			List<TableDesc> tableDescs = listTablesDesc(pattern);
			if (!tableDescs.isEmpty()){
				for (int i = 0; i < tableDescs.size(); i++) {
					tableList.add(tableDescs.get(i).getTableName());
				}
			}
		} catch (Exception e) {
			logger.error("获得hbase所有表信息发生异常,异常信息是:{}",e.getMessage());
		}
		return tableList;
	}
	
	/**
	 * 列出所有用户空间表
	 * @param pattern
	 * @return
	 */
	public List<TableDesc> listTablesDesc(Pattern pattern){
		List<TableDesc> tableList=new ArrayList<>();
		Admin admin=null;
		Connection conn = getConnection();
		List<TableDescriptor> tmpList=new ArrayList<>();
		try {
			admin = conn.getAdmin();
			if(pattern==null){
				tmpList = admin.listTableDescriptors();
			}else{
				tmpList =admin.listTableDescriptors(pattern);
			}
		} catch (Exception e) {
			logger.error("获得hbase列出所有用户空间表信息发生异常,异常信息是:{}",e.getMessage());
		}finally{
			close(conn,admin,null,null);
		}
		for (TableDescriptor descriptor : tmpList ) {
			tableList.add(HbaseConvertUtil.constructTableDesc(descriptor));
		}
		return tableList;
	}
	
	/**
	 * 列出用户空间表的所有名称
	 * @param pattern
	 * @return
	 */
	public List<String> listTableNames(Pattern pattern){
		List<String> tableList=new ArrayList<>();
		try {
			List<TableName> tableNames=listTNTableNames(pattern);
			for (int i = 0; i < tableNames.size(); i++) {
				tableList.add(tableNames.get(i).getNameAsString());
			}
		} catch (Exception e) {
			logger.error("获得hbase所有表信息发生异常,异常信息是:{}",e.getMessage());
		}
		return tableList;
	}
	
	/**
	 * 列出用户空间表的所有名称
	 * @param pattern
	 * @return
	 */
	public List<TableName> listTNTableNames(Pattern pattern){
		List<TableName> tableList=new ArrayList<>();
		Admin admin=null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			TableName [] tableNames;
			if(pattern==null){
				tableNames=admin.listTableNames();
			}else{
				tableNames=admin.listTableNames(pattern);
			}
			for (int i = 0; i < tableNames.length; i++) {
				tableList.add(tableNames[i]);
			}
		} catch (Exception e) {
			logger.error("获得hbase所有表信息发生异常,异常信息是:{}",e.getMessage());
		}finally{
			close(conn,admin,null,null);
		}
		return tableList;
	}
	
	/**
	 * 获取表描述
	 * @param tableName
	 * @return
	 */
	public TableDescriptor getTableDesc(String tableName){
		Admin admin=null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			TableDescriptor descriptor = admin.getDescriptor(TableName.valueOf(tableName));
			if (descriptor!=null){
				return descriptor;
			}
		} catch (Exception e) {
			logger.error("获得hbase表:{}信息发生异常,异常信息是:{}",tableName,e.getMessage());
		}finally{
			close(conn,admin,null,null);
		}
		return null;
	}
	
	/**
	 * 是否禁用表
	 * @param tableName
	 * @return
	 */
	public boolean isTableDisabled(String tableName){
		boolean result = true;
		Admin admin = null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			TableName name = TableName.valueOf(tableName);
			result = admin.isTableDisabled(name);
		}catch (Exception e){
			result = false;
			logger.error("查询hbase表{}是否禁用发生异常,异常信息是:{}",tableName,e.getMessage());
		}finally {
			close(conn,admin,null,null);
		}
		return result;
	}
	
	/**
	 * 是否可用表
	 * @param tableName
	 * @return
	 */
	public boolean isTableAvailable(String tableName){
		boolean result = true;
		Admin admin = null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			TableName name = TableName.valueOf(tableName);
			result = admin.isTableAvailable(name);
		}catch (Exception e){
			result = false;
			logger.error("查询hbase表{}是否可用发生异常,异常信息是:{}",tableName,e.getMessage());
		}finally {
			close(conn,admin,null,null);
		}
		return result;
	}
	
	/**
	 * 启用表
	 * @param tableName
	 * @return
	 */
	public boolean enableTable(String tableName,boolean isAsync){
		boolean result = true;
		Admin admin = null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			TableName name = TableName.valueOf(tableName);
			boolean tableFlag = tableExist(tableName);
			if (tableFlag) {
				if (isAsync){
					admin.enableTableAsync(name);
				}else {
					admin.enableTable(name);
				}
			}
		}catch (Exception e){
			result = false;
			logger.error("啓用hbase表:{}结构发生异常,异常信息是:{}",tableName,e.getMessage());
		}finally {
			close(conn,admin,null,null);
		}
		return result;
	}
	
	
	/**
	 * 禁用表
	 * @param tableName
	 * @return
	 */
	public boolean disableTable(String tableName,boolean isAsync){
		boolean result = true;
		Admin admin = null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			TableName name = TableName.valueOf(tableName);
			boolean tableFlag = tableExist(tableName);
			if (tableFlag) {
				if (isAsync){
					admin.disableTableAsync(name);
				}else {
					admin.disableTable(name);
				}
			}
		}catch (Exception e){
			result = false;
			logger.error("禁用hbase表:{}结构发生异常,异常信息是:{}",tableName,e.getMessage());
		}finally {
			close(conn,admin,null,null);
		}
		return result;
	}
	
	/**
	 * 将列族添加到现有表
	 * @param tableName
	 * @return
	 */
	public boolean addColumnFamily(String tableName,String columnFamily){
		boolean result = true;
		Admin admin = null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			TableName name = TableName.valueOf(tableName);
			boolean tableFlag = tableExist(tableName);
			if (tableFlag) {
				admin.addColumnFamily(name,ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(columnFamily)).build());
			}
		}catch (Exception e){
			result = false;
			logger.error("将列族:{}添加到hbase表:{}结构发生异常,异常信息是:{}",columnFamily,tableName,e.getMessage());
		}finally {
			close(conn,admin,null,null);
		}
		return result;
	}
	
	/**
	 * 修改将列族
	 * @param tableName
	 * @return
	 */
	public boolean updateColumnFamily(String tableName,String columnFamily){
		boolean result = true;
		Admin admin = null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			TableName name = TableName.valueOf(tableName);
			boolean tableFlag = tableExist(tableName);
			if (tableFlag) {
				admin.modifyColumnFamily(name,ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(columnFamily)).build());
			}
		}catch (Exception e){
			result = false;
			logger.error("将列族:{}修改到hbase表:{}结构发生异常,异常信息是:{}",columnFamily,tableName,e.getMessage());
		}finally {
			close(conn,admin,null,null);
		}
		return result;
	}
	
	/**
	 *  创建表结构
	 * @param tableName
	 * @param columnFamily
	 * @return
	 */
	public boolean createTable(String tableName,String columnFamily){
		boolean result = true;
		Admin admin = null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			TableName name = TableName.valueOf(tableName);
			boolean tableFlag = tableExist(tableName);
			if (!tableFlag) {
				TableDescriptorBuilder desc = TableDescriptorBuilder.newBuilder(name);
				//构建列族对象
				ColumnFamilyDescriptor family = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(columnFamily)).build();
				//设置列族
				desc.setColumnFamily(family);
				//创建表
				admin.createTable(desc.build());
			}
		}catch (Exception e){
			result = false;
			logger.error("创建hbase表结构发生异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,admin,null,null);
		}
		return result;
	}
	
	/**
	 * 创建表结构
	 * @param tableName
	 * @param columnFamilies
	 * @return
	 */
	public boolean createTable(String tableName,List<String> columnFamilies){
		boolean result = true;
		Admin admin = null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			TableName name = TableName.valueOf(tableName);
			boolean tableFlag = tableExist(tableName);
			if (!tableFlag) {
				// 创建一个表定义描述对象
				TableDescriptorBuilder desc  = TableDescriptorBuilder.newBuilder(name);
				//构建列族对象
				//为描述器添加表的详细参数
				columnFamilies.forEach(field->{
					// 列族描述对象
					desc.setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(field)).build());
				});
				
				//创建表
				admin.createTable(desc.build());
			}
		}catch (Exception e){
			result = false;
			logger.error("创建hbase表结构发生异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,admin,null,null);
		}
		return result;
	}
	
	/**
	 * 预分区创建表
	 * @param tableName 表名
	 * @param columnFamily 列族名的集合
	 * @param splitKeys 预分期region
	 * @return 是否创建成功
	 */
	public boolean createTableBySplitKeys(String tableName, List<String> columnFamily, byte[][] splitKeys) {
		Admin admin = null;
		boolean result = true;
		Connection conn = getConnection();
		try {
			if (StringUtils.isBlank(tableName) || columnFamily == null|| columnFamily.size() == 0) {
				logger.info("传入的表名为空,列族名的集合也为空,不符合要求");
				result=false;
				return result;
			}
			admin = conn.getAdmin();
			boolean tableFlag = tableExist(tableName);
			if (!tableFlag) {
				List<ColumnFamilyDescriptor> familyDescriptors = new ArrayList<>(columnFamily.size());
				columnFamily.forEach(cf -> {
					familyDescriptors.add(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(cf)).build());
				});
				//为描述器添加表的详细参数
				TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName)).setColumnFamilies(familyDescriptors).build();
				//指定splitkeys
				admin.createTable(tableDescriptor,splitKeys);
			}
		} catch (IOException e) {
			result=false;
			logger.error("预分区创建表出现了异常,异常信息是:{}",e.getMessage());
		}finally{
			close(conn,admin,null,null);
		}
		return result;
	}
	
	/**
	 * 刪除hbase的表
	 * @param tableName
	 */
	public boolean deleteTable(String tableName){
		boolean result = true;
		Admin admin = null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			admin.disableTable(TableName.valueOf(tableName));
			admin.deleteTable(TableName.valueOf(tableName));
		}catch (Exception e){
			result = false;
			logger.error("删除hbase表结构发生异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,admin,null,null);
		}
		return result;
	}
	
	/**
	 * 刪除hbase的表
	 * @param tableName
	 */
	public boolean deleteTable(String tableName,boolean isAsync){
		boolean result = true;
		Admin admin = null;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			admin.disableTable(TableName.valueOf(tableName));
			if(isAsync){
				admin.deleteTableAsync(TableName.valueOf(tableName));
			}else{
				admin.deleteTable(TableName.valueOf(tableName));
			}
		}catch (Exception e){
			result = false;
			logger.error("删除hbase表结构发生异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,admin,null,null);
		}
		return result;
	}
	
	/**
	 * 通过row删除
	 * @param tableName
	 * @param rowKey
	 */
	public boolean deleteByRowKey(String tableName,String rowKey){
		boolean result = true;
		Table table =null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			Delete del = new Delete(Bytes.toBytes(rowKey));
			table.delete(del);
		}catch (Exception e){
			result = false;
			logger.error("通过row上次信息发生异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return result;
	}
	
	/**
	 * 删除某条记录
	 * @param tableName  表名
	 * @param rowKey    rowKey
	 * @param columnFamily    列族名
	 * @param columkey  列名
	 */
	public boolean deleteData(String tableName,String rowKey,String columnFamily,String columkey){
		Table table=null;
		boolean result = true;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			//创建delete对象
			Delete deletData= new Delete(Bytes.toBytes(rowKey));
			//将要删除的数据的准确坐标添加到对象中
			deletData.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columkey));
			//删除表中数据
			table.delete(deletData);
		} catch (Exception e) {
			result = false;
			logger.error("通过rowKey,family,columnKey删除信息发生异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return result;
	}
	
	/**
	 * 根据columnFamily删除指定的列族
	 * @param tableName 表名
	 * @param columnFamily 列族
	 * @return boolean
	 */
	public boolean deleteColumnFamily(String tableName, String columnFamily,boolean isAysnc){
		Admin admin=null;
		boolean result = true;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			if(isAysnc){
				admin.deleteColumnFamilyAsync(TableName.valueOf(tableName), Bytes.toBytes(columnFamily));
			}else{
				admin.deleteColumnFamily(TableName.valueOf(tableName), Bytes.toBytes(columnFamily));
			}
		} catch (Exception e) {
			result = false;
			logger.error("通过columnFamily删除信息发生异常,异常信息是:{}",e.getMessage());
		}finally{
			close(conn,admin,null,null);
		}
		return result;
	}
	
	/**
	 * 通过rows 删除多条.
	 * @param tableName
	 * @param rowKeys
	 */
	public boolean deleteMultiRows(String tableName,String[] rowKeys){
		boolean result = true;
		Table table = null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			List<Delete> delList = new ArrayList<>();
			for (String rowKey : rowKeys ) {
				Delete del = new Delete(Bytes.toBytes(rowKey));
				delList.add(del);
			}
			table.delete(delList);
		}catch (Exception e){
			result = false;
			logger.error("通过row上次信息发生异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return result;
	}
	
	
	/**
	 * 截断表,让表全线下线
	 * @param tableName
	 * @param preserveSplits
	 */
	public boolean truncateTable(String tableName,boolean preserveSplits){
		Admin admin=null;
		boolean result = true;
		Connection conn = getConnection();
		try {
			admin = conn.getAdmin();
			if (preserveSplits){
				admin.truncateTable(TableName.valueOf(tableName),preserveSplits);
			}else{
				admin.truncateTableAsync(TableName.valueOf(tableName),preserveSplits);
			}
		} catch (Exception e) {
			result = false;
			logger.error("通过columnFamily删除信息发生异常,异常信息是:{}",e.getMessage());
		}finally{
			close(conn,admin,null,null);
		}
		return result;
	}
	
	/**
	 * 获取所有result的信息
	 * @param tableName
	 * @return
	 */
	public List<HbaseBean> getAllResult(String tableName){
		ArrayList<HbaseBean> dataList = new ArrayList<>();
		Table table = null;
		ResultScanner scanner =null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			Scan scan = new Scan();
			scanner = table.getScanner(scan);
			for (Result res : scanner) {
				for (Cell cell : res.listCells() ) {
					data4Bean(tableName,dataList,cell);
				}
			}
			return dataList;
		}catch (Exception e){
			logger.error("获得hbase所有result的信息异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,scanner,table);
		}
		return dataList;
	}
	

	
	/***
	 * 遍历查询指定表中的所有数据
	 * @param tableName
	 * @return
	 */
	public List<HbaseBean> getResultScanner(String tableName){
		Scan scan = new Scan();
		return queryData(tableName,scan);
	}
	
	/**
	 * 根据startRowKey和stopRowKey遍历查询指定表中的所有数据
	 * @param tableName 表名
	 * @param startRowKey 起始rowKey
	 * @param stopRowKey 结束rowKey
	 */
	public List<HbaseBean> getResultScanner(String tableName, String startRowKey, String stopRowKey){
		Scan scan = new Scan();
		if(StringUtils.isNotBlank(startRowKey) && StringUtils.isNotBlank(stopRowKey)){
			scan.withStartRow(Bytes.toBytes(startRowKey));
			scan.withStopRow(Bytes.toBytes(stopRowKey));
		}
		return queryData(tableName,scan);
	}
	
	/**
	 * 通过行前缀过滤器查询数据
	 * @param tableName 表名
	 * @param prefix 以prefix开始的行键
	 */
	public List<HbaseBean> getResultScannerPrefixFilter(String tableName, String prefix){
		Scan scan = new Scan();
		if(StringUtils.isNotBlank(prefix)){
			Filter filter = new PrefixFilter(Bytes.toBytes(prefix));
			scan.setFilter(filter);
		}
		return queryData(tableName,scan);
	}
	
	/**
	 * 通过列前缀过滤器查询数据
	 * @param tableName 表名
	 * @param prefix 以prefix开始的列名
	 */
	public List<HbaseBean> getResultScannerColumnPrefixFilter(String tableName, String prefix){
		Scan scan = new Scan();
		if(StringUtils.isNotBlank(prefix)){
			Filter filter = new ColumnPrefixFilter(Bytes.toBytes(prefix));
			scan.setFilter(filter);
		}
		return queryData(tableName,scan);
	}
	
	/**
	 * 查询行键中包含特定字符的数据
	 * @param tableName 表名
	 * @param keyWord 包含指定关键词的行键
	 */
	public List<HbaseBean> getResultScannerRowFilter(String tableName, CompareOperator compareOperator, String keyWord){
		Scan scan = new Scan();
		if(StringUtils.isNotBlank(keyWord)){
			Filter filter = new RowFilter(compareOperator,new SubstringComparator(keyWord));
			scan.setFilter(filter);
		}
		return queryData(tableName,scan);
	}
	
	/**
	 * 查询列名中包含特定字符的数据
	 * @author zifangsky
	 * @param tableName 表名
	 * @param keyWord 包含指定关键词的列名
	 */
	public List<HbaseBean> getResultScannerQualifierFilter(String tableName, CompareOperator compareOperator, String keyWord){
		Scan scan = new Scan();
		if(StringUtils.isNotBlank(keyWord)){
			Filter filter = new QualifierFilter(compareOperator,new SubstringComparator(keyWord));
			scan.setFilter(filter);
		}
		return queryData(tableName,scan);
	}
	
	/**
	 * 通过表名以及过滤条件查询数据
	 * @param tableName 表名
	 * @param scan 过滤条件
	 */
	private List<HbaseBean> queryData(String tableName,Scan scan){
		List<HbaseBean> beanList = new ArrayList<>();
		ResultScanner scanner = null;
		// 获取表
		Table table= null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			scanner = table.getScanner(scan);
			for (Result res : scanner) {
				//每一行数据
				for (Cell cell : res.listCells()) {
					data4Bean(tableName,beanList,cell);
				}
			}
		}catch (IOException e) {
			logger.error("遍历查询指定表中的所有数据发生异常,异常信息是:{}",e.getMessage());
		}finally{
			close(conn,null,scanner,table);
		}
		return beanList;
	}
	
	/**
	 * 更新数据 为表的某个单元格赋值
	 * @param tableName  表名
	 * @param rowKey    rowKey
	 * @param family    列族名
	 * @param columkey  列名
	 * @param updateData 列值
	 */
	public boolean updateData(String tableName,String rowKey,String family,String columkey,String updateData){
		boolean result = true;
		Table table=null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			//hbase中更新数据同样采用put方法，在相同的位置put数据，则在查询时只会返回时间戳较新的数据
			//且在文件合并时会将时间戳较旧的数据舍弃
			Put put = new Put(Bytes.toBytes(rowKey));
			//将新数据添加到put中
			put.addColumn(Bytes.toBytes(family), Bytes.toBytes(columkey),Bytes.toBytes(updateData));
			//将put写入HBase
			table.put(put);
		} catch (Exception e) {
			result = false;
			logger.error("修改为表的某个单元格赋值发生异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return result;
	}
	
	/**
	 *  插入数据
	 * @param tableName
	 * @param rowKey
	 * @param columnFamily
	 * @param column
	 * @param value
	 */
	public boolean insertOne(String tableName, String rowKey,String columnFamily, String column, String value){
		boolean result = true;
		Table table = null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			Put put = new Put(Bytes.toBytes(rowKey));
			put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column),Bytes.toBytes(value));
			table.put(put);
			table.close();
		}catch (Exception e){
			result = false;
			logger.error("放入数据到hbase发生了异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return result;
	}
	
	/**
	 * 往表中添加单条数据
	 * @param tableName 表明
	 * @param rowKey  指key
	 * @param family 列族名
	 * @param map    数据源
	 * @return
	 */
	public boolean insertOne(String tableName,String rowKey,String family,Map<String, String> map){
		boolean result = true;
		Table table=null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			//实例化put对象，传入行键
			Put put =new Put(Bytes.toBytes(rowKey));
			//调用addcolum方法，向i簇中添加字段
			Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
			while (it.hasNext()){
				Map.Entry<String, String> entry = it.next();
				put.addColumn(Bytes.toBytes(family),Bytes.toBytes(entry.getKey()),Bytes.toBytes(entry.getValue()));
			}
			table.put(put);
		} catch (Exception e) {
			result = false;
			logger.error("放入数据到hbase发生了异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return result;
	}
	
	/**
	 * 往表中添加多数据
	 * @param tableName HbaseTable
	 * @param rowKey rowKey
	 * @param tableName 表名
	 * @param columnFamily 列族名
	 * @param columns 列名数组
	 * @param values 列值得数组
	 * @return
	 */
	public boolean insertOrUpdate(String tableName,String rowKey,String columnFamily, String[] columns, String[] values){
		boolean result = true;
		Table table = null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			//实例化put对象，传入行键
			Put put =new Put(Bytes.toBytes(rowKey));
			if(columns != null && values != null && columns.length == values.length){
				for(int i=0;i<columns.length;i++){
					if(columns[i] != null && values[i] != null){
						put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columns[i]), Bytes.toBytes(values[i]));
					}
				}
			}
			table.put(put);
		} catch (Exception e) {
			result = false;
			logger.error("放入数据到hbase发生了异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return result;
	}
	
	
	/**
	 * 往表中添加多数据
	 * @param tableName HbaseTable
	 * @param rowKey rowKey
	 * @param tableName 表名
	 * @param familyName 列族名
	 * @param columns 列名数组
	 * @param values 列值得数组
	 * @return
	 */
	public boolean insertBatchMore(String tableName,String rowKey,String familyName, String[] columns, String[] values){
		boolean result = true;
		Table table = null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			//实例化put对象，传入行键
			Put put =new Put(Bytes.toBytes(rowKey));
			if(columns != null && values != null && columns.length == values.length){
				for(int i=0;i<columns.length;i++){
					if(columns[i] != null && values[i] != null){
						put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columns[i]), Bytes.toBytes(values[i]));
					}
				}
			}
			ArrayList<Row> list = new ArrayList<>();
			list.add(put);
			Object[] array = new Object[list.size()];
			table.batch(list,array);
		} catch (Exception e) {
			result = false;
			logger.error("放入数据到hbase发生了异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return result;
	}
	
	/**
	 * 根据tableName和rowKey精确查询一行的数据
	 * @param tableName
	 * @param rowKey
	 */
	public List<HbaseBean> getRowData(String tableName, String rowKey){
		List<HbaseBean> dataList = new ArrayList<>();
		Table table = null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			Get get = new Get(Bytes.toBytes(rowKey));
			Result rs = table.get(get);
			for(Cell cell : rs.listCells()){
				data4Bean(tableName,dataList, cell);
			}
		}catch (Exception e){
			logger.error("通过rowkey查找数据异常了,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return dataList;
	}
	
	/**
	 * 获取指定行的数据
	 * @param tableName 表名
	 * @param rowKey 行键key
	 * @param columnFamily 列族
	 * @param column 列名
	 */
	public String getValData(String tableName,String rowKey,String columnFamily,String column){
		String value = null;
		Table table = null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			Get get = new Get(Bytes.toBytes(rowKey));
			Result result = table.get(get);
			byte[] rb = result.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
			value = new String(rb, StandardCharsets.UTF_8);
		}catch (Exception e){
			logger.error("获取hbase行数据发生异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return value;
	}
	
	/**
	 * 根据tableName、rowKey、familyName、column查询指定单元格的数据
	 * @param tableName 表名
	 * @param rowKey rowKey
	 * @param columnFamily 列族名
	 * @param columnName 列名
	 */
	public String getColumnValue(String tableName, String rowKey, String columnFamily, String columnName){
		String value = null;
		// 获取表
		Table table= null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			Get get = new Get(Bytes.toBytes(rowKey));
			Result result = table.get(get);
			if (result != null && !result.isEmpty()) {
				Cell cell = result.getColumnLatestCell(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName));
				if(cell != null){
					value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
				}
			}
		} catch (IOException e) {
			logger.error("获取hbase行数据发生异常,异常信息是:{}",e.getMessage());
		}finally{
			close(conn,null, null, table);
		}
		return value;
	}
	
	/**
	 * 获取指定表的所有数据
	 * @param tableName 表名
	 * @throws IOException
	 */
	public List<HbaseBean> getTableData(String tableName){
		List<HbaseBean> dataList = new ArrayList<>();
		Table table = null;
		ResultScanner scanner = null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			Scan scan = new Scan();
			scanner = table.getScanner(scan);
			for (Result result : scanner) {
				List<Cell> cells= result.listCells();
				for (Cell cell : cells) {
					data4Bean(tableName,dataList, cell);
				}
			}
		}catch (Exception e){
			logger.error("获取指定表的所有数据异常了,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,scanner,table);
		}
		return dataList;
	}
	
	/**
	 * 通过列簇获取下面所有的列的数据
	 * @param tableName
	 * @param family
	 */
	public List<HbaseBean> getDataByFamilyColumn(String tableName,String family,String column){
		List<HbaseBean> dataList = new ArrayList<>();
		Table table = null;
		ResultScanner scanner = null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			Scan scan = new Scan();
			if (!column.isEmpty()) {
				scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
			}else {
				scan.addFamily(Bytes.toBytes(family));
			}
			scanner = table.getScanner(scan);
			for (Result result : scanner) {
				List<Cell> cells= result.listCells();
				for (Cell cell : cells) {
					data4Bean(tableName,dataList, cell);
				}
			}
		}catch (Exception e){
			logger.error("通过family查找数据异常了,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,scanner,table);
		}
		return dataList;
	}
	
	/**
	 * 根据tableName、rowKey、familyName、column查询指定单元格的数据
	 * @param tableName 表名
	 * @param rowKey rowKey
	 * @param familyName 列族名
	 * @param columnName 列名
	 */
	public List<HbaseBean> getDataByFamilyColumn(String tableName, String rowKey, String familyName, String columnName){
		List<HbaseBean> dataList = new ArrayList<>();
		// 获取表
		Table table= null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			Get get = new Get(Bytes.toBytes(rowKey));
			Result result = table.get(get);
			if (result != null && !result.isEmpty()) {
				Cell cell = result.getColumnLatestCell(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
				data4Bean(tableName,dataList,cell);
			}
		} catch (IOException e) {
			logger.error("通过family,column查找数据异常了,异常信息是:{}",e.getMessage());
		}finally{
			close(conn,null,null,table);
		}
		return dataList;
	}
	
	/**
	 * 根据tableName、rowKey、familyName、column查询指定单元格多个版本的数据
	 * @param tableName 表名
	 * @param rowKey rowKey
	 * @param familyName 列族名
	 * @param columnName 列名
	 * @param versions 需要查询的版本数
	 */
	public List<HbaseBean> getColumnValuesByVersion(String tableName, String rowKey, String familyName, String columnName,int versions) {
		//返回数据
		List<HbaseBean> dataList = new ArrayList<>();
		// 获取表
		Table table= null;
		Connection conn = getConnection();
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			Get get = new Get(Bytes.toBytes(rowKey));
			get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
			//读取多少个版本
			get.readVersions(versions);
			Result hTableResult = table.get(get);
			if (hTableResult != null && !hTableResult.isEmpty()) {
				for (Cell cell : hTableResult.listCells()) {
					data4Bean(tableName,dataList,cell);
				}
			}
		} catch (IOException e) {
			logger.error("通过family,column,version查找数据异常了,异常信息是:{}",e.getMessage());
		}finally{
			close(conn,null,null,table);
		}
		return dataList;
	}
	
	/**
	 * 处理对象集合
	 * @param dataList
	 * @param cell
	 */
	private void data4Bean(String tableName,List<HbaseBean> dataList, Cell cell) {
		String rowKey = Bytes.toString(CellUtil.cloneRow(cell));
		String columnFamily = Bytes.toString(CellUtil.cloneFamily(cell));
		//Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
		String columnKey = Bytes.toString(CellUtil.cloneQualifier(cell));
		//Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
		String columValue = Bytes.toString(CellUtil.cloneValue(cell));
		long timestamp = cell.getTimestamp();
		HbaseBean bean = new HbaseBean();
		bean.setTableName(tableName);
		bean.setRowKey(rowKey);
		bean.setColumnFamily(columnFamily);
		bean.setColumnKey(columnKey);
		bean.setColumnValue(columValue);
		bean.setDateTime(getDate(timestamp));
		bean.setTimestamp(timestamp);
		dataList.add(bean);
	}
	
	/**
	 * 获取时间
	 * @param timestamp
	 * @return
	 */
	private String getDate(Long timestamp){
		DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),ZoneId.systemDefault()));
	}
	
	/**
	 * 自定义获取分区splitKeys
	 * @param keys
	 * @return
	 */
	public byte[][] getSplitKeys(String[] keys){
		if(keys==null){
			//默认为10个分区
			keys = new String[] {  "1|", "2|", "3|", "4|", "5|", "6|", "7|", "8|", "9|" };
		}
		byte[][] splitKeys = new byte[keys.length][];
		//升序排序
		TreeSet<byte[]> rows = new TreeSet<byte[]>(Bytes.BYTES_COMPARATOR);
		for(String key : keys){
			rows.add(Bytes.toBytes(key));
		}
		Iterator<byte[]> rowKeyIter = rows.iterator();
		int i=0;
		while (rowKeyIter.hasNext()) {
			byte[] tempRow = rowKeyIter.next();
			rowKeyIter.remove();
			splitKeys[i] = tempRow;
			i++;
		}
		return splitKeys;
	}
	
	/**
	 * 按startKey和endKey，分区数获取分区
	 * @param startKey
	 * @param endKey
	 * @param numRegions
	 * @return
	 */
	public byte[][] getHexSplits(String startKey, String endKey, int numRegions) {
		byte[][] splits = new byte[numRegions-1][];
		BigInteger lowestKey = new BigInteger(startKey, 16);
		BigInteger highestKey = new BigInteger(endKey, 16);
		BigInteger range = highestKey.subtract(lowestKey);
		BigInteger regionIncrement = range.divide(BigInteger.valueOf(numRegions));
		lowestKey = lowestKey.add(regionIncrement);
		for(int i=0; i < numRegions-1;i++) {
			BigInteger key = lowestKey.add(regionIncrement.multiply(BigInteger.valueOf(i)));
			byte[] b = String.format("%016x", key).getBytes();
			splits[i] = b;
		}
		return splits;
	}
	
	/**
	 * 关闭流计算
	 * @param admin
	 * @param scanner
	 * @param table
	 */
	private void close(Connection connection,Admin admin, ResultScanner scanner, Table table){
		if(connection != null){
			try {
				connection.close();
			} catch (IOException e) {
				logger.error("关闭connection信息失败了!");
			}
		}
		if(admin != null){
			try {
				admin.close();
			} catch (IOException e) {
				logger.error("关闭admin信息失败了!");
			}
		}
		if(scanner != null){
			scanner.close();
		}
		if(table != null){
			try {
				table.close();
			} catch (IOException e) {
				logger.error("关闭table信息失败了!");
			}
		}
	}
	
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
	public List<HbaseBean> selectTableDataByFilter(String tableName,String columnFamily,List<String> queryParam,String regex,boolean bool){
		Scan scan = new Scan();
		FilterList filterList = queryFilter(columnFamily, queryParam, regex, bool);
		scan.setFilter(filterList);
		return queryData(tableName,scan);
	}
	
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
	public List<HbaseBean> selectColumnValueByFilter(String tableName,String columnFamily,List<String> queryParam,String regex,String column,boolean bool){
		Scan scan = new Scan();
		FilterList filterList = queryFilter(columnFamily, queryParam, regex, bool);
		scan.setFilter(filterList);
		scan.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(column));
		scan.setFilter(filterList);
		return queryData(tableName,scan);
	}
	
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
	public List<HbaseBean> selectTableDataByFilterPage(String tableName,String columnFamily,List<String> queryParam,String regex,boolean bool,int pageSize,String lastRow){
		Scan scan = new Scan();
		FilterList filterList = queryFilter(columnFamily, queryParam, regex, bool);
		FilterList pageFilterList = handlePageFilter(scan, pageSize, lastRow);
		pageFilterList.addFilter(filterList);
		scan.setFilter(pageFilterList);
		return queryData(tableName,scan);
	}
	/**
	 * 分页查询表中所有数据信息
	 * @param tableName     表名
	 * @param pageSize      每页数量
	 * @param lastRow       当前页的最后一行
	 * @return
	 */
	public  List<HbaseBean> selectTableDataByPage(String tableName,int pageSize,String lastRow){
		Scan scan = new Scan();
		FilterList pageFilterList = handlePageFilter(scan, pageSize, lastRow);
		scan.setFilter(pageFilterList);
		return queryData(tableName,scan);
	}
	
	/**
	 * 通用方法 -> 处理分页数据
	 * @param scan          过滤的数据
	 * @param pageSize      每页显示的数量
	 * @param lastRowKey    当前页的最后一行（rowKey）
	 * @return
	 */
	private FilterList handlePageFilter(Scan scan, int pageSize, String lastRowKey){
		Filter pageFilter = new PageFilter(pageSize);
		FilterList pageFilterList = new FilterList();
		pageFilterList.addFilter(pageFilter);
		if(!StringUtils.isEmpty(lastRowKey)){
			byte[] startRow = Bytes.add(Bytes.toBytes(lastRowKey), POSTFIX);
			scan.setStartRow(startRow);
		}
		return pageFilterList;
	}
	
	/**
	 * 通用方法 -> 处理查询条件
	 * @param columnFamily   列簇
	 * @param queryParam     过滤列集合   ("topicFileId,6282")=>("列,值")
	 * @param regex          分隔字符
	 * @param bool           查询方式：true:and ; false:or
	 * @return
	 */
	private FilterList queryFilter(String columnFamily,List<String> queryParam,String regex,boolean bool){
		FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		if(!bool){
			filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
		}
		for(String param: queryParam){
			String[] queryArray = param.split(regex);
			SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes(columnFamily), Bytes.toBytes(queryArray[0]), CompareOperator.EQUAL,Bytes.toBytes(queryArray[1]));
			singleColumnValueFilter.setFilterIfMissing(true);
			filterList.addFilter(singleColumnValueFilter);
		}
		return filterList;
	}
	
	/**
	 * 通用方法 -> 统计
	 * @param table
	 * @param scan
	 * @return
	 */
	private int queryDataCount(Table table,Scan scan){
		scan.setFilter(new FirstKeyOnlyFilter());
		ResultScanner resultScanner = null;
		int rowCount = 0;
		try {
			resultScanner = table.getScanner(scan);
			for(Result result : resultScanner){
				rowCount += result.size();
			}
			logger.info("统计全表数据总数：表名：{0}，查询结果：{1}",Bytes.toString(table.getName().getName()),rowCount);
			return rowCount;
		} catch (Exception e) {
			logger.error("查询指定表中数据信息：表名：{0}，错误信息：{1}",Bytes.toString(table.getName().getName()),e.getMessage());
			return rowCount;
		}finally {
			close(null,null,resultScanner,table);
		}
	}
}
