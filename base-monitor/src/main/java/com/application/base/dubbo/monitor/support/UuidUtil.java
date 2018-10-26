package com.application.base.dubbo.monitor.support;

import java.util.UUID;

/**
 * UUID生成工具类
 *
 *@author admin
 */
public class UuidUtil {

    /**
     * 获得UUID的方法
     *
     * @return
     */
    public static String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获得系统时间戳
     *
     * @return
     */
    public static long createIDForLong() {
        return System.currentTimeMillis();
    }

}