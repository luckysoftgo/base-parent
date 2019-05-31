package com.application.base.all.elastic.elastic.query;

import com.application.base.all.elastic.elastic.enums.PrefixQuery;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @NAME: EsQueryBuilders
 * @DESC:
 * @USER: 孤狼
 **/
public class EsQueryBuilders{
	
	/**
	 * 返回的结果集合.
	 */
	private List<QueryBuilder> resultList = new ArrayList<>();
	
	/**
	 * 功能描述：match 查询
	 * @param field 字段名
	 * @param value 值
	 */
	public EsQueryBuilders match(String field, Object value) {
		resultList.add(new EsSimpleExpression(field, value, PrefixQuery.MATCH).toBuilder());
		return this;
	}
	
	/**
	 * 功能描述：mutilMatch 查询
	 * @param fields 字段名
	 * @param value 值
	 */
	public EsQueryBuilders mutilMatch(String[] fields,Object value) {
		resultList.add(new EsSimpleExpression(fields, value, PrefixQuery.MULTIMATCH).toBuilder());
		return this;
	}
	
	/**
	 * 功能描述：ids 查询
	 * @param values 值
	 */
	public EsQueryBuilders ids(String[] values) {
		resultList.add(new EsSimpleExpression(values, PrefixQuery.IDS).toBuilder());
		return this;
	}
	
	
	/**
	 * 功能描述：Term 查询
	 * @param field 字段名
	 * @param value 值
	 */
	public EsQueryBuilders term(String field, Object value) {
		resultList.add(new EsSimpleExpression(field, value, PrefixQuery.TERM).toBuilder());
		return this;
	}
	
	/**
	 * 功能描述：Terms 查询
	 * @param field 字段名
	 * @param values 集合值
	 */
	public EsQueryBuilders terms(String field, Collection<Object> values) {
		resultList.add(new EsSimpleExpression(field, values,PrefixQuery.TERMS).toBuilder());
		return this;
	}
	
	/**
	 * 功能描述：fuzzy 查询
	 * @param field 字段名
	 * @param value 值
	 */
	public EsQueryBuilders fuzzy(String field, Object value) {
		resultList.add(new EsSimpleExpression(field, value, PrefixQuery.FUZZY).toBuilder());
		return this;
	}
	
	/**
	 * 功能描述：prefix 查询
	 * @param field 字段名
	 * @param value 值
	 */
	public EsQueryBuilders prefix(String field, Object value) {
		resultList.add(new EsSimpleExpression(field, value, PrefixQuery.PREFIX).toBuilder());
		return this;
	}
	
	/**
	 * 功能描述：Range 查询
	 * @param valFrom 起始值
	 * @param valTo 末尾值
	 */
	public EsQueryBuilders range(String field, Object valFrom, Object valTo) {
		resultList.add(new EsSimpleExpression(field,PrefixQuery.RANGE, valFrom, valTo).toBuilder());
		return this;
	}
	
	/**
	 * 功能描述：regexp 查询
	 * @param value 末尾值
	 */
	public EsQueryBuilders regexp(String field,Object value) {
		resultList.add(new EsSimpleExpression(field, value, PrefixQuery.REGEXP).toBuilder());
		return this;
	}
	
	/**
	 * 功能描述：queryString 查询
	 * @param value 查询语句
	 */
	public EsQueryBuilders queryStr(String value) {
		resultList.add(new EsSimpleExpression(value, PrefixQuery.QUERY_STRING).toBuilder());
		return this;
	}
	
	/**
	 * 功能描述：spanTerm 查询
	 * @param value 查询语句
	 */
	public EsQueryBuilders spanTerm(String field,Object value) {
		resultList.add(new EsSimpleExpression(field,value, PrefixQuery.SPAN_TERM).toBuilder());
		return this;
	}
	
	/**
	 * 功能描述：moreLike 查询
	 * @param values 查询语句
	 */
	public EsQueryBuilders moreLike(Object[] values) {
		resultList.add(new EsSimpleExpression(values, PrefixQuery.LIKE).toBuilder());
		return this;
	}
	
	/**
	 * 功能描述：exists 查询
	 * @param field 查询语句
	 */
	public EsQueryBuilders exists(String field) {
		resultList.add(new EsSimpleExpression(field,PrefixQuery.EXISTS).toBuilder());
		return this;
	}

	public List<QueryBuilder> listBuilders() {
		return resultList;
	}
}
