package com.application.base.operapi.tool.hbase.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : 孤狼
 * @NAME: HbaseTable
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HbaseTable {
	/**
	 * hbase table 的名称
	 * @return
	 */
	String table();
	
	/**
	 * hbase family 的名称
	 * @return
	 */
	String family() default "#DEFAULT_FAMILY#";
	
}
