package ${JavaBeanServiceImplPath};

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
	<#if useTransactional == "NO">
	<#else>
import org.springframework.transaction.annotation.Transactional;
	</#if>
import com.application.base.core.constant.CoreConstants;
import com.application.base.core.exception.CommonException;
import com.application.base.utils.page.Pagination;

import com.application.base.core.utils.CommonBeanUtils;

import ${JavaBeanPath}.${poName};
import ${JavaBeanDaoPath}.${poName}Dao;
import ${JavaBeanServicePath}.${poName}Service;

/**
 * ${poName}ServiceImpl实现
 * @author 孤狼
 */
@Service("${firstLowerPoName}Service")
public class ${poName}ServiceImpl implements ${poName}Service {

	/**
	 *Dao 注入.
	 */
	@Autowired
	private ${poName}Dao ${firstLowerPoName}Dao;

	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	@Override
	public ${poName} saveObject(Map<String, Object> param) {
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			${poName} object = ${firstLowerPoName}Dao.saveObject(param,factoryTag);
			<#else>
			${poName} object = ${firstLowerPoName}Dao.saveObject(param);
			</#if>
			return object;
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.ADD_DATA_TO_DB_FAIL);
		}
	}


	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	@Override
	public ${poName} saveObject(${poName} object) {
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			${poName} result = ${firstLowerPoName}Dao.saveObject(object,factoryTag);
			<#else>
			${poName} result = ${firstLowerPoName}Dao.saveObject(object);
			</#if>
			return result;
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.ADD_DATA_TO_DB_FAIL);
		}
	}
	
	
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	@Override
	public boolean saveBatchObject(List<${poName}> objs) {
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			boolean result = ${firstLowerPoName}Dao.saveBatchObject(objs,factoryTag);
			<#else>
			boolean result = ${firstLowerPoName}Dao.saveBatchObject(objs);
			</#if>
			return result;
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.ADD_DATA_TO_DB_FAIL);
		}
	}

	@Override
	public ${poName} getObjectById(Object objId) {
		${poName} object = null;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			object = ${firstLowerPoName}Dao.getObjectById(objId,factoryTag);
			<#else>
			object = ${firstLowerPoName}Dao.getObjectById(objId);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.SELECT_DATA_FROM_DB_FAIL);
		}
		if(object==null){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_DB_NO_DATA);
		}
		return object;
	}


	<#if existUuid == "0">
    @Override
	public ${poName} getObjectByUUId(String uuid) {
		${poName} object = null;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			object = ${firstLowerPoName}Dao.getObjectByUUId(uuid,factoryTag);
			<#else>
			object = ${firstLowerPoName}Dao.getObjectByUUId(uuid);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.SELECT_DATA_FROM_DB_FAIL);
		}
		if(object==null){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_DB_NO_DATA);
		}
		return object;
	}
	<#else>
    @Override
	public ${poName} getObjectByUUId(String uuid) {
		return null;
	}
	</#if>

	
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	@Override
	public int updateObjectById(Map<String, Object> param, Object objId) {
		int count = 0 ;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			${poName} object = getObjectById(objId); 
			count = ${firstLowerPoName}Dao.updateObjectById(param, object,factoryTag);
			<#else>
			${poName} object = getObjectById(objId); 
			count = ${firstLowerPoName}Dao.updateObjectById(param, object);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.DATA_OPERATE_EXCEPTION_MSG);
		}
		if(count < 1 ){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.UPDATE_DATA_TO_DB_FAIL);
		}
		return count;
	}


	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	@Override
	public int updateObjectById(${poName} object, Object objId) {
		int count = 0 ;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			${poName} existObj = getObjectById(objId);
			Map<String, Object> param = CommonBeanUtils.transBean2Map(object);
			param = CommonBeanUtils.getValueMap(param);
			count = ${firstLowerPoName}Dao.updateObjectById(param,existObj,factoryTag);
			<#else>
			${poName} existObj = getObjectById(objId);
			Map<String, Object> param = CommonBeanUtils.transBean2Map(object);
			param = CommonBeanUtils.getValueMap(param);
			count = ${firstLowerPoName}Dao.updateObjectById(param,existObj);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.DATA_OPERATE_EXCEPTION_MSG);
		}
		if(count < 1 ){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.UPDATE_DATA_TO_DB_FAIL);
		}
		return count;
	}
	
	
	<#if existUuid == "0">
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
    @Override
	public int updateObjectByUUId(Map<String, Object> param, String uuid) {
		int count = 0 ;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			${poName} object = getObjectByUUId(uuid);
			count = ${firstLowerPoName}Dao.updateObjectByUUId(param, object,factoryTag);
			<#else>
			${poName} object = getObjectByUUId(uuid);
			count = ${firstLowerPoName}Dao.updateObjectByUUId(param, object);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.DATA_OPERATE_EXCEPTION_MSG);
		}
		if(count < 1 ){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.UPDATE_DATA_TO_DB_FAIL);
		}
		return count;
	}
	<#else>
    @Override
	public int updateObjectByUUId(Map<String, Object> param, String uuid) {
		return 0;
	}
	</#if>
	
	
	<#if existUuid == "0">
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
    @Override
	public int updateObjectByUUId(${poName} object, String uuid) {
		int count = 0 ;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			${poName} existObj = getObjectByUUId(uuid);
			Map<String, Object> param = CommonBeanUtils.transBean2Map(object);
			param = CommonBeanUtils.getValueMap(param);
			count = ${firstLowerPoName}Dao.updateObjectByUUId(param,existObj,factoryTag);
			<#else>
			${poName} existObj = getObjectByUUId(uuid);
			Map<String, Object> param = CommonBeanUtils.transBean2Map(object);
			param = CommonBeanUtils.getValueMap(param);
			count = ${firstLowerPoName}Dao.updateObjectByUUId(param,existObj);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.DATA_OPERATE_EXCEPTION_MSG);
		}
		if(count < 1 ){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.UPDATE_DATA_TO_DB_FAIL);
		}
		return count;
	}
	<#else>
    @Override
	public int updateObjectByUUId(${poName} object, String uuid) {
		return 0;
	}
	</#if>
	
	
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	@Override
	public int updateObjectByWhere(Map<String, Object> param) {
		int count = 0 ;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			count = ${firstLowerPoName}Dao.updateObjectByWhere(param,factoryTag);
			<#else>
			count = ${firstLowerPoName}Dao.updateObjectByWhere(param);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.DATA_OPERATE_EXCEPTION_MSG);
		}
		if(count < 1 ){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.UPDATE_DATA_TO_DB_FAIL);
		}
		return count;
	}
	
	
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	@Override
	public int updateObjectByWhere(${poName} object) {
		int count = 0 ;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			count = ${firstLowerPoName}Dao.updateObjectByWhere(object,factoryTag);
			<#else>
			count = ${firstLowerPoName}Dao.updateObjectByWhere(object);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.DATA_OPERATE_EXCEPTION_MSG);
		}
		if(count < 1 ){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.UPDATE_DATA_TO_DB_FAIL);
		}
		return count;
	}
	
	
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	@Override
	public int deleteObjectById(Object objId) {
		int count = 0 ;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			count = ${firstLowerPoName}Dao.deleteObjectById(objId,factoryTag);
			<#else>
			count = ${firstLowerPoName}Dao.deleteObjectById(objId);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.DATA_OPERATE_EXCEPTION_MSG);
		}
		if(count < 1 ){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.DELETE_DATA_TO_DB_FAIL);
		}
		return count;
	}

	<#if existUuid == "0">
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
    @Override
	public int deleteObjectByUUId(String uuid) {
		int count = 0 ;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			count = ${firstLowerPoName}Dao.deleteObjectByUUId(uuid,factoryTag);
			<#else>
			count = ${firstLowerPoName}Dao.deleteObjectByUUId(uuid);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.DATA_OPERATE_EXCEPTION_MSG);
		}
		if(count < 1 ){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.DELETE_DATA_TO_DB_FAIL);
		}
		return count;
	}
	<#else>
    @Override
	public int deleteObjectByUUId(String uuid) {
		return 0;
	}
	</#if>
	
	
	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	@Override
	public int deleteObjectByWhere(Map<String, Object> param) {
		int count = 0 ;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			count = ${firstLowerPoName}Dao.deleteObjectByWhere(param,factoryTag);
			<#else>
			count = ${firstLowerPoName}Dao.deleteObjectByWhere(param);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.DATA_OPERATE_EXCEPTION_MSG);
		}
		if(count < 1 ){
			throw new CommonException(CoreConstants.CommonMsgResult.DELETE_DATA_TO_DB_FAIL);
		}
		return count;
	}


	<#if useTransactional == "NO">
	<#else>
	@Transactional
	</#if>
	@Override
	public int deleteObjectByWhere(${poName} object) {
		int count = 0 ;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			count = ${firstLowerPoName}Dao.deleteObjectByWhere(object,factoryTag);
			<#else>
			count = ${firstLowerPoName}Dao.deleteObjectByWhere(object);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.DATA_OPERATE_EXCEPTION_MSG);
		}
		if(count < 1 ){
			throw new CommonException(CoreConstants.CommonMsgResult.DELETE_DATA_TO_DB_FAIL);
		}
		return count;
	}

	@Override
	public Pagination<${poName}> paginationObjects(Map<String, Object> param, int pageNo, int pageSize){
		Pagination<${poName}> pagination = null;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			pagination = ${firstLowerPoName}Dao.paginationObjects(param, pageNo, pageSize,factoryTag);
			<#else>
			pagination = ${firstLowerPoName}Dao.paginationObjects(param, pageNo, pageSize);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_PAGE_DATA_FAIL);
		}
		if(pagination==null){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_DB_NO_DATA);
		}
		return pagination;
	}

	@Override
	public Pagination<${poName}> paginationObjects(${poName} object, int pageNo, int pageSize){
		Pagination<${poName}> pagination = null;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			pagination = ${firstLowerPoName}Dao.paginationObjects(object, pageNo, pageSize,factoryTag);
			<#else>
			pagination = ${firstLowerPoName}Dao.paginationObjects(object, pageNo, pageSize);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_PAGE_DATA_FAIL);
		}
		if(pagination==null){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_DB_NO_DATA);
		}
		return pagination;
	}

	@Override
	public ${poName} findObjectByPros(Map<String, Object> param) {
		${poName} object = null;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			object = ${firstLowerPoName}Dao.findObjectByPros(param,factoryTag);
			<#else>
			object = ${firstLowerPoName}Dao.findObjectByPros(param);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.SELECT_DATA_FROM_DB_FAIL);
		}
		if(object==null){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_DB_NO_DATA);
		}
		return object;
	}

	@Override
	public ${poName} findObjectByPros(${poName} object) {
		${poName} result = null;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			result = ${firstLowerPoName}Dao.findObjectByPros(object,factoryTag);
			<#else>
			result = ${firstLowerPoName}Dao.findObjectByPros(object);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.SELECT_DATA_FROM_DB_FAIL);
		}
		if(result==null){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_DB_NO_DATA);
		}
		return result;
	}

	@Override
	public List<${poName}> findObjectListByPros(Map<String, Object> param) {
		List<${poName}> objects = null;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			objects = ${firstLowerPoName}Dao.findObjectListByPros(param,factoryTag);
			<#else>
			objects = ${firstLowerPoName}Dao.findObjectListByPros(param);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.SELECT_DATA_FROM_DB_FAIL);
		}
		if(objects==null || objects.size()==0 ){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_DB_NO_DATA);
		}
		return objects;
	}

	@Override
	public List<${poName}> findObjectListByPros(${poName} object) {
		List<${poName}> objects = null;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			objects = ${firstLowerPoName}Dao.findObjectListByPros(object,factoryTag);
			<#else>
			objects = ${firstLowerPoName}Dao.findObjectListByPros(object);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.SELECT_DATA_FROM_DB_FAIL);
		}
		if(objects==null || objects.size()==0 ){
			//异常信息由自己去配置文件中编写
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_DB_NO_DATA);
		}
		return objects;
	}

	@Override
	public int getObjectCount() {
		int count = 0 ;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			count = ${firstLowerPoName}Dao.getObjectCount(factoryTag);
			<#else>
			count = ${firstLowerPoName}Dao.getObjectCount();
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_TOTAL_DATA_FAIL);
		}
		if(count < 1 ){
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_DB_NO_DATA);
		}
		return count;
	}

	@Override
	public int getObjectCount(Map<String, Object> param) {
		int count = 0 ;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			count = ${firstLowerPoName}Dao.getObjectCount(param,factoryTag);
			<#else>
			count = ${firstLowerPoName}Dao.getObjectCount(param);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_TOTAL_DATA_FAIL);
		}
		if(count < 1){
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_DB_NO_DATA);
		}	
		return count;		
	}

	@Override
	public int getObjectCount(${poName} object) {
		int count = 0 ;
		try {
			<#if moreDbTag == "YES">
			String factoryTag = "${factoryTag}" ;
			count = ${firstLowerPoName}Dao.getObjectCount(object,factoryTag);
			<#else>
			count = ${firstLowerPoName}Dao.getObjectCount(object);
			</#if>
		}
		catch (Exception e) {
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_TOTAL_DATA_FAIL);
		}
		if(count < 1){
			throw new CommonException(CoreConstants.CommonMsgResult.QUERY_DB_NO_DATA);
		}	
		return count;		
	}
}
