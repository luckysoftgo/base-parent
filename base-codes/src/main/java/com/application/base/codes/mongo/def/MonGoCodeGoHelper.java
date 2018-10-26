package com.application.base.codes.mongo.def;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * mongo获取数据源的类.
 * 
 * @author admin.
 *
 */
public class MonGoCodeGoHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(MonGoCodeGoHelper.class.getClass().getName());
	
	//表的设计.
	private static List<String> monGotables = new  ArrayList<String>();
	private static String DRIVER =	null;
	private static String URL = null;
	private static String PORT = null;
	private static String USER = null;
	private static String PASS = null;
	private static String DATABASENAME = null;
	
	/**
	 * 操作
	 */
	static{
		if (DRIVER==null) {
			ResourceBundle resource = ResourceBundle.getBundle("config/mongo_generate");
			DRIVER = resource.getString("mongo.diver_name");
			URL = resource.getString("mongo.url");
			PORT = resource.getString("mongo.port");
			USER = resource.getString("mongo.username");
			PASS = resource.getString("mongo.password");
			DATABASENAME = resource.getString("mongo.database_name");
			System.out.println("DRIVER = "+DRIVER+",URL="+URL+",PORT="+PORT+",USER="+USER+",PASS="+PASS+",DATABASENAME="+DATABASENAME);
		}
	}

	/**
	 * 获取表的列名.
	 * @param tableName
	 * @return
	 */
	public static List<String> getTableColumn(String tableName){
		try {
			List<String> columnList = new ArrayList<String>();
			DBCollection collection = getDBCollection(tableName);
			DBCursor dbCursor = collection.find();
			DBObject object = dbCursor.next();
			Set<String> columns = object.keySet();
			for (String str : columns) {
				columnList.add(str);
			}
			return columnList;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("获取表的列."+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 获得所有的mongo库中的表的集合.
	 * @return
	 */
	public static List<String> getMonGoTables(){
		if (monGotables!=null && monGotables.size()>0) {
			return monGotables;
		}
		List<String> tables = new ArrayList<String>();
		try {
			DB db = getDB();
			for (String table : db.getCollectionNames()) {
				tables.add(table);
				monGotables.add(table);
			}
			return tables;
		}
		catch (Exception e) {
			logger.error("获得mongo的所有的表失败了,"+e.getMessage());
			return tables;
		}
	}
	
	/**
	 * 返回mongo对象集合.
	 * 
	 * @param tableName
	 * @return
	 */
	public static DBCollection getDBCollection(String tableName){
		DBCollection collection = null;
		try {
			DB db = getDB();
			collection = db.getCollection(tableName);
			return collection;
		}
		catch (Exception e) {
			logger.error("获取mongo链接失败了,"+e.getMessage());
			collection = null;
		}
		return null;
	}
	
	/**
	 * 获得DB对象
	 * @return
	 */
	private static DB getDB(){
		try {
			int con_port = Integer.parseInt(PORT);
			Mongo mongo = new Mongo(URL, con_port);	
			DB db = mongo.getDB(DATABASENAME);
			return db;
		}
		catch (Exception e) {
			logger.error("获取mongo链接失败了,"+e.getMessage());
			return null;
		}
	}
	
}


