package com.application.base.utils.common.jsonsql;

import com.application.base.utils.common.BaseStringUtil;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author : 孤狼
 * @NAME: TableSchema
 * @DESC: 表结构的描述.
 **/
public class TableSchema {

	/**
	 *1:Integer 自增类型;2.String类型.
	 */
	private String intType="1",strType="2";
	/**
	 * 列信息.
	 */
	private LinkedList<String> cloumns;
	/**
	 * Json对象.
	 */
	private JSONObject jsonObject;
	
	/**
	 * 转换用的数据集对象.
	 */
	private String dataKey;
	
	/**
	 * 表名.
	 */
	private String tableName;
	/**
	 * 描述.
	 */
	private String tableDesc;
	/**
	 * 字段占表的大小.
	 */
	private Integer charLen=256;
	
	/**
	 * 主键设置.
	 */
	private PrimaryKey primaryKey;
	
	
	/**
	 * @param tableName
	 * @param iter
	 */
	public TableSchema(String tableName,Iterator<String> iter, JSONObject jsonObject) {
		cloumns = new LinkedList<String>();
		this.tableName = tableName;
		this.jsonObject = jsonObject;
		while (iter.hasNext()) {
			cloumns.add(iter.next());
		}
	}
	
	/**
	 * @param tableName
	 * @param iter
	 */
	public TableSchema(String tableName,String tableDesc, Iterator<String> iter, JSONObject jsonObject) {
		cloumns = new LinkedList<String>();
		this.tableName = tableName;
		this.tableDesc = tableDesc;
		this.jsonObject = jsonObject;
		while (iter.hasNext()) {
			cloumns.add(iter.next());
		}
	}
	
	/**
	 * @param tableName
	 * @param iter
	 */
	public TableSchema(String tableName,String tableDesc, Iterator<String> iter, JSONObject jsonObject,Integer charLen) {
		cloumns = new LinkedList<String>();
		this.tableName = tableName;
		this.tableDesc = tableDesc;
		this.jsonObject = jsonObject;
		this.charLen = charLen;
		while (iter.hasNext()) {
			cloumns.add(iter.next());
		}
	}
	
	/**
	 * @param tableName
	 * @param iter
	 */
	public TableSchema(String tableName,String tableDesc, Iterator<String> iter, JSONObject jsonObject,Integer charLen,PrimaryKey primaryKey) {
		cloumns = new LinkedList<String>();
		this.tableName = tableName;
		this.tableDesc = tableDesc;
		this.jsonObject = jsonObject;
		this.charLen = charLen;
		this.primaryKey = primaryKey;
		while (iter.hasNext()) {
			cloumns.add(iter.next());
		}
	}
	
	/**
	 * 建表语句
	 * @return
	 */
	public String createSql() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("drop table if exists " + tableName + ";\n");
		buffer.append("create table " + tableName + "(" + "\n");
		String primDesc = "primary key (id)";
		Integer integerCloumnLen = 11;
		Integer stringCloumnLen = 50;
		if (primaryKey==null){
			buffer.append("\tid int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长的主键',"+"\n");
		}else{
			if (intType.equals(primaryKey.getPrimType())){
				if (primaryKey.getCharLen()>=1){
					integerCloumnLen = primaryKey.getCharLen();
				}
				buffer.append("\t"+primaryKey.getPrimKey()+" int("+integerCloumnLen+") NOT NULL AUTO_INCREMENT COMMENT '自增长的主键',"+"\n");
			}else if (strType.equals(primaryKey.getPrimType())){
				if (primaryKey.getCharLen()>=1){
					stringCloumnLen = primaryKey.getCharLen();
				}
				buffer.append("\t"+primaryKey.getPrimKey()+" varchar("+stringCloumnLen+") NOT NULL COMMENT '主键',"+"\n");
			}
			primDesc = "primary key ("+primaryKey.getPrimKey()+")";
		}
		//列信息.
		for (Object x : cloumns) {
			buffer.append("\t"+x.toString() + " varchar("+charLen+") comment '" + jsonObject.get(x.toString()) + "' ,\n");
		}
		buffer.append("\t"+primDesc);
		if (BaseStringUtil.isNotEmpty(tableDesc)){
			buffer.append("\n) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '"+tableDesc+"';");
		}else{
			buffer.append("\n) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '"+tableName+"表信息';");
		}
		return  buffer.toString();
	}
	
	/**
	 * 列名.
	 * @return
	 */
	public LinkedList<String> getCloumns() {
		return this.cloumns;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setCloumns(LinkedList<String> cloumns) {
		this.cloumns = cloumns;
	}
	
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getTableDesc() {
		return tableDesc;
	}
	
	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}
	
	public Integer getCharLen() {
		return charLen;
	}
	
	public void setCharLen(Integer charLen) {
		this.charLen = charLen;
	}
	
	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}
	
	public void setPrimaryKey(PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public String getDataKey() {
		return dataKey;
	}
	
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
}
