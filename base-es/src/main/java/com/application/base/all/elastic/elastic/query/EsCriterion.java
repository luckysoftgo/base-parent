package com.application.base.all.elastic.elastic.query;

import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;

/**
 * @NAME: EsCriterion
 * @DESC: 操作的表达式
 * @USER: 孤狼
 **/
public interface EsCriterion {
	
	/**
	 * 获得构建的查询结果集.
	 * @return
	 */
	public List<QueryBuilder> listBuilders();
	
}
