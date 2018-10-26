package ${JavaBeanDaoImplPath};

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import org.springframework.stereotype.Repository;
<#if useCache == "YES">
	<#if moreDbTag == "YES">
import com.application.base.core.apisupport.impl.MultiCacheStrutsBaseDaoImpl;
	<#else>
import com.application.base.core.apisupport.impl.SingleCacheStrutsBaseDaoImpl;
	</#if>
<#else>
	<#if moreDbTag == "YES">
import com.application.base.core.apisupport.impl.MultiStrutsBaseDaoImpl;
	<#else>
import com.application.base.core.apisupport.impl.SingleStrutsBaseDaoImpl;
	</#if>
</#if>

import com.application.base.core.datasource.param.CustomSql;
import com.application.base.core.datasource.param.ESQLOperator;
import com.application.base.core.datasource.param.Param;
import com.application.base.core.datasource.param.ParamBuilder;
import com.application.base.core.datasource.param.SqlCreator;
import com.application.base.core.exception.BusinessException;
import com.application.base.utils.page.Pagination;
import com.application.base.core.utils.CommonBeanUtils;

import ${JavaBeanPath}.${poName};
import ${JavaBeanDaoPath}.${poName}Dao;

/**
 * ${poName}DaoImpl实现
 * @author 孤狼
 */
<#if useCache == "YES">
	<#if moreDbTag == "YES">
@Repository("${firstLowerPoName}Dao")
public class ${poName}DaoImpl extends MultiCacheStrutsBaseDaoImpl<${poName}> implements ${poName}Dao {
	<#else>
@Repository("${firstLowerPoName}Dao")
public class ${poName}DaoImpl extends SingleCacheStrutsBaseDaoImpl<${poName}> implements ${poName}Dao {
	</#if>
<#else>
	<#if moreDbTag == "YES">
@Repository("${firstLowerPoName}Dao")
public class ${poName}DaoImpl extends MultiStrutsBaseDaoImpl<${poName}> implements ${poName}Dao {
	<#else>
@Repository("${firstLowerPoName}Dao")
public class ${poName}DaoImpl extends SingleStrutsBaseDaoImpl<${poName}> implements ${poName}Dao {
	</#if>
</#if>
	
	
<#if moreDbTag == "YES">
	@Override
	public synchronized ${poName} saveObject(Map<String, Object> param,String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		${poName} ${firstLowerPoName} = CommonBeanUtils.transMap2BasePO(param, ${poName}.class);
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().saveObject(${poName}.class,${firstLowerPoName});
		<#else>
		return factory.getWriteDataSession().saveObject(${poName}.class,${firstLowerPoName});
		</#if>
	}
<#else>
	@Override
	public ${poName} saveObject(Map<String, Object> param) throws BusinessException {
		${poName} ${firstLowerPoName} = CommonBeanUtils.transMap2BasePO(param, ${poName}.class);
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().saveObject(${poName}.class,${firstLowerPoName});
		<#else>
		return factory.getWriteDataSession().saveObject(${poName}.class,${firstLowerPoName});
		</#if>
	}
</#if>
	


<#if moreDbTag == "YES">
	@Override
	public synchronized ${poName} saveObject(${poName} ${firstLowerPoName},String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().saveObject(${poName}.class,${firstLowerPoName});
		<#else>
		return factory.getWriteDataSession().saveObject(${poName}.class,${firstLowerPoName});
		</#if>
	}		
<#else>
	@Override
	public ${poName} saveObject(${poName} ${firstLowerPoName}) throws BusinessException {
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().saveObject(${poName}.class,${firstLowerPoName});
		<#else>
		return factory.getWriteDataSession().saveObject(${poName}.class,${firstLowerPoName});
		</#if>
	}
</#if>

	
	
<#if moreDbTag == "YES">
	@Override
	public synchronized boolean saveBatchObject(List<${poName}> ${firstLowerPoName}s,String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().saveBatchObject(${poName}.class,${firstLowerPoName}s);
		<#else>
		return factory.getWriteDataSession().saveBatchObject(${poName}.class,${firstLowerPoName}s);
		</#if>
	}	
<#else>
	@Override
	public boolean saveBatchObject(List<${poName}> ${firstLowerPoName}s) throws BusinessException {
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().saveBatchObject(${poName}.class,${firstLowerPoName}s);
		<#else>
		return factory.getWriteDataSession().saveBatchObject(${poName}.class,${firstLowerPoName}s);
		</#if>
	}
</#if>

	
	
<#if moreDbTag == "YES">
	@Override
	public synchronized ${poName} getObjectById(Object objId,String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().querySingleResultById(${poName}.class,"${tablePKVal}", objId);
		<#else>
		return factory.getReadDataSession().querySingleResultById(${poName}.class,"${tablePKVal}", objId);
		</#if>
	}		
<#else>
	@Override
	public ${poName} getObjectById(Object objId) throws BusinessException {
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().querySingleResultById(${poName}.class,"${tablePKVal}", objId);
		<#else>
		return factory.getReadDataSession().querySingleResultById(${poName}.class,"${tablePKVal}", objId);
		</#if>
	}
</#if>


	
<#if moreDbTag == "YES">
	<#if existUuid == "0">
    @Override
	public synchronized ${poName} getObjectByUUId(String uuid,String factoryTag) throws BusinessException {
    	//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().querySingleResultByUUId(${poName}.class, uuid);
		<#else>
		return factory.getReadDataSession().querySingleResultByUUId(${poName}.class, uuid);
		</#if>
	}
	<#else>
    @Override
	public ${poName} getObjectByUUId(String uuid,String factoryTag) throws BusinessException {
		return null;
	}
	</#if>	
<#else>
	<#if existUuid == "0">
    @Override
	public ${poName} getObjectByUUId(String uuid) throws BusinessException {
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().querySingleResultByUUId(${poName}.class, uuid);
		<#else>
		return factory.getReadDataSession().querySingleResultByUUId(${poName}.class, uuid);
		</#if>
	}
	<#else>
    @Override
	public ${poName} getObjectByUUId(String uuid) throws BusinessException {
		return null;
	}
	</#if>
</#if>

	
	
	
<#if moreDbTag == "YES">
	@Override
	public synchronized int updateObjectById(Map<String, Object> param, ${poName} object,String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		${poName} ${firstLowerPoName} = new ${poName}();
		${firstLowerPoName}.set${primaryKeySet}(object.get${primaryKeySet}());
		${firstLowerPoName}.setCreateTime(object.getCreateTime());
		${firstLowerPoName} = CommonBeanUtils.transMap2BasePO(param, ${firstLowerPoName});
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().updateObjectById(${poName}.class, ${firstLowerPoName});
		<#else>
		return factory.getWriteDataSession().updateObjectById(${poName}.class, ${firstLowerPoName});
		</#if>
	}		
<#else>
	@Override
	public int updateObjectById(Map<String, Object> param, ${poName} object) throws BusinessException {
		${poName} ${firstLowerPoName} = new ${poName}();
		${firstLowerPoName}.set${primaryKeySet}(object.get${primaryKeySet}());
		${firstLowerPoName}.setCreateTime(object.getCreateTime());
		${firstLowerPoName} = CommonBeanUtils.transMap2BasePO(param, ${firstLowerPoName});
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().updateObjectById(${poName}.class, ${firstLowerPoName});
		<#else>
		return factory.getWriteDataSession().updateObjectById(${poName}.class, ${firstLowerPoName});
		</#if>
	}
</#if>
	
	
<#if moreDbTag == "YES">
	<#if existUuid == "0">
    @Override
	public synchronized int updateObjectByUUId(Map<String, Object> param, ${poName} object,String factoryTag) throws BusinessException {
    	//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		${poName} ${firstLowerPoName} = new ${poName}();
		${firstLowerPoName}.setUuid(object.getUuid());
		${firstLowerPoName}.setCreateTime(object.getCreateTime());
		${firstLowerPoName} = CommonBeanUtils.transMap2BasePO(param, ${firstLowerPoName});
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().updateObjectByUUId(${poName}.class, ${firstLowerPoName});
		<#else>
		return factory.getWriteDataSession().updateObjectByUUId(${poName}.class, ${firstLowerPoName});
		</#if>
	}
	<#else>
    @Override
	public int updateObjectByUUId(Map<String, Object> param, ${poName} ${firstLowerPoName},String factoryTag) throws BusinessException {
		return 0;
	}
	</#if>		
<#else>
	<#if existUuid == "0">
    @Override
	public int updateObjectByUUId(Map<String, Object> param, ${poName} object) throws BusinessException {
		${poName} ${firstLowerPoName} = new ${poName}();
		${firstLowerPoName}.setUuid(object.getUuid());
		${firstLowerPoName}.setCreateTime(object.getCreateTime());
		${firstLowerPoName} = CommonBeanUtils.transMap2BasePO(param, ${firstLowerPoName});
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().updateObjectByUUId(${poName}.class, ${firstLowerPoName});
		<#else>
		return factory.getWriteDataSession().updateObjectByUUId(${poName}.class, ${firstLowerPoName});
		</#if>
	}
	<#else>
    @Override
	public int updateObjectByUUId(Map<String, Object> param, ${poName} object) throws BusinessException {
		return 0;
	}
	</#if>
</#if>
	
	
<#if moreDbTag == "YES">
	@Override
	public synchronized int updateObjectByWhere(Map<String, Object> param,String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		Param params = ParamBuilder.getInstance().getParam().add(param);
		//根据实际情况填写 ; SqlCreator.where().cloumn("AAA").operator(ESQLOperator.EQ).value("AAA");
		CustomSql where = null;
        <#if useCache == "YES">
		return factory.getCacheWriteDataSession().updateCustomColumnByWhere(${poName}.class, params, where);
		<#else>
		return factory.getWriteDataSession().updateCustomColumnByWhere(${poName}.class, params, where);
		</#if>
	}		
<#else>
	@Override
	public int updateObjectByWhere(Map<String, Object> param) throws BusinessException {
		Param params = ParamBuilder.getInstance().getParam().add(param);
		//根据实际情况填写 ; SqlCreator.where().cloumn("AAA").operator(ESQLOperator.EQ).value("AAA");
		CustomSql where = null;
        <#if useCache == "YES">
		return factory.getCacheWriteDataSession().updateCustomColumnByWhere(${poName}.class, params, where);
		<#else>
		return factory.getWriteDataSession().updateCustomColumnByWhere(${poName}.class, params, where);
		</#if>
	}
</#if>

	
<#if moreDbTag == "YES">
	@Override
	public synchronized int updateObjectByWhere(${poName} ${firstLowerPoName},String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		Param params = ParamBuilder.getInstance().getParam();
		//根据实际情况填写. //whereStr //SqlCreator.where().cloumn("AAA").operator(ESQLOperator.EQ).value("AAA");
		CustomSql where = null;
       	<#if useCache == "YES">
		return factory.getCacheWriteDataSession().updateCustomColumnByWhere(${poName}.class, params, where);
		<#else>
		return factory.getWriteDataSession().updateCustomColumnByWhere(${poName}.class, params, where);
		</#if>
	}		
<#else>
	@Override
	public int updateObjectByWhere(${poName} ${firstLowerPoName}) throws BusinessException {
		//根据实际情况填写 //updateStr
		Param params = ParamBuilder.getInstance().getParam();
		//根据实际情况填写. //whereStr //SqlCreator.where().cloumn("AAA").operator(ESQLOperator.EQ).value("AAA");
		CustomSql where = null;
       	<#if useCache == "YES">
		return factory.getCacheWriteDataSession().updateCustomColumnByWhere(${poName}.class, params, where);
		<#else>
		return factory.getWriteDataSession().updateCustomColumnByWhere(${poName}.class, params, where);
		</#if>
	}
</#if>
	
	
<#if moreDbTag == "YES">
	@Override
	public synchronized int deleteObjectById(Object objId,String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		<#if existIsDelete == "0">
		Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv("${tablePKVal}",objId));
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().logicDelete(${poName}.class, param);
			<#else>
		return factory.getWriteDataSession().logicDelete(${poName}.class, param);
			</#if>
		<#else>
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().physicalDelete(${poName}.class,"${tablePKVal}", objId);
			<#else>
		return factory.getWriteDataSession().physicalDelete(${poName}.class,"${tablePKVal}", objId);
			</#if>
		</#if>
	}		
<#else>
	@Override
	public int deleteObjectById(Object objId) throws BusinessException {
		<#if existIsDelete == "0">
		Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv("${tablePKVal}",objId));
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().logicDelete(${poName}.class, param);
			<#else>
		return factory.getWriteDataSession().logicDelete(${poName}.class, param);
			</#if>
		<#else>
		<#if useCache == "YES">
		return factory.getCacheWriteDataSession().physicalDelete(${poName}.class,"${tablePKVal}", objId);
			<#else>
		return factory.getWriteDataSession().physicalDelete(${poName}.class,"${tablePKVal}", objId);
			</#if>
		</#if>
	}
</#if>

	
<#if moreDbTag == "YES">
	<#if existUuid == "0">
    @Override
	public synchronized int deleteObjectByUUId(String uuid,String factoryTag) throws BusinessException {
    	//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		<#if existIsDelete == "0">
		Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv("uuid",uuid));
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().logicDelete(${poName}.class, param);
			<#else>
		return factory.getWriteDataSession().logicDelete(${poName}.class, param);
			</#if>
		<#else>
        //根据实际情况填写.
		CustomSql where = SqlCreator.where().cloumn("uuid").operator(ESQLOperator.EQ).value(uuid);
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().physicalWhereDelete(${poName}.class,where);
			<#else>
		return factory.getWriteDataSession().physicalWhereDelete(${poName}.class,where);
			</#if>
		</#if>
	}
	<#else>
    @Override
	public int deleteObjectByUUId(String uuid,String factoryTag) throws BusinessException {
    	//没有uuid,该方法就为不可用
		return 0;
	}
	</#if>		
<#else>
	<#if existUuid == "0">
    @Override
	public int deleteObjectByUUId(String uuid) throws BusinessException {
		<#if existIsDelete == "0">
		Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv("uuid",uuid));
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().logicDelete(${poName}.class, param);
			<#else>
		return factory.getWriteDataSession().logicDelete(${poName}.class, param);
			</#if>
		<#else>
        //根据实际情况填写.
		CustomSql where = SqlCreator.where().cloumn("uuid").operator(ESQLOperator.EQ).value(uuid);
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().physicalWhereDelete(${poName}.class,where);
			<#else>
		return factory.getWriteDataSession().physicalWhereDelete(${poName}.class,where);
			</#if>
		</#if>
	}
	<#else>
    @Override
	public int deleteObjectByUUId(String uuid) throws BusinessException {
    	//没有uuid,该方法就为不可用
		return 0;
	}
	</#if>
</#if>


<#if moreDbTag == "YES">
	@Override
	public synchronized int deleteObjectByWhere(Map<String, Object> param,String factoryTag) throws BusinessException {
		//根据实际情况自己设置访问的数据库.
		factory.setFactoryTag(factoryTag);
		//根据实际情况填写要查的列和对应的值.
		//根据实际情况填写. //whereStr //SqlCreator.where().cloumn("AAA").operator(ESQLOperator.EQ).value("AAA");
		CustomSql where = null;
		<#if existIsDelete == "0">
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().logicWhereDelete(${poName}.class, where);
			<#else>
		return factory.getWriteDataSession().logicWhereDelete(${poName}.class, where);
			</#if>
		<#else>
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().physicalWhereDelete(${poName}.class, where);
			<#else>
		return factory.getWriteDataSession().physicalWhereDelete(${poName}.class, where);
			</#if>
		</#if>		
	}		
<#else>
	@Override
	public int deleteObjectByWhere(Map<String, Object> param) throws BusinessException {
		//根据实际情况填写要查的列和对应的值.
		//根据实际情况填写. //whereStr //SqlCreator.where().cloumn("AAA").operator(ESQLOperator.EQ).value("AAA");
		CustomSql where = null;
		<#if existIsDelete == "0">
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().logicWhereDelete(${poName}.class, where);
			<#else>
		return factory.getWriteDataSession().logicWhereDelete(${poName}.class, where);
			</#if>
		<#else>
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().physicalWhereDelete(${poName}.class, where);
			<#else>
		return factory.getWriteDataSession().physicalWhereDelete(${poName}.class, where);
			</#if>
		</#if>		
	}
</#if>


	
	
<#if moreDbTag == "YES">
	@Override
	public synchronized int deleteObjectByWhere(${poName} ${firstLowerPoName},String factoryTag) throws BusinessException {
		//根据实际情况自己设置访问的数据库.
		factory.setFactoryTag(factoryTag);
		//根据实际情况填写要查的列和对应的值. //SqlCreator.where().cloumn("AAA").operator(ESQLOperator.EQ).value("AAA");
		CustomSql where = null;
		<#if existIsDelete == "0">
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().logicWhereDelete(${poName}.class, where);
			<#else>
		return factory.getWriteDataSession().logicWhereDelete(${poName}.class, where);
			</#if>
		<#else>
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().physicalWhereDelete(${poName}.class, where);
			<#else>
		return factory.getWriteDataSession().physicalWhereDelete(${poName}.class, where);
			</#if>
		</#if>		
	}	
<#else>
	@Override
	public int deleteObjectByWhere(${poName} ${firstLowerPoName}) throws BusinessException {
		//根据实际情况填写要查的列和对应的值.
		//根据实际情况填写 ; //SqlCreator.where().cloumn("AAA").operator(ESQLOperator.EQ).value("AAA");
		CustomSql where = null;
		<#if existIsDelete == "0">
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().logicWhereDelete(${poName}.class, where);
			<#else>
		return factory.getWriteDataSession().logicWhereDelete(${poName}.class, where);
			</#if>
		<#else>
			<#if useCache == "YES">
		return factory.getCacheWriteDataSession().physicalWhereDelete(${poName}.class, where);
			<#else>
		return factory.getWriteDataSession().physicalWhereDelete(${poName}.class, where);
			</#if>
		</#if>		
	}
</#if>

	
<#if moreDbTag == "YES">
	@Override
	public synchronized Pagination<${poName}> paginationObjects(Map<String, Object> param, int pageNo, int pageSize,String factoryTag)
			throws BusinessException {
		Param params = ParamBuilder.getInstance().getParam().add(param);
		return queryClassPagination(${poName}.class, params, pageNo, pageSize, factoryTag);
	}	
<#else>
	@Override
	public Pagination<${poName}> paginationObjects(Map<String, Object> param, int pageNo, int pageSize)
			throws BusinessException {
		Param params = ParamBuilder.getInstance().getParam().add(param);
		return queryClassPagination(${poName}.class, params, pageNo, pageSize);
	}
</#if>


<#if moreDbTag == "YES">
	@Override
	public synchronized Pagination<${poName}> paginationObjects(${poName} ${firstLowerPoName}, int pageNo, int pageSize,String factoryTag)
			throws BusinessException {
		Param params = ParamBuilder.getInstance().getParam().add(CommonBeanUtils.transBean2Map(${firstLowerPoName}));
		return queryClassPagination(${poName}.class, params, pageNo, pageSize, factoryTag);
	}		
<#else>
	@Override
	public Pagination<${poName}> paginationObjects(${poName} ${firstLowerPoName}, int pageNo, int pageSize)
			throws BusinessException {
		Param params = ParamBuilder.getInstance().getParam().add(CommonBeanUtils.transBean2Map(${firstLowerPoName}));
		return queryClassPagination(${poName}.class, params, pageNo, pageSize);
	}
</#if>
	
	
<#if moreDbTag == "YES">
	@Override
	public synchronized ${poName} findObjectByPros(Map<String, Object> param,String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		Param params = ParamBuilder.getInstance().getParam().add(param);
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().querySingleResultByParams(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().querySingleResultByParams(${poName}.class, params);
		</#if>
	}		
<#else>
	@Override
	public ${poName} findObjectByPros(Map<String, Object> param) throws BusinessException {
		Param params = ParamBuilder.getInstance().getParam().add(param);
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().querySingleResultByParams(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().querySingleResultByParams(${poName}.class, params);
		</#if>
	}
</#if>

	
<#if moreDbTag == "YES">
	@Override
	public synchronized ${poName} findObjectByPros(${poName} ${firstLowerPoName},String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		Param params = ParamBuilder.getInstance().getParam().add(CommonBeanUtils.transBean2Map(${firstLowerPoName}));
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().querySingleResultByParams(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().querySingleResultByParams(${poName}.class, params);
		</#if>
	}		
<#else>
	@Override
	public ${poName} findObjectByPros(${poName} ${firstLowerPoName}) throws BusinessException {
		Param params = ParamBuilder.getInstance().getParam().add(CommonBeanUtils.transBean2Map(${firstLowerPoName}));
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().querySingleResultByParams(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().querySingleResultByParams(${poName}.class, params);
		</#if>
	}
</#if>

	
	
<#if moreDbTag == "YES">
	@Override
	public synchronized List<${poName}> findObjectListByPros(Map<String, Object> param,String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		Param params = ParamBuilder.getInstance().getParam().add(param);
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResult(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().queryListResult(${poName}.class, params);
		</#if>
	}	
<#else>
	@Override
	public List<${poName}> findObjectListByPros(Map<String, Object> param) throws BusinessException {
		Param params = ParamBuilder.getInstance().getParam().add(param);
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResult(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().queryListResult(${poName}.class, params);
		</#if>
	}
</#if>
	
	
<#if moreDbTag == "YES">
	@Override
	public synchronized List<${poName}> findObjectListByPros(${poName} ${firstLowerPoName},String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		Param params = ParamBuilder.getInstance().getParam().add(CommonBeanUtils.transBean2Map(${firstLowerPoName}));
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResult(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().queryListResult(${poName}.class, params);
		</#if>
	}		
<#else>
	@Override
	public List<${poName}> findObjectListByPros(${poName} ${firstLowerPoName}) throws BusinessException {
		Param params = ParamBuilder.getInstance().getParam().add(CommonBeanUtils.transBean2Map(${firstLowerPoName}));
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResult(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().queryListResult(${poName}.class, params);
		</#if>
	}
</#if>
	
	
	
<#if moreDbTag == "YES">
	@Override
	public synchronized int getObjectCount(String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResultCount(${poName}.class, ParamBuilder.getInstance().getParam());
		<#else>
		return factory.getReadDataSession().queryListResultCount(${poName}.class, ParamBuilder.getInstance().getParam());
		</#if>
	}	
<#else>
	@Override
	public int getObjectCount() throws BusinessException {
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResultCount(${poName}.class, ParamBuilder.getInstance().getParam());
		<#else>
		return factory.getReadDataSession().queryListResultCount(${poName}.class, ParamBuilder.getInstance().getParam());
		</#if>
	}
</#if>

	
<#if moreDbTag == "YES">
	@Override
	public synchronized int getObjectCount(Map<String, Object> param,String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		Param params = ParamBuilder.getInstance().getParam().add(param);
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResultCount(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().queryListResultCount(${poName}.class, params);
		</#if>
	}		
<#else>
	@Override
	public int getObjectCount(Map<String, Object> param) throws BusinessException {
		Param params = ParamBuilder.getInstance().getParam().add(param);
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResultCount(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().queryListResultCount(${poName}.class, params);
		</#if>
	}
</#if>

	
<#if moreDbTag == "YES">
	@Override
	public synchronized int getObjectCount(${poName} ${firstLowerPoName},String factoryTag) throws BusinessException {
		//根据实际情况自己添加
		factory.setFactoryTag(factoryTag);
		Param params = ParamBuilder.getInstance().getParam().add(CommonBeanUtils.transBean2Map(${firstLowerPoName}));
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResultCount(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().queryListResultCount(${poName}.class, params);
		</#if>
	}
<#else>
	@Override
	public int getObjectCount(${poName} ${firstLowerPoName}) throws BusinessException {
		Param params = ParamBuilder.getInstance().getParam().add(CommonBeanUtils.transBean2Map(${firstLowerPoName}));
		<#if useCache == "YES">
		return factory.getCacheReadDataSession().queryListResultCount(${poName}.class, params);
		<#else>
		return factory.getReadDataSession().queryListResultCount(${poName}.class, params);
		</#if>
	}
</#if>


}
