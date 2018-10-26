package com.application.base.core.datasource.dao;

import java.util.List;

import com.application.base.utils.page.PageView;


/**
 * <b>function:</b> 集合持久层的公用的增，删，改，查接口
 * @project JavaBase
 * <T> 表示传入实体类
 * @version 1.0
 * @author 孤狼
 */
public interface BaseSystemDao<T> {

	/**
	 * 通过主键查询
	 * @param pk
	 * @return
	 */
	public T findById(String pk);
	
	/**
	 * 通过名字查询
	 * @param objName
	 * @return
	 */
	public T findByName(String objName);
	
	/**
	 * 通过对象查找对象
	 * @param t
	 * @return
	 */
	public T findByProps(T t);
	
	/**
	 * 返回分页后的数据
	 * @param pageView
	 * @param t
	 * @return
	 */
	public List<T> find(PageView pageView,T t);
	
	/**
	 * 返回所有数据
	 * @return
	 */
	public List<T> findAll();
	
	/**
	 * 返回所有数据
	 * @param t
	 * @return
	 */
	public List<T> findAllByPros(T t);
	
	/**
	 * 添加
	 * @param t
	 * @return
	 */
	public int addOne(T t);
	
	/**
	 * 添加所有
	 * @param ts
	 * @return
	 */
	public boolean addAll(List<T> ts);
	
	/**
	 * 修改
	 * @param t
	 * @return
	 */
	public int updateOne(T t);
	
	/**
	 * 修改所有属性.
	 * @param ts
	 * @return
	 */
	public boolean updateAll(List<T> ts);
	
	/**
	 * 删除
	 * @param pk
	 * @return
	 */
	public int deleteById(String pk);

	/**
	 * 删除所有
	 * @param pks
	 * @return
	 */
	public boolean deleteAll(List<String> pks);	

	/**
	 * 获得总条数.
	 * @return
	 */
	public int getObjsCount();	
	
	/**
	 *通过条件获得总条数.
	 * @param t
	 * @return
	 */
	public int getObjsByProsCount(T t);	
	

}
