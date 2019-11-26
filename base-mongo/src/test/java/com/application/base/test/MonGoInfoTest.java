package com.application.base.test;

import com.application.base.utils.common.PropStringUtils;
import com.mongodb.*;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class MonGoInfoTest {
	
	/**
	 * mongo 测试,得到链接和数据库的数据.
	 * @param args
	 */
	public static void main(String[] args) {
		Properties prop = PropStringUtils.getProperties("/properties/mongo.properties");
		String hosts = prop.getProperty("mongo.hosts");
		String host = hosts.split(",")[0].split(":")[0];
		int port = Integer.parseInt(hosts.split(",")[0].split(":")[1]);
		String msg = "";
		try {
			Mongo mongo = new Mongo(host,port);
	        DB db = mongo.getDB("mongoTest");
	        //查询所有的聚集集合
	        for (String name : db.getCollectionNames()) {
	        	 msg ="Mongo数据库 mongoTest 所拥有的数据库collectionName有 : " + name;
	            System.out.println(msg);
	        }
	        DBCollection users = db.getCollection("mongo_morphia");
	        //查询所有的数据
	        DBCursor dbCursor = users.find();
	        DBObject obj = dbCursor.next();
			Set<String> cloumns = obj.keySet();
			for (String str : cloumns) {
				msg ="mongo_morphia 集合拥有的字段有: "+str;
				System.out.println(msg);
			}
	        Iterator<DBObject> ites = dbCursor.iterator();
	        for (DBObject object : dbCursor) {
	        	Set<String> sets = object.keySet();  // : 所有的 key 
	        	Map<String, Object> maps = object.toMap(); // 所有的 key 和 value
	        	for (Map.Entry<String, Object> entry:maps.entrySet()) {
	        		msg = "所在的数据集是:"+object.getClass()+",字段是:"+entry.getKey()+",所承载的值是:"+entry.getValue();
					System.out.println(msg);
				}
	        	msg ="结果是:"+object.toString();
	        	System.out.println(msg);
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}

