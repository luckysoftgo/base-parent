package com.application.base.operapi.tool.hbase.core.impl;

import com.application.base.operapi.tool.hbase.annotation.HbaseField;
import com.application.base.operapi.tool.hbase.config.HbaseToolConfig;
import com.application.base.operapi.tool.hbase.core.HbaseInterface;
import com.application.base.operapi.tool.hbase.util.AnnoatationsUtil;
import com.application.base.operapi.tool.hbase.util.HbaseAnnontationUtil;
import com.application.base.operapi.tool.hbase.util.HbaseCommonUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : 孤狼
 * @NAME: BasicHbaseClient
 * @DESC: 实现hbase的接口
 **/
public class BasicHbaseClient implements HbaseInterface {
	
	private static Logger logger = LoggerFactory.getLogger(BasicHbaseClient.class);
	
	/**
	 * 创建工厂实例
	 */
	private HbaseToolConfig hbaseFactory;
	
	/**
	 * 构造方法
	 */
	public BasicHbaseClient(HbaseToolConfig hbaseFactory){
		this.hbaseFactory = hbaseFactory;
	}
	
	/**
	 * 检测是否有相应的表
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean hasTable(Class clazz){
		Connection connection = null;
		Admin admin = null;
		boolean result = true;
		try {
			String tableNameStr = HbaseAnnontationUtil.getTableName(clazz);
			connection = hbaseFactory.getConnection();
			admin = connection.getAdmin();
			TableName tableName = TableName.valueOf(tableNameStr);
			result = admin.tableExists(tableName);
		}catch (Exception e){
			result = false;
			logger.error("判断hbase的表是否存在异常,异常信息是:{}",e.getMessage());
		}finally {
			close(connection,admin,null,null);
		}
		return result;
	}
	
	/**
	 * 创建表
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean createTable(Class clazz){
		String tableName = HbaseAnnontationUtil.getTableName(clazz);
		String columnFamily = HbaseAnnontationUtil.getColumnFamily(clazz);
		boolean result = true;
		Connection conn = hbaseFactory.getConnection();
		Admin admin = null;
		try {
			admin = conn.getAdmin();
			TableName name = TableName.valueOf(tableName);
			boolean tableFlag = hasTable(clazz);
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
	 * 删除表
	 * @param clazz
	 * @return
	 */
	@Override
	public boolean deleteTable(Class clazz){
		String tableName = HbaseAnnontationUtil.getTableName(clazz);
		Connection conn = hbaseFactory.getConnection();
		Admin admin = null;
		boolean result = true;
		try {
			admin = conn.getAdmin();
			TableName name = TableName.valueOf(tableName);
			if (hasTable(clazz)){
				admin.disableTable(name);
				admin.deleteTable(name);
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
	 * 添加数据
	 * @param t
	 * @return
	 */
	@Override
	public boolean put(Object t){
		String tableNameStr = HbaseAnnontationUtil.getTableName(t.getClass());
		Connection conn = hbaseFactory.getConnection();
		Table table = null;
		boolean result = true;
		try {
			TableName name = TableName.valueOf(tableNameStr);
			table = conn.getTable(name);
			Object keyValue = HbaseAnnontationUtil.getRowKeyValue(t);
			Put put = new Put(HbaseCommonUtil.convertToBytes(keyValue));
			table.put(AddCell(put, t));
		}catch (Exception e){
			result = false;
			logger.error("放入数据到hbase发生了异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return result;
	}
	
	/***
	 * 批量添加数据
	 * @param clazz
	 * @param list
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> boolean putBatch(Class<T> clazz, List<T> list){
		String tableNameStr = HbaseAnnontationUtil.getTableName(clazz);
		TableName name = TableName.valueOf(tableNameStr);
		Connection conn = hbaseFactory.getConnection();
		Table table = null;
		boolean result = true;
		try {
			table = conn.getTable(name);
			List<Row> batch = new LinkedList<>();
			for (T t : list) {
				Object keyValue = HbaseAnnontationUtil.getRowKeyValue(t);
				Put put = new Put(HbaseCommonUtil.convertToBytes(keyValue));
				put = AddCell(put, t);
				batch.add(put);
			}
			//用于存放批量操作结果
			Object[] results = new Object[list.size()];
			table.batch(batch, results);
		}catch (Exception e){
			result = false;
			logger.error("放入数据到hbase发生了异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return result;
	}
	
	/***
	 * 根据rowkey获得具体的model对象
	 * @param rowkey
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> T get(Object rowkey, Class<T> clazz){
		//查询所有列
		List<String> columns = AnnoatationsUtil.getFieldsBySpecAnnoatationWithString(clazz, HbaseField.class);
		return get(rowkey, clazz, columns);
	}
	
	/**
	 * 根据rowkey获得指定列，并将结果转换为具体的model对象
	 * @param rowkey
	 * @param clazz
	 * @param columns
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> T get(Object rowkey, Class<T> clazz, List<String> columns){
		String tableNameStr = HbaseAnnontationUtil.getTableName(clazz);
		String columnFamily= HbaseAnnontationUtil.getColumnFamily(clazz);
		TableName name = TableName.valueOf(tableNameStr);
		Connection conn = hbaseFactory.getConnection();
		Table table = null;
		try {
			table = conn.getTable(name);
			Get get = new Get(HbaseCommonUtil.convertToBytes(rowkey));
			for (String column : columns) {
				get.addColumn(columnFamily.getBytes(), column.getBytes());
			}
			Result res=table.get(get);
			if (!res.isEmpty()){
				return ResultToModel(res, clazz);
			}
		}catch (Exception e){
			logger.error("获得一个hbase对象出现了异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return null;
	}
	
	/**
	 * 根据起始位置扫描表，并返回model的list集合
	 * @param startrowkey
	 * @param stoprowkey
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> List scan(Object startrowkey, Object stoprowkey, Class<T> clazz){
		//扫描行并获取所有列
		return scan(startrowkey, stoprowkey, clazz);
	}
	
	/**
	 * 根据起始位置扫描表，获取指定列，并返回model的list集合
	 * @param startrowkey
	 * @param stoprowkey
	 * @param clazz
	 * @param columns
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> List<T> scan(Object startrowkey, Object stoprowkey, Class<T> clazz, List<String> columns){
		String tableNameStr = HbaseAnnontationUtil.getTableName(clazz);
		String columnFamily= HbaseAnnontationUtil.getColumnFamily(clazz);
		TableName name = TableName.valueOf(tableNameStr);
		Connection conn = hbaseFactory.getConnection();
		Table table = null;
		try {
			table = conn.getTable(name);
			Scan scan = new Scan();
			for (String column : columns) {
				scan.addColumn(columnFamily.getBytes(), column.getBytes());
			}
			ResultScanner scanner = table.getScanner(scan);
			List<T> list = new LinkedList<T>();
			for (Result result : scanner) {
				T model = ResultToModel(result, clazz);
				list.add(model);
			}
			return list;
		}catch (Exception e){
			logger.error("获得多个hbase对象出现了异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return null;
	}
	
	/***
	 * 根据rowkey删除指定行
	 * @param rowkey
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> boolean delete(Object rowkey, Class<T> clazz){
		boolean result =true;
		if (!hasRow(rowkey, clazz)) {
			result= false;
		}
		String tableNameStr = HbaseAnnontationUtil.getTableName(clazz);
		Connection conn = hbaseFactory.getConnection();
		TableName name = TableName.valueOf(tableNameStr);
		Table table = null;
		try {
			table = conn.getTable(name);
			Delete delete = new Delete(HbaseCommonUtil.convertToBytes(rowkey));
			table.delete(delete);
		}catch (Exception e){
			result= false;
			logger.error("删除一个行数据异常,异常信息是:{}",e.getMessage());
		} finally {
			close(conn,null,null,table);
		}
		return result;
	}
	
	/***
	 * 根据rowkey（通过设置list对象中的HbaseRowkey值确定）批量删除指定行
	 * @param clazz
	 * @param list
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> boolean deleteBatch(Class<T> clazz, List<T> list){
		boolean result =true;
		String tableNameStr = HbaseAnnontationUtil.getTableName(clazz);
		Connection conn = hbaseFactory.getConnection();
		TableName name = TableName.valueOf(tableNameStr);
		Table table = null;
		try {
			table = conn.getTable(name);
			List<Row> batch = new LinkedList<Row>();
			for (T t : list) {
				Object keyValue = HbaseAnnontationUtil.getRowKeyValue(t);
				if (!hasRow(keyValue, clazz)) {
					continue;
				}
				Delete delete = new Delete(HbaseCommonUtil.convertToBytes(keyValue));
				batch.add(delete);
			}
			Object[] results = new Object[list.size()];
			table.batch(batch, results);
		}catch (Exception e){
			result= false;
			logger.error("删除一个行数据异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return result;
	}
	
	/***
	 * 根据rowkey判断是否含有某条数据
	 * @param rowkey
	 * @param clazz
	 * @return
	 */
	@Override
	public boolean hasRow(Object rowkey, Class clazz){
		boolean result =true;
		String tableNameStr = HbaseAnnontationUtil.getTableName(clazz);
		Connection conn = hbaseFactory.getConnection();
		TableName name = TableName.valueOf(tableNameStr);
		Table table = null;
		try {
			table = conn.getTable(name);
			Get get = new Get(HbaseCommonUtil.convertToBytes(rowkey));
			Result res = table.get(get);
			if (res.isEmpty()) {
				result=false;
			} else {
				result=true;
			}
		}catch (Exception e){
			result=false;
			logger.error("判断是否有某一行出错了异常,异常信息是:{}",e.getMessage());
		}finally {
			close(conn,null,null,table);
		}
		return result;
	}
	
	/***
	 * 解析model并向put对象添加字段值
	 * @param put
	 * @param model
	 * @return
	 */
	private static Put AddCell(Put put, Object model) {
		String family = HbaseAnnontationUtil.getColumnFamily(model.getClass());
		/**field字段为基本类型和集合类型的处理*/
		List<Field> fields = AnnoatationsUtil.getFieldsBySpecAnnoatation(model.getClass(), HbaseField.class);
		for (Field filed : fields) {
			Object value = HbaseCommonUtil.getProperty(model, filed.getName());
			if(value == null){
				continue;
			}
			String type = filed.getGenericType().toString();
			//List转换
			//字段类型为List
			if(type.startsWith("java.util.List")){
				String newValue = "";
				List list = (List)value;
				for(Object o:list){
					String oo = HbaseCommonUtil.filterSpecChar(o.toString());
					newValue += oo + HbaseCommonUtil.COLLECTION_SPLIT_CH;
				}
				if(list.size()==0){
					newValue = "";
				}else{
					newValue = newValue.substring(0,newValue.length()-1);
				}
				value = newValue;
			}
			//Set转换
			//字段类型为Set
			if(type.startsWith("java.util.Set")){
				String newValue = "";
				Set set = (Set)value;
				for(Object o:set){
					String oo = HbaseCommonUtil.filterSpecChar(o.toString());
					newValue += oo + HbaseCommonUtil.COLLECTION_SPLIT_CH;
				}
				if(set.size()==0){
					newValue = "";
				}else{
					newValue = newValue.substring(0,newValue.length()-1);
				}
				value = newValue;
			}
			//Map转换
			//字段类型为Map
			if(type.startsWith("java.util.Map")){
				String newValue = "";
				Map map = (Map)value;
				for(Object key:map.keySet()){
					String keyStr = HbaseCommonUtil.filterSpecChar(key.toString());
					String valueStr = HbaseCommonUtil.filterSpecChar(map.get(key).toString());
					newValue += keyStr + HbaseCommonUtil.MAP_KV_CH + valueStr + HbaseCommonUtil.COLLECTION_SPLIT_CH;
				}
				if(map.size()==0){
					newValue = "";
				}else{
					newValue = newValue.substring(0,newValue.length()-1);
				}
				value = newValue;
			}
			put.addColumn(family.getBytes(), filed.getName().getBytes(), HbaseCommonUtil.convertToBytes(value));
		}
		return put;
	}
	
	
	/**
	 * 将Hbase返回的Result对象解析为具体的model对象
	 * @param result
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	private static <T> T ResultToModel(Result result, Class<T> clazz) {
		String family = HbaseAnnontationUtil.getColumnFamily(clazz);
		T model = HbaseCommonUtil.getInstanceByClass(clazz);
		List<Field> fields = AnnoatationsUtil.getFieldsBySpecAnnoatation(model.getClass(), HbaseField.class);
		for (Field field : fields) {
			String fieldName = field.getName();
			byte[] b = result.getValue(family.getBytes(), fieldName.getBytes());
			Object value = null;
			Type type = field.getGenericType();
			//字段类型为List
			if(type.toString().startsWith("java.util.List")) {
				value = HbaseCommonUtil.bytesToValue(String.class, b);
				if(value ==null) {
					continue;
				}
				List list = Arrays.asList(value.toString().split(HbaseCommonUtil.COLLECTION_SPLIT_CH));
				value = list;
				//字段类型为set
			}else if(type.toString().startsWith("java.util.Set")) {
				value = HbaseCommonUtil.bytesToValue(String.class, b);
				if(value ==null) {
					continue;
				}
				Set set = new HashSet();
				for(Object o : value.toString().split(HbaseCommonUtil.COLLECTION_SPLIT_CH)){
					set.add(o);
				}
				value = set;
				//字段类型为Map
			}else if(type.toString().startsWith("java.util.Map")) {
				value = HbaseCommonUtil.bytesToValue(String.class, b);
				if(value ==null) {
					continue;
				}
				Map map = new HashMap();
				for(Object o:value.toString().split(HbaseCommonUtil.COLLECTION_SPLIT_CH)){
					Object[] arr = o.toString().split(HbaseCommonUtil.MAP_KV_CH);
					map.put(arr[0],arr[1]);
				}
				value = map;
			}else{
				value = HbaseCommonUtil.bytesToValue(type, b);
			}
			HbaseCommonUtil.setProperty(model, fieldName, value);
		}
		return model;
	}
	
	/**
	 * 关闭流计算
	 * @param admin
	 * @param scanner
	 * @param table
	 */
	private void close(Connection conn,Admin admin, ResultScanner scanner, Table table){
		if(conn != null){
			try {
				conn.close();
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
}
