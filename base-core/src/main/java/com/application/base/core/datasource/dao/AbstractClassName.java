package com.application.base.core.datasource.dao;

/**
 * @desc 获得传入的类名
 * @author 孤狼
 */
public abstract class AbstractClassName<T> {
	
	/**
	 * 获得顶级类的 class 名字
	 * @param t
	 * @return
	 */
	 public String getClassName(T t){
		 return t.getClass().getName();
	 }
	 
}
