package com.application.base;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: KylinHttpBasic
 * @DESC: kylin http请求工具类
 * 参考 : http://kylin.apache.org/docs15/howto/howto_use_restapi.html#authentication
 *       https://blog.csdn.net/songchunhong/article/details/79144080
 **/
public class KylinHttpBasic {
	
	/**
	 * 编码.
	 */
	private static String ENCODING;
	/**
	 * 请求地址.
	 */
	private static final String BASEURL = "http://192.168.10.185:7070/kylin/api";
	
	/**
	 * 登录.
	 * @param user
	 * @param passwd
	 * @return
	 */
	public static String login(String user,String passwd){
		String method = "POST";
		String para = "/user/authentication";
		byte[] key = (user+":"+passwd).getBytes();
		ENCODING = new sun.misc.BASE64Encoder().encode(key);
		return excute(para,method,null);
	}
	
	/**
	 * 列出可供查询的表.
	 * @param projectName:工程名
	 * @return
	 */
	public static String listQueryableTables(String projectName){
		String method = "GET";
		String para = "/tables_and_columns?project="+projectName;
		return  excute(para,method,null);
	}
	
	/**
	 * 列出可供查询的cube名字
	 * @param offset required int Offset used by pagination
	 * @param limit required int Cubes per page.
	 * @param cubeName optional string Keyword for cube names. To find cubes whose name contains this keyword.
	 * @param projectName optional string Project name.
	 * @return
	 */
	public static String listCubes(int offset,int limit,String cubeName,String projectName ){
		String method = "GET";
		StringBuffer buffer = new StringBuffer("/cubes?offset="+offset+"&limit="+limit);
		if (StringUtils.isNotBlank(cubeName)){
			buffer.append("&cubeName="+cubeName);
		}
		if (StringUtils.isNotBlank(projectName)){
			buffer.append("&projectName="+projectName);
		}
		String para = buffer.toString();
		return excute(para,method,null);
	}
	
	/**
	 * 获取cube的描述信息
	 * @param cubeName  Cube name.
	 * @return
	 */
	public static String getCubeDes(String cubeName){
		String method = "GET";
		String para = "/cube_desc/"+cubeName;
		return excute(para,method,null);
		
	}
	
	
	/**
	 * 获取cube
	 * @param cubeName
	 * @return
	 */
	public static String getCube(String cubeName){
		String method = "GET";
		String para = "/cubes/"+cubeName;
		return excute(para,method,null);
		
	}
	
	
	
	/**
	 * 获取数据的模式
	 * @param modelName Data model name, by default it should be the same with cube name.
	 * @return
	 */
	public static String getDataModel(String modelName){
		String method = "GET";
		String para = "/model/"+modelName;
		return excute(para,method,null);
		
	}
	
	/**
	 * 开启cube
	 * @param cubeName cubeName Cube name.
	 * @return
	 */
	public static String enableCube(String cubeName){
		String method = "PUT";
		String para = "/cubes/"+cubeName+"/enable";
		return excute(para,method,null);
		
	}
	
	/**
	 * 关闭cube
	 * @param cubeName Cube name.
	 * @return
	 */
	public static String disableCube(String cubeName){
		
		String method = "PUT";
		String para = "/cubes/"+cubeName+"/disable";
		return excute(para,method,null);
		
	}
	
	/**
	 *禁用cube
	 * @param cubeName Cube name.
	 * @return
	 */
	public static String purgeCube(String cubeName){
		
		String method = "PUT";
		String para = "/cubes/"+cubeName+"/purge";
		return excute(para,method,null);
		
	}
	
	
	/**
	 *恢复任务
	 * @param jobId Job id
	 * @return
	 */
	public static String resumeJob(String jobId){
		
		String method = "PUT";
		String para = "/jobs/"+jobId+"/resume";
		return excute(para,method,null);
		
	}

	/**
	 * 构建cube
	 * @param cubeName
	 * @param body
	 * @return
	 */
	public static String build(String cubeName,String body){
		String method = "PUT";
		String para = "/cubes/"+cubeName+"/build";
		return excute(para,method,body);
	}
	
	/**
	 * 增量的rebuild,
	 * startTime - required long Start timestamp of data to build, e.g. 1388563200000 for 2014-1-1
	 * endTime - required long End timestamp of data to build
	 * buildType - required string Supported build type: ‘BUILD’, ‘MERGE’, ‘REFRESH’
	 * @param cubeName  Cube name.
	 * @param body  json.
	 * @return
	 */
	public static String buildCube(String cubeName,String body){
		String method = "PUT";
		String para = "/cubes/"+cubeName+"/rebuild";
		return excute(para,method,body);
	}
	
	/**
	 * Pause job 停用job
	 * @param jobId  Job id.
	 * @return
	 */
	public static String pauseJob(String jobId){
		String method = "PUT";
		String para = "/jobs/"+jobId+"/pause";
		return excute(para,method,null);
		
	}
	
	/**
	 *
	 * @param jobId  Job id.
	 * @return
	 */
	public static String discardJob(String jobId){
		String method = "PUT";
		String para = "/jobs/"+jobId+"/cancel";
		return excute(para,method,null);
	}
	
	/**
	 *
	 * @param jobId  Job id.
	 * @return
	 */
	public static String getJobStatus(String jobId){
		
		String method = "GET";
		String para = "/jobs/"+jobId;
		return excute(para,method,null);
		
	}
	
	/**
	 *获取job每一步的输出
	 * @param jobId Job id.
	 * @param stepId  Step id; the step id is composed by jobId with step sequence id;
	 * for example, the jobId is “fb479e54-837f-49a2-b457-651fc50be110”, its 3rd step id
	 * is “fb479e54-837f-49a2-b457-651fc50be110-3”,
	 * @return
	 */
	public static String getJobStepOutput(String jobId,String stepId){
		String method = "GET";
		String para = "/"+jobId+"/steps/"+stepId+"/output";
		return excute(para,method,null);
	}
	
	/**
	 *获取hive的表
	 * @param tableName table name to find.
	 * @return
	 */
	public static String getHiveTable(String tableName){
		String method = "GET";
		String para = "/tables/"+tableName;
		return excute(para,method,null);
	}
	
	/**
	 *获取hive中的所有的表
	 * @param tableName table name to find.
	 * @return
	 */
	public static String getHiveTables(String tableName){
		String method = "GET";
		String para = "/tables";
		return excute(para,method,null);
	}
	
	/**
	 *获取hive表的扩展信息
	 * @param tableName  table name to find.
	 * @return
	 */
	public static String getHiveTableInfo(String tableName){
		String method = "GET";
		String para = "/tables/"+tableName+"/exd-map";
		return excute(para,method,null);
	}
	
	
	/**
	 *
	 * @param projectName will list all tables in the project.
	 * @param extOptional boolean set true to get extend info of table.
	 * @return
	 */
	public static String getHiveTables(String projectName,boolean extOptional){
		String method = "GET";
		String para = "/tables?project="+projectName+"&ext="+extOptional;
		return excute(para,method,null);
	}
	
	
	/**
	 *
	 * @param tables  table names you want to load from hive, separated with comma.
	 * @param project the project which the tables will be loaded into.
	 * @return
	 */
	public static String loadHiveTables(String tables,String project){
		String method = "POST";
		String para = "/tables/"+tables+"/"+project;
		return excute(para,method,null);
	}
	
	/**
	 *
	 * @param type ‘METADATA’ or ‘CUBE’
	 * @param name  Cache key, e.g the cube name.
	 * @param action ‘create’, ‘update’ or ‘drop’
	 * @return
	 */
	public static String wipeCache(String type,String name,String action){
		String method = "POST";
		String para = "/cache/"+type+"/"+name+"/"+action;
		return excute(para,method,null);
	}
	
	/**
	 * body为json串
	 * @param body
	 * @return
	 */
	public static String query(String body){
		String  method = "POST";
		String para = "/query";
		return excute(para,method,body);
	}
	
	/**
	 * 查询
	 * @param sql:String类型的参数，需要执行的sql语句,查询的sql (select * from tableName )
	 * @param offset:int类型的参数，开始执行的位置，若在sql中指定了则可以将此参数省略
	 * @param limit:int类型的参数，限制返回的行数，同样如果在sql中指定了则可以省略此参数
	 * @param project:string类型的参数，执行query的工程，默认值为DEDFAULT
	 * @return
	 */
	public static String query(String sql,int offset,int limit,String project){
		String  method = "POST";
		String para = "/query";
		Map<String, Object> map = new HashMap<>();
		map.put("sql", sql);
		map.put("offset", offset);
		map.put("limit", limit);
		map.put("acceptPartial", false);
		map.put("project",project);
		String jsonBody = new Gson().toJson(map);
		return excute(para,method,jsonBody);
	}
	
	
	/**
	 * 执行http请求.
	 * @param para
	 * @param method
	 * @param body
	 * @return
	 */
	private static String excute(String para,String method,String body){
		StringBuffer out = new StringBuffer();
		try {
			URL url = new URL(BASEURL +para);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			connection.setRequestProperty  ("Authorization", "Basic " + ENCODING);
			connection.setRequestProperty("Content-Type","application/json");
			if(body !=null){
				byte[] outputInBytes = body.getBytes("UTF-8");
				OutputStream os = connection.getOutputStream();
				os.write(outputInBytes);
				os.close();
			}
			InputStream content = (InputStream)connection.getInputStream();
			BufferedReader in  = new BufferedReader (new InputStreamReader(content));
			String line;
			while ((line = in.readLine()) != null) {
				out.append(line);
			}
			in.close();
			connection.disconnect();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return out.toString();
	}
	
}
