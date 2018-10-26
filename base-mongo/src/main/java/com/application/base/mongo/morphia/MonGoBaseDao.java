package com.application.base.mongo.morphia;

import com.application.base.utils.page.PageView;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.util.List;
import java.util.Map;

/**
 * @desc <b>function:</b> 集合持久层的公用的增，删，改，查接口
 * @author 孤狼
 */
public interface MonGoBaseDao<T,K> {
	
	/**
	 * 通过主键查询
	 * @param pk
	 * @return
	 */
	public T findObjById(K pk);
	
	/**
	 * 通过名字查询
	 * @param proKey
	 * @param proValue
	 * @return
	 */
	public T findObjByName(String proKey, String proValue);
	
	/**
	 * 通过对象查找对象
	 * @param params
	 * @return
	 */
	public T findObjByProps(Map<String, Object> params);
	
	/**
	 * 返回分页后的数据
	 * @param pageView
	 * @param params
	 * @return
	 */
	public List<T> findObjList(PageView pageView, Map<String, Object> params);
	
	/**
	 *返回分页后的数据
	 * @param pageView
	 * @param params
	 * @return
	 */
	public PageView findObjPage(PageView pageView, Map<String, Object> params);
	
	/**
	 *返回所有数据
	 * @return
	 */
	public List<T> findObjAll();
	
	/**
	 * 返回所有数据
	 * @param params
	 * @return
	 */
	public List<T> findObjAllByPros(Map<String, Object> params);
	
	/**
	 * 添加
	 * @param t
	 * @return
	 */
	public int addObjOne(T t);
	
	/**
	 *添加所有
	 * @param ts
	 * @return
	 */
	public boolean addObjAll(List<T> ts);
	
	/**
	 * 修改
	 * @param pk
	 * @param params
	 * @return
	 */
	public int updateObjOne(K pk, Map<String, Object> params);
	
	/**
	 *修改
	 * @param query
	 * @param ops
	 * @return
	 */
	public UpdateResults updateObjOneByResult(Query<T> query, UpdateOperations<T> ops);
	
	/**
	 *修改所有属性.
	 * @param pks
	 * @param ts
	 * @return
	 */
	public boolean updateObjAll(List<K> pks, List<Map<String, Object>> ts);
	
	/**
	 *删除
	 * @param pk
	 * @return
	 */
	public int deleteByObjId(K pk);

	/**
	 * 删除所有
	 * @param pks
	 * @return
	 */
	public boolean deleteObjAll(List<K> pks);	

	/**
	 *获得总条数.
	 * @return
	 */
	public long getObjsCount();	
	
	/**
	 *通过条件获得总条数.
	 * @param params
	 * @return
	 */
	public long getObjsByProsCount(Map<String, Object> params);	
	
}
