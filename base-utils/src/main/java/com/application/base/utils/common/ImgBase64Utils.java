package com.application.base.utils.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

/**
 * 
 * @desc Base64 工具异常
 * @author 孤狼
 */
public class ImgBase64Utils {

	static Logger logger = LoggerFactory.getLogger(ImgBase64Utils.class.getName());
	
	static  String ENCONDING = "UTF-8";
	
	/**
	 * base64字符串转化成图片保存
	 * @param imgBase64Str
	 * @param imgSavePath
	 * @return
	 */
	public static boolean generateImage(String imgBase64Str,String imgSavePath) {
		if (isEmpty(imgBase64Str) || isEmpty(imgSavePath)) {
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] bytes = decoder.decodeBuffer(imgBase64Str);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {
					// 调整异常数据
					bytes[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(imgSavePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		}
		catch (Exception e) {
			logger.error("生成图片发生异常了,异常信息是:{}",e.getMessage());
			return false;
		}
	}
	
	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imgLoclPath
	 * @return
	 */
	public static String getLocalImageStr(String imgLoclPath) {
		InputStream stream = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			stream = new FileInputStream(imgLoclPath);
			// 图片的可用字节长度
			data = new byte[stream.available()];
			stream.read(data);
			stream.close();
			// 对字节数组Base64编码
			BASE64Encoder encoder = new BASE64Encoder();
			// 返回Base64编码过的字节数组字符串
			return encoder.encode(data);
		}
		catch (IOException e) {
			logger.error("将图片信息转换成字符串异常了,异常信息是:{}",e.getMessage());
		}
		return null;
	}
	
	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imgRemoteUrl
	 * @return
	 */
	public static String getRemoteImageStr(String imgRemoteUrl) {
		String result = null;
		logger.info("获取图片地址:" + imgRemoteUrl);
		try {
			URL realUrl = new URL(imgRemoteUrl);
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			conn.setConnectTimeout(60000);
			conn.setReadTimeout(60000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			InputStream inStream = conn.getInputStream();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			// 设置接收字节长度.
			byte[] buffer = new byte[inStream.available()];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			// 对字节数组Base64编码
			BASE64Encoder encoder = new BASE64Encoder();
			// 返回Base64编码过的字节数组字符串
			result = encoder.encode(outStream.toByteArray());
			outStream.close();
			outStream = null;
			inStream.close();
			inStream = null;
			conn.disconnect();
			conn = null;
		}
		catch (Exception e) {
			logger.error("获取地址栏信息的流信息失败", e.getMessage());
		}
		return result;
	}
	
	/**
	 * base64加密
	 * @author zhanghan
	 * @date 2017年3月15日
	 * @return
	 */
	public static String encoder(String str)
			throws UnsupportedEncodingException {
		if (isEmpty(str)) {
			return "";
		}
		byte[] bys = str.getBytes(ENCONDING);
		if (bys != null && bys.length > 0) {
			String s = new BASE64Encoder().encode(bys);
			return s;
		}
		return "";
	}

	/**
	 * base64解密
	 * 
	 * @author zhanghan
	 * @date 2017年3月15日
	 * @return
	 */
	public static String decoder(String str) throws IOException {
		if (isEmpty(str)) {
			return "";
		}
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bys = decoder.decodeBuffer(str);
		if (bys != null && bys.length > 0) {
			String result = new String(bys, ENCONDING);
			return result;
		}
		return "";
	}
	
	/**
	 * 图片转化成base64字符串
	 * @param file
	 * @return
	 */
	public static String getImageStr(File file) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(file);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		// 返回Base64编码过的字节数组字符串
		return encoder.encode(data);
	}

	/**
	 * 是否为空.
	 * @param result
	 * @return
	 */
	private static boolean isEmpty(String result) {
		if (null==result){
			return true;
		}
		if ("".equals(result) || result.length()==0){
			return true;
		}
		return  false;
	}
}
