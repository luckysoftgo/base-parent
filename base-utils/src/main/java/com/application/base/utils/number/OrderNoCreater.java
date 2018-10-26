package com.application.base.utils.number;

import com.application.base.utils.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @desc 订单号创建.
 * @author 孤狼
 */
public class OrderNoCreater {
	
	private static Logger logger = LoggerFactory.getLogger(OrderNoCreater.class.getName());

	public static void main(String[] args) {
		System.out.println("MY201610261540021961962".length());
		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(OrderNoCreater.createOrderNo("my"));
				}
			}).start();
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(OrderNoCreater.createOrderNo("my"));
				}
			}).start();
		}
	}

	/**
	 * 对类同步,获取唯一的单号
	 * 
	 * @param tag
	 * @return
	 */
	public static String createOrderNo(String tag) {
		synchronized (OrderNoCreater.class) {
			String dateFormat = DateUtils.formatDateStr(new Date(), "yyyyMMdd");
			String timeFormat = DateUtils.formatDateStr(new Date(), "HHmmssS");
			StringBuffer buffer = new StringBuffer(tag.toUpperCase());
			DateUtils.formatDateToMinute(new Date(), DateUtils.FormatType.NORMAL);
			buffer.append(dateFormat).append(timeFormat).append(IDCreator.create());
			logger.info("生成的订单Id是{}" + buffer.toString());
			return buffer.toString();
		}
	}
}

/**
 * id生成号码.
 */
class IDCreator {
	private static Long second = 0L;
	private static Integer seed = 0;

	/**
	 * 产生Id, 多机器访问的时候会生成相同的id,故而加上:synchronized 关键字 修饰 static .
	 *
	 * 缺点: System.currentTimeMillis() 的循环调用,会降低系统的损耗,给系统带来相应的压力.
	 *
	 * @return
	 */
	private synchronized static String getId() {
		if (second == 0) {
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

	/**
	 * 创建成功.
	 * 
	 * @return
	 */
	public static String create() {
		String id = getId();
		return id.substring(id.length() - 4, id.length());
	}
}