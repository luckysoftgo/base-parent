package com.application.base;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: kylinPost
 * @DESC: 返回表的所有数据结构和查询的数据的值数组.
 **/
public class kylinPost {
	
	private String encoding = "UTF-8";
	static String ACCOUNT = "ADMIN";
	static String PWD = "KYLIN";
	/**
	 * 使用httpcline 进行post访问
	 *
	 * @throws IOException
	 */
	public void requestByPostMethod() throws IOException{
		CloseableHttpClient httpClient = this.getHttpClient();
		try {
			//创建post方式请求对象
			String url ="http://192.168.10.185:7070/kylin/api/query";
			HttpPost httpPost = new HttpPost(url);
			String sql = "select * from LOAN_DATA_80W ";
			// 接收参数json列表 (kylin 只接受json格式数据)
			
			Map<String,String> jsonParam = new HashMap<>();
			jsonParam.put("sql", sql);
			jsonParam.put("limit", "20");
			jsonParam.put("project","Kkklin");
			String request = new Gson().toJson(jsonParam);
			//解决中文乱码问题
			StringEntity sentity = new StringEntity(request,encoding);
			sentity.setContentEncoding(encoding);
			sentity.setContentType("application/json");
			httpPost.setEntity(sentity);
			//设置header信息
			//指定报文头【Content-type】、【User-Agent】
			httpPost.setHeader("Content-type", "application/json;charset=utf-8");
			httpPost.setHeader("Authorization", this.authCode());
			System.out.println("POST 请求...." + httpPost.getURI());
			//执行请求
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			try{
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity){
					//按指定编码转换结果实体为String类型
					String body = EntityUtils.toString(entity, encoding);
					JsonObject obj = new JsonParser().parse(body).getAsJsonObject();
					System.out.println(body);
					System.out.println(obj.get("results"));
				}
			} finally{
				httpResponse.close();
			}
		} catch( UnsupportedEncodingException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}finally{
			this.closeHttpClient(httpClient);
		}
	}
	/**
	 * kylin 是base64加密的，访问时候需要加上加密码
	 * @return
	 */
	private String authCode(){
		String auth = ACCOUNT + ":" + PWD;
		String code = "Basic "+new String(new sun.misc.BASE64Encoder().encode(auth.getBytes()));
		return code;
	}
	/**
	 * 创建httpclient对象
	 * @return
	 */
	private CloseableHttpClient getHttpClient(){
		return HttpClients.createDefault();
	}
	/**
	 * 关闭链接
	 * @param client
	 * @throws IOException
	 */
	private void closeHttpClient(CloseableHttpClient client) throws IOException{
		if (client != null){
			client.close();
		}
	}
	public static void main(String[] args) throws IOException{
		kylinPost ky = new kylinPost();
		ky.requestByPostMethod();
	}
}
