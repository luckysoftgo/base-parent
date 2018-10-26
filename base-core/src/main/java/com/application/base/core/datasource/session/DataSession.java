package com.application.base.core.datasource.session;

import java.util.List;

import com.application.base.core.datasource.param.CustomSql;
import org.apache.ibatis.session.SqlSession;

import com.application.base.core.datasource.param.Param;
import com.application.base.core.exception.DataAccessException;

/**
 * @desc 操作数据库的顶级接口的定义.
 * @author 孤狼
 */
public interface DataSession {

    /**
     *获得默认数据库sqlSession
     * @return
     * @throws DataAccessException
     */
    SqlSession getCurrentSession() throws DataAccessException ;
    
    /**
     *保存至数据库
     * @param clazz
     * @param obj
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> T saveObject(Class<T> clazz, T obj) throws DataAccessException;

    /**
     *批量插入数据
     * @param clazz
     * @param objs
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> boolean saveBatchObject(Class<T> clazz, List<T> objs) throws DataAccessException;
    
    /**
     *  通过ID,更新数据库对象
     * @param clazz
     * @param obj
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> int updateObjectById(Class<T> clazz, T obj) throws DataAccessException;

    /**
     * 通过UUID,更新数据库对象
     * @param clazz
     * @param obj
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> int updateObjectByUUId(Class<T> clazz, T obj) throws DataAccessException;
    
    /**
     * 根据 whereSql 更新固定列信息
     * @param clazz
     * @param param
     * @param whereSql
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> int updateCustomColumnByWhere(Class<T> clazz, Param param, CustomSql whereSql) throws DataAccessException;

    /**
     *逻辑删除数据库对象，设置 disabled=1
     * @param clazz
     * @param param
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> int logicDelete(Class<T> clazz, Param param) throws DataAccessException;

    /**
     *根据条件删除
     * @param clazz
     * @param whereSql
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> int logicWhereDelete(Class<T> clazz, CustomSql whereSql) throws DataAccessException;

    /**
     *物理删除数据库对象，执行 delete SQL语句
     * @param clazz
     * @param primaryKey
     * @param objId
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> int physicalDelete(Class<T> clazz, String primaryKey, Object objId) throws DataAccessException;

    /**
     *执行带where条件的物理删除
     * @param clazz
     * @param whereSql
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> int physicalWhereDelete(Class<T> clazz, CustomSql whereSql) throws DataAccessException;

    /**
     *根据对象ID查询数据库对象
     * @param clazz
     * @param primaryKey
     * @param objId
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> T querySingleResultById(Class<T> clazz,String primaryKey, Object objId) throws DataAccessException;

    /**
     *根据UUID查询数据库对象
     * @param clazz
     * @param uuid
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> T querySingleResultByUUId(Class<T> clazz, String uuid) throws DataAccessException;

    /**
     * 根据查询条件，查询单个数据库对象
     * @param clazz
     * @param param
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> T querySingleResultByParams(Class<T> clazz, Param param) throws DataAccessException;

    /**
     * 根据查询条件查询数据库对象列表
     * @param clazz
     * @param param
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> List<T> queryListResult(Class<T> clazz, Param param) throws DataAccessException;

    /**
     * 根据查询条件查询数据库对象列表
     * @param clazz
     * @param param
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> List<T> queryAllListResult(Class<T> clazz, Param param) throws DataAccessException;

    /**
     *根据where条件查询数据集合
     * @param clazz
     * @param whereSQL
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> List<T> queryListResultByWhere(Class<T> clazz, CustomSql whereSQL) throws DataAccessException;

    /**
     * 根据查询条件查询对象数量
     * @param clazz
     * @param param
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> int queryListResultCount(Class<T> clazz, Param param) throws DataAccessException;

    /**
     * 根据where条件查询数据库条数
     * @param clazz
     * @param whereSQL
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> int queryListResultCountByWhere(Class<T> clazz, CustomSql whereSQL) throws DataAccessException;

    /**
     *  Mapper模式查询，返回单一对象，可以提供VO查询
     * @param formater
     * @param able
     * @param param
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> T querySingleVO(Class<T> formater,Throwable able, Param param) throws DataAccessException;
    
    /**
     *可根据mapper中自定义的元素的ID操作
     * @param formater
     * @param elementId
     * @param param
     * @param <T>
     * @return
     * @throws DataAccessException
     */
    <T> T querySingleVOByCustomElementName(Class<T> formater,String elementId, Param param) throws DataAccessException;

    /**
     * Mapper模式查询，返回列表对象，可以提供VO查询
     * @param clazz
     * @param able
     * @param param
     * @param <T>
     * @return  List<Object>
     * @throws DataAccessException
     */
    <T> List<T> queryObjVOList(Class<T> clazz,Throwable able, Param param) throws DataAccessException;

    /**
     * 可根据mapper中自定义的元素的ID操作
     * @param clazz
     * @param elementId
     * @param param
     * @param <T>
     * @return List<Object>
     * @throws DataAccessException
     */
    <T> List<T> queryObjVOListByCustomElementName(Class<T> clazz,String elementId, Param param) throws DataAccessException;
    
}
