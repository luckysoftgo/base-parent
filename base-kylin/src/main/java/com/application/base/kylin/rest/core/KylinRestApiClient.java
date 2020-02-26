package com.application.base.kylin.rest.core;

import com.application.base.kylin.exception.KylinException;
import com.application.base.kylin.rest.config.KylinRestConfig;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @NAME: KylinRestApiClient
 * @DESC: rest api
 **/
public class KylinRestApiClient {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 池化对象.
	 */
	private KylinRestConfig poolConfig;
	/**
	 * 请求地址.
	 */
	private String requestUrl;
	/**
	 * 认证串.
	 */
	public String authToken = "";

	/**
	 * 构造函数
	 * @param poolConfig
	 */
	public KylinRestApiClient(KylinRestConfig poolConfig) {
		this.poolConfig = poolConfig;
		byte[] key = (poolConfig.getUserName()+":"+poolConfig.getUserPass()).getBytes();
		this.authToken = new sun.misc.BASE64Encoder().encode(key);
		this.requestUrl = poolConfig.getRequestUrl();
	}
	
	public boolean authToken() throws KylinException {
		byte[] key = (poolConfig.getUserName()+":"+poolConfig.getUserPass()).getBytes();
		String tmpAuthToken = new sun.misc.BASE64Encoder().encode(key);
		authToken = tmpAuthToken;
		return true;
	}
	
	public String login() throws KylinException {
		String method = "POST";
		String param = "/user/authentication";
		return  execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String query(String sql) throws KylinException {
		String method = "POST";
		String param = "/query";
		return  execute(requestUrl,authToken,param,method,sql);
	}
	
	
	public String query(String sql,Integer offset,Integer limit,Boolean acceptPartial, String projectName) throws KylinException {
		String method = "POST";
		String param = "/query";
		Map<String, Object> map = new HashMap<>(16);
		map.put("sql", sql);
		if (offset!=null){
			map.put("offset", offset.intValue());
		}
		if (limit!=null){
			map.put("limit", limit.intValue());
		}
		if (acceptPartial!=null){
			map.put("acceptPartial", acceptPartial.booleanValue());
		}else {
			map.put("acceptPartial", false);
		}
		if(StringUtils.isNotBlank(projectName)){
			map.put("project",projectName);
		}else{
			map.put("project","DEFAULT");
		}
		String jsonStr = new Gson().toJson(map);
		return execute(requestUrl,authToken,param,method,jsonStr);
	}
	
	
	public String queryTables(String projectName) throws KylinException {
		String method = "GET";
		String param = "/tables_and_columns?project="+projectName;
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String listCubes(int offset, int limit, String cubeName, String projectName) throws KylinException {
		String method = "GET";
		StringBuffer buffer = new StringBuffer("/cubes?offset="+offset+"&limit="+limit);
		if (StringUtils.isNotBlank(cubeName)){
			buffer.append("&cubeName="+cubeName);
		}
		if (StringUtils.isNotBlank(projectName)){
			buffer.append("&projectName="+projectName);
		}
		String param = buffer.toString();
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String getCube(String cubeName) throws KylinException {
		String method = "GET";
		String param = "/cubes/"+cubeName;
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String getCubeDesc(String cubeName) throws KylinException {
		String method = "GET";
		String param = "/cube_desc/"+cubeName;
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String getDataModel(String modelName) throws KylinException {
		String method = "GET";
		String param = "/model/"+modelName;
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String buildCube(String cubeName) throws KylinException {
		String method = "PUT";
		String param = "/cubes/"+cubeName+"/build";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String enableCube(String cubeName) throws KylinException {
		String method = "PUT";
		String param = "/cubes/"+cubeName+"/enable";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String disableCube(String cubeName) throws KylinException {
		String method = "PUT";
		String param = "/cubes/"+cubeName+"/disable";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String purgeCube(String cubeName) throws KylinException {
		String method = "PUT";
		String param = "/cubes/"+cubeName+"/purge";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String resumeJob(String jobId) throws KylinException {
		String method = "PUT";
		String param = "/jobs/"+jobId+"/resume";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String pauseJob(String jobId) throws KylinException {
		String method = "PUT";
		String param = "/jobs/"+jobId+"/pause";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String cancelJob(String jobId) throws KylinException {
		String method = "PUT";
		String param = "/jobs/"+jobId+"/cancel";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String getJobStatus(String jobId) throws KylinException {
		String method = "GET";
		String param = "/jobs/"+jobId;
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String getJobStepOutput(String jobId, String stepId) throws KylinException {
		String method = "GET";
		String param = "/jobs/"+jobId+"/steps/"+stepId+"/output";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String getHiveTable(String tableName) throws KylinException {
		String method = "GET";
		String param = "/tables/"+tableName;
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String getHiveTableInfo(String tableName) throws KylinException {
		String method = "GET";
		String param = "/tables/"+tableName+"/exd-map";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String getHiveTables(String project, boolean extend) throws KylinException {
		String method = "GET";
		String param = "/tables";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String loadHiveTables(String tables, String projectName) throws KylinException {
		String method = "POST";
		String param = "/tables/"+tables+"/"+projectName;
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String initCubeStartOffSet(String cubeName) throws KylinException {
		String method = "PUT";
		String param = "/cubes/"+cubeName+"/init_start_offsets";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String buildSteam(String cubeName) throws KylinException {
		String method = "PUT";
		String param = "/cubes/"+cubeName+"/build2";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String checkCubeHole(String cubeName) throws KylinException {
		String method = "GET";
		String param = "/cubes/"+cubeName+"/holes";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	
	public String fillCubeHole(String cubeName) throws KylinException {
		String method = "PUT";
		String param = "/cubes/"+cubeName+"/holes";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	public String getCubeSql(String cubeName) throws KylinException {
		String method = "GET";
		String param = "/cubes/"+cubeName+"/sql";
		return execute(requestUrl,authToken,param,method,null);
	}
	
	/**
	 * 执行http请求.
	 * @param basicUrl
	 * @param authToken
	 * @param param
	 * @param method
	 * @param body
	 * @return
	 */
	private String execute(String basicUrl,String authToken,String param,String method,String body){
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(basicUrl + param);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			//毫秒
			connection.setConnectTimeout(60000);
			//毫秒
			connection.setReadTimeout(50000);
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			connection.setRequestProperty  ("Authorization", "Basic " + authToken);
			connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			if(body !=null){
				byte[] outputInBytes = body.getBytes("UTF-8");
				OutputStream os = connection.getOutputStream();
				os.write(outputInBytes);
				os.flush();
				os.close();
			}
			InputStream content = (InputStream)connection.getInputStream();
			BufferedReader reader = new BufferedReader (new InputStreamReader(content));
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			content.close();
			content=null;
			reader.close();
			reader=null;
			connection.disconnect();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
}

