package com.application.base.core.datasource.impl.common;

import com.application.base.core.datasource.impl.cache.ObjectMapUtil;
import com.application.base.core.datasource.param.CustomSql;
import com.application.base.core.datasource.param.SqlCreator;
import com.application.base.core.datasource.session.DataSession;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.core.datasource.param.ESQL;
import com.application.base.core.datasource.param.Param;
import com.application.base.core.exception.DataAccessException;
import com.application.base.core.utils.SqlUtil;
import com.application.base.utils.common.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 默认的 Session处理
 * @author 孤狼
 */
public class DefaultDataSession implements DataSession {

    public static final Logger logger = LoggerFactory.getLogger(DefaultDataSession.class.getName());
	/**
	 * sqlsession 工厂
	 */
	private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * update 字符串
	 */
	private static final String UPDATE_STRING = "updateSqlStr";
	/**
	 * where 字符串
	 */
	private static final String WHERE_STRING = "whereSqlStr";
	
	/**
	 * sqlsession 实例
	 */
	private SqlSession session;
	
	/**
	 * map size
	 */
	int mapSize = 16;
	
    public DefaultDataSession() {
    }
	
	/**
	 * 注入的 sqlSessionFactory
	 * @param sqlSessionFactory
	 */
	public DefaultDataSession(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    
    @Override
	public SqlSession getCurrentSession() {
    	String sqlSessionFactoryBeanName = (String) ObjectMapUtil.sessionFactoryVal.get(sqlSessionFactory);
    	SqlSession cacheSession = ObjectMapUtil.sessionKey.get(sqlSessionFactoryBeanName);
    	if (cacheSession==null) {
    		if (session == null){
            	session = new SqlSessionTemplate(sqlSessionFactory);
            	//存储对象.
        		ObjectMapUtil.sessionKey.put(sqlSessionFactoryBeanName, session);
        		ObjectMapUtil.sessionVal.put(session, sqlSessionFactoryBeanName);
            }
		}else {
			session = cacheSession; 
		}
         return session;
    }
    
    /**
     * 存储对象
     */
    @Override
	public <T> T saveObject(Class<T> clazz, T object) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<>(mapSize);
            map.put("obj", object);
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.SAVE).get(), map));
            int count = session.insert(SqlCreator.set(clazz, ESQL.SAVE).get(), object);
            if (count > 0) {
				return object;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    /**
     * 批量存储对象.
     */
	@Override
	public <T> boolean saveBatchObject(Class<T> clazz, List<T> objs) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<>(mapSize);
            map.put("list", objs);
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.SAVEBATCH).get(), map));
            int count = session.insert(SqlCreator.set(clazz, ESQL.SAVEBATCH).get(), objs);
            if (count > 0) {
				return true;
			}else{
				return false;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

	/**
	 * 通过id修改对象
	 */
	@Override
	public <T> int updateObjectById(Class<T> clazz, T obj) throws DataAccessException {
		 try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<>(mapSize);
            map.put("obj", obj);
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.UPDATEBYID).get(), map));
            int count = session.update(SqlCreator.set(clazz, ESQL.UPDATEBYID).get(), obj);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
	}

	/**
	 * 通过uuid修改对象
	 */
	@Override
	public <T> int updateObjectByUUId(Class<T> clazz, T obj) throws DataAccessException {
		try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<>(mapSize);
            map.put("obj", obj);
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.UPDATEBYUUID).get(), map));
            int count = session.update(SqlCreator.set(clazz, ESQL.UPDATEBYUUID).get(), obj);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
	}
    
    /**
     * 逻辑删除对象.
     */
    @Override
	public <T> int logicDelete(Class<T> clazz, Param param) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.LOGICDELETE).get(), param.get()));
            int count = session.update(SqlCreator.set(clazz, ESQL.LOGICDELETE).get(), param.get());
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }
    
    /**
     * 物理删除对象
     */
    @Override
	public <T> int physicalDelete(Class<T> clazz, String primaryKey, Object objId) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<>(mapSize);
            map.put(primaryKey, objId);
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.PHYSICALDELETE).get(), map));
            int count = session.delete(SqlCreator.set(clazz, ESQL.PHYSICALDELETE).get(), objId);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    /**
     * 通过id查找对象.
     */
	@Override
	public <T> T querySingleResultById(Class<T> clazz, String primaryKey, Object objId) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<>(mapSize);
            map.put(primaryKey,objId);
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.QUERYSINGLERESULTBYID).get(), map));
            T object = session.selectOne(SqlCreator.set(clazz, ESQL.QUERYSINGLERESULTBYID).get(), objId);
            if (object != null ) {
				return object;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    /**
     * 通过uuid查找对象.
     */
    @Override
	public <T> T querySingleResultByUUId(Class<T> clazz, String uuid) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            Map<String, Object> map = new HashMap<>(mapSize);
            map.put("uuid", uuid);
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.QUERYSINGLERESULTBYUUID).get(), map));
            T object = session.selectOne(SqlCreator.set(clazz, ESQL.QUERYSINGLERESULTBYUUID).get(), uuid);
            if (object != null ) {
				return object;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    /**
     * 通过条件查询对象
     */
    @Override
	public <T> T querySingleResultByParams(Class<T> clazz, Param param) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.QUERYSINGLERESULTBYPARAMS).get(), param.get()));
            T object = session.selectOne(SqlCreator.set(clazz, ESQL.QUERYSINGLERESULTBYPARAMS).get(), param.get());
            if (object != null ) {
				return object;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    /**
     * 通过条件查询集合
     */
    @Override
	public <T> List<T> queryListResult(Class<T> clazz, Param param) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.QUERYLISTRESULT).get(), param.get()));
            List<T> list = session.selectList(SqlCreator.set(clazz, ESQL.QUERYLISTRESULT).get(), param.get());
            if (list != null && list.size() > 0 ) {
				return list;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    /**
     * 通过条件查询集合
     */
    @Override
	public <T> List<T> queryAllListResult(Class<T> clazz, Param param) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.QUERYALLLISTRESULT).get(), param.get()));
            List<T> list = session.selectList(SqlCreator.set(clazz, ESQL.QUERYALLLISTRESULT).get(), param.get());
            if (list != null && list.size() > 0 ) {
				return list;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    /**
     * 通过条件查询个数
     */
    @Override
	public <T> int queryListResultCount(Class<T> clazz, Param param) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.QUERYLISTRESULTCOUNT).get(), param.get()));
            int count = session.selectOne(SqlCreator.set(clazz, ESQL.QUERYLISTRESULTCOUNT).get(), param.get());
            if (count > 0) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }
    
    /**
     * 物理删除操作
     */
    @Override
	public <T> int physicalWhereDelete(Class<T> clazz, CustomSql whereSql) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            String whereSqlStr = whereSql.toString();
            Map<String, Object> map = new HashMap<>(mapSize);
            map.put(WHERE_STRING, whereSqlStr);
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.PHYSICALWHEREDELETE).get(), map));
            int count = session.delete(SqlCreator.set(clazz, ESQL.PHYSICALWHEREDELETE).get(), map);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    /**
     * 通过条件更改操作
     */
    @Override
	public <T> int updateCustomColumnByWhere(Class<T> clazz, Param param, CustomSql whereSql)
            throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            Map<String, Object> strMap = new HashMap<>(mapSize);
            strMap.put(UPDATE_STRING, SqlUtil.updateSql(param));
            strMap.put(WHERE_STRING, whereSql.toString());
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.UPDATECUSTOMCOLUMNBYWHERE).get(), strMap));
            int count = session.update(SqlCreator.set(clazz, ESQL.UPDATECUSTOMCOLUMNBYWHERE).get(), strMap);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    
    /**
     * 逻辑删除对象
     */
    @Override
	public <T> int logicWhereDelete(Class<T> clazz, CustomSql whereSql) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            String whereSqlStr = whereSql.toString();
            Map<String, Object> map = new HashMap<>(mapSize);
            map.put(WHERE_STRING, whereSqlStr);
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.LOGICWHEREDELETE).get(), map));
            int count = session.update(SqlCreator.set(clazz, ESQL.LOGICWHEREDELETE).get(), map);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    
    /**
     * 通过条件查询对象
     */
    @Override
	public <T> List<T> queryListResultByWhere(Class<T> clazz, CustomSql whereSQL) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            String whereSqlStr = whereSQL.toString();
            Map<String, Object> map = new HashMap<>(mapSize);
            map.put(WHERE_STRING, whereSqlStr);
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.QUERYLISTRESULTBYWHERE).get(), map));
            List<T> list = session.selectList(SqlCreator.set(clazz, ESQL.QUERYLISTRESULTBYWHERE).get(), map);
            if (list != null && list.size() > 0 ) {
				return list;
			}else{
				return null;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    /**
     * 通过条件查询对象个数
     */
    @Override
	public <T> int queryListResultCountByWhere(Class<T> clazz, CustomSql whereSQL) throws DataAccessException {
        try {
            SqlSession session = getCurrentSession();
            String whereSqlStr = whereSQL.toString();
            Map<String, Object> map = new HashMap<>(mapSize);
            map.put(WHERE_STRING, whereSqlStr);
            logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(clazz, ESQL.QUERYLISTRESULTCOUNTBYWHERE).get(), map));
            int count = session.selectOne(SqlCreator.set(clazz, ESQL.QUERYLISTRESULTCOUNTBYWHERE).get(), map);
            if (count > 0 ) {
				return count;
			}else{
				return 0;
			}
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }
	
	/**
	 * 通过CustomerSQL文件中的id来查找对象集合
	 */
	@Override
	public <T> T querySingleVO(Class<T> formater, Throwable able, Param param) throws DataAccessException {
		try {
			SqlSession session = getCurrentSession();
			logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(Constants.SqlConstants.CUSTOMER_SQL, able).get(), param.get()));
			T t = session.selectOne(SqlCreator.set(Constants.SqlConstants.CUSTOMER_SQL, able).get(), param.get());
			if (t != null ) {
				return t;
			}else{
				return null;
			}
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}
	
	/**
	 * 通过CustomerSQL文件中的id来查找对象
	 */
	@Override
	public <T> T querySingleVOByCustomElementName(Class<T> formater, String elementId, Param param)
			throws DataAccessException {
		try {
			SqlSession session = getCurrentSession();
			logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(Constants.SqlConstants.CUSTOMER_SQL, elementId).get(), param.get()));
			T t = session.selectOne(SqlCreator.set(Constants.SqlConstants.CUSTOMER_SQL, elementId).get(), param.get());
			if (t != null ) {
				return t;
			}else{
				return null;
			}
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}
	
	
	/**
	 * 通过参数查找对象
	 */
	@Override
	public <T> List<T> queryObjVOList(Class<T> clazz, Throwable able, Param param) throws DataAccessException {
		try {
			SqlSession session = getCurrentSession();
			logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(Constants.SqlConstants.CUSTOMER_SQL, able).get(), param.get()));
			List<T> list = session.selectList(SqlCreator.set(Constants.SqlConstants.CUSTOMER_SQL, able).get(), param.get());
			if (list != null && list.size() > 0 ) {
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}
	
	
	/**
	 * 通过CustomerSQL文件中的id来查找对象集合
	 */
	@Override
	public <T> List<T> queryObjVOListByCustomElementName(Class<T> clazz, String elementId, Param param)
			throws DataAccessException {
		try {
			SqlSession session = getCurrentSession();
			logger.debug("sql:[{}]", SqlUtil.getSql(session, SqlCreator.set(Constants.SqlConstants.CUSTOMER_SQL, elementId).get(), param.get()));
			List<T> list = session.selectList(SqlCreator.set(Constants.SqlConstants.CUSTOMER_SQL, elementId).get(), param.get());
			if (list != null && list.size() > 0 ) {
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}
	
}
