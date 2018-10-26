package com.application.base.pay.wechat.util;

import java.security.MessageDigest;

/**
 * @desc md5的工具类
 * @author 孤狼
 */
public class Md5Util {

	private static final String[] HEX_DIGITS = new String[]{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d","e", "f" };

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b,num=256;
		if (n < 0) {
			n += num;
		};
		int d1 = n / 16;
		int d2 = n % 16;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}

	/**
	 * md5 加密
	 * @param origin
	 * @param charsetname
	 * @return
	 */
	public static String md5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname)) {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			}
			else {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
			}
		}
		catch (Exception exception) {
		}
		return resultString;
	}

}
