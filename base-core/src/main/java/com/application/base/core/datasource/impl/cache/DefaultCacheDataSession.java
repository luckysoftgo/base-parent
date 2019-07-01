package com.application.base.core.datasource.impl.cache;

import com.application.base.cache.redis.api.RedisSession;
import com.application.base.cache.redis.exception.RedisException;
import com.application.base.cache.redis.factory.RedisSessionFactory;
import com.application.base.core.datasource.impl.common.DefaultDataSession;
import com.application.base.core.datasource.param.CustomSql;
import com.application.base.core.datasource.param.ESQL;
import com.application.base.core.datasource.param.Param;
import com.application.base.core.datasource.param.SqlCreator;
import com.application.base.core.datasource.session.CacheDataSession;
import com.application.base.core.exception.DataAccessException;
import com.application.base.core.utils.CacheKeyUtils;
import com.application.base.core.utils.SqlUtil;
import com.application.base.utils.common.BaseStringUtil;
import com.application.base.utils.common.Constants;
import com.application.base.utils.common.UUIDProvider;
import com.application.base.utils.json.JsonConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 默认的缓存 Session 数据源.
 * @author 孤狼
 */
public class DefaultCacheDataSession extends DefaultDataSession implements CacheDataSession {
    
    /**
     * mybatis 数据源设置.
     */
    private SqlSessionFactory sqlSessionFactory;
    /**
     * redis 数据源设置.
     */
    private RedisSessionFactory redisSessionFactory;
   
    /**
     * sql session设置.
     */
    private SqlSession session;
    
    /**
     * map size
     */
    int mapSize = 16;
    
    
    public DefaultCacheDataSession() {
    	super();
    }

    public DefaultCacheDataSession(SqlSessionFactory sqlSessionFactory, RedisSessionFactory redisSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.redisSessionFactory = redisSessionFactory;
    }
    
    @Override
    public RedisSession getRedisSession() {
        return redisSessionFactory.getRedisSession();
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
     * 存储对象。
     */
    @Override
    public <T> T saveObject(Class<T> clazz, T object) throws DataAccessException {
        T result;
        try {
            result = super.saveObject(clazz, object);
            updateTableKey(clazz); 
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }
    
    /**
     * 批量存储对象。
     */
    @Override
    public <T> boolean saveBatchObject(Class<T> clazz, List<T> objs) throws DataAccessException {
        boolean result = false;
        try {
            result = super.saveBatchObject(clazz, objs);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }
    
    /**
     * 修改对象。
     */
    @Override
    public <T> int updateObjectById(Class<T> clazz, T obj) throws DataAccessException {
        int result;
        try {
            result = super.updateObjectById(clazz, obj);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    /**
     * 修改对象。
     */
    @Override
    public <T> int updateObjectByUUId(Class<T> clazz, T obj) throws DataAccessException {
        int result;
        try {
            result = super.updateObjectByUUId(clazz, obj);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    /**
     * 通过条件修改对象。
     */
    @Override
    public <T> int updateCustomColumnByWhere(Class<T> clazz, Param param, CustomSql whereSql) throws DataAccessException {
        int result;
        try {
            result = super.updateCustomColumnByWhere(clazz, param, whereSql);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }
    
    /**
     * 逻辑删除对象。
     */
    @Override
    public <T> int logicDelete(Class<T> clazz, Param param) throws DataAccessException {
        int result;
        try {
            result = super.logicDelete(clazz, param);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }
    
    /**
     * 逻辑删除对象。
     */
    @Override
    public <T> int logicWhereDelete(Class<T> clazz, CustomSql whereSql) throws DataAccessException {
        int result;
        try {
            result = super.logicWhereDelete(clazz, whereSql);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    /**
     * 物理删除对象。
     */
    @Override
    public <T> int physicalDelete(Class<T> clazz, String primaryKey, Object objId) throws DataAccessException {
        int result;
        try {
            result = super.physicalDelete(clazz, primaryKey,objId);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }
    
    /**
     * 物理删除对象。
     */
    @Override
    public <T> int physicalWhereDelete(Class<T> clazz, CustomSql whereSql) throws DataAccessException {
        int result;
        try {
            result = super.physicalWhereDelete(clazz, whereSql);
            updateTableKey(clazz);
            updateDatabaseKey();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return result;
    }
    
    /**
     * 通过id查找对象。
     */
    @Override
    public <T> T querySingleResultById(Class<T> clazz, String primaryKey, Object objId) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleResultById(clazz, primaryKey,objId);
            } else {
                //如果是行ID缓存行,则将行缓存的表对应的类名做前缀拼接
                String tableCacheKey = redis.getData(CacheKeyUtils.createTableKey(clazz));
                String rowKey = CacheKeyUtils.createRowResultCacheKey(tableCacheKey, String.valueOf(objId),clazz.getSimpleName());
                obj = redis.getTypeObject(clazz, rowKey);
                //如果getTypeObject方法中转化错误,返回NULL,则删除redis中的缓存
                if (BaseStringUtil.isJsonEmpty(obj)) {
                    obj = super.querySingleResultById(clazz, primaryKey,objId);
                    if (obj == null) {
						redis.delete(rowKey);
					} else {
						redis.setData(rowKey, JsonConvertUtils.toJson(obj));
					}
                }
                logger.info("[根据rowKey:{},获取值:{}]", rowKey, JsonConvertUtils.toJson(obj));
            }
        } catch (RedisException e) {
            logger.error("querySingleResultById:RedisException:{}", e);
            obj = super.querySingleResultById(clazz, primaryKey, objId);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    /**
     * 通过uuid查找对象。
     */
    @Override
    public <T> T querySingleResultByUUId(Class<T> clazz, String uuid) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleResultByUUId(clazz, uuid);
            } else {
                //如果是UUID缓存行,则直接用数据行的UUID作为缓存KEY
                String tableCacheKey = redis.getData(CacheKeyUtils.createTableKey(clazz));
                String rowKey = CacheKeyUtils.createRowResultCacheKey(tableCacheKey, uuid,clazz.getSimpleName());
                obj = redis.getTypeObject(clazz, rowKey);
                if (BaseStringUtil.isJsonEmpty(obj)) {
                    obj = super.querySingleResultByUUId(clazz, uuid);
                    if (obj == null) {
						redis.delete(rowKey);
					} else {
						redis.setData(rowKey, JsonConvertUtils.toJson(obj));
					}
                }
                logger.info("[根据rowKey:{},获取值:{}]", rowKey, JsonConvertUtils.toJson(obj));
            }
           } catch (RedisException e) {
            logger.error("querySingleResultByUUId:RedisException:{}", e);
            obj = super.querySingleResultByUUId(clazz, uuid);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }
    
    /**
     * 通过属性查找对象。
     */
    @Override
    public <T> T querySingleResultByParams(Class<T> clazz, Param param) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleResultByParams(clazz, param);
            } else {
                //表缓存的KEY由两部分数据组成  表缓存key=md5(前缀+表缓存所在表的缓存key+_+sql)
                String resultKey = createTableResultKey(clazz, ESQL.QUERYSINGLERESULTBYPARAMS, param.get(), redis);
                obj = redis.getTypeObject(clazz, resultKey);
                if (BaseStringUtil.isJsonEmpty(obj)) {
                    obj = super.querySingleResultByParams(clazz, param);
                    if (obj == null) {
						redis.delete(resultKey);
					} else {
						redis.setData(resultKey, JsonConvertUtils.toJson(obj));
					}
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, JsonConvertUtils.toJson(obj));
            }
        } catch (RedisException e) {
            logger.error("querySingleResultByParams:RedisException:{}", e);
            obj = super.querySingleResultByParams(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    /**
     * 查找对象的集合。
     */
    @Override
    public <T> List<T> queryListResult(Class<T> clazz, Param param) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryListResult(clazz, param);
            } else {
                String resultKey = createTableResultKey(clazz, ESQL.QUERYLISTRESULT, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    list = super.queryListResult(clazz, param);
                    if (list == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JsonConvertUtils.toJson(list);
                        redis.setData(resultKey,jsonResult);
                    }
                } else {
                    list = JsonConvertUtils.fromListJson(jsonResult,clazz);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryListResult:RedisException:{}", e);
            list = super.queryListResult(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    /**
     * 查找对象的集合。
     */
    @Override
    public <T> List<T> queryAllListResult(Class<T> clazz, Param param) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryAllListResult(clazz, param);
            } else {
                String resultKey = createTableResultKey(clazz, ESQL.QUERYALLLISTRESULT, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    list = super.queryListResult(clazz, param);
                    if (list == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JsonConvertUtils.toJson(list);
                        redis.setData(resultKey,jsonResult);
                    }
                } else {
                    list = JsonConvertUtils.fromListJson(jsonResult, clazz);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryAllListResult:RedisException:{}", e);
            list = super.queryListResult(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }
    
    /**
     * 通过条件查找集合。
     */
    @Override
    public <T> List<T> queryListResultByWhere(Class<T> clazz, CustomSql whereSQL) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryListResultByWhere(clazz, whereSQL);
            } else {
                String whereSqlStr = whereSQL.toString();
                Map<String, Object> map = new HashMap<>(mapSize);
                map.put("whereSqlStr", whereSqlStr);
                String resultKey = createTableResultKey(clazz, ESQL.QUERYLISTRESULTBYWHERE, map, redis);
                String jsonResult = redis.getData(resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    list = super.queryListResultByWhere(clazz, whereSQL);
                    if (list == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JsonConvertUtils.toJson(list);
                        redis.setData(resultKey, jsonResult);
                    }
                } else {
                    list = JsonConvertUtils.fromListJson(jsonResult, clazz);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryListResultByWhere:RedisException:{}", e);
            list = super.queryListResultByWhere(clazz, whereSQL);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    /**
     * 获得集合的大小。
     */
    @Override
    public <T> int queryListResultCount(Class<T> clazz, Param param) throws DataAccessException {
        int count = 0 ;
    	try {
            count = super.queryListResultCount(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return count;
    }
    
    /**
     * 获得集合的大小。
     */
    @Override
    public <T> int queryListResultCountByWhere(Class<T> clazz, CustomSql whereSQL) {
        int count = 0 ;
    	try {
            count = super.queryListResultCountByWhere(clazz, whereSQL);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return count;
    }

    
    /*************************************************************************** VO对象操作 **************************************************************************/
    
    
    /**
     * 查找 VO 对象。
     */
    @Override
    public <T> T querySingleVO(Class<T> formater, Throwable able, Param param) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleVO(formater,able, param);
            } else {
                String resultKey = createDatabaseResultKey(able, null, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    obj = super.querySingleVO(formater,able, param);
                    if (obj == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JsonConvertUtils.toJson(obj);
                        redis.setData(resultKey, jsonResult);
                    }
                } else {
                    obj = JsonConvertUtils.fromJsonHasNull(jsonResult,formater);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("querySingleVO:RedisException:{}", e);
            obj = super.querySingleVO(formater,able, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    
    /**
     * 通过customsql 的 id 查找对象
     */
    @Override
    public <T> T querySingleVOByCustomElementName(Class<T> formater, String elementId, Param param) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleVOByCustomElementName(formater,elementId, param);
            } else {
                String resultKey = createDatabaseResultKey(null, elementId, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    obj = super.querySingleVOByCustomElementName(formater,elementId, param);
                    if (obj == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JsonConvertUtils.toJson(obj);
                        redis.setData(resultKey, jsonResult);
                    }
                } else {
                    obj = JsonConvertUtils.fromJsonHasNull(jsonResult,formater);
                }
                logger.info("[根据databaseKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("querySingleVOByCustomElementName:RedisException:{}", e);
            obj = super.querySingleVOByCustomElementName(formater,elementId, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    
    /**
     * 通过customsql 的 id 查找对象
     */
    @Override
    public <T> List<T> queryObjVOList(Class<T> clazz, Throwable able, Param param) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryObjVOList(clazz, able, param);
            } else {
                String resultKey = createDatabaseResultKey(able, null, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    list = super.queryObjVOList(clazz, able, param);
                    if (list == null) {
                        redis.delete(resultKey);
                    } else {
                        jsonResult = JsonConvertUtils.toJson(list);
                        redis.setData(resultKey, jsonResult);
                    }
                } else {
                    list = JsonConvertUtils.fromListJson(jsonResult, clazz);
                }
                logger.info("[根据databaseKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryVOList:RedisException:{}", e);
            list = super.queryObjVOList(clazz, able, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }


    /**
     * 通过customsql 的 id 查找对象
     */
    @Override
    public <T> List<T> queryObjVOListByCustomElementName(Class<T> clazz, String elementId, Param param) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryObjVOListByCustomElementName(clazz, elementId, param);
            } else {
                String resultKey = createDatabaseResultKey(null, elementId, param.get(), redis);
                String jsonResult = redis.getData(resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    list = super.queryObjVOListByCustomElementName(clazz, elementId, param);
                    if (list != null) {
                        jsonResult = JsonConvertUtils.toJson(list);
                        redis.setData(resultKey, jsonResult);
                    }
                } else {
                    list = JsonConvertUtils.fromListJson(jsonResult, clazz);
                }
                logger.info("[根据databaseKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryVOListByCustomElementName:RedisException:{}", e);
            list = super.queryObjVOListByCustomElementName(clazz, elementId, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    /**
     * 更新table Key
     *
     * @param clazz
     */
    private <T> void updateTableKey(Class<T> clazz) {
        String tableKey = null;
        try {
            //表进行添加操作,需要让表KEY失效
            RedisSession redis = redisSessionFactory.getRedisSession();
            if (redis != null) {
                //更新表Key
                String cacheTableKey = CacheKeyUtils.createTableKey(clazz);
                tableKey = UUIDProvider.uuid();
                redis.setData(cacheTableKey, tableKey);
                logger.info("更新表:{},key:{}", cacheTableKey, tableKey);
            }
        } catch (RedisException e) {
            //处理了RedisException,不向上抛
            logger.debug("更新表Key异常:{}", e);
        }
    }

    /**
     * 更新库级缓存
     *
     * @return
     */
    private void updateDatabaseKey() {
        String databaseKey = null;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            if (redis != null) {
                //更新表Key
                databaseKey = UUIDProvider.uuid();
                redis.setData(Constants.SqlConstants.DATABASE_KEY, databaseKey);
                logger.info("更新库:{},key:{}", Constants.SqlConstants.DATABASE_KEY, databaseKey);
            }
        } catch (RedisException e) {
            //处理了RedisException,不向上抛
            logger.debug("更新表Key异常:{}", e);
        }
    }

    /**
     * 获取表缓存结果的KEY
     *
     * @param clazz 表po
     * @param map   查询参数
     * @param redis
     * @param <T>
     * @return
     */
    private <T> String createTableResultKey(Class<T> clazz, ESQL esql, Map<String, Object> map, RedisSession redis) {
        try {
            String sql = SqlUtil.getSql(getCurrentSession(), SqlCreator.set(clazz, esql).get(), map);
            String tableCacheKey = redis.getData(CacheKeyUtils.createTableKey(clazz));
            return CacheKeyUtils.createTableResultCacheKey(tableCacheKey, sql);
        } catch (RedisException e) {
            //处理了RedisException,不向上抛e
            logger.debug("更新表Key异常:{}", e);
        } catch (Exception e) {
            logger.error("创建表级结果key异常:{}", e);
            throw new DataAccessException(e);
        }
        return null;
    }

    /**
     * 创建库级缓存结果KEY
     *
     * @param able
     * @param sqlId
     * @param map
     * @param redis
     * @return
     */
    private String createDatabaseResultKey(Throwable able, String sqlId, Map<String, Object> map, RedisSession redis) {
        try {
            if (able == null && StringUtils.isEmpty(sqlId)) {
				return null;
			}
            if (able != null) {
                String sql = SqlUtil.getSql(getCurrentSession(), SqlCreator.set(Constants.SqlConstants.CUSTOMER_SQL, able).get(), map);
                String databaseCacheKey = redis.getData(Constants.SqlConstants.DATABASE_KEY);
                return CacheKeyUtils.createDatabaseResultCacheKey(databaseCacheKey, sql);
            } else if (!StringUtils.isEmpty(sqlId)) {
                String sql = SqlUtil.getSql(getCurrentSession(), sqlId, map);
                String databaseCacheKey = redis.getData(Constants.SqlConstants.DATABASE_KEY);
                return CacheKeyUtils.createDatabaseResultCacheKey(databaseCacheKey, sql);
            }
        } catch (RedisException e) {
            //处理了RedisException,不向上抛e
            logger.debug("更新表Key异常:{}", e);
        }
        return null;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    /**--------------------------------------------------------------------------------选择redis的库进行存储数据.----------------------------------------------------------------------------------------------------**/
    
    
    
    
    
    
    
    
    
    
    /**
     * 通过id查找对象。
     */
    public <T> T querySingleResultByIdByDB(int index,Class<T> clazz, String primaryKey, Object objId) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleResultById(clazz, primaryKey,objId);
            } else {
                //如果是行ID缓存行,则将行缓存的表对应的类名做前缀拼接
                String tableCacheKey = redis.getData(index,CacheKeyUtils.createTableKey(clazz));
                String rowKey = CacheKeyUtils.createRowResultCacheKey(tableCacheKey, String.valueOf(objId),clazz.getSimpleName());
                obj = redis.getTypeObject(index,clazz, rowKey);
                //如果getTypeObject方法中转化错误,返回NULL,则删除redis中的缓存
                if (BaseStringUtil.isJsonEmpty(obj)) {
                    obj = super.querySingleResultById(clazz, primaryKey,objId);
                    if (obj == null) {
						redis.delete(index, rowKey);
					} else{
						redis.setData(index, rowKey, JsonConvertUtils.toJson(obj));
					}
                }
                logger.info("[根据rowKey:{},获取值:{}]", rowKey, JsonConvertUtils.toJson(obj));
            }
        } catch (RedisException e) {
            logger.error("querySingleResultById:RedisException:{}", e);
            obj = super.querySingleResultById(clazz, primaryKey, objId);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    /**
     * 通过uuid查找对象。
     */
    public <T> T querySingleResultByUUIdByDB(int index, Class<T> clazz, String uuid) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
        	 //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleResultByUUId(clazz, uuid);
            } else {
                //如果是UUID缓存行,则直接用数据行的UUID作为缓存KEY
                String tableCacheKey = redis.getData(index,CacheKeyUtils.createTableKey(clazz));
                String rowKey = CacheKeyUtils.createRowResultCacheKey(tableCacheKey, uuid,clazz.getSimpleName());
                obj = redis.getTypeObject(index,clazz, rowKey);
                if (BaseStringUtil.isJsonEmpty(obj)) {
                    obj = super.querySingleResultByUUId(clazz, uuid);
                    if (obj == null) {
						redis.delete(index, rowKey);
					} else{
                        redis.setData(index, rowKey, JsonConvertUtils.toJson(obj));
					}

                }
                logger.info("[根据rowKey:{},获取值:{}]", rowKey, JsonConvertUtils.toJson(obj));
            }
           } catch (RedisException e) {
            logger.error("querySingleResultByUUId:RedisException:{}", e);
            obj = super.querySingleResultByUUId(clazz, uuid);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }
    
   
    /**
     * 通过属性查找对象。
     */
    public <T> T querySingleResultByParamsByDB(int index,Class<T> clazz, Param param) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleResultByParams(clazz, param);
            } else {
                //表缓存的KEY由两部分数据组成  表缓存key=md5(前缀+表缓存所在表的缓存key+_+sql)
                String resultKey = createTableResultKey(clazz, ESQL.QUERYSINGLERESULTBYPARAMS, param.get(), redis);
                obj = redis.getTypeObject(index,clazz, resultKey);
                if (BaseStringUtil.isJsonEmpty(obj)) {
                    obj = super.querySingleResultByParams(clazz, param);
                    if (obj == null) {
						redis.delete(index, resultKey);
					} else{
                        redis.setData(index, resultKey, JsonConvertUtils.toJson(obj));
					}
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, JsonConvertUtils.toJson(obj));
            }

        } catch (RedisException e) {
            logger.error("querySingleResultByParams:RedisException:{}", e);
            obj = super.querySingleResultByParams(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    /**
     * 查找对象的集合。
     */
    public <T> List<T> queryListResultByDB(int index,Class<T> clazz, Param param) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryListResult(clazz, param);
            } else {
                String resultKey = createTableResultKey(clazz, ESQL.QUERYLISTRESULT, param.get(), redis);
                String jsonResult = redis.getData(index,resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    list = super.queryListResult(clazz, param);
                    if (list == null) {
                        redis.delete(index,resultKey);
                    } else {
                        jsonResult = JsonConvertUtils.toJson(list);
                        redis.setData(index, resultKey, jsonResult);
                    }
                } else {
                    list = JsonConvertUtils.fromListJson(jsonResult, clazz);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryListResult:RedisException:{}", e);
            list = super.queryListResult(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    

    /**
     * 查找对象的集合。
     */
    public <T> List<T> queryAllListResultByDB(int index,Class<T> clazz, Param param) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryListResult(clazz, param);
            } else {
                String resultKey = createTableResultKey(clazz, ESQL.QUERYALLLISTRESULT, param.get(), redis);
                String jsonResult = redis.getData(index,resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    list = super.queryListResult(clazz, param);
                    if (list == null) {
                        redis.delete(index,resultKey);
                    } else {
                        jsonResult = JsonConvertUtils.toJson(list);
                        redis.setData(index,resultKey, jsonResult);
                    }
                } else {
                    list = JsonConvertUtils.fromListJson(jsonResult, clazz);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryAllListResult:RedisException:{}", e);
            list = super.queryListResult(clazz, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    
    /**
     * 通过条件查找集合。
     */
    public <T> List<T> queryListResultByWhereByDB(int index,Class<T> clazz, CustomSql whereSQL) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryListResultByWhere(clazz, whereSQL);
            } else {
                String whereSqlStr = whereSQL.toString();
                Map<String, Object> map = new HashMap<>(mapSize);
                map.put("whereSqlStr", whereSqlStr);
                String resultKey = createTableResultKey(clazz, ESQL.QUERYLISTRESULTBYWHERE, map, redis);
                String jsonResult = redis.getData(index,resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    list = super.queryListResultByWhere(clazz, whereSQL);
                    if (list == null) {
                        redis.delete(index,resultKey);
                    } else {
                        jsonResult = JsonConvertUtils.toJson(list);
                        redis.setData(index,resultKey, jsonResult);
                    }
                } else {
                    list = JsonConvertUtils.fromListJson(jsonResult, clazz);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryListResultByWhere:RedisException:{}", e);
            list = super.queryListResultByWhere(clazz, whereSQL);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }
    
    
    /**
     * 查找 VO 对象。
     */
    public <T> T querySingleVOByDB(int index,Class<T> formater,Throwable able, Param param) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleVO(formater,able, param);
            } else {
                String resultKey = createDatabaseResultKey(able, null, param.get(), redis);
                String jsonResult = redis.getData(index,resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    obj = super.querySingleVO(formater,able, param);
                    if (obj == null) {
                        redis.delete(index,resultKey);
                    } else {
                        jsonResult = JsonConvertUtils.toJson(obj);
                        redis.setData(index,resultKey, jsonResult);
                    }
                } else {
                    obj = JsonConvertUtils.fromJsonHasNull(jsonResult,formater);
                }
                logger.info("[根据tableKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("querySingleVO:RedisException:{}", e);
            obj = super.querySingleVO(formater,able, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    
    /**
     * 通过customsql 的 id 查找对象
     */
    public <T> T querySingleVOByCustomElementNameByDB(int index,Class<T> formater,String elementId, Param param) throws DataAccessException {
        T obj;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                obj = super.querySingleVOByCustomElementName(formater,elementId, param);
            } else {
                String resultKey = createDatabaseResultKey(null, elementId, param.get(), redis);
                String jsonResult = redis.getData(index,resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    obj = super.querySingleVOByCustomElementName(formater,elementId, param);
                    if (obj == null) {
                        redis.delete(index,resultKey);
                    } else {
                        jsonResult = JsonConvertUtils.toJson(obj);
                        redis.setData(index,resultKey, jsonResult);
                    }
                } else {
                    obj = JsonConvertUtils.fromJsonHasNull(jsonResult,formater);
                }
                logger.info("[根据databaseKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("querySingleVOByCustomElementName:RedisException:{}", e);
            obj = super.querySingleVOByCustomElementName(formater,elementId, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return obj;
    }

    
    /**
     * 通过customsql 的 id 查找对象
     */
    public <T> List<T> queryVOListByDB(int index,Class<T> clazz, Throwable able, Param param) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryObjVOList(clazz, able, param);
            } else {
                String resultKey = createDatabaseResultKey(able, null, param.get(), redis);
                String jsonResult = redis.getData(index,resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    list = super.queryObjVOList(clazz, able, param);
                    if (list == null) {
                        redis.delete(index,resultKey);
                    } else {
                        jsonResult = JsonConvertUtils.toJson(list);
                        redis.setData(index,resultKey, jsonResult);
                    }
                } else {
                    list = JsonConvertUtils.fromListJson(jsonResult, clazz);
                }
                logger.info("[根据databaseKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryVOList:RedisException:{}", e);
            list = super.queryObjVOList(clazz, able, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    /**
     * 通过customsql 的 id 查找对象
     */
    public <T> List<T> queryVOListByCustomElementNameByDB(int index,Class<T> clazz, String elementId, Param param) throws DataAccessException {
        List<T> list;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            //未获取缓存链接,执行数据库操作并返回
            if (redis == null) {
                list = super.queryObjVOListByCustomElementName(clazz, elementId, param);
            } else {
                String resultKey = createDatabaseResultKey(null, elementId, param.get(), redis);
                String jsonResult = redis.getData(index,resultKey);
                if (BaseStringUtil.isJsonEmpty(jsonResult)) {
                    list = super.queryObjVOListByCustomElementName(clazz, elementId, param);
                    if (list != null) {
                        jsonResult = JsonConvertUtils.toJson(list);
                        redis.setData(index,resultKey, jsonResult);
                    }
                } else {
                    list = JsonConvertUtils.fromListJson(jsonResult, clazz);
                }
                logger.info("[根据databaseKey:{},获取值:{}]", resultKey, jsonResult);
            }
        } catch (RedisException e) {
            logger.error("queryVOListByCustomElementName:RedisException:{}", e);
            list = super.queryObjVOListByCustomElementName(clazz, elementId, param);
        } catch (Exception e) {
            logger.error("cacheDao访问异常:{}", e);
            throw new DataAccessException(e);
        }
        return list;
    }

    /**
     * 更新table Key
     *
     * @param clazz
     */
    private <T> void updateTableKeyByDB(int index,Class<T> clazz) {
        String tableKey = null;
        try {
            //表进行添加操作,需要让表KEY失效
            RedisSession redis = redisSessionFactory.getRedisSession();
            if (redis != null) {
                //更新表Key
                String cacheTableKey = CacheKeyUtils.createTableKey(clazz);
                tableKey = UUIDProvider.uuid();
                redis.setData(index,cacheTableKey, tableKey);
                logger.info("更新表:{},key:{}", cacheTableKey, tableKey);
            }
        } catch (RedisException e) {
            //处理了RedisException,不向上抛
            logger.debug("更新表Key异常:{}", e);
        }
    }

    /**
     * 更新库级缓存
     *
     * @return
     */
    private void updateDatabaseKeyByDB(int index) {
        String databaseKey = null;
        try {
            RedisSession redis = redisSessionFactory.getRedisSession();
            if (redis != null) {
                //更新表Key
                databaseKey = UUIDProvider.uuid();
                redis.setData(index,Constants.SqlConstants.DATABASE_KEY, databaseKey);
                logger.info("更新库:{},key:{}", Constants.SqlConstants.DATABASE_KEY, databaseKey);
            }
        } catch (RedisException e) {
            //处理了RedisException,不向上抛
            logger.debug("更新表Key异常:{}", e);
        }
    }

}
