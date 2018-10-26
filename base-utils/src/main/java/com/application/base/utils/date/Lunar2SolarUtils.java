package com.application.base.utils.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @desc 农历/公历互转类
 * @author bruce
 */
public class Lunar2SolarUtils {

	/**
	 * 测试开始.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(solarTolunar("2014-09-24"));
	}
	
	static int num1=12,num2=1936,num3=2028,num4=13,num5=400,num6=100,num7=4;
	
	private final static int[] MONTH_DAYS = { 29, 30 };
	
	private final static int[][] DAYS_MONTH = { { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
			{ 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };
	
	private final static int[][] DATAS = { { 23, 3, 2, 17, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0 },
			{ 41, 0, 4, 23, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1 },
			{ 30, 7, 5, 28, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1 },
			{ 49, 0, 6, 33, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1 },
			{ 38, 0, 0, 38, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 26, 6, 2, 44, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0 },
			{ 45, 0, 3, 49, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 35, 0, 4, 54, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 },
			{ 24, 4, 5, 59, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1 },
			{ 43, 0, 0, 5, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1 },
			{ 32, 0, 1, 10, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1 },
			{ 21, 2, 2, 15, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1 },
			{ 40, 0, 3, 20, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1 },
			{ 28, 7, 5, 26, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 47, 0, 6, 31, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 36, 0, 0, 36, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 26, 5, 1, 41, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1 },
			{ 44, 0, 3, 47, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1 },
			{ 33, 0, 4, 52, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0 },
			{ 23, 3, 5, 57, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1 },
			{ 42, 0, 6, 2, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1 },
			{ 30, 8, 1, 8, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0 },
			{ 48, 0, 2, 13, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0 },
			{ 38, 0, 3, 18, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 27, 6, 4, 23, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 45, 0, 6, 29, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0 },
			{ 35, 0, 0, 34, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
			{ 24, 4, 1, 39, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0 },
			{ 43, 0, 2, 44, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0 },
			{ 32, 0, 4, 50, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1 },
			{ 20, 3, 5, 55, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0 },
			{ 39, 0, 6, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0 },
			{ 29, 7, 0, 5, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 47, 0, 2, 11, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 },
			{ 36, 0, 3, 16, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0 },
			{ 26, 5, 4, 21, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1 },
			{ 45, 0, 5, 26, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1 },
			{ 33, 0, 0, 32, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1 },
			{ 22, 4, 1, 37, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1 },
			{ 41, 0, 2, 42, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1 },
			{ 30, 8, 3, 47, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1 },
			{ 48, 0, 5, 53, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1 },
			{ 37, 0, 6, 58, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 },
			{ 27, 6, 0, 3, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0 },
			{ 46, 0, 1, 8, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0 },
			{ 35, 0, 3, 14, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1 },
			{ 24, 4, 4, 19, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1 },
			{ 43, 0, 5, 24, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1 },
			{ 32, 10, 6, 29, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1 },
			{ 50, 0, 1, 35, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0 },
			{ 39, 0, 2, 40, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1 },
			{ 28, 6, 3, 45, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0 },
			{ 47, 0, 4, 50, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
			{ 36, 0, 6, 56, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0 },
			{ 26, 5, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1 },
			{ 45, 0, 1, 6, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0 },
			{ 34, 0, 2, 11, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0 },
			{ 22, 3, 4, 17, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0 },
			{ 40, 0, 5, 22, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0 },
			{ 30, 8, 6, 27, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1 },
			{ 49, 0, 0, 32, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1 },
			{ 37, 0, 2, 38, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
			{ 27, 5, 3, 43, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1 },
			{ 46, 0, 4, 48, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1 },
			{ 35, 0, 5, 53, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1 },
			{ 23, 4, 0, 59, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 42, 0, 1, 4, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 31, 0, 2, 9, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0 },
			{ 21, 2, 3, 14, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 39, 0, 5, 20, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1 },
			{ 28, 7, 6, 25, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1 },
			{ 48, 0, 0, 30, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1 },
			{ 37, 0, 1, 35, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1 },
			{ 25, 5, 3, 41, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1 },
			{ 44, 0, 4, 46, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1 },
			{ 33, 0, 5, 51, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 22, 4, 6, 56, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 40, 0, 1, 2, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 30, 9, 2, 7, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1 },
			{ 49, 0, 3, 12, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1 },
			{ 38, 0, 4, 17, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0 },
			{ 27, 6, 6, 23, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1 },
			{ 46, 0, 0, 28, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0 },
			{ 35, 0, 1, 33, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0 },
			{ 24, 4, 2, 38, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 42, 0, 4, 44, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 31, 0, 5, 49, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 21, 2, 6, 54, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1 },
			{ 40, 0, 0, 59, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1 },
			{ 28, 6, 2, 5, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0 },
			{ 47, 0, 3, 10, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1 },
			{ 36, 0, 4, 15, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1 },
			{ 25, 5, 5, 20, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0 },
			{ 43, 0, 0, 26, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1 },
			{ 32, 0, 1, 31, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0 },
			{ 22, 3, 2, 36, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0 } };

	private static String dateToStr(Date d, int type) {
		String s = "";
		SimpleDateFormat sdf = null;
		switch (type) {
		case 1:
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case 2:
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		case 3:
			sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			break;
		default:
			break;
		}
		s = sdf.format(d);
		return s;
	}

	/**
	 * 根据阴历取得对应的阳历日期（该日期必须在1936—2028年之间）
	 *
	 * @param date
	 *            阴历日期（字符串）
	 * @return 返回对应的阳历日期
	 * @throws Exception
	 */
	public static String lunarTosolar(String date) {
		Date d = getGregorianCalendar(date);
		String s = dateToStr(d, 1);
		s = s.substring(0, 4) + "-" + s.substring(4, 6) + "-" + s.substring(6, 8);
		return s;

	}

	/**
	 * 根据阳历取得对应的阴历日期（该日期必须在1936—2028年之间）
	 *
	 * @param date
	 *            "2015-04-04" 阳历日期（字符串）
	 * @return 返回阴历日期
	 * @throws Exception
	 */
	public static String solarTolunar(String date) {
		Date d = getLunarCalendar(date);
		String s = dateToStr(d, 1);
		s = s.substring(0, 4) + "年" + s.substring(4, 6) + "月" + s.substring(6, 8) + "日";
		return s;
	}

	private static Date getGregorianCalendar(String date) {
		Calendar c = getCalendar(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int index = year - 1936;
		int endIndex = month;

		if ((DATAS[index][1] != 0) && (month > DATAS[index][1])) {
			endIndex++;
		}

		int dayNum = 0;

		for (int i = 0; i < (endIndex - 1); i++) {
			dayNum += MONTH_DAYS[DATAS[index][4 + i]];
		}

		dayNum += day;
		dayNum += DATAS[index][0];

		int yearDays = 365;

		if (isLeapYear(year)) {
			yearDays = 366;
		}

		if (dayNum > yearDays) {
			year++;
			dayNum -= yearDays;
		}

		month = 1;

		int[] dayOfMonth = DAYS_MONTH[0];

		if (isLeapYear(year)) {
			dayOfMonth = DAYS_MONTH[1];
		}
		int i = 0;
		for (; i < num1; i++) {
			dayNum -= dayOfMonth[i];
			if (dayNum <= 0) {
				break;
			}
			month++;
		}

		day = dayOfMonth[i] + dayNum;

		return getDate(year + "-" + month + "-" + day);
	}

	private static Date getLunarCalendar(String date) {
		Calendar calendar = getCalendar(date);
		int year = calendar.get(Calendar.YEAR);
		int month = 1;
		int day;

		if ((year < num2) || (year > num3)) {
			return null;
		}

		int index = year - 1936;
		int lDays = DATAS[index][0];
		int dayYear = calendar.get(Calendar.DAY_OF_YEAR);
		int days;

		if (dayYear >= lDays) {
			days = dayYear - lDays;
		}
		else {
			index--;
			year--;

			Calendar c = getCalendar(year + "-12-31");
			days = (c.get((Calendar.DAY_OF_YEAR)) + dayYear) - DATAS[index][0];
		}

		int i = 0;
		int dayNum = 0;

		for (; i < num4; i++) {
			dayNum += MONTH_DAYS[DATAS[index][i + 4]];

			if (dayNum >= days) {
				break;
			}

			month++;
		}

		day = MONTH_DAYS[DATAS[index][i + 4]] - (dayNum - days);

		if ((DATAS[index][1] != 0) && (month > DATAS[index][1])) {
			month--;
		}

		return getDate(year + "-" + month + "-" + day);
	}

	private static Calendar getCalendar(String date) {
		Date dd = getDate(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dd);

		return calendar;
	}

	private static Date getDate(String date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dd = null;

		try {
			dd = format.parse(date);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return dd;
	}

	private static boolean isLeapYear(int year) {
		if ((year % num5) == 0) {
			return true;
		}
		else if ((year % num6) == 0) {
			return false;
		}
		else if ((year % num7) == 0) {
			return true;
		}

		return false;
	}

}
