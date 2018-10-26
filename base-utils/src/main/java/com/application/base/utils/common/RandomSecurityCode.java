package com.application.base.utils.common;

import java.util.Arrays;

/**
 * @desc 随机数/短信验证码的生成 1.只包含数字 2.包含数字和小写英文 3.包含数字和大小写英文
 * @author 孤狼
 */
public class RandomSecurityCode {
	/**
	 * 字符集合(除去易混淆的数字0、数字1、字母l、字母o、字母O)
	 */
	private final static char[] CHAR_CODE = { '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
			'Z' };

	/**
	 * 产生默认验证码，4位数字,可以重复<br>
	 * 调用此方法和调用getSecurityCode(6, SecurityCodeLevel.Simple, true)有一样的行为
	 *
	 * @return 验证码
	 * @see #getSecurityCode(int, SecurityCodeLevel, boolean)
	 */
	public static char[] getSecurityCode() {
		return getSecurityCode(4, SecurityCodeLevel.SIMPLE, true);
	}

	/**
	 * 获取验证码，指定长度、难度、是否允许重复字符
	 *
	 * @param length
	 *            长度
	 * @param level
	 *            难度
	 * @param isCanRepeat
	 *            是否允许重复字符
	 * @return 验证码
	 */
	public static char[] getSecurityCode(int length, SecurityCodeLevel level, boolean isCanRepeat) {
		// 随机抽取len个字符
		int len = length;
		char[] code;

		// 根据不同的难度截取字符数组
		switch (level) {
			case SIMPLE: {
				code = Arrays.copyOfRange(CHAR_CODE, 0, 9);
				break;
			}
			case MEDIUM: {
				code = Arrays.copyOfRange(CHAR_CODE, 0, 33);
				break;
			}
			case HARD: {
				code = Arrays.copyOfRange(CHAR_CODE, 0, CHAR_CODE.length);
				break;
			}
			default: {
				code = Arrays.copyOfRange(CHAR_CODE, 0, CHAR_CODE.length);
			break;
			}
		}

		// 字符集合长度
		int n = code.length;

		// 抛出运行时异常
		if (len > n && isCanRepeat == false) {
			throw new RuntimeException(String.format("调用SecurityCode.getSecurityCode(%1$s,%2$s,%3$s)出现异常，" + "当isCanRepeat为%3$s时，传入参数%1$s不能大于%4$s", len,level, isCanRepeat, n));
		}
		// 存放抽取出来的字符
		char[] result = new char[len];
		// 判断能否出现重复的字符
		if (isCanRepeat) {
			for (int i = 0; i < result.length; i++) {
				// 索引 0 and n-1
				int r = (int) (Math.random() * n);

				// 将result中的第i个元素设置为codes[r]存放的数值
				result[i] = code[r];
			}
		}
		else {
			for (int i = 0; i < result.length; i++) {
				// 索引 0 and n-1
				int r = (int) (Math.random() * n);

				// 将result中的第i个元素设置为codes[r]存放的数值
				result[i] = code[r];

				// 必须确保不会再次抽取到那个字符，因为所有抽取的字符必须不相同。
				// 因此，这里用数组中的最后一个字符改写codes[r]，并将n减1
				code[r] = code[n - 1];
				n--;
			}
		}
		return result;
	}

	/**
	 * 验证码难度级别，Simple只包含数字，Medium包含数字和小写英文，Hard包含数字和大小写英文
	 */
	public enum SecurityCodeLevel {
		/**
		 * 简单
		 */
		SIMPLE,
		/**
		 * 中等
		 */
		MEDIUM,
		/**
		 * 难
		 */
		HARD
	}
}
