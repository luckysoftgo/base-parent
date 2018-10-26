package com.application.base.utils.common;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * @desc: md5加密
 *
 * @author 孤狼
 */
public class Md5Utils {

	private Md5Utils() {
		throw new AssertionError("不要实例化工具类");
	}

	/**
	 * md5 加密
	 * @param string
	 * @return
	 */
	public static String md5Str(String string) {
		// 用于加密的字符
		char[] md5Strs = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			// 使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
			byte[] btInput = string.getBytes();
			// 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
			mdInst.update(btInput);
			// 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			int size = j * 2;
			char[] str = new char[size];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = md5Strs[byte0 >>> 4 & 0xf];
				str[k++] = md5Strs[byte0 & 0xf];
			}
			// 返回经过加密后的字符串
			String md5Str = new String(str);
			return encoder(mdInst,md5Str);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	/**
	 *base64 处理.
	 * @param mdInst
	 * @param md5Str
	 * @return
	 */
	private  static String encoder(MessageDigest mdInst,String md5Str){
		try {
			BASE64Encoder base64en = new BASE64Encoder();
			return base64en.encode(mdInst.digest(md5Str.getBytes("UTF-8")));
		}catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
}
