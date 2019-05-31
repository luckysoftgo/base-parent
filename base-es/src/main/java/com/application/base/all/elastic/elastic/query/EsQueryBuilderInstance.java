package com.application.base.all.elastic.elastic.query;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.ArrayList;
import java.util.List;

/**
 * @NAME: EsQueryBuilderInstance
 * @DESC: 查询实例
 * @USER: 孤狼
 **/
public class EsQueryBuilderInstance {
	/**
	 * 参数.
	 */
	private int size = Integer.MAX_VALUE;
	private int from = 0;
	private String asc;
	private String desc;
	
	/**
	 * 查询条件容器
	 */
	private List<EsCriterion> mustCriterions = new ArrayList<>();
	/**
	 * 可能条件集合
	 */
	private List<EsCriterion> shouldCriterions = new ArrayList<>();
	/**
	 * 不可能条件集合
	 */
	private List<EsCriterion> mustNotCriterions = new ArrayList<>();
	
	/**
	 * 构造builder
	 */
	public QueryBuilder listBuilders() {
		int count = mustCriterions.size() + shouldCriterions.size() + mustNotCriterions.size();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		QueryBuilder queryBuilder = null;
		if (count >= 1) {
			/**
			 * must容器
			 */
			if (!mustCriterions.isEmpty()) {
				for (EsCriterion criterion : mustCriterions) {
					for (QueryBuilder builder : criterion.listBuilders()) {
						queryBuilder = boolQueryBuilder.must(builder);
					}
				}
			}
			/**
			 * should容器
			 */
			if (!shouldCriterions.isEmpty()) {
				for (EsCriterion criterion : shouldCriterions) {
					for (QueryBuilder builder : criterion.listBuilders()) {
						queryBuilder = boolQueryBuilder.should(builder);
					}
					
				}
			}
			/**
			 * must not 容器
			 */
			if (!mustNotCriterions.isEmpty()) {
				for (EsCriterion criterion : mustNotCriterions) {
					for (QueryBuilder builder : criterion.listBuilders()) {
						queryBuilder = boolQueryBuilder.mustNot(builder);
					}
				}
			}
			return queryBuilder;
		} else {
			return QueryBuilders.matchAllQuery();
		}
	}
	
	/**
	 * 增加简单条件表达式
	 */
	public EsQueryBuilderInstance must(EsCriterion criterion){
		if(criterion!=null){
			mustCriterions.add(criterion);
		}
		return this;
	}
	/**
	 * 增加简单条件表达式
	 */
	public EsQueryBuilderInstance should(EsCriterion criterion){
		if(criterion!=null){
			shouldCriterions.add(criterion);
		}
		return this;
	}
	/**
	 * 增加简单条件表达式
	 */
	public EsQueryBuilderInstance mustNot(EsCriterion criterion){
		if(criterion!=null){
			mustNotCriterions.add(criterion);
		}
		return this;
	}
	
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public String getAsc() {
		return asc;
	}
	
	public void setAsc(String asc) {
		this.asc = asc;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public int getFrom() {
		return from;
	}
	
	public void setFrom(int from) {
		this.from = from;
	}
}
