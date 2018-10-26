package com.application.base.utils.common;

import org.slf4j.Logger;

/**
 * @desc 异常打印字符串等工具方法
 * @author 孤狼 .
 */
public class ExceptionInfo {
    /**
     * 打印异常栈信息前置标识符:" at"
     */
    private static String BLANK_SEPARATOR = "\tat ";

    /**
     * 返回异常栈信息的字符串
     *
     * @param ex
     * @return
     */
    public static void exceptionInfo(Exception ex, Logger logger) {

        StackTraceElement[] trace = ex.getStackTrace();
        //链接异常信息
        synchronized (logger){
            logger.error(ex.toString());
            for (StackTraceElement traceElement : trace){
                logger.error("{}{}",BLANK_SEPARATOR,traceElement.toString());
            }
        }
    }
}
