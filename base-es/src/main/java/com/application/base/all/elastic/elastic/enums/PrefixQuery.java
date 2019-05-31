package com.application.base.all.elastic.elastic.enums;

/**
 * @NAME: PrefixQuery
 * @DESC: 查询前缀
 * @USER: 孤狼
 **/
public enum  PrefixQuery {
	
	/**
	 * all 查询.
	 */
	ALL,
	
	/**
	 * match 查询.
	 */
	MATCH,
	
	/**
	 * multiMatch 查询.
	 */
	MULTIMATCH,
	
	/**
	 * ids 查询.
	 */
	IDS,
	
	/**
	 * term 查询(int,long,float,double,boolean).
	 */
	TERM,
	
	/**
	 * terms 查询(int,long,float,double,boolean).
	 */
	TERMS,
	
	/**
	 * fuzzy 模糊查询.
	 */
	FUZZY,
	
	/**
	 * prefix 前缀查询.
	 */
	PREFIX,
	
	/**
	 * range 范围查询.
	 */
	RANGE,
	
	/**
	 * regexp 正则表达式.
	 */
	REGEXP,
	
	/**
	 * 查询字符串.
	 */
	QUERY_STRING,
	
	/**
	 * spanTerm 跨项查询.
	 */
	SPAN_TERM,
	
	/**
	 * moreLike 模糊查询.
	 */
	LIKE,
	
	/**
	 * exists 是否存在查询.
	 */
	EXISTS,
	,
	
}
