package com.application.base.pay.wechat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单号创建
 * @author 孤狼
 */
public class OrderNoCreator {

	public static void main(String[] args) {
		System.out.println(createOrderNo());
	}

	/**
	 * 获取毫秒内唯一号码
	 * @return
	 */
	public static String createOrderNo() {
		StringBuilder builder = new StringBuilder("DRZW");
		String dateFormat = formatDate(new Date(), "yyyyMMdd");
		String timeFormat = formatDate(new Date(), "HHmmss");
		builder.append(dateFormat).append(timeFormat).append(IDCreator.create());
		return builder.toString();
	}

	/**
	 * 将日期对象格式化成指定类型的字符串
	 *
	 * @param date
	 *            待格式化日期对象
	 * @param format
	 *            格式化格式
	 * @param defVal
	 *            格式化失败时的默认返回空
	 * @return 格式化后的字符串
	 */
	private static String formatDate(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

}

class IDCreator {
	private static Long second = 0L;
	private static Integer seed = 0;

	private synchronized static String getId() {
		if (second == 0){
			second = System.currentTimeMillis();
		}
		if (second != System.currentTimeMillis()) {
			second = System.currentTimeMillis();
			seed = 0;
			return second.toString() + seed;
		}
		else {
			return second.toString() + ++seed;
		}
	}

	public static String create() {
		String id = getId();
		return id.substring(id.length() - 4, id.length());
	}
}
