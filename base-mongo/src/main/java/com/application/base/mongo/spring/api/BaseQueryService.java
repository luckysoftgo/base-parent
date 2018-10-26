package com.application.base.mongo.spring.api;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 构建顶级查询抽象实现
 * @author rocky
 *
 * @param <T>
 */
public abstract class BaseQueryService<T> {
	
	/**
	 * 创建基础查询 Query
	 * @param t : Object . 
	 * @param tag :  .
	 * @param orders : 排序 .
	 * @return
	 */
	public Query buildBaseQuery(T t, String tag, List<Sort.Order> orders) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		List<Criteria> listC = new ArrayList<Criteria>();
		Field[] fields = t.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				Object value = field.get(t);
				if ("gt".equals(tag)) {
					listC.add(Criteria.where(field.getName()).gt(value));
				}
				else if ("lt".equals(tag)) {
					listC.add(Criteria.where(field.getName()).lt(value));
				}
				else if ("eq".equals(tag)) {
					listC.add(Criteria.where(field.getName()).is(value));
				}
				else if ("gte".equals(tag)) {
					listC.add(Criteria.where(field.getName()).gte(value));
				}
				else if ("lte".equals(tag)) {
					listC.add(Criteria.where(field.getName()).lte(value));
				}
				// like 查询
				else if ("regex".equals(tag)) {
					listC.add(Criteria.where(field.getName()).regex(value.toString()));
				}
				else if ("in".equals(tag)) {
					listC.add(Criteria.where(field.getName()).in(new Object[] { value }));
				}
				else if ("nin".equals(tag)) {
					listC.add(Criteria.where(field.getName()).nin(new Object[] { value }));
				}
				else if ("ne".equals(tag)) {
					listC.add(Criteria.where(field.getName()).ne(value));
				}
				else {
					listC.add(Criteria.where(field.getName()).is(value));
				}
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if ((listC != null) && (listC.size() > 0) && (listC.size() > 0)) {
			Criteria[] cs = new Criteria[listC.size()];
			criteria.andOperator((Criteria[]) listC.toArray(cs));
			query.addCriteria(criteria);
		}
		Sort sort = null;
		if ((orders != null) && (orders.size() > 0)) {
			sort = new Sort(orders);
		}
		if (sort != null) {
			query = query.with(sort);
		}
		return query;
	}

	/**
	 * 创建基础查询 Query
	 * @param params : 参数key-value集合
	 * @param tag : 操作符
	 * @param orders : 排序
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Query buildBaseQuery(Map<String, Object> params, String tag, List<Sort.Order> orders) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		List<Criteria> listC = new ArrayList<Criteria>();
		for (Map.Entry entry : params.entrySet()) {
			String key = (String) entry.getKey();
			Object value = entry.getValue();
			if ("gt".equals(tag)) {
				listC.add(Criteria.where(key).gt(value));
			}
			else if ("lt".equals(tag)) {
				listC.add(Criteria.where(key).lt(value));
			}
			else if ("eq".equals(tag)) {
				listC.add(Criteria.where(key).is(value));
			}
			else if ("gte".equals(tag)) {
				listC.add(Criteria.where(key).gte(value));
			}
			else if ("lte".equals(tag)) {
				listC.add(Criteria.where(key).lte(value));
			}
			// like 查询
			else if ("regex".equals(tag)) {
				listC.add(Criteria.where(key).regex(value.toString()));
			}
			else if ("in".equals(tag)) {
				listC.add(Criteria.where(key).in(new Object[] { value }));
			}
			else if ("nin".equals(tag)) {
				listC.add(Criteria.where(key).nin(new Object[] { value }));
			}
			else if ("ne".equals(tag)) {
				listC.add(Criteria.where(key).ne(value));
			}
			else {
				listC.add(Criteria.where(key).is(value));
			}
		}
		if ((listC != null) && (listC.size() > 0) && (listC.size() > 0)) {
			Criteria[] cs = new Criteria[listC.size()];
			criteria.andOperator((Criteria[]) listC.toArray(cs));
			query.addCriteria(criteria);
		}
		Sort sort = null;
		if ((orders != null) && (orders.size() > 0)) {
			sort = new Sort(orders);
		}
		if (sort != null) {
			query = query.with(sort);
		}
		return query;
	}

	
	/**
	 * 修改的创建
	 * @param t
	 * @return
	 */
	public Update buildBaseUpdate(T t) {
		Update update = new Update();
		Field[] fields = t.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				Object value = field.get(t);
				if ((value != null) && (!"_id".equals(field.getName()))) {
					update.set(field.getName(), value);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return update;
	}
	
	/**
	 * 修改的创建
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Update buildBaseUpdate(Map<String, Object> params) {
		Update update = new Update();
		for (Map.Entry entry : params.entrySet()) {
			if (!"_id".equals((String) entry.getKey())) {
				update.set((String) entry.getKey(), entry.getValue());
			}
		}
		return update;
	}

	/**
	 * 创建查询的集合
	 * @param param
	 * @param tag
	 * @return
	 */
	public Criteria createCriteria(Map<String, Object> param, String tag) {
		Criteria result = new Criteria();
		List<Criteria> listC = new ArrayList<Criteria>();
		Set<String> params = null;
		if ((param != null) && (param.size() > 0)) {
			params = param.keySet();
			for (String str : params) {
				if ("gt".equals(tag)) {
					listC.add(Criteria.where(str).gt(param.get(str)));
				}
				else if ("lt".equals(tag)) {
					listC.add(Criteria.where(str).lt(param.get(str)));
				}
				else if ("eq".equals(tag)) {
					listC.add(Criteria.where(str).is(param.get(str)));
				}
				else if ("gte".equals(tag)) {
					listC.add(Criteria.where(str).gte(param.get(str)));
				}
				else if ("lte".equals(tag)) {
					listC.add(Criteria.where(str).lte(param.get(str)));
				}
				// like 查询
				else if ("regex".equals(tag)) {
					listC.add(Criteria.where(str).regex(param.get(str).toString()));
				}
				else if ("in".equals(tag)) {
					listC.add(Criteria.where(str).in(new Object[] { param.get(str) }));
				}
				else if ("nin".equals(tag)) {
					listC.add(Criteria.where(str).nin(new Object[] { param.get(str) }));
				}
				else if ("ne".equals(tag)) {
					listC.add(Criteria.where(str).ne(param.get(str)));
				}
				else {
					listC.add(Criteria.where(str).is(param.get(str)));
				}
			}
		}
		if (listC.size() > 0) {
			Criteria[] cs = new Criteria[listC.size()];
			result.andOperator((Criteria[]) listC.toArray(cs));
		}
		return result;
	}

	
	@SuppressWarnings("rawtypes")
	public Criteria createCriteria(Map<String, Object> gtMap, Map<String, Object> ltMap, Map<String, Object> eqMap,
			Map<String, Object> gteMap, Map<String, Object> lteMap, Map<String, String> regexMap,
			Map<String, Collection> inMap, Map<String, Object> neMap,Map<String, Object> ninMap) {
		Criteria result = new Criteria();
		List<Criteria> listC = new ArrayList<Criteria>();
		Set<String> params = null;
		if ((gtMap != null) && (gtMap.size() > 0)) {
			params = gtMap.keySet();
			for (String str : params) {
				listC.add(Criteria.where(str).gt(gtMap.get(str)));
			}
		}
		if ((ltMap != null) && (ltMap.size() > 0)) {
			params = ltMap.keySet();
			for (String str : params) {
				listC.add(Criteria.where(str).lt(ltMap.get(str)));
			}
		}
		if ((eqMap != null) && (eqMap.size() > 0)) {
			params = eqMap.keySet();
			for (String str : params) {
				listC.add(Criteria.where(str).is(eqMap.get(str)));
			}
		}
		if ((gteMap != null) && (gteMap.size() > 0)) {
			params = gteMap.keySet();
			for (String str : params) {
				listC.add(Criteria.where(str).gte(gteMap.get(str)));
			}
		}
		if ((lteMap != null) && (lteMap.size() > 0)) {
			params = lteMap.keySet();
			for (String str : params) {
				listC.add(Criteria.where(str).lte(lteMap.get(str)));
			}
		}

		if ((regexMap != null) && (regexMap.size() > 0)) {
			params = regexMap.keySet();
			for (String str : params) {
				listC.add(Criteria.where(str).regex((String) regexMap.get(str)));
			}
		}

		if ((inMap != null) && (inMap.size() > 0)) {
			params = inMap.keySet();
			for (String str : params) {
				listC.add(Criteria.where(str).in((Collection) inMap.get(str)));
			}
		}
		if ((neMap != null) && (neMap.size() > 0)) {
			params = neMap.keySet();
			for (String str : params) {
				listC.add(Criteria.where(str).ne(neMap.get(str)));
			}
		}
		if ((ninMap != null) && (ninMap.size() > 0)) {
			params = ninMap.keySet();
			for (String str : params) {
				listC.add(Criteria.where(str).nin(ninMap.get(str)));
			}
		}
		if (listC.size() > 0) {
			Criteria[] cs = new Criteria[listC.size()];
			result.andOperator((Criteria[]) listC.toArray(cs));
		}
		return result;
	}
}