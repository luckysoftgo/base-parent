package com.application.base.operapi.tool.hbase.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author : 孤狼
 * @NAME: HbaseField
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited()
public @interface HbaseField {
	
	/**
	 * 介绍
	 * @return
	 */
	String desc();
}
