package com.application.base.util.sql;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author : 孤狼
 * @NAME: PkProvider
 * @DESC: 主键生成策略.
 **/
public interface PkProvider {
	
	static String[] numbers = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	
	/**
	 * 生成主键.
	 * @return
	 */
	default String defaultStrPk(){
		return UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
	}
	
	/**
	 * 生成主键.
	 * @return
	 */
	default String getStrDefPk(int len){
		String uuid = UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
		return uuid.substring(0,len);
	}
	
	/**
	 * 生成主键.
	 * @return
	 */
	default Integer defaultIntPk(){
		String number = number();
		return Integer.parseInt(number.substring(0,9));
	}
	
	/**
	 * 生成主键.
	 * @return
	 */
	default Integer getIntDefPk(int len){
		if (len>=10){
			len = 9;
		}
		String number = number();
		number = number.substring(0,9);
		number = number.substring(0,len);
		return Integer.parseInt(number);
	}
	
	
	
	/**
	 * 生成主键.
	 * @return
	 */
	static String defaultPk(){
		return UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
	}

	/**
	 * 返回数字 .
	 * @return
	 */
	static String number() {
		String uuid = defaultPk();
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
	 * @return
	 */
	static String letter() {
		String uuid = defaultPk();
		List<String> nums = Arrays.asList(numbers);
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < uuid.length(); i++) {
			String tmp = uuid.charAt(i) + "";
			if (!nums.contains(tmp)) {
				buffer.append(tmp);
			}
		}
		return buffer.toString().toLowerCase();
	}
	
	/**
	 * 生成主键.
	 * @return
	 */
	public String getStrPrimKey();
	
	/**
	 * 生成主键.
	 * @return
	 */
	public Integer getIntPrimKey();
	
}

