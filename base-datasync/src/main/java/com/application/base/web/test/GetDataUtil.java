package com.application.base.web.test;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: GetDataUtil
 * @DESC: 测试数据
 **/
public class GetDataUtil {
	
	public static void main(String[] args) {
		String url="http://open.xxx.xxxx.com/services/";
		System.out.println(getDataInfo(url,"branch","不要乱科技","2311497658"));
	}
	
	private static final String DATA_TOKEN = "4c00c062-3a13-4b3e-9e35-7ddce7f207b5";
	
	/**
	 * 获取天眼查的信息.
	 * @param target
	 * @param companyName
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	public static String getDataInfo(String url,String target,String companyName, String companyId){
		System.out.println("url="+url+",target="+target+",companyName="+companyName+",companyId="+companyId);
		String result=null;
		try {
			String baseURL = "/v4/open/"+target+"?id={id}&name={name}&pageNum={pageNum}";
			System.out.println("baseURL="+baseURL);
			String serverUrl = baseURL.replace("{name}", urlEncodeURL(companyName)).replace("{id}", companyId).replace("{pageNum}","1");
			url = url + serverUrl;
			System.out.println("url="+url);
			result = getInfo(url);
			return result;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * URLEncode处理
	 */
	public static String urlEncodeURL(String url) {
		try {
			String result = URLEncoder.encode(url, "UTF-8");
			//+实际上是 空格 url encode而来
			result = result.replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("\\+", "%20");
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static final Map<String, String> errMap = new HashMap<>();
	static {
		errMap.put("300001", "请求失败");
		errMap.put("300002", "账号失效");
		errMap.put("300003", "账号过期");
		errMap.put("300004", "访问频率过快");
		errMap.put("300005", "无权限访问此api");
		errMap.put("300006", "余额不足");
		errMap.put("300007", "剩余次数不足");
		errMap.put("300008", "缺少必要参数");
		errMap.put("300009", "账号信息有误");
		errMap.put("300010", "URL不存在");
	}
	
	public static String getInfo(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("Authorization",DATA_TOKEN);
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 使用finally块来关闭输入流
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> resMap = JSON.parseObject(result, Map.class);
			String error_code = String.valueOf(resMap.get("error_code"));
			if (errMap.containsKey(error_code)) {
				System.out.println("结果状态为：" + errMap.get(error_code));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
