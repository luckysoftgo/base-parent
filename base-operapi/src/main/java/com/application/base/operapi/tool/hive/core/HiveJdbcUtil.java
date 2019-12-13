package com.application.base.operapi.tool.hive.core;

import com.application.base.operapi.tool.hive.common.config.HiveConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : 孤狼
 * @NAME: HiveJdbcUtil
 * @DESC: hive 的jdbc方式操作.
 **/
public class HiveJdbcUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HiveJdbcUtil.class);
	private static  HiveJdbcUtil instance = null;
	private Connection connection = null;

	/**
	 * hive 配置
	 */
	private static HiveConfig hiveConfig;
	
	public static HiveConfig getHiveConfig() {
		return hiveConfig;
	}
	public static void setHiveConfig(HiveConfig hiveConfig) {
		HiveJdbcUtil.hiveConfig = hiveConfig;
	}
	
	/**
	 * 单例模式.
	 * @param hiveConfig
	 * @return
	 */
	public static synchronized HiveJdbcUtil getInstance(HiveConfig hiveConfig) {
		if (instance == null) {
			instance = new HiveJdbcUtil(hiveConfig);
		}
		return instance;
	}
	
	/**
	 * 构造函数.
	 * @param hiveConfig
	 */
	public HiveJdbcUtil(HiveConfig hiveConfig) {
		setHiveConfig(hiveConfig);
	}
	
	
  
  /**
   * 获取jdbc连接
   * @return Connection
   */
  public Connection getConn(){
	  Connection conn = null;
	  try {
		  Class.forName(hiveConfig.getDriver());
		  conn = DriverManager.getConnection(hiveConfig.getUrl(),hiveConfig.getUsername(),hiveConfig.getPassword());
		  connection =conn;
	  }catch (Exception e){
	  	  e.printStackTrace();
	  }
	  return conn;
  }
  
 /**
  * 获取指定表名的表字段信息
  * @param tableName
  * @return List<String>
  * @throws Exception
  */
  public List<String> getTableInfo(String tableName) throws Exception {
      List<String> resultList = new ArrayList<String>();
      List<Map<String,String>> resultMapList = describeTable(tableName);
      for(int i=0;i<resultMapList.size();i++){
          resultList.add(resultMapList.get(i).get("col_name"));
      }
      return resultList;
  }
	
	
	/**
	 * 获取表的描述信息.
	 * @param tableName
	 * @return
	 */
	public List<Map<String,String>> describeTable(String tableName){
	    List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
	    Connection con = getConn();
	    ResultSet res = null;
	    String hiveql = "describe "+tableName;
	    try {
	        Statement stmt = con.createStatement();
	        res = stmt.executeQuery(hiveql);
	        ResultSetMetaData rsmd = res.getMetaData();
	        while(res.next()) {
	            Map<String,String> map = new HashMap<>();
	            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
	                map.put(rsmd.getColumnName(i), res.getString(rsmd.getColumnName(i)));
	            }
	            resultList.add(map);
	        }
	    }catch (Exception e){
	        e.printStackTrace();
	    }
	    return resultList;
	}

    /**
     * 根据表名称、查询字段、条件、限制条数返回数据,若参数为空,请填入"";
     * @param tableName
     * @param columnList
     * @param condition
     * @param limitInfo
     * @return
     */
    public List<Map<String,String>> getDataFromTable(String tableName, List<String> columnList, String condition,String limitInfo){
        List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
        Connection con = getConn();
        ResultSet res = null;
        String hiveql = "select ";
        if(columnList !=null&&columnList.size() > 0){
            for (int i = 0;i < columnList.size();i++){
                if(i != columnList.size()-1) {
	                hiveql += columnList.get(i) + ",";
                }else {
	                hiveql += columnList.get(i);
                }
            }
        }else{
            hiveql += " * ";
        }
       hiveql += " from " + tableName;
       if(condition!=null&&!condition.equals("")){
           hiveql += " where "+condition;
       }
        if(limitInfo!=null&&!limitInfo.equals("")){
            hiveql += " " + limitInfo;
        }
        try {
            Statement stmt = con.createStatement();
            res = stmt.executeQuery(hiveql);
            ResultSetMetaData rsmd = res.getMetaData();
            while(res.next()) {
                Map<String,String> map = new HashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    map.put(rsmd.getColumnName(i), res.getString(rsmd.getColumnName(i)));
                }
                resultList.add(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultList;
    }
	
	/**
	 * 获取hive上表的信息.
	 * @return
	 */
	public LinkedList<String> showTables(){
		String sql = "show tables";
		Connection connn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		LinkedList<String> tables = new LinkedList<>();
		try {
			connn = getConn();
			statement = connn.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String tableName = resultSet.getString(1);
				tables.add(tableName);
			}
			return tables;
		} catch (SQLException e) {
			logger.error("查询数据库表失败,失败原因是:{}",e.getMessage());
			return tables;
		}finally {
		}
	}
	/**
	 * 执行数据.
	 * @param loadStr
	 * @return
	 */
	public String excuteLoadData(String loadStr){
        String result = "";
        Connection con = getConn();
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(loadStr);
        } catch (Exception e){
            result = "执行失败："+loadStr;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 仅执行hiveql，不返回数据，只返回成功失败，比如执行创建表，加载数据等
     * @param hiveql
     * @return
     * @throws Exception
     */
    public boolean excuteHiveql(String hiveql){
        Connection con = getConn();
       try {
           Statement stmt = con.createStatement();
			return stmt.execute(hiveql);
       }catch (Exception e){
           e.printStackTrace();
       }
        return false;
    }
	
	/**
	 * 执行查看条数.
	 * @param tableName
	 * @return
	 */
	public int queryCount(String tableName){
        int sum = 0;
        Connection con = getConn();
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("select count(*) from "+tableName);
            while(res.next()){
                sum = res.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sum;
    }

    /**
     * 获取生成hive表的hiveql
     * hive表默认全部都是string类型，若有其他类型的需要另外封装方法
     * @param tableName
     * @param columnList
     * @return
     */
    public String genCreateTablesql(String tableName,List<String> columnList){
        String sql = "create table "+tableName+"(";
        for(int i = 0;i<columnList.size();i++){
            if(columnList.get(i).equals("")){
               continue;
            }
            if(i != columnList.size()-1){
                sql += columnList.get(i) + " string,";
            }else {
                sql += columnList.get(i) + " string";
            }
        }
        sql += ") row format delimited fields terminated by ','";
        return sql;
    }

    /**
     * 获取生成hive表的hiveql
     * @param tableName
     * @param columnMapList
     * @return
     */
    public String genCreateTablesqlByColumnAndType(String tableName,List<Map<String,String >> columnMapList){
        String sql = "create table "+tableName+"(";
        for (Map<String,String> map:columnMapList){
            Set<String> keys = map.keySet();
            for (String key :keys){
                sql +=  "`"+key +"`"+" " +map.get(key) + ",";
            }
        }
        sql = sql.substring(0,sql.length()-1);
	    sql += ") row format delimited fields terminated by ',' ";
        return sql;
    }
	
	/**
	 *
	 * @param tableName
	 * @param columnList
	 * @return
	 */
	public String genQueryDatasql(String tableName,List<String> columnList){
        String sql = "select ";
        if(columnList.size() == 0) {
            sql += "*";
        }else {
            for (int i = 0; i < columnList.size(); i++) {
                if (i != columnList.size() - 1) {
                    sql += columnList.get(i) + ",";
                } else {
                    sql += columnList.get(i);
                }
            }
        }
        sql += " from "+tableName;
        return sql;
    }
	
	/**
	 *
	 * @param tableName
	 * @return
	 */
	public boolean isTableDataExist(String tableName) {
        Connection con = getConn();
        ResultSet res =null;
        String str = "";
        try {
            Statement stmt = con.createStatement();
            res = stmt.executeQuery("select * from "+tableName+" limit 1");
            while(res.next()){
              str = res.getString(1);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return !str.equals("");
        //return false;
    }
	
	/**
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		HiveConfig hiveConfig = new HiveConfig("jdbc:hive2://192.168.10.185:10000/default");
		HiveJdbcUtil hiveJdbcClient = new HiveJdbcUtil(hiveConfig);
	    //int count = hiveJdbcClient.queryCount("kylin_account");
		//System.out.println(count);
		LinkedList<String> list =hiveJdbcClient.showTables();
		System.out.println(list);
		/*
		List<String> columnList = new ArrayList<>();
		columnList.add("account_id");
		columnList.add("account_buyer_level");
		columnList.add("account_contact");
		columnList.add("account_country");
		String condition = "account_buyer_level=1";
		String limit="limit 1";
		List<Map<String, String>> dataFromTable =hiveJdbcClient.getDataFromTable("kylin_account",columnList,condition,limit);
		for (Map<String, String> map : dataFromTable) {
			map.forEach((key,value)->{
				System.out.println(key+":"+value);
			});
		}
	
		
		List<String> goods2 = hiveJdbcClient.getTableInfo("kylin_account");
		for (String s : goods2) {
			System.out.println(s);
		}
		dataFromTable = hiveJdbcClient.getDataFromTable("kylin_account", Arrays.asList("account_id","account_buyer_level","account_seller_level","account_country","account_contact"), null, null);
		for (Map<String, String> map : dataFromTable) {
			map.forEach((key,value)->{
				System.out.println(key+":"+value);
			});
		}
		*/
	}
}