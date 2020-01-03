package com.application.base.operapi.api.hbase.util;

import com.application.base.operapi.api.hbase.bean.ColumnFamily;
import com.application.base.operapi.api.hbase.bean.ColumnValue;
import com.application.base.operapi.api.hbase.bean.RowValue;
import com.application.base.operapi.api.hbase.bean.TableDesc;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : 孤狼
 * @NAME: HbaseConvertUtil
 * @DESC: 类型转换.
 **/
public class HbaseConvertUtil {
	
	/**
	 * 类型转换.
	 * @param descriptor
	 * @return
	 */
	public static TableDesc constructTableDesc(TableDescriptor descriptor) {
		String tableName = descriptor.getTableName().getNameAsString();
		List<String> columnFamilies = new ArrayList<>();
		Set<byte[]> columnFamilyNames = descriptor.getColumnFamilyNames();
		for (byte[] instance : columnFamilyNames) {
			String columnFamily = Bytes.toString(instance);
			columnFamilies.add(columnFamily);
		}
		TableDesc desc = new TableDesc(tableName,columnFamilies);
		return desc;
	}
	
	/**
	 * 类型转换
	 * @param result
	 * @return
	 */
	public static RowValue constructRowValue(Result result) {
		RowValue rowValue = null;
		if (result != null && !result.isEmpty()) {
			String rowKey = new String(result.getRow());
			NavigableMap<byte[], NavigableMap<byte[], byte[]>> valueMap = result.getNoVersionMap();
			List<ColumnFamily> families = valueMap.entrySet().stream()
					.map(b -> new ColumnFamily(new String(b.getKey()), constructColumnValues(b.getValue())))
					.collect(Collectors.toList());
			rowValue = new RowValue(rowKey, families);
		}
		return rowValue;
	}
	
	/**
	 * 类型转换.
	 * @param map
	 * @return
	 */
	private static List<ColumnValue> constructColumnValues(NavigableMap<byte[], byte[]> map) {
		List<ColumnValue> result = null;
		result = map.entrySet().stream()
				.map(i -> new ColumnValue(new String(i.getKey()), new String(i.getValue())))
				.collect(Collectors.toList());
		return result;
	}
	
}
