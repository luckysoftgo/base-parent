package com.application.base.utils.common;

import java.security.MessageDigest;

/**
 * @desc sha256 加密
 * @author 孤狼
 */
public class SHA256Util {
	
	/**
	 * 加密设置提交.
	 * @param str
	 * @return
	 */
	public static String getSHA256(String str){
		MessageDigest messageDigest;
		String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes("UTF-8"));
			encodeStr = byte2Hex(messageDigest.digest());
		}catch (Exception e){
			e.printStackTrace();
		}
		return encodeStr;
	}
	
	/**
	 * 加密设置.
	 * @param bytes
	 * @return
	 */
	private static String byte2Hex(byte[] bytes){
		StringBuffer buffer = new StringBuffer();
		String tmp = "";
		for (int i=0;i<bytes.length;i++) {
			tmp = Integer.toHexString(bytes[i] & 0xFF);
			if (tmp.length()==1){
				buffer.append("0");
			}
			buffer.append(tmp);
		}
		return buffer.toString();
	}
	
}
