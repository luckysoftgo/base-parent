package com.application.base.core.apisupport;

/**
 * @Author: 孤狼
 * @desc: 通用模板设置.
 */
public interface CacheLoadable<T> {

    /**
     * 获取缓存中的数据
     * @return
     */
    T load();

}
