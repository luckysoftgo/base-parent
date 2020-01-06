package com.application.base.utils.common;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @desc http 请求工具类
 * @author 孤狼
 */
public class HttpRequestUtils {
	/**
	 * 编码
	 */
	private static final String ENCODING = "UTF-8";
	
	/**
	 * 连接超时时间
	 */
	private static final int CONNECT_TIMEOUT = 1000 * 120;
	/**
	 * 读取数据超时时间
	 */
	private static final int SOCKET_TIMEOUT = 1000 * 180;
	
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url,String param) throws Exception {
		String result = "";
		BufferedReader in = null;
		String urlNameStr = url;
		if (StringUtils.isNotBlank(param)){
			urlNameStr=urlNameStr +"?"+ param;
		}
		URL realUrl = new URL(urlNameStr);
		// 打开和URL之间的连接
		HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
		connection.setConnectTimeout(CONNECT_TIMEOUT);
		connection.setReadTimeout(SOCKET_TIMEOUT);
		// 设置通用的请求属性
		connection.setRequestMethod("GET");
		settings(connection);
		
		// 建立实际的连接
		connection.connect();
		// 获取所有响应头字段
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		if (in != null) {
			in.close();
		}
		connection.disconnect();
		return result;
	}
	
	/**
	 * 公共属性设置.
	 * @param connection
	 */
	private static void settings(HttpURLConnection connection) {
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
		connection.setRequestProperty("Accept", "application/json");
	}
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url,String param) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			System.setProperty("sun.net.http.retryPost", "false");
		}
		catch (Exception e) {
		}
		URL realUrl = new URL(url);
		// 打开和URL之间的连接
		HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
		conn.setConnectTimeout(CONNECT_TIMEOUT);
		conn.setReadTimeout(SOCKET_TIMEOUT);
		conn.setRequestMethod("POST");
		// 设置通用的请求属性
		settings(conn);
		
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.connect();
		// 获取URLConnection对象对应的输出流
		out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), ENCODING));
		//发送请求参数
		out.print(param);

		// flush输出流的缓冲
		out.flush();
		// 定义BufferedReader输入流来读取URL的响应
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(), ENCODING));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		if (out != null) {
			out.close();
		}
		if (in != null) {
			in.close();
		}
		conn.disconnect();
		return result;
	}
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static Map<String, List<String>> getHeaders(String url, String param) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			System.setProperty("sun.net.http.retryPost", "false");
		}
		catch (Exception e) {
		}
		URL realUrl = new URL(url);
		// 打开和URL之间的连接
		HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
		conn.setConnectTimeout(CONNECT_TIMEOUT);
		conn.setReadTimeout(SOCKET_TIMEOUT);
		// 设置通用的请求属性
		settings(conn);
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.connect();
		// 获取URLConnection对象对应的输出流
		out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), ENCODING));
		//发送请求参数
		out.print(param);
		// flush输出流的缓冲
		out.flush();
		// 定义BufferedReader输入流来读取URL的响应
		Map<String, List<String>>  headerFields = conn.getHeaderFields();
		if (out != null) {
			out.close();
		}
		if (in != null) {
			in.close();
		}
		conn.disconnect();
		return headerFields;
	}
}
