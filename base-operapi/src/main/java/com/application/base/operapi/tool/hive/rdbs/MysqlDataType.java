package com.application.base.operapi.tool.hive.rdbs;

import com.application.base.operapi.tool.hive.core.HiveDataType;

/**
 * @author : 孤狼
 * @NAME: MysqlDataType
 * @DESC: Mysql 数据类型定义.
 **/
public class MysqlDataType {
	
    public static final String TINYINT ="TINYINT";
    public static final String SMALLINT ="SMALLINT";
    public static final String MEDIUMINT ="MEDIUMINT";
    public static final String INT ="INT";
    public static final String INTEGER ="INTEGER";
    public static final String BIGINT ="BIGINT";
    public static final String DOUBLE ="DOUBLE";
    public static final String FLOAT ="FLOAT";
    public static final String DECIMAL ="DECIMAL";
    public static final String NUMERIC ="NUMERIC";
    public static final String CHAR ="CHAR";
    public static final String VARCHAR ="VARCHAR";
    public static final String DATE ="DATE";
    public static final String DATETIME ="DATETIME";
    public static final String TIMESTAMP ="TIMESTAMP";
	
	/**
	 * mysql 转 hive 类型
	 * @param databaseDataType
	 * @return
	 */
	public static String dataTypeConvertToHiveType(String databaseDataType) {
		switch (databaseDataType){
			case MysqlDataType.TINYINT:
				return HiveDataType.TINYINT;
			case MysqlDataType.SMALLINT:
				return HiveDataType.SMALLINT;
			case MysqlDataType.MEDIUMINT:
			case MysqlDataType.INT:
			case MysqlDataType.INTEGER:
				return HiveDataType.INT;
			case MysqlDataType.BIGINT:
				return HiveDataType.BIGINT;
			case MysqlDataType.DOUBLE:
				return HiveDataType.DOUBLE;
			case MysqlDataType.FLOAT:
				return HiveDataType.FLOAT;
			case MysqlDataType.DECIMAL:
				return HiveDataType.DECIMAL;
			case MysqlDataType.NUMERIC:
				return HiveDataType.DOUBLE;
			case MysqlDataType.VARCHAR:
				return HiveDataType.STRING;
			case MysqlDataType.CHAR:
				return HiveDataType.STRING;
			case MysqlDataType.DATE:
				return HiveDataType.DATE;
			case MysqlDataType.DATETIME:
				return HiveDataType.TIMESTAMP;
			case MysqlDataType.TIMESTAMP:
				return HiveDataType.TIMESTAMP;
			default:
				return HiveDataType.STRING;
		}
	}
}
