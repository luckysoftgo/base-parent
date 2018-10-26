package com.application.base.core.datasource.param;

import java.util.Map;

/**
 * @desc Param 对象
 * @author 孤狼
 */
public interface Param {
	
	/**
	 * map获得value
	 * @return
	 */
	Map<String, Object> get();
	
	/**
	 * 添加对象
	 * @param nvPair
	 * @return
	 */
	Param add(NvPair nvPair);
	
	/**
	 * 添加对象
	 * @param pair
	 * @return
	 */
	Param add(NvPair... pair);
	
	/**
	 * 添加对象
	 * @param params
	 * @return
	 */
	Param add(Map<String, Object> params);
	
	/**
	 * clean 对象
	 * @return
	 */
	Param clean();
}
