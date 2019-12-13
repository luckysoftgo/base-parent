package com.application.base.operapi.tool.hive.rdbs;


/**
 * @author : 孤狼
 * @NAME: DataSourceType
 * @DESC: 数据类型定义.
 **/
public class DataSourceType {
	
	public static void main(String[] args) {
		System.out.println(getRequestUrl("127.0.0.1",3306,"test","mysql"));
	}
	
	/**
	 * mysql 数据类型定义
	 */
    public static final String MYSQL_TYPE = "mysql";
	/**
	 * sqlserver 数据类型定义
	 */
    public static final String SQLSERVER_TYPE = "sqlserver";
	/**
	 * oracle 数据类型定义
	 */
    public static final String ORACLE_TYPE = "oracle";
	/**
	 * mysql driver 名
	 */
    public static final String MYSQL_DRIVER_NAME = "com.mysql.jdbc.Driver";
	/**
	 * sqlserver driver 名
	 */
    public static final String SQLSERVER_DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	/**
	 * oracle driver 名
	 */
    public static final String ORACLE_TYPE_DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	
	/**
	 * 通过类型判断导出的目标库.
	 * @param dataSourceType
	 * @return
	 */
	public static String getDriverName(String dataSourceType){
        switch (dataSourceType){
            case MYSQL_TYPE:
                return MYSQL_DRIVER_NAME;
            case SQLSERVER_TYPE:
                return SQLSERVER_DRIVER_NAME;
            case ORACLE_TYPE:
                return ORACLE_TYPE_DRIVER_NAME;
            default:
            	return MYSQL_DRIVER_NAME;
        }
    }
	
	/**
	 * 斜杆
	 * @param host
	 * @param port
	 * @param databaseName
	 * @param dataSourceType
	 * @return
	 */
	public static String getRequestUrl(String host,int port,String databaseName,String dataSourceType){
		switch (dataSourceType){
			case DataSourceType.MYSQL_TYPE:
				return "jdbc:mysql://"+host+":"+port+"/"+databaseName+"?serverTimezone=GMT%2b8&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
			case DataSourceType.SQLSERVER_TYPE:
				return "jdbc:sqlserver://"+host+":"+port+";DatabaseName="+databaseName;
			case DataSourceType.ORACLE_TYPE:
				return "jdbc:oracle:thin:@//"+host+":"+port+":"+databaseName;
			default:
				return "jdbc:mysql://"+host+":"+port+"/"+databaseName+"?serverTimezone=GMT%2b8&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
		}
	}
	
}
