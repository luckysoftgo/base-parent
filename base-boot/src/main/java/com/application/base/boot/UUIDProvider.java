package com.application.base.boot;

import java.lang.management.ManagementFactory;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @desc UUID生产器
 * @author 孤狼
 */
public class UUIDProvider {
	
	private static final int UUID_BIN_LEN = 10;
	private static final int UUID_STR_LEN = UUID_BIN_LEN * 2;
	private static long procId = -1;
	private static long threadId = -1;
	
	static String[] numbers = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	public static void main(String[] args) throws Exception {
		System.out.println(getId());
		System.out.println(uuid());
		Map<String, Object> result = getResult();
		System.out.println(result.get("uuid"));
    	System.out.println(result.get("number"));
    	System.out.println(result.get("letter"));
	}
	
	/**
	 * 产生一个32位的GUID
	 * 
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * 返回数字 .
	 * @param uuid
	 * @return
	 */
	public static String number(String uuid) {
		List<String> nums = Arrays.asList(numbers);
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < uuid.length(); i++) {
			String tmp = uuid.charAt(i) + "";
			if (nums.contains(tmp)) {
				buffer.append(tmp);
			}
		}
		return buffer.toString();
	}

	/**
	 * 返回字母.
	 * @param uuid
	 * @return
	 */
	public static String letter(String uuid) {
		List<String> nums = Arrays.asList(numbers);
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < uuid.length(); i++) {
			String tmp = uuid.charAt(i) + "";
			if (!nums.contains(tmp)) {
				buffer.append(tmp);
			}
		}
		return buffer.toString();
	}

	/**
	 * 获得uuid的组合:返回uuid 和组成 uuid 的: 数字 和 字母.
	 * @return
	 */
	public static Map<String, Object> getResult() {
		int size = 16;
		Map<String, Object> result = new HashMap<String, Object>(size);
		String uuid = uuid();
		result.put("uuid", uuid);
		result.put("number", number(uuid));
		result.put("letter", letter(uuid));
		return result;
	}

	/**
	 * 获取32位GUID
	 * @return
	 */
	public static String getId() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			UUID uuid = UUID.randomUUID();
			String guidStr = uuid.toString();
			md.update(guidStr.getBytes(), 0, guidStr.length());
			return new BigInteger(1, md.digest()).toString(16);
		}
		catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * 主键生成方式,年月日时分秒毫秒的时间戳+四位随机数保证不重复
	 */
	public static Long getNumId() {
		// 当前系统时间戳精确到毫秒
		Long simple = System.currentTimeMillis();
		// 三位随机数
		int random = new Random().nextInt(900000) + 100000;
		// 为变量赋随机值100000-99999;
		return Long.decode((simple.toString() + random));
	}

	/**
	 * 主键生成方式,年月日时分秒毫秒的时间戳+四位随机数保证不重复
	 */
	public static String getNumStringId() {
		// 当前系统时间戳精确到毫秒
		Long simple = System.currentTimeMillis();
		// 三位随机数
		int random = new Random().nextInt(900000) + 100000;
		// 为变量赋随机值100000-99999;
		return simple.toString() + random;
	}
	
	
	/**
	 * 生成20字节的UUID字符串,不含'-'字符.
	 * TODO 未追加jvmId,ip和mac信息,需要完善.
	 * @return
	 */
	public static String genUuid() {
		StringBuilder buf = new StringBuilder();
		buf.append(UUID.randomUUID());
		buf.append(genAddon(null));
		for (int i = buf.length() - 1; i >= 0; i--) {
			if (buf.charAt(i) == '-') {
				buf.deleteCharAt(i);
			}
		}
		while (buf.length() > UUID_STR_LEN) {
			buf.deleteCharAt(UUID_STR_LEN);
		}
		while (buf.length() < UUID_STR_LEN) {
			buf.append('0');
		}
		threadId = Thread.currentThread().getId();
		return new String(buf);
	}
	
	/**
	 * 根据虚拟机进程名、线程ID生成8个字节的附加串.
	 *
	 * @return
	 */
	private static String genAddon(Integer len) {
		StringBuilder buf = new StringBuilder();
		buf.append('/');
		buf.append(threadId);
		
		buf.append('/');
		buf.append(procId);
		String bufStr = new String(buf);
		
		String addonStr = Integer.toHexString(bytesToInt(bufStr.getBytes()));
		if (null==len){
			len =10;
		}
		String zero = "0";
		while (addonStr.length() < len) {
			addonStr = zero + addonStr;
		}
		addonStr = "-" + addonStr;
		
		return addonStr;
	}
	
	/**
	 * 字节到 Int
	 * @param bytes
	 * @return
	 */
	private static int bytesToInt(final byte[] bytes) {
		int hVal = 0;
		if (bytes != null) {
			for (int i = 0; i < bytes.length; i++) {
				hVal += (hVal << 1) + (hVal << 24);
				hVal ^= bytes[i] & 0x0ff;
			}
		}
		return hVal;
	}
	
	/**
	 * 将UUID字符串转成二进制数据.
	 *
	 * @param uuidStr
	 * @return
	 */
	public static byte[] toUuidBinary(final String uuidStr) {
		if (uuidStr == null) {
			throw new RuntimeException("The parameter uuidStr is null.");
		}
		if (uuidStr.length() != UUID_STR_LEN) {
			throw new RuntimeException(
					"The length[" + uuidStr.length() + "] of parameter uuidStr is not " + UUID_STR_LEN);
		}
		byte[] uuidBinary = new byte[UUID_BIN_LEN];
		for (int i = 0; i < UUID_BIN_LEN; i++) {
			uuidBinary[i] = (byte) (Short.parseShort(uuidStr.substring(i << 1, (i + 1) << 1), 16) & 0x0ff);
		}
		
		return uuidBinary;
	}
	
	/**
	 * 将UUID二进制数据转换成字符串
	 *
	 * @param uuidBinary
	 * @return
	 */
	public static String toUuidString(final byte[] uuidBinary) {
		if (uuidBinary == null) {
			throw new RuntimeException("parameter uuidBinary is null.");
		}
		if (uuidBinary.length != UUID_BIN_LEN) {
			throw new RuntimeException(
					"The length[" + uuidBinary.length + "] of parameter uuidBinary is not " + UUID_BIN_LEN);
		}
		StringBuilder buf = new StringBuilder();
		int len = 2;
		for (int i = 0; i < UUID_BIN_LEN; i++) {
			String str = Integer.toHexString(uuidBinary[i] & 0x0ff);
			String zero = "0";
			while (str.length() < len) {
				str = zero + str;
			}
			buf.append(str.substring(0, 2));
		}
		return new String(buf);
	}
	
	static {
		try {
			final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
			procId = Long.parseLong(jvmName.substring(0, jvmName.indexOf('@')));
			threadId = Thread.currentThread().getId();
			System.out.println(procId + "===" + threadId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
