package com.application.base.pay.wechat.util;

import com.application.base.pay.wechat.constant.WechatConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @desc http 请求 util 类.
 * @author admin
 */
public class SocketRequestUtils {

	private static Logger logger = LoggerFactory.getLogger(SocketRequestUtils.class.getName());
	
	/**
	 * 向指定URL发送GET方法的请求
	 * @requestUrl requestUrl发送请求的URL
	 * @requestParams 请求的参数,请求参数应该是 name1=value1&name2=value2 的形式。
	 */
	public static String sendHttpGetRequest(String requestUrl,String requestParams,boolean https) {
		logger.info("GET请求的地址{},参数是{}",requestUrl,requestParams);
		StringBuffer buffer = new StringBuffer("");
		BufferedReader in = null;
		try {
			if (StringUtils.isNotBlank(requestParams)) {
				requestUrl = "?" + requestParams;
			}
			URL realUrl = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			if (https) {
				connection = (HttpsURLConnection) realUrl.openConnection();
			}
			connection.setConnectTimeout(60000);
			connection.setReadTimeout(60000);
			//请求方式
			connection.setRequestMethod(WechatConstant.REQUEST_METHOD_GET);
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			connection.setRequestProperty("Content-type", "text/xml");
			connection.setRequestProperty("Pragma:", "no-cache");
			connection.setRequestProperty("Cache-Control", "no-cache");
			
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// // 遍历所有的响应头字段
			for (String key : map.keySet()) {
				 System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(),WechatConstant.ENCODING));
			String line;
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			//关闭连接
			connection.disconnect();
			connection=null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			}
			catch (Exception e) {
				logger.error("关闭流出现异常,Exception:{}",e);
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * @param requestUrl:请求的地址 
	 * @param requestParams:发送请求的参数
	 */
	public static String sendHttpPostRequest(String requestUrl,String requestParams,boolean https) {
		logger.info("http 请求的地址:{},请求的参数是:{}",requestUrl,requestParams);
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer buffer = new StringBuffer("");
		try {
			URL realUrl = new URL(requestUrl);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			if (https) {
				connection = (HttpsURLConnection) realUrl.openConnection();
			}
			connection.setConnectTimeout(60000);
			connection.setReadTimeout(60000);
			//请求方式
			connection.setRequestMethod(WechatConstant.REQUEST_METHOD_POST);
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			//POST 请求不能使用缓存
			connection.setUseCaches(false);
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			connection.setRequestProperty("Content-type", "text/xml");
			connection.setRequestProperty("Pragma:", "no-cache");
			connection.setRequestProperty("Cache-Control", "no-cache");
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), WechatConstant.ENCODING));
			if (StringUtils.isNotBlank(requestParams)) {
				OutputStream outputStream = connection.getOutputStream();
				outputStream.write(requestParams.getBytes(WechatConstant.ENCODING));
				outputStream.close();
			}
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), WechatConstant.ENCODING));
			String line;
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			//关闭连接
			connection.disconnect();
			connection=null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
					out=null;
				}
				if (in != null) {
					in.close();
					in=null;
				}
			}
			catch (IOException ex) {
				logger.error("关闭流出现异常,Exception:{}",ex);
				ex.printStackTrace();
			}
		}
		return buffer.toString();
	}

}
