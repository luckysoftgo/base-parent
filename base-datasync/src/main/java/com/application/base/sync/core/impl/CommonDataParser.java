package com.application.base.sync.core.impl;

import com.application.base.sync.util.xml.ItemInfo;
import com.application.base.sync.util.xml.TableInfo;
import com.application.base.sync.conts.DataConstant;
import com.application.base.sync.util.xml.ColumnInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: CommonDataParser
 * @DESC: 处理生成的解析
 **/
@Component
public class CommonDataParser {
	
	private Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	/**
	 * 生成创建表的语句(创建create table sql 的方法(告诉列的所有信息,xml中配置了具体的 field 字段).
	 * @return
	 */
	public LinkedList<String> getCreateTablesSql(List<TableInfo> tableInfos){
		if (tableInfos.isEmpty()){
			return null;
		}
		LinkedList<String> tables = new LinkedList<>();
		for (TableInfo tableInfo : tableInfos ) {
			tables.addAll(createTable(tableInfo));
		}
		return tables;
	}
	
	/**
	 * 创建sql
	 * @param tableInfo
	 * @return
	 */
	private LinkedList<String> createTable(TableInfo tableInfo) {
		LinkedList<String> sqls = new LinkedList<>();
		StringBuffer buffer = new StringBuffer();
		LinkedList<String> hearders = getTableHearderInfo(tableInfo, buffer);
		String primDesc = hearders.get(0);
		//列信息.
		List<ColumnInfo> columns = tableInfo.getColumns();
		if (columns!=null && columns.size()>0){
			for (ColumnInfo info : tableInfo.getColumns()) {
				if (StringUtils.isNotBlank(info.getOwner())){
					buffer.append("\t"+info.getOwner());
				}else {
					buffer.append("\t"+info.getName());
				}
				buffer.append(" "+info.getType()).append(" ("+info.getLength()+")");
				if (DataConstant.FLAG_VALUE.equals(info.getRequired())){
					buffer.append(" not null ");
				}
				String defVal = "''";
				if (StringUtils.isNotEmpty(info.getDefaultValue())){
					defVal = info.getDefaultValue();
				}
				buffer.append(" default "+defVal+" comment '" + info.getComments() + "',\n");
			}
		}
		buffer.append("\t"+primDesc);
		if (StringUtils.isNotEmpty(tableInfo.getComments())){
			buffer.append("\n) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '"+tableInfo.getComments()+"';");
		}else{
			buffer.append("\n) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '"+tableInfo.getTableName()+"表信息';");
		}
		sqls.add(buffer.toString());
		if (tableInfo.isItems()){
			sqls.addAll(getItemsSql(tableInfo,hearders.get(1)));
		}
		return sqls;
	}
	
	/**
	 * 子项的建表
	 * @param tableInfo
	 * @return
	 */
	private LinkedList<String> getItemsSql(TableInfo tableInfo,String primKeyStr) {
		LinkedList<String> sqls = new LinkedList<>();
		LinkedList<ItemInfo> itemInfos = tableInfo.getItemInfos();
		for (ItemInfo itemInfo : itemInfos) {
			StringBuffer buffer = new StringBuffer();
			String primDesc = getTableItemHearderInfo(itemInfo, buffer,null);
			//列信息.
			List<ColumnInfo> columns = itemInfo.getColumns();
			if (columns!=null && columns.size()>0){
				for (ColumnInfo info : columns) {
					if (StringUtils.isNotBlank(info.getOwner())){
						buffer.append("\t"+info.getOwner());
					}else {
						buffer.append("\t"+info.getName());
					}
					buffer.append(" "+info.getType()).append(" ("+info.getLength()+")");
					if (DataConstant.FLAG_VALUE.equals(info.getRequired())){
						buffer.append(" not null ");
					}
					String defVal = "''";
					if (StringUtils.isNotEmpty(info.getDefaultValue())){
						defVal = info.getDefaultValue();
					}
					buffer.append(" default "+defVal+" comment '" + info.getComments() + "',\n");
				}
				
				boolean flag = false;
				//主表主键设置.
				for (ColumnInfo info : columns) {
					if (info.getName().equalsIgnoreCase(DataConstant.MAINID)){
						flag = true;
					}
				}
				if (!flag){
					buffer.append(buffer.append(primKeyStr));
				}
			}
			buffer.append("\t"+primDesc);
			if (StringUtils.isNotEmpty(itemInfo.getComments())){
				buffer.append("\n) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '"+itemInfo.getComments()+"';");
			}else{
				buffer.append("\n) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '"+itemInfo.getTableName()+"表信息';");
			}
			sqls.add(buffer.toString());
		}
		return sqls;
	}
	
	/**
	 * 获得建表语句.
	 * @param tableInfo
	 * @param buffer
	 * @return
	 */
	public LinkedList<String> getTableHearderInfo(TableInfo tableInfo, StringBuffer buffer) {
		LinkedList<String> header=new LinkedList<>();
		buffer.append("create table " + tableInfo.getTableName() + "(" + "\n");
		String primDesc = "primary key (id)";
		//默认长度.
		Integer integerCloumnLen = 11;
		Integer stringCloumnLen = 50;
		String primKey="";
		if (DataConstant.FLAG_VALUE.equalsIgnoreCase(tableInfo.getAutoPk())) {
			buffer.append("\tid int(11) not null auto_increment comment '自增长的主键',\n");
			primKey = "\t"+DataConstant.MAINID+" int(11) not null default 0 comment '"+tableInfo.getTableName()+"表的主键',\n";
		} else {
			String primaryKey = tableInfo.getPrimKey();
			String primType = tableInfo.getPrimType();
			int charLen = tableInfo.getPrimLen();
			if (primType.equalsIgnoreCase("Integer")) {
				if (charLen >= 1) {
					integerCloumnLen = charLen;
				}
				buffer.append("\t" + primaryKey + " int(" + integerCloumnLen + ") not null auto_increment comment " +"'主键',\n");
				primKey = "\t"+DataConstant.MAINID+"  int("+integerCloumnLen+") not null default 0 comment '"+tableInfo.getTableName()+"表的主键',\n";
			} else if (primType.equalsIgnoreCase("String")) {
				if (charLen >= 1) {
					stringCloumnLen = charLen;
				}
				buffer.append("\t" + primaryKey + " varchar(" + stringCloumnLen + ") not null default '' comment '主键',\n");
				primKey = "\t"+DataConstant.MAINID+" varchar(" + stringCloumnLen + ") not null default '' comment '"+tableInfo.getTableName()+"表的主键',\n";
			}
			primDesc = "primary key (" + primaryKey + ")";
		}
		buffer.append("\tdisabled tinyint(1)  default 0 comment '删除标志,1删除,0正常使用',\n");
		buffer.append("\t"+DataConstant.UNIQUE_ID+" varchar(50) default '' comment '工业库企业Id',\n");
		buffer.append("\tsaveUser varchar(20) default '' comment '创建者',\n");
		buffer.append("\tsaveTime datetime  default now() comment '创建时间',\n");
		//buffer.append("\tupdateUser varchar(20) default '' comment '更新者',\n");
		//buffer.append("\tupdateTime datetime  default current_timestamp on update current_timestamp comment '更新时间',\n");
		buffer.append("\t\n");
		header.add(primDesc);
		header.add(primKey);
		return header;
	}
	
	/**
	 * 获得建表语句.
	 * @param itemInfo
	 * @param buffer
	 * @return
	 */
	public String getTableItemHearderInfo(ItemInfo itemInfo, StringBuffer buffer, String mainPk) {
		buffer.append("create table " + itemInfo.getTableName() + "(" + "\n");
		String primDesc = "primary key (id)";
		//默认长度.
		Integer integerCloumnLen = 11;
		Integer stringCloumnLen = 50;
		if (DataConstant.FLAG_VALUE.equals(itemInfo.getAutoPk())) {
			buffer.append("\tid int(11) not null auto_increment comment '自增长的主键',\n");
		} else {
			String primaryKey = itemInfo.getPrimKey();
			String primType = itemInfo.getPrimType();
			int charLen = itemInfo.getPrimLen();
			if (primType.equalsIgnoreCase("Integer")) {
				if (charLen >= 1) {
					integerCloumnLen = charLen;
				}
				buffer.append("\t" + primaryKey + " int(" + integerCloumnLen + ") not null auto_increment comment " +"'主键',\n");
			} else if (primType.equalsIgnoreCase("String")) {
				if (charLen >= 1) {
					stringCloumnLen = charLen;
				}
				buffer.append("\t" + primaryKey + " varchar(" + stringCloumnLen + ") not null default '' comment '主键',\n");
			}
			primDesc = "primary key (" + primaryKey + ")";
		}
		buffer.append("\tdisabled tinyint(1)  default 0 comment '删除标志,1删除,0正常使用',\n");
		buffer.append("\t"+DataConstant.UNIQUE_ID+" varchar(50) default '' comment '工业库企业Id',\n");
		buffer.append("\tsaveUser varchar(20) default '' comment '创建者',\n");
		buffer.append("\tsaveTime datetime  default now() comment '创建时间',\n");
		//主表的主键.
		if (StringUtils.isNotBlank(mainPk)){
			buffer.append(mainPk);
		}
		//buffer.append("\tupdateUser varchar(20) default '' comment '更新者',\n");
		//buffer.append("\tupdateTime datetime  default current_timestamp on update current_timestamp comment '更新时间',\n");
		buffer.append("\t\n");
		return primDesc;
	}
}
