package com.application.base.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

/**
 * @desc 根据IP地址获取详细的地域信息
 * @author 孤狼
 */
public class IpAddressUtils {

	/**
	 *
	 * @param content
	 *            请求的参数 格式为：name=xxx&pwd=xxx
	 * @param encoding
	 *            服务器端请求编码。如GBK,UTF-8等
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getAddresses(String content, String encoding) throws UnsupportedEncodingException {
		// 这里调用pconline的接口
		String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
		// 从http://whois.pconline.com.cn取得IP所在的省市区信息
		String returnStr = this.getResult(urlStr, content, encoding);
		if (returnStr != null) {
			// 处理返回的省市区信息
			System.out.println(returnStr);
			String[] temp = returnStr.split(",");
			int num =3;
			if (temp.length < num) {
				// 无效IP，局域网测试
				return "0";
			}
			String region = (temp[5].split(":"))[1].replaceAll("\"", "");
			// 省份
			region = decodeUnicode(region);

			String country = "";
			String area = "";
			// String region = "";
			String city = "";
			String county = "";
			String isp = "";
			for (int i = 0; i < temp.length; i++) {
				switch (i) {
				case 1:
					country = (temp[i].split(":"))[2].replaceAll("\"", "");
					// 国家
					country = decodeUnicode(country);
					break;
				case 3:
					area = (temp[i].split(":"))[1].replaceAll("\"", "");
					// 地区
					area = decodeUnicode(area);
					break;
				case 5:
					// 省份
					region = (temp[i].split(":"))[1].replaceAll("\"", "");
					region = decodeUnicode(region);
					break;
				case 7:
					city = (temp[i].split(":"))[1].replaceAll("\"", "");
					// 市区
					city = decodeUnicode(city);
					break;
				case 9:
					county = (temp[i].split(":"))[1].replaceAll("\"", "");
					// 地区
					county = decodeUnicode(county);
					break;
				case 11:
					isp = (temp[i].split(":"))[1].replaceAll("\"", "");
					// ISP公司
					isp = decodeUnicode(isp);
					break;
				default:
					break;
				}
			}

			System.out.println(country + "=" + area + "=" + region + "=" + city + "=" + county + "=" + isp);
			return region;
		}
		return null;
	}

	/**
	 * @param urlStr
	 *            请求的地址
	 * @param content
	 *            请求的参数 格式为：name=xxx&pwd=xxx
	 * @param encoding
	 *            服务器端请求编码。如GBK,UTF-8等
	 * @return
	 */
	private String getResult(String urlStr, String content, String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.connect();
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(content);
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}

	/**
	 * unicode 转换成 中文
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					int num = 4;
					for (int i = 0; i < num; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				}
				else {
					if (aChar == 't') {
						aChar = '\t';
					}
					else if (aChar == 'r') {
						aChar = '\r';
					}
					else if (aChar == 'n') {
						aChar = '\n';
					}
					else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			}
			else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}
	
	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
		IpAddressUtils addressUtils = new IpAddressUtils();
		String ip = "125.70.11.136";
		String address = "";
		try {
			address = addressUtils.getAddresses("ip=" + ip, "utf-8");
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(address);
	}

	/**
	 * 获取IP地址.
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
		String unknown ="unknown";
		int count = 15;
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		}
		
		else if (ip.length() > count) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				String str ="unknown";
				if (!(str.equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}
}