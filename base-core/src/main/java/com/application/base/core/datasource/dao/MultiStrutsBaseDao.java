package com.application.base.core.datasource.dao;

import java.util.List;
import java.util.Map;

import com.application.base.core.exception.BusinessException;
import com.application.base.utils.page.Pagination;

/**
 * @desc 上级的通用dao接口.
 * @author 孤狼
 * @param <T>
 */
public interface MultiStrutsBaseDao<T>{

	/**
	 *指定数据源添加对象。
	 * @param param
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	T saveObject(Map<String, Object> param,String factoryTag) throws BusinessException;
	
	/**
	 * 指定数据源添加对象。
	 * @param t
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	T saveObject(T t,String factoryTag) throws BusinessException;
	
	/**
	 * 批量添加对象
	 * @param objs
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	boolean saveBatchObject(List<T> objs,String factoryTag) throws BusinessException;
	
	/**
	 * 通过主键id获得对象
	 * @param objId
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	T getObjectById(Object objId, String factoryTag) throws BusinessException;

	/**
	 * 通过uuid获得对象.
	 * @param uuid
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	T getObjectByUUId(String uuid, String factoryTag) throws BusinessException;

	/**
	 *通过id修改对象.
	 * @param param
	 * @param t
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	int updateObjectById(Map<String, Object> param, T t, String factoryTag) throws BusinessException;

	/**
	 *  通过uuid修改对象.
	 * @param param
	 * @param t
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	int updateObjectByUUId(Map<String, Object> param, T t, String factoryTag) throws BusinessException;

	/**
	 * 通过条件修改对象.
	 * @param param @ CustomSQL where : 由自己来创建！！！
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	int updateObjectByWhere(Map<String, Object> param,String factoryTag) throws BusinessException;
	
	/**
	 *  通过条件修改对象.
	 * @param t  @ CustomSQL where : 由自己来创建！！！
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	int updateObjectByWhere(T t,String factoryTag) throws BusinessException;
	
	/**
	 * 通过id删除对象.
	 * @param objId
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	int deleteObjectById(Object objId, String factoryTag) throws BusinessException;
	
	/**
	 * 通过uuid删除对象.
	 * @param uuid
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	int deleteObjectByUUId(String uuid, String factoryTag) throws BusinessException;

	/**
	 * 通过条件删除对象.
	 * @param param
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	int deleteObjectByWhere(Map<String, Object> param,String factoryTag) throws BusinessException;

	/**
	 * 通过条件删除对象.
	 * @param t
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	int deleteObjectByWhere(T t,String factoryTag) throws BusinessException;
	
	/**
	 *  分页获得对象集合.
	 * @param param
	 * @param pageNo
	 * @param pageSize
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	Pagination<T> paginationObjects(Map<String, Object> param, int pageNo, int pageSize, String factoryTag) throws BusinessException;

	/**
	 *分页获得对象集合.
	 * @param t
	 * @param pageNo
	 * @param pageSize
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	Pagination<T> paginationObjects(T t, int pageNo, int pageSize, String factoryTag) throws BusinessException;
	
	/**
	 *通过属性查找对象.
	 * @param param
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	T findObjectByPros(Map<String, Object> param,String factoryTag) throws BusinessException;

	/**
	 * 通过属性查找对象.
	 * @param t
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	T findObjectByPros(T t,String factoryTag) throws BusinessException;
	
	/**
	 *通过属性查找对象集合.
	 * @param param
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	List<T> findObjectListByPros(Map<String, Object> param,String factoryTag) throws BusinessException;
	
	/**
	 * 通过属性查找对象集合.
	 * @param t
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	List<T> findObjectListByPros(T t,String factoryTag) throws BusinessException;
	
	/**
	 *获得总条数
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	int getObjectCount(String factoryTag) throws BusinessException;
	
	/**
	 * 通过条件获得总条数
	 * @param param
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	int getObjectCount(Map<String, Object> param,String factoryTag) throws BusinessException;
	
	/**
	 * 通过条件获得总条数
	 * @param t
	 * @param factoryTag
	 * @return
	 * @throws BusinessException
	 */
	int getObjectCount(T t,String factoryTag) throws BusinessException;
	
}
