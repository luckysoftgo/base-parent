package com.application.base.mongo.morphia.api;

import com.application.base.utils.page.PageView;
import com.mongodb.DBCollection;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.dao.DAO;
import org.mongodb.morphia.query.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * MongoDAO的操作基类
 * @author 孤狼
 */
public abstract class BaseMonGoDAO<T, K> implements DAO<T, K> {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 框架基础类.
	 */
	private final BasicDAO<T, K> baseDao;
	/**
	 *显示.
	 */
	private final Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseMonGoDAO() {
		DataStore store = getClass().getAnnotation(DataStore.class);
		if(store == null){
			throw new ExceptionInInitializerError("must supply DataStore annotation for this constructor");
		}
		String poolName = store.tagValue();
		String dbName = store.mongoDBName();
		logger.info("poolName="+poolName+",dbName="+dbName);
		Datastore ds = null;
		if(dbName.isEmpty()){
			ds = BaseMonGoHelper.getDatastore(poolName);
		} else{
			ds = BaseMonGoHelper.getDatastore(poolName, dbName);
		}
		if(ds == null){
			throw new ExceptionInInitializerError("don't find the Datastore for poolName ["+poolName+"] and file ["+dbName+"]");
		}

		ParameterizedType type = null;
		Type temp = getClass().getGenericSuperclass();
		do{
			if(temp instanceof ParameterizedType){
				type = (ParameterizedType) temp;
				break;
			}
			if(!(temp instanceof Class<?>)){
				break;
			}
			temp = ((Class<?>)temp).getGenericSuperclass();
			if(temp == Object.class){
				break;
			}
		} while(temp != null);

		if(type == null){
			throw new ExceptionInInitializerError("can't find the associated generic type:"+temp.getClass().getName());
		}
		clazz = (Class<T>)type.getActualTypeArguments()[0]; 
		baseDao = new BasicDAO<T, K>(clazz, ds);
	}

	@SuppressWarnings("unchecked")
	public BaseMonGoDAO(Datastore ds) {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
		baseDao = new BasicDAO<T, K>(clazz, ds);
	}

	/**
	 * 获取自增ID的下一个参数值, 需要子类实现
	 * @return
	 */
	public abstract long getNextIdValue();

	@Override
	public Datastore getDatastore() {
		return baseDao.getDatastore();
	}

	@Override
	public DBCollection getCollection() {
        return baseDao.getCollection();
    }

	@Override
	public UpdateOperations<T> createUpdateOperations() {
        return baseDao.createUpdateOperations();
    }

	/**
	 * 查询
	 * 查询条件,map中的key值为字段名(下面用col代表)加上一些后缀，代表意义如下： 
	 * 
	 *     coloum         ：     =         value
	 *     coloum_@like   ： like      value
	 *     coloum_@begin  :  >=        value
	 *     coloum_@end    :  <=        value
	 *     coloum_@contain: 包含       value    (此字段值为数组)
	 *     coloum_@in     : 在其中      value    (map中的value值为数组)
	 * 
	 * coloum : mongo中collections的列;
	 * "_" : 是链接符号,必须写上;
	 * @ 标示组装分割符号;
	 * 
	 * Map<String, String> params = new  HashMap<String,String>();
	 * params.put("id_@like", "j") : 查询 id 中包含 "j" 的所有的集合.
	 * 
	 * @param params
	 * @param offset:从第几条开始
	 * @param limit:可以显示多少条
	 * @return
	 */
	public List<T> query(Map<String, Object> params, int offset, int limit) {
		Query<T> query = getQuery(params).offset(offset).limit(limit);
		return find(query).asList();
	}
	
	public long count(Map<String, Object> params) {
		Query<T> query = getQuery(params);
		return find(query).countAll();
	}
	
	/**
	 * mongo 分页显示.
	 * @param pageView:分页对象.
	 * @param params:查询参数
	 * @return
	 */
	public PageView findPage(PageView pageView,Map<String, Object> params){
		int pageNow = pageView.getPageNow();
		int offset=0,limit=0;
		if (pageNow == 1) {
			offset = 0 ;
		}else {
			offset = (pageView.getPageNow()-1) * pageView.getPageSize();
		}
		limit = pageView.getPageSize();
		pageView.setRecords(query(params, offset, limit));
		pageView.setRowCount(count(params));
		return pageView;
	}
	
	
	/**
	 * 返回QueryResults
	 */
	@Override
	public QueryResults<T> find(Query<T> query) {
		return baseDao.find(query);
	}

	/**
	 * 查询的编写.
	 * 
	 * @param params
	 * @return
	 */
	public Query<T> getQuery(Map<String, Object> params) {
		Query<T> query = createQuery();
		if(params != null){
			for(Entry<String, ?> entity : params.entrySet()) {
				String name = entity.getKey();
				Object value = entity.getValue();
				String[] colOpe = name.split("_@");
				if(colOpe.length >= 2){
					if("like".equals(colOpe[1])){
						query.filter(colOpe[0], java.util.regex.Pattern.compile(value.toString()));
					}else if("begin".equals(colOpe[1])){
						query.field(colOpe[0]).greaterThanOrEq(value);
					}else if("end".equals(colOpe[1])){
						query.field(colOpe[0]).lessThanOrEq(value);
					}else if("not".equals(colOpe[1])){
						query.field(colOpe[0]).notEqual(value);
					}else if("contain".equals(colOpe[1])){
						query.field(colOpe[0]).hasThisOne(value);
					}else if("in".equals(colOpe[1])){
						query.field(colOpe[0]).hasAnyOf((Iterable<?>)value);
					}else if("noneof".equals(colOpe[1])){
						query.field(colOpe[0]).hasNoneOf((Iterable<?>)value);
						//或者查询
					}else if("or".equals(colOpe[1])){
						@SuppressWarnings("unchecked")
						Map<String,Object> temp = (Map<String,Object>)value;
						if(temp!=null && temp.size()>0){
							List<CriteriaContainer> collect = new ArrayList<CriteriaContainer>(); 
							for(Entry<String,Object> entry : temp.entrySet()){
								collect.add(query.criteria(entry.getKey()).equal(entry.getValue()));
							}
							query.or(collect.toArray(new CriteriaContainer[collect.size()]));
						}
					}
				}else{
					if("order".equals(name)){
						query.order(value.toString());
					}else{
						query.field(colOpe[0]).equal(value);
					}
				}
			}
		}
		return query;
	}

	@Override
	public Query<T> createQuery() {
		return baseDao.createQuery();
	}

	@Override
	public long count() {
		return baseDao.count();
	}

	@Override
	public long count(Query<T> query) {
		return baseDao.count(query);
	}

	@Override
	public long count(String key, Object value) {
		return baseDao.count(key, value);
	}

	@Override
	public WriteResult delete(T entity) {
		return baseDao.delete(entity);
	}

	@Override
	public WriteResult delete(T entity, WriteConcern writeConcern) {
		return baseDao.delete(entity, writeConcern);
	}

	@Override
	public WriteResult deleteById(K id) {
		return baseDao.deleteById(id);
	}

	@Override
	public WriteResult deleteByQuery(Query<T> query) {
		return baseDao.deleteByQuery(query);
	}

	@Override
	public void ensureIndexes() {
		baseDao.ensureIndexes();
		
	}

	@Override
	public boolean exists(Query<T> query) {
		return baseDao.exists(query);
	}

	@Override
	public boolean exists(String key, Object value) {
		return baseDao.exists(key, value);
	}

	@Override
	public QueryResults<T> find() {
		return baseDao.find();
	}

	@Override
	public List<K> findIds() {
		return baseDao.findIds();
	}

	@Override
	public List<K> findIds(Query<T> query) {
		return baseDao.findIds(query);
	}

	@Override
	public Key<T> findOneId() {
		return baseDao.findOneId();
	}

	@Override
	public Key<T> findOneId(String s, Object o) {
		return baseDao.findOneId(s, o) ;
	}

	@Override
	public Key<T> findOneId(Query<T> query) {
		return baseDao.findOneId(query);
	}

	@Override
	public List<K> findIds(String key, Object value) {
		return baseDao.findIds(key, value);
	}

	@Override
	public T findOne(Query<T> query) {
		return baseDao.findOne(query);
	}

	@Override
	public T findOne(String key, Object value) {
		return baseDao.findOne(key, value);
	}

	@Override
	public T get(final K id) {
		return baseDao.get(id);
	}

	@Override
	public Class<T> getEntityClass() {
		return baseDao.getEntityClass();
	}

	@Override
	public Key<T> save(final T entity) {
		return baseDao.save(entity);
	}

	@Override
	public Key<T> save(final T entity, final WriteConcern writeConcern) {
		return baseDao.save(entity, writeConcern);
	}

	@Override
	public UpdateResults update(Query<T> query, UpdateOperations<T> ops) {
		return baseDao.update(query, ops);
	}

	@Override
	public UpdateResults updateFirst(Query<T> query, UpdateOperations<T> ops) {
		return baseDao.updateFirst(query, ops);
	}
	
	/**
	 * 修改一条数据.
	 * @param id
	 * @param params
	 * @return
	 */
	public int update(K id, Map<String,Object> params) {
		Query<T> query = createQuery().field("_id").equal(id);
		UpdateOperations<T> update = createUpdateOperations();
		for (Entry<String, Object> entry : params.entrySet()) {
			update.set(entry.getKey(), entry.getValue());
		}
		return baseDao.update(query, update).getUpdatedCount();
	}
}
