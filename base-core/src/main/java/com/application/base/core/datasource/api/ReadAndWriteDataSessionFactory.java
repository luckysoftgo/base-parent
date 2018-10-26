package com.application.base.core.datasource.api;

import com.application.base.core.datasource.session.DataSession;

/**
 * @dees 读写数据源的设置.
 * @author 孤狼
 */
public interface ReadAndWriteDataSessionFactory extends DataSessionFactory{

    /**
     * 获取读库数据访问session
     * @return
     */
    DataSession getReadDataSession();

    /**
     * 获取写库数据访问session
     * @return
     */
    DataSession getWriteDataSession();
    
}
