package com.application.base.core.apisupport.impl;

import java.util.List;

import javax.annotation.Resource;

import com.application.base.core.datasource.param.*;
import com.application.base.core.apisupport.ApiMultiStrutsBaseDao;
import com.application.base.core.datasource.impl.common.MutilDefaultReadAndWriteDataSessionFactory;
import com.application.base.utils.page.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.application.base.core.exception.BusinessException;
import com.application.base.utils.common.Constants;

/**
 * @desc 多数据源操作的实现类.
 * @author 孤狼
 */

@Service
public class MultiStrutsBaseDaoImpl<T> implements ApiMultiStrutsBaseDao<T> {

	@Resource
	protected MutilDefaultReadAndWriteDataSessionFactory factory;
	
	@Override
	public void settingSessionFactory(String factoryTag){
		 factory.setFactoryTag(factoryTag);
	}
	
	/**
	 * PO 的分页方式.
	 */
	@Override
	@SuppressWarnings("hiding")
	public synchronized <T> Pagination<T> queryClassPagination(Class<T> clazz, Param param, int pageNo, int pageSize, String factoryTag) throws BusinessException {
        if(param == null){
            param = ParamBuilder.getInstance().getParam();
        }
        if (pageNo==0) {
			pageNo = 1;
		}
        if (pageSize==0) {
			pageSize = 10;
		}
		param.add(ParamBuilder.nv(Constants.SqlConstants.PAGE_NO, pageNo));
		// 如果每页显示的数据为0时，不需要进行分析
		if (pageSize > 0) {
			param.add(ParamBuilder.nv(Constants.SqlConstants.PAGE_SIZE, pageSize));
		}

		int count = 0;
		if (!StringUtils.isEmpty(factoryTag)) {
			settingSessionFactory(factoryTag.toUpperCase());
		}
		List<T> list = factory.getReadDataSession().queryListResult(clazz, param);
		// 如果list为空则没有必须再查询总条数
		if (list != null && list.size() > 0) {
			if (!StringUtils.isEmpty(factoryTag)) {
				settingSessionFactory(factoryTag.toUpperCase());
			}
			count = factory.getReadDataSession().queryListResultCount(clazz, param);
		}
		Pagination<T> pageResult = new Pagination<T>(list, pageNo, pageSize);
		// 如果总条数为零则不需要设置初始化数值
		if (count > 0L) {
			pageResult.setRowCount(count);
		}
		return pageResult;
	}

	/**
	 * VO 的分页查询方式
	 * @param listElementId:查询list集合的select标签id
	 * @param countElementId:查询list集合size的select标签id
	 */
	@Override
	@SuppressWarnings("hiding")
	public synchronized <T> Pagination<T> queryClassPagination(Class<T> clazz, String listElementId, String countElementId, Param param, int pageNo, int pageSize, String factoryTag) throws BusinessException {
		param.add(ParamBuilder.nv(Constants.SqlConstants.PAGE_NO, pageNo));
		// 如果每页显示的数据为0时，不需要进行分析
		if (pageSize > 0) {
			param.add(ParamBuilder.nv(Constants.SqlConstants.PAGE_SIZE, pageSize));
		}

		int count = 0;
		if (!StringUtils.isEmpty(factoryTag)) {
			settingSessionFactory(factoryTag.toUpperCase());
		}
		List<T> list = factory.getReadDataSession().queryObjVOListByCustomElementName(clazz,listElementId,param);
		// 如果list为空则没有必须再查询总条数
		if (list != null && list.size() > 0) {
			if (!StringUtils.isEmpty(factoryTag)) {
				settingSessionFactory(factoryTag.toUpperCase());
			}
			count = factory.getReadDataSession().querySingleVOByCustomElementName(Integer.class,countElementId, param);
		}
		Pagination<T> pageResult = new Pagination<T>(list, pageNo, pageSize);
		// 如果总条数为零则不需要设置初始化数值
		if (count > 0L) {
			pageResult.setRowCount(count);
		}
		return pageResult;
	}
	
	
	/**
	 * 自定义的 whereSQL 查询分页操作.
	 */
	@Override
	@SuppressWarnings("hiding")
	public synchronized <T> Pagination<T> queryClassPagination(Class<T> clazz, CustomSql whereSQL, int pageNo, int pageSize, String factoryTag) throws BusinessException {
		if (whereSQL == null){
			whereSQL = SqlCreator.where();
		}
		int count = 0;
		Pagination<T> pageResult = null;
		if (pageNo==0) {
			pageNo = 1;
		}
	    if (pageSize==0) {
			pageSize = 10;
		}
		// 如果每页显示的数据为0时，不需要进行分页
		if (pageSize > 0) {
			if (!StringUtils.isEmpty(factoryTag)) {
				settingSessionFactory(factoryTag.toUpperCase());
			}
			count = factory.getReadDataSession().queryListResultCountByWhere(clazz, whereSQL);
			whereSQL.operator(ESQLOperator.LIMIT).value((pageNo - 1) * pageSize).operator(ESQLOperator.COMMA).value(pageSize);
			if (!StringUtils.isEmpty(factoryTag)) {
				settingSessionFactory(factoryTag.toUpperCase());
			}
			List<T> list = factory.getReadDataSession().queryListResultByWhere(clazz, whereSQL);
			pageResult = new Pagination<T>(list, pageNo, pageSize);
			// 如果总条数为零则不需要设置初始化数值
			if (count > 0L) {
				pageResult.setRowCount(count);
			}
		}
		return pageResult;
	}
}
