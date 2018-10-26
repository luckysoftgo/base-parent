package com.application.base.core.utils;

import com.application.base.core.constant.CoreConstants;
import com.application.base.utils.common.Md5Utils;

/**
 * @desc 缓存键生成,获取工具
 * @author 孤狼
 */
public class CacheKeyUtils {

    /**
     * 用于当做表缓存key的key值;
     * 用于当做ID行缓存的前缀;
     * 获取数据库表key
     * @param clazz
     * @return
     */
    public static String createTableKey(Class<?> clazz){
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(CoreConstants.CacheBuff.TABLE).append(CoreConstants.Separator.UPDERLINE).append(clazz.getSimpleName());
        return keyBuilder.toString();
    }

    /**
     * MD5(ROW_CACHE_TABLEKEY_ROWID)
     * 根据数据库表key值和行key生成查询行记录结果的key(仅限id行缓存)
     * @param tableKey
     * @param rowKey
     * @return
     */
    public static String createRowResultCacheKey(String tableKey,String rowKey,String className){
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(CoreConstants.CacheBuff.ROW_CACHE).append(CoreConstants.Separator.UPDERLINE).append(className).append(CoreConstants.Separator.UPDERLINE).append(tableKey).append(CoreConstants.Separator.UPDERLINE).append(rowKey);
        String key = Md5Utils.md5Str(keyBuilder.toString());
        return key;
    }

    /**
     * MD5(TABLE_CACHE_TABLECACHEKEY_SQL)
     * 恒诚查询表缓存结果的key
     * @param tableKey
     * @param sql
     * @return
     */
    public static String createTableResultCacheKey(String tableKey,String sql){
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(CoreConstants.CacheBuff.TABLE_CACHE).append(CoreConstants.Separator.UPDERLINE).append(tableKey).append(CoreConstants.Separator.UPDERLINE).append(sql);
        String key = Md5Utils.md5Str(keyBuilder.toString());
        return key;
    }


    public static String createDatabaseResultCacheKey(String databaseKey,String sql){
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(CoreConstants.CacheBuff.DATABASE_CACHE).append(CoreConstants.Separator.UPDERLINE).append(databaseKey).append(CoreConstants.Separator.UPDERLINE).append(sql);
        String key = Md5Utils.md5Str(keyBuilder.toString());
        return key;
    }

}
