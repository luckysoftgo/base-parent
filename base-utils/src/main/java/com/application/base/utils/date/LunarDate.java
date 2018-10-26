package com.application.base.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @desc 計算日期
 * @author bruce
 *
 */
public class LunarDate {

	private static int[] dayMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	public static int day;
	public static int dayCyl;
	public static boolean isLeap;
	public static int monCyl;
	public static int month;
	public static int year;
	public static int yearCyl;

	public LunarDate() {
	}

	public LunarDate(int paramInt1, int paramInt2, int paramInt3) {
		year = paramInt1;
		month = paramInt2;
		day = paramInt3;
	}

	public static int getNumDayOfMonth(int paramInt1, int paramInt2) {
		int i = dayMonth[(paramInt2 - 1)];
		int num=2;
		if ((isBigYear(paramInt1)) && (paramInt2 == num)) {
			i++;
		}
		return i;
	}

	public static boolean isBigYear(int paramInt) {
		int num = 400;
		if (paramInt % num == 0) {
			return true;
		}
		return (paramInt % 4 == 0) && (paramInt % 100 != 0);
	}

	public static long datedays(Date aDate, Date aDate2) {
		long myTime;
		long myTime2;
		long days = 0;
		myTime = (aDate.getTime() / 1000);
		// SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
		myTime2 = (aDate2.getTime() / 1000);
		if (myTime > myTime2) {
			days = (myTime - myTime2) / (1 * 60 * 60 * 24);
		}
		else {
			days = (myTime2 - myTime) / (1 * 60 * 60 * 24);
		}
		return days;

	}
	// 求2个日期的天数

	public static long dateDays(String date1, String date2) throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		long myTime;
		Date aDate2;
		Date aDate;
		long myTime2;
		long days = 0;

		aDate = formatter.parse(date1);
		myTime = (aDate.getTime() / 1000);

		aDate2 = formatter.parse(date2);
		myTime2 = (aDate2.getTime() / 1000);

		if (myTime > myTime2) {
			days = (myTime - myTime2) / (1 * 60 * 60 * 24);
		}
		else {
			days = (myTime2 - myTime) / (1 * 60 * 60 * 24);
		}

		return days;

	}
	
	/**
	 * 求2个日期的天数
	 * @param year1
	 * @param month1
	 * @param day1
	 * @param year2
	 * @param month2
	 * @param day2
	 * @return
	 * @throws ParseException
	 */
	public static long datedays2(int year1, int month1, int day1, int year2, int month2, int day2)
			throws ParseException {

		String date1;
		String date2;
		date1 = year1 + "-" + month1 + "-" + day1;
		date2 = year2 + "-" + month2 + "-" + day2;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		long myTime;
		Date aDate;
		Date aDate2;
		long myTime2;
		long days = 0;

		aDate = formatter.parse(date1);
		myTime = (aDate.getTime() / 1000);

		aDate2 = formatter.parse(date2);
		myTime2 = (aDate2.getTime() / 1000);

		if (myTime > myTime2) {
			days = (myTime - myTime2) / (1 * 60 * 60 * 24);
		}
		else {
			days = (myTime2 - myTime) / (1 * 60 * 60 * 24);
		}

		return days;

	}

	public static int getNumDayFrom19000101() {
		int day = 0;
		try {
			day = (int) datedays2(year, month, day, 1900, 1, 1);
		}
		catch (ParseException ex) {
			System.out.println(ex.getMessage());
		}
		return day;
	}

	public static long getSFrom19000101() {
		return 86400L * (1L + getNumDayFrom19000101());
	}
}
