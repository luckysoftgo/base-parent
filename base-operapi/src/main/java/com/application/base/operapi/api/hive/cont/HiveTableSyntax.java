package com.application.base.operapi.api.hive.cont;

/**
 * @author : 孤狼
 * @NAME: HiveTableSyntax
 * @DESC: 创建一个枚举类，定义一些常用语句
  **/
public enum  HiveTableSyntax {
	
	/**
	 * 扩展的.
	 */
	EXTERNAL("CREATE EXTERNAL TABLE ","external"),inner("CREATE TABLE ","inner"),
	/**
	 * 默认的
	 */
	DEFALUT("PARTITIONED BY(logdate String) ROW FORMAT DELIMITED FIELDS TERMINATED BY '|' ","default"),
	/**
	 * 生成的
	 */
	SEQUENCE("PARTITIONED BY(logdate String) ROW FORMAT DELIMITED FIELDS TERMINATED BY '|' STORED AS SequenceFile ","sequence");
	
	private String value;
	
	private String index;
	
	private HiveTableSyntax() {
	
	}
	
	private HiveTableSyntax(String value) {
		this.value=value;
	}
	
	private HiveTableSyntax(String value,String index){
		this.value=value;
		this.index=index;
	}
	
	public static String getValue(String index){
		for(HiveTableSyntax hts:HiveTableSyntax.values()){
			if(hts.getIndex().equalsIgnoreCase(index)){
				return hts.getValue();
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getIndex() {
		return index;
	}
	
	public void setIndex(String index) {
		this.index = index;
	}
}
