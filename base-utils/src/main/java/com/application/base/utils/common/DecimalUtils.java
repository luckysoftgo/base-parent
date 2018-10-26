package com.application.base.utils.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @desc decimal格式化工具
 * @author 孤狼
 */
public class DecimalUtils {
	
	/**
	 *decimal 格式化的方式.
	 */
	private static final DecimalFormat FORMAT_ZERO = new DecimalFormat("0.00");
	private static final DecimalFormat FORMAT_NO_ZERO = new DecimalFormat("#,###");
	private static final DecimalFormat FORMAT_DECIMAL = new DecimalFormat("#,###.################################");
	private static final DecimalFormat FORMAT_ZERO_SIMPLE = new DecimalFormat("#,##0.00");

	public static final RoundingMode ROUNDING_MODE_FOR_FORMAT_UTIL = RoundingMode.HALF_UP;

	public static String formatToDecimalPlaces(BigDecimal num) {
		if (num == null) {
			num = BigDecimal.ZERO;
		}
		FORMAT_ZERO_SIMPLE.setRoundingMode(ROUNDING_MODE_FOR_FORMAT_UTIL);
		return FORMAT_ZERO_SIMPLE.format(num);
	}

	public static String formatToDecimal(BigDecimal num) {
		if (num == null) {
			num = BigDecimal.ZERO;
		}
		FORMAT_ZERO.setRoundingMode(ROUNDING_MODE_FOR_FORMAT_UTIL);
		return FORMAT_ZERO.format(num);
	}

	public static String formatToNoZero(BigDecimal num) {
		if (num == null) {
			num = BigDecimal.ZERO;
		}
		FORMAT_DECIMAL.setRoundingMode(ROUNDING_MODE_FOR_FORMAT_UTIL);
		return FORMAT_DECIMAL.format(num);
	}

	public static String formatTo0DecimalPlaces(BigDecimal num) {
		if (num == null) {
			num = BigDecimal.ZERO;
		}
		FORMAT_NO_ZERO.setRoundingMode(ROUNDING_MODE_FOR_FORMAT_UTIL);
		return FORMAT_NO_ZERO.format(num);
	}

	public static String formatToPatternDecimalPlaces(BigDecimal num, String pattern) {
		if (num == null) {
			num = BigDecimal.ZERO;
		}
		return formatToInputDecimalPlaces(num, findDecimalPlaces(pattern));
	}

	public static String formatToInputDecimalPlaces(BigDecimal num, int places) {
		if (num == null) {
			num = BigDecimal.ZERO;
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(places);
		df.setMinimumFractionDigits(places);
		df.setRoundingMode(ROUNDING_MODE_FOR_FORMAT_UTIL);
		return df.format(num);
	}

	public static String readValue(String value) {
		return value.replaceAll(",", "");
	}

	public static int findDecimalPlaces(String pattern) {
		int decimalPlaces = 0;
		String patt ="1.0";
		char index = '1';
		if ((pattern != null) && (!pattern.contains(patt))) {
			int idx = pattern.indexOf(".");
			while ((idx < pattern.length()) && (pattern.charAt(idx) != index)) {
				idx++;
				decimalPlaces++;
			}
		}
		return decimalPlaces;
	}
}
