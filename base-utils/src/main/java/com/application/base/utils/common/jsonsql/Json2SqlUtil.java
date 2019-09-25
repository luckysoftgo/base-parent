package com.application.base.utils.common.jsonsql;


import com.application.base.utils.common.AjaxResult;
import com.application.base.utils.common.BaseStringUtil;
import com.application.base.utils.json.JsonConvertUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: Json2SqlUtil
 * @DESC: json串到sql语句.
 **/
public class Json2SqlUtil {
	
	/**
	 * 测试.
	 * @param args
	 */
	public static void main(String[] args) {
		String json = "{\n" +
				"    \"id\": \"id\",\n" +
				"    \"name\": \"名称\",\n" +
				"    \"price\": \"价格\",\n" +
				"    \"store\": \"住址\"\n" +
				"}";
		
		String data = "{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"test\":\"test123\",\"value\":[{\"primType\":\"1\"," +
					"\"primKey\":\"orderId\",\"charLen\":20},{\"primType\":\"1\",\"primKey\":\"userId\"," +
					"\"charLen\":20},{\"primType\":\"2\",\"primKey\":\"coureId\",\"charLen\":50}]}}";
		
		JSONObject jsonObject = new JSONObject(data);
		jsonObject = jsonObject.getJSONObject("data");
		JSONArray array = jsonObject.getJSONArray("value");
		jsonObject = (JSONObject) array.get(0);
		
		PrimaryKey primaryKey = new PrimaryKey("userId","1",20);
		System.out.println(createSql(json,false,"test1",null));
		System.out.println("===================================");
		System.out.println(createSql(json,false,"test2","测试test2表的信息描述",null));
		System.out.println("===================================");
		System.out.println(createSql(json,false,"test3","测试test3表的信息描述",500,null));
		System.out.println("===================================");
		System.out.println(createSql(json,false,"test4","测试test4表的信息描述",50,primaryKey,null));
		System.out.println("===================================");
		primaryKey.setPrimType("2");
		System.out.println(createSql(json,false,"test5","测试test5表的信息描述",100,primaryKey,null));
		System.out.println("===================================");
		
		AjaxResult ajaxResult = AjaxResult.success(primaryKey);
		data = JsonConvertUtils.toJson(ajaxResult);
		System.out.println(createSql(data,false,"test6","测试data表的信息描述",100,primaryKey,new String[]{"data"}));
		System.out.println("===================================");
		PrimaryKey primaryKey1 = new PrimaryKey("orderId","1",20);
		PrimaryKey primaryKey2 = new PrimaryKey("userId","1",20);
		PrimaryKey primaryKey3 = new PrimaryKey("coureId","2",50);
		List<PrimaryKey> list = new ArrayList<>();
		list.add(primaryKey1);
		list.add(primaryKey2);
		list.add(primaryKey3);
		ajaxResult = AjaxResult.success(list);
		data = JsonConvertUtils.toJson(ajaxResult);
		primaryKey.setPrimType("2");
		System.out.println(createSql(data,true,"test7","测试list的信息描述",100,primaryKey,new String[]{"data"}));
		System.out.println("===================================");
		
		HashMap<String,Object> map=new HashMap<>();
		map.put("test","test123");
		map.put("value",list);
		ajaxResult = AjaxResult.success(map);
		data = JsonConvertUtils.toJson(ajaxResult);
		primaryKey.setPrimType("2");
		System.out.println(createSql(data,true,"test8","测试map的信息描述",100,primaryKey,new String[]{"data","value"}));
		System.out.println("===================================");
	}
	
	/**
	 * 生成sql
	 * @param jsonStr
	 * @param dataKey
	 * @param array
	 * @param tableName
	 * @return
	 */
	public static String createSql(String jsonStr,boolean array,String tableName,String... dataKey){
		JSONObject jsonObject = getJSONObject(jsonStr,array,dataKey);
		TableSchema schema = new TableSchema(tableName,jsonObject.keys(),jsonObject);
		return schema.createSql();
	}
	
	
	/**
	 * 生成sql
	 * @param jsonStr
	 * @param dataKey
	 * @param array
	 * @param tableName
	 * @param tableDesc
	 * @return
	 */
	public static String createSql(String jsonStr,boolean array,String tableName,String tableDesc,String... dataKey){
		JSONObject jsonObject = getJSONObject(jsonStr,array,dataKey);
		TableSchema schema = new TableSchema(tableName,tableDesc,jsonObject.keys(),jsonObject);
		return schema.createSql();
	}
	
	/**
	 * 生成sql
	 * @param jsonStr
	 * @param dataKey
	 * @param array
	 * @param tableName
	 * @param tableDesc
	 * @param charLen
	 * @return
	 */
	public static String createSql(String jsonStr,boolean array,String tableName,String tableDesc,Integer charLen,String... dataKey){
		JSONObject jsonObject = getJSONObject(jsonStr,array,dataKey);
		TableSchema schema = new TableSchema(tableName,tableDesc,jsonObject.keys(),jsonObject,charLen);
		return schema.createSql();
	}
	
	/**
	 * 生成sql
	 * @param jsonStr
	 * @param dataKey
	 * @param array
	 * @param tableName
	 * @param tableDesc
	 * @param charLen
	 * @param primaryKey
	 * @return
	 */
	public static String createSql(String jsonStr,boolean array,String tableName,String tableDesc,Integer charLen,PrimaryKey primaryKey,String... dataKey){
		JSONObject jsonObject = getJSONObject(jsonStr,array,dataKey);
		TableSchema schema = new TableSchema(tableName,tableDesc,jsonObject.keys(),jsonObject,charLen,primaryKey);
		return schema.createSql();
	}
	
	/**
	 * 拿到JSONObject.
	 * @param jsonStr
	 * @param dataKeys
	 * @param array
	 * @return
	 */
	private static JSONObject getJSONObject(String jsonStr,boolean array,String... dataKeys){
		JSONObject jsonObject = new JSONObject(jsonStr);
		if (BaseStringUtil.isNotEmpty(dataKeys)){
			for (int i = 0; i < dataKeys.length ; i++) {
				String dataKey = dataKeys[i];
				if (array && i==dataKeys.length-1){
					JSONArray jsonArray = jsonObject.getJSONArray(dataKey);
					jsonObject = (JSONObject) jsonArray.get(0);
				}else {
					jsonObject = jsonObject.getJSONObject(dataKey);
				}
			}
		}
		return jsonObject;
	}
}

