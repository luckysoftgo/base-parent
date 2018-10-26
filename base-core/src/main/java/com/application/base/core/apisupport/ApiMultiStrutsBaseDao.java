package com.application.base.core.apisupport;

import com.application.base.core.datasource.param.CustomSql;
import com.application.base.core.datasource.param.Param;
import com.application.base.core.exception.BusinessException;
import com.application.base.utils.page.Pagination;

/**
 * 多数据源操作数据库
 * @param <T>
 * @author 孤狼
 */
public interface ApiMultiStrutsBaseDao<T>{

	/**
	 *设置访问数据库的数据源
	 * @param factoryTag
	 */
	void settingSessionFactory(String factoryTag);
	
	/**
	 *  查询列表数据，带分页功能
	 * @param clazz
	 * @param param
	 * @param pageNo
	 * @param pageSize
	 * @param factoryTag
	 * @param <T>
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("hiding")
	<T> Pagination<T> queryClassPagination(Class<T> clazz, Param param, int pageNo, int pageSize, String factoryTag) throws BusinessException;

	/**
	 *
	 * 查询列表数据，带分页功能查询列表数据，带分页功能
	 * @param clazz
	 * @param listElementId 列表查询元素ID,在 custom.xml 文件中.
	 * @param countElementId 列表个数查询元素ID,在 custom.xml 文件中.
	 * @param param
	 * @param pageNo
	 * @param pageSize
	 * @param factoryTag
	 * @param <T>
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("hiding")
	<T> Pagination<T> queryClassPagination(Class<T> clazz, String listElementId, String countElementId, Param param,int pageNo, int pageSize,String factoryTag) throws BusinessException;
	
	/**
	 * 根据自定义where条件sql分页查询
	 * @param clazz
	 * @param whereSQL
	 * @param pageNo
	 * @param pageSize
	 * @param factoryTag
	 * @param <T>
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("hiding")
	<T> Pagination<T> queryClassPagination(Class<T> clazz, CustomSql whereSQL, int pageNo, int pageSize, String factoryTag) throws BusinessException;
	
	
}
