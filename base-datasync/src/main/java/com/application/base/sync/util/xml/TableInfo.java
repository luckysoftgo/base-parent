package com.application.base.sync.util.xml;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * @author : admin
 * @NAME: TableInfo
 * @DESC: 表属性描述
 **/
@Data
@NoArgsConstructor
public class TableInfo extends BaseInfo{
	
	/**
	 * 是否有items项目.
	 */
	private boolean items;
	
	/**
	 * 用来表示这个接口对应的数据源和表信息 (唯一).
	 */
	private String uniqueKey;
	/**
	 * json读取路径.
	 */
	private String path;
	/**
	 * 数据解析的层级.
	 */
	private String[] dataKey;
	/**
	 * json结果是否是数组,默认是字符串.
	 */
	private String dataArray= "false";
	
	/**
	 * 接口对应的表名
	 */
	private String tableName;
	/**
	 * 表的描述
	 */
	private String comments;
	/**
	 * 是否全局.
	 */
	private String global;
	/**
	 * 是否是自增主键.
	 */
	private String autoPk;
	
	/**
	 * 列的长度.
	 */
	private int columnLen;
	/**
	 * text 类型的字段用","隔开.
	 */
	private String[] textField;
	
	
	/***当autoPk='true'时候,下面的参数才有意义***/
	
	/**
	 * 主键类型:默认是:String,String 或 Integer.
	 */
	private String primType;
	/**
	 * 主键
	 */
	private String primKey;
	/**
	 * 主键长度.
	 */
	private int primLen;
	
	/**
	 * 需要剔除的项目.
	 */
	private String[] deleteItem;
	/**
	 * 要编列的项.
	 */
	private String[] turnColumn;
	
	/**
	 * 所有的列.
	 */
	private LinkedList<ColumnInfo> columns;
	
	/**
	 * 所有的二级项目.
	 */
	private LinkedList<ItemInfo> itemInfos;
	
	/**
	 * 子表和子表关联关系.
	 */
	private LinkedHashMap<String,ItemInfo> itemsMap;
	
}
