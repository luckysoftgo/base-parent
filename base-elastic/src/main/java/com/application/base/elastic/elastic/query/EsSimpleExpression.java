package com.application.base.elastic.elastic.query;

import com.application.base.elastic.elastic.enums.PrefixQuery;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @NAME: EsSimpleExpression
 * @DESC:
 * @USER: 孤狼
 * @DATE: 2019年5月31日
 **/
public class EsSimpleExpression {
	/**
	 * 属性名
	 */
	private String fieldName;
	/**
	 * 属性名
	 */
	private String[] fieldNames;
	
	/**
	 * 对应值
	 */
	private Object value;
	/**
	 * 对应值
	 */
	private Object[] values;
	/**
	 * 计算符
	 */
	private PrefixQuery operator;
	
	private Object valFrom;
	private Object valTo;
	
	public EsSimpleExpression(String fieldName, PrefixQuery operator) {
		this.fieldName = fieldName;
		this.operator = operator;
	}
	
	public EsSimpleExpression(Object value, PrefixQuery operator) {
		this.value = value;
		this.operator = operator;
	}
	
	public EsSimpleExpression(Object[] values, PrefixQuery operator) {
		this.values = values;
		this.operator = operator;
	}
	
	public EsSimpleExpression(String fieldName, Object value, PrefixQuery operator) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}
	
	public EsSimpleExpression(String[] fieldNames, Object value, PrefixQuery operator) {
		this.fieldNames = fieldNames;
		this.value = value;
		this.operator = operator;
	}
	
	public EsSimpleExpression(String fieldName, Object[] values, PrefixQuery operator) {
		this.fieldName = fieldName;
		this.values = values;
		this.operator = operator;
	}
	
	public EsSimpleExpression(String fieldName, PrefixQuery operator, Object valFrom, Object valTo) {
		this.fieldName = fieldName;
		this.operator = operator;
		this.valFrom = valFrom;
		this.valTo = valTo;
	}
	
	public EsSimpleExpression(String fieldName, String[] fieldNames, Object value, Object[] values,
	                          PrefixQuery operator, Object valFrom, Object valTo) {
		this.fieldName = fieldName;
		this.fieldNames = fieldNames;
		this.value = value;
		this.values = values;
		this.operator = operator;
		this.valFrom = valFrom;
		this.valTo = valTo;
	}
	
	public QueryBuilder toBuilder() {
		QueryBuilder builder = null;
		switch (operator) {
			case MATCH:
				builder = QueryBuilders.matchQuery(fieldName, value);
				break;
			case MULTIMATCH:
				builder = QueryBuilders.multiMatchQuery(value,fieldNames);
				break;
			case IDS:
				String[] ids= (String[])values;
				builder = QueryBuilders.idsQuery(ids);
				break;
			case TERM:
				builder = QueryBuilders.termQuery(fieldName, value);
				break;
			case TERMS:
				builder = QueryBuilders.termsQuery(fieldName, values);
				break;
			case FUZZY:
				builder = QueryBuilders.fuzzyQuery(fieldName, value);
				break;
			case PREFIX:
				builder = QueryBuilders.prefixQuery(fieldName, value.toString());
				break;
			case RANGE:
				builder = QueryBuilders.rangeQuery(fieldName).from(valFrom).to(valTo).includeLower(true).includeUpper(true);
				break;
			case REGEXP:
				builder = QueryBuilders.regexpQuery(fieldName,value.toString());
				break;
			case QUERY_STRING:
				builder = QueryBuilders.queryStringQuery(value.toString());
				break;
			case SPAN_TERM:
				builder = QueryBuilders.spanTermQuery(fieldName,value.toString());
				break;
			case LIKE:
				String[] likeTexts= (String[])values;
				builder = QueryBuilders.moreLikeThisQuery(likeTexts);
				break;
			case EXISTS:
				builder = QueryBuilders.existsQuery(fieldName);
				break;
			default:
				builder = QueryBuilders.matchAllQuery();
				break;
		}
		return builder;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String[] getFieldNames() {
		return fieldNames;
	}
	
	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public Object[] getValues() {
		return values;
	}
	
	public void setValues(Object[] values) {
		this.values = values;
	}
	
	public PrefixQuery getOperator() {
		return operator;
	}
	
	public void setOperator(PrefixQuery operator) {
		this.operator = operator;
	}
	
	public Object getValFrom() {
		return valFrom;
	}
	
	public void setValFrom(Object valFrom) {
		this.valFrom = valFrom;
	}
	
	public Object getValTo() {
		return valTo;
	}
	
	public void setValTo(Object valTo) {
		this.valTo = valTo;
	}
}
