package com.application.base;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author : 孤狼
 * @NAME: KylinMain
 * @DESC: 麒麟测试.
 **/
public class KylinMain {
	
	private static String newLine = "\r\n";
	
	/**
	 * 麒麟测试.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String result= KylinHttpBasic.login("ADMIN", "KYLIN");
		System.out.println("登录信息:"+result);
		String json = KylinHttpBasic.listCubes(0,50,null,null);
		System.out.println("cobes:"+json);
		
		String cubdesc_array = KylinHttpBasic.getCubeDes("/kylin_sales_cube?");
		String cubdesc_str=cubdesc_array.substring(1, cubdesc_array.length()-1);
		System.out.println("cube 数组:"+cubdesc_array);
		System.out.println("cube 字符串:"+cubdesc_str);
		
		JsonObject json_Cube = new JsonParser().parse(cubdesc_str).getAsJsonObject();
		
		//将json数据转为为int型的数据
		System.out.println("version:"+json_Cube.get("version").toString());
		//将json数据转为为String型的数据
		System.out.println("name:"+json_Cube.get("name").toString());
		String version = json_Cube.get("version").toString();
		System.out.println("version:"+version);
		String cube_Name = json_Cube.get("name").toString();
		System.out.println("cube_Name"+cube_Name);
		String model_name = json_Cube.get("model_name").toString();
		System.out.println("model_name"+model_name);
		String partition_date_start = json_Cube.get("partition_date_start").toString();
		String partition_date_end = json_Cube.get("partition_date_end").toString();
		String engine_type = json_Cube.get("engine_type").toString();
		String storage_type = json_Cube.get("storage_type").toString();
		System.out.println("partition_date_start:"+partition_date_start+",partition_date_end="+partition_date_end+",engine_type="+engine_type+",storage_type="+storage_type);
		
		KylinHttpBasic.login("ADMIN", "KYLIN");
		String modelDesc = KylinHttpBasic.getDataModel(model_name);
		System.out.println("模块介绍:"+modelDesc);
		JsonObject json_Model = new JsonParser().parse(modelDesc).getAsJsonObject();
		String fact_table_str = json_Model.get("fact_table").toString();
		System.out.println("fact_table:"+fact_table_str);
		
	}
}