package com.application.base.pay.wechat.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @desc nonce_str 随即字符串
 * @author 孤狼
 */
public class RandCharsUtils {

	/**
	 * 随机字符串的生成.
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		int number = 0;
		for (int i = 0; i < length; i++) {
			number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 订单开始交易的时间
	 */
	public static String timeStart() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date());
	}

	/**
	 * 订单开始交易的时间
	 */
	public static String timeExpire() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		//订单后退30分钟结束
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, 30);
		return df.format(now.getTimeInMillis());
	}

	public static void main(String[] args) {
		int count = 10;
		for (int i = 0; i < count; i++) {
			System.out.println("第" + i + "次是：" + getRandomString(32));
		}
		System.out.println("开始时间是：" + timeStart());
		System.out.println("开始时间是：" + timeExpire());
	}

}
