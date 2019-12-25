package com.application.base.operapi.core.hive.rdbs;

/**
 * @author : 孤狼
 * @NAME: OracleDataType
 * @DESC: Oracle 数据类型定义.
 * 	 * https://www.cnblogs.com/HelloBigTable/p/10675258.html
 * 	 * https://www.jianshu.com/p/b9168e48ec09
 **/
public class OracleDataType{

    public static final String TINYINT ="TINYINT";
    public static final String SMALLINT ="SMALLINT";
    public static final String MEDIUMINT ="MEDIUMINT";
    public static final String INT ="INT";
    public static final String INTEGER ="INTEGER";
    public static final String BINARY_FLOAT ="BINARY_FLOAT";
    public static final String BINARY_DOUBLE ="BINARY_DOUBLE";
    public static final String DECIMAL ="DECIMAL";
    public static final String NUMERIC ="NUMERIC";
    public static final String CHAR ="CHAR";
    public static final String NCHAR ="NCHAR";
    public static final String VARCHAR ="VARCHAR";
    public static final String VARCHAR2 ="VARCHAR2";
    public static final String NVARCHAR ="NVARCHAR";
    public static final String NVARCHAR2 ="NVARCHAR2";
    public static final String DATE ="DATE";
    public static final String DATETIME ="DATETIME";
    public static final String TIMESTAMP ="TIMESTAMP";
	
	/**
	 * oracle 转 hive
	 * @param databaseDataType
	 * @return
	 */
	public static String dataTypeConvertToHiveType(String databaseDataType) {
		switch (databaseDataType){
			case OracleDataType.TINYINT:
				return HiveDataType.TINYINT;
			case OracleDataType.SMALLINT:
				return HiveDataType.SMALLINT;
			case OracleDataType.MEDIUMINT:
			case OracleDataType.INTEGER:
			case OracleDataType.INT:
				return HiveDataType.INT;
			case OracleDataType.BINARY_DOUBLE:
				return HiveDataType.DOUBLE;
			case OracleDataType.DECIMAL:
				return HiveDataType.DOUBLE;
			case OracleDataType.BINARY_FLOAT:
				return HiveDataType.FLOAT;
			case OracleDataType.NUMERIC:
				return HiveDataType.DOUBLE;
			case OracleDataType.CHAR:
			case OracleDataType.NCHAR:
			case OracleDataType.VARCHAR:
			case OracleDataType.VARCHAR2:
			case OracleDataType.NVARCHAR:
			case OracleDataType.NVARCHAR2:
				return HiveDataType.STRING;
			case OracleDataType.DATE:
				return HiveDataType.DATE;
			case OracleDataType.DATETIME:
				return HiveDataType.TIMESTAMP;
			case OracleDataType.TIMESTAMP:
				return HiveDataType.TIMESTAMP;
			default:
				return HiveDataType.STRING;
		}
	}
	
	/**
	 * oracle 转 java 类型
	 * @param databaseDataType
	 * @return
	 */
	public static String dataTypeConvertToJavaType(String databaseDataType) {
		switch (databaseDataType){
			case OracleDataType.TINYINT:
			case OracleDataType.SMALLINT:
			case OracleDataType.MEDIUMINT:
			case OracleDataType.INTEGER:
			case OracleDataType.INT:
				return JavaDataType.INTEGER;
			case OracleDataType.BINARY_DOUBLE:
				return JavaDataType.DOUBLE;
			case OracleDataType.DECIMAL:
				return JavaDataType.BIGDECIMAL;
			case OracleDataType.BINARY_FLOAT:
				return JavaDataType.FLOAT;
			case OracleDataType.NUMERIC:
				return JavaDataType.BIGDECIMAL;
			case OracleDataType.CHAR:
			case OracleDataType.NCHAR:
			case OracleDataType.VARCHAR:
			case OracleDataType.VARCHAR2:
			case OracleDataType.NVARCHAR:
			case OracleDataType.NVARCHAR2:
				return JavaDataType.STRING;
			case OracleDataType.DATE:
				return JavaDataType.DATE;
			case OracleDataType.DATETIME:
				return JavaDataType.DATE;
			case OracleDataType.TIMESTAMP:
				return JavaDataType.TIMESTAMP;
			default:
				return JavaDataType.STRING;
		}
	}
}
