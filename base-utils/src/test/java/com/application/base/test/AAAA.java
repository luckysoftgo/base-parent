package com.application.base.test;

import com.application.base.utils.common.BeanCopyUtils;
import com.application.base.utils.json.JsonConvertUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 测试地址.
 * @author 孤狼
 */
public class AAAA {

	public static void main(String[] args) {
		
		DDDD bean1 = new DDDD();
		bean1.setD1("d1");
		bean1.setD2(new BigDecimal("0.0023456"));
		
		bean1.setName("6789");
		bean1.setAge(20);
		
		bean1.setAaa("aaaa");
		bean1.setBbb(0.1d);
		
		bean1.setId(20);
		bean1.setUuid("uuid123456");
		bean1.setInfoDesc("11111111111");
		bean1.setCreateTime(new Date());
		
		System.out.println(JsonConvertUtils.toJson(bean1));
		System.out.println(JsonConvertUtils.toJsonHasNull(bean1));
		
		System.out.println("==========================================1==========================================");

		Test test = new Test();
		BeanCopyUtils.copyProperties(bean1,test);
		System.out.println(JsonConvertUtils.toJson(test));
		System.out.println(JsonConvertUtils.toJsonHasNull(test));
		
		String json = JsonConvertUtils.toJson(test);
		test = JsonConvertUtils.fromJson(json,Test.class);
		System.out.println("==========================================2==========================================");
		System.out.println(JsonConvertUtils.toJson(test));
		System.out.println(JsonConvertUtils.toJsonHasNull(test));
	}
}
