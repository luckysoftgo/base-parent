package com.application.base.utils.number;

import com.application.base.utils.date.DateUtils;

import java.util.Date;

/**
 * @Author: 孤狼
 * @desc:基于 redis 的方法逐个加"1"
 */
public class RedisNoUtil {
	
	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
		
		String todayDate = DateUtils.formatDateStr(new Date(),DateUtils.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_SIMPLE);
		
		for (int i = 0; i <1000; i++) {
			
			//todayDate + String.format("%1$06d", redis.incr(i));
			
			// incr(i)：redis 的 incr(i) 使用.
			//todayDate + String.format("%1$06d", redis.incr(i));
			
		}
	}

}
