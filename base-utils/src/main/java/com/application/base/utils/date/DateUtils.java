
package com.application.base.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @desc 日期工具类
 * @author bruce
 */
public class DateUtils {
	
	/**
	 * 时间格式定义
	 */
	private static final String BLANK = " ";
	/**
	 * 年
	 */
	public static final String YEAR = "YYYY";
	/**
	 * 月
	 */
	public static final String MONTH = "MM";
	/**
	 * 日
	 */
	public static final String DAY = "dd";
	/**
	 * 时
	 */
	public static final String HOUR = "HH";
	/**
	 * 分
	 */
	public static final String MINUTE = "mm";
	/**
	 * 秒
	 */
	public static final String SECOND = "ss";
	/**
	 * 年月日
	 */
	public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
	public static final String YEAR_MONTH_DAY_SIMPLE = "yyyyMMdd";
	public static final String YEAR_MONTH_DAY_CHINESE = "yyyy年MM月dd日";
	/**
	 * 时分
	 */
	public static final String HOUR_MINUTE = "HH:mm";
	public static final String HOUR_MINUTE_SIMPLE = "HHmm";
	public static final String HOUR_MINUTE_CHINESE = "HH时mm分";
	/**
	 * 十分秒
	 */
	public static final String HOUR_MINUTE_SECOND = "HH:mm:ss";
	public static final String HOUR_MINUTE_SECOND_SIMPLE = "HHmmss";
	public static final String HOUR_MINUTE_SECOND_CHINESE = "HH时mm分ss秒";
	/**
	 * 年月日时分
	 */
	public static final String YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy-MM-dd HH:mm";
	public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SIMPLE = "yyyyMMddHHmm";
	public static final String YEAR_MONTH_DAY_HOUR_MINUTE_CHINESE = "yyyy年MM月dd日HH时mm分";
	/**
	 *年月日时分秒
	 */
	public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";
	public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_SIMPLE = "yyyyMMddHHmmss";
	public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_CHINESE = "yyyy年MM月dd日HH时mm分ss秒";
	
	/**
	 * 带毫秒数据.
	 */
	public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_SIMPLE_S = "yyyyMMddHHmmssS";
	
	/**
	 * 时间字符串.
	 */
	public static final String REG_EXP_DATE = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
	
	/**
	 * 一天的秒数
	 */
	private static final int DAY_SECOND = 24 * 60 * 60;

	/**
	 * 一小时的秒数
	 */
	private static final int HOUR_SECOND = 60 * 60;

	/**
	 * 一分钟的秒数
	 */
	private static final int MINUTE_SECOND = 60;

	/**
	 * 每天小时数
	 */
	private static final long HOURS_PER_DAY = 24;
	/**
	 * 每小时分钟数
	 */
	private static final long MINUTES_PER_HOUR = 60;
	/**
	 * 每分钟秒数
	 */
	private static final long SECONDS_PER_MINUTE = 60;
	/**
	 * 每秒的毫秒数
	 */
	private static final long MILLIONSECONDS_PER_SECOND = 1000;
	/**
	 * 每分钟毫秒数
	 */
	private static final long MILLIONSECONDS_PER_MINUTE = 60*1000;
	/**
	 * 每天毫秒数
	 */
	private static final long MILLIONSECONDS_SECOND_PER_DAY = 24 * 60 * 60 * 1000;
	/**
	 * 默认时区
	 */
	public static TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");
	
	
	/**
	 * 时间枚举
	 */
	public static enum SearchDateBuff {
		/**
		 *开始时间
		 */
		SEARCH_BEGIN_TIME("00:00:00"),
		/**
		 *结束时间
		 */
		SEARCH_END_TIME("23:59:59");
		private String buff;
		SearchDateBuff(String buff) {
			this.buff = buff;
		}
		@Override
		public String toString() {
			return buff;
		}
	}
	
	/**
	 * 格式化类型
	 */
	public static enum FormatType {
		/**
		 * 正常
		 */
		NORMAL("1"),
		/**
		 * 合在一起
		 */
		TOGETHER("2"),
		/**
		 * 中文
		 */
		CHINA("3");
		
		private String type;
		FormatType(String type) {
			this.type = type;
		}
		@Override
		public String toString() {
			return type;
		}
	}
	
	/************************************************************************************ Get-Time ****************************************************************************/
	
	/**
	 * 获取YYYY
	 * @return
	 */
	public static String getYear() {
		return formatDateStr(new Date(),YEAR);
	}
	
	/**
	 * 获取MM
	 * @return
	 */
	public static String getMonth() {
		return formatDateStr(new Date(),MONTH);
	}
	
	/**
	 * 获取dd
	 * @return
	 */
	public static String getDay() {
		return formatDateStr(new Date(),DAY);
	}
	
	/**
	 * 获取HH
	 * @return
	 */
	public static String getHour() {
		return formatDateStr(new Date(),HOUR);
	}
	
	/**
	 * 获取mm
	 * @return
	 */
	public static String getMinute() {
		return formatDateStr(new Date(),MINUTE);
	}
	
	/**
	 * 获取ss
	 * @return
	 */
	public static String getSecond() {
		return formatDateStr(new Date(),SECOND);
	}
	
	/**
	 * 将yyyy-MM-dd格式的字符串转换为日期对象
	 * @param dateStr 时间串
	 * @param type 1是正常模式;2是合并在一起模式;3中文模式.
	 * @return
	 */
	public static Date getDateDayByDateStr(String dateStr,FormatType type) {
		Date date =null;
		switch (type){
			case NORMAL:
				date = getDateObjByDateStr(dateStr, YEAR_MONTH_DAY, null);
				break;
			case TOGETHER:
				date = getDateObjByDateStr(dateStr, YEAR_MONTH_DAY_SIMPLE, null);
				break;
			case CHINA:
				date = getDateObjByDateStr(dateStr, YEAR_MONTH_DAY_CHINESE, null);
				break;
			default:
				date = getDateObjByDateStr(dateStr, YEAR_MONTH_DAY, null);
				break;
		}
		return date;
	}
	
	/**
	 *将HH:mm格式的字符串转换为日期对象
	 * @param dateStr
	 * @param type 1是正常模式;2是合并在一起模式;3中文模式.
	 * @return
	 */
	public static Date getDateMinuteByDateStr(String dateStr,FormatType type) {
		Date date =null;
		switch (type){
			case NORMAL:
				date = getDateObjByDateStr(dateStr, HOUR_MINUTE, null);
				break;
			case TOGETHER:
				date = getDateObjByDateStr(dateStr, HOUR_MINUTE_SIMPLE, null);
				break;
			case CHINA:
				date = getDateObjByDateStr(dateStr, HOUR_MINUTE_CHINESE, null);
				break;
			default:
				date = getDateObjByDateStr(dateStr, HOUR_MINUTE_SECOND, null);
				break;
		}
		return date;
	}
	
	/**
	 *将HH:mm:ss格式的字符串转换为日期对象
	 * @param dateStr
	 * @param type 1是正常模式;2是合并在一起模式;3中文模式.
	 * @return
	 */
	public static Date getDateSecondByDateStr(String dateStr,FormatType type) {
		Date date =null;
		switch (type){
			case NORMAL:
				date = getDateObjByDateStr(dateStr, HOUR_MINUTE_SECOND, null);
				break;
			case TOGETHER:
				date = getDateObjByDateStr(dateStr, HOUR_MINUTE_SECOND_SIMPLE, null);
				break;
			case CHINA:
				date = getDateObjByDateStr(dateStr, HOUR_MINUTE_SECOND_CHINESE, null);
				break;
			default:
				date = getDateObjByDateStr(dateStr, HOUR_MINUTE_SECOND, null);
				break;
		}
		return date;
	}
	
	/**
	 *将yyyy-MM-dd HH:mm格式的字符串转换为日期对象
	 * @param dateStr
	 * @param type 1是正常模式;2是合并在一起模式;3中文模式.
	 * @return
	 */
	public static Date getDateTogtherMinuteByDateStr(String dateStr,FormatType type) {
		Date date =null;
		switch (type){
			case NORMAL:
				date = getDateObjByDateStr(dateStr, YEAR_MONTH_DAY_HOUR_MINUTE, null);
				break;
			case TOGETHER:
				date = getDateObjByDateStr(dateStr, YEAR_MONTH_DAY_HOUR_MINUTE_SIMPLE, null);
				break;
			case CHINA:
				date = getDateObjByDateStr(dateStr, YEAR_MONTH_DAY_HOUR_MINUTE_CHINESE, null);
				break;
			default:
				date = getDateObjByDateStr(dateStr, YEAR_MONTH_DAY_HOUR_MINUTE, null);
				break;
		}
		return date;
	}
	
	/**
	 *将yyyy-MM-dd HH:mm:ss格式的字符串转换为日期对象
	 * @param dateStr
	 * @param type 1是正常模式;2是合并在一起模式;3中文模式.
	 * @return
	 */
	public static Date getDateTimeByDateStr(String dateStr,FormatType type) {
		Date date =null;
		switch (type){
			case NORMAL:
				date = getDateObjByDateStr(dateStr, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, null);
				break;
			case TOGETHER:
				date = getDateObjByDateStr(dateStr, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_SIMPLE, null);
				break;
			case CHINA:
				date = getDateObjByDateStr(dateStr, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_CHINESE, null);
				break;
			default:
				date = getDateObjByDateStr(dateStr, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, null);
				break;
		}
		return date;
	}
	
	/**
	 * 获取Date时间
	 * @param dateStr 时间串
	 * @param format 格式方式
	 * @param defVal 格式化失败返回值
	 * @return
	 */
	public static Date getDateObjByDateStr(String dateStr, String format, Date defVal) {
		Date date;
		try {
			date = new SimpleDateFormat(format).parse(dateStr);
		}
		catch (ParseException e) {
			date = defVal;
		}
		return date;
	}
	
	
	/**
	 *将日期对象格式化成yyyy-MM-dd格式的字符串
	 * @param date 时间戳
	 * @param type 1是正常模式;2是合并在一起模式;3中文模式.
	 * @return
	 */
	public static String formatDateToDay(Date date,FormatType type) {
		String result;
		switch (type){
			case NORMAL:
				result = formatDateToStr(date, YEAR_MONTH_DAY, null);
				break;
			case TOGETHER:
				result = formatDateToStr(date, YEAR_MONTH_DAY_SIMPLE, null);
				break;
			case CHINA:
				result = formatDateToStr(date, YEAR_MONTH_DAY_CHINESE, null);
				break;
			default:
				result = formatDateToStr(date, YEAR_MONTH_DAY, null);
				break;
		}
		return result;
	}
	
	/**
	 * 将日期对象格式化成HH:mm格式的字符串
	 * @param date 时间戳
	 * @param type 1是正常模式;2是合并在一起模式;3中文模式.
	 * @return
	 */
	public static String formatDateToMinute(Date date,FormatType type) {
		String result;
		switch (type){
			case NORMAL:
				result = formatDateToStr(date, HOUR_MINUTE, null);
				break;
			case TOGETHER:
				result = formatDateToStr(date, HOUR_MINUTE_SIMPLE, null);
				break;
			case CHINA:
				result = formatDateToStr(date, HOUR_MINUTE_CHINESE, null);
				break;
			default:
				result = formatDateToStr(date, HOUR_MINUTE, null);
				break;
		}
		return result;
	}
	
	/**
	 * 将日期对象格式化成HH:mm:ss格式的字符串
	 * @param date 时间戳
	 * @param type 1是正常模式;2是合并在一起模式;3中文模式.
	 * @return
	 */
	public static String formatDateToSecond(Date date,FormatType type) {
		String result;
		switch (type){
			case NORMAL:
				result = formatDateToStr(date, HOUR_MINUTE_SECOND, null);
				break;
			case TOGETHER:
				result = formatDateToStr(date, HOUR_MINUTE_SECOND_SIMPLE, null);
				break;
			case CHINA:
				result = formatDateToStr(date, HOUR_MINUTE_SECOND_CHINESE, null);
				break;
			default:
				result = formatDateToStr(date, HOUR_MINUTE_SECOND, null);
				break;
		}
		return result;
	}
	
	/**
	 * 将日期对象格式化成yyyy-MM-dd HH:mm格式的字符串
	 * @param date 时间戳
	 * @param type 1是正常模式;2是合并在一起模式;3中文模式.
	 * @return
	 */
	public static String formatDateToTogtherMinute(Date date,FormatType type) {
		String result;
		switch (type){
			case NORMAL:
				result = formatDateToStr(date, YEAR_MONTH_DAY_HOUR_MINUTE, null);
				break;
			case TOGETHER:
				result = formatDateToStr(date, YEAR_MONTH_DAY_HOUR_MINUTE_SIMPLE, null);
				break;
			case CHINA:
				result = formatDateToStr(date, YEAR_MONTH_DAY_HOUR_MINUTE_CHINESE, null);
				break;
			default:
				result = formatDateToStr(date, YEAR_MONTH_DAY_HOUR_MINUTE, null);
				break;
		}
		return result;
	}
	
	/**
	 * 将日期对象格式化成yyyy-MM-dd HH:mm:ss格式的字符串
	 * @param date 时间戳
	 * @param type 1是正常模式;2是合并在一起模式;3中文模式.
	 * @return
	 */
	public static String formatDateToTime(Date date,FormatType type) {
		String result;
		switch (type){
			case NORMAL:
				result = formatDateToStr(date, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, null);
				break;
			case TOGETHER:
				result = formatDateToStr(date, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_SIMPLE, null);
				break;
			case CHINA:
				result = formatDateToStr(date, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_CHINESE, null);
				break;
			default:
				result = formatDateToStr(date, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, null);
				break;
		}
		return result;
	}
	
	/**
	 *将日期对象格式化成指定类型的字符串
	 * @param date 待格式化日期对象
	 * @return
	 */
	public static String formatDateStr(Date date) {
		return formatDateToStr(date, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, null);
	}
	
	/**
	 *将日期对象格式化成指定类型的字符串
	 * @param date 待格式化日期对象
	 * @param format 格式化格式
	 * @return
	 */
	public static String formatDateStr(Date date, String format) {
		return formatDateToStr(date, format, null);
	}
	
	/**
	 * 将日期对象格式化成指定类型的字符串
	 * @param date 待格式化日期对象
	 * @param format 格式化格式
	 * @param defVal 格式化失败时的默认返回空
	 * @return
	 */
	public static String formatDateToStr(Date date, String format, String defVal) {
		String result;
		try {
			result = new SimpleDateFormat(format).format(date);
		}
		catch (Exception e) {
			result = defVal;
		}
		return result;
	}
	
	/**
	 * 带时区的格式化时间
	 * @param format
	 * @param timeZone
	 * @return
	 */
	public static String formatDateTimeZone(Date date, String format, TimeZone timeZone) {
		String result = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setTimeZone(timeZone);
			result = sdf.format(date);
		}
		catch (Exception e) {
		}
		return result;
	}
	
	/**
	 *年加减 返回指定日期加减上年数后的日期
	 * @param date 如果为空则默认为当前时间
	 * @param years 为正数：加年，负数：减年
	 * @return
	 */
	public static Date addAndSubtractYear(Date date, int years) {
		if (date == null) {
			date = getToday();
		}
		return changeYear(date, years);
	}
	
	/**
	 *月份加减 返回指定日期加减上月数后的日期
	 * @param date 如果为空则默认为当前时间
	 * @param months 为正数：加月，负数：减月
	 * @return
	 */
	public static Date addAndSubtractMonth(Date date, int months) {
		if (date == null) {
			date = getToday();
		}
		return changeMonth(date, months);
	}
	
	/**
	 *天加减 返回指定日期加上days天后的日期
	 * @param date 如果为空则默认为当前时间
	 * @param days 为正数：加天，负数：减天
	 * @return
	 */
	public static Date addAndSubtractDays(Date date, int days) {
		if (date == null) {
			date = getToday();
		}
		return changeDays(date, days);
	}
	
	/**
	 *小时加减 返回指定日期加减上小时后的日期
	 * @param date  如果为空则默认为当前时间
	 * @param hours 为正数：加小时，负数：减小时
	 * @return
	 */
	public static Date  addAndSubtractHours(Date date, int hours) {
		if (date == null) {
			date = getToday();
		}
		return changeHours(date, hours);
	}
	
	/**
	 *分钟加减 返回指定日期加减上分钟后的日期
	 * @param date 如果为空则默认为当前时间
	 * @param minutes 为正数：加分钟，负数：减分钟
	 * @return
	 */
	public static Date addAndSubtractMinute(Date date, int minutes) {
		if (date == null) {
			date = getToday();
		}
		return changeMinute(date, minutes);
	}
	
	/**
	 * 指定日期时间分钟上加上分钟数
	 *
	 * @param date
	 * @param minutes
	 * @return
	 */
	private static Date changeMinute(Date date, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}
	
	/**
	 * 指定日期时间上加上时间数
	 *
	 * @param date
	 * @param hours
	 * @return
	 */
	private static Date changeHours(Date date, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return cal.getTime();
	}
	
	/**
	 * 指定的日期加减天数
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	private static Date changeDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, days);
		return cal.getTime();
	}
	
	/**
	 * 指定的日期加减年数
	 *
	 * @param date
	 * @param years
	 * @return
	 */
	private static Date changeYear(Date date, int years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}
	
	/**
	 * 指定的日期加减月数
	 *
	 * @param date
	 * @return
	 */
	private static Date changeMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}
	
	/**
	 * 获取当前日期加时间
	 *
	 * @return
	 */
	public static Date getToday() {
		return new Date();
	}
	
	/**
	 * 当前时间的毫秒数
	 *
	 * @return
	 */
	public static long currentTimeMillis() {
		return getToday().getTime();
	}
	
	/**
	 * 获得当前时间,时间格式为指定格式
	 * @param format 格式方式.
	 * @return
	 */
	public static String getDateCurrentStr(String format) {
		return formatDateStr(getToday(), format);
	}
	
	/**
	 * 获得当前时间,时间格式为 "yyyy-MM-dd"
	 * @return
	 */
	public static String getCurrentDayStr() {
		return formatDateStr(getToday(), YEAR_MONTH_DAY);
	}
	
	/**
	 * 获得当前时间,时间格式为 "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String getCurrentTimeStr() {
		return formatDateStr(getToday(), YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
	}
	
	/**
	 * 获得两个日期相差的天数 D1-D2
	 * @param date1 为空：默认为当前时间
	 * @param date2
	 * @return
	 */
	public static int getTwoDateDays(Date date1, Date date2) {
		if (date1 == null) {
			date1 = getToday();
		}
		long intervalMillSecond = getDateToDayTime(date1).getTime() - getDateToDayTime(date2).getTime();
		// 相差的天数 = 相差的毫秒数 / 每天的毫秒数 (小数位采用去尾制)
		return (int) (intervalMillSecond / MILLIONSECONDS_SECOND_PER_DAY);
	}
	
	/**
	 *获得两个日期之间相差的分钟数。（date1 - date2）
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getTwoDateMinutes(Date date1, Date date2) {
		long intervalMillSecond = date1.getTime() - date2.getTime();
		// 相差的分钟数 = 相差的毫秒数 / 每分钟的毫秒数 (小数位采用进位制处理，即大于0则加1)
		return (int) (intervalMillSecond / MILLIONSECONDS_PER_MINUTE
				+ (intervalMillSecond % MILLIONSECONDS_PER_MINUTE > 0 ? 1 : 0));
	}

	/**
	 *获得两个日期之间相差的秒数差（date1 - date2）
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getTwoDateSeconds(Date date1, Date date2) {
		long intervalMillSecond = date1.getTime() - date2.getTime();
		return (int) (intervalMillSecond / MILLIONSECONDS_PER_SECOND
				+ (intervalMillSecond % MILLIONSECONDS_PER_SECOND > 0 ? 1 : 0));
	}
	
	/**
	 * 将时间调整到当天0:0:0
	 * @return
	 */
	public static Date getDateToDayTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 将时间调整到当天0:0:0
	 * @return
	 */
	public static Date getDateToDayTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获得年龄
	 * @param birthday 生日
	 * @return
	 */
	public static int getAgeByDate(Date birthday) {
		Calendar now = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		birth.setTime(birthday);
		// 取得生日年份
		int year = birth.get(Calendar.YEAR);
		// 年龄
		int age = now.get(Calendar.YEAR) - year;
		// 修正
		now.set(Calendar.YEAR, year);
		age = (now.before(birth)) ? age - 1 : age;
		return age;
	}

	/**
	 * date1 和 date2 是否同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(date1.getTime());
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(date2.getTime());
		return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
				&& c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 得到没有时间的日期
	 * @return
	 */
	public static Date getDateDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 得到没有时间的日期
	 * @param date
	 * @return
	 */
	public static Date getDateDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 *得到没有分和秒的时间
	 * @param date
	 * @return
	 */
	public static Date getDateHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获得输入时间的下月第一天
	 * @param date
	 * @return
	 */
	public static Date getNextMonthFirst(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		// 加一个月
		c.add(Calendar.MONTH, 1);
		// 把日期设置为当月第一天
		c.set(Calendar.DATE, 1);
		return c.getTime();
	}
	
	/**
	 * 根据指定年月，获取月的天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getMonthDays(Integer year, Integer month) {
		if (year != null && year > 0 && month != null && month > 0) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month);
			c.set(Calendar.DATE, 1);
			c.add(Calendar.DATE, -1);
			return c.get(Calendar.DATE);
		}
		return 0;
	}

	/**
	 *获取当前时间是星期几
	 * @param date 時間
	 * @return
	 */
	public static int getWeek(Date date) {
		int[] week = { 7, 1, 2, 3, 4, 5, 6 };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return week[w];
	}
	
	/**
	 *获取当前时间是星期几
	 * @param date 時間
	 * @return
	 */
	public static String getWeekStr(Date date) {
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDays[w];
	}

	/**
	 * 获取当前日期的开始时间
	 * @return
	 */
	public static Date getCurrentDayBegin() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取当天结束时间
	 * @return
	 */
	public static Date getCurrentDayEnd() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 获取几天之前日期的开始时间
	 * @return
	 */
	public static Date getObjDayBegin(int days) {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		// 前一天
		calendar.add(Calendar.DATE, -days);
		return calendar.getTime();
	}

	/**
	 * 获取几天之前日期的结束时间
	 * @return
	 */
	public static Date getObjDayEnd(int days) {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		// 前一天
		calendar.add(Calendar.DATE, -days);
		return calendar.getTime();
	}

	/**
	 * 得到n天之后的日期
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(int days) {
		Calendar canlendar = Calendar.getInstance();
		canlendar.add(Calendar.DATE, days);
		Date date = canlendar.getTime();
		SimpleDateFormat sdfd = new SimpleDateFormat(YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
		String dateStr = sdfd.format(date);
		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);
		Calendar canlendar = Calendar.getInstance();
		canlendar.add(Calendar.DATE, daysInt);
		Date date = canlendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);
		return dateStr;
	}

}
