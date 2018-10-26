package com.application.base.mongo.spring.api;

import com.application.base.utils.page.PageView;

import java.util.List;
import java.util.Map;


/**
 *@desc 定义顶级基础接口
 *@author 孤狼
 */
public interface MongoBaseService<T> {

	/**
	 * 通过Id查找对象
	 * @param id
	 * @return
	 */
	public T findObjById(Object id);

	/**
	 * 通过唯一key找对象
	 * @param proKey
	 * @param proVal
	 * @return
	 */
	public T findObjByName(String proKey, String proVal);

	/**
	 * 通过对象查找一个对象
	 * @param t
	 * @return
	 */
	public T findObjByProps(T t);

	/**
	 * 通过Map属性集合查找对象
	 * @param params
	 * @return
	 */
	public T findObjByProps(Map<String, Object> params);

	/**
	 * 通过对象查找一个List个集合
	 * @param t
	 * @return
	 */
	public List<T> findObjList(T t);

	/**
	 * 通过Map属性集合查找一个List个集合
	 * @param params
	 * @return
	 */
	public List<T> findObjList(Map<String, Object> params);

	/**
	 * 通过对象属性查找并分页
	 * @param pageView
	 * @param t
	 * @return
	 */
	public PageView findObjsByPage(PageView pageView, T t);

	/**
	 * 通过Map属性集合查找分页数据
	 * @param pageView
	 * @param params
	 * @return
	 */
	public PageView findObjsByPage(PageView pageView, Map<String, Object> params);

	/**
	 * 查找所有对象
	 * @return
	 */
	public List<T> findObjAll();

	/**
	 * 添加一个对象
	 * @param t
	 * @return
	 */
	public boolean addObjOne(T t);

	/**
	 * 添加所有对象
	 * @param ts
	 * @return
	 */
	public boolean addObjAll(List<T> ts);

	/**
	 * 通过id修改对象
	 * @param t
	 * @param id
	 * @return
	 */
	public int updateObjOne(T t, Object id);

	/**
	 * 通过id修改对象
	 * @param params
	 * @param id
	 * @return
	 */
	public int updateObjOne(Map<String, Object> params, Object id);

	/**
	 * 修改给定的集合
	 * @param tMap
	 * @param ids
	 * @return
	 */
	public boolean updateObjAll(List<Map<String, Object>> tMap, List<Object> ids);

	/**
	 * 通过id删除对象
	 * @param id
	 * @return
	 */
	public boolean deleteByObjId(Object id);

	/**
	 * 通过id集合删除所有
	 * @param ids
	 * @return
	 */
	public boolean deleteAll(List<T> ids);

	/**
	 * 查找总条数
	 * @return
	 */
	public long getObjsCount();

	/**
	 * 通过对象属性查找总条数
	 * @param t
	 * @return
	 */
	public long getObjsByProsCount(T t);

	/**
	 * 通过Map属性集合查找总条数
	 * @param params
	 * @return
	 */
	public long getObjsByProsCount(Map<String, Object> params);

}
