package com.application.base.utils.common;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @desc
 * 此工具类的灵活即可以加密，也可以确定id唯一性，实际运用中，可参考官网的实例，此工具类即可实现表id，也可以作为session
 * 官网只介绍只作为唯一id使用，实际中，此类可以灵活运用到其他的功能中。 优点介绍：1.数据加密 2.数据颜值加密
 * 3.数据唯一性（开发中，尽量不要id暴露给用户） 4.灵活性，开发只需要提供数据，即可。
 * 
 * 缺点介绍：1.开发使用时，需要传值。 例如： 1.可以id加密，使数据安全得到保护，作为表id。 2.对帐户的加密，可实现密码与账户的经过盐值加密。
 * 3.可以作为uuid加密，使数据得到安全， 4.实际中，对一些数据要保护，参考官网实例，即可加密，并确保唯一。
 *
 * @author 孤狼
 */

public class HaShiDsUtils {

	private static final String DEFAULT_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	/**
	 * 自定义格式
 	 */
	private static final String DEFAULT_ALPHABET_TEST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-@";
	
	/**
	 * 常量设置.
	 */
	private String salt = "";
	private String alphabet = "";
	private String seps = "cfhistuCFHISTU";
	private int minHashLength = 0;
	private String guards;
	
	/**
	 * 正则校验.
	 */
	Pattern pattern = Pattern.compile("[\\w\\W]{1,12}");
	
	public HaShiDsUtils() {
		this("");
	}

	public HaShiDsUtils(String salt) {
		this(salt, 0);
	}

	public HaShiDsUtils(String salt, int minHashLength) {
		this(salt, minHashLength, DEFAULT_ALPHABET);
	}

	public HaShiDsUtils(String salt, int minHashLength, String alphabet) {
		this.salt = salt;
		if (minHashLength < 0) {
			this.minHashLength = 0;
		} else {
			this.minHashLength = minHashLength;
		}
		this.alphabet = alphabet;

		String uniqueAlphabet = "";
		for (int i = 0; i < this.alphabet.length(); i++) {
			if (!uniqueAlphabet.contains("" + this.alphabet.charAt(i))) {
				uniqueAlphabet += "" + this.alphabet.charAt(i);
			}
		}

		this.alphabet = uniqueAlphabet;

		int minAlphabetLength = 16;
		if (this.alphabet.length() < minAlphabetLength) {
			throw new IllegalArgumentException(
					"alphabet must contain at least " + minAlphabetLength + " unique characters");
		}
		
		String blank =" ";
		if (this.alphabet.contains(blank)) {
			throw new IllegalArgumentException("alphabet cannot contains spaces");
		}

		for (int i = 0; i < this.seps.length(); i++) {
			int j = this.alphabet.indexOf(this.seps.charAt(i));
			if (j == -1) {
				this.seps = this.seps.substring(0, i) + " " + this.seps.substring(i + 1);
			}
			else {
				this.alphabet = this.alphabet.substring(0, j) + " " + this.alphabet.substring(j + 1);
			}
		}

		this.alphabet = this.alphabet.replaceAll("\\s+", "");
		this.seps = this.seps.replaceAll("\\s+", "");
		this.seps = this.consistentShuffle(this.seps, this.salt);

		double sepDiv = 3.5;
		if (("".equals(this.seps)) || ((this.alphabet.length() / this.seps.length()) > sepDiv)) {
			int sepsLen = (int) Math.ceil(this.alphabet.length() / sepDiv);

			if (sepsLen == 1) {
				sepsLen++;
			}

			if (sepsLen > this.seps.length()) {
				int diff = sepsLen - this.seps.length();
				this.seps += this.alphabet.substring(0, diff);
				this.alphabet = this.alphabet.substring(diff);
			}
			else {
				this.seps = this.seps.substring(0, sepsLen);
			}
		}

		this.alphabet = this.consistentShuffle(this.alphabet, this.salt);
		// use double to round up
		int guardDiv = 12;
		int guardCount = (int) Math.ceil((double) this.alphabet.length() / guardDiv);
		int value = 3;
		if (this.alphabet.length() < value) {
			this.guards = this.seps.substring(0, guardCount);
			this.seps = this.seps.substring(guardCount);
		}
		else {
			this.guards = this.alphabet.substring(0, guardCount);
			this.alphabet = this.alphabet.substring(guardCount);
		}
	}

	/**
	 * @deprecated should use encode() since v1.0
	 */
	@Deprecated
	@SuppressWarnings("unused")
	public String encrypt(long... numbers) {
		return encode(numbers);
	}

	/**
	 * @deprecated should use decode() since v1.0
	 */
	@Deprecated
	@SuppressWarnings("unused")
	public long[] decrypt(String hash) {
		return decode(hash);
	}

	/**
	 * @deprecated should use encodeHex() since v1.0
	 */
	@Deprecated
	@SuppressWarnings("unused")
	public String encryptHex(String hexa) {
		return encodeHex(hexa);
	}

	/**
	 * @deprecated should use decodeHex() since v1.0
	 */
	@Deprecated
	@SuppressWarnings("unused")
	public String decryptHex(String hash) {
		return decodeHex(hash);
	}

	/**
	 * Encrypt numbers to string
	 *
	 * @param numbers
	 *            the numbers to encrypt
	 * @return the encrypt string
	 */
	public String encode(long... numbers) {
		for (long number : numbers) {
			if (number > 9007199254740992L) {
				throw new IllegalArgumentException("number can not be greater than 9007199254740992L");
			}
		}
		String retval = "";
		if (numbers.length == 0) {
			return retval;
		}

		return this.encodeMain(numbers);
	}

	/**
	 * Decrypt string to numbers
	 *
	 * @param hash
	 *            the encrypt string
	 * @return decryped numbers
	 */
	public long[] decode(String hash) {
		long[] ret = {};
		if ("".equals(hash)) {
			return ret;
		}
		return this.decodeMain(hash, this.alphabet);
	}

	/**
	 * Encrypt hexa to string
	 *
	 * @param hexa
	 *            the hexa to encrypt
	 * @return the encrypt string
	 */
	public String encodeHex(String hexa) {
		String patten ="^[0-9a-fA-F]+$";
		if (!hexa.matches(patten)) {
			return "";
		}

		List<Long> matched = new ArrayList<Long>();
		Matcher matcher = pattern.matcher(hexa);

		while (matcher.find()) {
			matched.add(Long.parseLong("1" + matcher.group(), 16));
		}

		// conversion
		long[] result = new long[matched.size()];
		for (int i = 0; i < matched.size(); i++) {
			result[i] = matched.get(i);
		}

		return this.encodeMain(result);
	}

	/**
	 * Decrypt string to numbers
	 *
	 * @param hash
	 *            the encrypt string
	 * @return decryped numbers
	 */
	public String decodeHex(String hash) {
		String result = "";
		long[] numbers = this.decode(hash);

		for (long number : numbers) {
			result += Long.toHexString(number).substring(1);
		}

		return result;
	}

	private String encodeMain(long... numbers) {
		int numberHashInt = 0;
		for (int i = 0; i < numbers.length; i++) {
			numberHashInt += (numbers[i] % (i + 100));
		}
		String alphabet = this.alphabet;
		char ret = alphabet.toCharArray()[numberHashInt % alphabet.length()];
		long num;
		int sepsIndex, guardIndex;
		String buffer, retStr = ret + "";
		char guard;

		for (int i = 0; i < numbers.length; i++) {
			num = numbers[i];
			buffer = ret + this.salt + alphabet;

			alphabet = this.consistentShuffle(alphabet, buffer.substring(0, alphabet.length()));
			String last = this.hash(num, alphabet);

			retStr += last;

			if (i + 1 < numbers.length) {
				num %= ((int) last.toCharArray()[0] + i);
				sepsIndex = (int) (num % this.seps.length());
				retStr += this.seps.toCharArray()[sepsIndex];
			}
		}

		if (retStr.length() < this.minHashLength) {
			guardIndex = (numberHashInt + (int) (retStr.toCharArray()[0])) % this.guards.length();
			guard = this.guards.toCharArray()[guardIndex];

			retStr = guard + retStr;

			if (retStr.length() < this.minHashLength) {
				guardIndex = (numberHashInt + (int) (retStr.toCharArray()[2])) % this.guards.length();
				guard = this.guards.toCharArray()[guardIndex];

				retStr += guard;
			}
		}

		int halfLen = alphabet.length() / 2;
		while (retStr.length() < this.minHashLength) {
			alphabet = this.consistentShuffle(alphabet, alphabet);
			retStr = alphabet.substring(halfLen) + retStr + alphabet.substring(0, halfLen);
			int excess = retStr.length() - this.minHashLength;
			if (excess > 0) {
				int startPos = excess / 2;
				retStr = retStr.substring(startPos, startPos + this.minHashLength);
			}
		}

		return retStr;
	}

	private long[] decodeMain(String hash, String alphabet) {
		ArrayList<Long> ret = new ArrayList<Long>();
		int i = 0,value =3,len =2;
		String regexp = "[" + this.guards + "]";
		String hashBreakdown = hash.replaceAll(regexp, " ");
		String[] hashArray = hashBreakdown.split(" ");
		if (hashArray.length == value || hashArray.length == len) {
			i = 1;
		}
		hashBreakdown = hashArray[i];
		char lottery = hashBreakdown.toCharArray()[0];
		hashBreakdown = hashBreakdown.substring(1);
		hashBreakdown = hashBreakdown.replaceAll("[" + this.seps + "]", " ");
		hashArray = hashBreakdown.split(" ");

		String subHash, buffer;
		for (String aHashArray : hashArray) {
			subHash = aHashArray;
			buffer = lottery + this.salt + alphabet;
			alphabet = this.consistentShuffle(alphabet, buffer.substring(0, alphabet.length()));
			ret.add(this.unhash(subHash, alphabet));
		}

		// transform from List<Long> to long[]
		long[] arr = new long[ret.size()];
		for (int k = 0; k < arr.length; k++) {
			arr[k] = ret.get(k);
		}

		if (!this.encodeMain(arr).equals(hash)) {
			arr = new long[0];
		}

		return arr;
	}

	private String consistentShuffle(String alphabet, String salt) {
		if (salt.length() <= 0) {
			return alphabet;
		}

		char[] arr = salt.toCharArray();
		int ascVal, j;
		char tmp;
		for (int i = alphabet.length() - 1, v = 0, p = 0; i > 0; i--, v++) {
			v %= salt.length();
			ascVal = (int) arr[v];
			p += ascVal;
			j = (ascVal + v + p) % i;

			tmp = alphabet.charAt(j);
			alphabet = alphabet.substring(0, j) + alphabet.charAt(i) + alphabet.substring(j + 1);
			alphabet = alphabet.substring(0, i) + tmp + alphabet.substring(i + 1);
		}

		return alphabet;
	}

	private String hash(long input, String alphabet) {
		String hash = "";
		int alphabetLen = alphabet.length();
		char[] arr = alphabet.toCharArray();

		do {
			hash = arr[(int) (input % alphabetLen)] + hash;
			input /= alphabetLen;
		}
		while (input > 0);

		return hash;
	}

	private Long unhash(String input, String alphabet) {
		long number = 0, pos;
		char[] inputArr = input.toCharArray();

		for (int i = 0; i < input.length(); i++) {
			pos = alphabet.indexOf(inputArr[i]);
			number += pos * Math.pow(alphabet.length(), input.length() - i - 1);
		}

		return number;
	}

	@SuppressWarnings("unused")
	public static int checkedCast(long value) {
		int result = (int) value;
		if (result != value) {
			throw new IllegalArgumentException("Out of range: " + value);
		}
		return result;
	}

	public static String hashidsString(Integer num) {
		String random = String.valueOf(UUID.randomUUID());
		String saltStr = String.valueOf(
				RandomSecurityCode.getSecurityCode(10, RandomSecurityCode.SecurityCodeLevel.HARD, true)) + random;
		HaShiDsUtils hashids = new HaShiDsUtils(saltStr, num, DEFAULT_ALPHABET_TEST);
		String hash = hashids.encode(System.currentTimeMillis());
		return hash;
	}

}
